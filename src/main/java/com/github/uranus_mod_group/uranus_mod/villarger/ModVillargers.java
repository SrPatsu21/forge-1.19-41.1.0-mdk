package com.github.uranus_mod_group.uranus_mod.villarger;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.block.ModBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

public class ModVillargers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Uranus_mod.ModId);
    // no lugar de VILLAGER_PROFESSIONS era apenas PROFESSIONS entao troquei eles
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Uranus_mod.ModId);

    public static final RegistryObject<PoiType> SWORD_SUPPORT_POI = POI_TYPES.register("sword_support_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SWORD_SUPPORT.get().getStateDefinition().getPossibleStates()),1, 1)
    );


    public static void registerPOis() {
        try {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class).invoke(null, null);
        }catch (InvocationTargetException | IllegalAccessException exception){
            exception.printStackTrace();
        }
    }

    public static void register(IEventBus eventBus){
        POI_TYPES.register(eventBus);
        PROFESSIONS.register(eventBus);
    }
}
