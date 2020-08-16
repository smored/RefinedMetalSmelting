package com.smore_d.rms.containers;

public interface IPhantomSlot {
    /*
     * Phantom Slots don't "use" items, they are used for filters and various
     * other logic slots.
     */
    boolean canAdjust();
}