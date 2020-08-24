package com.smore_d.rms.client.render;


import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.client.model.IronPigModel;
import com.smore_d.rms.entities.IronPigEntity;
import com.smore_d.rms.entities.LaserEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.ResourceLocation;

public class LaserRenderer extends EntityRenderer<LaserEntity> {


    protected static final ResourceLocation TEXTURE = new ResourceLocation(RefinedMetalSmelting.MOD_ID, "textures/entity/laser.png");

    public LaserRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(LaserEntity entity) {
        System.out.println("Texture found!!!");
        return TEXTURE;
    }


}
