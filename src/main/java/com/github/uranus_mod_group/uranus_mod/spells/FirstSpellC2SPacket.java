package com.github.uranus_mod_group.uranus_mod.spells;

import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import com.github.uranus_mod_group.uranus_mod.skills.CreateSkills;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

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
    /*
    NetworkEvent.Context context = supplier.get();
        new CreateSkills(context, 1, null).createSkill(3.0F);
        return true;
    */
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        new CreateSkills(context, 1, null).createSkill(3.0F);
        return true;
    }
}