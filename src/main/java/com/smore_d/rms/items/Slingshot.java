package com.smore_d.rms.items;

import com.smore_d.rms.entities.IronBallEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class Slingshot extends BowItem {

    private final Predicate<ItemStack> AMMO;

    public Slingshot(Item AMMO, Properties builder) {
        super(builder);
        this.AMMO = (x) -> {
            return x.getItem() == AMMO;
        };
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity living, int duration) {
        int j = getUseDuration(stack) - duration;

        if (!world.isRemote && (!(living instanceof PlayerEntity) || ((PlayerEntity) living).abilities.isCreativeMode || hasAmmo((PlayerEntity) living, AMMO))) {
            float f = j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if (f < 1F) {
                return;
            }

            if (living instanceof PlayerEntity && !((PlayerEntity) living).abilities.isCreativeMode) {
                consumeAmmo((PlayerEntity) living, AMMO);
            }

            IronBallEntity arrow = new IronBallEntity(living, true);
            arrow.func_234612_a_(living, living.rotationPitch, living.rotationYaw, 0.0F, f * 3.0F, 1.0F);
            //arrow.setMotion(arrow.getMotion().scale(1.0));
            world.addEntity(arrow);
            world.playSound(null, living.getPosX(), living.getPosY(), living.getPosZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        }
    }

    public static ItemStack getAmmo(PlayerEntity player, Predicate<ItemStack> ammoFunc) {
        if (ammoFunc.test(player.getHeldItem(Hand.OFF_HAND))) {
            return player.getHeldItem(Hand.OFF_HAND);
        } else if (ammoFunc.test(player.getHeldItem(Hand.MAIN_HAND))) {
            return player.getHeldItem(Hand.MAIN_HAND);
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
                ItemStack itemstack = player.inventory.getStackInSlot(i);

                if (ammoFunc.test(itemstack)) {
                    return itemstack;
                }
            }

            return ItemStack.EMPTY;
        }
    }

    public static boolean hasAmmo(PlayerEntity player, Predicate<ItemStack> ammoFunc) {
        return !getAmmo(player, ammoFunc).isEmpty();
    }

    public static void consumeAmmo(PlayerEntity player, Predicate<ItemStack> ammoFunc) {
        ItemStack ammo = getAmmo(player, ammoFunc);
        if (!ammo.isEmpty()) {
            ammo.shrink(1);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 18000;
    }

    @Nonnull
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.abilities.isCreativeMode || hasAmmo(player, AMMO)) {
            player.setActiveHand(hand);
            return ActionResult.resultSuccess(stack);
        }

        return ActionResult.resultPass(stack);
    }

//    // These two methods override what ammo is used for the slingshot
//    @Override
//    public Predicate<ItemStack> getAmmoPredicate() {
//        return AMMO;
//    }
//
//    @Override
//    public Predicate<ItemStack> getInventoryAmmoPredicate() {
//        return AMMO;
//    }
//
//    @Override
//    public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
//        return arrow;
//    }
//
//    @Override
//    public int getMaxDamage(ItemStack stack) {
//        return 100;
//    }
//
//    @Override
//    public int getItemStackLimit(ItemStack stack) {
//        return 1;
//    }
//
//
////    @Nullable
////    @Override
////    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
////        return null;
////    }
//
//    //    public static float getArrowVelocity(int charge) {
////        float f = (float)charge / 20.0F;
////        f = (f * f + f * 2.0F) / 3.0F;
////        if (f > 1.0F) {
////            f = 1.0F;
////        }
////
////        return (int) (f/4);
////    }
////
////    @Override
////    public int getUseDuration(ItemStack stack) {
////        return 18000;
////    }


}