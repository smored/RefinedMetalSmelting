package com.smore_d.rms.recipes;

import com.google.gson.JsonObject;

import com.google.gson.JsonParseException;
import com.smore_d.rms.init.ModRecipeTypes;
import com.smore_d.rms.recipes.api.RMSRecipe;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Mk2SmeltingRecipe extends RMSRecipe {
    private final Ingredient input;
    private final ItemStack output;

    public Mk2SmeltingRecipe(ResourceLocation id, @Nonnull Ingredient input, @Nonnull ItemStack output) {
        super(id);

        this.input = input;
        this.output = output;
    }

    public Ingredient getInput() {
        return input;
    }

    public int getInputAmount() {
        return input.getMatchingStacks().length > 0 ? input.getMatchingStacks()[0].getCount() : 0;
    }

    public ItemStack getOutput() {
        return output;
    }

    public boolean matches(ItemStack stack) {
        return input.test(stack) && stack.getCount() >= getInputAmount();
    }

    @Override
    public void write(PacketBuffer buffer) {
        input.write(buffer);
        buffer.writeItemStack(output);
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.MK2_SMELTING.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return RMSRecipeType.MK2_SMELTING;
    }

    @Override
    public String getGroup() {
        return RMSRecipeType.MK2_SMELTING.toString();
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Blocks.TNT);
    }

    public static class Serializer<T extends Mk2SmeltingRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
        private final IFactory<T> factory;

        public Serializer(IFactory<T> factory) {
            this.factory = factory;
        }

        @Override
        public T read(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.deserialize(json.get("input"));
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            try {
                return factory.create(recipeId, input, result);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e.getMessage());
            }
        }

        @Nullable
        @Override
        public T read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient input = Ingredient.read(buffer);
            ItemStack out = buffer.readItemStack();
            return factory.create(recipeId, input, out);
        }

        @Override
        public void write(PacketBuffer buffer, T recipe) {
            recipe.write(buffer);
        }

        public interface IFactory<T extends Mk2SmeltingRecipe> {
            T create(ResourceLocation id, @Nonnull Ingredient input, @Nonnull ItemStack output);
        }
    }

}
