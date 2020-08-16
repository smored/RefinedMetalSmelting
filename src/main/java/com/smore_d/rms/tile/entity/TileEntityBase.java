package com.smore_d.rms.tile.entity;

import com.google.common.collect.ImmutableList;
import com.smore_d.rms.blocks.BlockRMS;
import com.smore_d.rms.network.NetworkHandler;
import com.smore_d.rms.network.NetworkUtils;
import com.smore_d.rms.network.PacketDescription;
import com.smore_d.rms.network.SyncedField;
import com.smore_d.rms.network.packets.DescSynced;
import com.smore_d.rms.network.packets.IDescSynced;
import com.smore_d.rms.util.RMSUtils;
import com.smore_d.rms.util.RMSValues;
import com.smore_d.rms.util.TileEntityCache;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.List;

import static com.smore_d.rms.util.RMSUtils.xlate;

public abstract class TileEntityBase extends TileEntity implements IDescSynced, IGUIButtonSensitive {

    private static final List<String> REDSTONE_LABELS = ImmutableList.of(
            "rms.gui.tab.redstoneBehaviour.button.anySignal",
            "rms.gui.tab.redstoneBehaviour.button.highSignal",
            "rms.gui.tab.redstoneBehaviour.button.lowSignal"
    );

    int poweredRedstone;

    boolean firstRun = true;  // True only the first time updateEntity invokes in a session
    private boolean forceFullSync;
    private List<SyncedField<?>> descriptionFields;
    private TileEntityCache[] tileCache;
    private boolean preserveStateOnBreak = false; // set to true if shift-wrenched to keep upgrades in the block
    private float actualSpeedMult = RMSValues.DEF_SPEED_UPGRADE_MULTIPLIER;
    private float actualUsageMult = RMSValues.DEF_SPEED_UPGRADE_USAGE_MULTIPLIER;

    // tracks which synced fields have changed and need to be synced on the next tick
    private BitSet fieldsToSync;

    public TileEntityBase(TileEntityType type) {
        super(type);
    }

    public String getBlockTranslationKey() {
        return "block.rms." + getType().getRegistryName().getPath();
    }

    /**
     * Call this from {@link INamedContainerProvider#getDisplayName()}
     * @return display name for this TE's GUI
     */
    ITextComponent getDisplayNameInternal() {
        return new TranslationTextComponent(getBlockTranslationKey());
    }

    // server side, chunk sending
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compound = super.getUpdateTag();
        return new PacketDescription(this, true).writeNBT(compound);
    }

    // client side, chunk sending
    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        new PacketDescription(tag).process();
    }

    /***********
     We don't override getUpdatePacket() or onDataPacket() because TE sync'ing is all handled
     by our custom PacketDescription and the @DescSynced system
     ***********/

    @Override
    public BlockPos getPosition() {
        return getPos();
    }

    @Override
    public boolean shouldSyncField(int idx) {
        return fieldsToSync.get(idx);
    }

    @Override
    public List<SyncedField<?>> getDescriptionFields() {
        if (descriptionFields == null) {
            descriptionFields = NetworkUtils.getSyncedFields(this, DescSynced.class);
            fieldsToSync = new BitSet(descriptionFields.size());
            for (SyncedField<?> field : descriptionFields) {
                field.update();
            }
        }
        return descriptionFields;
    }

    public void sendDescriptionPacket() {
        sendDescriptionPacket(256);
    }

    void sendDescriptionPacket(double maxPacketDistance) {
        PacketDescription descPacket = new PacketDescription(this, forceFullSync);
        if (descPacket.hasData()) {
            NetworkHandler.sendToAllAround(descPacket, world, maxPacketDistance);
        }
        fieldsToSync.clear();
        forceFullSync = false;
    }

    /**
     * A way to safely mark a block for an update from another thread (like the CC Lua thread).
     */
    void scheduleDescriptionPacket() {
        forceFullSync = true;
    }

    /*
     * Even though this class doesn't implement ITickableTileEntity, we'll keep the base update() logic here; classes
     * which extend non-tickable subclasses might need it (e.g. TileEntityPressureChamberInterface)
     */
    void tickImpl() {
        if (firstRun && !world.isRemote) {
            onFirstServerTick();
            onNeighborTileUpdate();
            onNeighborBlockUpdate();
        }
        firstRun = false;

        if (!world.isRemote) {
            //getCapability(PNCCapabilities.HEAT_EXCHANGER_CAPABILITY).ifPresent(IHeatExchangerLogic::tick);

            for (int i = 0; i < getDescriptionFields().size(); i++) {
                if (getDescriptionFields().get(i).update()) {
                    fieldsToSync.set(i);
                }
            }

            if (forceFullSync || !fieldsToSync.isEmpty()) {
                sendDescriptionPacket();
            }
        }
    }

    @Override
    public void remove() {
        super.remove();

        if (getInventoryCap().isPresent()) getInventoryCap().invalidate();
    }

    protected void onFirstServerTick() {
    }

    protected void updateNeighbours() {
        world.notifyNeighborsOfStateChange(getPos(), getBlockState().getBlock());
    }

    public void onBlockRotated() {
        if (this instanceof ISideConfigurable) {
            for (SideConfigurator<?> sc : ((ISideConfigurable) this).getSideConfigurators()) {
                sc.setupFacingMatrix();
            }
        }
    }

    void rerenderTileEntity() {
        world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 0);
    }

    protected boolean shouldRerenderChunkOnDescUpdate() {
        return false;
        //return this instanceof ICamouflageableTE;
    }

    /**
     * Encoded into the description packet. Also included in saved data written by {@link TileEntityBase#write(CompoundNBT)}
     *
     * Prefer to use @DescSynced where possible - use this either for complex fields not handled by @DescSynced,
     * or for non-ticking tile entities.
     *
     * @param tag NBT tag
     */
    @Override
    public void writeToPacket(CompoundNBT tag) {
        if (this instanceof ISideConfigurable) {
            tag.put("SideConfiguration", SideConfigurator.writeToNBT((ISideConfigurable) this));
        }
    }

    /**
     * Encoded into the description packet. Also included in saved data read by {@link TileEntityBase#read(BlockState, CompoundNBT)}.
     *
     * Prefer to use @DescSynced where possible - use this either for complex fields not handled by @DescSynced,
     * or for non-ticking tile entities.
     *
     * @param tag NBT tag
     */
    @Override
    public void readFromPacket(CompoundNBT tag) {
        if (this instanceof ISideConfigurable) {
            SideConfigurator.readFromNBT(tag.getCompound("SideConfiguration"), (ISideConfigurable) this);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);

        /*getCapability(PNCCapabilities.HEAT_EXCHANGER_CAPABILITY)
                .ifPresent(logic -> tag.put(NBTKeys.NBT_HEAT_EXCHANGER, logic.serializeNBT()));

        if (this instanceof ISerializableTanks) {
            tag.put(NBTKeys.NBT_SAVED_TANKS, ((ISerializableTanks) this).serializeTanks());
        }

         */
        writeToPacket(tag);

        return tag;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        /*
        getCapability(PNCCapabilities.HEAT_EXCHANGER_CAPABILITY)
                .ifPresent(logic -> logic.deserializeNBT(tag.getCompound(NBTKeys.NBT_HEAT_EXCHANGER)));

        if (this instanceof ISerializableTanks) {
            ((ISerializableTanks) this).deserializeTanks(tag.getCompound(NBTKeys.NBT_SAVED_TANKS));
        }

         */
        readFromPacket(tag);
    }

    @Override
    public void validate() {
        super.validate();

        scheduleDescriptionPacket();  // TODO verify that we actually need this
    }

    @Override
    public void onDescUpdate() {
        if (shouldRerenderChunkOnDescUpdate()) {
            rerenderTileEntity();
            //if (this instanceof ICamouflageableTE) requestModelDataUpdate();
        }
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        /*
        if (this instanceof ICamouflageableTE) {
            return new ModelDataMap.Builder()
                    .withInitial(BlockPneumaticCraftCamo.BLOCK_ACCESS, world)
                    .withInitial(BlockPneumaticCraftCamo.BLOCK_POS, pos)
                    .withInitial(BlockPneumaticCraftCamo.CAMO_STATE, ((ICamouflageableTE) this).getCamouflage())
                    .build();
        }

         */
        return super.getModelData();
    }

    /**
     * Called when a key is synced in the container.
     */
    public void onGuiUpdate() {
    }

    public Direction getRotation() {
        BlockState state = getBlockState();
        return state.getBlock() instanceof BlockRMS ? ((BlockRMS) state.getBlock()).getRotation(state) : Direction.NORTH;
    }

    @Override
    public void updateContainingBlockInfo() {
        super.updateContainingBlockInfo();
    }

    public float getSpeedMultiplierFromUpgrades() {
        return actualSpeedMult;
    }

    public float getSpeedUsageMultiplierFromUpgrades() {
        return actualUsageMult;
    }

    @Override
    public void handleGUIButtonPress(String tag, boolean shiftHeld, PlayerEntity player) {
    }

    public boolean isGuiUseableByPlayer(PlayerEntity player) {
        return getWorld().getTileEntity(getPos()) == this && player.getDistanceSq(getPos().getX() + 0.5D, getPos().getY() + 0.5D, getPos().getZ() + 0.5D) <= 64.0D;
    }

    public void onNeighborTileUpdate() {
        for (TileEntityCache cache : getTileCache()) {
            cache.update();
        }
    }

    public TileEntityCache[] getTileCache() {
        if (tileCache == null) tileCache = TileEntityCache.getDefaultCache(getWorld(), getPos());
        return tileCache;
    }

    public TileEntity getCachedNeighbor(Direction dir) {
        return getTileCache()[dir.getIndex()].getTileEntity();
    }

    public void onNeighborBlockUpdate() {
        poweredRedstone = RMSUtils.getRedstoneLevel(getWorld(), getPos());
        for (TileEntityCache cache : getTileCache()) {
            cache.update();
        }
    }

    public boolean redstoneAllows() {
        switch (((IRedstoneControl) this).getRedstoneMode()) {
            case 0:
                return true;
            case 1:
                return poweredRedstone > 0;
            case 2:
                return poweredRedstone == 0;
        }
        return false;
    }

    /**
     * Take a fluid-containing from the input slot, use it to fill the primary input tank of the tile entity,
     * and place the resulting emptied container in the output slot.
     *
     * @param inputSlot input slot
     * @param outputSlot output slot
     */
    void processFluidItem(int inputSlot, int outputSlot) {
        getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(itemHandler -> {
            ItemStack inputStack = itemHandler.getStackInSlot(inputSlot);
            if (inputStack.getCount() > 1) return;

            FluidUtil.getFluidHandler(inputStack).ifPresent(fluidHandlerItem -> {
                FluidStack itemContents = fluidHandlerItem.drain(1000, IFluidHandler.FluidAction.SIMULATE);

                getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(fluidHandler -> {
                    if (!itemContents.isEmpty()) {
                        // input item contains fluid: drain from input item into tank, move to output if empty
                        FluidStack transferred = FluidUtil.tryFluidTransfer(fluidHandler, fluidHandlerItem, itemContents.getAmount(), true);
                        if (transferred.getAmount() == itemContents.getAmount()) {
                            // all transferred; move empty container to output if possible
                            ItemStack emptyContainerStack = fluidHandlerItem.getContainer();
                            ItemStack excess = itemHandler.insertItem(outputSlot, emptyContainerStack, true);
                            if (excess.isEmpty()) {
                                itemHandler.extractItem(inputSlot, 1, false);
                                itemHandler.insertItem(outputSlot, emptyContainerStack, false);
                            }
                        }
                    } else if (itemHandler.getStackInSlot(outputSlot).isEmpty()) {
                        // input item(s) is/are empty: drain from tank to one input item, move to output
                        FluidStack transferred = FluidUtil.tryFluidTransfer(fluidHandlerItem, fluidHandler, Integer.MAX_VALUE, true);
                        if (!transferred.isEmpty()) {
                            itemHandler.extractItem(inputSlot, 1, false);
                            ItemStack filledContainerStack = fluidHandlerItem.getContainer();
                            itemHandler.insertItem(outputSlot, filledContainerStack, false);
                        }
                    }
                });
            });
        });
    }

    public abstract IItemHandler getPrimaryInventory();

    @Nonnull
    protected LazyOptional<IItemHandler> getInventoryCap() {
        // for internal use only!
        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, getInventoryCap());
        }
        return super.getCapability(cap, side);
    }

    /**
     * Collect all items which should be dropped when this TE is broken.  Override and extend this in subclassing
     * TE's which have extra inventories to be dropped.
     *
     * @param drops list in which to collect dropped items
     */
    public void getContentsToDrop(NonNullList<ItemStack> drops) {
        RMSUtils.collectNonEmptyItems(getPrimaryInventory(), drops);
        /*
        if (this instanceof ICamouflageableTE) {
            BlockState camoState = ((ICamouflageableTE) this).getCamouflage();
            if (camoState != null) {
                drops.add(ICamouflageableTE.getStackForState(camoState));
            }
        }

         */
    }

    public final ITextComponent getRedstoneButtonText(int mode) {
        if (mode >= 0 && mode < getRedstoneButtonLabels().size()) {
            return xlate(getRedstoneButtonLabels().get(mode));
        } else {
            return new StringTextComponent("<ERROR>");
        }
    }

    protected List<String> getRedstoneButtonLabels() {
        return REDSTONE_LABELS;
    }

    public int getRedstoneModeCount() {
        return getRedstoneButtonLabels().size();
    }

    public ITextComponent getRedstoneTabTitle() {
        return this instanceof IRedstoneControlled ?
                xlate("rms.gui.tab.redstoneBehaviour.enableOn") :
                xlate("rms.gui.tab.redstoneBehaviour.emitRedstoneWhen");
    }

    /**
     * Should this tile entity preserve its state (currently: upgrades and stored air) when broken?
     * By default this is true when sneak-wrenched, and false when broken by pick.
     *
     * @return true if state should be preserved, false otherwise
     */
    public boolean shouldPreserveStateOnBreak() {
        return preserveStateOnBreak;
    }

    public void setPreserveStateOnBreak(boolean preserveStateOnBreak) {
        this.preserveStateOnBreak = preserveStateOnBreak;
    }

    /**
     * Get any extra data to be serialized onto a dropped item stack. The supplied tag is the "BlockEntityTag" subtag of
     * the item's NBT data, so will be automatically deserialized into the TE when the itemblock is next placed.
     *
     * @param blockEntityTag the existing "BlockEntityTag" subtag to add data to
     * @param preserveState true when dropped with a wrench, false when broken with a pickaxe etc.
     */
    public void serializeExtraItemData(CompoundNBT blockEntityTag, boolean preserveState) {
    }
}
