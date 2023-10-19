package com.github.uranus_mod_group.uranus_mod.skills;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FirstSpellC2SPacket
{
    private byte[] skill_attributes =
    {
        0,
        0,
        0,
        30,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
    };
    private int skill_kind = 2;
//    private byte[] skill_attributes =
//    {
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21,
//        21
//    };

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
        new CreateSkills(context, skill_kind, this.skill_attributes).createSkill();
        return true;
    }
}