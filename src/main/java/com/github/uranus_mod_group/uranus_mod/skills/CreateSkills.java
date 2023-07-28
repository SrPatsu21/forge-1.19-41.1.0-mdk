package com.github.uranus_mod_group.uranus_mod.skills;

import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import com.github.uranus_mod_group.uranus_mod.networking.packet.SkillsDataSyncS2CPacket;
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
    private float damage = 0.0F;
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
    byte [] player_attributes =
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
//0    fire 1
//1    water 2
//2    stone 3
//3    air 4
//4    elektron 5
//5    lava 6
//6    construct 7 8
//7    body manipulation 9 10 11
//8    ender magic 12
//9    lux 13
//10   heat 14
//11   blood 15
//12   mana manipulation 16 17
//13   explosion 18
//14   gravity 19 20
//15   summon 21
    private final byte [] respective_skill =
    {
        0,
        1,
        2,
        3,
        4,
        5,
        6,
        6,
        7,
        7,
        7,
        8,
        9,
        10,
        11,
        12,
        12,
        13,
        14,
        14,
        15
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
        //setDamage();
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
    //xp
    public void setPlayerXpSkill(int i, int xp)
    {
        getOwner().getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(skill ->
        {
            skill.setSkillXp(i, xp);
            ModMessages.sendToPlayer(new SkillsDataSyncS2CPacket(skill.getSkillsLevel(), skill.getSkillsXp()), getOwner());
        });
    }
    public void setPlayerXpSkills()
    {
        for(int i =0;i < this.getSkill_attributes().length; i++)
        {
            if(getSkill_attributes()[i] != 0){
                int xp = this.getSkill_attributes()[i];
                setPlayerXpSkill((this.respective_skill[i]), xp);
            }
        }
    }
    //damage
    private void setDamage()
    {
        for(int i = 0; i < this.getSkill_attributes().length; i++)
        {
            this.damage += (float) this.getSkill_attributes()[i] * this.getPlayerAttributes()[(this.respective_skill[i])];
        }
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
    public byte [] getPlayerAttributes()
    {
        return this.player_attributes;
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
    //create skill as set
    public void createSkill(float speed_plus)
    {
        // && getSkill_attributes() != null
        if(getSkill_kind() != 0)
        {
            //setPlayerXpSkills();
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
                , getSkill_attributes(), getDamage());

        magic_sphere.shootFromRotation(magic_sphere.getOwner(), magic_sphere.getOwner().getXRot(), magic_sphere.getOwner().getYRot()
                , 0.0F, magic_sphere.getSpeed() * speed_plus, 0.0F);

        level.addFreshEntity(magic_sphere);
    }
    public void createFailMagicSkill()
    {

    }
}