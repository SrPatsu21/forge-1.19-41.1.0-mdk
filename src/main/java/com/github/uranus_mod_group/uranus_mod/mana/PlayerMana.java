package com.github.uranus_mod_group.uranus_mod.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    //final
    public final int MAX_ML = 308581;
    public final int M_BASE = 100;
    public final int REGEN_TIME = 20;
    //vars
    private double mana = 0;
    private int ml = 0;
    private int mxp = 0;
    private float mana_regen = 0.01f;
    private int max_mana = (int) (M_BASE + (M_BASE*(0.5f * ml)));
    //if -1 is blocked
    private byte[] proficiency =
    {
        0,
        0,
        0,
        0,
        0,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1,
        -1
    };
    private int[] proficiency_xp =
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
        0
    };

    //functions
        //get
    public double getMana()
    {
        return mana;
    }
    public int getMl()
    {
        return ml;
    }
    public int getMxp()
    {
        return mxp;
    }
    public int getMaxMana()
    {
        return max_mana;
    }
    public int getManaToUp()
    {
        return (int) Math.pow(max_mana, 1.7f);
    }
    public float getManaRegen()
    {
        return mana_regen;
    }
    public int getREGEN_TIME()
    {
        return REGEN_TIME;
    }
    public byte getProficiency(int i)
    {
        return proficiency[i];
    }
    public int getProficiencyXpToUp(int i)
    {
        return getProficiency(i)*3000;
    }

    //sub and add
    public void addMana(double add)
    {
        this.mana = Math.min(mana + add, max_mana);
    }
    public void subMana(double sub)
    {
        this.mana = Math.max(mana - sub, 0);
    }
    public void addML(int add)
    {
        this.ml = Math.min(ml + add, MAX_ML);
        this.max_mana = (int) (M_BASE + (M_BASE*(0.5f * getMl())));
    }
    public void subMl(int sub)
    {
        this.ml = Math.max(ml - sub, 0);
        this.max_mana = (int) (M_BASE + (M_BASE*(0.5f * getMl())));
    }
    public void addMxp(int add)
    {
        this.mxp = mxp + add;
        if (getMxp() >= getManaToUp())
        {
            subMxp(getManaToUp());
            addML(1);
        }
    }
    public void subMxp(int sub)
    {
        this.mxp = Math.max(mxp - sub, 0);
    }
    public void addManaRegen(float add)
    {
        this.mana_regen = Math.min(mana_regen + add, 100);
    }
    public void subManaRegen(float sub)
    {
        this.mana_regen = Math.max(mana_regen - sub, 100);
    }
    public void addProficiencyXp(int i, int add)
    {
        proficiency_xp[i] += proficiency_xp[i] + add;
        if (proficiency_xp[i] > getProficiencyXpToUp(i))
        {
            addProficiency(i);
            subProficiencyXp(i, getProficiencyXpToUp(i));
        }
    }
    public void subProficiencyXp(int i, int sub)
    {
        this.proficiency_xp[i] = Math.max(proficiency_xp[i] - sub, 0);
    }

    public void addProficiency(int i)
    {
        this.proficiency_xp[i] = Math.min(proficiency_xp[i] + 1, 125);
    }
    public void subProficiency(int i)
    {
        this.proficiency_xp[i] = Math.max(proficiency_xp[i] - 1, -1);
    }
    //source
    public void copyFrom(PlayerMana source)
    {
        this.mana = source.mana;
        this.ml = source.ml;
        this.mxp = source.mxp;
        this.proficiency = source.proficiency;
        this.proficiency_xp = source.proficiency_xp;

    }
    //save on nbt
    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putDouble("mana", mana);
        nbt.putInt("ml", ml);
        nbt.putInt("mxp", mxp);
        nbt.putByteArray("proficiency", proficiency);
        nbt.putIntArray("proficiencyxp", proficiency_xp);

    }
    //load mana
    public void loadNBTData(CompoundTag nbt)
    {
        this.mana = nbt.getDouble("mana");
        this.ml = nbt.getInt("ml");
        this.mxp = nbt.getInt("mxp");
        this.proficiency = nbt.getByteArray("proficiency");
        this.proficiency_xp = nbt.getIntArray("proficiencyxp");
        //reset status
        this.max_mana = (int) (M_BASE + (M_BASE*(0.5f * getMl())));
    }
}
