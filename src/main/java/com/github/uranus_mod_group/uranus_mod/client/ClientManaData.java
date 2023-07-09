package com.github.uranus_mod_group.uranus_mod.client;

public class ClientManaData {
    private static int playerMana;

    //set mana on playerMana
    public static void set(int mana){
        ClientManaData.playerMana = mana;
    }

    public static int getPlayerMana(){
        return playerMana;
    }
}
