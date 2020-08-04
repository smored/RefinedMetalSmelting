package com.smore_d.rms.tile.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.INameable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public abstract class TileEntityInv extends TileEntity implements ITileInv, ISidedInventory, INamedContainerProvider, INameable {

    public NonNullList<ItemStack> inventory;
    protected ITextComponent name;

    public TileEntityInv(TileEntityType<?> tileEntityTypeIn, int sizeInv) {
        super(tileEntityTypeIn);
        inventory = NonNullList.withSize(sizeInv, ItemStack.EMPTY);
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return this.IgetSlotsForFace(side);
    }


    @Override
    public boolean IisItemValidForSlot(int index, ItemStack stack) {
        return this.IisItemValidForSlot(index, stack);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return IcanExtractItem(index, stack, direction);
    }

    @Override
    public int getSizeInventory() {
        return 64;
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.inventory) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.inventory.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public ITextComponent getName() {
        return (this.name != null ? this.name : new TranslationTextComponent(IgetName()));
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void openInventory(PlayerEntity player) { }

    @Override
    public void closeInventory(PlayerEntity player) { }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public boolean hasCustomName() {
        return this.name != null;
    }

    @Nullable
    @Override
    public ITextComponent getCustomName() {
        return this.name;
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return IcreateMenu(i, playerInventory, playerEntity);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);
        if (this.name != null) {
            compound.putString("BlockName", ITextComponent.Serializer.toJson(this.name));
        }
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);
        if (compound.contains("BlockName", 8)) {
            this.name = ITextComponent.Serializer.func_240643_a_(compound.getString("BlockName"));
        }
    }

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

}
