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
    public static final RenderType RENDER_TYPE = RenderType.entityDecal(MAGIC_SPHERE_LOCATION);
    public final MagicSphereModel<MagicSphereEntity> model;

    public MagicSphereRender(EntityRendererProvider.Context manager)
    {
        super(manager);
        this.model = new MagicSphereModel<>(manager.bakeLayer(MagicSphereModel.LAYER_LOCATION));
    }

    protected int getBlockLightLevel(MagicSphereEntity entity, BlockPos position)
    {
        return 15;
    }

    public void render(MagicSphereEntity entity, float p_115863_, float p_115864_, PoseStack poseStack,
                       MultiBufferSource bufferSource, int p_115867_) {
        poseStack.pushPose();
        //animation
        float f = Mth.rotlerp(entity.yRotO, entity.getYRot(), p_115864_);
        float f1 = Mth.lerp(p_115864_, entity.xRotO, entity.getXRot());
        float f2 = (float)entity.tickCount + p_115864_;
        poseStack.translate(0.0D, (double)0.15F, 0.0D);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.sin(f2 * 0.1F) * 180.0F));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(Mth.cos(f2 * 0.1F) * 180.0F));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(f2 * 0.15F) * 360.0F));
        poseStack.scale(-1F, -1F, 0.5F);
        this.model.setupAnim(entity, 0.0F, 0.0F, 0.0F, f, f1);
        //texture
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(MAGIC_SPHERE_LOCATION));
        //
        this.model.renderToBuffer(poseStack, vertexconsumer, p_115867_,
                OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        //escale
        poseStack.scale(2F, 2F, 2F);
        //
        VertexConsumer vertexconsumer1 = bufferSource.getBuffer(RENDER_TYPE);
        //render
        this.model.renderToBuffer(poseStack, vertexconsumer1, p_115867_, OverlayTexture.NO_OVERLAY
                , 1.0F, 1.0F, 1.0F, 0.15F);
        //set poseStack to ".removeLast()" ???
        poseStack.popPose();
        //call the superrender
        super.render(entity, p_115863_, p_115864_, poseStack, bufferSource, p_115867_);
    }

    public ResourceLocation getTextureLocation(MagicSphereEntity Projectile)
    {
        return MAGIC_SPHERE_LOCATION;
    }
}
