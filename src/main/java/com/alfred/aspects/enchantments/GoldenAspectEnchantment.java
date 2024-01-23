import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

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
    public void onTargetDamaged(int level, World world, PlayerEntity user, LivingEntity target, int damage) {
        if (level == 1) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 200, 1));
        }
    }
}
