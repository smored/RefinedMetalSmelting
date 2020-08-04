package com.smore_d.rms.items.special_tools.swords;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import com.smore_d.rms.util.enums.Rarity;

import java.util.List;

public class FumeSword extends SwordItem {


    public FumeSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }


    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {


        tooltip.set(0, new StringTextComponent(Rarity.LEGENDARY.colour + "Fume Ultra-GreatSword"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7c" + "\u00A7o" + "Your journey began because you died"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "The sheer weight of this weapon makes it hard hitting but very slow"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "Creates a great" + "\u00A7e" + " shockwave " + "\u00A7f" + "on use"));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
