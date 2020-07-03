package com.smore_d.rms.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class PorousStoneBlock extends Block{


    public PorousStoneBlock() {
        super(Block.Properties.create(Material.EARTH)
                .hardnessAndResistance(1.5f, 30.0f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
