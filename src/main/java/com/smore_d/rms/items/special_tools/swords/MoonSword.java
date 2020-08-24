package com.smore_d.rms.items.special_tools.swords;

import com.smore_d.rms.entities.LaserEntity;
import com.smore_d.rms.init.ModEntityTypes;
import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import com.smore_d.rms.util.enums.Rarity;

import java.util.List;

public class MoonSword extends SwordItem {


    public MoonSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }


    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        tooltip.set(0, new StringTextComponent(Rarity.LEGENDARY.colour + "Moon Sword"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7c" + "\u00A7o" + "That's rough, buddy"));
        tooltip.add(new StringTextComponent("\u00A7f" + "- " + "\u00A7f" + "This weapon becomes" + "\u00A7b" + " Empowered " + "\u00A7f" + "with the moon's presence"));

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        Vector3d look = playerIn.getLookVec();
        LaserEntity laser = new LaserEntity(ModEntityTypes.LASER.get(), worldIn);
        laser.setNoGravity(true);
        laser.setPosition(playerIn.getPosX() + look.x * 1.5d, playerIn.getPosY() + look.y * 1.5d, playerIn.getPosZ() + look.z * 1.5d);
        laser.shoot(playerIn.getPosX() + look.x * 1.5d, playerIn.getPosY() + look.y * 1.5d, playerIn.getPosZ() + look.z * 1.5d, 0, 0.5f);

        if (!worldIn.isRemote) {
            worldIn.addEntity(laser);
            System.out.println(laser);
        }



        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

//    @Override
//    public void onUse(World worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
//        System.out.println("used");
//
//        Vector3d look = livingEntityIn.getLookVec();
//        LaserEntity laser = new LaserEntity(ModEntityTypes.LASER.get(), worldIn);
//        laser.setPosition(livingEntityIn.getPosX() + look.x * 1.5d, livingEntityIn.getPosY() + look.y * 1.5d, livingEntityIn.getPosZ() + look.z * 1.5d);
//        laser.shoot(livingEntityIn.getPosX() + look.x * 1.5d, livingEntityIn.getPosY() + look.y * 1.5d, livingEntityIn.getPosZ() + look.z * 1.5d, 1.5f, 0.5f);
//
//        if (!worldIn.isRemote) {
//            worldIn.addEntity(laser);
//        }
//
//        System.out.println(laser);
//
//        super.onUse(worldIn, livingEntityIn, stack, count);
//    }

}
