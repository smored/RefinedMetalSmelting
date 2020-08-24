package com.smore_d.rms.init;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.blocks.*;
import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RefinedMetalSmelting.MOD_ID);

    // Blocks
    public static final RegistryObject<Block> MK2_FURNACE_BLOCK = BLOCKS.register("mk2_furnace_block", Mk2FurnaceBlock::new);
    public static final RegistryObject<Block> MK3_FURNACE_BLOCK = BLOCKS.register("mk3_furnace_block", Mk3FurnaceBlock::new);
    public static final RegistryObject<Block> ELECTROLYTIC_BLOCK = BLOCKS.register("electrolytic_block", ElectrolyticRefinery::new);
    //public static final RegistryObject<Block> BLAST_CHAMBER_BLOCK = BLOCKS.register("blast_chamber_block", );
    //register zone refinery
    public static final RegistryObject<Block> POROUS_STONE_BLOCK = BLOCKS.register("porous_stone_block", PorousStoneBlock::new);
    public static final RegistryObject<Block> BLOOD_DIAMOND_ORE_BLOCK = BLOCKS.register("blood_diamond_ore_block", BloodDiamondOreBlock::new);
    public static final RegistryObject<Block> CARBON_COAL_ORE_BLOCK = BLOCKS.register("carbon_coal_ore_block", CarbonCoalOreBlock::new);
    public static final RegistryObject<Block> KAOLINITE_ORE_BLOCK = BLOCKS.register("kaolinite_ore_block", KaoliniteOreBlock::new);
    public static final RegistryObject<Block> BORON_NITRIDE_ORE_BLOCK = BLOCKS.register("boron_nitride_ore_block", BoronNitrideOreBlock::new);
    public static final RegistryObject<Block> WEAK_BORON_NITRIDE_ORE_BLOCK = BLOCKS.register("weak_boron_nitride_ore_block", WeakBoronNitrideOreBlock::new);
    public static final RegistryObject<Block> BRIGHT_AIR = BLOCKS.register("bright_air", BrightAir::new);
    public static final RegistryObject<Block> PINE_LOG = BLOCKS.register("pine_log", PineLog::new);
    public static final RegistryObject<Block> GOLD_PINE_LOG = BLOCKS.register("gold_pine_log", GoldPineLog::new);
    public static final RegistryObject<Block> PINE_LEAVES = BLOCKS.register("pine_leaves", PineLeaves::new);
    public static final RegistryObject<Block> GOLD_PINE_LEAVES = BLOCKS.register("gold_pine_leaves", GoldPineLeaves::new);
    public static final RegistryObject<Block> ASHEN_GRASS_BLOCK = BLOCKS.register("ashen_grass_block", AshenGrass::new);
    public static final RegistryObject<Block> ASHEN_DIRT_BLOCK = BLOCKS.register("ashen_dirt_block", AshenDirt::new);
}
