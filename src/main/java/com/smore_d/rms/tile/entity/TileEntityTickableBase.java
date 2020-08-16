package com.smore_d.rms.tile.entity;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * Ticking tile entities should either extend this class, or implement ITickable themselves.
 * Note that the superclass, TileEntityBase, contains an implementation of tick() which
 * is used by default.
 */
public abstract class TileEntityTickableBase extends TileEntityBase implements ITickableTileEntity {

    public TileEntityTickableBase(TileEntityType type) {
        super(type);
    }

    @Override
    public void tick() {
        tickImpl();
    }
}