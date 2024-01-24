package com.alfred.aspects.enchantments;

import com.alfred.aspects.AspectsConfig;
import com.alfred.aspects.AspectsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class EarthAspectEnchantment extends Enchantment {
    public EarthAspectEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.VANISHABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return (!AspectsMod.isAspectEnchantment(other) || (other instanceof GravityAspectEnchantment && AspectsConfig.getInstance().allowEarthAndGravityAspects)) && super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return AspectsConfig.getInstance().isItemAllowed(stack.getItem()) && AspectsConfig.getInstance().earthAspectEnabled && super.isAcceptableItem(stack);
    }

    @Override
    public int getMinPower(int level) {
        return 15 + 25 * (level - 1);
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
            livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 + (10 * level), 0));
            livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20 + (10 * level), 0));
        }
    }
}
