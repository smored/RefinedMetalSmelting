package com.smore_d.rms.blocks;


import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class GoldPineLog extends DirectionalBlock {

    public GoldPineLog() {
        super(Properties.create(Material.WOOD)
                .hardnessAndResistance(20.0F, 30.0F)
                .sound(SoundType.WOOD)
                .harvestLevel(6) // not a final value, just for testing
                .harvestTool(ToolType.AXE)
                .setRequiresTool()
                .setLightLevel(value -> 15)
        );
    }

}
