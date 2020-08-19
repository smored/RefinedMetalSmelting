package com.smore_d.rms.tile.entity;

import com.smore_d.rms.containers.handler.BaseItemStackHandler;
import com.smore_d.rms.containers.handler.ComparatorItemStackHandler;
import com.smore_d.rms.containers.handler.OutputItemHandler;
import com.smore_d.rms.init.ModTileEntityTypes;
import com.smore_d.rms.recipes.Mk2SmeltingRecipe;
import com.smore_d.rms.recipes.RMSRecipeType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class TileEntityMk2Furnace extends TileEntityTickableBase implements IRedstoneControlled, INamedContainerProvider, IComparatorSupport{

    private static final int INVENTORY_SIZE = 2;
    private static final int CRAFTING_TIME = 60 * 100;
    private static final double MAX_SPEED_UP = 2.5;

    private boolean searchForRecipe = true;
    private Mk2SmeltingRecipe currentRecipe;


    private final ItemStackHandler itemHandler = new BaseItemStackHandler(this, INVENTORY_SIZE) {
        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.isEmpty()
                    || RMSRecipeType.MK2_SMELTING.stream(world).anyMatch(r -> r.getInputItem().test(stack));
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            searchForRecipe = true;
        }
    };



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

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }

    @Override
    public int getComparatorValue() {
        return 0;
    }

    @Override
    public boolean shouldPreserveStateOnBreak() {
        return true;
    }

    @Override
    public ITextComponent getDisplayName() {
        return getDisplayNameInternal();
    }
}
