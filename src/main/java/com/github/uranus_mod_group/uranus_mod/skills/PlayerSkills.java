package com.github.uranus_mod_group.uranus_mod.skills;

import net.minecraft.nbt.CompoundTag;

public class PlayerSkills {
    //skills
//0    fire
//1    water
//2    stone
//3    air
//4    elektron
//5    lava
//6    construct
//7    body manipulation
//8    ender magic
//9    lux
//10   heat
//11   blood
//12   mana manipulation
//13   explosion
//14   gravity
//15   summon
    byte [] skill_level = {
            1,
            1,
            1,
            1,
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
    int [] skill_xp = {
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
    //get
    public int getSkillLevel(int i)
    {
        return this.skill_level[i];
    }
    public int getSkillXp(int i)
    {
        return this.skill_xp[i];
    }
    public int getSkillXpToUp(int i)
    {
        return (int) Math.pow((this.skill_level[i]*100), 1.5);
    }
    //set
    public void setSkillLevel(int i, byte level)
    {
        this.skill_level[i] = level;
    }
    public void setSkillXp(int i, int xp)
    {
        this.skill_xp[i] = xp;
    }
    //add and sub
    public void addSkillLevel(int i, int add)
    {
        this.skill_level[i] = (byte) Math.min(skill_level[i] + add, 10);
    }
    public void subSkillLevel(int i, int sub)
    {
        this.skill_level[i] = (byte) Math.max(skill_level[i] + sub, 0);
    }
    public void addSkillXp(int i, int add)
    {
        this.skill_xp[i] = skill_xp[i] + add;
    }
    public void subSkillXp(int i)
    {
        this.skill_xp[i] = 0;
    }
    //functions
    public void upSkill(int i)
    {
        addSkillLevel(i, 1);
        subSkillXp(i);
    }
    //source
    public void copyFrom(PlayerSkills source)
    {
        this.skill_level = source.skill_level;
        this.skill_xp = source.skill_xp;
    }
    //save on nbt
    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putByteArray("skill_level", skill_level);
        nbt.putIntArray("skill_xp", skill_xp);
    }
    //load mana
    public void loadNBTData(CompoundTag nbt)
    {
        skill_level = nbt.getByteArray("skill_level");
        skill_xp = nbt.getIntArray("skill_xp");
    }
}
