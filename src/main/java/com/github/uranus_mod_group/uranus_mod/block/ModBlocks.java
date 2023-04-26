package com.github.uranus_mod_group.uranus_mod.block;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.block.custom.ManaGemOre;
import com.github.uranus_mod_group.uranus_mod.item.ModItems;
import com.github.uranus_mod_group.uranus_mod.item.ModTab;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Uranus_mod.ModId);

    //block

    public static final RegistryObject<Block> MANA_GEM_ORE = registerBlock("mana_gem_ore",
            () -> new DropExperienceBlock(
                    BlockBehaviour
                            .Properties.of(Material.STONE)
                            .strength(6.5f)
                            .requiresCorrectToolForDrops()
                            .lightLevel(state -> 10),
                    UniformInt.of(10,12)
                    ),ModTab.URANUS_TAB
    );

    public static final RegistryObject<Block> SWORD_SUPPORT = registerBlock("sword_support",
            () -> new Block(
                    BlockBehaviour
                            .Properties.of(Material.HEAVY_METAL)
                            .strength(3f)
            ),ModTab.URANUS_TAB
    );

    //functions
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    //eventBus
    public static void register(IEventBus eventBus){BLOCKS.register(eventBus);}
}
