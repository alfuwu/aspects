import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class BloodAspectEnchantment extends Enchantment {
    public BloodAspectEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMinPower(int level) {
        return 15 + 20 * (level - 1);
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
        if (level == 1) {
            user.heal(0.5f * damage);
        } else if (level == 2) {
            user.heal(0.75f * damage);
        }
    }
}
