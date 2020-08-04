package com.smore_d.rms.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;

public class FastDespawnArrowEntity extends ArrowEntity {

    private int ticksInGround;

    public FastDespawnArrowEntity(EntityType<? extends ArrowEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public FastDespawnArrowEntity(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public FastDespawnArrowEntity(World worldIn, LivingEntity shooter) {
        super(worldIn, shooter);
    }

    @Override
    protected void func_225516_i_() {
        ++this.ticksInGround;
        if (this.ticksInGround >= 40) {
            this.remove();
        };
    }

}
