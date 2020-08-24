package com.smore_d.rms.world.biomes;

import com.google.common.collect.ImmutableList;
import com.smore_d.rms.world.gen.trees.ModTrees;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;

public class ModBiomeFeatures extends DefaultBiomeFeatures {


    public static void addPineTrees(Biome biomeIn) {
        biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
                Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
                        Feature.field_236291_c_.withConfiguration(ModTrees.GOLD_PINE_TREE_CONFIG).withChance(0.0001F)),
                        Feature.field_236291_c_.withConfiguration(ModTrees.PINE_TREE_CONFIG))).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    }

}
