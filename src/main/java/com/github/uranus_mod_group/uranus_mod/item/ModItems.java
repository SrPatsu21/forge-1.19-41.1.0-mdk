package com.github.uranus_mod_group.uranus_mod.item;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Uranus_mod.ModId);

    public static final RegistryObject<Item> mana_gem = ITEMS.register("mana_gem", () -> new Item(
            new Item.Properties()
                    
    ));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
