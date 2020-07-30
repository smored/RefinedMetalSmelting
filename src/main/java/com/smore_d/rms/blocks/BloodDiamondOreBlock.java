package com.smore_d.rms.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BloodDiamondOreBlock extends OreBlock {


    public BloodDiamondOreBlock() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(50.0f, 1200.0f)
                .sound(SoundType.STONE)
                .harvestLevel(6)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
                .setLightLevel(value -> 15)
        );
    }

    @Override  //                                                      fortune affects xp drop?????
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return 1;
    }

}