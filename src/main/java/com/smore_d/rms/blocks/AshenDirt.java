package com.smore_d.rms.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.ToolType;

public class AshenDirt extends Block {

    public AshenDirt() {
        super(Block.Properties.create(Material.EARTH)
                .hardnessAndResistance(0.5f, 0.5f)
                .sound(SoundType.PLANT)
                .harvestTool(ToolType.SHOVEL)
        );
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }

}
