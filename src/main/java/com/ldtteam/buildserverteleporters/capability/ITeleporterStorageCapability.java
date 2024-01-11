package com.ldtteam.buildserverteleporters.capability;

import com.ldtteam.buildserverteleporters.capability.TeleporterStorageCapability.TeleporterInfo;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface ITeleporterStorageCapability
{
    @NotNull
    Map<BlockPos, TeleporterInfo> getAllTeleporters();

    @Nullable
    TeleporterInfo getTeleporterAtPosition(final BlockPos position);
}
