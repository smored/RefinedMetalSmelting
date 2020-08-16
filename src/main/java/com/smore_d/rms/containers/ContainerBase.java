package com.smore_d.rms.containers;

import com.smore_d.rms.network.*;
import com.smore_d.rms.tile.entity.IGUIButtonSensitive;
import com.smore_d.rms.tile.entity.TileEntityBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ContainerBase<T extends TileEntityBase> extends Container implements IGUIButtonSensitive {
    public final T te;
    private final List<SyncedField<?>> syncedFields = new ArrayList<>();
    private boolean firstTick = true;
    int playerSlotsStart;

    public ContainerBase(ContainerType type, int windowId, PlayerInventory invPlayer, PacketBuffer extraData) {
        this(type, windowId, invPlayer, getTilePos(extraData));
    }

    public ContainerBase(ContainerType type, int windowId, PlayerInventory invPlayer) {
        this(type, windowId, invPlayer, (BlockPos) null);
    }

    public ContainerBase(ContainerType type, int windowId, PlayerInventory invPlayer, BlockPos tilePos) {
        super(type, windowId);
        if (tilePos != null) {
            TileEntity te0 = invPlayer.player.world.getTileEntity(tilePos);
            if (te0 instanceof TileEntityBase) {
                //noinspection unchecked
                te = (T) te0;  // should be safe: T extends TileEntityBase, and we're doing an instanceof
                addSyncedFields(te);
            } else {
                te = null;
            }
        } else {
            te = null;
        }
    }

    static Hand getHand(PacketBuffer extraData) { return extraData.readBoolean() ? Hand.MAIN_HAND : Hand.OFF_HAND; }

    static BlockPos getTilePos(PacketBuffer extraData) {
        return extraData.readBlockPos();
    }

    void addSyncedField(SyncedField<?> field) {
        syncedFields.add(field);
        field.setLazy(false);
    }

    void addSyncedFields(Object annotatedObject) {
        List<SyncedField<?>> fields = NetworkUtils.getSyncedFields(annotatedObject, GuiSynced.class);
        for (SyncedField<?> field : fields)
            addSyncedField(field);
    }

    public void updateField(int index, Object value) {
        syncedFields.get(index).setValue(value);
        if (te != null) te.onGuiUpdate();
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return te.isGuiUseableByPlayer(player);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (int i = 0; i < syncedFields.size(); i++) {
            if (syncedFields.get(i).update() || firstTick) {
                sendToContainerListeners(new PacketUpdateGui(i, syncedFields.get(i)));
            }
        }
        firstTick = false;
    }

    void sendToContainerListeners(Object message) {
        /*
        for (IContainerListener listener : listeners) {
            if (listener instanceof ServerPlayerEntity) {
                NetworkHandler.sendToPlayer(message, (ServerPlayerEntity) listener);
            }
        }

         */
    }

    protected void addPlayerSlots(PlayerInventory inventoryPlayer, int yOffset) {
        addPlayerSlots(inventoryPlayer, 8, yOffset);
    }

    protected void addPlayerSlots(PlayerInventory inventoryPlayer, int xOffset, int yOffset) {
        playerSlotsStart = inventorySlots.size();

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < 3; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < 9; ++inventoryColumnIndex) {
                addSlot(new Slot(inventoryPlayer, inventoryColumnIndex + inventoryRowIndex * 9 + 9, xOffset + inventoryColumnIndex * 18, yOffset + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < 9; ++actionBarSlotIndex) {
            addSlot(new Slot(inventoryPlayer, actionBarSlotIndex, xOffset + actionBarSlotIndex * 18, yOffset + 58));
        }
    }


    @Override
    @Nonnull
    public ItemStack transferStackInSlot(PlayerEntity player, int slot) {
        Slot srcSlot = inventorySlots.get(slot);
        if (srcSlot == null || !srcSlot.getHasStack()) {
            return ItemStack.EMPTY;
        }
        ItemStack srcStack = srcSlot.getStack().copy();
        ItemStack copyOfSrcStack = srcStack.copy();

        if (slot < playerSlotsStart) {
            if (!mergeItemStack(srcStack, playerSlotsStart, playerSlotsStart + 36, false))
                return ItemStack.EMPTY;
        } else {
            if (!mergeItemStack(srcStack, 0, playerSlotsStart, false))
                return ItemStack.EMPTY;
        }

        srcSlot.putStack(srcStack);
        srcSlot.onSlotChange(srcStack, copyOfSrcStack);
        srcSlot.onTake(player, srcStack);

        return copyOfSrcStack;
    }

    // almost the same as the super method, but pays attention to slot itemstack limits
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean flag = false;
        int i = startIndex;
        if (reverseDirection) {
            i = endIndex - 1;
        }

        if (stack.isStackable()) {
            while(!stack.isEmpty()) {
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }

                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();
                if (!itemstack.isEmpty() && areItemsAndTagsEqual(stack, itemstack)) {
                    int j = itemstack.getCount() + stack.getCount();
                    // modified HERE
                    int maxSize = Math.min(slot.getItemStackLimit(itemstack), Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize()));
                    if (j <= maxSize) {
                        stack.setCount(0);
                        itemstack.setCount(j);
                        slot.onSlotChanged();
                        flag = true;
                    } else if (itemstack.getCount() < maxSize) {
                        stack.shrink(maxSize - itemstack.getCount());
                        itemstack.setCount(maxSize);
                        slot.onSlotChanged();
                        flag = true;
                    }
                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty()) {
            if (reverseDirection) {
                i = endIndex - 1;
            } else {
                i = startIndex;
            }

            while(true) {
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }

                Slot slot1 = this.inventorySlots.get(i);
                ItemStack itemstack1 = slot1.getStack();
                if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
                    // modified HERE
                    int limit = Math.min(slot1.getSlotStackLimit(), slot1.getItemStackLimit(stack));
                    if (stack.getCount() > limit) {
                        slot1.putStack(stack.split(limit));
                    } else {
                        slot1.putStack(stack.split(stack.getCount()));
                    }

                    slot1.onSlotChanged();
                    flag = true;
                    break;
                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        return flag;
    }

    @Nonnull
    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, PlayerEntity player) {
        Slot slot = slotId < 0 ? null : inventorySlots.get(slotId);
        if (slot instanceof IPhantomSlot) {
            return slotClickPhantom(slot, dragType, clickType, player);
        }
        return super.slotClick(slotId, dragType, clickType, player);

    }

    @Nonnull
    private ItemStack slotClickPhantom(Slot slot, int dragType, ClickType clickType, PlayerEntity player) {
        ItemStack stack = ItemStack.EMPTY;

        if (clickType == ClickType.CLONE && dragType == 2) {
            // middle-click: clear slot
            if (((IPhantomSlot) slot).canAdjust()) {
                slot.putStack(ItemStack.EMPTY);
            }
        } else if ((clickType == ClickType.PICKUP || clickType == ClickType.QUICK_MOVE) && (dragType == 0 || dragType == 1)) {
            // left or right-click...
            PlayerInventory playerInv = player.inventory;
            slot.onSlotChanged();
            ItemStack stackSlot = slot.getStack();
            ItemStack stackHeld = playerInv.getItemStack();

            stack = stackSlot.copy();
            if (stackSlot.isEmpty()) {
                if (!stackHeld.isEmpty() && slot.isItemValid(stackHeld)) {
                    fillPhantomSlot(slot, stackHeld, dragType);
                }
            } else if (stackHeld.isEmpty()) {
                adjustPhantomSlot(slot, clickType, dragType);
                slot.onTake(player, playerInv.getItemStack());
            } else if (slot.isItemValid(stackHeld)) {
                if (canStacksMerge(stackSlot, stackHeld)) {
                    adjustPhantomSlot(slot, clickType, dragType);
                } else {
                    fillPhantomSlot(slot, stackHeld, dragType);
                }
            }
        }
        return stack;
    }

    private boolean canStacksMerge(ItemStack stack1, ItemStack stack2) {
        return !(stack1.isEmpty() || stack2.isEmpty()) && stack1.isItemEqual(stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    private void adjustPhantomSlot(Slot slot, ClickType clickType, int dragType) {
        if (!((IPhantomSlot) slot).canAdjust()) {
            return;
        }
        ItemStack stackSlot = slot.getStack().copy();
        if (dragType == 1) {
            if (clickType == ClickType.QUICK_MOVE) {
                stackSlot.setCount(Math.min(stackSlot.getCount() * 2, slot.getSlotStackLimit())); // shift-r-click: double stack size
            } else {
                stackSlot.setCount(Math.min(stackSlot.getCount() + 1, slot.getSlotStackLimit())); // r-click: increase stack size
            }
        } else if (dragType == 0) {
            if (clickType == ClickType.QUICK_MOVE) {
                stackSlot.setCount(stackSlot.getCount() / 2); // shift-l-click: half stack size
            } else {
                stackSlot.shrink(1); // l-click: decrease stack size
            }
        }
        slot.putStack(stackSlot);
    }

    private void fillPhantomSlot(Slot slot, ItemStack stackHeld, int dragType) {
        if (!((IPhantomSlot) slot).canAdjust()) {
            return;
        }
        int stackSize = dragType == 0 ? stackHeld.getCount() : 1;
        if (stackSize > slot.getSlotStackLimit()) {
            stackSize = slot.getSlotStackLimit();
        }
        ItemStack phantomStack = stackHeld.copy();
        phantomStack.setCount(stackSize);

        slot.putStack(phantomStack);
    }

    @Override
    public void handleGUIButtonPress(String tag, boolean shiftHeld, PlayerEntity player) {
        if (te != null) {
            te.handleGUIButtonPress(tag, shiftHeld, player);
        }
    }
}