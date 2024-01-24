package com.alfred.aspects.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class CreeperAspectEnchantment extends Enchantment {
    public CreeperAspectEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !AspectsMod.isAspectEnchantment(other) && super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return AspectsConfig.getInstance().isItemAllowed(stack.getItem()) && AspectsConfig.getInstance().creeperAspectEnabled && super.isAcceptableItem(stack);
    }

    @Override
    public int getMinPower(int level) {
        return 25 + 30 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (!user.getEntityWorld().isClient)
            user.getEntityWorld().createExplosion(user, target.getX(), target.getY(), target.getZ(), 2.0f, World.ExplosionSourceType.MOB);
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }
}
