package com.github.uranus_mod_group.uranus_mod.client;

public class ClientManaData {
    private static double playerMana;

    //set mana on playerMana
    public static void set(double mana)
    {
        ClientManaData.playerMana = mana;
    }

    public static double getPlayerMana()
    {
        return playerMana;
    }
}
