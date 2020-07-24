package com.smore_d.rms.events;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

@Mod.EventBusSubscriber(modid = RefinedMetalSmelting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onItemBlast(ExplosionEvent.Detonate event) {
        ItemStack itemToBeDestroyed = new ItemStack(RegistryHandler.MK4_IRON_INGOT.get());
        Item itemToBeSpawned = RegistryHandler.MK5_IRON_INGOT.get();
        List<Entity> itemsBlasted = event.getAffectedEntities();
        int itemsInCount = 0;
        double x = 0, y = 0, z = 0;
        World world = event.getWorld();

        for (Entity currentEntity : itemsBlasted) {
            if (currentEntity instanceof ItemEntity) {
                ItemStack stack = ((ItemEntity) currentEntity).getItem();

                if (stack.isItemEqual(itemToBeDestroyed)) {
                    itemsInCount += stack.getCount();
                    x = currentEntity.getPosX();
                    y = currentEntity.getPosY();
                    z = currentEntity.getPosZ();
                }
            }
        }
        if (itemsInCount != 0) {
            ItemStack output = new ItemStack(itemToBeSpawned, itemsInCount / 32);
            world.addEntity(new ItemEntity(world, x, y, z, output));
        }
    }

    @SubscribeEvent
    public static void onBlockBlast(ExplosionEvent.Detonate event) {
        List<BlockPos> blockPosBlasted = event.getAffectedBlocks();
        World world = event.getWorld();

        for (BlockPos blockPos : blockPosBlasted) {
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();

            if (block.equals(RegistryHandler.BORON_NITRIDE_ORE_BLOCK.get())) {
                world.setBlockState(blockPos, RegistryHandler.WEAK_BORON_NITRIDE_ORE_BLOCK.get().getDefaultState());
            }
        }
    }

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
        if (!event.getPlayer().getEntityWorld().isRemote) {
            if (player.getHeldItemMainhand().getItem() == RegistryHandler.SLINGSHOT.get() || player.getHeldItemOffhand().getItem() == RegistryHandler.SLINGSHOT.get()) {
                World world = player.getEntityWorld();
                world.playSound((PlayerEntity) null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1.0F, 0.50F);
            }
        }
    }


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
