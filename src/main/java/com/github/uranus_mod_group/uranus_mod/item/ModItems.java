package com.github.uranus_mod_group.uranus_mod.item;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;

import com.github.uranus_mod_group.uranus_mod.item.custom.AlchemicalTome;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.print.Book;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Uranus_mod.ModId);

    // items
    //mana gem
    public static final RegistryObject<Item> MANA_GEM = ITEMS.register("mana_gem",
            () -> new Item(new Item.Properties()
                    .tab(ModTab.URANUS_TAB)
                    .food(new FoodProperties.Builder().nutrition(6).saturationMod(6).meat().alwaysEat().effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200, 3), 1.0f).build())
                    .stacksTo(16)
            )
    );
    //alchemical tome
    public static final RegistryObject<Item> ALCHEMICAL_TOME = ITEMS.register("alchemical_tome",
            () -> new AlchemicalTome(new Item.Properties()
                    .tab(ModTab.URANUS_TAB)
                    .stacksTo(1)
            )
    );

    //eventBus
    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
