package com.alfred.aspects.curses;

import com.alfred.aspects.AspectsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class MirrorAspectEnchantment extends Enchantment {
    public MirrorAspectEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
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
        // Reflect a portion of the damage back to the attacker
        DamageSource damageSource = user.getDamageSources().mobAttack(user);
        user.damage(damageSource, AspectsMod.getEntityDamage(user, target) / 2.0f);
    }

    @Override
    public boolean isCursed() {
        return true;
    }
}
