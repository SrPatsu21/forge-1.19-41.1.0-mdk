package com.github.uranus_mod_group.uranus_mod.networking.packet;

import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FirstSpellC2SPacket {

    public FirstSpellC2SPacket() {

    }

    public FirstSpellC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->{
                mana.subMana(20);
                //send a message with the current mana
                player.sendSystemMessage(Component.literal("Fist spell; current mana "+ mana.getMana())
                        .withStyle(ChatFormatting.AQUA));
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()),player);
            });
        });
        return true;
    }
}