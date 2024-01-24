package com.alfred.aspects.mixin;

import com.alfred.aspects.AspectsConfig;
import com.alfred.aspects.AspectsMod;
import com.alfred.aspects.enchantments.CreeperAspectEnchantment;
import com.alfred.aspects.enchantments.GoldenAspectEnchantment;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract NbtList getEnchantments();

    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    private void cancelDurabilityDamage(CallbackInfoReturnable<Boolean> cir) {
        if (AspectsMod.hasEnchantment(((ItemStack) (Object) this), GoldenAspectEnchantment.class))
            cir.setReturnValue(false);
    }

    @Inject(method = "postMine", at = @At("HEAD"))
    private void explode(World world, BlockState state, BlockPos pos, PlayerEntity miner, CallbackInfo ci) {
        if (AspectsMod.hasEnchantment(((ItemStack) (Object) this), CreeperAspectEnchantment.class))
            world.createExplosion(AspectsConfig.getInstance().creeperAspectDamagesSelf ? null : miner, Explosion.createDamageSource(world, AspectsConfig.getInstance().creeperAspectDamagesSelf ? null : miner), new EntityExplosionBehavior(miner), pos.getX(), pos.getY(), pos.getZ(), AspectsMod.getEnchantmentLevel(((ItemStack) (Object) this), CreeperAspectEnchantment.class) * 2, false, World.ExplosionSourceType.MOB);
    }
}
