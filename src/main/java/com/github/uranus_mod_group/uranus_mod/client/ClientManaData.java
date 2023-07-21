package com.github.uranus_mod_group.uranus_mod.client;

public class ClientManaData {
    private static double playerMana;
    private static int playerManaMax;

    //set mana on playerMana
    public static void set(double mana, int mana_max)
    {
        ClientManaData.playerMana = mana;
        ClientManaData.playerManaMax = mana_max;
    }

    public static double getPlayerMana()
    {
        return playerMana;
    }
    public static double getPlayerManaMax()
    {
        return playerManaMax;
    }

}


