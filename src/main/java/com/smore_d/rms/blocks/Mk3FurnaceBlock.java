package com.smore_d.rms.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class Mk3FurnaceBlock extends Block {


    public Mk3FurnaceBlock() {
        super(Block.Properties.create(Material.EARTH)
                .hardnessAndResistance(3.5f, 17.5f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
        );
    }

}
