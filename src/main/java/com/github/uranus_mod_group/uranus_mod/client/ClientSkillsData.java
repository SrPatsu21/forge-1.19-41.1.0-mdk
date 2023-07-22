package com.github.uranus_mod_group.uranus_mod.client;

public class ClientSkillsData {
    private static byte [] skills_level;
    private static int [] skill_xp;

    //set mana on playerMana
    public static void set(byte [] skills_level, int [] skill_xp)
    {
        ClientSkillsData.skills_level = skills_level;
        ClientSkillsData.skill_xp = skill_xp;
    }

    public static byte[] getPlayerMana()
    {
        return skills_level;
    }
    public static int[] getPlayerManaMax()
    {
        return skill_xp;
    }
}
