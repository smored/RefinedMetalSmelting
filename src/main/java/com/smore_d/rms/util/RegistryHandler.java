package com.smore_d.rms.util;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.blocks.BlockItemBase;
import com.smore_d.rms.blocks.Mk2FurnaceBlock;
import com.smore_d.rms.blocks.PorousStoneBlock;
import com.smore_d.rms.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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

    public static final RegistryObject<Item> MK6_SWORD = ITEMS.register("mk6_sword", ItemBase::new);
    public static final RegistryObject<Item> MK7_SWORD = ITEMS.register("mk7_sword", ItemBase::new);

    // Blocks
    public static final RegistryObject<Block> MK2_FURNACE_BLOCK = BLOCKS.register("mk2_furnace_block", Mk2FurnaceBlock::new);
    public static final RegistryObject<Block> POROUS_STONE_BLOCK = BLOCKS.register("porous_stone_block", PorousStoneBlock::new);

    // Block Items
    public static final RegistryObject<Item> MK2_FURNACE_BLOCK_ITEM = ITEMS.register("mk2_furnace_block", () -> new BlockItemBase(MK2_FURNACE_BLOCK.get()));
    public static final RegistryObject<Item> POROUS_STONE_BLOCK_ITEM = ITEMS.register("porous_stone_block", () -> new BlockItemBase(POROUS_STONE_BLOCK.get()));
}
