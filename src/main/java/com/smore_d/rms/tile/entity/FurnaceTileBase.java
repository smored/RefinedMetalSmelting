package com.smore_d.rms.tile.entity;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.FurnaceFuelSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

import static net.minecraftforge.common.ForgeHooks.getBurnTime;

public abstract class FurnaceTileBase extends TileEntityInv implements ITickableTileEntity, IRecipeHolder, IRecipeHelperPopulator {

    private static final int[] SLOTS_UP = new int[]{0};
    private static final int[] SLOTS_DOWN = new int[]{2, 1};
    private static final int[] SLOTS_HORIZONTAL = new int[]{1};

    private int furnaceBurnTime;

    private int currentItemBurnTime;
    private int cookTime;
    private int totalCookTime = this.getCookTime();
    private int timer;
    private final Map<ResourceLocation, Integer> recipeUseCounts = Maps.newHashMap();

    public Map<ResourceLocation, Integer> getRecipeUseCounts() {
        return this.recipeUseCounts;
    }

    protected IRecipeType<? extends AbstractCookingRecipe> recipeType;

    public FurnaceTileBase(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, 3);
        this.recipeType = IRecipeType.SMELTING;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(world.getBlockState(pkt.getPos()), pkt.getNbtCompound());
        world.notifyBlockUpdate(pos, world.getBlockState(pos).getBlock().getDefaultState(), world.getBlockState(pos), 2);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compound = new CompoundNBT();

        this.write(compound);
        return compound;
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, this.getUpdateTag());
    }


    @Override
    public void read(BlockState state, CompoundNBT tag) {
        ItemStackHelper.loadAllItems(tag, this.inventory);
        this.furnaceBurnTime = tag.getInt("BurnTime");
        this.cookTime = tag.getInt("CookTime");
        this.totalCookTime = tag.getInt("CookTimeTotal");
        this.timer = 0;
        this.currentItemBurnTime = getBurnTime(this.inventory.get(1));
        int i = tag.getShort("RecipesUsedSize");
        for (int j = 0; j < i; ++j) {
            ResourceLocation resourcelocation = new ResourceLocation(tag.getString("RecipeLocation" + j));
            int k = tag.getInt("RecipeAmount" + j);
            this.recipeUseCounts.put(resourcelocation, k);
        }
        super.read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        ItemStackHelper.saveAllItems(tag, this.inventory);
        tag.putInt("BurnTime", this.furnaceBurnTime);
        tag.putInt("CookTime", this.cookTime);
        tag.putInt("CookTimeTotal", this.totalCookTime);
        tag.putShort("RecipesUsedSize", (short) this.recipeUseCounts.size());
        int i = 0;

        for (Map.Entry<ResourceLocation, Integer> entry : this.recipeUseCounts.entrySet()) {
            tag.putString("RecipeLocation" + i, entry.getKey().toString());
            tag.putInt("RecipeAmount" + i, entry.getValue());
            ++i;
        }

        return super.write(tag);
    }

    protected int getCookTime() {
        //TODO Add an obtion to increase the cooktime based on some modifiers to the furnace like augments or overclocking
        return 20;
    }

    public final IIntArray fields = new IIntArray() {
        public int get(int index) {
            switch (index) {
                case 0:
                    return FurnaceTileBase.this.furnaceBurnTime;
                case 1:
                    return FurnaceTileBase.this.currentItemBurnTime;
                case 2:
                    return FurnaceTileBase.this.cookTime;
                case 3:
                    return FurnaceTileBase.this.totalCookTime;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch (index) {
                case 0:
                    FurnaceTileBase.this.furnaceBurnTime = value;
                    break;
                case 1:
                    FurnaceTileBase.this.currentItemBurnTime = value;
                    break;
                case 2:
                    FurnaceTileBase.this.cookTime = value;
                    break;
                case 3:
                    FurnaceTileBase.this.totalCookTime = value;
            }

        }

        public int size() {
            return 4;
        }
    };

    @Override
    public void tick() {
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote) {
            timer++;
            if (this.totalCookTime != this.getCookTime()) {
                this.totalCookTime = this.getCookTime();
            }
            if (this.recipeType != IRecipeType.SMELTING) {
                this.recipeType = IRecipeType.SMELTING;
            }
            ItemStack itemstack = this.inventory.get(1);
            if (this.isBurning() || !itemstack.isEmpty() && !this.inventory.get(0).isEmpty()) {
                IRecipe<?> irecipe = this.world.getRecipeManager().getRecipe((IRecipeType<AbstractCookingRecipe>) this.recipeType, this, this.world).orElse(null);
                if (!this.isBurning() && this.canSmelt(irecipe)) {
                    this.furnaceBurnTime = getBurnTime(itemstack) * this.getCookTime() / 200;
                    this.currentItemBurnTime = this.furnaceBurnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (itemstack.hasContainerItem()) {
                            this.inventory.set(1, itemstack.getContainerItem());
                        } else if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.shrink(1);
                            if (itemstack.isEmpty()) {
                                Item item1 = item.getContainerItem();
                                this.inventory.set(1, item1 == null ? ItemStack.EMPTY : new ItemStack(item1));
                            }
                        }
                    }
                }
                if (this.isBurning() && this.canSmelt(irecipe)) {
                    ++this.cookTime;
                    if (this.cookTime >= this.totalCookTime) {
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime();
                        this.smeltItem(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }
            if (timer % 24 == 0) {
                BlockState state = world.getBlockState(pos);
                if (state.get(BlockStateProperties.LIT) != this.furnaceBurnTime > 0) {
                    world.setBlockState(pos, state.with(BlockStateProperties.LIT, this.furnaceBurnTime > 0), 3);
                }
            }
        }

        if (flag1) {
            this.markDirty();
        }
    }

    private void removeFuel() {
        this.inventory.get(2).shrink(1);
    }

    private void smeltItem(@Nullable IRecipe recipe) {
        timer = 0;
        if (recipe != null && this.canSmelt(recipe)) {
            ItemStack itemstack = this.inventory.get(0);
            ItemStack itemstack1 = recipe.getRecipeOutput();
            ItemStack itemstack2 = this.inventory.get(2);
            if (itemstack2.isEmpty()) {
                this.inventory.set(2, itemstack1.copy());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (!this.world.isRemote) {
                this.canUseRecipe(this.world,null, recipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.inventory.get(1).isEmpty() && this.inventory.get(1).getItem() == Items.BUCKET) {
                this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
        }
    }


    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    private boolean canSmelt(@Nullable IRecipe recipe) {
        if (!this.inventory.get(0).isEmpty() && recipe != null) {
            ItemStack itemstack = recipe.getRecipeOutput();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack1 = this.inventory.get(2);
                if (itemStack1.isEmpty()) {
                    return true;
                } else if (!itemStack1.isItemEqual(itemstack)) {
                    return false;
                } else if (itemStack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemStack1.getCount() < itemStack1.getMaxStackSize()) {
                    return true;
                } else {
                    return itemStack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
            net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Nonnull
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
        if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }
        /**
         if (cap == CapabilityEnergy.ENERGY) {
         return energy.cast();
         }
         **/
        return super.getCapability(capability, facing);
    }

    @Override
    public void onCrafting(PlayerEntity player) {
    }

    @Override
    public void fillStackedContents(RecipeItemHelper helper) {
        for (ItemStack itemstack : this.inventory) {
            helper.accountStack(itemstack);
        }

    }

    @Override
    public void setRecipeUsed(IRecipe recipe) {
        if (this.recipeUseCounts.containsKey(recipe.getId())) {
            this.recipeUseCounts.put(recipe.getId(), this.recipeUseCounts.get(recipe.getId()) + 1);
        } else {
            this.recipeUseCounts.put(recipe.getId(), 1);
        }

    }

    @Nullable
    public IRecipe getRecipeUsed() {
        return null;
    }

    @Override
    public int[] IgetSlotsForFace(Direction side) {
        if (side == Direction.DOWN) {
            return SLOTS_DOWN;
        } else {
            return side == Direction.UP ? SLOTS_UP : SLOTS_HORIZONTAL;
        }
    }

    @Override
    public boolean IcanExtractItem(int index, ItemStack stack, Direction direction) {
        if (direction == Direction.DOWN && index == 1) {
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean IisItemValidForSlot(int index, ItemStack stack) {
        if (index == 2) {
            return false;
        } else if (index != 1) {
            return true;
        } else {
            ItemStack itemstack = this.inventory.get(1);
            return FurnaceTileEntity.isFuel(stack) || FurnaceFuelSlot.isBucket(stack) && itemstack.getItem() != Items.BUCKET;
        }
    }

    public static boolean isItemFuel(ItemStack stack) {
        return getBurnTime(stack) > 0;
    }
}
