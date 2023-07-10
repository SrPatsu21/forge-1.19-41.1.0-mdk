package com.github.uranus_mod_group.uranus_mod.villager;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.block.ModBlocks;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;

public class ModVillagers
{
    //register
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, Uranus_mod.ModId);
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSION =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Uranus_mod.ModId);
    //define villager table
    public static final RegistryObject<PoiType> SWORD_SUPPORT_POI = POI_TYPES.register("sword_support_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.SWORD_SUPPORT.get().getStateDefinition().getPossibleStates()),
                    1, 1)
    );
    //profession
    public static final RegistryObject<VillagerProfession> FIGHT_MASTER = VILLAGER_PROFESSION.register("fight_master",
            () -> new VillagerProfession( "fight_master", x -> x.get() == SWORD_SUPPORT_POI.get(),
                    x -> x.get() == SWORD_SUPPORT_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_TOOLSMITH));
    //register villager table
    public static void registerPOis()
    {
        try
        {
            ObfuscationReflectionHelper.findMethod(PoiType.class, "registerBlockStates", PoiType.class)
                    .invoke(null, SWORD_SUPPORT_POI.get());
        }
        catch (InvocationTargetException | IllegalAccessException exception)
        {
            exception.printStackTrace();
        }
    }
    //eventBus
    public static void register(IEventBus eventBus)
    {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSION.register(eventBus);
    }
}
