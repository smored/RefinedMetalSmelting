package com.smore_d.rms.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IWrenchable {
    boolean onWrenched(World world, PlayerEntity player, BlockPos pos, Direction side, Hand hand);

    static IWrenchable forBlock(Block b) {
        return b instanceof IWrenchable ? (IWrenchable) b : null;
    }
}
