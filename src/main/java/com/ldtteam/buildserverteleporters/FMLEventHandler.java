package com.ldtteam.buildserverteleporters;

import com.ldtteam.buildserverteleporters.capability.TeleporterStorageCapabilityProvider;
import com.ldtteam.buildserverteleporters.constants.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

/**
 * Event handler for forge events.
 */
@Mod.EventBusSubscriber(bus = Bus.FORGE)
public class FMLEventHandler
{
    /**
     * Event called to attach capabilities on the world.
     *
     * @param event the event.
     */
    @SubscribeEvent
    public static void onAttachingCapabilitiesWorld(@NotNull final AttachCapabilitiesEvent<Level> event)
    {
        event.addCapability(new ResourceLocation(Constants.MOD_ID, "teleporterstorage"), new TeleporterStorageCapabilityProvider());
    }
}
