package com.github.uranus_mod_group.uranus_mod.skills;

import com.github.uranus_mod_group.uranus_mod.client.ClientSkillsData;
import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.AbstractUranusModProjectile;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class CreateSkills {
    private int skill_kind = 0;
    private Level level;
    private byte[] skill_attributes;
    private LivingEntity owner;
    public CreateSkills(Level level, LivingEntity entity, int skill_kind,@Nullable byte [] attributes)
    {
        this.setLevel(level);
        this.setOwner(entity);
        this.setSkill_kind(skill_kind);
        this.setSkill_attributes(attributes);
    }
    //set
    public void setLevel(Level level)
    {
        this.level = level;
    }
    public void setOwner(LivingEntity entity)
    {
        this.owner = entity;
    }
    public void setSkill_kind(int skill_kind)
    {
        this.skill_kind = skill_kind;
    }
    public void setSkill_attributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    //get
    public Level getLevel()
    {
        return this.level;
    }
    public int getSkill_kind()
    {
        return this.skill_kind;
    }
    public byte[] getSkill_attributes()
    {
        return this.skill_attributes;
    }
    public LivingEntity getOwner()
    {
        return this.owner;
    }
    //get player attributes
    public byte [] getPlayerAttributes()
    {
        return ClientSkillsData.getSkillsLevel();
    }

    public void createSkill()
    {
        if(getSkill_kind() == 1){

            MagicSphereEntity magic_sphere = new MagicSphereEntity(ModEntityTypes.MAGIC_SPHERE.get() , getLevel(), getOwner());
            magic_sphere.shootFromRotation(magic_sphere.getOwner(), magic_sphere.getOwner().getXRot(), magic_sphere.getOwner().getYRot(),
                    0.0F, magic_sphere.getSpeed() * 3.0F, 0.0F);
            level.addFreshEntity(magic_sphere);

        }else {
            MagicSphereEntity magic_sphere = new MagicSphereEntity(ModEntityTypes.MAGIC_SPHERE.get() , getLevel(), getOwner());
            magic_sphere.shootFromRotation(magic_sphere.getOwner(), magic_sphere.getOwner().getXRot(), magic_sphere.getOwner().getYRot(),
                    0.0F, magic_sphere.getSpeed() * 0.0F, 0.0F);
            level.addFreshEntity(magic_sphere);
        }
    }
}
