package com.github.uranus_mod_group.uranus_mod.entity;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.entity.custom.MagicSphereEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Uranus_mod.ModId);

    //magic sphere
    //create the registry object
    public static final RegistryObject<EntityType<MagicSphereEntity>> MAGIC_SPHERE =
            ENTITY_TYPES.register("magic_sphere", () -> EntityType.Builder.of((EntityType.EntityFactory<MagicSphereEntity>) MagicSphereEntity::new, MobCategory.MISC)
                            //hit box
                            .sized(0.5f, 0.5f)
                            //I think it is to find teh texture
                            //.build("magic_sphere"));
                            .build(new ResourceLocation(Uranus_mod.ModId, "magic_sphere").toString()));

    //registry in bus
    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}

