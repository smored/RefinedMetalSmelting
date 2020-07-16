package com.smore_d.rms.util;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.blocks.BlockItemBase;
import com.smore_d.rms.blocks.Mk2FurnaceBlock;
import com.smore_d.rms.blocks.PorousStoneBlock;
import com.smore_d.rms.items.ItemBase;
import com.smore_d.rms.tools.ModItemTier;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, RefinedMetalSmelting.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, RefinedMetalSmelting.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // Items
    public static final RegistryObject<Item> MK1_IRON_INGOT = ITEMS.register("mk1_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK2_IRON_INGOT = ITEMS.register("mk2_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK3_IRON_INGOT = ITEMS.register("mk3_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK4_IRON_INGOT = ITEMS.register("mk4_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK5_IRON_INGOT = ITEMS.register("mk5_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK6_IRON_INGOT = ITEMS.register("mk6_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK7_IRON_INGOT = ITEMS.register("mk7_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> IRON_BEAD = ITEMS.register("iron_bead", ItemBase::new);

    //tools subcategory
    public static final RegistryObject<SwordItem> MK1_SWORD = ITEMS.register("mk1_sword", () ->
            new SwordItem(ModItemTier.MK1, 0, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK1_PICKAXE = ITEMS.register("mk1_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK1, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK1_SHOVEL = ITEMS.register("mk1_shovel", () ->
            new ShovelItem(ModItemTier.MK1, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK1_AXE = ITEMS.register("mk1_axe", () ->
            new AxeItem(ModItemTier.MK1, 3, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK1_HOE = ITEMS.register("mk1_hoe", () ->
            new HoeItem(ModItemTier.MK1, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> MK2_SWORD = ITEMS.register("mk2_sword", () ->
            new SwordItem(ModItemTier.MK2, 1, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK2_PICKAXE = ITEMS.register("mk2_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK2, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK2_SHOVEL = ITEMS.register("mk2_shovel", () ->
            new ShovelItem(ModItemTier.MK2, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK2_AXE = ITEMS.register("mk2_axe", () ->
            new AxeItem(ModItemTier.MK2, 4, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK2_HOE = ITEMS.register("mk2_hoe", () ->
            new HoeItem(ModItemTier.MK2, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> MK4_SWORD = ITEMS.register("mk4_sword", () ->
            new SwordItem(ModItemTier.MK4, 4, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK4_PICKAXE = ITEMS.register("mk4_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK4, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK4_SHOVEL = ITEMS.register("mk4_shovel", () ->
            new ShovelItem(ModItemTier.MK4, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK4_AXE = ITEMS.register("mk4_axe", () ->
            new AxeItem(ModItemTier.MK4, 7, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK4_HOE = ITEMS.register("mk4_hoe", () ->
            new HoeItem(ModItemTier.MK4, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> MK5_SWORD = ITEMS.register("mk5_sword", () ->
            new SwordItem(ModItemTier.MK5, 5, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK5_PICKAXE = ITEMS.register("mk5_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK5, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK5_SHOVEL = ITEMS.register("mk5_shovel", () ->
            new ShovelItem(ModItemTier.MK5, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK5_AXE = ITEMS.register("mk5_axe", () ->
            new AxeItem(ModItemTier.MK5, 8, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK5_HOE = ITEMS.register("mk5_hoe", () ->
            new HoeItem(ModItemTier.MK5, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    
    public static final RegistryObject<SwordItem> MK6_SWORD = ITEMS.register("mk6_sword", () ->
            new SwordItem(ModItemTier.MK6, 6, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK6_PICKAXE = ITEMS.register("mk6_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK6, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK6_SHOVEL = ITEMS.register("mk6_shovel", () ->
            new ShovelItem(ModItemTier.MK6, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK6_AXE = ITEMS.register("mk6_axe", () ->
            new AxeItem(ModItemTier.MK6, 9, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK6_HOE = ITEMS.register("mk6_hoe", () ->
            new HoeItem(ModItemTier.MK6, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> MK7_SWORD = ITEMS.register("mk7_sword", () ->
            new SwordItem(ModItemTier.MK7, 7, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK7_PICKAXE = ITEMS.register("mk7_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK7, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK7_SHOVEL = ITEMS.register("mk7_shovel", () ->
            new ShovelItem(ModItemTier.MK7, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK7_AXE = ITEMS.register("mk7_axe", () ->
            new AxeItem(ModItemTier.MK7, 10, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK7_HOE = ITEMS.register("mk7_hoe", () ->
            new HoeItem(ModItemTier.MK7, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));


    // Blocks
    public static final RegistryObject<Block> MK2_FURNACE_BLOCK = BLOCKS.register("mk2_furnace_block", Mk2FurnaceBlock::new);
    public static final RegistryObject<Block> POROUS_STONE_BLOCK = BLOCKS.register("porous_stone_block", PorousStoneBlock::new);

    // Block Items
    public static final RegistryObject<Item> MK2_FURNACE_BLOCK_ITEM = ITEMS.register("mk2_furnace_block", () -> new BlockItemBase(MK2_FURNACE_BLOCK.get()));
    public static final RegistryObject<Item> POROUS_STONE_BLOCK_ITEM = ITEMS.register("porous_stone_block", () -> new BlockItemBase(POROUS_STONE_BLOCK.get()));
}