package com.smore_d.rms.blocks;


import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class PineLog extends DirectionalBlock {

    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;

    public PineLog() {
        super(Block.Properties.create(Material.WOOD)
        .hardnessAndResistance(2.0F, 3.0F)
        .sound(SoundType.WOOD)
        .harvestTool(ToolType.AXE)
        );
    }

//    @Nullable
//    @Override
//    public BlockState getStateForPlacement(BlockItemUseContext context) {
//        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
//    }
//
//    @Override
//    public BlockState rotate(BlockState state, Rotation rot) {
//        return state.with(FACING, rot.rotate(state.get(FACING)));
//    }
//
//    @Override
//    public BlockState mirror(BlockState state, Mirror mirrorIn) {
//        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
//    }
//
//    @Override
//    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
//        builder.add(FACING);
//    }

//    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
//        return facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL ? stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), Boolean.valueOf(this.canConnect(facingState, facingState.isSolidSide(worldIn, facingPos, facing.getOpposite()), facing.getOpposite()))) : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
//    }

}
