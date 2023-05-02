package com.github.uranus_mod_group.uranus_mod.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {
    private static final ResourceLocation FILLED_MANA = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/filled_mana.png");
    private static final ResourceLocation EMPTY_MANA = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/empty_mana.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        int x = width / 2;
        int y = height;

        RenderSystem.setShaderTexture(0, EMPTY_MANA);
        for(int i = 0; i < 10; i++) {
            GuiComponent.blit(poseStack,x - 310 + (i * 9), y - 16,0,0,12,12,
                    12,12);
        }

        RenderSystem.setShaderTexture(0, FILLED_MANA);
        for(int i = 0; i < 10; i++) {
            if(ClientManaData.getPlayerMana() > i) {
            GuiComponent.blit(poseStack,x - 310 + (i * 9), y - 16,0,0,12,12,
                    12,12);
            } else {
                break;
            }
        }
    });
}
