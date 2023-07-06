package com.github.uranus_mod_group.uranus_mod.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    //mana level
    private int ml = 0;
    private final int MAX_ML = 2147483647;
    //mana
    private int mana;
    private int Mbase = 100;
    private final int MIN_MANA = 0;
    private int MAX_MANA = (int) (Mbase + (Mbase*(0.5f * ml)));
    //functions
    public int getMana(){return mana;}
    public int getMl(){return ml;}
    public int getMAX_MANA(){return MAX_MANA;}

    public void addMana(int add){
        this.mana = Math.min(mana + add, MAX_MANA);
    }
    public void subMana(int sub){
        this.mana = Math.max(mana - sub, MIN_MANA);
    }
    public void addML(int add){
        this.ml = Math.min(ml + add, MAX_ML);
    }

    //source
    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
        this.ml = source.ml;
    }

    //save on nbt
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
        nbt.putInt("ml", ml);
    }
    //load mana
    public void loadNBTData(CompoundTag nbt){
        mana = nbt.getInt("mana");
        ml = nbt.getInt("ml");
    }
}
