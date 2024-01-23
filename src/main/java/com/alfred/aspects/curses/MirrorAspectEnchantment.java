import net.minecraft.enchantment.CurseEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class MirrorAspectCurseEnchantment extends CurseEnchantment {
    public MirrorAspectCurseEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 25 + 30 * (level - 1);
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
            // Reflect a portion of the damage back to the attacker
            DamageSource damageSource = DamageSource.player(user);
            user.damage(damageSource, damage / 2.0f);
        }
    }
}
