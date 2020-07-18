package com.smore_d.rms.items;

import com.smore_d.rms.RefinedMetalSmelting;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class Smore extends Item {

    public Smore() {
        super(new Item.Properties()
                .group(RefinedMetalSmelting.TAB)
                .food(new Food.Builder()
                        .hunger(4)
                        .saturation(4f)
                        .effect(new EffectInstance(Effects.RESISTANCE, 300, 4), 1)
                        .setAlwaysEdible()
                        .build())
        );
    }

}
