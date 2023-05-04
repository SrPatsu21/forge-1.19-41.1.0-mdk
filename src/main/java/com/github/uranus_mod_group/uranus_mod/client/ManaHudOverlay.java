package com.github.uranus_mod_group.uranus_mod.client;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerMana;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaHudOverlay {
    private static final ResourceLocation MANA_BAR_CONTENT = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_content-56x8.png");
    private static final ResourceLocation MANA_BAR_STRUCTURE = new ResourceLocation(Uranus_mod.ModId,
            "textures/mana/mana_bar_structure-56x8.png");

    public static final IGuiOverlay HUD_MANA = ((gui, poseStack, partialTick, width, height) -> {
        //filled mana
        RenderSystem.setShaderTexture(0, MANA_BAR_CONTENT);
        PlayerMana pm = new PlayerMana();
        int MAX_MANA = pm.getMAX_MANA();

        for(int i = 0; i < 56; i++) {

            if(ClientManaData.getPlayerMana() > i) {
                GuiComponent.blit(poseStack,width - 620, height - 16,0,0,1+i,8,
                        1+i,8);
            } else {
                break;
            }
        }
        //empty mana
        RenderSystem.setShaderTexture(0, MANA_BAR_STRUCTURE);
            GuiComponent.blit(poseStack,width - 620, height - 16,0,0,56,8,
                    56,8);
    });
}
