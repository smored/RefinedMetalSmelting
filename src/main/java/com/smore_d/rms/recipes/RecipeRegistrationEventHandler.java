package com.smore_d.rms.recipes;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.recipes.api.Ingredient.FluidIngredient;
import com.smore_d.rms.recipes.api.Ingredient.StackedIngredient;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = RefinedMetalSmelting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipeRegistrationEventHandler {
    @SubscribeEvent
    public static void onRegister(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        // register our custom recipe and ingredient types

        RMSRecipeType.registerRecipeTypes(event.getRegistry());

        CraftingHelper.register(StackedIngredient.Serializer.ID, StackedIngredient.Serializer.INSTANCE);
        CraftingHelper.register(FluidIngredient.Serializer.ID, FluidIngredient.Serializer.INSTANCE);
    }
}
