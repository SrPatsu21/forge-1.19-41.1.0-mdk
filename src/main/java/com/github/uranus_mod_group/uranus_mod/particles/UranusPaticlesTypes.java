package com.github.uranus_mod_group.uranus_mod.particles;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UranusPaticlesTypes
{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Uranus_mod.ModId);

    public static final RegistryObject<SimpleParticleType> AUTO_ADD_PARTICLES =
            PARTICLE_TYPES.register("auto_add_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
