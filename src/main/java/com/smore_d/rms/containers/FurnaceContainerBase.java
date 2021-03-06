package com.smore_d.rms.containers;

import com.smore_d.rms.tile.entity.FurnaceTileBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class FurnaceContainerBase extends Container {

    protected FurnaceTileBase te;
    protected IIntArray fields;
    protected PlayerEntity playerEntity;
    protected IItemHandler playerInv;
    protected final World world;

    public FurnaceContainerBase(ContainerType<?> containerType, int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        this(containerType, windowId, world, pos, playerInventory, player, new IntArray(4));
    }

    public FurnaceContainerBase(ContainerType<?> containerType, int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IIntArray fields) {
        super(containerType, windowId);
        this.te = (FurnaceTileBase) world.getTileEntity(pos);
        this.playerEntity = player;
        this.playerInv = new InvWrapper(playerInventory);
        this.world = playerInventory.player.world;
        this.fields = fields;
        assert(this.fields.size() == 4);
        this.trackIntArray(this.fields);

        this.addSlot(new Slot(te, 0, 56, 17));
        this.addSlot(new SlotFurnaceFuel(this.te, 1, 56, 53));
        this.addSlot(new SlotFurnace(playerEntity, te, 2, 116, 35));
        //this.addSlot(new SlotFurnaceFuel(te, 3, 26, 35));
        layoutPlayerInventorySlots(8, 84);
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        return this.te.isBurning();
    }

    @OnlyIn(Dist.CLIENT)
    public int getCookScaled(int pixels) {
        int lvt_1_1_ = this.fields.get(2);
        //int lvt_2_1_ = this.fields.get(3);
        //return lvt_2_1_ != 0 && lvt_1_1_ != 0 ? lvt_1_1_ * pixels / lvt_2_1_ : 0;
        return lvt_1_1_ != 0 ?  lvt_1_1_ : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnScaled(int pixels) {
        int lvt_1_1_ = this.fields.get(1);
        if (lvt_1_1_ == 0) {
            lvt_1_1_ = 200;
        }

        return this.fields.get(0) * pixels / lvt_1_1_;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        this.te.fields.set(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 1 && index != 0) {
                if (this.hasRecipe(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (FurnaceTileBase.isItemFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 29) {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 4, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }



    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInv, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInv, 0, leftCol, topRow, 9, 18);
    }
    protected boolean hasRecipe(ItemStack itemStack) {
        return this.world.getRecipeManager().getRecipe(IRecipeType.SMELTING, new Inventory(new ItemStack[]{itemStack}), this.world).isPresent();
    }
}
