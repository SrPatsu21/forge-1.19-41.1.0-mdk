package com.github.uranus_mod_group.uranus_mod.entity.custom.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.*;


import java.util.List;

public class MagicSphereEntity extends AbstractUranusModProjectile
{
    private float gravity = -0.05F;
    private int life = 200;
    private final float speed = 0.999F;
    private final float speed_on_water_r = -0.1F;
    private final float speed_on_rain_r = -0.07F;
    private float damage = 0.0F;
    private byte[] skill_attributes;

    //constructor
    public MagicSphereEntity(EntityType<? extends MagicSphereEntity> entityEntityType,Level level)
    {
        super(entityEntityType, level);
    }
    public MagicSphereEntity(EntityType<? extends MagicSphereEntity> entityEntityType,Level level,double x ,double y,double z)
    {
        this(entityEntityType, level);
        this.setPos(x, y, z);
    }
    public MagicSphereEntity(EntityType<? extends MagicSphereEntity> entityEntityType, Level level, LivingEntity entity,
                             byte[] skill_attributes, float damage)
    {
        this(entityEntityType, level, entity.getX(),entity.getEyeY() - (double)0.1F, entity.getZ());
        super.setOwner(entity);
        this.setSkillAttributes(skill_attributes);
        this.setDamage(damage);
    }
    //on tick event
    public void tick()
    {
        super.tick();
        tickOutSpawn();
        //position
        this.checkInsideBlocks();
        Vec3 vec3delta = this.getDeltaMovement();
        double d2 = this.getX() + vec3delta.x;
        double d0 = this.getY() + vec3delta.y;
        double d1 = this.getZ() + vec3delta.z;

        this.updateRotation();
        //speed
        float f;
        //if is underwater
        if (this.isInWater())
        {
            f = getSpeed_on_water_r();
        }
        else if(this.isInRain())
        {
            f = getSpeed_on_rain_r();
        }
        else
        {
            f = getSpeed();
        }
        //set delta movement
        this.setDeltaMovement(vec3delta.scale(f));
        //if it has gravity it will move
        if (!this.isNoGravity()) {
            Vec3 vec3delta2 = this.getDeltaMovement();
            this.setDeltaMovement(vec3delta2.x, vec3delta2.y + (double)getGravity(), vec3delta2.z);
        }

        this.setPos(d2, d0, d1);
    }
    //tick count to entity disappear
    protected void tickOutSpawn()
    {
        this.life--;
        if (this.life <= 0)
        {
            this.discard();
        }
    }
    //gravity
    public float getGravity()
    {
        return this.gravity;
    }
    //speed
    public float getSpeed(){
        return this.speed;
    }
    public float getSpeed_on_water_r(){
        return (this.speed + this.speed_on_water_r);
    }
    public float getSpeed_on_rain_r(){
        return (this.speed + this.speed_on_rain_r);
    }
    //damage
    private void setDamage(float damage)
    {
        this.damage = damage;
    }
    public float getDamage()
    {
        return this.damage;
    }
    //skill attributes
    private void setSkillAttributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    public byte getSkillAttributes(int i)
    {
        return this.skill_attributes[i];
    }
    //skill functions
    public void reactionOnBlock(BlockPos block_pos2){
        if (getLevel().getBlockState(block_pos2).isAir())
        {
            //ignite
            if (getSkillAttributes(0)>0)
            {
                if (getSkillAttributes(0) > 10)
                {
                    if (random.nextInt(126)<= getSkillAttributes(0))
                    {
                        getLevel().setBlockAndUpdate(block_pos2, BaseFireBlock.getState(this.level, block_pos2));
                    }
                }
            }
            //water
            if (getSkillAttributes(1)>0)
            {
                getLevel().setBlock(block_pos2, new Blocks().WATER.defaultBlockState(), 1);
            }
        }
    }
    public void reactionOnEntity(BlockPos block_pos2, float distance_from_0)
    {
        List<Entity> list = this.level.getEntities(this.getOwner(), new AABB(
                block_pos2.getX(), block_pos2.getY(), block_pos2.getZ(),
                (double)block_pos2.getX()+1, (double)block_pos2.getY()+1, (double)block_pos2.getZ()+1
        ));
        if(list != null)
        {
            for(int e = 0; e < list.size(); e++)
            {
                Entity entity = list.get(e);
                //damage
                if (this.getOwner() != null)
                {
                    entity.hurt(DamageSource.thrown(this, this.getOwner()), (getDamage()*(1-distance_from_0)));
                }

                //ignite
                if (getSkillAttributes(0)>0)
                {
                    entity.setSecondsOnFire(getSkillAttributes(0));
                }
                //water
                if (getSkillAttributes(1)> 0)
                {
                    entity.clearFire();
                }

            }
        }
    }
    public void skillsReactions(BlockPos block_pos)
    {
        int radius = (int) getSkillAttributes(21)+1;
        BlockPos block_pos2;
        for(int y = -radius+1; y < radius; y++)
        {
            for(int z = -radius+1; z < radius; z++)
            {
                for(int x = -radius+1; x < radius; x++)
                {
                    //distance of the 0 point
                    double distance = ((Math.abs(x)+Math.abs(y)+Math.abs(z))/3) *3.14;
                    if(distance < radius)
                    {
                        block_pos2 = new BlockPos(block_pos.getX()+x, block_pos.getY()+y, block_pos.getZ()+z);
                        reactionOnBlock(block_pos2);
                        reactionOnEntity(block_pos2, (float) distance/radius);
                    }
                }
            }
        }
    }

    //on hit
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        if (!getLevel().isClientSide)
        {
            BlockPos block_pos = blockPosition();
            //??????????
            getLevel().broadcastEntityEvent(this, (byte) 3);
            //call reactions
            this.skillsReactions(block_pos);
        }
    }
    //on hit at an entity
    protected void onHitEntity(EntityHitResult hitResult)
    {
        super.onHitEntity(hitResult);
        this.discard();
    }
    //on hit a block
    protected void onHitBlock(BlockHitResult hitResult)
    {
        super.onHitBlock(hitResult);
        this.discard();
    }
    @Override
    protected void defineSynchedData()
    {

    }
}