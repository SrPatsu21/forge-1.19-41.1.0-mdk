package com.github.uranus_mod_group.uranus_mod.skills;

import com.github.uranus_mod_group.uranus_mod.entity.ModEntityTypes;
import com.github.uranus_mod_group.uranus_mod.entity.custom.projectile.MagicSphereEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class CreateSkills {
    private int skill_kind;
    private Level level;
    private byte[] skill_attributes;
    private LivingEntity owner;

    public CreateSkills(int skill_kind, byte [] attributes, LivingEntity entity, Level level)
    {
        this.setOwner(entity);
        this.setSkill_kind(skill_kind);
        this.setSkill_attributes(attributes);
        this.setLevel(level);
    }
    //set
    private void setSkill_kind(int skill_kind)
    {
        this.skill_kind = skill_kind;
    }
    private void setSkill_attributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    private void setOwner(LivingEntity entity)
    {
        this.owner = entity;
    }
    private void setLevel(Level level)
    {
        this.level = level;
    }
    //get
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
//    public MagicSphereEntity createSkill(Level level, LivingEntity Owner)
//    {
//        MagicSphereEntity magic_sphere = new MagicSphereEntity(ModEntityTypes.MAGIC_SPHERE.get() ,level, Owner);
//        magic_sphere.shootFromRotation(magic_sphere.getOwner(), magic_sphere.getOwner().getXRot(), magic_sphere.getOwner().getYRot(),
//                0.0F, magic_sphere.getSpeed() * 3.0F, 0.0F);
//        return magic_sphere;
//    }
}
