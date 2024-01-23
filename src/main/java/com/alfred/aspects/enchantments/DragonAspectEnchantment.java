import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class DragonAspectEnchantment extends Enchantment {
    public DragonAspectEnchantment() {
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMinPower(int level) {
        return 30 + 40 * (level - 1);
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
            SmallFireballEntity fireball = new SmallFireballEntity(world, livingTarget, 0, 0, 0);
            fireball.explosionPower = 1;
            fireball.setPos(livingTarget.getX(), livingTarget.getY() + 1, livingTarget.getZ());
            world.spawnEntity(fireball);
        }
    }
}
