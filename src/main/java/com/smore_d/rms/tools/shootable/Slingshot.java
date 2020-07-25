package com.smore_d.rms.tools.shootable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

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


    // These two methods override what ammo is used for the slingshot
    @Override
    public Predicate<ItemStack> getAmmoPredicate() {
        return AMMO;
    }

    @Override
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return AMMO;
    }

    @Override
    public AbstractArrowEntity customArrow(AbstractArrowEntity arrow) {
        return arrow;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 100;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }


//    @Nullable
//    @Override
//    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
//        return null;
//    }

    //    public static float getArrowVelocity(int charge) {
//        float f = (float)charge / 20.0F;
//        f = (f * f + f * 2.0F) / 3.0F;
//        if (f > 1.0F) {
//            f = 1.0F;
//        }
//
//        return (int) (f/4);
//    }
//
//    @Override
//    public int getUseDuration(ItemStack stack) {
//        return 18000;
//    }


}
