package com.alfred.aspects;

import com.alfred.aspects.curses.*;
import com.alfred.aspects.enchantments.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.concurrent.atomic.AtomicBoolean;

public class AspectsMod implements ModInitializer {
	@Override
	public void onInitialize() {
        AutoConfig.register(AspectsConfig.class, JanksonConfigSerializer::new);

        AspectsConfig config = AspectsConfig.getInstance();
		if (config.frostAspectEnabled) // this may break if an item with an aspect is loaded when the aspect is disabled, depending on how it breaks, leave in
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "frost_aspect"), new FrostAspectEnchantment());
		if (config.earthAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "earth_aspect"), new EarthAspectEnchantment());
        if (config.windAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "wind_aspect"), new WindAspectEnchantment());
        if (config.gravityAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "gravity_aspect"), new GravityAspectEnchantment());
        if (config.bloodAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "blood_aspect"), new BloodAspectEnchantment());
        if (config.poisonAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "poison_aspect"), new PoisonAspectEnchantment());
        if (config.witherAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "wither_aspect"), new WitherAspectEnchantment());
        if (config.chaosAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "chaos_aspect"), new ChaosAspectEnchantment());
        if (config.mirrorAspectCurseEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "mirror_aspect"), new MirrorAspectEnchantment());
        if (config.creeperAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "creeper_aspect"), new CreeperAspectEnchantment());
        if (config.dragonAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "dragon_aspect"), new DragonAspectEnchantment());
        if (config.goldenAspectEnabled)
            Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "golden_aspect"), new GoldenAspectEnchantment());
	}

    public static float getEntityDamage(LivingEntity entity, Entity target) {
        // TODO: take into account PlayerEntity's attack speed to get a more accurate function
        float f = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (target instanceof LivingEntity livingTarget)
            f += EnchantmentHelper.getAttackDamage(entity.getMainHandStack(), livingTarget.getGroup());
        return f;
    }

    public static boolean hasEnchantment(ItemStack stack, Class<? extends Enchantment> enchantment) { // fancy
        AtomicBoolean foundEnchantment = new AtomicBoolean(false);
        for (int i = 0; i < stack.getEnchantments().size(); ++i) {
            NbtCompound nbtCompound = stack.getEnchantments().getCompound(i);
            Registries.ENCHANTMENT.getOrEmpty(EnchantmentHelper.getIdFromNbt(nbtCompound)).ifPresent((e) -> {
                if (enchantment.isInstance(e))
                    foundEnchantment.set(true);
            });
        }
        return foundEnchantment.get();
    }

    public static boolean isAspectEnchantment(Enchantment other) {
        return !(!(other instanceof BloodAspectEnchantment || other instanceof ChaosAspectEnchantment || other instanceof CreeperAspectEnchantment || other instanceof DragonAspectEnchant || other instanceof EarthAspectEnchantment || other instanceof FrostAspectEnchantment || other instanceof GoldenAspectEnchantment || other instanceof MirrorAspectEnchantment || other instanceof PoisonAspectEnchantment || other instanceof WindAspectEnchantment || other instanceof WitherAspectEnchantment || other instanceof Enchantments.FIRE_ASPECT) || AspectsConfig.getInstance().allowallowMultipleAspects);
    }
}