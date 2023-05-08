package com.github.uranus_mod_group.uranus_mod.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    //base status
    private int mana;
    private int Mbase = 100;
    private byte Mlevel = 1;
    private final int MIN_MANA = 0;
    private final int MAX_MANA = (int) (Mbase + (Mbase*(0.5f * Mlevel)));

    //functions
    public int getMana(){return mana;}
    public int getMAX_MANA(){return MAX_MANA;}

    public void addMana(int add){
        this.mana = Math.min(mana + add, MAX_MANA);
    }
    public void subMana(int sub){
        this.mana = Math.max(mana - sub, MIN_MANA);
    }

    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
    }

    //save on nbt
    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("mana", mana);
    }

    public void loadNBTData(CompoundTag nbt){
        nbt.getInt("mana");
    }
}
