package com.smore_d.rms.tile.entity;

import com.smore_d.rms.containers.Mk2FurnaceContainer;
import com.smore_d.rms.init.ModRecipeTypes;
import com.smore_d.rms.init.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntityType;

public class Mk2FurnaceEntity extends FurnaceTileBase {


    public Mk2FurnaceEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    void addRecipeType() {
    }

    public Mk2FurnaceEntity(){
        this(ModTileEntityTypes.MK2FURNACE.get());
    }

    @Override
    public String IgetName() {
        return "Cupellation Furnace";
    }

    @Override
    public Container IcreateMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new Mk2FurnaceContainer(i, world, pos, playerInventory, playerEntity, this.fields);
        //return null;
    }

}
