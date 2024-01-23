package com.alfred.aspects.mixin.client;

import com.alfred.aspects.AspectsMod;
import com.alfred.aspects.enchantments.GoldenAspectEnchantment;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.client.render.RenderLayer.of;
import static net.minecraft.client.render.RenderPhase.*;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @Unique private static final Identifier ENTITY_ENCHANTMENT_GLINT = new Identifier("aspects", "textures/misc/enchanted_glint_entity.png");
    @Unique private static final Identifier ITEM_ENCHANTMENT_GLINT = new Identifier("aspects", "textures/misc/enchanted_glint_item.png");
    @Unique private static final RenderLayer ARMOR_GLINT = of("golden_armor_glint", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(ARMOR_GLINT_PROGRAM).texture(new RenderPhase.Texture(ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).layering(VIEW_OFFSET_Z_LAYERING).build(false));
    @Unique private static final RenderLayer ARMOR_ENTITY_GLINT = of("golden_armor_entity_glint",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(ARMOR_ENTITY_GLINT_PROGRAM).texture(new RenderPhase.Texture(ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).layering(VIEW_OFFSET_Z_LAYERING).build(false));
    @Unique private static final RenderLayer GLINT_TRANSLUCENT = of("golden_glint_translucent",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(TRANSLUCENT_GLINT_PROGRAM).texture(new RenderPhase.Texture(ITEM_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).target(ITEM_ENTITY_TARGET).build(false));
    @Unique private static final RenderLayer GLINT = of("golden_glint",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(GLINT_PROGRAM).texture(new RenderPhase.Texture(ITEM_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
    @Unique private static final RenderLayer DIRECT_GLINT = of("golden_glint_direct",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(DIRECT_GLINT_PROGRAM).texture(new RenderPhase.Texture(ITEM_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
    @Unique private static final RenderLayer ENTITY_GLINT = of("golden_entity_glint",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(ENTITY_GLINT_PROGRAM).texture(new RenderPhase.Texture(ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).target(ITEM_ENTITY_TARGET).texturing(ENTITY_GLINT_TEXTURING).build(false));
    @Unique private static final RenderLayer DIRECT_ENTITY_GLINT = of("golden_entity_glint_direct",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(DIRECT_ENTITY_GLINT_PROGRAM).texture(new RenderPhase.Texture(ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).build(false));

    // why the hecc is this crashing
    // god why
    // help
    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer modifyItemGlint(VertexConsumerProvider vertexConsumers, RenderLayer layer, boolean solid, boolean glint, @Local ItemStack stack) {
        if (AspectsMod.hasEnchantment(stack, GoldenAspectEnchantment.class)) {
            if (glint) {
                return MinecraftClient.isFabulousGraphicsOrBetter() && layer == TexturedRenderLayers.getItemEntityTranslucentCull() ? VertexConsumers.union(vertexConsumers.getBuffer(GLINT_TRANSLUCENT), vertexConsumers.getBuffer(layer)) : VertexConsumers.union(vertexConsumers.getBuffer(solid ? GLINT : ENTITY_GLINT), vertexConsumers.getBuffer(layer));
            } else {
                return vertexConsumers.getBuffer(layer);
            }
        } else {
            return ItemRenderer.getItemGlintConsumer(vertexConsumers, layer, solid, glint);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectItemGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;ZZ)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer modifyDirectItemGlint(VertexConsumerProvider vertexConsumers, RenderLayer layer, boolean solid, boolean glint, @Local ItemStack stack) {
        if (AspectsMod.hasEnchantment(stack, GoldenAspectEnchantment.class)) {
            if (glint) {
                return MinecraftClient.isFabulousGraphicsOrBetter() && layer == TexturedRenderLayers.getItemEntityTranslucentCull() ? VertexConsumers.union(vertexConsumers.getBuffer(GLINT_TRANSLUCENT), vertexConsumers.getBuffer(layer)) : VertexConsumers.union(vertexConsumers.getBuffer(solid ? GLINT : ENTITY_GLINT), vertexConsumers.getBuffer(layer));
            } else {
                return vertexConsumers.getBuffer(layer);
            }
        } else {
            return ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, layer, solid, glint);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDynamicDisplayGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack$Entry;)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer modifyDynamicDisplayGlint(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry, @Local ItemStack stack) {
        if (AspectsMod.hasEnchantment(stack, GoldenAspectEnchantment.class)) {
            return VertexConsumers.union(new OverlayVertexConsumer(provider.getBuffer(GLINT), entry.getPositionMatrix(), entry.getNormalMatrix(), 0.0078125F), provider.getBuffer(layer));
        } else {
            return ItemRenderer.getDynamicDisplayGlintConsumer(provider, layer, entry);
        }
    }

    @Redirect(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getDirectDynamicDisplayGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/util/math/MatrixStack$Entry;)Lnet/minecraft/client/render/VertexConsumer;"))
    private VertexConsumer modifyDirectDynamicDisplayGlint(VertexConsumerProvider provider, RenderLayer layer, MatrixStack.Entry entry, @Local ItemStack stack) {
        if (AspectsMod.hasEnchantment(stack, GoldenAspectEnchantment.class)) {
            return VertexConsumers.union(new OverlayVertexConsumer(provider.getBuffer(DIRECT_GLINT), entry.getPositionMatrix(), entry.getNormalMatrix(), 0.0078125F), provider.getBuffer(layer));
        } else {
            return ItemRenderer.getDirectDynamicDisplayGlintConsumer(provider, layer, entry);
        }
    }
}
