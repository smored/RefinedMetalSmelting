package com.smore_d.rms.entities;

import com.smore_d.rms.util.RegistryHandler;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;

public class IronBallEntity extends ThrowableEntity implements IRendersAsItem {
    private static final DataParameter<Float> GRAVITY = EntityDataManager.createKey(IronBallEntity.class, DataSerializers.FLOAT);

    // register the iron ball entity
    public static final EntityType<IronBallEntity> IRON_BALL = EntityType.Builder.<IronBallEntity>create(IronBallEntity::new, EntityClassification.MISC)
            .size(0.25F, 0.25F)
            .setTrackingRange(64)
            .setUpdateInterval(10)
            .setShouldReceiveVelocityUpdates(true)
            .build("");

    public IronBallEntity(EntityType<IronBallEntity> type, World world) {
        super(type, world);
    }

    public IronBallEntity(LivingEntity thrower, boolean gravity) {
        super(IRON_BALL, thrower, thrower.world);
        dataManager.set(GRAVITY, gravity ? 0.03F : 0F);
    }

    @Override
    protected void registerData() {
        dataManager.register(GRAVITY, 0F);
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(RegistryHandler.IRON_BEAD.get());
    }

    @Nonnull
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for (int j = 0; j < 16; j++) {
                world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(RegistryHandler.IRON_BEAD.get())), getPosX(), getPosY(), getPosZ(), Math.random() * 0.2 - 0.1, Math.random() * 0.25, Math.random() * 0.2 - 0.1);
            }
        }
    }

    @Override
    protected float getGravityVelocity() {
        return dataManager.get(GRAVITY);
    }

}
