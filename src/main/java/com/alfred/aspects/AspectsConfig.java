package com.alfred.aspects;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.item.*;

import java.util.Arrays;
import java.util.List;

@Config(name = "aspects")
public class AspectsConfig implements ConfigData {
    @Comment("These are the normal aspects the mod implements")
    public boolean frostAspectEnabled = true;
    public boolean earthAspectEnabled = true;
    public boolean windAspectEnabled = true;
    public boolean gravityAspectEnabled = true;
    public boolean bloodAspectEnabled = true;
    public boolean poisonAspectEnabled = true;
    public boolean witherAspectEnabled = true;
    public boolean mirrorAspectCurseEnabled = true;

    @Comment("This will allow multiple aspect enchantments to be present on an item at the same time")
    public boolean allowMultipleAspects = false;

    // Aspects disabled by default
    @Comment("These aspects are disabled by default")
    public boolean creeperAspectEnabled = false;
    public boolean chaosAspectEnabled = false;
    public boolean dragonAspectEnabled = false;
    public boolean goldenAspectEnabled = false;
    @Comment("Enchantment-specific settings")
    public boolean allowEarthAndGravityAspects = true;
    public boolean creeperAspectDamagesSelf = true;

    // Restrict enchantments to specific item types
    @ConfigEntry.Category("Item Restrictions")
    @Comment("Acceptable values are \"sword\", \"axe\", \"pickaxe\", \"shovel\", \"hoe\", \"bow\", \"crossbow\", \"trident\", and \"any\"")
    public List<String> allowedItemTypes = Arrays.asList("sword", "bow");

    public static AspectsConfig getInstance() {
        return AutoConfig.getConfigHolder(AspectsConfig.class).getConfig();
    }
    public static void save() {
        AutoConfig.getConfigHolder(AspectsConfig.class).save();
    }
    public static void load() {
        AutoConfig.getConfigHolder(AspectsConfig.class).load();
    }

    public boolean isItemAllowed(Item item) {
        return (allowedItemTypes.contains("any")) ||
               (item instanceof SwordItem && allowedItemTypes.contains("sword")) ||
               (item instanceof AxeItem && allowedItemTypes.contains("axe")) ||
               (item instanceof PickaxeItem && allowedItemTypes.contains("pickaxe")) ||
               (item instanceof ShovelItem && allowedItemTypes.contains("shovel")) ||
               (item instanceof HoeItem && allowedItemTypes.contains("hoe")) ||
               (item instanceof BowItem && allowedItemTypes.contains("bow")) ||
               (item instanceof CrossbowItem && allowedItemTypes.contains("crossbow")) ||
               (item instanceof TridentItem && allowedItemTypes.contains("trident")) ||
               (item instanceof RangedWeaponItem && allowedItemTypes.contains("ranged"));
    }
}
