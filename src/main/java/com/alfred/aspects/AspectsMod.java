package com.alfred.aspects;

import com.alfred.aspects.AspectsConfig;
import com.alfred.aspects.enchantments.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AspectsMod implements ModInitializer {
	@Override
	public void onInitialize() {
        AutoConfig.register(AspectsConfig.class, JanksonConfigSerializer::new);

        AspectsConfig config = AspectsConfig.getInstance();
		if (config.frostAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "frost_aspect"), new FrostAspectEnchantment());
		if (config.earthAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "earth_aspect"), new EarthAspectEnchantment());
        if (config.windAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "wind_aspect"), new WindAspectEnchantment());
        if (config.bloodAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "blood_aspect"), new BloodAspectEnchantment());
        if (config.poisonAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "poison_aspect"), new PoisonAspectEnchantment());
        if (config.witherAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "wither_aspect"), new WitherAspectEnchantment());
        if (config.chaosAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "chaos_aspect"), new ChaosAspectEnchantment());
        if (config.creeperAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "creeper_aspect"), new CreeperAspectEnchantment());
        if (config.dragonAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "dragon_aspect"), new DragonAspectEnchantment());
        if (config.goldenAspectEnabled)
            Registry.register(Registry.ENCHANTMENT, new Identifier("aspects", "golden_aspect"), new GoldenAspectEnchantment());
	}
}