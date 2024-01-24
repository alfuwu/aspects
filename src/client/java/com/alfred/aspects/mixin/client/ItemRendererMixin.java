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
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import static net.minecraft.client.render.RenderLayer.of;
import static net.minecraft.client.render.RenderPhase.*;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    //@Unique private static final Identifier ENTITY_ENCHANTMENT_GLINT = new Identifier("aspects", "textures/misc/enchanted_glint_entity.png");
    //@Unique private static final Identifier ITEM_ENCHANTMENT_GLINT = new Identifier("aspects", "textures/misc/enchanted_glint_item.png");
    //@Unique private static final RenderLayer ARMOR_GLINT = of("golden_armor_glint", VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(ARMOR_GLINT_PROGRAM).texture(new RenderPhase.Texture(ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).layering(VIEW_OFFSET_Z_LAYERING).build(false));
    //@Unique private static final RenderLayer ARMOR_ENTITY_GLINT = of("golden_armor_entity_glint",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(ARMOR_ENTITY_GLINT_PROGRAM).texture(new RenderPhase.Texture(ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).layering(VIEW_OFFSET_Z_LAYERING).build(false));
    //@Unique private static final RenderLayer GLINT_TRANSLUCENT = of("golden_glint_translucent",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(TRANSLUCENT_GLINT_PROGRAM).texture(new RenderPhase.Texture(ITEM_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).target(ITEM_ENTITY_TARGET).build(false));
    //@Unique private static final RenderLayer GLINT = of("golden_glint",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(GLINT_PROGRAM).texture(new RenderPhase.Texture(ITEM_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
    //@Unique private static final RenderLayer DIRECT_GLINT = of("golden_glint_direct",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(DIRECT_GLINT_PROGRAM).texture(new RenderPhase.Texture(ITEM_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(GLINT_TEXTURING).build(false));
    //@Unique private static final RenderLayer ENTITY_GLINT = of("golden_entity_glint",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(ENTITY_GLINT_PROGRAM).texture(new RenderPhase.Texture(ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).target(ITEM_ENTITY_TARGET).texturing(ENTITY_GLINT_TEXTURING).build(false));
    //@Unique private static final RenderLayer DIRECT_ENTITY_GLINT = of("golden_entity_glint_direct",VertexFormats.POSITION_TEXTURE, VertexFormat.DrawMode.QUADS, 1536,RenderLayer.MultiPhaseParameters.builder().program(DIRECT_ENTITY_GLINT_PROGRAM).texture(new RenderPhase.Texture(ENTITY_ENCHANTMENT_GLINT, true, false)).writeMaskState(COLOR_MASK).cull(DISABLE_CULLING).depthTest(EQUAL_DEPTH_TEST).transparency(GLINT_TRANSPARENCY).texturing(ENTITY_GLINT_TEXTURING).build(false));

    @ModifyArg(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderBakedItemModel(Lnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/item/ItemStack;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;)V"))
    private VertexConsumer modifyItemGlint(VertexConsumer value, @Local ItemStack stack, @Local(ordinal = 0) boolean bl, @Local(ordinal = 1) boolean bl2, @Local VertexConsumerProvider vertexConsumers, @Local RenderLayer layer) {
        // so.
        // the way i'm creating my RenderLayers seems to not initialize BufferBuilder or something, causing it to crash when buffer builder is accessed
        // no clue how to fix, i'll leave this here i guess
        // RIP golden glint, woulda looked sick
        return value;
    }
}
