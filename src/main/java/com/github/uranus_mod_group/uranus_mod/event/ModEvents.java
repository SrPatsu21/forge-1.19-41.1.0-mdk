package com.github.uranus_mod_group.uranus_mod.event;

import com.github.uranus_mod_group.uranus_mod.Uranus_mod;
import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.MasterWizardEntity;
import com.github.uranus_mod_group.uranus_mod.item.ModItems;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerMana;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import com.github.uranus_mod_group.uranus_mod.particles.ModParticles;
import com.github.uranus_mod_group.uranus_mod.particles.custom.MagicParticles;
import com.github.uranus_mod_group.uranus_mod.villager.ModVillagers;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.List;

public class ModEvents
{

    @Mod.EventBusSubscriber(modid = Uranus_mod.ModId)
    public static class ForgeEvents
    {

        //give mana/skills when player joins the game
        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event)
        {
            //mana
            if(event.getObject() instanceof Player)
            {
                if(!event.getObject().getCapability(PlayerManaProvider.PLAYER_MANA).isPresent())
                {
                    event.addCapability(new ResourceLocation(Uranus_mod.ModId, "properties_u_mana"), new PlayerManaProvider());
                }
            }
        }
        //if player die
        //save mana lvl and mana xp
        @SubscribeEvent
        public static void onDeath(PlayerEvent.Clone event)
        {
            //mana
            if(event.isWasDeath())
            {
                event.getOriginal().reviveCaps();
                event.getOriginal().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(oldStore ->
                {
                    event.getEntity().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(newStore ->
                    {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().invalidateCaps();
            }
        }

        //register capabilities
        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
        {
            event.register(PlayerMana.class);
        }
        //mana regen
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event)
        {
            if(event.side == LogicalSide.SERVER && event.phase == TickEvent.Phase.END)
            {
                event.player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->
                {
                    if(mana.getMana() < mana.getMaxMana() && (event.player.getCommandSenderWorld().getGameTime() % mana.getREGEN_TIME()) == 0)
                    {
                        //will be regen
                        int add = (int) (mana.getMaxMana() * mana.getManaRegen());
                        mana.addMana(add);
                        //send mana
                        ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMaxMana()), ((ServerPlayer) event.player));
                    }
                });
            }
        }

        //mana hud
        public static void onPlayerJoinWorld(EntityJoinLevelEvent event)
        {
            if(event.getLevel().isClientSide)
            {
                if(event.getEntity() instanceof ServerPlayer player)
                {
                    player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(playerMana ->
                    {
                        ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(playerMana.getMana(), playerMana.getMaxMana()), player);
                    });
                }
            }
        }

        //villager
        @SubscribeEvent
        public static void addCustomTrades(VillagerTradesEvent event)
        {
            if(event.getType() == VillagerProfession.TOOLSMITH)
            {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.ALCHEMICAL_TOME.get(), 1);
                int villagerLevel = 1;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 1),
                        stack,1,8,0.02F));
            }

            if(event.getType() == ModVillagers.FIGHT_MASTER.get())
            {
                Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
                ItemStack stack = new ItemStack(ModItems.ALCHEMICAL_TOME.get(), 1);
                int villagerLevel = 1;

                trades.get(villagerLevel).add((trader, rand) -> new MerchantOffer(
                        new ItemStack(Items.EMERALD, 5),
                        stack,10,8,0.02F));
            }
        }
    }


    @Mod.EventBusSubscriber(modid = Uranus_mod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {


        //need when an entity has attributes like life...
        //master wizard attributes
        @SubscribeEvent
        public static void entityAttributeEvent (EntityAttributeCreationEvent event)
        {
            event.put(ModEntityTypes.MASTER_WIZARD.get(), MasterWizardEntity.getMasterWizardAttributes().build());
        }
        //register particle
        @SubscribeEvent
        public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
            Minecraft.getInstance().particleEngine.register(ModParticles.MAGIC_PARTICLES.get(),
                    MagicParticles.Provider::new);
        }

    }

}