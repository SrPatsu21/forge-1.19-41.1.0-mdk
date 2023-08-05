package com.github.uranus_mod_group.uranus_mod.entity.custom.projectile;

import com.google.common.collect.Sets;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import net.minecraftforge.event.TickEvent;
import org.openjdk.nashorn.internal.objects.annotations.Function;

import java.util.Set;

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
    private void setSkillAttributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    public byte getSkillAttributes(int i)
    {
        return this.skill_attributes[i];
    }
    //skill functions

    public void setFire(BlockPos block_pos)
    {
        int size = (int) getSkillAttributes(0)/5 +1;
        BlockPos block_pos2;
        Set<BlockPos> set = Sets.newHashSet();
        for(int y = -size+1; y < size; y++)
        {
            for(int z = -size+1; z < size; z++)
            {
                for(int x = -size+1; x < size; x++)
                {
                    if((Math.abs(x)+Math.abs(y)+Math.abs(z)) < size)
                    {
                        block_pos2 = new BlockPos(block_pos.getX()+x, block_pos.getY()+y, block_pos.getZ()+z);
                        if (getLevel().getBlockState(block_pos2).isAir())
                        {
                            getLevel().setBlockAndUpdate(block_pos2, BaseFireBlock.getState(this.level, block_pos));
                            getLevel().addParticle(ParticleTypes.FLAME,
                                    block_pos2.getX(), block_pos2.getY(), block_pos2.getZ(),
                                    0.0D, 0.2D, 0.0D);
                            getLevel().addParticle(new BlockParticleOption(ParticleTypes.BLOCK_MARKER, getLevel().getBlockState(block_pos2)),
                                    block_pos2.getX(), block_pos2.getY(), block_pos2.getZ(),
                                    0.0D, 0.2D, 0.0D);
                        }
                    }
                }
            }
        }
    }
    //does not have a way to add particles
//    Explosion
//    if (this.level.isClientSide) {
//        this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
//    }
//    boolean flag = this.blockInteraction != Explosion.BlockInteraction.NONE;
//    if (p_46076_) {
//        if (!(this.radius < 2.0F) && flag) {
//            this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
//        } else {
//            this.level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
//        }
//    }
    //on hit
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        if (!getLevel().isClientSide)
        {
            BlockPos block_pos = blockPosition();
            getLevel().broadcastEntityEvent(this, (byte) 3);
            //fire skill
            if(getSkillAttributes(0) > 0)
            {
                this.setFire(block_pos);
            }
            //water
        }
    }
    //on hit at an entity
    protected void onHitEntity(EntityHitResult hitResult)
    {
        super.onHitEntity(hitResult);
        Entity entity = hitResult.getEntity();
        if (this.getOwner() != null && this.skill_attributes != null)
        {
            entity.hurt(DamageSource.thrown(this, this.getOwner()), getDamage());
            if(getSkillAttributes(0) != 0)
            {
                entity.setSecondsOnFire(getSkillAttributes(0));
            }
        }
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