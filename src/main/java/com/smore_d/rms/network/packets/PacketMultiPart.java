package com.smore_d.rms.network.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Received on: SERVER
 * Part of a multipart mesage from client (whole message too big to send at once)
 */
public class PacketMultiPart {
    private byte[] payload;

    public PacketMultiPart() {
    }

    public PacketMultiPart(byte[] payload) {
        this.payload = payload;
    }

    public PacketMultiPart(PacketBuffer buf) {
        payload = new byte[buf.readInt()];
        buf.readBytes(payload);
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(payload.length);
        buf.writeBytes(payload);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> PacketMultiHeader.receivePayload(ctx.get().getSender(), payload));
        ctx.get().setPacketHandled(true);
    }
}