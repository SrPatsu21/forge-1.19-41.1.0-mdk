package com.github.uranus_mod_group.uranus_mod.entity.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.entity.custom.MasterWizardEntity;
import com.github.uranus_mod_group.uranus_mod.entity.model.MasterWizardModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MasterWizardRender extends MobRenderer<MasterWizardEntity, MasterWizardModel>
{
    private static final ResourceLocation MASTER_WIZARD_LOCATION = new ResourceLocation(
            Uranus_mod.ModId, "textures/entity/master_wizard.png");

    public MasterWizardRender(EntityRendererProvider.Context ctx)
    {
        super(ctx, new MasterWizardModel(ctx.bakeLayer(MasterWizardModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(MasterWizardEntity entity)
    {
        return MASTER_WIZARD_LOCATION;
    }
}
