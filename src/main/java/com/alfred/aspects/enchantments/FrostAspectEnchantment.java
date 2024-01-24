package com.alfred.aspects.enchantments;

import com.alfred.aspects.AspectsConfig;
import com.alfred.aspects.AspectsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class FrostAspectEnchantment extends Enchantment {
    public FrostAspectEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.VANISHABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
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
            livingTarget.setOnFire(false); // frost aspect clears fire, and thus naturally conflicts with Fire Aspect even if it is forced onto the same item
            livingTarget.setFrozenTicks(Math.min(livingTarget.getMinFreezeDamageTicks() * 3 + (600 * level), livingTarget.getFrozenTicks() + (300 * level)));
            user.playSound(SoundEvent.of(SoundEvents.ENTITY_PLAYER_HURT_FREEZE.getId(), 10f), 1f, 1f);
        }
    }
}
