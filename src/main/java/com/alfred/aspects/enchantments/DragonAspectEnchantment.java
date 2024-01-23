package com.alfred.aspects.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;

public class DragonAspectEnchantment extends Enchantment {
    public DragonAspectEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMinPower(int level) {
        return 30 + 40 * (level - 1);
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
        if (!user.getEntityWorld().isClient && target instanceof LivingEntity livingTarget) {
            SmallFireballEntity fireball = new SmallFireballEntity(user.getEntityWorld(), user, 0, 0, 0);
            fireball.setPos(user.getX(), user.getY() + 1, user.getZ());
            fireball.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, level * 3.0f, 1.0f / level);
            user.getEntityWorld().spawnEntity(fireball);
        }
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }
}
