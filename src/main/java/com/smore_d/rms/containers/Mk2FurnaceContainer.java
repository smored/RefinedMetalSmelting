package com.smore_d.rms.containers;

import com.smore_d.rms.init.ModBlocks;
import com.smore_d.rms.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Mk2FurnaceContainer extends FurnaceContainerBase {

    public Mk2FurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
        super(ModContainers.MK2_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player);
    }

    public Mk2FurnaceContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IIntArray fields) {
        super(ModContainers.MK2_FURNACE_CONTAINER.get(), windowId, world, pos, playerInventory, player, fields);
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), playerEntity, ModBlocks.MK2_FURNACE_BLOCK.get());
    }
}
