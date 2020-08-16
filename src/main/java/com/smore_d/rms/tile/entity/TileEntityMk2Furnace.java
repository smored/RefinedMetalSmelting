package com.smore_d.rms.tile.entity;

import com.smore_d.rms.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class TileEntityMk2Furnace extends TileEntityTickableBase implements IRedstoneControlled, INamedContainerProvider {
    public TileEntityMk2Furnace(TileEntityType type) {
        super(type);
    }

    public TileEntityMk2Furnace() {
        super(ModTileEntityTypes.MK2FURNACE.get());
    }

    @Override
    public IItemHandler getPrimaryInventory() {
        return null;
    }

    @Override
    public int getRedstoneMode() {
        return 0;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }
}
