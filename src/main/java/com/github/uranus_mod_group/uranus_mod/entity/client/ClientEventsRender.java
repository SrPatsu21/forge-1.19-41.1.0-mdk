package com.github.uranus_mod_group.uranus_mod.entity.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventsRender {

    @Mod.EventBusSubscriber(modid = Uranus_mod.ModId, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents
    {
        public class ClientSetup
        {
            //about texture of entity magic sphere
            @SubscribeEvent
            public static void doSetup(FMLClientSetupEvent event)
            {
                EntityRenderers.register(ModEntityTypes.MAGIC_SPHERE.get(), MagicSphereRender::new);
            }
        }
    }
}
