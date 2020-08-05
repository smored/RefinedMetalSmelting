package com.smore_d.rms.init;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.recipes.Mk2SmeltingRecipe;
import com.smore_d.rms.recipes.api.RMSRecipeTypes;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeTypes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RefinedMetalSmelting.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<Mk2SmeltingRecipe>> MK2_SMELTING = RECIPES.register(RMSRecipeTypes.MK2_SMELTING, () -> new Mk2SmeltingRecipe.Serializer<>(Mk2SmeltingRecipe::new));
}
