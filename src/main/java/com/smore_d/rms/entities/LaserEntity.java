package com.smore_d.rms.entities;

import com.smore_d.rms.init.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.World;

public class LaserEntity extends AbstractArrowEntity {



    public LaserEntity(EntityType<? extends LaserEntity> type, World worldIn) {
        super(type, worldIn);
    }

// commenting these two constructors fixes the issue in ModEntityTypes.class

//    public LaserEntity(EntityType<? extends LaserEntity> type, double x, double y, double z, World worldIn) {
//        super(type, x, y, z, worldIn);
//    }
//
//    public LaserEntity(EntityType<? extends LaserEntity> type, LivingEntity livingEntityIn, World worldIn) {
//        super(type, livingEntityIn, worldIn);
//    }



    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        if (!this.world.isRemote) {
            //result
            Entity entity = p_213868_1_.getEntity();
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getEntity()), 25f);

            if (entity instanceof LivingEntity) {
                entity.addVelocity(0, 0.5f, 0);
            }

            super.onEntityHit(p_213868_1_);
        }
    }

    @Override
    protected ItemStack getArrowStack() {
        return null;
    }

}
