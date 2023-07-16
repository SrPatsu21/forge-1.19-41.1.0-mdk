package com.github.uranus_mod_group.uranus_mod.entity.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.entity.custom.MagicSphereEntity;
import com.github.uranus_mod_group.uranus_mod.entity.model.MagicSphereModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MagicSphereRender extends EntityRenderer<MagicSphereEntity>
{
    public static final ResourceLocation MAGIC_SPHERE_LOCATION = new ResourceLocation(
            Uranus_mod.ModId, "textures/entity/spells/magic_sphere.png");
    public static final RenderType RENDER_TYPE = RenderType.entityTranslucent(MAGIC_SPHERE_LOCATION);
    public final MagicSphereModel<MagicSphereEntity> model;

    public MagicSphereRender(EntityRendererProvider.Context manager)
    {
        super(manager);
        this.model = new MagicSphereModel<>(manager.bakeLayer(MagicSphereModel.LAYER_LOCATION));
    }

    protected int getBlockLightLevel(MagicSphereEntity entity, BlockPos position) {
        return 15;
    }

    public void render(MagicSphereEntity entity, float p_115863_, float p_115864_, PoseStack p_115865_, MultiBufferSource p_115866_, int p_115867_) {
        p_115865_.pushPose();
        float f = Mth.rotlerp(entity.yRotO, entity.getYRot(), p_115864_);
        float f1 = Mth.lerp(p_115864_, entity.xRotO, entity.getXRot());
        float f2 = (float)entity.tickCount + p_115864_;
        p_115865_.translate(0.0D, (double)0.15F, 0.0D);
        p_115865_.mulPose(Vector3f.YP.rotationDegrees(Mth.sin(f2 * 0.1F) * 180.0F));
        p_115865_.mulPose(Vector3f.XP.rotationDegrees(Mth.cos(f2 * 0.1F) * 180.0F));
        p_115865_.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(f2 * 0.15F) * 360.0F));
        p_115865_.scale(-0.5F, -0.5F, 0.5F);
        this.model.setupAnim(entity, 0.0F, 0.0F, 0.0F, f, f1);
        VertexConsumer vertexconsumer = p_115866_.getBuffer(this.model.renderType(MAGIC_SPHERE_LOCATION));
        this.model.renderToBuffer(p_115865_, vertexconsumer, p_115867_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        p_115865_.scale(1.5F, 1.5F, 1.5F);
        VertexConsumer vertexconsumer1 = p_115866_.getBuffer(RENDER_TYPE);
        this.model.renderToBuffer(p_115865_, vertexconsumer1, p_115867_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.15F);
        p_115865_.popPose();
        super.render(entity, p_115863_, p_115864_, p_115865_, p_115866_, p_115867_);
    }

    public ResourceLocation getTextureLocation(MagicSphereEntity Projectile)
    {
        return MAGIC_SPHERE_LOCATION;
    }
}
