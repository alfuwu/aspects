package com.alfred.aspects.mixin;

import com.alfred.aspects.AspectsMod;
import com.alfred.aspects.enchantments.GoldenAspectEnchantment;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow public abstract ItemStack getStack();

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void modifyItemDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (AspectsMod.hasEnchantment(this.getStack(), GoldenAspectEnchantment.class))
            cir.setReturnValue(false);
    }

    @Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
    private void modifyItemOnFire(CallbackInfoReturnable<Boolean> cir) {
        if (AspectsMod.hasEnchantment(this.getStack(), GoldenAspectEnchantment.class))
            cir.setReturnValue(true);
    }
}
