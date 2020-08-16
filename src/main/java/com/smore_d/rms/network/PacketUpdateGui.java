package com.smore_d.rms.network;

import com.smore_d.rms.containers.ContainerBase;
import com.smore_d.rms.network.SyncedField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Received on: CLIENT
 *
 * The primary mechanism for sync'ing TE fields to an open GUI.  TE fields annotated with @GuiSynced will be synced
 * in this packet, via {@link ContainerPneumaticBase#detectAndSendChanges()}.
 */
public class PacketUpdateGui {
    private int syncId;
    private Object value;
    private byte type;

    public PacketUpdateGui() {
    }

    public PacketUpdateGui(int syncId, SyncedField<?> syncField) {
        this.syncId = syncId;
        value = syncField.getValue();
        type = SyncedField.getType(syncField);
    }

    public PacketUpdateGui(PacketBuffer buf) {
        syncId = buf.readVarInt();
        type = buf.readByte();
        value = SyncedField.fromBytes(buf, type);
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeVarInt(syncId);
        buf.writeByte(type);
        SyncedField.toBytes(buf, value, type);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (Minecraft.getInstance().currentScreen instanceof ContainerScreen) {
                Container container = ((ContainerScreen) Minecraft.getInstance().currentScreen).getContainer();
                if (container instanceof ContainerBase) {
                    ((ContainerBase) container).updateField(syncId, value);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
