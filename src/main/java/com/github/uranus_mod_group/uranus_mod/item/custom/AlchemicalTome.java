package com.github.uranus_mod_group.uranus_mod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AlchemicalTome extends Item {
    public AlchemicalTome(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && interactionHand == InteractionHand.MAIN_HAND) {
            outputMSG(player);
            player.getCooldowns().addCooldown(this, 1);
        }
        if (interactionHand == InteractionHand.OFF_HAND) {
            player.kill();
        }
        return super.use(level, player, interactionHand);
    }

    private void outputMSG(Player player) {
        player.sendSystemMessage(Component.literal("a "));
    }
}
