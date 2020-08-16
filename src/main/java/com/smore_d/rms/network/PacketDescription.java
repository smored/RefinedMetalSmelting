package com.smore_d.rms.network;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.network.SyncedField;
import com.smore_d.rms.network.packets.IDescSynced;
import com.smore_d.rms.network.packets.LocationIntPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class PacketDescription extends LocationIntPacket {
    private boolean fullSync;
    private final List<IndexedField> fields = new ArrayList<>();
    private CompoundNBT extraData;

    public PacketDescription() {
    }

    public PacketDescription(IDescSynced te, boolean fullSync) {
        super(te.getPosition());

        this.fullSync = fullSync;
        List<SyncedField<?>> descFields = te.getDescriptionFields();
        for (int i = 0; i < descFields.size(); i++) {
            if (fullSync || te.shouldSyncField(i)) {
                fields.add(new IndexedField(i, SyncedField.getType(descFields.get(i)), descFields.get(i).getValue()));
            }
        }
        extraData = new CompoundNBT();
        te.writeToPacket(extraData);
    }

    public PacketDescription(PacketBuffer buf) {
        super(buf);

        fullSync = buf.readBoolean();
        int fieldCount = buf.readVarInt();
        for (int i = 0; i < fieldCount; i++) {
            int idx = fullSync ? i : buf.readVarInt();
            byte type = buf.readByte();
            fields.add(new IndexedField(idx, type, SyncedField.fromBytes(buf, type)));
        }
        extraData = buf.readCompoundTag();
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        super.toBytes(buf);

        buf.writeBoolean(fullSync);
        buf.writeVarInt(fields.size());
        for (IndexedField indexedField : fields) {
            if (!fullSync) buf.writeVarInt(indexedField.idx);
            buf.writeByte(indexedField.type);
            SyncedField.toBytes(buf, indexedField.value, indexedField.type);
        }
        buf.writeCompoundTag(extraData);
    }

    public void process(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(this::process);
        ctx.get().setPacketHandled(true);
    }

    public void process() {
        if (Minecraft.getInstance().world.isAreaLoaded(pos, 0)) {
            TileEntity syncable = Minecraft.getInstance().world.getTileEntity(pos);
            if (syncable instanceof IDescSynced) {
                IDescSynced descSynced = (IDescSynced) syncable;
                List<SyncedField<?>> descFields = descSynced.getDescriptionFields();
                if (descFields != null) {
                    for (IndexedField indexedField : fields) {
                        if (indexedField.idx < descFields.size()) {
                            descFields.get(indexedField.idx).setValue(indexedField.value);
                        }
                    }
                }
                descSynced.readFromPacket(extraData);
                descSynced.onDescUpdate();
            }
        }
    }

    /********************
     * These two methods are only used for initial chunk sending (getUpdateTag() and handleUpdateTag())
     */

    public CompoundNBT writeNBT(CompoundNBT compound) {
        CompoundNBT subTag = new CompoundNBT();

        subTag.putInt("Length", fields.size());
        PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
        ListNBT list = new ListNBT();
        for (IndexedField field : fields) {
            CompoundNBT element = new CompoundNBT();
            element.putByte("Type", field.type);
            buf.clear();
            SyncedField.toBytes(buf, field.value, field.type);
            element.putByteArray("Value", Arrays.copyOf(buf.array(), buf.writerIndex()));
            list.add(list.size(), element);
        }
        buf.release();
        subTag.put("Data", list);
        subTag.put("Extra", extraData);
        compound.put(RefinedMetalSmelting.MOD_ID, subTag);

        return compound;
    }

    public PacketDescription(CompoundNBT compound) {
        super(new BlockPos(compound.getInt("x"), compound.getInt("y"), compound.getInt("z")));

        CompoundNBT subTag = compound.getCompound(RefinedMetalSmelting.MOD_ID);
        int fieldCount = subTag.getInt("Length");
        ListNBT list = subTag.getList("Data", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < fieldCount; i++) {
            CompoundNBT element = list.getCompound(i);
            byte type = element.getByte("Type");
            byte[] b = element.getByteArray("Value");
            fields.add(new IndexedField(i, type, SyncedField.fromBytes(new PacketBuffer(Unpooled.wrappedBuffer(b)), type)));
        }
        extraData = subTag.getCompound("Extra");
    }

    public boolean hasData() {
        return !fields.isEmpty() || !extraData.isEmpty();
    }

    private static class IndexedField {
        final int idx;
        final byte type;
        final Object value;

        IndexedField(int idx, byte type, Object value) {
            this.idx = idx;
            this.type = type;
            this.value = value;
        }
    }
}
