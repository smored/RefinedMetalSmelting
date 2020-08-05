package com.smore_d.rms.network.packets;

import com.smore_d.rms.network.ILargePayload;
import com.smore_d.rms.util.Log;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Received on: SERVER
 * First part of a following multi-part message from the client
 */
public class PacketMultiHeader {
    private int length;
    private String className;
    private static final Map<UUID, PayloadBuffer> payloadBuffers = new HashMap<>();

    public PacketMultiHeader() {
        // empty
    }

    public PacketMultiHeader(int length, String className) {
        this.length = length;
        this.className = className;
    }

    public PacketMultiHeader(PacketBuffer buffer) {
        length = buffer.readInt();
        className = buffer.readString(32767);
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(length);
        buf.writeString(className);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            try {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                if (cl == null) cl = PacketMultiHeader.class.getClassLoader(); // fallback
                Class<?> clazz = cl.loadClass(className);
                UUID id = ctx.get().getSender() == null ? Minecraft.getInstance().player.getUniqueID() : ctx.get().getSender().getUniqueID();
                payloadBuffers.put(id, new PayloadBuffer(clazz.asSubclass(ILargePayload.class), length));
            } catch (ClassNotFoundException|ClassCastException e) {
                e.printStackTrace();
            }
        });
        ctx.get().setPacketHandled(true);
    }

    static void receivePayload(PlayerEntity player, byte[] payload) {
        UUID id = player == null ? Minecraft.getInstance().player.getUniqueID() : player.getUniqueID();
        PayloadBuffer buffer = payloadBuffers.get(id);
        if (buffer != null) {
            System.arraycopy(payload, 0, buffer.payload, buffer.offset, payload.length);
            buffer.offset += ILargePayload.MAX_PAYLOAD_SIZE;
            if (buffer.offset > buffer.payload.length) {
                // we have the complete message
                try {
                    Constructor<? extends ILargePayload> ctor = buffer.clazz.getConstructor(PacketBuffer.class);
                    ILargePayload packet = ctor.newInstance(new PacketBuffer(Unpooled.wrappedBuffer(buffer.payload)));
                    packet.handleLargePayload(player);
                    payloadBuffers.remove(id);
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Log.error("Received unexpected multi-message payload from player " + player.getName() + " - " + player.getUniqueID());
        }
    }

    private static class PayloadBuffer {
        final Class<? extends ILargePayload> clazz;
        final byte[] payload;
        int offset;

        PayloadBuffer(Class<? extends ILargePayload> clazz, int length) {
            this.clazz = clazz;
            this.payload = new byte[length];
        }
    }
}