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
        1,
        0
    };
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
        new CreateSkills(context, 1, this.skill_attributes).createSkill();
        return true;
    }
}