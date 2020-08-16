package com.smore_d.rms.network.packets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields marked with this annotation in a TileEntity class will be automatically synced to any players within 64 blocks
 * of this TileEntity.
 * <p>
 * Supported field types are int, float, double, boolean, String, int[], float[], double[], boolean[] and String[], as
 * well as ItemStack, FluidTank and ItemStackHandler and their corresponding arrays.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DescSynced {

}
