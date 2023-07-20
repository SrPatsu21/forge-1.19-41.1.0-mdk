package com.github.uranus_mod_group.uranus_mod.networking.packet;

import com.github.uranus_mod_group.uranus_mod.client.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaDataSyncS2CPacket {
    private double mana;

    //this mana will be that mana
    public ManaDataSyncS2CPacket(double mana)
    {
        this.mana = mana;
    }

    //this mana = buf double
    public ManaDataSyncS2CPacket(FriendlyByteBuf buf)
    {
        this.mana = buf.readDouble();
    }

    //the buf write mana
    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeDouble(mana);
    }

    //send to ClientManaData
    public boolean handle(Supplier<NetworkEvent.Context> supplier)
    {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() ->
        {
            // HERE WE ARE ON THE CLIENT!
            ClientManaData.set(mana);
        });
        return true;
    }
}