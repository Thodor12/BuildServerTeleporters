package com.ldtteam.buildserverteleporters;

import com.ldtteam.buildserverteleporters.constants.Constants;
import com.ldtteam.buildserverteleporters.initializers.RegistryInitializer;
import com.ldtteam.buildserverteleporters.renderer.TeleporterBlockRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * Event handler for mod client only events.
 */
@Mod.EventBusSubscriber(bus = Bus.MOD, modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler
{
    /**
     * Event called for registering new block entity renderers.
     *
     * @param event the event.
     */
    @SubscribeEvent
    public static void registerBlockEntityRenderers(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerBlockEntityRenderer(RegistryInitializer.TELEPORTER_ENTITY_TYPE, TeleporterBlockRenderer::new);
    }
}
