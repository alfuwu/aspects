import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Random;

public class ChaosAspectEnchantment extends Enchantment {
    public ChaosAspectEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 20 + 30 * (level - 1);
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
    public void onTargetDamaged(int level, World world, PlayerEntity user, LivingEntity target, int damage) {
        Random random = new Random();
        applyRandomEffect(target, random, random.nextInt((int)((5/4)*(4^level))) == 0, level); // Fancy exponential bullshit for compatability with levels outside this thing's range because NBT editing exists
    }

    private void applyRandomEffect(LivingEntity target, Random random, boolean isGoodEffect, int level) {
        // Define the pool of effects
        StatusEffect[] effects = isGoodEffect ? // Increase size of both pools, maybe use some other way to determine if effect is good or bad for compat with other mods
                new StatusEffect[]{StatusEffects.STRENGTH, StatusEffects.SPEED, StatusEffects.REGENERATION} :
                new StatusEffect[]{StatusEffects.WEAKNESS, StatusEffects.SLOWNESS, StatusEffects.POISON};

        // Apply a random effect from the pool
        StatusEffect selectedEffect = effects[random.nextInt(effects.length)];
        int duration = isGoodEffect ? (int)(20 * (0.5 / level)) : 20 * (level * 5); // Bad effects last longer at level 2

        // Apply the effect to the target
        target.addStatusEffect(new StatusEffectInstance(selectedEffect, duration, 0));
    }
}
