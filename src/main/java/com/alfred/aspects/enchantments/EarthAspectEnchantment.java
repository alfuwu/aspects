import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EarthAspectEnchantment extends Enchantment {
    public EarthAspectEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMinPower(int level) {
        return 15 + 25 * (level - 1);
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
    public void onTargetDamaged(int level, World world, PlayerEntity user, Entity target, int damage) {
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            if (level == 1) {
                livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 30, 0));
                livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 30, 0));
            } else if (level == 2) {
                livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 1));
                livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 40, 1));
            }
        }
    }
}
