package com.ldtteam.buildserverteleporters.tileentity;

import com.ldtteam.buildserverteleporters.initializers.RegistryInitializer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The teleporter block tile entity.
 */
public class TeleporterBlockEntity extends SignBlockEntity
{
    public TeleporterBlockEntity(
      final BlockPos pos,
      final BlockState blockState)
    {
        super(RegistryInitializer.TELEPORTER_ENTITY_TYPE, pos, blockState);
    }
}
