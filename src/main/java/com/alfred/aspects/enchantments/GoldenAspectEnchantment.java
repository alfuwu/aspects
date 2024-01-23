package com.alfred.aspects.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GoldenAspectEnchantment extends Enchantment {
    public GoldenAspectEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    // override on item damaged method to remove durability, mixin to item renderer to modify glint color

    // also mixin to itementity to mark it as invulnerable if it has this enchantment, so that it cannot be destroyed by lava, cacti, etcetera

    @Override
    public int getMinPower(int level) {
        return 35 + 50 * (level - 1);
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
    public Text getName(int level) {
        MutableText mutableText = Text.translatable(this.getTranslationKey()).formatted(Formatting.GOLD);

        if (level != 1 || this.getMaxLevel() != 1)
            mutableText.append(ScreenTexts.SPACE).append(Text.translatable("enchantment.level." + level));

        return mutableText;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity livingTarget)
            livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 200 * level, level));
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }
}
