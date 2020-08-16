package com.smore_d.rms.items.furnaceContainers;

import com.smore_d.rms.RefinedMetalSmelting;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RMSFurnaceContainer extends Item {

    public RMSFurnaceContainer() {
        super(new Item.Properties().group(RefinedMetalSmelting.TAB).maxStackSize(8));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Used in the smelting process of refined metal ingots."));
    }
}
