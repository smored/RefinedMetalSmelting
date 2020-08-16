package com.smore_d.rms.network;

import com.smore_d.rms.network.packets.FilteredSynced;
import com.smore_d.rms.network.packets.LazySynced;
import com.smore_d.rms.util.Log;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {
    /**
     * Get a list of all the synced fields for a syncable object
     *
     * @param syncable the object whose fields we are extracting
     * @param searchedAnnotation the annotation type to search for
     * @return a list of all the fields annotated with the given type
     */
    public static List<SyncedField<?>> getSyncedFields(Object syncable, Class<? extends Annotation> searchedAnnotation) {
        List<SyncedField<?>> syncedFields = new ArrayList<>();
        Class<?> examinedClass = syncable.getClass();
        while (examinedClass != null) {
            for (Field field : examinedClass.getDeclaredFields()) {
                if (field.getAnnotation(searchedAnnotation) != null) {
                    syncedFields.addAll(getSyncedFieldsForField(field, syncable, searchedAnnotation));
                }
            }
            examinedClass = examinedClass.getSuperclass();
        }
        // record how the field was found - later on, SyncedField methods can use this information
        // e.g. GuiSynced data can be sent much more often than DescSynced data
        syncedFields.forEach(field -> field.setAnnotation(searchedAnnotation));
        return syncedFields;
    }

    private static List<SyncedField<?>> getSyncedFieldsForField(Field field, Object te, Class<? extends Annotation> searchedAnnotation) {
        boolean isLazy = field.getAnnotation(LazySynced.class) != null;
        List<SyncedField<?>> syncedFields = new ArrayList<>();
        SyncedField<?> syncedField = getSyncedFieldForField(field, te);
        if (syncedField != null) {
            syncedFields.add(syncedField.setLazy(isLazy));
        } else {
            Object o;
            try {
                int filteredIndex = field.getAnnotation(FilteredSynced.class) != null ? field.getAnnotation(FilteredSynced.class).index() : -1;
                field.setAccessible(true);
                o = field.get(te);
                if (o instanceof int[]) {
                    int[] array = (int[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncedField.SyncedInt(te, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncedField.SyncedInt(te, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof float[]) {
                    float[] array = (float[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncedField.SyncedFloat(te, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncedField.SyncedFloat(te, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof double[]) {
                    double[] array = (double[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncedField.SyncedDouble(te, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncedField.SyncedDouble(te, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof boolean[]) {
                    boolean[] array = (boolean[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncedField.SyncedBoolean(te, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncedField.SyncedBoolean(te, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof String[]) {
                    String[] array = (String[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncedField.SyncedString(te, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncedField.SyncedString(te, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o.getClass().isArray() && o.getClass().getComponentType().isEnum()) {
                    Object[] enumArray = (Object[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncedField.SyncedEnum(te, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < enumArray.length; i++) {
                            syncedFields.add(new SyncedField.SyncedEnum(te, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof ItemStack[]) {
                    ItemStack[] array = (ItemStack[]) o;
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncedField.SyncedItemStack(te, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncedField.SyncedItemStack(te, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof FluidStack[]) {
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncedField.SyncedFluidStack(te, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        FluidStack[] array = (FluidStack[]) o;
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncedField.SyncedFluidStack(te, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                    return syncedFields;
                }
                if (o instanceof ItemStackHandler[]) {
                    if (filteredIndex >= 0) {
                        syncedFields.add(new SyncedField.SyncedItemStack(te, field).setArrayIndex(filteredIndex).setLazy(isLazy));
                    } else {
                        ItemStackHandler[] array = (ItemStackHandler[]) o;
                        for (int i = 0; i < array.length; i++) {
                            syncedFields.add(new SyncedField.SyncedItemStack(te, field).setArrayIndex(i).setLazy(isLazy));
                        }
                    }
                }
                if (field.getType().isArray()) {
                    Object[] array = (Object[]) o;
                    for (Object obj : array) {
                        syncedFields.addAll(getSyncedFields(obj, searchedAnnotation));
                    }
                } else {
                    syncedFields.addAll(getSyncedFields(o, searchedAnnotation));
                }
                if (syncedFields.size() > 0) return syncedFields;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.warning("Field " + field + " didn't produce any syncable fields!");
        }
        return syncedFields;
    }

    private static SyncedField<?> getSyncedFieldForField(Field field, Object te) {
        if (int.class.isAssignableFrom(field.getType())) return new SyncedField.SyncedInt(te, field);
        if (float.class.isAssignableFrom(field.getType())) return new SyncedField.SyncedFloat(te, field);
        if (double.class.isAssignableFrom(field.getType())) return new SyncedField.SyncedDouble(te, field);
        if (boolean.class.isAssignableFrom(field.getType())) return new SyncedField.SyncedBoolean(te, field);
        if (String.class.isAssignableFrom(field.getType())) return new SyncedField.SyncedString(te, field);
        if (field.getType().isEnum()) return new SyncedField.SyncedEnum(te, field);
        if (ItemStack.class.isAssignableFrom(field.getType())) return new SyncedField.SyncedItemStack(te, field);
        if (FluidStack.class.isAssignableFrom(field.getType())) return new SyncedField.SyncedFluidStack(te, field);
        if (IItemHandlerModifiable.class.isAssignableFrom(field.getType())) return new SyncedField.SyncedItemHandler(te, field);
        return null;
    }
}
