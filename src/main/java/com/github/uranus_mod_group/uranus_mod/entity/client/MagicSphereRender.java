package com.github.uranus_mod_group.uranus_mod.entity.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.entity.custom.MagicSphereEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public abstract class MagicSphereRender extends ArrowRenderer<MagicSphereEntity>
{
    public static final ResourceLocation MAGIC_SPHERE_LOCATION = new ResourceLocation(Uranus_mod.ModId, "textures/entity/spells/magic_sphere_texture.png");

    public MagicSphereRender(EntityRendererProvider.Context manager)
    {
        super(manager);
    }

    public ResourceLocation getTextureLocation(MagicSphereEntity arrow)
    {
        return MAGIC_SPHERE_LOCATION;
    }
}
