package com.smore_d.rms.init;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.tile.entity.furnace.Mk2FurnaceEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, RefinedMetalSmelting.MOD_ID);


    public static final RegistryObject<TileEntityType<Mk2FurnaceEntity>> MK2FURNACE = TILE_ENTITY_TYPES.register("mk2_furnace", () -> TileEntityType.Builder.create(Mk2FurnaceEntity::new, ModBlocks.MK2_FURNACE_BLOCK.get()).build(null));
}
