package com.ldtteam.buildserverteleporters.initializers;

import com.ldtteam.buildserverteleporters.block.TeleporterBlock;
import com.ldtteam.buildserverteleporters.constants.Constants;
import com.ldtteam.buildserverteleporters.tileentity.TeleporterBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;

/**
 * Blocks initializer.
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Constants.MOD_ID)
public class RegistryInitializer
{
    /**
     * The teleporter block registry item.
     */
    public static Block TELEPORTER;

    /**
     * The teleporter block entity type registry item.
     */
    public static BlockEntityType<TeleporterBlockEntity> TELEPORTER_ENTITY_TYPE;

    /**
     * Event called for registering new blocks.
     *
     * @param event the event.
     */
    @SubscribeEvent
    public static void registerBlocks(final RegisterEvent event)
    {
        if (event.getForgeRegistry() == null)
        {
            return;
        }

        if (event.getRegistryKey().equals(Keys.BLOCKS))
        {
            RegistryInitializer.initBlocks(event.getForgeRegistry());
        }
        else if (event.getRegistryKey().equals(Keys.ITEMS))
        {
            RegistryInitializer.initItems(event.getForgeRegistry());
        }
        else if (event.getRegistryKey().equals(Keys.BLOCK_ENTITY_TYPES))
        {
            RegistryInitializer.initBlockEntityTypes(event.getForgeRegistry());
        }
    }

    /**
     * Initializer method for blocks.
     *
     * @param registry the mod block registry.
     */
    public static void initBlocks(final IForgeRegistry<Block> registry)
    {
        TELEPORTER = new TeleporterBlock();
        registry.register(new ResourceLocation(Constants.MOD_ID, TeleporterBlock.ID), TELEPORTER);
    }

    /**
     * Initializer method for blocks.
     *
     * @param registry the mod block registry.
     */
    public static void initItems(final IForgeRegistry<Item> registry)
    {
        registry.register(new ResourceLocation(Constants.MOD_ID, TeleporterBlock.ID), new BlockItem(TELEPORTER, new Item.Properties()));
    }

    /**
     * Initializer method for blocks.
     *
     * @param registry the mod block registry.
     */
    public static void initBlockEntityTypes(final IForgeRegistry<BlockEntityType<?>> registry)
    {
        TELEPORTER_ENTITY_TYPE = BlockEntityType.Builder.of(TeleporterBlockEntity::new, RegistryInitializer.TELEPORTER).build(null);
        registry.register(new ResourceLocation(Constants.MOD_ID, TeleporterBlock.ID), TELEPORTER_ENTITY_TYPE);
    }
}
