package com.smore_d.rms.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public abstract class BlockFurnaceBase extends BlockRMS{

    protected BlockFurnaceBase() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(3.5f, 17.5f)
                .sound(SoundType.METAL)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
                .setRequiresTool()
        );
    }
}
