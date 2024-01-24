package com.alfred.aspects.mixin;

import com.alfred.aspects.AspectsMod;
import com.alfred.aspects.enchantments.WitherAspectEnchantment;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

// alternative to this could be to just set WitherAspectEnchantment to be a curse and override its getName() function to not apply the red
// I'll leave this in for now but I might remove it later
@Mixin(GrindstoneScreenHandler.class)
public class GrindstoneScreenHandlerMixin {
    @Final @Shadow private Inventory result;
    @Final @Shadow Inventory input;

    @Inject(method = "<init>(ILnet/minecraft/entity/player/PlayerInventory;Lnet/minecraft/screen/ScreenHandlerContext;)V", at = @At("RETURN"))
    private void modifySlots(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context, CallbackInfo ci) {
        // kinda bad mixin, but I haven't seen a mod modify grindstone
        ((GrindstoneScreenHandler) (Object) this).slots.set(3, new Slot(this.result, 2, 129, 34) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                context.run((world, pos) -> {
                    if (world instanceof ServerWorld)
                        ExperienceOrbEntity.spawn((ServerWorld)world, Vec3d.ofCenter(pos), this.getExperience(world));

                    world.syncWorldEvent(1042, pos, 0);
                });
                GrindstoneScreenHandlerMixin.this.input.setStack(0, ItemStack.EMPTY);
                GrindstoneScreenHandlerMixin.this.input.setStack(1, ItemStack.EMPTY);
            }

            private int getExperience(World world) {
                int i = 0;
                i += this.getExperience(GrindstoneScreenHandlerMixin.this.input.getStack(0));
                i += this.getExperience(GrindstoneScreenHandlerMixin.this.input.getStack(1));
                if (i > 0) {
                    int j = (int) Math.ceil((double)i / 2.0);
                    return j + world.random.nextInt(j);
                } else {
                    return 0;
                }
            }

            private int getExperience(ItemStack stack) {
                int i = 0;
                Map<Enchantment, Integer> map = EnchantmentHelper.get(stack);

                for (Map.Entry<Enchantment, Integer> enchantmentIntegerEntry : map.entrySet()) {
                    Enchantment enchantment = enchantmentIntegerEntry.getKey();
                    Integer integer = enchantmentIntegerEntry.getValue();
                    if (!enchantment.isCursed() && !(enchantment instanceof WitherAspectEnchantment))
                        i += enchantment.getMinPower(integer);
                }

                return i;
            }
        });
    }

    @ModifyVariable(method = "grind", at = @At("STORE"), index = 5)
    private Map<Enchantment, Integer> isEnchantmentCursed(Map<Enchantment, Integer> map, @Local(ordinal = 0) ItemStack item) {
        if (AspectsMod.hasEnchantment(item, WitherAspectEnchantment.class))
            map.put(AspectsMod.WITHER_ASPECT, AspectsMod.getEnchantmentLevel(item, WitherAspectEnchantment.class));
        return map;
    }
}
