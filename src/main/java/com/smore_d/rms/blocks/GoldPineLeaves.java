package com.smore_d.rms.blocks;


import com.smore_d.rms.RefinedMetalSmelting;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.FallingDustParticle;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class GoldPineLeaves extends LeavesBlock {

    public GoldPineLeaves() {
        super(Properties.create(Material.LEAVES)
                .hardnessAndResistance(2.0F, 2.0F)
                .sound(SoundType.PLANT)
                .harvestLevel(6) // not a final value, just for testing
                .harvestTool(ToolType.AXE)
                .setLightLevel(value -> 15)
                .notSolid()
        );
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (RefinedMetalSmelting.RANDOM.nextInt(4) == 3) { // decrease the frequency
            BlockPos below = pos.add(0, -1, 0);
            if (worldIn.getBlockState(below) == Blocks.AIR.getDefaultState()) {
                worldIn.addParticle(ParticleTypes.DRIPPING_HONEY,
                        pos.getX() + RefinedMetalSmelting.RANDOM.nextInt(100) / 100D,
                        pos.getY(),
                        pos.getZ() + RefinedMetalSmelting.RANDOM.nextInt(100) / 100D,
                        0, 0, 0
                );
            }
        }
    }

}
