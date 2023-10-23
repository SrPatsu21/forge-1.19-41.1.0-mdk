package com.github.uranus_mod_group.uranus_mod.skills;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ReactionsOutPlayer
{
    private Entity owner;
    private Entity entity_use;
    private Level level;
    private byte[] skill_attributes;
    private float damage;
    protected final RandomSource random = RandomSource.create();

    public ReactionsOutPlayer(Entity owner, Level level, byte[] skill_attributes, float damage, Entity entity_use)
    {
        setOwner(owner);
        setLevel(level);
        setSkill_attributes(skill_attributes);
        setDamage(damage);
        setEntity_use(entity_use);
    }
    //get set
    public Entity getOwner()
    {
        return owner;
    }
    public void setOwner(Entity owner)
    {
        this.owner = owner;
    }
    public Level getLevel()
    {
        return level;
    }
    public void setLevel(Level level)
    {
        this.level = level;
    }
    public void setSkill_attributes(byte[] skill_attributes)
    {
        this.skill_attributes = skill_attributes;
    }
    public float getDamage()
    {
        return damage;
    }
    public void setDamage(float damage)
    {
        this.damage = damage;
    }
    private byte getSkillAttributes(int i)
    {
        return this.skill_attributes[i];
    }
    public Entity getEntity_use() {
        return entity_use;
    }
    public void setEntity_use(Entity entity_use) {
        this.entity_use = entity_use;
    }

    //skill
    public void reactionOnEntity(BlockPos block_pos2, Vec3 vec3)
    {
        List<Entity> list = getLevel().getEntities(this.getOwner(), new AABB(
                block_pos2.getX(), block_pos2.getY(), block_pos2.getZ(),
                (double)block_pos2.getX()+1, (double)block_pos2.getY()+1, (double)block_pos2.getZ()+1
        ));
        if(list != null)
        {
            for(int e = 0; e < list.size(); e++)
            {
                if (list.get(e).showVehicleHealth()){
                    Entity entity = list.get(e);

                    //damage || heal
                    lifeDealt(entity, getDamage(),getSkillAttributes(10));
                    //lava || ignite || heat
                    if (getSkillAttributes(0)>3 || getSkillAttributes(4)>0 || getSkillAttributes(9) > 10)
                    {
                        setFire(entity, (int)(getSkillAttributes(0) + (getSkillAttributes(4)*2) + getSkillAttributes(9)/4) );
                    }
                    //water
                    if (getSkillAttributes(1)> 0 || getSkillAttributes(3) > 20)
                    {
                        entity.clearFire();
                    }
                    //stonep
                    //air || push
                    if (getSkillAttributes(3) != 0 || getSkillAttributes(15) != 0)
                    {
                        entity.moveTo(new Vec3(
                                vec3.x,
                                vec3.y,
                                vec3.z
                        ));
                    }
                    if(entity instanceof LivingEntity)
                    {
                        //sticky
                        if (getSkillAttributes(5) > 1) {
                            ((LivingEntity) entity).addEffect(
                                    new MobEffectInstance(MobEffects.DIG_SLOWDOWN,
                                            getSkillAttributes(5) * 10,
                                            ((int) (getSkillAttributes(5) / 6)) + 1));
                            ((LivingEntity) entity).addEffect(
                                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                                            getSkillAttributes(5) * 10,
                                            ((int) (getSkillAttributes(5) / 6)) + 1));
                        }
                        ///lux
                        if (getSkillAttributes(8) > 0)
                        {
                            ((LivingEntity) entity).addEffect(
                                    new MobEffectInstance(MobEffects.GLOWING,
                                            getSkillAttributes(8) * 20));
                        }else if (getSkillAttributes(8) < 0)
                        {
                            ((LivingEntity) entity).addEffect(
                                    new MobEffectInstance(MobEffects.BLINDNESS,
                                            getSkillAttributes(8) * 10*-1));
                        }
                        //poison
                        if(getSkillAttributes(11) > 0)
                        {
                            ((LivingEntity) entity).addEffect(
                                    new MobEffectInstance(MobEffects.POISON,
                                            getSkillAttributes(11) * 10,
                                            (int)(getSkillAttributes(11)*0.1F)));
                        }else if(getSkillAttributes(11) < 0)
                        {
                            ((LivingEntity) entity).addEffect(
                                    new MobEffectInstance(MobEffects.REGENERATION,
                                            getSkillAttributes(11) * 10*-1,
                                            (int)(getSkillAttributes(11)*0.1F)));
                        }
                        //wither
                        if (getSkillAttributes(12) > 0)
                        {
                            ((LivingEntity) entity).addEffect(
                                    new MobEffectInstance(MobEffects.WITHER,
                                            getSkillAttributes(12) * 5,
                                            (int)(getSkillAttributes(12)*0.05F)));
                        }
                        //teleport
                        if (getSkillAttributes(13) > 0)
                        {
                            ((LivingEntity) entity).randomTeleport(
                                    entity.getX() + random.nextInt(getSkillAttributes(13)),
                                    entity.getY()+ random.nextInt(getSkillAttributes(13)),
                                    entity.getZ() + random.nextInt(getSkillAttributes(13)),
                                    true);
                        }
                        //gravitational
                        if (getSkillAttributes(14) != 0)
                        {
                            entity.setDeltaMovement(0 ,(getSkillAttributes(14)/15), 0);
                        }
                    }
                }
            }
        }
    }
    public void lifeDealt(Entity entity, double damage, int bonus)
    {
        if ((damage + (bonus*-1)) > 0){
            entity.hurt(DamageSource.thrown(getEntity_use(), getOwner()), (float)(damage + (bonus*-1)/2));
        }else if (bonus > 0)
        {
            ((LivingEntity) entity).addEffect(
                    new MobEffectInstance(MobEffects.HEAL,
                            1, (int)(bonus-damage)/2));
        }
    }
    public void setFire(Entity entity, int time)
    {
        entity.setSecondsOnFire(time);
    }

    public void reactionOnBlock(BlockPos block_pos2)
    {
        if (
                (getLevel().getBlockState(block_pos2).isAir() || getLevel().getBlockState(block_pos2).is(Blocks.WATER) || getLevel().getBlockState(block_pos2).is(BlockTags.REPLACEABLE_PLANTS))
                        &&
                !(getLevel().getBlockState(block_pos2.below()).isAir() || getLevel().getBlockState(block_pos2.below()).is(Blocks.WATER) ||
                        getLevel().getBlockState(block_pos2.below()).is(BlockTags.REPLACEABLE_PLANTS) || getLevel().getBlockState(block_pos2.below()).is(Blocks.FIRE)
                ))
        {
            //fire on floor
            if (getSkillAttributes(0)>=10 || getSkillAttributes(4) >= 5 || getSkillAttributes(9) >= 20)
            {
                if (random.nextInt(120)<= getSkillAttributes(0)+(getSkillAttributes(4)*2)+(getSkillAttributes(9)/4))
                {
                    getLevel().setBlockAndUpdate(block_pos2, BaseFireBlock.getState(this.level, block_pos2.below()));
                }
            }
            //water
            if (getSkillAttributes(1)>19)
            {
                if (random.nextInt(120)<= getSkillAttributes(1) && !getLevel().dimensionType().ultraWarm())
                {
                    getLevel().setBlockAndUpdate(block_pos2, new Blocks().WATER.defaultBlockState());
                }
            }
            //stone 2
            //lava
            if (getSkillAttributes(4) > 20){
                if (random.nextInt(126) <= getSkillAttributes(4))
                {
                    getLevel().setBlockAndUpdate(block_pos2, new Blocks().LAVA.defaultBlockState());
                }
            }
            //elektron
            if (getSkillAttributes(7) > 10){
                if (random.nextInt(380) <= getSkillAttributes(7))
                {
                    LightningBolt lightningbolt = new LightningBolt(EntityType.LIGHTNING_BOLT, getLevel());
                    lightningbolt.setDamage(getSkillAttributes(7));
                    lightningbolt.setPos(block_pos2.getX(), block_pos2.getY()+1, block_pos2.getZ());
                    getLevel().addFreshEntity(lightningbolt);
                }
            }
        }else
        {
            //air
            if(getSkillAttributes(3) != 0)
            {
                if((getLevel().getBlockState(block_pos2.below()).is(BlockTags.LEAVES) ||
                        getLevel().getBlockState(block_pos2).is(BlockTags.REPLACEABLE_PLANTS)) &&
                        random.nextInt(126)<= Math.abs(getSkillAttributes(3)))
                {
                    getLevel().destroyBlock(block_pos2.below(), true, getOwner());
                }
            }
            //heat
            if(getSkillAttributes(9) != 0) {
                if(getSkillAttributes(9) > 10){
                    if(getLevel().getBlockState(block_pos2).is(Blocks.ICE))
                    {
                        getLevel().destroyBlock(block_pos2, true, getOwner(), 0);
                    }
                    if (random.nextInt(126) <= getSkillAttributes(9)) {
                        if (getLevel().getBlockState(block_pos2.below()).is(Blocks.WATER) && getSkillAttributes(9) > 10) {
                            getLevel().setBlockAndUpdate(block_pos2.below(), new Blocks().AIR.defaultBlockState());
                        } else if (getLevel().getBlockState(block_pos2.below()).is(BlockTags.SAND)) {
                            getLevel().setBlockAndUpdate(block_pos2.below(), new Blocks().GLASS.defaultBlockState());
                        } else if (getLevel().getBlockState(block_pos2.below()).is(Blocks.COBBLESTONE)) {
                            getLevel().setBlockAndUpdate(block_pos2.below(), new Blocks().STONE.defaultBlockState());
                        } else if (getLevel().getBlockState(block_pos2.below()).is(Blocks.COBBLED_DEEPSLATE)) {
                            getLevel().setBlockAndUpdate(block_pos2.below(), new Blocks().DEEPSLATE.defaultBlockState());
                        }
                    }
                }else if(getSkillAttributes(9)<-10)
                {
                    System.out.println(-1);
                    if(-1*random.nextInt(126) >= getSkillAttributes(9))
                    {
                        System.out.println(0);
                        if (getLevel().getBlockState(block_pos2.below()).is(Blocks.WATER) && getSkillAttributes(9) > 10)
                        {
                            System.out.println(1);
                            getLevel().setBlockAndUpdate(block_pos2.below(), new Blocks().ICE.defaultBlockState());
                        } else if (
                                !(getLevel().getBlockState(block_pos2.below()).isAir() || getLevel().getBlockState(block_pos2.below()).is(Blocks.WATER) ||
                                        getLevel().getBlockState(block_pos2.below()).is(BlockTags.REPLACEABLE_PLANTS) || getLevel().getBlockState(block_pos2.below()).is(Blocks.FIRE)
                                ))
                            System.out.println(2);
                        {
                            getLevel().setBlockAndUpdate(block_pos2, new Blocks().SNOW.defaultBlockState());
                            System.out.println(3);
                        }
                    }
                }

            }
        }
    }
}
