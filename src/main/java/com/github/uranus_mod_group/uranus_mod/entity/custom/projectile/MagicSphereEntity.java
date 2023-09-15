package com.github.uranus_mod_group.uranus_mod.entity.custom.projectile;

import com.github.uranus_mod_group.uranus_mod.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.*;
import org.openjdk.nashorn.api.tree.BlockTree;
import org.openjdk.nashorn.api.tree.Tree;

import javax.annotation.Nullable;
import java.util.List;

public class MagicSphereEntity extends AbstractUranusModProjectile
{
    private int life = 200;
    private float damage = 0.0F;
    private byte[] skill_attributes;
    private final int RADIUS = 5;
    public final float GRAVITY = -0.02F;
    public final float SPEED = 0.96F;
    public final float SPEED_ON_WATER_R = 0.91F;
    public final float SPEED_ON_RAIN_R = 0.94F;

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
                Vec3 vec3delta2 = this.getDeltaMovement();
                this.setDeltaMovement(vec3delta2.x, vec3delta2.y + (double) GRAVITY, vec3delta2.z);
            }
            //particles
            if (tickCount%4 == 0) {
                getLevel().addParticle(ModParticles.MAGIC_PARTICLES.get(), true,
                        hit_result.getLocation().x+random.nextFloat()/1.5,
                        hit_result.getLocation().y+random.nextFloat()/1.5,
                        hit_result.getLocation().z+random.nextFloat()/1.5,
                        0D, -0.07D, 0D);
            }
            this.setPos(d2, d0, d1);
        }else
        {
            this.setDeltaMovement(vec3delta.scale(0));
            this.setPos(hit_result.getLocation());
            spawnFoundParticles(hit_result.getLocation());
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
    //damage
    protected void setDamage(float damage)
    {
        this.damage = damage;
    }
    public float getDamage()
    {
        return this.damage;
    }
    //particles
    public void spawnFoundParticles(Vec3 vec3)
    {
        for(int y = -RADIUS+1; y < RADIUS; y++)
        {
        for(int z = -RADIUS+1; z < RADIUS; z++)
        {
        for(int x = -RADIUS+1; x < RADIUS; x++)
        {
            if (((int)((Math.abs(x) + Math.abs(y) + Math.abs(z)) / 3) * 3.14) < this.RADIUS)
            {
                getLevel().addParticle(ModParticles.MAGIC_PARTICLES.get(),
                        vec3.x + x + random.nextFloat()/1.5,
                        vec3.y + y + random.nextFloat()/1.5,
                        vec3.z + z + random.nextFloat()/1.5,
                        0D, -0.01D, 0D);
            }
        }
        }
        }
    }
    //skill attributes
    protected void setSkillAttributes(byte[] attributes)
    {
        this.skill_attributes = attributes;
    }
    protected byte getSkillAttributes(int i)
    {
        return this.skill_attributes[i];
    }
    //skill functions
    protected void reactionOnBlock(BlockPos block_pos2)
    {
        if (getLevel().getBlockState(block_pos2).isAir() && getLevel().getBlockState(block_pos2.below()).isCollisionShapeFullBlock(getLevel(), block_pos2.below()))
        {
            //fire on floor
            if (getSkillAttributes(0)>=10 || getSkillAttributes(4) >= 5)
            {
                if (random.nextInt(120)<= getSkillAttributes(0)+(getSkillAttributes(4)*2))
                {
                    getLevel().setBlockAndUpdate(block_pos2, BaseFireBlock.getState(this.level, block_pos2.below()));
                }
            }
            //water
            if (getSkillAttributes(1)>19)
            {
                if (random.nextInt(120)<= getSkillAttributes(1))
                {
                    getLevel().setBlock(block_pos2, new Blocks().WATER.defaultBlockState(), 120);
                }
            }
            //stone 2
            //lava
            if (random.nextInt(126) <= getSkillAttributes(4) && getSkillAttributes(4) > 20)
            {
               getLevel().setBlock(block_pos2, new Blocks().LAVA.defaultBlockState(), 120);
            }
        }else
        {
            //air
            if(getLevel().getBlockState(block_pos2).is(BlockTags.LEAVES) &&
                    random.nextInt(126)<= getSkillAttributes(3))
            {
                getLevel().destroyBlock(block_pos2, true, getOwner());
            }
        }
    }
    protected void reactionOnEntity(BlockPos block_pos2, Vec3 vec3)
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
                entity.hurt(DamageSource.thrown(this, this.getOwner()), getDamage());
                //ignite
                //lava
                if (getSkillAttributes(0)>3 || getSkillAttributes(4)>0)
                {
                    entity.setSecondsOnFire(getSkillAttributes(0)+(getSkillAttributes(4)*2));
                }
                //water
                if (getSkillAttributes(1)> 0)
                {
                    entity.clearFire();
                }
                //stone
                //air
                if (getSkillAttributes(3)> 0)
                {
                    entity.moveTo(new Vec3(
                            vec3.x+((vec3.x - block_pos2.getX())),
                            vec3.y+((vec3.y - block_pos2.getY())),
                            vec3.z+((vec3.z - block_pos2.getZ()))
                    ));
                }
                //sticky
                if(getSkillAttributes(5)>1)
                {
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, getSkillAttributes(5)*10, 2));
                        ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, getSkillAttributes(5), 2));
                    }
                }
            }
        }
    }
    protected void skillsReactions(Vec3 vec3)
    {
        BlockPos block_pos;
        for(int y = -RADIUS+1; y < RADIUS; y++)
        {
        for(int z = -RADIUS+1; z < RADIUS; z++)
        {
        for(int x = -RADIUS+1; x < RADIUS; x++)
        {
            //distance of the 0 point
            if (((int)((Math.abs(x) + Math.abs(y) + Math.abs(z)) / 3) * 3.14) < this.RADIUS)
            {
                block_pos = new BlockPos(vec3.x + x, vec3.y + y, vec3.z + z);
                    reactionOnBlock(block_pos);
                    reactionOnEntity(block_pos, vec3);
            }
        }
        }
        }
    }

    //on hit
    protected void onHit(HitResult hitResult)
    {
        super.onHit(hitResult);
        //call reactions
        if (this.getOwner() != null && this.skill_attributes != null)
        {
            this.skillsReactions(hitResult.getLocation());
        }
        spawnFoundParticles(hitResult.getLocation());
        this.life = 1;
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