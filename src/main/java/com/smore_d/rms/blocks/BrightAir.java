package com.smore_d.rms.blocks;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;

public class BrightAir extends AirBlock {

    public BrightAir() {
        super(Block.Properties.create(Material.AIR)
        .setLightLevel(value -> 15));
    }



}
