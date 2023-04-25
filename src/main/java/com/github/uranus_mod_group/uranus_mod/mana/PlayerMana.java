package com.github.uranus_mod_group.uranus_mod.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    private float mana;
    private final float MIN_MANA = 0;
    private final float MAX_MANA = 100;

    public float getMana(){
        return mana;
    }
    public void addMana(int add){
        this.mana = Math.min(mana + add, MAX_MANA);
    }
    public void subMana(int sub){
        this.mana = Math.min(mana - sub, MAX_MANA);
    }

    public void copyFrom(PlayerMana source){
        this.mana = source.mana;
    }

    public void saveNBTData(CompoundTag nbt){
        nbt.putFloat("mana", mana);
    }

    public void loadNBTData(CompoundTag nbt){
        nbt.getFloat("mana");
    }
}
