package com.smore_d.rms.client.render;

import com.smore_d.rms.RefinedMetalSmelting;
import com.smore_d.rms.client.model.IronPigModel;
import com.smore_d.rms.entities.IronPigEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class IronPigRenderer extends MobRenderer<IronPigEntity, IronPigModel<IronPigEntity>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(RefinedMetalSmelting.MOD_ID, "textures/entity/iron_pig.png");

    public IronPigRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new IronPigModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getEntityTexture(IronPigEntity entity) {
        return TEXTURE;
    }
}
