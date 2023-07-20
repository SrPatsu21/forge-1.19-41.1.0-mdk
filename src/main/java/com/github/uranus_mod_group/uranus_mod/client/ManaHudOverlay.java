package com.github.uranus_mod_group.uranus_mod.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerMana;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.ForgeConfigSpec;

public class ManaHudOverlay extends GuiComponent{
    private static final ResourceLocation MANA_BAR_MANA = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_mana-99x23.png");
    private static final ResourceLocation MANA_BAR_STRUCTURE = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_structure-101x23.png");
    private static final ResourceLocation MANA_BAR_BACKGROUND = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_background-101x23.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        PlayerMana mana = new PlayerMana();
        /*
        if (mana.getMaxMana() == 0) {
            return;
        }
        */

        int manaBarLength = 101;
        int manaBarPosX = 10;
        int manaBarPosY = height - (23 + 5);
        System.out.println("manaBarPosX - "+manaBarPosX);
        System.out.println("manaBarPosY - "+manaBarPosY);
        // background
        RenderSystem.setShaderTexture(0, MANA_BAR_BACKGROUND);
        GuiComponent.blit(poseStack, manaBarPosX, manaBarPosY, 0, 0, 101, 23, 101, 23);

        // value mana
        String manaValue = Integer.toString(ClientManaData.getPlayerMana());
        GuiComponent.drawString(poseStack, Minecraft.getInstance().font, manaValue, manaBarPosX, manaBarPosY-10, 0x1B82AB);

        // mana
        RenderSystem.setShaderTexture(0,MANA_BAR_MANA);
        GuiComponent.blit(poseStack, manaBarPosX + 2, manaBarPosY, 0, 0, 99, 23, 99, 23);

        // stucture (details)
        RenderSystem.setShaderTexture(0, MANA_BAR_STRUCTURE);
        GuiComponent.blit(poseStack, manaBarPosX, manaBarPosY, 0, 0, 101, 23, 101, 23);

    });

}
