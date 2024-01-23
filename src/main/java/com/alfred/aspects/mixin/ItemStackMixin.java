package com.alfred.aspects.mixin;

import com.alfred.aspects.AspectsMod;
import com.alfred.aspects.enchantments.GoldenAspectEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract NbtList getEnchantments();

    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    private void cancelDurabilityDamage(CallbackInfoReturnable<Boolean> cir) {
        if (AspectsMod.hasEnchantment(((ItemStack) (Object) this), GoldenAspectEnchantment.class))
            cir.setReturnValue(false);
    }
}
