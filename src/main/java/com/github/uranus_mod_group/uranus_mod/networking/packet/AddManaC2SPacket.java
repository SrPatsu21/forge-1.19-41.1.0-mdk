package com.github.uranus_mod_group.uranus_mod.networking.packet;

import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AddManaC2SPacket
{

    public AddManaC2SPacket()
    {

    }

    public AddManaC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->
            {
                mana.addMana(1);
                //send a message with the current mana
                player.sendSystemMessage(Component.literal("current mana "+ mana.getMana())
                        .withStyle(ChatFormatting.GREEN));
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()),player);
            });
        });
        return true;
    }
}