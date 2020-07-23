package com.smore_d.rms.tools.shootable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class PelletAmmo extends AbstractArrowEntity {


    protected PelletAmmo(EntityType<? extends AbstractArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    protected PelletAmmo(EntityType<? extends AbstractArrowEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
    }

    protected PelletAmmo(EntityType<? extends AbstractArrowEntity> type, LivingEntity shooter, World worldIn) {
        super(type, shooter, worldIn);
    }

    @Override
    protected ItemStack getArrowStack() {
        return null;
    }

    @Override
    protected SoundEvent getHitEntitySound() {
        return SoundEvents.BLOCK_ANVIL_PLACE;
    }

    @Override
    public boolean getIsCritical() {
        return false;
    }

}
