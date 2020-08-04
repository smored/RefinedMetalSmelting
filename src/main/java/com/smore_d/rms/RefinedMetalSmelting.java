package com.smore_d.rms;

import com.smore_d.rms.entities.IronBallEntity;
import com.smore_d.rms.entities.IronPigEntity;
import com.smore_d.rms.init.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("rms")
public class RefinedMetalSmelting
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "rms";

    public RefinedMetalSmelting() {

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);

        ModBlocks.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntityTypes.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModContainers.CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
             GlobalEntityTypeAttributes.put(ModEntityTypes.IRON_PIG.get(), IronPigEntity.setCustomAttributes().create());
        });
    }

    public static final ItemGroup TAB = new ItemGroup("rmsTab"){

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.MK7_SWORD.get());
        }
    };
}
