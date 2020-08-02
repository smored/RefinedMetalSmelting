package com.smore_d.rms.world.gen;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.init.ModBlocks;
import com.smore_d.rms.init.ModItems;
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

import java.rmi.registry.RegistryHandler;

@Mod.EventBusSubscriber(modid = RefinedMetalSmelting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModOreGen {

    // create a new filler block type
    public static OreFeatureConfig.FillerBlockType OBSIDIAN = OreFeatureConfig.FillerBlockType.create("OBSIDIAN", "obsidian", new BlockMatcher(Blocks.OBSIDIAN));
    public static OreFeatureConfig.FillerBlockType COAL_ORE = OreFeatureConfig.FillerBlockType.create("COAL_ORE", "coal_ore", new BlockMatcher(Blocks.COAL_ORE));
    public static OreFeatureConfig.FillerBlockType BEDROCK = OreFeatureConfig.FillerBlockType.create("BEDROCK", "bedrock", new BlockMatcher(Blocks.BEDROCK));


    @SubscribeEvent
    public static void generateOres(FMLLoadCompleteEvent event) {

        for (Biome biome : ForgeRegistries.BIOMES) {

            if ((biome.getCategory() != Biome.Category.NETHER) && (biome.getCategory() != Biome.Category.THEEND)) {
                genOre(biome, 250, 0, 5, 50, OBSIDIAN, ModBlocks.BLOOD_DIAMOND_ORE_BLOCK.get().getDefaultState(), 3);
                genOre(biome, 1000, 0, 5, 30, COAL_ORE, ModBlocks.CARBON_COAL_ORE_BLOCK.get().getDefaultState(), 3);
                genOre(biome, 2, 0, 5, 35, OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.KAOLINITE_ORE_BLOCK.get().getDefaultState(), 7);
                genOre(biome, 100, 5, 0, 6, BEDROCK, ModBlocks.BORON_NITRIDE_ORE_BLOCK.get().getDefaultState(), 3);
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
