package com.ldtteam.buildserverteleporters.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TeleporterStorageCapabilityProvider implements ICapabilitySerializable<Tag>
{
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull final Capability<T> cap, @Nullable final Direction side)
    {
        return null;
    }

    @Override
    public Tag serializeNBT()
    {
        return null;
    }

    @Override
    public void deserializeNBT(final Tag nbt)
    {

    }
}
