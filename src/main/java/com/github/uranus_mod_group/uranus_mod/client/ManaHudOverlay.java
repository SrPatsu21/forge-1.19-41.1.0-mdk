package com.github.uranus_mod_group.uranus_mod.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerMana;
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
        int x = width/2;
        int y = height;

        RenderSystem.setShaderTexture(0, MANA_BAR_BACKGROUND);
        GuiComponent.blit(poseStack,x - 210, y - 16,0,0, 240,26,
                240,26);
        //filled mana

        PlayerMana mana = new PlayerMana();
        RenderSystem.setShaderTexture(0, MANA_BAR_MANA);
        int size = (int) (ClientManaData.getPlayerMana()*240)/mana.getMaxMana();
        GuiComponent.blit(poseStack,x - 210, y - 16,0,0, size,26,
                size,26);

        RenderSystem.setShaderTexture(0, MANA_BAR_MARCADORES);
        GuiComponent.blit(poseStack,x - 210, y - 16,0,0, 240,26,
                240,26);

        //empty mana
        RenderSystem.setShaderTexture(0, MANA_BAR_STRUCTURE);
        GuiComponent.blit(poseStack,x - 210, y - 16,0,0, 240,26,
                240,26);
    });
}
