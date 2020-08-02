package com.smore_d.rms.events;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.entities.IronPigEntity;
import com.smore_d.rms.init.ModBlocks;
import com.smore_d.rms.init.ModEntityTypes;
import com.smore_d.rms.init.ModItems;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.CritParticle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.rmi.registry.RegistryHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = RefinedMetalSmelting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {


    @SubscribeEvent // iron boots sink you
    public static void onIronBoots(PlayerEvent event) {
        PlayerEntity player = event.getPlayer();
        World world = player.getEntityWorld();
        BlockPos playerPos = player.getPosition().add(0, 0, 0);

        if (world.getBlockState(playerPos).equals(Blocks.WATER.getDefaultState()) || world.getBlockState(playerPos.add(0, 1, 0)).equals(Blocks.WATER.getDefaultState())) {

            Iterable<ItemStack> armor = player.getArmorInventoryList();
            for (ItemStack currentItem : armor) {
                if (currentItem.isItemEqual(new ItemStack(Items.IRON_BOOTS))) {
                    player.addVelocity(0, -0.1D, 0);
                }
            }
        }
    }


    private static List<BlockPos> blockPosList = new ArrayList<BlockPos>();

    @SubscribeEvent // make the glowstone sword glow
    public static void onHoldingGlowSword(PlayerEvent event) {
        PlayerEntity player = event.getPlayer();
        World world = player.getEntityWorld();
        BlockPos playerPos = player.getPosition().add(0, 0, 0);


        if (player.getHeldItemMainhand().getItem().equals(ModItems.GLOWSTONE_SWORD.get()) || player.getHeldItemOffhand().getItem().equals(ModItems.GLOWSTONE_SWORD.get())) {
            if (world.getLight(playerPos) <= 13) {
                if (world.getBlockState(playerPos).equals(Blocks.AIR.getDefaultState()) || world.getBlockState(playerPos).equals(Blocks.CAVE_AIR.getDefaultState())) {

                    world.setBlockState(playerPos, ModBlocks.BRIGHT_AIR.get().getDefaultState());
                    blockPosList.add(playerPos);
                }
            }
        } else {
            if (blockPosList.size() > 0) {
                for (BlockPos i : blockPosList) {
                    if (world.getBlockState(i).equals(ModBlocks.BRIGHT_AIR.get().getDefaultState())) {
                        world.setBlockState(i, Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        Random rand = new Random();
        double closeness = 1D;
        double rotationOffset = 90D;
        double transformOffset = 0.5D;
        double randomness = rand.nextInt(16)/12.5D;

        if (player.getHeldItemMainhand().getItem().equals(ModItems.GLOWSTONE_SWORD.get())) {
            world.addParticle(ParticleTypes.CRIT,
                    Math.cos(Math.toRadians(player.rotationYaw + rotationOffset)) + playerPos.getX(),
                    playerPos.getY() + 1,
                    Math.sin(Math.toRadians(player.rotationYaw + rotationOffset)) + playerPos.getZ(),
                    0, -0.01, 0);


//            world.addParticle(ParticleTypes.CRIT,
//                    randomness * closeness * Math.cos(Math.toRadians(player.rotationYaw + rotationOffset)) + player.getPosX() + transformOffset,
//                    player.getPosY() + 1,
//                    randomness * closeness * Math.sin(Math.toRadians(player.rotationYaw + rotationOffset)) + player.getPosZ() + transformOffset,
//                    0, -0.01, 0);
        }



    }


    @SubscribeEvent // Push the player up if flying over a campfire
    public static void onElytra(PlayerEvent event) {
        int blocksDown = 0;
        double playerX = event.getPlayer().getPosX();
        double playerY = event.getPlayer().getPosY();
        double playerZ = event.getPlayer().getPosZ();
        Block currentBlock;
        World world = event.getPlayer().getEntityWorld();

        if (event.getPlayer().isElytraFlying()) {
            do {
                currentBlock = world.getBlockState(new BlockPos(playerX, playerY, playerZ)).getBlock();
                playerY--;
                blocksDown++;
                if (playerY <= 0 || blocksDown >= 255) break;
            } while (currentBlock == Blocks.AIR);

            if (currentBlock == Blocks.CAMPFIRE && blocksDown <= 15) {
                event.getPlayer().addVelocity(0, 0.1D, 0);
            } else if (currentBlock == Blocks.SOUL_CAMPFIRE && blocksDown <= 30) {
                event.getPlayer().addVelocity(0, 0.2D, 0);
            }
        }
    }

    @SubscribeEvent // code for the swordsplosion
    public static void onLeftClick(AttackEntityEvent event) {
        Entity entity = event.getTarget().getEntity(); //grab entity being targeted
        LivingEntity igniter = event.getEntityLiving(); //grab igniter
        World world = entity.getEntityWorld(); //grab entity's world

        Random rng = new Random();

        if (igniter.getHeldItemMainhand().getItem() == ModItems.SWORDSPLOSION.get()) {
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
                tntEntity.setInvisible(true);
                tntEntity.addVelocity(x, y + 1, z);
                tntEntity.setFuse(fuse);
            }
        }
    }

    private static boolean isCooledDown = true;

    @SubscribeEvent // code to check if player is wearing an armour set
    public static void onPlayerDamaged(LivingDamageEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        boolean helmet = false, chest = false, leg = false, boot = false;

        if (!world.isRemote && entity instanceof PlayerEntity) {

            Iterable<ItemStack> armor = entity.getArmorInventoryList();
            for (ItemStack currentItem : armor) {
                if (currentItem.isItemEqual(new ItemStack(ModItems.MK7_HELMET.get().asItem()))) {
                    helmet = true;
                    continue;
                }
                if (currentItem.isItemEqual(new ItemStack(ModItems.MK7_CHESTPLATE.get().asItem()))) {
                    chest = true;
                    continue;
                }
                if (currentItem.isItemEqual(new ItemStack(ModItems.MK7_LEGGINGS.get().asItem()))) {
                    leg = true;
                    continue;
                }
                if (currentItem.isItemEqual(new ItemStack(ModItems.MK7_BOOTS.get().asItem()))) {
                    boot = true;
                }
            }
            if (helmet && chest && leg && boot) {
//                isCooledDown = false;
//                Thread t = new Thread(() -> {
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException ignored) {}
//                    isCooledDown = true;
//                });
//                t.start();
            }


//            if (isCooledDown) {
//                PlayerEntity playerEntity = ((PlayerEntity) entity);
//                playerEntity.addPotionEffect(new EffectInstance(Effects.ABSORPTION, 5 * 20, 4));
//                isCooledDown = false;
//            }
        }
    }

    @SubscribeEvent // iron pig conversion code
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

    @SubscribeEvent // code for blast armour and item conversion
    public static void onEntityBlast(ExplosionEvent.Detonate event) {
        ItemStack itemToBeDestroyed = new ItemStack(ModItems.MK4_IRON_INGOT.get());
        Item itemToBeSpawned = ModItems.MK5_IRON_INGOT.get();
        List<Entity> entitiesBlasted = event.getAffectedEntities();
        int itemsInCount = 0;
        double x = 0, y = 0, z = 0;
        World world = event.getWorld();
        boolean helmet = false, chest = false, leg = false, boot = false;


        for (Entity currentEntity : entitiesBlasted) {
            if (currentEntity instanceof ItemEntity) { // items
                ItemStack stack = ((ItemEntity) currentEntity).getItem();

                if (stack.isItemEqual(itemToBeDestroyed)) {
                    itemsInCount += stack.getCount();
                    x = currentEntity.getPosX();
                    y = currentEntity.getPosY();
                    z = currentEntity.getPosZ();
                }
            }

            if (currentEntity instanceof PlayerEntity) { // players
                PlayerEntity entity = ((PlayerEntity) currentEntity);

                Iterable<ItemStack> armor = entity.getArmorInventoryList();
                for (ItemStack currentItem : armor) {
                    if (currentItem.isItemEqual(new ItemStack(ModItems.BLAST_HELMET.get().asItem()))) {
                        helmet = true;
                        continue;
                    }
                    if (currentItem.isItemEqual(new ItemStack(ModItems.BLAST_CHESTPLATE.get().asItem()))) {
                        chest = true;
                        continue;
                    }
                    if (currentItem.isItemEqual(new ItemStack(ModItems.BLAST_LEGGINGS.get().asItem()))) {
                        leg = true;
                        continue;
                    }
                    if (currentItem.isItemEqual(new ItemStack(ModItems.BLAST_BOOTS.get().asItem()))) {
                        boot = true;
                    }
                }

                if (helmet && chest && leg && boot) {
                    ((PlayerEntity) entity).addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1, 4));
                }

            }

        }
        if (itemsInCount != 0) {
            ItemStack output = new ItemStack(itemToBeSpawned, itemsInCount / 32);
            world.addEntity(new ItemEntity(world, x, y, z, output));
        }

    }


    @SubscribeEvent // code for ore conversion
    public static void onBlockBlast(ExplosionEvent.Detonate event) {
        List<BlockPos> blockPosBlasted = event.getAffectedBlocks();
        World world = event.getWorld();

        for (BlockPos blockPos : blockPosBlasted) {
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();

            if (block.equals(ModBlocks.BORON_NITRIDE_ORE_BLOCK.get())) {
                world.setBlockState(blockPos, ModBlocks.WEAK_BORON_NITRIDE_ORE_BLOCK.get().getDefaultState());
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
