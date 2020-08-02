package com.smore_d.rms.items;

import com.smore_d.rms.RefinedMetalSmelting;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

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

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        tooltip.set(0, new StringTextComponent("\u00A76" + "test"));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    //    @Override
//    public Rarity getRarity(ItemStack stack) {
//        return Rarity.UNCOMMON;
//    }

}
