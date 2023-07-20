package com.github.uranus_mod_group.uranus_mod.spells;

import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;


public class Spells {
    public MagicSphereEntity createMagicSphere(Level level, LivingEntity entity){
        MagicSphereEntity magic_sphere = new MagicSphereEntity(ModEntityTypes.MAGIC_SPHERE.get() ,level, entity);
        magic_sphere.shootFromRotation(magic_sphere.getOwner(), magic_sphere.getOwner().getXRot(), magic_sphere.getOwner().getYRot(),
                0.0F, magic_sphere.getSpeed() * 3.0F, 0.0F);
        return magic_sphere;
    }
}

