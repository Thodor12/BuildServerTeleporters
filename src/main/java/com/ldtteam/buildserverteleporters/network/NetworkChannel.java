package com.ldtteam.buildserverteleporters.network;

import com.ldtteam.buildserverteleporters.constants.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Wrapper for Forge network layer
 */
public class NetworkChannel
{
    /**
     * Forge network channel
     */
    private final SimpleChannel rawChannel;

    /**
     * Creates a new instance of network channel.
     *
     * @param channelName unique channel name
     * @throws IllegalArgumentException if channelName already exists
     */
    public NetworkChannel(final String channelName)
    {
        final String modVersion = ModList.get().getModContainerById(Constants.MOD_ID).map(m -> m.getModInfo().getVersion().toString()).orElse("0.0.1");
        rawChannel =
          NetworkRegistry.newSimpleChannel(new ResourceLocation(Constants.MOD_ID, channelName), () -> modVersion, str -> str.equals(modVersion), str -> str.equals(modVersion));
    }

    private void registerMessages()
    {
        rawChannel.registerMessage(0, SplitPacketMessage.class, IMessage::toBytes, (buf) -> {
            final SplitPacketMessage msg = new SplitPacketMessage();
            msg.fromBytes(buf);
            return msg;
        }, (msg, ctxIn) -> {
            final net.minecraftforge.network.NetworkEvent.Context ctx = ctxIn.get();
            final LogicalSide packetOrigin = ctx.getDirection().getOriginationSide();
            ctx.setPacketHandled(true);
            msg.onExecute(ctx, packetOrigin.equals(LogicalSide.CLIENT));
        });
    }
}
