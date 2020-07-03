package com.smore_d.rms.blocks;

import com.smore_d.rms.RefinedMetalSmelting;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {

    public BlockItemBase(Block block) {
        super(block, new Item.Properties().group(RefinedMetalSmelting.TAB));
    }
}
