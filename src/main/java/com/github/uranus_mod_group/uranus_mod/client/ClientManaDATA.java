package com.github.uranus_mod_group.uranus_mod.client;

public class ClientManaDATA {
    private static int playerMana;

    public static void set(int mana){
        ClientManaDATA.playerMana = mana;
    }

    public static int getPlayerMana(){
        return playerMana;
    }
}
