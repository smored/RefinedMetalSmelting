package com.smore_d.rms.tools;

import com.smore_d.rms.util.RegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModItemTier implements IItemTier {

    MK1(1, 60, 3, 2.0f, 5, () -> {
        return Ingredient.fromItems(RegistryHandler.MK1_SWORD.get());
    }),

    MK2(2, 200, 5, 2.0f, 10, () -> {
        return Ingredient.fromItems(RegistryHandler.MK2_SWORD.get());
    }),

    // MK3 is skipped because that material is base iron

    MK4(3, 500, 6, 2.0f, 15, () -> {
        return Ingredient.fromItems(RegistryHandler.MK4_SWORD.get());
    }),

    MK5(3, 2000, 10, 2.0f, 20, () -> {
        return Ingredient.fromItems(RegistryHandler.MK5_SWORD.get());
    }),

    MK6(3, 5000, 20, 2.0f, 40, () -> {
        return Ingredient.fromItems(RegistryHandler.MK6_SWORD.get());
    }),

    MK7(3, 10000, 100, 2.0f, 80, () -> {
        return Ingredient.fromItems(RegistryHandler.MK7_SWORD.get());
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
