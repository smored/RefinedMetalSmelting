package com.smore_d.rms.world.gen;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = RefinedMetalSmelting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModOreGen {

    // create a new filler block type
    public static OreFeatureConfig.FillerBlockType OBSIDIAN = OreFeatureConfig.FillerBlockType.create("OBSIDIAN", "obsidian", new BlockMatcher(Blocks.OBSIDIAN));


    @SubscribeEvent
    public static void generateOres(FMLLoadCompleteEvent event) {

        for (Biome biome : ForgeRegistries.BIOMES) {

            if ((biome.getCategory() != Biome.Category.NETHER) && (biome.getCategory() != Biome.Category.THEEND)) {
                genOre(biome, 250, 1, 5, 50, OBSIDIAN, RegistryHandler.BLOOD_DIAMOND_ORE_BLOCK.get().getDefaultState(), 3);
            }

        }
    }


    private static void genOre(Biome biome, int count, int bottomOffset, int topOffset, int max, OreFeatureConfig.FillerBlockType filler, BlockState defaultBlockstate, int size) {
        CountRangeConfig range = new CountRangeConfig(count, bottomOffset, topOffset, max);
        OreFeatureConfig feature = new OreFeatureConfig(filler, defaultBlockstate, size);
        ConfiguredPlacement config = Placement.COUNT_RANGE.configure(range);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(feature).withPlacement(config));
    }
}
