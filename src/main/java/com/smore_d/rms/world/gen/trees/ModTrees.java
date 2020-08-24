package com.smore_d.rms.world.gen.trees;

import com.smore_d.rms.init.ModBlocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.MegaPineFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class ModTrees {

    public static final BaseTreeFeatureConfig GOLD_PINE_TREE_CONFIG =
            (new BaseTreeFeatureConfig.Builder
                    (new SimpleBlockStateProvider(ModBlocks.GOLD_PINE_LOG.get().getDefaultState()),
                            new SimpleBlockStateProvider(ModBlocks.GOLD_PINE_LEAVES.get().getDefaultState()),
                            new MegaPineFoliagePlacer(3,1,1,1,5,5),
                            new GiantTrunkPlacer(10,5,10),
                            new TwoLayerFeature(0, 0, 0)).build());


    public static final BaseTreeFeatureConfig PINE_TREE_CONFIG =
            (new BaseTreeFeatureConfig.Builder
                    (new SimpleBlockStateProvider(ModBlocks.PINE_LOG.get().getDefaultState()),
                            new SimpleBlockStateProvider(ModBlocks.PINE_LEAVES.get().getDefaultState()),
                            new BlobFoliagePlacer(2, 1, 0, 2, 2),
                            new StraightTrunkPlacer(5, 1, 2),
                            new TwoLayerFeature(0, 0, 0)).build());

}
