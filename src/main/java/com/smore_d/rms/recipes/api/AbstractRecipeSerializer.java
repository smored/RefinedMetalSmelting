package com.smore_d.rms.recipes.api;

import net.minecraft.network.PacketBuffer;

public abstract class AbstractRecipeSerializer<T extends IModRecipe> implements IModRecipeSerializer<T> {

    @Override
    public void write(PacketBuffer buffer, T recipe) {
        buffer.writeResourceLocation(recipe.getRecipeType());
        buffer.writeResourceLocation(recipe.getId());
    }
}
