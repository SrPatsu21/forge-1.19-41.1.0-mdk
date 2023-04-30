package com.github.uranus_mod_group.uranus_mod;

import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.block.ModBlocks;
import com.github.uranus_mod_group.uranus_mod.item.ModItems;
import com.github.uranus_mod_group.uranus_mod.villager.ModVillagers;
import com.github.uranus_mod_group.uranus_mod.world.feature.ModConfiguredFeatures;
import com.github.uranus_mod_group.uranus_mod.world.feature.ModPlacedFeatures;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Uranus_mod.ModId)
public class Uranus_mod {
    public static final String ModId = "uranus_mod";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Uranus_mod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        //eventBus
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModConfiguredFeatures.register(modEventBus);
        ModPlacedFeatures.register(modEventBus);
        ModVillagers.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModVillagers.registerPOis();
        });

        ModMessages.register();
    }


    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
