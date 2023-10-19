package com.github.uranus_mod_group.uranus_mod.entity.custom.projectile;

import com.github.uranus_mod_group.uranus_mod.particles.ModParticles;
import com.github.uranus_mod_group.uranus_mod.skills.ReactionsOutPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MagicLaserEntity extends AbstractUranusModProjectile
{
    public final float GRAVITY = -0.02F;
    public final float SPEED = 0.99F;
    public final float SPEED_ON_WATER_R = 0.95F;
    public final float SPEED_ON_RAIN_R = 0.97F;
    private int life = 200;
    private ReactionsOutPlayer reaction;

    public MagicLaserEntity(EntityType<? extends Projectile> entity_type, Level level)
    {
        super(entity_type, level);
    }
    public MagicLaserEntity(EntityType<? extends MagicLaserEntity> entityEntityType, Level level, double x , double y, double z)
    {
        this(entityEntityType, level);
        this.setPos(x, y, z);
    }
    public MagicLaserEntity(EntityType<? extends MagicLaserEntity> entityEntityType, Level level, LivingEntity entity,
                            byte[] skill_attributes, float damage)
    {
        this(entityEntityType, level, entity.getX(),entity.getEyeY() - (double)0.1F, entity.getZ());
        super.setOwner(entity);
        this.setSkillAttributes(skill_attributes);
        this.setDamage(damage);
        reaction = new ReactionsOutPlayer(getOwner(), getLevel(), getSkillAttributes(), getDamage(), this);
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
        //speed
        float f = SPEED;
        //if is underwater
        if (this.isInWater())
        {
            f = SPEED_ON_WATER_R;
        }
        else if(this.isInRain())
        {
            f = SPEED_ON_RAIN_R;
        }

        HitResult hit_result = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if(hit_result.getType() == HitResult.Type.MISS)
        {
            //set delta movement
            this.setDeltaMovement(vec3delta.scale(f));
            //if it has gravity it will move
            if (!this.isNoGravity())
            {
                Vec3 vec3delta2 = getDeltaMovement();
                this.setDeltaMovement(vec3delta2.x, vec3delta2.y + (double) GRAVITY, vec3delta2.z);
            }
            //particles
            if (tickCount%4 == 0)
            {
                getLevel().addParticle(ModParticles.MAGIC_PARTICLES.get(), true,
                        hit_result.getLocation().x+random.nextFloat()/1.5,
                        hit_result.getLocation().y+random.nextFloat()/1.5,
                        hit_result.getLocation().z+random.nextFloat()/1.5,
                        0D, -0.07D, 0D);
            }
            this.setPos(d2, d0, d1);
            this.checkInsideBlocks();
        }else
        {
            this.setDeltaMovement(vec3delta.scale(0));
            this.setPos(hit_result.getLocation());
        }
    }
    protected void tickOutSpawn()
    {
        this.life--;
        if (this.life <= 0)
        {
            this.discard();
        }
    }
    public boolean isNoPhysics() {
        if (!this.level.isClientSide) {
            return this.noPhysics;
        } else {
            return false;
        }
    }
    //on hit
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        this.life = 2;
    }
    //on hit at an entity
    protected void onHitEntity(EntityHitResult hitResult)
    {
        super.onHitEntity(hitResult);
        if (this.getOwner() != null && this.getSkillAttributes() != null)
        {
            reaction.reactionOnEntity(new BlockPos(hitResult.getLocation()), hitResult.getLocation());
        }
    }
    //on hit a block
    protected void onHitBlock(BlockHitResult hitResult)
    {
        super.onHitBlock(hitResult);
        if (this.getOwner() != null && this.getSkillAttributes() != null)
        {
            reaction.reactionOnBlock(hitResult.getBlockPos().above());
        }
    }
//    @Override
//    protected void defineSynchedData()
//    {
//
//    }
}
