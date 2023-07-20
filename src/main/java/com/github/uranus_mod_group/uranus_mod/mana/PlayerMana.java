package com.github.uranus_mod_group.uranus_mod.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    //final
    private final int MAX_ML = 8000;
    private final double M_BASE = 100.0D;
    private final int REGEN_TIME = 20;
    //vars
    private double mana = 0.0D;
    private int ml = 0;
    private int mxp = 0;
    private float mana_regen = 0.01f;
    private int max_mana = (int) (M_BASE + (M_BASE*(0.5f * ml)));
    private int mana_to_up = (int) Math.pow(max_mana, 1.7f);

    //get
    public double getMana()
    {
        return this.mana;
    }
    public int getMl()
    {
        return this.ml;
    }
    public int getMxp()
    {
        return this.mxp;
    }
    public int getMaxMana()
    {
        return this.max_mana;
    }
    public int getManaToUp()
    {
        return this.mana_to_up;
    }
    public float getManaRegen()
    {
        return this.mana_regen;
    }
    public int getREGEN_TIME()
    {
        return this.REGEN_TIME;
    }

    //sub and add
    public void addMana(float add)
    {
        this.mana = Math.min(mana + add, max_mana);
    }
    public void subMana(float sub)
    {
        this.mana = Math.max(mana - sub, 0);
    }
    public void addML(int add)
    {
        this.ml = Math.min(ml + add, MAX_ML);
    }
    public void subMl(int sub)
    {
        this.ml = Math.min(ml - sub, 0);
    }
    public void addMxp(int add)
    {
        this.mxp = Math.min(mxp + add, MAX_ML);
    }
    public void subMxp()
    {
        this.mxp = 0;
    }
    public void addManaRegen(float add)
    {
        this.mana_regen = Math.min(mana_regen + add, 100);
    }
    public void subManaRegen(float sub)
    {
        this.mana_regen = Math.min(mana_regen - sub, 100);
    }
    //reset mana stats
    public void manaStatsSet()
    {
        this.max_mana = (int) (this.M_BASE + (this.M_BASE*(0.5f * this.ml)));
        this.mana_to_up = (int) Math.pow(this.max_mana, 1.5f);
    }
    //mana up
    public void manaUpProcess()
    {
        this.subMxp();
        this.addML(1);
        this.manaStatsSet();
    }
    //source
    public void copyFrom(PlayerMana source)
    {
        this.mana = source.mana;
        this.ml = source.ml;
        this.mxp = source.mxp;
    }
    //save on nbt
    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putDouble("mana", mana);
        nbt.putInt("ml", ml);
        nbt.putInt("mxp", mxp);
    }
    //load mana
    public void loadNBTData(CompoundTag nbt)
    {
        this.mana = nbt.getDouble("mana");
        this.ml = nbt.getInt("ml");
        this.mxp = nbt.getInt("mxp");
        this.manaStatsSet();
    }
}
