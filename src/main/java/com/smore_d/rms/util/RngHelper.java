package com.smore_d.rms.util;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.util.enums.Prefixes;
import com.smore_d.rms.util.enums.Rarity;

import java.util.Random;

public class RngHelper {

    //rarity picker
    //specific tool picker
    //stat picker

    private int legendaryChance = 1;
    private int epicChance = 6;
    private int rareChance = 14;
    private int uncommonChance = 24;
    private int commonChance = 55;

    public RngHelper() {
    }

    public RngHelper(int legendaryChance, int epicChance, int rareChance, int uncommonChance, int commonChance) {
        this.legendaryChance = legendaryChance;
        this.epicChance = epicChance;
        this.rareChance = rareChance;
        this.uncommonChance = uncommonChance;
        this.commonChance = commonChance;
    }

    public Rarity rarityPicker() {
        int num = RefinedMetalSmelting.RANDOM.nextInt(100) + 1;

        if (num <= commonChance) {
            return Rarity.COMMON;
        } else if (num <= (commonChance + uncommonChance)) {
            return Rarity.UNCOMMON;
        } else if (num <= (commonChance + uncommonChance + rareChance)) {
            return Rarity.RARE;
        } else if (num <= (commonChance + uncommonChance + rareChance + epicChance)) {
            return Rarity.EPIC;
        } else if (num <= (commonChance + uncommonChance + rareChance + epicChance + legendaryChance)) {
            return Rarity.LEGENDARY;
        }

        return Rarity.COMMON;
    }

    public Prefixes prefixSelector() {
        int num = RefinedMetalSmelting.RANDOM.nextInt(1);

        switch (num) {
            case 0:
                return Prefixes.HEAVY;
            case 1:
                return Prefixes.LIGHT;
            default:
                return Prefixes.LIGHT;
        }
    }

}
