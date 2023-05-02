package com.github.uranus_mod_group.uranus_mod.networking.packet;

import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ManaC2SPacket {
    private static final String MESSAGE_DRINK_WATER = "message.uranus_mod.drink_water";
    private static final String MESSAGE_NO_WATER = "message.uranus_mod.no_water";

    public ManaC2SPacket() {

    }

    public ManaC2SPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            ServerPlayer player = context.getSender();
            ServerLevel level = player.getLevel();

            if(hasWaterAroundThem(player, level, 2)) {
                // Notify the player that water has been drunk
                player.sendSystemMessage(Component.translatable(MESSAGE_DRINK_WATER).withStyle(ChatFormatting.DARK_AQUA));
                // play the drinking sound
                level.playSound(null, player.getOnPos(), SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS,
                        0.5F, level.random.nextFloat() * 0.1F + 0.9F);
                player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->{
                    mana.subMana(1);
                    player.sendSystemMessage(Component.literal("mana atual"+ mana.getMana())
                            .withStyle(ChatFormatting.DARK_AQUA));
                    ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()),player);
                        });
                // increase the water level / thirst level of player
                // Output the current thirst level

            } else {
                // Notify the player that there is no water around!
                player.sendSystemMessage(Component.translatable(MESSAGE_NO_WATER).withStyle(ChatFormatting.RED));
                // Output the current thirst level
                player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->{
                    mana.subMana(1);
                    player.sendSystemMessage(Component.literal("mana atual"+ mana.getMana())
                            .withStyle(ChatFormatting.DARK_AQUA));
                    ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana()),player);
                });
            }
        });
        return true;
    }

    private boolean hasWaterAroundThem(ServerPlayer player, ServerLevel level, int size) {
        return level.getBlockStates(player.getBoundingBox().inflate(size))
                .filter(state -> state.is(Blocks.WATER)).toArray().length > 0;
    }
}