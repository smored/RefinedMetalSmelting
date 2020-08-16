package com.smore_d.rms.items;

import com.smore_d.rms.RefinedMetalSmelting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemWrench extends Item {

    public ItemWrench(Properties properties) {
        super(properties);
    }

    public ItemWrench() {
        super(new Item.Properties().group(RefinedMetalSmelting.TAB));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        // too early to use a capability here :(
        if (this.isInGroup(group)) {
            items.add(new ItemStack(this));

            ItemStack stack = new ItemStack(this);
            items.add(stack);
        }
    }
}
