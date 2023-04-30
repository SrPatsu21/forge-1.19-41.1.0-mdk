package com.github.uranus_mod_group.uranus_mod.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {
    private static final ResourceLocation FILLED_MANA = new ResourceLocation(Uranus_mod.ModId, "textures/mana/filled_mana.png");
    private static final ResourceLocation EMPTY_MANA = new ResourceLocation(Uranus_mod.ModId, "textures/mana/empty_mana.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_MANA);
        for(int i = 0; i < 10; i++) {
            GuiComponent.blit(poseStack,x - 94 + (i * 9), y - 54,0,0,16,16,
                    16,16);
        }

        //RenderSystem.setShaderTexture(0, FILLED_MANA);
        for(int i = 0; i < 8; i++) {
            if(ClientManaDATA.getPlayerMana() > i) {
            GuiComponent.blit(poseStack,x - 94 + (i * 9), y - 54,0,0,16,16,
                    16,16);
            } else {
                break;
            }
        }
    });
}
