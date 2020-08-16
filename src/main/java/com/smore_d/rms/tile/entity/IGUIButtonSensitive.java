package com.smore_d.rms.tile.entity;

import net.minecraft.entity.player.PlayerEntity;

public interface IGUIButtonSensitive {
    String REDSTONE_TAG = "redstone";

    void handleGUIButtonPress(String tag, boolean shiftHeld, PlayerEntity player);
}
