package com.github.uranus_mod_group.uranus_mod.entity.custom.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public abstract class AbstractUranusModProjectile extends Projectile
{
    private float damage = 0.0F;
    private byte[] skill_attributes;
    //constructor
    protected AbstractUranusModProjectile(EntityType<? extends Projectile> entity_type, Level level)
    {
        super(entity_type, level);
    }
    //render distance
    public boolean shouldRenderAtSqrDistance(double d3)
    {
        double d0 = this.getBoundingBox().getSize() * 10.0D;
        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }

        d0 *= 64.0D * getViewScale();
        return d3 < d0 * d0;
    }
    public boolean isAttackable()
    {
        return false;
    }
    //tick event
    public void tick()
    {
        super.tick();
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
                && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult))
        {
            this.onHit(hitresult);
        }
    }
    protected boolean isInRain()
    {
        return this.level.isRainingAt(this.blockPosition()) || this.level.isRainingAt(new BlockPos((double)this.blockPosition().getX(), this.getBoundingBox().maxY, (double)this.blockPosition().getZ()));
    }
    //damage
    protected void setDamage(float damage)
    {
        this.damage = damage;
    }
    public float getDamage()
    {
        return this.damage;
    }
    //attributes
    protected void setSkillAttributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    public byte[] getSkillAttributes()
    {
        return this.skill_attributes;
    }
    //owner
    public void setOwner(@Nullable Entity entity)
    {
        super.setOwner(entity);
    }
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
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
