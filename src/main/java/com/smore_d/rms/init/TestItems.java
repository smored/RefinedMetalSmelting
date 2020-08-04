package com.smore_d.rms.init;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.items.ItemBase;
import com.smore_d.rms.util.enums.ModItemTier;
import com.smore_d.rms.util.enums.Prefixes;
import com.smore_d.rms.util.enums.Rarity;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TestItems {


    public static Prefixes prefix = Prefixes.LIGHT;
    public static Rarity rarity = Rarity.COMMON;
    public static int registryNum = 0;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RefinedMetalSmelting.MOD_ID);


    public static final RegistryObject<SwordItem> COMMON_SWORD = ITEMS.register("common_sword", () ->
            new SwordItem(ModItemTier.MK2, prefix.attackDamage, prefix.attackSpeed, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> UNCOMMON_SWORD = ITEMS.register("uncommon_sword", () ->
            new SwordItem(ModItemTier.MK4, prefix.attackDamage, prefix.attackSpeed, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> RARE_SWORD = ITEMS.register("rare_sword", () ->
            new SwordItem(ModItemTier.MK5, prefix.attackDamage, prefix.attackSpeed, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> EPIC_SWORD = ITEMS.register("epic_sword", () ->
            new SwordItem(ModItemTier.MK6, prefix.attackDamage, prefix.attackSpeed, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> LEGENDARY_SWORD = ITEMS.register("legendary_sword", () ->
            new SwordItem(ModItemTier.MK7, prefix.attackDamage, prefix.attackSpeed, new Item.Properties().group(RefinedMetalSmelting.TAB)));

}
