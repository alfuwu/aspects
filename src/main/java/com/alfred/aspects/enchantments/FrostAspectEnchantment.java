package com.alfred.aspects.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class FrostAspectEnchantment extends Enchantment {
    public FrostAspectEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !AspectsMod.isAspectEnchantment(other) && super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return AspectsConfig.getInstance().isItemAllowed(stack.getItem()) && AspectsConfig.getInstance().frostAspectEnabled && super.isAcceptableItem(stack);
    }

    @Override
    public int getMinPower(int level) {
        return 10 + 20 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity livingTarget) {
            livingTarget.clearFire(); // frost aspect clears fire, and thus naturally conflicts with Fire Aspect even if it is forced onto the same item
            livingTarget.setTicksFrozen(Math.min(100 + (100 * level), livingTarget.getTicksFrozen() + (20 * level)));
        }
    }
}
