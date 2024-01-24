package com.alfred.aspects.mixin;

import com.alfred.aspects.AspectsMod;
import com.alfred.aspects.enchantments.PoisonAspectEnchantment;
import com.alfred.aspects.enchantments.WitherAspectEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
    @Shadow @Final private Property levelCost;

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @Inject(method = "updateResult", at = @At("HEAD"), cancellable = true)
    private void modifyResult(CallbackInfo ci) {
        ItemStack input = this.input.getStack(0);
        ItemStack combined = this.input.getStack(1);
        ItemStack output = input.copy();

        if (!input.isOf(Items.ENCHANTED_BOOK) && !combined.isEmpty() && combined.isOf(Items.NETHER_STAR) && AspectsMod.hasEnchantment(input, PoisonAspectEnchantment.class)) {
            int enchantmentLevel = AspectsMod.getEnchantmentLevel(input, PoisonAspectEnchantment.class);
            AspectsMod.removeEnchantment(output, PoisonAspectEnchantment.class);
            output.addEnchantment(AspectsMod.WITHER_ASPECT, enchantmentLevel);
            this.output.setStack(0, output);
            this.levelCost.set(10);
            ci.cancel();
        }
    }
}
