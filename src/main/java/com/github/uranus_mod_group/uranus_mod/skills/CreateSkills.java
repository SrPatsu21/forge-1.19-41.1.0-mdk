package com.github.uranus_mod_group.uranus_mod.skills;

import com.github.uranus_mod_group.uranus_mod.client.ClientSkillsData;
import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import com.github.uranus_mod_group.uranus_mod.mana.PlayerManaProvider;
import com.github.uranus_mod_group.uranus_mod.networking.ModMessages;
import com.github.uranus_mod_group.uranus_mod.networking.packet.ManaDataSyncS2CPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class CreateSkills {
    private int skill_kind = 0;
    private Level level;
    @Nullable
    private byte[] skill_attributes;
    private ServerPlayer owner;
    private double value_of_skill;
//1    fire
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
//13   light
//14   shadows
//15   cold/warm
//16   blood
//17   give mana
//18   remove mana
//19   explosion
//20   gravitational
//21   pull/push
//22   summon
    private int[] value_of_attributes =
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
    public CreateSkills(Level level, ServerPlayer player, int skill_kind,@Nullable byte [] attributes)
    {
        this.setLevel(level);
        this.setOwner(player);
        this.setSkill_kind(skill_kind);
        this.setSkill_attributes(attributes);
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
    @Nullable
    public void setSkill_attributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    private void setValueOfSkill(double add)
    {
        for(int i: this.value_of_attributes)
        {
            this.value_of_skill += this.value_of_attributes[i] * getPlayerAttributes()[i];
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
    //get player attributes
    @Nullable
    public byte [] getPlayerAttributes()
    {
        return ClientSkillsData.getSkillsLevel();
    }

    public void createSkill(float speed_plus)
    {
        if(getSkill_kind() != 0 && getSkill_attributes() != null)
        {
            if(getSkill_kind() == 1)
            {
                setValueOfSkill(10);
                //fazer o if mana para reduzir a mana
                getOwner().getCapability(PlayerManaProvider.PLAYER_MANA).ifPresent(mana ->
                {
                   if(mana.getMana() >= getValue_of_skill())
                   {
                       createMagicSphereEntity(speed_plus);
                   }
                   mana.subMana(getValue_of_skill());
                    ModMessages.sendToPlayer(new ManaDataSyncS2CPacket(mana.getMana(), mana.getMaxMana()),getOwner());
                });
            }
        }
    }
    public void createMagicSphereEntity (float speed_plus)
    {
        MagicSphereEntity magic_sphere = new MagicSphereEntity(ModEntityTypes.MAGIC_SPHERE.get() , getLevel(), getOwner());
        magic_sphere.shootFromRotation(magic_sphere.getOwner(), magic_sphere.getOwner().getXRot(), magic_sphere.getOwner().getYRot(),
                0.0F, magic_sphere.getSpeed() * speed_plus, 0.0F);
        level.addFreshEntity(magic_sphere);
    }
    public void createFailMagicSkill()
    {

    }
}