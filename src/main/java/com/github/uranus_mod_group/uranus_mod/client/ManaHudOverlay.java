package com.github.uranus_mod_group.uranus_mod.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerMana;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {
    private static final ResourceLocation MANA_BAR_MANA = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_mana-120x26.png");
    private static final ResourceLocation MANA_BAR_STRUCTURE = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_structure-120x26.png");

    private static final ResourceLocation MANA_BAR_BACKGROUND = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_background-120x26.png");

    private static final ResourceLocation MANA_BAR_MARCADORES = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_marcadores-120x26.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        int x = width/3;
        int y = height;
        int tam_bar_x = 120;
        int tam_bar_y = 26;

        RenderSystem.setShaderTexture(0, MANA_BAR_BACKGROUND);
        GuiComponent.blit(poseStack,x - tam_bar_x, y - tam_bar_y,0,0, tam_bar_x,tam_bar_y,
                tam_bar_x,tam_bar_y);
        //filled mana

        PlayerMana mana = new PlayerMana();
        RenderSystem.setShaderTexture(0, MANA_BAR_MANA);
        int size = (int) (ClientManaData.getPlayerMana()*tam_bar_x)/mana.getMaxMana();
        GuiComponent.blit(poseStack,x - tam_bar_x, y - tam_bar_y,0,0, size,tam_bar_y,
                tam_bar_x,tam_bar_y);

        RenderSystem.setShaderTexture(0, MANA_BAR_MARCADORES);
        GuiComponent.blit(poseStack,x - tam_bar_x, y - tam_bar_y,0,0, tam_bar_x,tam_bar_y,
                tam_bar_x,tam_bar_y);

        //empty mana
        RenderSystem.setShaderTexture(0, MANA_BAR_STRUCTURE);
        GuiComponent.blit(poseStack,x - tam_bar_x, y - tam_bar_y,0,0, tam_bar_x,tam_bar_y,
                tam_bar_x,tam_bar_y);
    });
}
