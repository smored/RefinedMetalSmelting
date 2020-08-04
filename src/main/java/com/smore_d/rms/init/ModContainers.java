package com.smore_d.rms.init;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.blocks.Mk2FurnaceBlock;
import com.smore_d.rms.containers.Mk2FurnaceContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, RefinedMetalSmelting.MOD_ID);


    public static final RegistryObject<ContainerType<Mk2FurnaceContainer>> MK2_FURNACE_CONTAINER = CONTAINERS.register(Mk2FurnaceBlock.MK2_FURNACE, () -> IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        return new Mk2FurnaceContainer(windowId, Minecraft.getInstance().world, pos, inv, Minecraft.getInstance().player);
    }));
}
