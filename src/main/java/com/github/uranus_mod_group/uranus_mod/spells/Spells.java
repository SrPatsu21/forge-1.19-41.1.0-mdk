package com.github.uranus_mod_group.uranus_mod.spells;

import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;


public class Spells {
    public static MagicSphereEntity CreateMagicSphere(Level level, LivingEntity entity){
        MagicSphereEntity magicSphere = new MagicSphereEntity(ModEntityTypes.MAGIC_SPHERE.get() ,level, entity);
        return magicSphere;
    }
}
