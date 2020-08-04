package com.smore_d.rms;

import com.smore_d.rms.entities.IronPigEntity;
import com.smore_d.rms.init.ModBlocks;
import com.smore_d.rms.init.ModEntityTypes;
import com.smore_d.rms.init.ModItems;
import com.smore_d.rms.init.TestItems;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod("rms")
public class RefinedMetalSmelting
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "rms";
    public static final Random RANDOM = new Random();

    public RefinedMetalSmelting() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityTypes.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        TestItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
             GlobalEntityTypeAttributes.put(ModEntityTypes.IRON_PIG.get(), IronPigEntity.setCustomAttributes().create());
        });
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        System.out.println(ModItems.MK1_SWORD.get().getRegistryName());
    }

    public static final ItemGroup TAB = new ItemGroup("rmsTab"){

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.MK7_SWORD.get());
        }
    };
}
