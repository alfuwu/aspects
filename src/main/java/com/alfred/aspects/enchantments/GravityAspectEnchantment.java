package com.alfred.aspects.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class GravityAspectEnchantment extends Enchantment {
    public GravityAspectEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return (!AspectsMod.isAspectEnchantment(other) || other instanceof EarthAspectEnchantment) && !(other instanceof Enchantments.KNOCKBACK) && super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return AspectsConfig.getInstance().isItemAllowed(stack.getItem()) && AspectsConfig.getInstance().gravityAspectEnabled && super.isAcceptableItem(stack);
    }

    @Override
    public int getMinPower(int level) {
        return 18 + 25 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 40;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity livingTarget)
            livingTarget.setVelocity(livingTarget.getVelocity().add(0, 0, 0)); // perform vector math to get normalized vector of direction between user and the target to apply inverse knockback
    }
}
