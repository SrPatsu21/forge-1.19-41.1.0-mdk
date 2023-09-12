package com.github.uranus_mod_group.uranus_mod.entity.custom.projectile;

import com.github.uranus_mod_group.uranus_mod.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;

import java.util.List;

public class MagicSphereEntity extends AbstractUranusModProjectile
{
    private final float gravity = -0.05F;
    private int life = 200;
    private final float speed = 0.999F;
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
        float f = getSpeed();
        //if is underwater
        if (this.isInWater())
        {
            f *= getSpeed_on_water_r();
        }
        else if(this.isInRain())
        {
            f *= getSpeed_on_rain_r();
        }

        HitResult hit_result = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if(hit_result.getType() == HitResult.Type.MISS)
        {
            //set delta movement
            this.setDeltaMovement(vec3delta.scale(f));
            //if it has gravity it will move
            if (!this.isNoGravity())
            {
                Vec3 vec3delta2 = this.getDeltaMovement();
                this.setDeltaMovement(vec3delta2.x, vec3delta2.y + (double) getGravity(), vec3delta2.z);
            }
            //particles
            if (tickCount%4 == 0) {
                getLevel().addParticle(ModParticles.MAGIC_PARTICLES.get(), true,
                        hit_result.getLocation().x+(random.nextFloat() - random.nextFloat()),
                        hit_result.getLocation().y+(random.nextFloat() - random.nextFloat()),
                        hit_result.getLocation().z+(random.nextFloat() - random.nextFloat()),
                        0D, -0.07D, 0D);
            }
            this.setPos(d2, d0, d1);
        }else
        {
            this.setDeltaMovement(vec3delta.scale(0));
            this.setPos(hit_result.getLocation());
        }
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
    public float getSpeed()
    {
        return this.speed;
    }
    public float getSpeed_on_water_r()
    {
        return 0.999F;
    }
    public float getSpeed_on_rain_r()
    {
        return 0.930F;
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
    //particles
    private void spawnFoundParticles(Vec3 vec3, int radius) {
        for (int y = 0; y < 360; y += 15) {
            for (int x = 0; x < 180; x += 15) {
                getLevel().addParticle(ModParticles.MAGIC_PARTICLES.get(),
                        vec3.x + (radius*(Math.sin(x)*Math.cos(y))),
                        vec3.y + (radius*Math.sin(y)),
                        vec3.z + (radius*(-Math.cos(x)*Math.cos(y))),
                        0D, 0D, 0D);
            }
        }
    }
    //skill attributes
    private void setSkillAttributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    public final byte getSkillAttributes(int i)
    {
        return this.skill_attributes[i];
    }
    //radius
    public final byte getRadius()
    {
        if (this.skill_attributes != null){
            return (byte) (getSkillAttributes((this.skill_attributes.length-1))+1);
        }else
        {
            return 0;
        }
    }
    //skill functions
    public void reactionOnBlock(BlockPos block_pos2)
    {
        if (getLevel().getBlockState(block_pos2).isAir())
        {
            //ignite
            if (getSkillAttributes(0)>0)
            {
                //fire on floor
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
                //create water
//                getLevel().setBlock(block_pos2, new Blocks().WATER.defaultBlockState(), 1);
            }
        }
    }
    public void reactionOnEntity(BlockPos block_pos2, float distance_from_0)
    {
        List<Entity> list = getLevel().getEntities(this.getOwner(), new AABB(
                block_pos2.getX(), block_pos2.getY(), block_pos2.getZ(),
                (double)block_pos2.getX()+1, (double)block_pos2.getY()+1, (double)block_pos2.getZ()+1
        ));
        if(list != null)
        {
            for(int e = 0; e < list.size(); e++)
            {
                Entity entity = list.get(e);
                //damage
                entity.hurt(DamageSource.thrown(this, this.getOwner()), (getDamage()*(1-distance_from_0)));

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
        BlockPos block_pos2;
        for(int y = -getRadius()+1; y < getRadius(); y++)
        {
        for(int z = -getRadius()+1; z < getRadius(); z++)
        {
        for(int x = -getRadius()+1; x < getRadius(); x++)
        {
            //distance of the 0 point
            double distance = ((Math.abs(x) + Math.abs(y) + Math.abs(z)) / 3) * 3.14;
            if (distance < getRadius())
            {
                block_pos2 = new BlockPos(block_pos.getX() + x, block_pos.getY() + y, block_pos.getZ() + z);
                if (!getLevel().isClientSide)
                {
                    reactionOnBlock(block_pos2);
                    reactionOnEntity(block_pos2, (float) distance / getRadius());
                }
            }
        }
        }
        }
        //this discard the particle
        this.life = 2;
    }

    //on hit
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        BlockPos block_pos = blockPosition();
        //call reactions
        if (this.getOwner() != null && this.skill_attributes != null)
        {
            this.skillsReactions(block_pos);
        }
        spawnFoundParticles(hitResult.getLocation(), getRadius());
    }
    //on hit at an entity
    protected void onHitEntity(EntityHitResult hitResult)
    {
        super.onHitEntity(hitResult);
    }
    //on hit a block
    protected void onHitBlock(BlockHitResult hitResult)
    {
        super.onHitBlock(hitResult);
    }
    @Override
    protected void defineSynchedData()
    {

    }
}