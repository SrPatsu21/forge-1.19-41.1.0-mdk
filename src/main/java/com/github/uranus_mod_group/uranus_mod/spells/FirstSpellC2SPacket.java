package com.github.uranus_mod_group.uranus_mod.spells;

import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.projectile.Arrow;
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
                    /*
                    BlockPos block_pos = new BlockPos(player.getX(), player.getY(), player.getBlockZ());
                    ModEntityTypes.MAGIC_SPHERE.get().spawn(level,null, player, block_pos
                            , MobSpawnType.EVENT, true, true);
                     */
                        MagicSphereEntity magic_sphere = new Spells().createMagicSphere(level, player);

                        level.addFreshEntity(magic_sphere);

                }
                //manass
                mana.subMana(1);
                //send a message with the current mana
                player.sendSystemMessage(Component.literal("Fist spell; current mana "+ mana.getMana())
                        .withStyle(ChatFormatting.AQUA));
                ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMaxMana()),player);
            });
        });
        return true;
    }
}