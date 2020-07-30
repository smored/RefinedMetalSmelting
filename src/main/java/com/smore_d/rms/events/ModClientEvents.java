package com.smore_d.rms.events;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.entities.IronPigEntity;
import com.smore_d.rms.init.ModEntityTypes;
import com.smore_d.rms.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = RefinedMetalSmelting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onLeftClick(AttackEntityEvent event) {
        Entity entity = event.getTarget().getEntity(); //grab entity being targeted
        LivingEntity igniter = event.getEntityLiving(); //grab igniter
        World world = entity.getEntityWorld(); //grab entity's world

        Random rng = new Random();

        //if (!world.isRemote) {
        if (igniter.getHeldItemMainhand().getItem() == RegistryHandler.SWORDSPLOSION.get()) {
            for (int i = 0; i < 100; i++) {
                int fuse = rng.nextInt(15) + 5;
                double x = rng.nextDouble();
                double y = rng.nextDouble();
                double z = rng.nextDouble();

                if (y < 0.25D) {
                    x = -x;
                } else if (y < 0.5D) {
                    z = -z;
                } else if (y < 0.75D) {
                    x = -x;
                    z = -z;
                }

                TNTEntity tntEntity = new TNTEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), igniter);
                world.addEntity(tntEntity);
                tntEntity.addVelocity(x, y + 1, z);
                tntEntity.setFuse(fuse);
            }
        }
        //   }
    }

    @SubscribeEvent
    public static void onPlayerDamaged(LivingDamageEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        boolean helmet = false, chest = false, leg = false, boot = false;
        long initialTime = 0;
        long coolDownTime = 5 * 20;
        boolean isCooledDown = true;

        if (!world.isRemote && entity instanceof PlayerEntity) {

            Iterable<ItemStack> armor = entity.getArmorInventoryList();
            for (ItemStack currentItem : armor) {
                if (currentItem.isItemEqual(new ItemStack(RegistryHandler.MK7_HELMET.get().asItem()))) {
                    helmet = true;
                    continue;
                }
                if (currentItem.isItemEqual(new ItemStack(RegistryHandler.MK7_CHESTPLATE.get().asItem()))) {
                    chest = true;
                    continue;
                }
                if (currentItem.isItemEqual(new ItemStack(RegistryHandler.MK7_LEGGINGS.get().asItem()))) {
                    leg = true;
                    continue;
                }
                if (currentItem.isItemEqual(new ItemStack(RegistryHandler.MK7_BOOTS.get().asItem()))) {
                    boot = true;
                }
            }

            if (helmet && chest && leg && boot) {
                PlayerEntity playerEntity = ((PlayerEntity) entity);
                playerEntity.addPotionEffect(new EffectInstance(Effects.REGENERATION, 5 * 20, 0));
            }


            isCooledDown = world.getGameTime() - initialTime >= coolDownTime;
            RefinedMetalSmelting.LOGGER.info(world.getGameTime() - initialTime);

            if (isCooledDown) {
                if (((PlayerEntity) entity).getHeldItemMainhand().isItemEqual(new ItemStack(RegistryHandler.MK7_SWORD.get()))) {
                    if (event.getSource().isMagicDamage()) {
                        PlayerEntity playerEntity = ((PlayerEntity) entity);


                        if (playerEntity.getHealth() <= (playerEntity.getMaxHealth() * 0.30F)) {
                            playerEntity.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 5 * 20, 4));
                        }
                    }
                }
                isCooledDown = false;
                initialTime = world.getGameTime();
            }
        }
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.EntityInteractSpecific event) {
        Entity entity = event.getTarget().getEntity(); //grab entity being targeted
        World world = entity.getEntityWorld(); //grab entity's world


        if (!world.isRemote && event.getHand().equals(Hand.MAIN_HAND)) {
            if (event.getEntityLiving().getHeldItemMainhand().getItem() == Items.IRON_INGOT) { //check if player is holding iron ingot
                if (event.getTarget().isAlive() && event.getTarget() instanceof PigEntity) { //check if target is a pig

                    ItemStack itemStack = event.getEntityLiving().getHeldItemMainhand();
                    itemStack.setCount(itemStack.getCount() - 1);
                    event.getEntityLiving().setHeldItem(Hand.MAIN_HAND, itemStack); //removes one item from the players hand

                    entity.remove();
                    IronPigEntity ironPigEntity = new IronPigEntity(ModEntityTypes.IRON_PIG.get(), world);
                    ironPigEntity.moveToBlockPosAndAngles(entity.getPosition(), entity.rotationYaw, entity.rotationPitch);
                    world.addEntity(ironPigEntity); //remove the pig and replace it with an iron pig
                }
            } else if (event.getEntityLiving().getHeldItemMainhand().getItem() == Items.BONE_MEAL) {
                if (event.getTarget().isAlive() && event.getTarget() instanceof IronPigEntity) {

                    ItemStack itemStack = event.getEntityLiving().getHeldItemMainhand();
                    itemStack.setCount(itemStack.getCount() - 1);
                    event.getEntityLiving().setHeldItem(Hand.MAIN_HAND, itemStack); //removes one item from the players hand

                    world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY() + 1, entity.getPosZ(), (new ItemStack(Items.IRON_NUGGET)))); //spawn an iron nugget in if the player clicks with bone meal
                }
            }
        }
    }

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

//    @SubscribeEvent
//    public static void onShootSlingshot(ArrowLooseEvent event) {
//        LivingEntity player = event.getEntityLiving();
//        if (!event.getPlayer().getEntityWorld().isRemote) {
//            if (player.getHeldItemMainhand().getItem() == RegistryHandler.SLINGSHOT.get() || player.getHeldItemOffhand().getItem() == RegistryHandler.SLINGSHOT.get()) {
//                World world = player.getEntityWorld();
//                world.playSound((PlayerEntity) null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 1.0F, 0.50F);
//            }
//        }
//    }


//    @SubscribeEvent
//    public static void onDamageSheep(AttackEntityEvent event) {
//        if (event.getEntityLiving().getHeldItemMainhand().getItem() == RegistryHandler.MK4_SWORD.get()) {
//            if (event.getTarget().isAlive()) {
//                LivingEntity target = (LivingEntity) event.getTarget();
//
//                PlayerEntity player = event.getPlayer();
//
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
