package com.alfred.aspects;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;

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
    public boolean chaosAspectEnabled = true; // I guess?
    public boolean mirrorAspectCurseEnabled = true;

    @Comment("This will allow multiple aspect enchantments to be present on an item at the same time")
    public boolean allowMultipleAspects = false;

    // Aspects disabled by default
    @Comment("These aspects are disabled by default")
    public boolean creeperAspectEnabled = false;
    public boolean dragonAspectEnabled = false;
    public boolean goldenAspectEnabled = false;

    // Restrict enchantments to specific item types
    @ConfigEntry.Category("Item Restrictions")
    @Comment("Acceptable values are \"sword\", \"axe\", \"pickaxe\", \"shovel\", \"hoe\", \"bow\", \"crossbow\", and \"any\"")
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

    // Get a list of allowed items based on the configured item types
    public List<Item> getAllowedItems() {
        return Registries.ITEM.stream()
            .filter(item -> allowedItemTypes.contains(getItemType(item)))
            .toList();
    }

    // Get the item type as a string
    private String getItemType(Item item) {
        if (item instanceof SwordItem) {
            return "sword";
        } else if (item instanceof AxeItem) {
            return "axe";
        } else if (item instanceof PickaxeItem) {
            return "pickaxe";
        } else if (item instanceof ShovelItem) {
            return "shovel";
        } else if (item instanceof HoeItem) {
            return "hoe";
        } else if (item instanceof BowItem) {
            return "bow";
        } else if (item instanceof CrossbowItem) {
            return "crossbow";
        } else if (item instanceof RangedItem) {
            return "ranged";
        } else {
            return "any";
        }
    }
}
