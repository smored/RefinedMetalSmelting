package com.smore_d.rms.items.special_tools.swords;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import com.smore_d.rms.util.enums.Rarity;

import java.util.List;

public class Swordsplosion extends SwordItem {


    public Swordsplosion(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }


    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {


        tooltip.set(0, new StringTextComponent(Rarity.LEGENDARY.colour + "SWORDSPLOSION!!!"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7c" + "Because Mister Torgue Said So"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "+500% Weapon Damage"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "Deals bonus" + "\u00A7e" + " explosive " + "\u00A7f" + "damage"));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
