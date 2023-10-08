package com.github.uranus_mod_group.uranus_mod.skills;

import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.NotNull;

public class CreateSkills {
    private int skill_kind = 0;
    private Level level;
    //just for test
    private ServerPlayer owner;
    private double value_of_skill = 0.0D;
    private NetworkEvent.Context context;
    private float damage = 0.0F;
//    ignite
//    water
//    stone
//    air
//    lava
//    sticky
//    metal
//    elektron
//    lux
//    dark
//    heat
//    heal
//    poison
//    wither
//    teleport
//    gravitational
//    push
    private byte[] skill_attributes =
    {
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
    public final byte[] VALUE_OF_ATTRIBUTES =
    {
        20,
        10,
        30,
        20,
        60,
        20,
        90,
        40,
        5,
        80,
        40,
        40,
        80,
        100,
        40,
        40
    };
    public final float[] DAMAGE_MULTIPLAYER = {
        1,
        1,
        1.5F,
        1,
        2,
        1,
        2,
        1,
        0.5F,
        0,
        0,
        0,
        0,
        0,
        0,
        1
    };
    public final byte [] RESPECTIVE_SKILLS =
    {
        0,
        0,
        0,
        0,
        1,
        1,
        1,
        2,
        2,
        2,
        3,
        3,
        3,
        4,
        4,
        4
    };
    public byte [] proficiency;
    //constructor
    public CreateSkills(@NotNull NetworkEvent.Context context, int skill_kind,byte [] attributes)
    {
        setContext(context);
        setOwner(context.getSender());
        setLevel(context.getSender().getLevel());
        setSkillKind(skill_kind);
        setProficiency(getOwner());
        setSkillAttributes(attributes);
    }
    //set
    public void setContext(NetworkEvent.Context context)
    {
        this.context = context;
    }
    public void setLevel(Level level)
    {
        this.level = level;
    }
    public void setOwner(ServerPlayer player)
    {
        this.owner = player;
    }
    public void setSkillKind(int skill_kind)
    {
        this.skill_kind = skill_kind;
    }
    public void setProficiency(ServerPlayer player)
    {
        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana -> {
            this.proficiency = mana.getProficiency();
        });
    }
    public void setSkillAttributes(byte[] attributes)
    {
        for (int i = 0; i < this.skill_attributes.length; i++)
        {
            if (getProficiency(RESPECTIVE_SKILLS[i]) > -1)
            {
                this.skill_attributes[i] = attributes[i];
            }else
            {
                this.skill_attributes[i] = 0;
            }
        }
    }
    private void setValueOfSkillMana(float add)
    {
        for (int i = 0; i < VALUE_OF_ATTRIBUTES.length; i++) {
            if (getSkillAttributes()[i] != 0 && getProficiency(RESPECTIVE_SKILLS[i]) > -1) {
                if (getSkillAttributes()[i] < 0) {
                    this.value_of_skill += (VALUE_OF_ATTRIBUTES[i] * getSkillAttributes()[i] * -1)*(1-getProficiency(RESPECTIVE_SKILLS[i])*0.005);
                } else {
                    this.value_of_skill += (VALUE_OF_ATTRIBUTES[i] * getSkillAttributes()[i])*(1-getProficiency(RESPECTIVE_SKILLS[i])* 0.005);
                }
            }
        }
        this.value_of_skill *= add;
    }
    //damage
    private void setDamage()
    {
        for(int i = 0; i < DAMAGE_MULTIPLAYER.length; i++)
        {
            if (getSkillAttributes()[i] != 0){
                if (getSkillAttributes()[i] < 0)
                {
                    this.damage += (DAMAGE_MULTIPLAYER[i] * getSkillAttributes()[i]*-1)*(1+getProficiency(RESPECTIVE_SKILLS[i])*0.01);
                } else
                {
                    this.damage += (DAMAGE_MULTIPLAYER[i] * getSkillAttributes()[i])*(1+getProficiency(RESPECTIVE_SKILLS[i])*0.01);
                }
            }
        }
    }
    public void setProficiencyXp(ServerPlayer player, byte[] skill_attributes)
    {
        player.getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent( mana ->{
            for(int i = 0; i < skill_attributes.length; i++)
            {
                if (!(skill_attributes[i] <= 0))
                {
                    mana.addProficiencyXp(RESPECTIVE_SKILLS[i], skill_attributes[i]);
                }
            }
        });
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
    public byte[] getSkillAttributes()
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
    public float getDamage()
    {
        return this.damage;
    }

    public byte getProficiency(int i) {
        return proficiency[i];
    }

    //create skill as set
    public void createSkill()
    {
        if(getSkill_kind() != 0)
        {
            getContext().enqueueWork(() ->
            {
                //magic sphere
                if (getSkill_kind() == 1) {
                    //if player has mana, works
                    setValueOfSkillMana(2.0F);
                    getOwner().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->
                    {
                        if (mana.getMana() >= getValue_of_skill() && getValue_of_skill() != 0) {
                            setDamage();
                            mana.addMxp((int)getValue_of_skill());
                            setProficiencyXp(getOwner(), getSkillAttributes());
                            createMagicSphereEntity(3);
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
                , getSkillAttributes(), getDamage());

        magic_sphere.shootFromRotation(getOwner(), getOwner().getXRot(), getOwner().getYRot()
                , 0.0F, 1 * speed_plus, 0.0F);

        level.addFreshEntity(magic_sphere);
    }
    public void createFailMagicSkill()
    {

    }
}