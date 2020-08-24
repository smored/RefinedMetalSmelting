package com.smore_d.rms.world.biomes;

import com.smore_d.rms.init.ModEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public final class PinesBiome extends Biome {

    public PinesBiome(Builder biomeBuilder) {
        super(biomeBuilder);
        DefaultBiomeFeatures.addOres(this);
        ModBiomeFeatures.addPineTrees(this);
    }

    @Override
    public int getSkyColor() {
        return 0x292929;
    }

    @Override
    public int getFoliageColor() {
        return 0x5c5c5c;
    }

    @Override
    public int getGrassColor(double posX, double posZ) {
        return 0x292929;
    }

}

