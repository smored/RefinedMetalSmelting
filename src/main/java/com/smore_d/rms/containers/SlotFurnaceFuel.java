package com.smore_d.rms.containers;

import com.smore_d.rms.tile.entity.FurnaceTileBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SlotFurnaceFuel extends Slot {

    FurnaceTileBase te;

    public SlotFurnaceFuel(FurnaceTileBase te, int index, int xPosition, int yPosition) {
        super(te, index, xPosition, yPosition);
        this.te = te;
    }

    public boolean isItemValid(ItemStack stack) {
        return this.te.isItemFuel(stack) || isBucket(stack);
    }

    public int getItemStackLimit(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getItemStackLimit(stack);
    }

    public static boolean isBucket(ItemStack stack) {
        return stack.getItem() == Items.BUCKET;
    }
}
