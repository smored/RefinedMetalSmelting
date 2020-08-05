package com.smore_d.rms.items;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import org.jline.style.StyleColor;

import java.util.List;

public class Swordsplosion extends SwordItem {


    public Swordsplosion(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }


    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {


        tooltip.set(0, new StringTextComponent("\u00A7d" + "SWORDSPLOSION!!!"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7c" + "Because Mister Torgue Said So"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "+500% Weapon Damage"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "Deals bonus" + "\u00A7e" + " explosive " + "\u00A7f" + "damage"));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }



//    @Override
//    public Rarity getRarity(ItemStack stack) {
//        return Rarity.EPIC;
//    }


}