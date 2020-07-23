package com.smore_d.rms.events;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.util.RegistryHandler;
import javafx.scene.control.TextFormatter;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.sql.ResultSet;

@Mod.EventBusSubscriber(modid = RefinedMetalSmelting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

//    @SubscribeEvent
//    public static void onCraftSpunIron(PlayerEvent.ItemCraftedEvent event) {
//        if (event.getCrafting().toString().equals("1 spun_iron")) {
//            LivingEntity player = event.getEntityLiving();
//            World world = player.getEntityWorld();
//            world.addEntity(new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), new ItemStack(Items.SHEARS)));
//        }
//    }

    @SubscribeEvent
    public static void onShootSlingshot(ArrowLooseEvent event) {
        LivingEntity player = event.getEntityLiving();
        if (player.getHeldItemMainhand().getItem() == RegistryHandler.SLINGSHOT.get() || player.getHeldItemOffhand().getItem() == RegistryHandler.SLINGSHOT.get()) {
            RefinedMetalSmelting.LOGGER.info("Slingshot Used");
            World world = player.getEntityWorld();
            world.playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1.0F, 0.50F);
        }
    }

//    @SubscribeEvent
//    public static void onJumpWithStick(LivingEvent.LivingJumpEvent event) {
//        LivingEntity player = event.getEntityLiving();
//        if (player.getHeldItemMainhand().getItem() == Items.STICK) {
//            World world = player.getEntityWorld();
//            world.setBlockState(player.getPosition().add(0, -1, 0), RegistryHandler.BLOOD_DIAMOND_ORE_BLOCK.get().getDefaultState());
//        }
//    }
//
//    @SubscribeEvent
//    public static void onDamageSheep(AttackEntityEvent event) {
//        if (event.getEntityLiving().getHeldItemMainhand().getItem() == RegistryHandler.MK4_SWORD.get()) {
//            if (event.getTarget().isAlive()) {
//                LivingEntity target = (LivingEntity) event.getTarget();
//
//                PlayerEntity player = event.getPlayer();
//                target.addPotionEffect(new EffectInstance(Effects.WITHER, 5*20));
//
//                if (!event.getPlayer().getEntityWorld().isRemote) {
//                    String msg = TextFormatting.RED + "test";
//                    player.sendMessage(new StringTextComponent(msg));
//                    //player.sendMessage(new StringTextComponent(msg), player.getUniqueID());
//                }
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public static void onCraftingTableOpen(GuiOpenEvent event) {
//        if (event.isCancelable()) {
//            if (event.getGui() instanceof CraftingScreen) {
//                event.setCanceled(true);
//            }
//        }
//    }

}
