package com.smore_d.rms.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class WeakBoronNitrideOreBlock extends OreBlock {


    public WeakBoronNitrideOreBlock() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(25.0f, 1200.0f)
                .sound(SoundType.STONE)
                .harvestLevel(4)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
        );
    }

    @Override
    public int getExpDrop(BlockState state, IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return 1;
    }

}
