package com.smore_d.rms.recipes.api;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public interface IModRecipeSerializer<T extends IModRecipe> {
    /**
     * Read a recipe from JSON.  Called server-side when recipes are read from data packs.
     *
     * @param recipeId the recipe ID, as returned by {@link IModRecipe#getId()}
     * @param json the JSON object
     * @return a new machine recipe, deserialized from JSON
     */
    T read(ResourceLocation recipeId, JsonObject json);

    /**
     * Read a recipe from a packet buffer. Called client-side when recipes are sync'd from server to client.
     *
     * @param recipeId the recipe ID, as returned by {@link IModRecipe#getId()}
     * @param buffer a packet buffer
     * @return a new machine recipe, deserialized from the buffer
     */
    @javax.annotation.Nullable
    T read(ResourceLocation recipeId, PacketBuffer buffer);

    /**
     * Write a recipe to a packet buffer. Called server-side when recipes are sync'd from server to client.
     *
     * @param buffer a packet buffer
     * @param recipe the recipe to serialize
     */
    void write(PacketBuffer buffer, T recipe);
}