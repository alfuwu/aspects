import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.List;

@Config(name = "aspects")
public class AspectsConfig implements ConfigData {
    @Comment("These are the normal aspects the mod implements")
    public boolean frostAspectEnabled = true;
    public boolean earthAspectEnabled = true;
    public boolean windAspectEnabled = true;
    public boolean bloodAspectEnabled = true;
    public boolean poisonAspectEnabled = true;
    public boolean witherAspectEnabled = true;
    public boolean chaosAspectEnabled = true; // I guess?

    // Aspects disabled by default
    @Comment("These aspects are disabled by default")
    public boolean creeperAspectEnabled = false;
    public boolean dragonAspectEnabled = false;
    public boolean goldenAspectEnabled = false;

    // Restrict enchantments to specific item types
    @ConfigEntry.Category("Item Restrictions")
    public List<String> allowedItemTypes = Arrays.asList("sword", "axe", "bow", "crossbow");

    public static DiscordConfig getInstance() {
        return AutoConfig.getConfigHolder(DiscordConfig.class).getConfig();
    }
    public static void save() {
        AutoConfig.getConfigHolder(DiscordConfig.class).save();
    }
    public static void load() {
        AutoConfig.getConfigHolder(DiscordConfig.class).load();
    }

    // Get a list of allowed items based on the configured item types
    public List<Item> getAllowedItems() {
        return Registry.ITEM.stream()
            .filter(item -> allowedItemTypes.contains(getItemType(item)))
            .toList();
    }

    // Get the item type as a string
    private String getItemType(Item item) {
        if (item.isIn(ItemTags.SWORDS)) {
            return "sword";
        } else if (item.isIn(ItemTags.AXES)) {
            return "axe";
        } else if (item.isIn(ItemTags.BOWS)) {
            return "bow";
        } else if (item.isIn(ItemTags.CROSSBOWS)) {
            return "crossbow";
        } else {
            return "other";
        }
    }
}
