package com.github.uranus_mod_group.uranus_mod.client;

public class ClientSkillsData {
    private static byte [] skills_level;
    private static int [] skill_xp;

    //set mana on playerMana
    public static void setSkillsLevel(byte [] skills_level)
    {
        ClientSkillsData.skills_level = skills_level;
    }
    public static void setSkillXp(int [] skill_xp)
    {
        ClientSkillsData.skill_xp = skill_xp;
    }

    public static byte[] getSkillsLevel()
    {
        return skills_level;
    }
    public static int[] getSkillXp()
    {
        return skill_xp;
    }
}
