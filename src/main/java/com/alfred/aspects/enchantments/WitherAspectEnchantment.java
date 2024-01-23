import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class WitherAspectEnchantment extends Enchantment {
    public WitherAspectEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMinPower(int level) {
        return 14 + 20 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 40;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void onTargetDamaged(int level, World world, PlayerEntity user, LivingEntity target, int damage) {
        if (target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            if (level == 1) {
                livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20, 0));
            } else if (level == 2) {
                livingTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 40, 1));
            }
        }
    }
}
