package com.ldtteam.buildserverteleporters.block;

import com.ldtteam.buildserverteleporters.tileentity.TeleporterBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The teleporter block definition.
 */
public class TeleporterBlock extends HorizontalDirectionalBlock implements EntityBlock
{
    /**
     * The block ID.
     */
    public static final String ID = "teleporter";

    /**
     * Default constructor.
     */
    public TeleporterBlock()
    {
        super(Properties.copy(Blocks.COMMAND_BLOCK));
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(final @NotNull BlockPos pos, final @NotNull BlockState blockState)
    {
        return new TeleporterBlockEntity(pos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(final Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
}
