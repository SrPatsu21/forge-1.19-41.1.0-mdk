package com.github.uranus_mod_group.uranus_mod.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
    //make a creative tab for the mod
public class ModTab {
    public static final CreativeModeTab URANUS_TAB = new CreativeModeTab("uranus_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ALCHEMICAL_TOME.get());
        }
    };
}
