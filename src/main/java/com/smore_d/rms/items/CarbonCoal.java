package com.smore_d.rms.items;

import com.smore_d.rms.RefinedMetalSmelting;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class CarbonCoal extends Item {


    public CarbonCoal() {
        super(new Item.Properties().group(RefinedMetalSmelting.TAB));
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return 3200;
    }

//    @Override // messing around with tooltips
//    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
//
//        tooltip.add(new StringTextComponent("A rarer, denser version of coal that burns hotter and for twice as long"));
//
//        super.addInformation(stack, worldIn, tooltip, flagIn);
//    }

}
