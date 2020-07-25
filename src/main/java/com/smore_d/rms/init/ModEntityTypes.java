package com.smore_d.rms.init;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.entities.IronPigEntity;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, RefinedMetalSmelting.MOD_ID);

    public static final RegistryObject<EntityType<IronPigEntity>> IRON_PIG = ENTITY_TYPES.register("iron_pig",
            () -> EntityType.Builder.create(IronPigEntity::new, EntityClassification.CREATURE).size(1.0f, 1.0f).build(new ResourceLocation(RefinedMetalSmelting.MOD_ID, "iron_pig").toString()));
}
