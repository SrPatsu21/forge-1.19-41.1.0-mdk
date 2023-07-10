package com.github.uranus_mod_group.uranus_mod.spells;

import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.world.entity.MobSpawnType;


import java.util.function.Supplier;

public class FirstSpellC2SPacket
{

    public FirstSpellC2SPacket()
    {

    }

    public FirstSpellC2SPacket(FriendlyByteBuf buf)
    {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->
            {
                //magic effect
                if (mana.getMana() >= 20)
                {
                    EntityType.FIREBALL.spawn(level, null, null, (player.blockPosition()),
                            MobSpawnType.COMMAND, true, false);
                }
                //mana
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