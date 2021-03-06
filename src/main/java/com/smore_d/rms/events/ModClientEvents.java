package com.smore_d.rms.events;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.entities.FastDespawnArrowEntity;
import com.smore_d.rms.entities.IronPigEntity;
import com.smore_d.rms.init.*;
import com.smore_d.rms.util.MathHelper;
import com.smore_d.rms.util.RngHelper;
import com.smore_d.rms.util.enums.Prefixes;
import com.smore_d.rms.util.enums.Rarity;
import javafx.geometry.Side;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.annotation.Inherited;
import java.util.*;


@Mod.EventBusSubscriber(modid = RefinedMetalSmelting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ModClientEvents {



    @SubscribeEvent
    public static void itemOnGround(EntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof ItemEntity) {
            World world = entity.getEntityWorld();
            ItemStack itemStack = ((ItemEntity) entity).getItem();
            Set set = itemStack.getItem().getTags();

            for (ResourceLocation tags : itemStack.getItem().getTags()) {
                if (tags.toString().equals("forge:legendary")) {
                    for (int i = 0; i < 20; i++) {
                        world.addParticle(ParticleTypes.CRIT,
                                entity.getPosX(),
                                entity.getPosY() + 0.1D,
                                entity.getPosZ(),
                                0, 2 + RefinedMetalSmelting.RANDOM.nextInt(20) / 10D, 0);
                    }
                    for (double i = 0; i < 30; i++) {
                        world.addParticle(ParticleTypes.CRIT,
                                entity.getPosX() + 0.75D * Math.sin(Math.toRadians(i * 30)),
                                entity.getPosY() + 0.25D + i / 10D,
                                entity.getPosZ() + 0.75D * Math.cos(Math.toRadians(i * 30)),
                                0, 0, 0);
                    }
                } else if (tags.toString().equals("forge:epic")) {
                    world.addParticle(ParticleTypes.DRAGON_BREATH,
                            entity.getPosX(),
                            entity.getPosY() + 0.1D,
                            entity.getPosZ(),
                            0, RefinedMetalSmelting.RANDOM.nextInt(20) / 200D, 0);
                }
            }
        }
    }

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

    private static List<UUID> userList = new ArrayList<>();
    private static List<BlockPos> blockPosList = new ArrayList<BlockPos>();
    //private static Map<userList, blockPosList>


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

    @SubscribeEvent // code for many swords
    public static void onAttack(AttackEntityEvent event) {
        Entity target = event.getTarget().getEntity(); //grab entity being targeted
        LivingEntity igniter = event.getEntityLiving(); //grab igniter
        World world = target.getEntityWorld(); //grab entity's world
        BlockPos targetPos = target.getPosition(); //grab position of the victim

        if (igniter.getHeldItemMainhand().getItem() == ModItems.SWORDSPLOSION.get()) { // swordsplosion
            for (int i = 0; i < 100; i++) {
                int fuse = RefinedMetalSmelting.RANDOM.nextInt(15) + 5;
                double x = RefinedMetalSmelting.RANDOM.nextDouble();
                double y = RefinedMetalSmelting.RANDOM.nextDouble();
                double z = RefinedMetalSmelting.RANDOM.nextDouble();

                if (y < 0.25D) {
                    x = -x;
                } else if (y < 0.5D) {
                    z = -z;
                } else if (y < 0.75D) {
                    x = -x;
                    z = -z;
                }

                TNTEntity tntEntity = new TNTEntity(world, target.getPosX(), target.getPosY(), target.getPosZ(), igniter);
                world.addEntity(tntEntity);
                tntEntity.setInvisible(true);
                tntEntity.addVelocity(x, y + 1, z);
                tntEntity.setFuse(fuse);
            }
        } else if (igniter.getHeldItemMainhand().getItem() == ModItems.FUME_SWORD.get()) { // fume sword
            for (int i = 0; i < 100; i++) {
                world.addParticle(ParticleTypes.LAVA,
                        target.getPosX(),
                        target.getPosY(),
                        target.getPosZ(),
                        0, 25 + RefinedMetalSmelting.RANDOM.nextInt(20) / 10D, 0);
            }

            for (int x = 0; x < 12; x++) {
                for (int z = 0; z < 12; z++) {
                    BlockPos blockPos = new BlockPos(targetPos.getX() + x - 6, targetPos.getY() - 1, targetPos.getZ() + z - 6);
                    float hardness = world.getBlockState(blockPos).getBlockHardness(world, blockPos);
                    if (!world.getBlockState(blockPos).hasTileEntity() && !world.getBlockState(blockPos).equals(Blocks.AIR.getDefaultState()) && !world.getBlockState(blockPos).equals(Blocks.CAVE_AIR.getDefaultState()) && !(hardness == -1)) {
                        System.out.println("current block is: " + world.getBlockState(blockPos) + ", at: " + blockPos);
                        FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(world, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, world.getBlockState(blockPos));
                        world.addEntity(fallingBlockEntity);
                        fallingBlockEntity.addVelocity(0, 1, 0);
                    }
                }
            }

            AxisAlignedBB aabb = new AxisAlignedBB(target.getPosX() - 12, target.getPosY() - 6, target.getPosZ() - 12, target.getPosX() + 12, target.getPosY() + 6, target.getPosZ() + 12);
            List<Entity> affectedList = world.getEntitiesWithinAABBExcludingEntity(igniter, aabb);
            List<FallingBlockEntity> fallingList = new ArrayList<>();


            for (Entity current : affectedList) {
                if (current instanceof FallingBlockEntity) {
                    fallingList.add((FallingBlockEntity) current);
                } else {
                    double[] polar = MathHelper.toPolar(target.getPosX(), target.getPosZ(), current.getPosX(), current.getPosZ());
                    polar[0] = 5 / (polar[0] + 0.0001D);

                    polar[0] = polar[0] > 4 ? 4 : polar[0];

                    double[] rect = MathHelper.toRect(polar[0], polar[1]);
                    current.addVelocity(rect[0], polar[0] / 5, rect[1]);

                    if (current instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) current;
                        livingEntity.setHealth(livingEntity.getHealth() - (float) polar[0]);
                    }
                }
            }
        } else if (igniter.getHeldItemMainhand().getItem() == ModItems.MOON_SWORD.get()) {
            if (world.getDayTime() > 12000) {
                System.out.println("test");
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

}
