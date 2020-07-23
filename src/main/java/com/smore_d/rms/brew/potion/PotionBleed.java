package com.smore_d.rms.brew.potion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;

import javax.annotation.Nonnull;

public class PotionBleed extends Effect {

    // using botania source code to try and make my own bleed effect
    // https://github.com/Vazkii/Botania/blob/19962e146f80e876e87d19dfc0cea0b008d8f307/src/main/java/vazkii/botania/common/brew

    public PotionBleed() {
        super(EffectType.HARMFUL, 0x610404);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(@Nonnull LivingEntity living, int amplifier) {
        living.attackEntityFrom(DamageSource.MAGIC, 1);
    }

}
