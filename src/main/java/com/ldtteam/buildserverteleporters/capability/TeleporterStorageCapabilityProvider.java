package com.ldtteam.buildserverteleporters.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.ldtteam.buildserverteleporters.BuildServerTeleporters.STORAGE_CAPABILITY;

public class TeleporterStorageCapabilityProvider implements ICapabilitySerializable<CompoundTag>
{
    private final TeleporterStorageCapability storageCapability;

    private final LazyOptional<ITeleporterStorageCapability> storageCapabilityOptional;

    public TeleporterStorageCapabilityProvider()
    {
        storageCapability = new TeleporterStorageCapability();
        storageCapabilityOptional = LazyOptional.of(() -> storageCapability);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull final Capability<T> cap, @Nullable final Direction side)
    {
        return STORAGE_CAPABILITY.orEmpty(cap, storageCapabilityOptional);
    }

    @Override
    public CompoundTag serializeNBT()
    {
        return storageCapability.writeNBT();
    }

    @Override
    public void deserializeNBT(final CompoundTag nbt)
    {
        storageCapability.readNBT(nbt);
    }
}
