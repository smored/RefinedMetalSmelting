package com.smore_d.rms.items.special_tools.swords;

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

public class GlowstoneSword extends SwordItem {


    public GlowstoneSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }


    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {


        tooltip.set(0, new StringTextComponent("\u00A79" + "Glowstone Sword"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "Glows in the dark"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "Deals bonus" + "\u00A7b" + " holy " + "\u00A7f" + "damage"));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }


}
