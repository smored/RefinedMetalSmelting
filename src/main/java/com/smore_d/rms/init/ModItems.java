package com.smore_d.rms.init;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.blocks.BlockItemBase;
import com.smore_d.rms.items.*;
import com.smore_d.rms.items.special_tools.shootables.CarpetBow;
import com.smore_d.rms.items.special_tools.shootables.Slingshot;
import com.smore_d.rms.items.special_tools.swords.FumeSword;
import com.smore_d.rms.items.special_tools.swords.GlowstoneSword;
import com.smore_d.rms.items.special_tools.swords.Swordsplosion;
import com.smore_d.rms.util.enums.ModArmourMaterial;
import com.smore_d.rms.util.enums.ModItemTier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RefinedMetalSmelting.MOD_ID);


    // Items
    public static final RegistryObject<Item> MK1_IRON_INGOT = ITEMS.register("mk1_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK2_IRON_INGOT = ITEMS.register("mk2_iron_ingot", ItemBase::new);
    //public static final RegistryObject<Item> MK3_IRON_INGOT = ITEMS.register("mk3_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK4_IRON_INGOT = ITEMS.register("mk4_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK5_IRON_INGOT = ITEMS.register("mk5_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK6_IRON_INGOT = ITEMS.register("mk6_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> MK7_IRON_INGOT = ITEMS.register("mk7_iron_ingot", ItemBase::new);
    public static final RegistryObject<Item> IRON_BEAD = ITEMS.register("iron_bead", ItemBase::new);
    public static final RegistryObject<Item> BLOOD_DIAMOND = ITEMS.register("blood_diamond", ItemBase::new);
    public static final RegistryObject<Item> ENERGIZED_BLOOD_DIAMOND = ITEMS.register("energized_blood_diamond", ItemBase::new);
    public static final RegistryObject<Item> UNFIRED_CUPEL = ITEMS.register("unfired_cupel", ItemBase::new);
    public static final RegistryObject<Item> CUPEL = ITEMS.register("cupel", ItemBase::new);
    public static final RegistryObject<Item> CARBON_COAL = ITEMS.register("carbon_coal", CarbonCoal::new);
    public static final RegistryObject<Item> KAOLIN = ITEMS.register("kaolin", ItemBase::new);
    public static final RegistryObject<Item> SPUN_IRON = ITEMS.register("spun_iron", ItemBase::new);
    public static final RegistryObject<Item> BONE_ASH = ITEMS.register("bone_ash", ItemBase::new);
    public static final RegistryObject<Item> KAOWOOL = ITEMS.register("kaowool", ItemBase::new);
    public static final RegistryObject<Item> LIME = ITEMS.register("lime", ItemBase::new);
    public static final RegistryObject<Item> REFRACTORY_CLAY = ITEMS.register("refractory_clay", ItemBase::new);
    public static final RegistryObject<Item> FIREBRICK = ITEMS.register("firebrick", ItemBase::new);
    public static final RegistryObject<Item> DIAMOND_FILE = ITEMS.register("diamond_file", ItemBase::new);
    public static final RegistryObject<Item> T1_FRAME = ITEMS.register("t1_frame", ItemBase::new);
    public static final RegistryObject<Item> T2_FRAME = ITEMS.register("t2_frame", ItemBase::new);
    public static final RegistryObject<Item> T3_FRAME = ITEMS.register("t3_frame", ItemBase::new);
    public static final RegistryObject<Item> BLUE_IRON_AGGREGATE = ITEMS.register("blue_iron_aggregate", ItemBase::new);
    public static final RegistryObject<Item> GRAPHITE = ITEMS.register("graphite", ItemBase::new);
    public static final RegistryObject<Item> CATHODE = ITEMS.register("cathode", ItemBase::new);
    public static final RegistryObject<Item> IRON_ANODE = ITEMS.register("iron_anode", ItemBase::new);
    public static final RegistryObject<Item> AEROGEL = ITEMS.register("aerogel", ItemBase::new);
    public static final RegistryObject<Item> C_BN = ITEMS.register("c_bn", ItemBase::new);
    public static final RegistryObject<Item> W_BN = ITEMS.register("w_bn", ItemBase::new);
    public static final RegistryObject<Item> D_BN = ITEMS.register("d_bn", ItemBase::new);
    public static final RegistryObject<Item> PIG_IRON = ITEMS.register("pig_iron", ItemBase::new);
    public static final RegistryObject<Item> BLAST_PLATE = ITEMS.register("blast_plate", ItemBase::new);

    // Food subcategory
    public static final RegistryObject<Smore> SMORE = ITEMS.register("smore", Smore::new);

    // Tools subcategory
    public static final RegistryObject<SwordItem> MK1_SWORD = ITEMS.register("mk1_sword", () ->
            new SwordItem(ModItemTier.MK1, 2, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK1_PICKAXE = ITEMS.register("mk1_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK1, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK1_SHOVEL = ITEMS.register("mk1_shovel", () ->
            new ShovelItem(ModItemTier.MK1, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK1_AXE = ITEMS.register("mk1_axe", () ->
            new AxeItem(ModItemTier.MK1, 3, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK1_HOE = ITEMS.register("mk1_hoe", () ->
            new HoeItem(ModItemTier.MK1, 0, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> MK2_SWORD = ITEMS.register("mk2_sword", () ->
            new SwordItem(ModItemTier.MK2, 3, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK2_PICKAXE = ITEMS.register("mk2_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK2, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK2_SHOVEL = ITEMS.register("mk2_shovel", () ->
            new ShovelItem(ModItemTier.MK2, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK2_AXE = ITEMS.register("mk2_axe", () ->
            new AxeItem(ModItemTier.MK2, 4, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK2_HOE = ITEMS.register("mk2_hoe", () ->
            new HoeItem(ModItemTier.MK2, 0, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<Item> SLINGSHOT = ITEMS.register("slingshot", () ->
            new Slingshot(IRON_BEAD.get(), new Item.Properties().group((RefinedMetalSmelting.TAB))));

    public static final RegistryObject<Item> CARPET_BOW = ITEMS.register("carpet_bow", () ->
            new CarpetBow(new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> MK4_SWORD = ITEMS.register("mk4_sword", () ->
            new SwordItem(ModItemTier.MK4, 4, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK4_PICKAXE = ITEMS.register("mk4_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK4, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK4_SHOVEL = ITEMS.register("mk4_shovel", () ->
            new ShovelItem(ModItemTier.MK4, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK4_AXE = ITEMS.register("mk4_axe", () ->
            new AxeItem(ModItemTier.MK4, 7, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK4_HOE = ITEMS.register("mk4_hoe", () ->
            new HoeItem(ModItemTier.MK4, 0, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> BORON_SWORD = ITEMS.register("boron_sword", () ->
            new SwordItem(ModItemTier.BN, 17, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> BORON_PICKAXE = ITEMS.register("boron_pickaxe", () ->
            new PickaxeItem(ModItemTier.BN, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> MK5_SWORD = ITEMS.register("mk5_sword", () ->
            new SwordItem(ModItemTier.MK5, 5, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK5_PICKAXE = ITEMS.register("mk5_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK5, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK5_SHOVEL = ITEMS.register("mk5_shovel", () ->
            new ShovelItem(ModItemTier.MK5, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK5_AXE = ITEMS.register("mk5_axe", () ->
            new AxeItem(ModItemTier.MK5, 8, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK5_HOE = ITEMS.register("mk5_hoe", () ->
            new HoeItem(ModItemTier.MK5, 0, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> MK6_SWORD = ITEMS.register("mk6_sword", () ->
            new SwordItem(ModItemTier.MK6, 6, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK6_PICKAXE = ITEMS.register("mk6_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK6, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK6_SHOVEL = ITEMS.register("mk6_shovel", () ->
            new ShovelItem(ModItemTier.MK6, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK6_AXE = ITEMS.register("mk6_axe", () ->
            new AxeItem(ModItemTier.MK6, 9, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK6_HOE = ITEMS.register("mk6_hoe", () ->
            new HoeItem(ModItemTier.MK6, 0, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> MK7_SWORD = ITEMS.register("mk7_sword", () ->
            new SwordItem(ModItemTier.MK7, 7, -2.4f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<PickaxeItem> MK7_PICKAXE = ITEMS.register("mk7_pickaxe", () ->
            new PickaxeItem(ModItemTier.MK7, 0, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ShovelItem> MK7_SHOVEL = ITEMS.register("mk7_shovel", () ->
            new ShovelItem(ModItemTier.MK7, 0, -3.0f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<AxeItem> MK7_AXE = ITEMS.register("mk7_axe", () ->
            new AxeItem(ModItemTier.MK7, 10, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<HoeItem> MK7_HOE = ITEMS.register("mk7_hoe", () ->
            new HoeItem(ModItemTier.MK7, 0, 0, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<PickaxeItem> MK8_PICKAXE = ITEMS.register("mk8_pickaxe", () ->
            new PickaxeItem(ModItemTier.GOD, 996, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> SWORDSPLOSION = ITEMS.register("swordsplosion", () ->
            new Swordsplosion(ModItemTier.MK1, 0, -3.1f, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> GLOWSTONE_SWORD = ITEMS.register("glowstone_sword", () ->
            new GlowstoneSword(ModItemTier.MK4, 5, -2.8f, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<SwordItem> FUME_SWORD = ITEMS.register("fume_sword", () ->
            new FumeSword(ModItemTier.MK5, 20, -3.5f, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    // Armour
    public static final RegistryObject<ArmorItem> MK7_HELMET = ITEMS.register("mk7_helmet", () ->
            new ArmorItem(ModArmourMaterial.MK7, EquipmentSlotType.HEAD, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ArmorItem> MK7_CHESTPLATE = ITEMS.register("mk7_chestplate", () ->
            new ArmorItem(ModArmourMaterial.MK7, EquipmentSlotType.CHEST, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ArmorItem> MK7_LEGGINGS = ITEMS.register("mk7_leggings", () ->
            new ArmorItem(ModArmourMaterial.MK7, EquipmentSlotType.LEGS, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ArmorItem> MK7_BOOTS = ITEMS.register("mk7_boots", () ->
            new ArmorItem(ModArmourMaterial.MK7, EquipmentSlotType.FEET, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    public static final RegistryObject<ArmorItem> BLAST_HELMET = ITEMS.register("blast_helmet", () ->
            new ArmorItem(ModArmourMaterial.BLAST, EquipmentSlotType.HEAD, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ArmorItem> BLAST_CHESTPLATE = ITEMS.register("blast_chestplate", () ->
            new ArmorItem(ModArmourMaterial.BLAST, EquipmentSlotType.CHEST, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ArmorItem> BLAST_LEGGINGS = ITEMS.register("blast_leggings", () ->
            new ArmorItem(ModArmourMaterial.BLAST, EquipmentSlotType.LEGS, new Item.Properties().group(RefinedMetalSmelting.TAB)));
    public static final RegistryObject<ArmorItem> BLAST_BOOTS = ITEMS.register("blast_boots", () ->
            new ArmorItem(ModArmourMaterial.BLAST, EquipmentSlotType.FEET, new Item.Properties().group(RefinedMetalSmelting.TAB)));

    // Block Items
    public static final RegistryObject<Item> MK2_FURNACE_BLOCK_ITEM = ITEMS.register("mk2_furnace_block", () -> new BlockItemBase(ModBlocks.MK2_FURNACE_BLOCK.get()));
    public static final RegistryObject<Item> MK3_FURNACE_BLOCK_ITEM = ITEMS.register("mk3_furnace_block", () -> new BlockItemBase(ModBlocks.MK3_FURNACE_BLOCK.get()));
    public static final RegistryObject<Item> ELECTROLYTIC_BLOCK_ITEM = ITEMS.register("electrolytic_block", () -> new BlockItemBase(ModBlocks.ELECTROLYTIC_BLOCK.get()));
    public static final RegistryObject<Item> POROUS_STONE_BLOCK_ITEM = ITEMS.register("porous_stone_block", () -> new BlockItemBase(ModBlocks.POROUS_STONE_BLOCK.get()));
    public static final RegistryObject<Item> BLOOD_DIAMOND_ORE_BLOCK_ITEM = ITEMS.register("blood_diamond_ore_block", () -> new BlockItemBase(ModBlocks.BLOOD_DIAMOND_ORE_BLOCK.get()));
    public static final RegistryObject<Item> CARBON_COAL_ORE_BLOCK_ITEM = ITEMS.register("carbon_coal_ore_block", () -> new BlockItemBase(ModBlocks.CARBON_COAL_ORE_BLOCK.get()));
    public static final RegistryObject<Item> KAOLINITE_ORE_BLOCK_ITEM = ITEMS.register("kaolinite_ore_block", () -> new BlockItemBase(ModBlocks.KAOLINITE_ORE_BLOCK.get()));
    public static final RegistryObject<Item> BORON_NITRIDE_ORE_BLOCK_ITEM = ITEMS.register("boron_nitride_ore_block", () -> new BlockItemBase(ModBlocks.BORON_NITRIDE_ORE_BLOCK.get()));
    public static final RegistryObject<Item> WEAK_BORON_NITRIDE_ORE_BLOCK_ITEM = ITEMS.register("weak_boron_nitride_ore_block", () -> new BlockItemBase(ModBlocks.WEAK_BORON_NITRIDE_ORE_BLOCK.get()));
}
