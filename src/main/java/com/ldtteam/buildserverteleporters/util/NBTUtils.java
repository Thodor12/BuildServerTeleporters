package com.ldtteam.buildserverteleporters.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

public class NBTUtils
{
    public static BlockPos readBlockPos(@NotNull final CompoundTag compound)
    {
        final int x = compound.getInt("x");
        final int y = compound.getInt("y");
        final int z = compound.getInt("z");
        return new BlockPos(x, y, z);
    }

    public static CompoundTag writeBlockPos(@NotNull final CompoundTag compound, @NotNull final BlockPos pos)
    {
        @NotNull final CompoundTag coordsCompound = new CompoundTag();
        coordsCompound.putInt("x", pos.getX());
        coordsCompound.putInt("y", pos.getY());
        coordsCompound.putInt("z", pos.getZ());
        return compound;
    }
}
