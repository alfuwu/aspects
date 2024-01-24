package com.alfred.aspects.enchantments;

import com.alfred.aspects.AspectsConfig;
import com.alfred.aspects.AspectsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class DragonAspectEnchantment extends Enchantment {
    public DragonAspectEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.VANISHABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !AspectsMod.isAspectEnchantment(other) && super.canAccept(other);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return AspectsConfig.getInstance().isItemAllowed(stack.getItem()) && AspectsConfig.getInstance().dragonAspectEnabled && super.isAcceptableItem(stack);
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
            DragonFireballEntity fireball = new DragonFireballEntity(user.getEntityWorld(), user, 0, 0, 0);
            fireball.setPos(user.getX(), user.getY() + 1, user.getZ());
            fireball.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, level * 3.0f, 1.0f / level);
            user.getEntityWorld().spawnEntity(fireball);
            user.playSound(SoundEvent.of(SoundEvents.ENTITY_ENDER_DRAGON_SHOOT.getId(), 10f), 0.3f, 1f);
        }
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }
}
