package com.ldtteam.buildserverteleporters;

import com.ldtteam.buildserverteleporters.capability.ITeleporterStorageCapability;
import com.ldtteam.buildserverteleporters.constants.Constants;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.fml.common.Mod;

/**
 * Mod entrypoint.
 */
@Mod(Constants.MOD_ID)
public class BuildServerTeleporters
{
    public static final Capability<ITeleporterStorageCapability> STORAGE_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    public BuildServerTeleporters()
    {
    }
}
