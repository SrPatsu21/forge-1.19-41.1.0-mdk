package com.github.uranus_mod_group.uranus_mod.entity.custom.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.*;

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
        this.skill_attributes = skill_attributes;
        this.setDamage(damage);
    }
    //on tick event
    public void tick(){
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
    private void addGravity(float gravity)
    {
        this.gravity += gravity;
    }
    private void subGravity(float gravity)
    {
        this.gravity -= gravity;
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
    public byte getSkillAttributes(int i)
    {
        return this.skill_attributes[i];
    }
    //action area
    //1 or >=5
    public int getVolume(int size)
    {
        int v;
        if (size > 1) {
            v = (int) Math.pow(size, 3)- size+1;
        }else{
            v = 1;
        }
        return v;
    }
    public BlockPos[] actionArea(BlockPos block_pos, int size)
    {
        if (size > 3) {
            size = size/2;
        }else {
            size = 0;
        }

        BlockPos[] action_area = new BlockPos[getVolume(size)];
        int cont = 0;

        for(int z = -size+1; z < size; z++)
        {
            for(int y = -size+1; y < size; y++)
            {
                for(int x = -size+1; x < size; x++)
                {
                    if((Math.abs(x)+Math.abs(y)+Math.abs(z)) < size &&  cont < getVolume(size))
                    {
                        action_area[cont] = new BlockPos(block_pos.getX()+x, block_pos.getY()+y, block_pos.getZ()+z);
                        cont++;
                    }
                }
            }
        }
        return action_area;
    }

    //on hit
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        if (!getLevel().isClientSide)
        {
            BlockPos block_pos = blockPosition();
            getLevel().broadcastEntityEvent(this, (byte) 3);
            if(getSkillAttributes(0) != 0)
            {
                BlockPos[] action_area = actionArea(block_pos, getSkillAttributes(0));
                for(BlockPos block : action_area)
                {
                    if (getLevel().getBlockState(block).isAir())
                    {
                        getLevel().setBlockAndUpdate(block, BaseFireBlock.getState(this.level, block_pos));
                    }
                }
            }
            this.discard();
        }

    }
    //on hit at an entity
    protected void onHitEntity(EntityHitResult hitResult)
    {
        if (this.getOwner() != null)
        {
            super.onHitEntity(hitResult);
            Entity entity = hitResult.getEntity();
            entity.hurt(DamageSource.thrown(this, this.getOwner()), getDamage());
//            if(getSkillAttributes(0) != 0)
//            {
//                entity.setSecondsOnFire(getSkillAttributes(0));
//            }
        }
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