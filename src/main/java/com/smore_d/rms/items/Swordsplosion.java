package com.smore_d.rms.items;

import com.smore_d.rms.util.KeyboardHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class Swordsplosion extends SwordItem {


    public Swordsplosion(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }



    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        if (KeyboardHelper.isHoldingShift()) {
            tooltip.add(new StringTextComponent("\u00A7c" + "*Because Mister Torgue Said So"));
            tooltip.add(new StringTextComponent("\u00A7f" + "*+500% Weapon Damage"));
            tooltip.add(new StringTextComponent("\u00A7f" + "*Deals bonus" + "\u00A7e" + " explosive " + "\u00A7f" + "damage"));
        } else {
            tooltip.add(new StringTextComponent("Hold" + "\u00A71" + " SHIFT " + "\u00A7f" +"for more information"));
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.EPIC;
    }
}
