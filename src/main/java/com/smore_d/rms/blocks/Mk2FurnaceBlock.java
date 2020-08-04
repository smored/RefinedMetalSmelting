package com.smore_d.rms.blocks;

import com.smore_d.rms.init.ModBlocks;
import com.smore_d.rms.tile.entity.FurnaceTileBase;
import com.smore_d.rms.tile.entity.Mk2FurnaceEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class Mk2FurnaceBlock extends FurnaceBase {

    public static final String MK2_FURNACE = "mk2_furnace";


    public Mk2FurnaceBlock() {
        super(Properties.from(Blocks.IRON_BLOCK));
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.isCreative()) {
            FurnaceTileBase te = (FurnaceTileBase) world.getTileEntity(pos);
            if (te.hasCustomName()) {
                ItemStack itemstack = new ItemStack(ModBlocks.MK2_FURNACE_BLOCK.get());
                itemstack.setDisplayName(te.getName());
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemstack));
            } else {
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.MK2_FURNACE_BLOCK.get())));
            }
        }
        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 1;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new Mk2FurnaceEntity();
    }
}