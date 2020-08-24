package com.smore_d.rms.util;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.client.render.IronPigRenderer;
import com.smore_d.rms.client.render.LaserRenderer;
import com.smore_d.rms.init.ModEntityTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = RefinedMetalSmelting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.IRON_PIG.get(), IronPigRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.LASER.get(), LaserRenderer::new);
    }

}
