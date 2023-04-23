package com.github.uranus_mod_group.uranus_mod.world.feature;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.block.ModBlocks;
import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModConfiguredFeatures {
    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURE =
            DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Uranus_mod.ModId);

    public static final Supplier<List<OreConfiguration.TargetBlockState>> ManaGemOre = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, ModBlocks.MANA_GEM_ORE.get().defaultBlockState())
            )
    );

    public static final RegistryObject<ConfiguredFeature<?, ?>> MANA_GEM_ORE = CONFIGURED_FEATURE.register("mana_gem_ore",
            () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ManaGemOre.get(),7)));

    public static void register(IEventBus eventBus){CONFIGURED_FEATURE.register(eventBus);}
}
