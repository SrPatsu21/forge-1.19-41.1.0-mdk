package com.github.uranus_mod_group.uranus_mod.entity.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicLaserEntity;
import com.github.uranus_mod_group.uranus_mod.entity.model.MagicLaserModel;
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

public class MagicLaserRender extends EntityRenderer<MagicLaserEntity>
{
    public static final ResourceLocation MAGIC_LASER_LOCATION = new ResourceLocation(
            Uranus_mod.ModId, "textures/entity/spells/magic_laser.png");
    public static final RenderType RENDER_TYPE = RenderType.entityDecal(MAGIC_LASER_LOCATION);
    public final MagicLaserModel<MagicLaserEntity> model;

    public MagicLaserRender(EntityRendererProvider.Context manager)
    {
        super(manager);
        this.model = new MagicLaserModel<>(manager.bakeLayer(MagicLaserModel.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(MagicLaserEntity p_114482_) {
        return MAGIC_LASER_LOCATION;
    }

    protected int getBlockLightLevel(MagicLaserEntity entity, BlockPos position)
    {
        return 15;
    }
    //how does it work?
    public void render(MagicLaserEntity entity, float p_115863_, float p_115864_, PoseStack poseStack,
                       MultiBufferSource bufferSource, int p_115867_)
    {
        poseStack.pushPose();
        //texture
        VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(MAGIC_LASER_LOCATION));
        this.model.renderToBuffer(poseStack, vertexconsumer, p_115867_,
                OverlayTexture.NO_OVERLAY, 0.0F, 0.4F, 0.6F, 1.0F);
        //scale
        poseStack.scale(2F, 2F, 2F);
        //
        VertexConsumer vertexconsumer1 = bufferSource.getBuffer(RENDER_TYPE);
        this.model.renderToBuffer(poseStack, vertexconsumer1, p_115867_, OverlayTexture.NO_OVERLAY
                , 0.0F, 0.0F, 0.0F, 0.0F);
        //set poseStack to ".removeLast()" ???
        poseStack.popPose();
        //call the superrender
        super.render(entity, p_115863_, p_115864_, poseStack, bufferSource, p_115867_);
    }
}
