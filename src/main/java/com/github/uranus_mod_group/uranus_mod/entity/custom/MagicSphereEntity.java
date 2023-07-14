package com.github.uranus_mod_group.uranus_mod.entity.custom;

import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;


public class MagicSphereEntity extends AbstractArrow{

    //create entity
    public MagicSphereEntity(EntityType<? extends AbstractArrow> entityType, Level world)
    {
        super(entityType, world);
    }
    //hit by entity
    @Override
    protected void onHitEntity(EntityHitResult ray)
    {
        super.onHitEntity(ray);
        this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.BREAK);
    }
    //when hit by block
    @Override
    protected void onHitBlock(BlockHitResult ray)
    {
        super.onHitBlock(ray);
        BlockState theBlockYouHit = this.level.getBlockState(ray.getBlockPos());
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    //make despawn
    @Override
    protected void tickDespawn() {
        if (this.inGroundTime > 30){
            this.level.explode(this, this.getX(), this.getY(), this.getZ(), 4.0f, true, Explosion.BlockInteraction.BREAK);
            this.remove(null);
        }
    }
    //render the entity
    @Override
    public Packet<?> getAddEntityPacket() {
        return super.getAddEntityPacket();
    }
}