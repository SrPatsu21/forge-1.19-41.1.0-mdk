package com.github.uranus_mod_group.uranus_mod.entity.custom.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

public class MagicSphereEntity extends AbstractUranusModProjectile
{
    private float gravity = 0.2F;
    private int life = 1200;
    private float speed = 0.9F;
    private float speed_on_water_r = 0.1F;
    private float damage = 0.0F;

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
    public MagicSphereEntity(EntityType<? extends MagicSphereEntity> entityEntityType, Level level, LivingEntity entity)
    {
        this(entityEntityType, level, entity.getX(),entity.getEyeY() - (double)0.1F, entity.getZ());
        this.setOwner(entity);
    }


    public void tick(){
        super.tick();
        tickOutSpawn();

        //position
        this.checkInsideBlocks();
        Vec3 vec3delta = this.getDeltaMovement();
        double d2 = this.getX() + vec3delta.x;
        double d0 = this.getY() + vec3delta.y;
        double d1 = this.getZ() + vec3delta.z;
        //movement
        double d4 = super.getOwner().getX();
        double d3 = super.getOwner().getY();
        double d5 = super.getOwner().getZ();

        this.updateRotation();
        //speed
        float f;
        //if is underwater
        if (this.isInWater())
        {
            f = getSpeed_on_water_r();
        } else
        {
            f = getSpeed();
        }
        //set delta movement
        this.setDeltaMovement(vec3delta.scale(f));
        //if it has gravity it will move
        if (!this.isNoGravity()) {
            Vec3 vec3delta2 = this.getDeltaMovement();
            this.setDeltaMovement(vec3delta2.x+d4, vec3delta2.y - (double)getGravity() +d3, vec3delta2.z+d5);
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
    private float getGravity()
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
    protected float getSpeed(){
        return this.getSpeed();
    }
    protected float getSpeed_on_water_r(){
        return (this.speed + this.speed_on_water_r);
    }
    //on hit
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        if (!this.level.isClientSide)
        {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.BREAK);
            this.discard();
        }

    }
    //on hit at an entity
    protected void onHitEntity(EntityHitResult hitResult)
    {
        super.onHitEntity(hitResult);
        Entity entity = hitResult.getEntity();
        int i = entity instanceof Blaze ? 3 : 0;
        entity.hurt(DamageSource.thrown(this, this.getOwner()), (float)i);
    }
    //on hit a block
    protected void onHitBlock(BlockHitResult p_37258_)
    {
        super.onHitBlock(p_37258_);
        this.discard();
    }
    @Override
    protected void defineSynchedData()
    {

    }
}