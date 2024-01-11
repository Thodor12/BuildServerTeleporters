package com.ldtteam.buildserverteleporters.capability;

import com.ldtteam.buildserverteleporters.util.NBTUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.SignText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TeleporterStorageCapability implements ITeleporterStorageCapability
{
    private static final String TAG_TELEPORTERS = "teleporters";
    private static final String TAG_POSITION    = "position";
    private static final String TAG_NAME        = "name";
    private static final String TAG_SIGN_TEXT   = "signtext";

    public record TeleporterInfo(String name, SignText text)
    {
    }

    private final Map<BlockPos, TeleporterInfo> teleporters = new HashMap<>();

    public void readNBT(final CompoundTag compound)
    {
        final Map<BlockPos, TeleporterInfo> newTeleporters = new HashMap<>();

        final ListTag teleportersCompound = compound.getList(TAG_TELEPORTERS, Tag.TAG_COMPOUND);
        for (int i = 0; i < teleportersCompound.size(); i++)
        {
            final CompoundTag teleporterCompound = teleportersCompound.getCompound(i);
            final BlockPos position = NBTUtils.readBlockPos(teleporterCompound.getCompound(TAG_POSITION));
            final String name = teleporterCompound.getString(TAG_NAME);
            final SignText signText = SignText.DIRECT_CODEC.decode(NbtOps.INSTANCE, teleporterCompound.getCompound(TAG_SIGN_TEXT)).result()
                                        .map(Pair::getFirst)
                                        .orElse(null);
            newTeleporters.put(position, new TeleporterInfo(name, signText));
        }

        teleporters.clear();
        teleporters.putAll(newTeleporters);
    }

    public CompoundTag writeNBT()
    {
        final CompoundTag compound = new CompoundTag();

        final ListTag teleportersCompound = new ListTag();
        for (final Entry<BlockPos, TeleporterInfo> teleporter : teleporters.entrySet())
        {
            final CompoundTag teleporterCompound = new CompoundTag();
            teleporterCompound.put(TAG_POSITION, NBTUtils.writeBlockPos(teleporterCompound, teleporter.getKey()));
            teleporterCompound.putString(TAG_NAME, teleporter.getValue().name);
            SignText.DIRECT_CODEC.encodeStart(NbtOps.INSTANCE, teleporter.getValue().text)
              .result()
              .ifPresent(f -> teleporterCompound.put(TAG_SIGN_TEXT, f));

            teleportersCompound.add(teleporterCompound);
        }
        compound.put(TAG_TELEPORTERS, teleportersCompound);

        return compound;
    }

    @Override
    public @NotNull Map<BlockPos, TeleporterInfo> getAllTeleporters()
    {
        return Collections.unmodifiableMap(teleporters);
    }

    @Override
    public @Nullable TeleporterInfo getTeleporterAtPosition(final BlockPos position)
    {
        return teleporters.get(position);
    }
}
