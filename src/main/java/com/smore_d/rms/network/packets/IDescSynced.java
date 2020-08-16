package com.smore_d.rms.network.packets;

import com.smore_d.rms.network.SyncedField;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface IDescSynced {
    List<SyncedField<?>> getDescriptionFields();

    void writeToPacket(CompoundNBT tag);

    void readFromPacket(CompoundNBT tag);

    BlockPos getPosition();

    void onDescUpdate();

    boolean shouldSyncField(int idx);
}
