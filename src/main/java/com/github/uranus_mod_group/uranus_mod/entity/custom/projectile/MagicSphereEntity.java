package com.github.uranus_mod_group.uranus_mod.entity.custom.projectile;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
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
    private byte[] player_attributes;
//0    fire 1
//1    water 2
//2    stone 3
//3    air 4
//4    elektron 5
//5    lava 6
//6    construct 7 8
//7    body manipulation 9 10 11
//8    ender magic 12
//9    lux 13
//10   heat 14
//11   blood 15
//12   mana manipulation 16 17
//13   explosion 18
//14   gravity 19 20
//15   summon 21
    //Skill that it will up
    private final byte [] respective_skill =
            {
                    0,
                    1,
                    2,
                    3,
                    4,
                    5,
                    6,
                    6,
                    7,
                    7,
                    7,
                    8,
                    9,
                    10,
                    11,
                    12,
                    12,
                    13,
                    14,
                    14,
                    15
            };

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
                             byte[] skill_attributes, byte[] player_attributes)
    {
        this(entityEntityType, level, entity.getX(),entity.getEyeY() - (double)0.1F, entity.getZ());
        super.setOwner(entity);
        this.skill_attributes = skill_attributes;
        this.player_attributes = player_attributes;
        //this.setDamage(skill_attributes, player_attributes);
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
    private void setDamage(byte[] skill_attributes, byte[] player_attributes)
    {
        for(int i = 0; i < skill_attributes.length; i++)
        {
            this.damage += (float) skill_attributes[i] * player_attributes[(this.respective_skill[i])];
        }
    }
    public float getDamage()
    {
        return this.damage;
    }
    //on hit
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        if (!this.level.isClientSide)
        {
            if (this.getOwner() != null)
            {
//                this.level.broadcastEntityEvent(this, (byte) 3);
//                this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.BREAK);
                this.discard();
            }
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