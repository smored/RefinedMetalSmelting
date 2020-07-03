package com.smore_d.rms.items;

import com.smore_d.rms.RefinedMetalSmelting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Item.Properties().group(RefinedMetalSmelting.TAB));
    }
}
