package com.github.uranus_mod_group.uranus_mod.item.custom;

import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ManaController extends Item
{

    public ManaController(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand)
    {
        if (!level.isClientSide() && interactionHand == InteractionHand.MAIN_HAND)
        {
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->
            {
                mana.addML(1000);
                mana.manaStatsReset();
                player.sendSystemMessage(Component.literal("mana level:"+ mana.getMl()));
            });
        }
        if (interactionHand == InteractionHand.OFF_HAND)
        {
            player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->
            {
                mana.subMl(1);
                mana.manaStatsReset();
                player.sendSystemMessage(Component.literal("mana level:"+ mana.getMl()));
            });
        }
        return super.use(level, player, interactionHand);
    }

}
