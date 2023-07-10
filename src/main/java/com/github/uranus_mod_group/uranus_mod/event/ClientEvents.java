package com.github.uranus_mod_group.uranus_mod.event;

import com.github.uranus_mod_group.uranus_mod.client.ManaHudOverlay;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.networking.packet.AddManaC2SPacket;
import com.github.uranus_mod_group.uranus_mod.networking.packet.spells.FirstSpellC2SPacket;
import com.github.uranus_mod_group.uranus_mod.networking.packet.SubManaC2SPacket;
import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class ClientEvents {
    @Mod.EventBusSubscriber(modid = Uranus_mod.ModId, value = Dist.CLIENT)
    public static class ClientForgeEvents
    {
        @SubscribeEvent
        //key events
        public static void onKeyInput(InputEvent.Key event)
        {
            if(KeyBinding.SKILLS_1_KEY.consumeClick())
            {
                //this is now to sub mana
                ModMessages.sendToServer(new SubManaC2SPacket());
            }
            if(KeyBinding.SKILLS_2_KEY.consumeClick())
            {
                //fist spell
                ModMessages.sendToServer(new FirstSpellC2SPacket());
            }
            if(KeyBinding.SKILLS_3_KEY.consumeClick())
            {
                ModMessages.sendToServer(new AddManaC2SPacket());
            }
        }

    }
    //register key
    @Mod.EventBusSubscriber(modid = Uranus_mod.ModId, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents
    {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event)
        {
            event.register(KeyBinding.SKILLS_1_KEY);
            event.register(KeyBinding.SKILLS_2_KEY);
            event.register(KeyBinding.SKILLS_3_KEY);
        }
        //I dont know now, I think it is mana hud
        @SubscribeEvent
        public static void registerGuiOverLays(RegisterGuiOverlaysEvent event)
        {
            event.registerAboveAll("mana", ManaHudOverlay.HUD_MANA);
        }
    }
}