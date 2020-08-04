package com.smore_d.rms.items.special_tools.shootables;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.entities.FastDespawnArrowEntity;
import com.smore_d.rms.util.enums.Rarity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.*;
import net.minecraft.stats.Stats;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class CarpetBow extends BowItem {


    public CarpetBow(Properties builder) {
        super(builder);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entityLiving;
            boolean flag = playerentity.abilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemstack = playerentity.findAmmo(stack);

            int i = this.getUseDuration(stack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, playerentity, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = playerentity.abilities.isCreativeMode || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, stack, playerentity));
                    if (!worldIn.isRemote) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrowEntity abstractarrowentity = arrowitem.createArrow(worldIn, itemstack, playerentity);
                        abstractarrowentity = customArrow(abstractarrowentity);
                        abstractarrowentity.func_234612_a_(playerentity, playerentity.rotationPitch, playerentity.rotationYaw, 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrowentity.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if (j > 0) {
                            abstractarrowentity.setDamage(abstractarrowentity.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if (k > 0) {
                            abstractarrowentity.setKnockbackStrength(k);
                        }

                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            abstractarrowentity.setFire(100);
                        }

                        stack.damageItem(1, playerentity, (p_220009_1_) -> {
                            p_220009_1_.sendBreakAnimation(playerentity.getActiveHand());
                        });
                        if (flag1 || playerentity.abilities.isCreativeMode && (itemstack.getItem() == Items.SPECTRAL_ARROW || itemstack.getItem() == Items.TIPPED_ARROW)) {
                            abstractarrowentity.pickupStatus = AbstractArrowEntity.PickupStatus.CREATIVE_ONLY;
                        }

                        //worldIn.addEntity(abstractarrowentity);
                        shootModBow(entityLiving, worldIn, f);
                    }

                    worldIn.playSound((PlayerEntity)null, playerentity.getPosX(), playerentity.getPosY(), playerentity.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            playerentity.inventory.deleteStack(itemstack);
                        }
                    }

                    playerentity.addStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    private void shootModBow(LivingEntity shooter, World world, float vel) {

        Vector3d eyePos = shooter.getEyePosition(1.0F);
        BlockRayTraceResult result = shooter.getEntityWorld().rayTraceBlocks(new RayTraceContext(eyePos, shooter.getLookVec().mul(256, 256, 256).add(eyePos), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, shooter));

        if (result.getType() == RayTraceResult.Type.BLOCK) {
            int x = new BlockPos(result.getHitVec()).getX();
            int y = new BlockPos(result.getHitVec()).getY();
            int z = new BlockPos(result.getHitVec()).getZ();
            int offset = RefinedMetalSmelting.RANDOM.nextInt(90);

            for (double i = 1; i < 101*vel; i++) {
                FastDespawnArrowEntity arrowEntity = new FastDespawnArrowEntity(world, shooter);
                arrowEntity.setPosition(x + Math.sin(Math.toRadians(i * 50 + offset)) * (i / 10D), y + 100, z + Math.cos(Math.toRadians(i * 50 + offset)) * (i / 10D));
                arrowEntity.setVelocity(0, -i / 10D, 0);
                world.addEntity(arrowEntity);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {


        tooltip.set(0, new StringTextComponent(Rarity.LEGENDARY.colour + "Carpet Bow"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7c" + "\u00A7o" + "Galaxy Gun"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "Summons a mighty hail of arrows from the sky in a circular pattern"));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

}
