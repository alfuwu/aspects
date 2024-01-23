import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class CreeperAspectEnchantment extends Enchantment {
    public CreeperAspectEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
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
    public void onTargetDamaged(int level, World world, PlayerEntity user, Entity target, int damage) {
        if (!world.isClient && target instanceof LivingEntity) {
            LivingEntity livingTarget = (LivingEntity) target;
            world.createExplosion(livingTarget, livingTarget.getX(), livingTarget.getY(), livingTarget.getZ(), 2.0f, true, true);
        }
    }
}
