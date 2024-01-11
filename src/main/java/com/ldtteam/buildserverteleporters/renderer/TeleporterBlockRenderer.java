package com.ldtteam.buildserverteleporters.renderer;

import com.ldtteam.buildserverteleporters.tileentity.TeleporterBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Renderer for the text on teleporter blocks, this is mostly based on {@link SignRenderer}.
 */
@OnlyIn(Dist.CLIENT)
public class TeleporterBlockRenderer implements BlockEntityRenderer<TeleporterBlockEntity>
{
    private static final Vec3 TEXT_OFFSET             = new Vec3(0.0D, 0.33333334F, 0.046666667F);
    private static final int  OUTLINE_RENDER_DISTANCE = Mth.square(16);

    private static final float MODEL_RENDER_SCALE = 0.6666667F;
    private static final float TEXT_RENDER_SCALE  = 0.6666667F;

    private final Font font;

    public TeleporterBlockRenderer(final BlockEntityRendererProvider.Context context)
    {
        font = context.getFont();
    }

    private int getDarkColor(final SignText signText)
    {
        int i = signText.getColor().getTextColor();
        if (i == DyeColor.BLACK.getTextColor() && signText.hasGlowingText())
        {
            return -988212;
        }
        else
        {
            int j = (int) (FastColor.ARGB32.red(i) * 0.4D);
            int k = (int) (FastColor.ARGB32.green(i) * 0.4D);
            int l = (int) (FastColor.ARGB32.blue(i) * 0.4D);
            return FastColor.ARGB32.color(0, j, k, l);
        }
    }

    private boolean isOutlineVisible(final BlockPos pPos, final int pTextColor)
    {
        if (pTextColor == DyeColor.BLACK.getTextColor())
        {
            return true;
        }
        else
        {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer localplayer = minecraft.player;
            if (localplayer != null && minecraft.options.getCameraType().isFirstPerson() && localplayer.isScoping())
            {
                return true;
            }
            else
            {
                Entity entity = minecraft.getCameraEntity();
                return entity != null && entity.distanceToSqr(Vec3.atCenterOf(pPos)) < OUTLINE_RENDER_DISTANCE;
            }
        }
    }

    @Override
    public void render(
      final TeleporterBlockEntity blockEntity,
      final float partialTick,
      final PoseStack poseStack,
      final @NotNull MultiBufferSource buffer,
      final int packedLight,
      final int packedOverlay)
    {
        BlockState blockstate = blockEntity.getBlockState();
        final Direction direction = blockstate.getValue(HorizontalDirectionalBlock.FACING);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));

        translateText(poseStack, blockstate);
        renderBlockText(blockEntity.getBlockPos(),
          blockEntity.getFrontText(),
          poseStack,
          buffer,
          packedLight,
          blockEntity.getTextLineHeight(),
          blockEntity.getMaxTextLineWidth());
        poseStack.popPose();
    }

    private void translateText(final PoseStack poseStack, final BlockState state)
    {
        poseStack.translate(0.5F, 0.75F * MODEL_RENDER_SCALE, 0.5F);
        if (!(state.getBlock() instanceof StandingSignBlock))
        {
            poseStack.translate(0.0F, -0.3125F, -0.4375F);
        }
    }

    private void renderBlockText(
      final BlockPos pos,
      final SignText text,
      final PoseStack poseStack,
      final MultiBufferSource buffer,
      final int packedLight,
      final int lineHeight,
      final int maxWidth)
    {
        poseStack.pushPose();
        float renderScale = 0.015625F * TEXT_RENDER_SCALE;
        poseStack.translate(TEXT_OFFSET.x, TEXT_OFFSET.y, TEXT_OFFSET.z);
        poseStack.scale(renderScale, -renderScale, renderScale);

        int i = getDarkColor(text);
        int j = 4 * lineHeight / 2;
        FormattedCharSequence[] aformattedcharsequence = text.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), formatter -> {
            List<FormattedCharSequence> list = font.split(formatter, maxWidth);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        int k;
        boolean flag;
        int l;
        if (text.hasGlowingText())
        {
            k = text.getColor().getTextColor();
            flag = isOutlineVisible(pos, k);
            l = 15728880;
        }
        else
        {
            k = i;
            flag = false;
            l = packedLight;
        }

        for (int i1 = 0; i1 < 4; ++i1)
        {
            FormattedCharSequence formattedcharsequence = aformattedcharsequence[i1];
            float f = -font.width(formattedcharsequence) / 2F;
            if (flag)
            {
                font.drawInBatch8xOutline(formattedcharsequence, f, (i1 * lineHeight - j), k, i, poseStack.last().pose(), buffer, l);
            }
            else
            {
                font.drawInBatch(formattedcharsequence, f, (i1 * lineHeight - j), k, false, poseStack.last().pose(), buffer, Font.DisplayMode.POLYGON_OFFSET, 0, l);
            }
        }

        poseStack.popPose();
    }
}
