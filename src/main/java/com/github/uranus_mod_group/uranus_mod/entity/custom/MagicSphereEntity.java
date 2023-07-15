package com.github.uranus_mod_group.uranus_mod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;

import javax.annotation.Nullable;


public class MagicSphereEntity extends Projectile  {
    @Nullable
    private int life;
    @Nullable
    //create entity
    public MagicSphereEntity(EntityType<? extends Projectile> entityType, Level level)
    {
        super(entityType, level);
    }
    //render distance
    public boolean shouldRenderAtSqrDistance(double d3) {
        double d0 = this.getBoundingBox().getSize() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }

        d0 *= 64.0D * getViewScale();
        return d3 < d0 * d0;
    }

    @Override
    protected void defineSynchedData() {

    }

    //tick event
    public void tick() {
        super.tick();
        tickDespawn();
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        //if hit at a block is a portal
        if (hitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult)hitresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockpos, blockstate, this, (TheEndGatewayBlockEntity)blockentity);
                }

                flag = true;
            }
        }
        //hit result
        //if type ! MISS
        //flag is flase
        //onProjectileImpact is false
        if (hitresult.getType() != HitResult.Type.MISS
                && !flag
                && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }
        //position
        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        this.updateRotation();
        //speed i think
        float f;
        //if is underwater
        if (this.isInWater())
        {
            for(int i = 0; i < 4; ++i)
            {
                this.level.addParticle(ParticleTypes.BUBBLE, d2 - vec3.x * 0.25D,
                        d0 - vec3.y * 0.25D, d1 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
            }

            f = 0.8F;
        } else
        {
            f = 0.99F;
        }
        this.setDeltaMovement(vec3.scale((double)f));
        //if it has gravity
        if (!this.isNoGravity()) {
            Vec3 vec31 = this.getDeltaMovement();
            this.setDeltaMovement(vec31.x, vec31.y - (double)this.getGravity(), vec31.z);
        }

        this.setPos(d2, d0, d1);
    }
    //tick count to entity disappear
    protected void tickDespawn()
    {
        ++this.life;
        if (this.life >= 1200)
        {
            //this.discard();
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
    //on hit
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        if (!this.level.isClientSide)
        {
            this.level.broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }
    //find the entity to hit
    @Nullable
    protected EntityHitResult findHitEntity(Vec3 position1, Vec3 position2)
    {
        return ProjectileUtil.getEntityHitResult(this.level, this, position1, position2,
                this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }
    //set player and verify if is a player
    public void setOwner(@Nullable Entity entity)
    {
        super.setOwner(entity);
    }

    //say that projectile cant be attack
    public boolean isAttackable()
    {
        return false;
    }
    //??????
    protected float getEyeHeight(Pose p_36752_, EntityDimensions p_36753_)
    {
        return 0.13F;
    }
    //when the projectile is fall
    protected float getGravity()
    {
        return 0.1F;
    }
}