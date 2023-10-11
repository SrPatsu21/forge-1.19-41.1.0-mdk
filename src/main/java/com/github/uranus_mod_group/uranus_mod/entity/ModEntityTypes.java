package com.github.uranus_mod_group.uranus_mod.entity;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicLaserEntity;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import com.github.uranus_mod_group.uranus_mod.entity.custom.MasterWizardEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes{
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Uranus_mod.ModId);

    //create the registry object
    //magic sphere
    public static RegistryObject<EntityType<MagicSphereEntity>> MAGIC_SPHERE =
            ENTITY_TYPES.register("magic_sphere", () -> EntityType.Builder.<MagicSphereEntity>of(MagicSphereEntity::new, MobCategory.MISC)
                            //hit box
                            .sized(0.4f, 0.4f)
                            //I think it is to find teh texture
                            .build(new ResourceLocation(Uranus_mod.ModId, "magic_sphere").toString())
            );
    //magic laser
    public static RegistryObject<EntityType<MagicLaserEntity>> MAGIC_LASER =
            ENTITY_TYPES.register("magic_laser", () -> EntityType.Builder.<MagicLaserEntity>of(MagicLaserEntity::new, MobCategory.MISC)
                    //hit box
                    .sized(0.4f, 0.4f)
                    //I think it is to find teh texture
                    .build(new ResourceLocation(Uranus_mod.ModId, "magic_laser").toString())
            );
    //master wizard
    public  static final RegistryObject<EntityType<MasterWizardEntity>> MASTER_WIZARD =
            ENTITY_TYPES.register("master_wizard", () -> EntityType.Builder.of(MasterWizardEntity::new, MobCategory.MONSTER)
                    //hit box
                    .sized(1f, 2f)
                    //texture
                    .build(new ResourceLocation(Uranus_mod.ModId, "master_wizard").toString())
            );

    //registry in bus
    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}

