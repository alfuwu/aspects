package com.alfred.aspects;

import com.alfred.aspects.curses.*;
import com.alfred.aspects.enchantments.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.FireAspectEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AspectsMod implements ModInitializer {
    public static Enchantment FROST_ASPECT;
    public static Enchantment EARTH_ASPECT;
    public static Enchantment WIND_ASPECT;
    public static Enchantment GRAVITY_ASPECT;
    public static Enchantment BLOOD_ASPECT;
    public static Enchantment POISON_ASPECT;
    public static Enchantment WITHER_ASPECT;
    public static Enchantment CHAOS_ASPECT;
    public static Enchantment MIRROR_ASPECT;
    public static Enchantment CREEPER_ASPECT;
    public static Enchantment DRAGON_ASPECT;
    public static Enchantment GOLDEN_ASPECT;
	@Override
	public void onInitialize() {
        AutoConfig.register(AspectsConfig.class, JanksonConfigSerializer::new);

        FROST_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "frost_aspect"), new FrostAspectEnchantment());
        EARTH_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "earth_aspect"), new EarthAspectEnchantment());
        WIND_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "wind_aspect"), new WindAspectEnchantment());
        GRAVITY_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "gravity_aspect"), new GravityAspectEnchantment());
        BLOOD_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "blood_aspect"), new BloodAspectEnchantment());
        POISON_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "poison_aspect"), new PoisonAspectEnchantment());
        WITHER_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "wither_aspect"), new WitherAspectEnchantment());
        CHAOS_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "chaos_aspect"), new ChaosAspectEnchantment());
        MIRROR_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "mirror_aspect"), new MirrorAspectEnchantment());
        CREEPER_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "creeper_aspect"), new CreeperAspectEnchantment());
        DRAGON_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "dragon_aspect"), new DragonAspectEnchantment());
        GOLDEN_ASPECT = Registry.register(Registries.ENCHANTMENT, new Identifier("aspects", "golden_aspect"), new GoldenAspectEnchantment());
	}

    public static float getEntityDamage(LivingEntity entity, Entity target) {
        float f = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        if (target instanceof LivingEntity livingTarget)
            f += EnchantmentHelper.getAttackDamage(entity.getMainHandStack(), livingTarget.getGroup());
        float s;
        if (entity instanceof PlayerEntity player)
            s = (float) player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED) / 20;
        else
            s = 0.2f; // 4 attack speed (default)
        return f * Math.min(1, (entity.getLastAttackTime() / 20f) * s);
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

    public static int getEnchantmentLevel(ItemStack stack, Class<? extends  Enchantment> enchantment) {
        AtomicInteger level = new AtomicInteger(0);
        for (int i = 0; i < stack.getEnchantments().size(); ++i) {
            NbtCompound nbtCompound = stack.getEnchantments().getCompound(i);
            Registries.ENCHANTMENT.getOrEmpty(EnchantmentHelper.getIdFromNbt(nbtCompound)).ifPresent((e) -> {
                if (enchantment.isInstance(e))
                    level.set(nbtCompound.getInt("lvl"));
            });
        }
        return level.get();
    }

    public static void removeEnchantment(ItemStack itemStack, Class<? extends Enchantment> enchantment) {
        if (itemStack.hasEnchantments()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);
            enchantments.entrySet().removeIf(entry -> enchantment.isInstance(entry.getKey()));
            EnchantmentHelper.set(enchantments, itemStack);
        }
    }

    public static boolean isAspectEnchantment(Enchantment other) {
        return !(!(other instanceof BloodAspectEnchantment || other instanceof ChaosAspectEnchantment || other instanceof CreeperAspectEnchantment || other instanceof DragonAspectEnchantment || other instanceof EarthAspectEnchantment || other instanceof FrostAspectEnchantment || other instanceof GoldenAspectEnchantment || other instanceof MirrorAspectEnchantment || other instanceof PoisonAspectEnchantment || other instanceof WindAspectEnchantment || other instanceof WitherAspectEnchantment || other instanceof FireAspectEnchantment) || AspectsConfig.getInstance().allowMultipleAspects);
    }
}