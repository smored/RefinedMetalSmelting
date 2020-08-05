package com.smore_d.rms.recipes;

import com.smore_d.rms.network.NetworkHandler;
import com.smore_d.rms.network.packets.PacketClearRecipeCache;
import com.smore_d.rms.recipes.api.RMSRecipe;
import com.smore_d.rms.recipes.api.RMSRecipeTypes;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static com.smore_d.rms.util.RMSUtils.RL;

public class RMSRecipeType<T extends RMSRecipe> implements IRecipeType<T> {
    private static final List<RMSRecipeType<? extends RMSRecipe>> types = new ArrayList<>();

    private final Map<ResourceLocation, T> cachedRecipes = new HashMap<>();
    private final ResourceLocation registryName;
    private static CacheReloadListener cacheReloadListener;

    public static final RMSRecipeType<Mk2SmeltingRecipe> MK2_SMELTING = registerType(RMSRecipeTypes.MK2_SMELTING);

    private static <T extends RMSRecipe> RMSRecipeType<T> registerType(String name) {
        RMSRecipeType<T> type = new RMSRecipeType<>(name);
        types.add(type);
        return type;
    }

    // TODO: use a Forge registry if/when there is one for recipe types
    static void registerRecipeTypes(IForgeRegistry<IRecipeSerializer<?>> registry) {
        types.forEach(type -> Registry.register(Registry.RECIPE_TYPE, type.registryName, type));
    }

    private RMSRecipeType(String name) {
        this.registryName = RL(name);
    }

    public static void clearCachedRecipes() {
        types.forEach(type -> type.cachedRecipes.clear());
    }

    public static class CacheReloadListener implements IFutureReloadListener {
        @Override
        public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
            return CompletableFuture.runAsync(() -> {
                clearCachedRecipes();
                if (ServerLifecycleHooks.getCurrentServer() != null) {
                    NetworkHandler.sendToAll(new PacketClearRecipeCache());
                }
            }, gameExecutor).thenCompose(stage::markCompleteAwaitingOthers);
        }
    }
}
