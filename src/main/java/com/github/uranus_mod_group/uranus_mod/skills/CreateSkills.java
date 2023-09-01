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
import org.jetbrains.annotations.NotNull;

public class CreateSkills {
    private int skill_kind = 0;
    private Level level;
    //just for test
    private byte[] skill_attributes;
    private ServerPlayer owner;
    private double value_of_skill = 0.0D;
    private NetworkEvent.Context context;
    private float damage = 0.0F;
//0    ignite
//1    water
//2    stone
//3    air
//4    elektron
//5    lava
//6    break
//7    build
//8    heal
//9    poison
//10   wither
//11   teleport
//12   light/shadows
//13   cold/warm
//14   blood
//15   give mana
//16   remove mana
//17   explosion
//18   gravitational
//19   pull/push
//20   summon
//21   radius
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
        40,
        20
    };
    private byte[] player_attributes;
//0    fire 0 5
//1    water 1
//2    stone 2
//3    air 3
//4    elektron 4
//5    construct 6 7
//6    body manipulation 8 9 10
//7    ender magic 11
//8    lux 12
//9    heat 13
//10   blood 14
//11   mana manipulation 15 16
//12   explosion 17
//13   gravity 18 19
//14   summon 20
    private final byte [] RESPECTIVE_SKILLS =
    {
        0,
        1,
        2,
        3,
        4,
        0,
        5,
        5,
        6,
        6,
        6,
        7,
        8,
        9,
        10,
        11,
        11,
        12,
        13,
        13,
        14
    };
    //constructor
    public CreateSkills(@NotNull NetworkEvent.Context context, int skill_kind, byte [] attributes)
    {
        this.setContext(context);
        this.setOwner(context.getSender());
        this.setLevel(context.getSender().getLevel());
        this.setSkillKind(skill_kind);
        this.setSkillAttributes(attributes);
        this.setPlayerAttributes(getOwner());
        this.setDamage();
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
    public void setSkillKind(int skill_kind)
    {
        this.skill_kind = skill_kind;
    }
    public void setSkillAttributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    public void setContext(NetworkEvent.Context context) {
        this.context = context;
    }
    public void setPlayerAttributes(ServerPlayer owner)
    {
        owner.getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(skill ->
                this.player_attributes = skill.getSkillsLevel());
    }
    private void setValueOfSkillMana(float add)
    {
        //to 20
        for(int i = 0; i < (this.value_of_attributes.length-1); i++)
        {
            this.value_of_skill += this.value_of_attributes[i] * (int) getSkillAttributes()[i];
        }
        this.value_of_skill += add;
        //to 21
        this.value_of_skill *= getSkillAttributes()[(getSkillAttributes().length-1)];
    }
    public void setPlayerXpSkills()
    {
        getOwner().getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(skill -> {
            for (int i = 0; i < (this.getSkillAttributes().length-1); i++) {
                if (getSkillAttributes()[i] != 0) {
                    skill.addSkillXp((this.RESPECTIVE_SKILLS[i]), (this.getSkillAttributes()[i]));
                }
            }
            ModMessages.sendToPlayer(new SkillsDataSyncS2CPacket(skill.getSkillsLevel(), skill.getSkillsXp()), getOwner());
        });
    }
    //damage
    private void setDamage()
    {
        for(int i = 0; i < (this.RESPECTIVE_SKILLS.length-1); i++)
        {
            if (getSkillAttributes()[i] != 0) {
                this.damage += (float) (this.getSkillAttributes()[i]) * (this.getPlayerAttributes()[(this.RESPECTIVE_SKILLS[i])]);
            }
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
    public byte[] getSkillAttributes()
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
        if(getSkill_kind() != 0)
        {
            setPlayerXpSkills();
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
                , getSkillAttributes(), getDamage());

        magic_sphere.shootFromRotation(magic_sphere.getOwner(), magic_sphere.getOwner().getXRot(), magic_sphere.getOwner().getYRot()
                , 0.0F, magic_sphere.getSpeed() * speed_plus, 0.0F);

        level.addFreshEntity(magic_sphere);
    }
    public void createFailMagicSkill()
    {

    }
}