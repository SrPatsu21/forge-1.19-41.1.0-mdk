package com.github.uranus_mod_group.uranus_mod.skills;

import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;

public class CreateSkills {
    private int skill_kind = 0;
    private Level level;
    //just for test
    private byte[] skill_attributes;
    private ServerPlayer owner;
    private double value_of_skill = 0.0D;
    private NetworkEvent.Context context;
//1    ignite
//2    water
//3    stone
//4    air
//5    elektron
//6    lava
//7    break
//8    build
//9    heal
//10   poison
//11   wither
//12   teleport
//13   light/shadows
//14   cold/warm
//15   blood
//16   give mana
//17   remove mana
//18   explosion
//19   gravitational
//20   pull/push
//21   summon
    private byte[] value_of_attributes =
    {
        10,
        10,
        20,
        10,
        30,
        30,
        20,
        40,
        50,
        40,
        60,
        40,
        50,
        35,
        20,
        20,
        30,
        80,
        30,
        30,
        40
    };
    byte [] player_attributes = {
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
    };
    //constructor
    public CreateSkills(NetworkEvent.Context context, int skill_kind,@Nullable byte [] attributes)
    {
        this.setContext(context);
        this.setOwner(context.getSender());
        this.setLevel(context.getSender().getLevel());
        this.setSkill_kind(skill_kind);
        this.setSkill_attributes(attributes);
        setPlayerAttributes(getOwner());
    }
    //set
    public void setLevel(Level level)
    {
        this.level = level;
    }
    public void setOwner(ServerPlayer player)
    {
        this.owner = player;
    }
    public void setSkill_kind(int skill_kind)
    {
        this.skill_kind = skill_kind;
    }
    public void setSkill_attributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    public void setContext(NetworkEvent.Context context) {
        this.context = context;
    }
    public void setPlayerAttributes(ServerPlayer owner)
    {
        owner.getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(skill ->
        {
            this.player_attributes = skill.getSkillsLevel();
        });
    }
    private void setValueOfSkillMana(float add)
    {
        for(int i = 0; i < this.value_of_attributes.length; i++)
        {
            this.value_of_skill += this.value_of_attributes[i] * (int) getSkill_attributes()[i];
        }
        this.value_of_skill += add;
    }
    //get
    public Level getLevel()
    {
        return this.level;
    }
    public int getSkill_kind()
    {
        return this.skill_kind;
    }
    @Nullable
    public byte[] getSkill_attributes()
    {
        return this.skill_attributes;
    }
    public ServerPlayer getOwner()
    {
        return this.owner;
    }
    public double getValue_of_skill()
    {
        return this.value_of_skill;
    }
    public NetworkEvent.Context getContext() {
        return context;
    }
    //get player attributes
    public byte [] getPlayerAttributes()
    {
        return this.player_attributes;
    }

    public void createSkill(float speed_plus)
    {
        // && getSkill_attributes() != null
        if(getSkill_kind() != 0)
        {
            getContext().enqueueWork(() ->
            {
                //magic sphere
                if (getSkill_kind() == 1) {
                    setValueOfSkillMana(10.0F);
                    //if player has mana, works
                    getOwner().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->
                    {
                        if (mana.getMana() >= getValue_of_skill()) {
                            createMagicSphereEntity(speed_plus);
                        }
                        mana.subMana(getValue_of_skill());
                        ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMaxMana()), getOwner());
                    });
                }
            });
        }
    }
    public void createMagicSphereEntity (float speed_plus)
    {
        MagicSphereEntity magic_sphere = new MagicSphereEntity(ModEntityTypes.MAGIC_SPHERE.get() , getLevel(), getOwner()
                , getSkill_attributes(), getPlayerAttributes());

        magic_sphere.shootFromRotation(magic_sphere.getOwner(), magic_sphere.getOwner().getXRot(), magic_sphere.getOwner().getYRot()
                , 0.0F, magic_sphere.getSpeed() * speed_plus, 0.0F);

        level.addFreshEntity(magic_sphere);
    }
    public void createFailMagicSkill()
    {

    }
}