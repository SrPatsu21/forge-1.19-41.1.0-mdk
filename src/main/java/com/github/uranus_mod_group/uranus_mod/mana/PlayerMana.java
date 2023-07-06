package com.github.uranus_mod_group.uranus_mod.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    //mana level
    private int ml = 0;
    private int mxp = 0;
    private final int MAX_ML = 2147483647;
    //mana
    private int mana;
    private int Mbase = 100;
    private int max_mana = (int) (Mbase + (Mbase*(0.5f * ml)));
    //functions
        //get
    public int getMana(){return mana;}
    public int getMl(){return ml;}
    public int getMxp(){return mxp;}
    public int getMaxMana(){return max_mana;}
        //sub and add
    public void addMana(int add){
        this.mana = Math.min(mana + add, max_mana);
    }
    public void subMana(int sub){
        this.mana = Math.max(mana - sub, 0);
    }
    public void addML(int add){
        this.ml = Math.min(ml + add, MAX_ML);
    }
    public void subMl(int sub){this.ml = Math.min(ml - sub, 0); }
    public void addMxp(int add){
        this.mxp = Math.min(mxp + add, MAX_ML);
    }
    public void subMxp(int sub){this.mxp = Math.min(mxp - sub, 0); }
    //source
    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
        this.ml = source.ml;
        this.mxp = source.mxp;
    }

    //save on nbt
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
        nbt.putInt("ml", ml);
        nbt.putInt("mxp", mxp);
    }
    //load mana
    public void loadNBTData(CompoundTag nbt){
        mana = nbt.getInt("mana");
        ml = nbt.getInt("ml");
        mxp = nbt.getInt("mxp");
    }
}
