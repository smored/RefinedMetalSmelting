package com.smore_d.rms.util.enums;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public enum Rarity {

    //WGBPO

    COMMON(TextFormatting.WHITE),
    UNCOMMON(TextFormatting.GREEN),
    RARE(TextFormatting.BLUE),
    EPIC(TextFormatting.DARK_PURPLE),
    LEGENDARY(TextFormatting.GOLD);


    public final TextFormatting colour;

    Rarity(TextFormatting textFormatting) {
        this.colour = textFormatting;
    }

    public static List<Rarity> RarityList() {
        List<Rarity> rarities = new ArrayList<>();

        rarities.add(COMMON);
        rarities.add(UNCOMMON);
        rarities.add(RARE);
        rarities.add(EPIC);
        rarities.add(LEGENDARY);

        return rarities;
    }

}
