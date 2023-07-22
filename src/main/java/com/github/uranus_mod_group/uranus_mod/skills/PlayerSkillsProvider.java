package com.github.uranus_mod_group.uranus_mod.skills;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerSkillsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>
{
    //capability
    public static Capability<PlayerSkills> PLAYER_SKILLS = CapabilityManager.get(
            new CapabilityToken<PlayerSkills>()
            {

            }
    );
    //create skills
    private PlayerSkills skills = null;
    private final LazyOptional<PlayerSkills> optional = LazyOptional.of(this::createPlayerSkills);

    private PlayerSkills createPlayerSkills()
    {
        if(this.skills == null)
        {
            this.skills = new PlayerSkills();
        }

        return this.skills;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if(cap == PLAYER_SKILLS)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT()
    {
        CompoundTag nbt = new CompoundTag();
        createPlayerSkills().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt)
    {
        createPlayerSkills().loadNBTData(nbt);
    }
}
