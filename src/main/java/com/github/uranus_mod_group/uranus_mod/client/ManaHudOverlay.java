package com.github.uranus_mod_group.uranus_mod.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerMana;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {
    private static final ResourceLocation MANA_BAR_MANA = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_mana-99x23.png");
    private static final ResourceLocation MANA_BAR_STRUCTURE = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_structure-101x23.png");

    private static final ResourceLocation MANA_BAR_BACKGROUND = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_background-101x23.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        PlayerMana mana = new PlayerMana();
        if (mana.getMaxMana() == 0)
        {
            return;
        }

        int x = width/3;
        int y = height;
        int tamBarX = 101;
        int tamBarY = 23;
        int tamManaX = 99;
        int tamManaY = 23;
        int size = (int) (ClientManaData.getPlayerMana()*tamManaX)/mana.getMaxMana();

        /*
        GuiComponent.blit(
            poseStack -> indica que vai ser em matriz,
            posicao inicial em x,
            posicao inicial em y,
            naosei,
            naosei,
            tamanho em x,
            tamanho em y,
            naosei,
            naosei
        )
         */

        //background
        RenderSystem.setShaderTexture(0, MANA_BAR_BACKGROUND);
        GuiComponent.blit(poseStack,x - (tamBarX + 20), y - (tamBarY + 20),0,0, tamBarX,tamBarY,
                tamBarX,tamBarY);

        //mana
        RenderSystem.setShaderTexture(0, MANA_BAR_MANA);
        GuiComponent.blit(poseStack,x - (tamManaX + 1 + 20), y - (tamBarY + 20),0,0, size,tamBarY,
                tamBarX,tamBarY);

        //mana value bellow mana_bar
        Minecraft minecraft = Minecraft.getInstance();
        String textMana = (int) ClientManaData.getPlayerMana() + " / " + mana.getMaxMana();
        GuiComponent.drawString(poseStack, minecraft.font, textMana, x - (tamManaX), y - 10, 0x1B82AB);

        //structure (details)
        RenderSystem.setShaderTexture(0, MANA_BAR_STRUCTURE);
        GuiComponent.blit(poseStack,x - (tamBarX + 20), y - (tamBarY + 20),0,0, tamBarX,tamBarY,
                tamBarX,tamBarY);

    });
}
