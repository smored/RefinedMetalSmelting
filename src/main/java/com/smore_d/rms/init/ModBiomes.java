package com.smore_d.rms.init;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.world.biomes.PinesBiome;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, RefinedMetalSmelting.MOD_ID);


    public static final RegistryObject<Biome> ASHEN_PINES_BIOME = BIOMES.register("ashen_pines_biome", () ->
            new PinesBiome(new Biome.Builder()
                    .precipitation(Biome.RainType.NONE)
                    .scale(1.0f)
                    .temperature(0.5f)
                    .func_235097_a_(new BiomeAmbience.Builder()
                            .setParticle(new ParticleEffectAmbience(ParticleTypes.ASH, 0.1F))
                            .setFogColor(0x141414)
                            .setWaterColor(0x141414)
                            .setWaterFogColor(0x424242)
                            .build())
                    .surfaceBuilder(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(ModBlocks.ASHEN_GRASS_BLOCK.get().getDefaultState(), ModBlocks.ASHEN_DIRT_BLOCK.get().getDefaultState(), Blocks.GRAVEL.getDefaultState()))
                    .category(Biome.Category.FOREST)
                    .downfall(0)
                    .depth(0.01f)
                    .parent(null)));



    public static void registerBiomes() {
        registerBiome(ASHEN_PINES_BIOME.get(), BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.OVERWORLD);
    }

    private static void registerBiome(Biome biome, BiomeDictionary.Type... types) {
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
    }
}
