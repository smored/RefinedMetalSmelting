package com.smore_d.rms.network.packets;

import com.smore_d.rms.recipes.RMSRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketClearRecipeCache {
    public PacketClearRecipeCache() {
    }

    public void toBytes(PacketBuffer buf) {
    }

    public PacketClearRecipeCache(PacketBuffer buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
        ctx.get().enqueueWork(RMSRecipeType::clearCachedRecipes);
    }
}