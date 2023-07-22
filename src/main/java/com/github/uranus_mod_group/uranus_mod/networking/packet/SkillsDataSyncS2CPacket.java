package com.github.uranus_mod_group.uranus_mod.networking.packet;

import com.github.uranus_mod_group.uranus_mod.client.ClientSkillsData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillsDataSyncS2CPacket
{
    private byte[] skills_level;
    private int[] skill_xp;

    //this mana will be that mana
    public SkillsDataSyncS2CPacket(byte [] skills_level, int [] skill_xp)
    {
        this.skills_level = skills_level;
        this.skill_xp = skill_xp;
    }

    //this mana = buf double
    public SkillsDataSyncS2CPacket(FriendlyByteBuf buf)
    {
        this.skills_level = buf.readByteArray();
        this.skill_xp = buf.readVarIntArray();
    }

    //the buf write mana
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeByteArray(skills_level);
        buf.writeVarIntArray(skill_xp);
    }

    //send to ClientManaData
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            // HERE WE ARE ON THE CLIENT!
            ClientSkillsData.set(skills_level, skill_xp);
        });
        return true;
    }
}
