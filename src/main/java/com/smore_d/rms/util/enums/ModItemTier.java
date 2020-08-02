package com.smore_d.rms.util.enums;

import com.smore_d.rms.init.ModItems;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.rmi.registry.RegistryHandler;
import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {

    // harvest level:
    // 0 is wood and fist
    // 1 is stone
    // 2 is iron
    // 3 is diamond

    MK1(1, 60, 3, 2.0f, 5, () -> {
        return Ingredient.fromItems(ModItems.MK1_IRON_INGOT.get());
    }),

    MK2(2, 200, 5, 2.0f, 10, () -> {
        return Ingredient.fromItems(ModItems.MK2_IRON_INGOT.get());
    }),

    // MK3 is skipped because that material is base iron

    MK4(4, 1500, 6, 2.0f, 15, () -> {
        return Ingredient.fromItems(ModItems.MK4_IRON_INGOT.get());
    }),

    MK5(5, 3000, 10, 2.0f, 20, () -> {
        return Ingredient.fromItems(ModItems.MK5_IRON_INGOT.get());
    }),

    BN(5, 1, 40, 2.0f, 0, null),

    MK6(6, 4000, 20, 2.0f, 40, () -> {
        return Ingredient.fromItems(ModItems.MK6_IRON_INGOT.get());
    }),

    MK7(7, 5000, 100, 2.0f, 80, () -> {
        return Ingredient.fromItems(ModItems.MK7_IRON_INGOT.get());
    }),

    GOD(8, Integer.MAX_VALUE, 9999, 2.0f, 160, () -> {
        return Ingredient.fromItems((ModItems.MK7_IRON_INGOT.get()));
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }
}
