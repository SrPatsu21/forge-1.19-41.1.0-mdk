package com.github.uranus_mod_group.uranus_mod.networking;

import com.github.uranus_mod_group.uranus_mod.networking.packet.AddManaC2SPacket;
import com.github.uranus_mod_group.uranus_mod.networking.packet.FirstSpellC2SPacket;
import com.github.uranus_mod_group.uranus_mod.networking.packet.SubManaC2SPacket;
import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packerId = 0;
    //add 1 for the Message ID for every sent message
    private static int id(){
        return packerId++;
    }

    //register the massages
    public static void register(){
        //create the chanel
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Uranus_mod.ModId, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        INSTANCE = net;
        //message to player of sub mana came from ManaC2SPacket
        net.messageBuilder(SubManaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SubManaC2SPacket::new)
                .encoder(SubManaC2SPacket::toBytes)
                .consumerMainThread(SubManaC2SPacket::handle)
                .add();
        //message to player of add mana came from ManaC2SPacket
        net.messageBuilder(AddManaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AddManaC2SPacket::new)
                .encoder(AddManaC2SPacket::toBytes)
                .consumerMainThread(AddManaC2SPacket::handle)
                .add();

        //message to ? of ? came from ManaDataSyncS2CPacket
        net.messageBuilder(ManaDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ManaDataSyncS2CPacket::new)
                .encoder(ManaDataSyncS2CPacket::toBytes)
                .consumerMainThread(ManaDataSyncS2CPacket::handle)
                .add();
        //fist spell
        net.messageBuilder(FirstSpellC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FirstSpellC2SPacket::new)
                .encoder(FirstSpellC2SPacket::toBytes)
                .consumerMainThread(FirstSpellC2SPacket::handle)
                .add();
    }

    //send the message to the server
    public static <MSG> void sendToServer(MSG message){
        INSTANCE.sendToServer(message);
    }

    //send the message to the player
    public static <MSG> void sendToPlayer (MSG message, ServerPlayer player){
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
