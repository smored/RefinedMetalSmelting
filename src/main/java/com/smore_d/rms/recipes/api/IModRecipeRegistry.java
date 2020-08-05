package com.smore_d.rms.recipes.api;

import com.smore_d.rms.recipes.Mk2SmeltingRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IModRecipeRegistry {

    Mk2SmeltingRecipe mk2SmeltingRecipe(ResourceLocation id, @Nonnull Ingredient input, @Nonnull ItemStack output);
}
