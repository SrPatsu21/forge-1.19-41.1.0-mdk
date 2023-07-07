package com.github.uranus_mod_group.uranus_mod.mana;

import net.minecraft.nbt.CompoundTag;

public class PlayerMana {
    //final
    private final int MAX_ML = 308581;
    private final int M_BASE = 100;
    private final int REGEN_TIME = 20;
    //vars
    private int mana = 0;
    private int ml = 0;
    private int mxp = 0;
    private float mana_regen = 0.01f;
    private int max_mana = (int) (M_BASE + (M_BASE*(0.5f * ml)));
    private int mana_to_up = (int) Math.pow(max_mana, 1);//change to 1.7f after testing
    //functions
        //get
    public int getMana(){return mana;}
    public int getMl(){return ml;}
    public int getMxp(){return mxp;}
    public int getMaxMana(){return max_mana;}
    public int getManaToUp(){
        return mana_to_up;
    }
    public float getManaRegen(){
        return mana_regen;
    }
    public int getREGEN_TIME() {
        return REGEN_TIME;
    }

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
    public void subMxp(){
        this.mxp = 0;
    }
    public void addManaRegen(float add){
        this.mana_regen = Math.min(mana_regen + add, 100);
    }
    public void subManaRegen(float sub){
        this.mana_regen = Math.min(mana_regen - sub, 100);
    }
    //reset mana stats
    public void manaStatsReset(){
        this.max_mana = (int) (this.M_BASE + (this.M_BASE*(0.5f * this.ml)));
        this.mana_to_up = (int) Math.pow(this.max_mana, 1);//change to 1.7f after testing
    }
    //mana up
    public void manaUpProcess(){
        subMxp();
        addML(1);
        manaStatsReset();
    }
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
        manaStatsReset();
    }

}
