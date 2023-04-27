package com.github.uranus_mod_group.uranus_mod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_CATEGORY_URANUS =  "key.category.uranus_mod.uranus";
    public static final String KEY_SKILLS_1 = "key.uranus_mod.skills_1";
    public static final String KEY_SKILLS_2 = "key.uranus_mod.skills_2";
    public static final String KEY_SKILLS_3 = "key.uranus_mod.skills_3";


    public static final KeyMapping SKILLS_1_KEY = new KeyMapping(KEY_SKILLS_1, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_URANUS);
    public static final KeyMapping SKILLS_2_KEY = new KeyMapping(KEY_SKILLS_2, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_URANUS);
    public static final KeyMapping SKILLS_3_KEY = new KeyMapping(KEY_SKILLS_3, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, KEY_CATEGORY_URANUS);
}
