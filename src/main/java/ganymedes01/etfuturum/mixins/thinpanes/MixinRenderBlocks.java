package ganymedes01.etfuturum.mixins.thinpanes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(RenderBlocks.class)
public abstract class MixinRenderBlocks {

    @Inject(method = "renderBlockPane", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPane;shouldSideBeRendered(Lnet/minecraft/world/IBlockAccess;IIII)Z", ordinal = 1, shift = At.Shift.AFTER), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void remapCollisionsBoxes(BlockPane p_147767_1_, int posX, int posY, int posZ, CallbackInfoReturnable<Boolean> cir,
                                      int l, Tessellator tessellator, int i1, float f, float f1, float f2,
                                      IIcon iicon, IIcon iicon1,
                                      double d21, double d0, double d1, double d2, double d3, double d4, double d5,
                                      double d6, double d7, double d8, double d9, double d10, double d11, double d12,
                                      double d13, double d14, double d15, double d16, double d17, double d18,
                                      boolean flag, boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
        // Not Connected to anything
        if(!flag && !flag1 && !flag2 && !flag3){
            // Cut out Icon Textures for Tops / Bottoms
            double d00 = (double)iicon.getInterpolatedU(7.0D);
            double d01 = (double)iicon.getInterpolatedU(9.0D);
            double d000 = (double)iicon1.getInterpolatedV(7.0D);
            double d001 = (double)iicon1.getInterpolatedV(9.0D);

            tessellator.addVertexWithUV(d16, (double)(posY + 1), d13, d00, d2);
            tessellator.addVertexWithUV(d16, (double)(posY + 0), d13, d00, d3);
            tessellator.addVertexWithUV(d15, (double)(posY + 0), d13, d01, d3);
            tessellator.addVertexWithUV(d15, (double)(posY + 1), d13, d01, d2);

            tessellator.addVertexWithUV(d15, (double)(posY + 1), d13, d00, d2);
            tessellator.addVertexWithUV(d15, (double)(posY + 0), d13, d00, d3);
            tessellator.addVertexWithUV(d16, (double)(posY + 0), d13, d01, d3);
            tessellator.addVertexWithUV(d16, (double)(posY + 1), d13, d01, d2);

            tessellator.addVertexWithUV(d10, (double)(posY + 1), d17, d00, d2);
            tessellator.addVertexWithUV(d10, (double)(posY + 0), d17, d00, d3);
            tessellator.addVertexWithUV(d10, (double)(posY + 0), d18, d01, d3);
            tessellator.addVertexWithUV(d10, (double)(posY + 1), d18, d01, d2);

            tessellator.addVertexWithUV(d10, (double)(posY + 1), d18, d00, d2);
            tessellator.addVertexWithUV(d10, (double)(posY + 0), d18, d00, d3);
            tessellator.addVertexWithUV(d10, (double)(posY + 0), d17, d01, d3);
            tessellator.addVertexWithUV(d10, (double)(posY + 1), d17, d01, d2);

            // Top-Over
            tessellator.addVertexWithUV(d15, (double)(posY + 1 - 0.001D), d18, d5, d000);
            tessellator.addVertexWithUV(d16, (double)(posY + 1 - 0.001D), d18, d5, d001);
            tessellator.addVertexWithUV(d16, (double)(posY + 1 - 0.001D), d17, d4, d001);
            tessellator.addVertexWithUV(d15, (double)(posY + 1 - 0.001D), d17, d4, d000);

            // Top-Under
            tessellator.addVertexWithUV(d16, (double)(posY + 1 - 0.001D), d18, d5, d000);
            tessellator.addVertexWithUV(d15, (double)(posY + 1 - 0.001D), d18, d5, d001);
            tessellator.addVertexWithUV(d15, (double)(posY + 1 - 0.001D), d17, d4, d001);
            tessellator.addVertexWithUV(d16, (double)(posY + 1 - 0.001D), d17, d4, d000);

            // Bottom-Over
            tessellator.addVertexWithUV(d15, (double)(posY + 0.001D), d18, d5, d000);
            tessellator.addVertexWithUV(d16, (double)(posY + 0.001D), d18, d5, d001);
            tessellator.addVertexWithUV(d16, (double)(posY + 0.001D), d17, d4, d001);
            tessellator.addVertexWithUV(d15, (double)(posY + 0.001D), d17, d4, d000);

            // Bottom-Under
            tessellator.addVertexWithUV(d16, (double)(posY + 0.001D), d18, d5, d000);
            tessellator.addVertexWithUV(d15, (double)(posY + 0.001D), d18, d5, d001);
            tessellator.addVertexWithUV(d15, (double)(posY + 0.001D), d17, d4, d001);
            tessellator.addVertexWithUV(d16, (double)(posY + 0.001D), d17, d4, d000);

            cir.setReturnValue(true);
        }
    }

    @Inject(method = "renderBlockStainedGlassPane", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/block/BlockPane;canPaneConnectTo(Lnet/minecraft/world/IBlockAccess;IIILnet/minecraftforge/common/util/ForgeDirection;)Z", ordinal = 3, shift = At.Shift.AFTER), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void remapCollisionsBoxes(Block p_147733_1_, int posX, int posY, int posZ, CallbackInfoReturnable<Boolean> cir,
                                      int l, Tessellator tessellator, int i1, float f, float f1, float f2, boolean flag5,
                                      IIcon iicon, IIcon iicon1, double d22,
                                      double d0, double d1, double d2, double d3, double d4, double d5, double d6,
                                      double d7, double d8, double d9, double d10, double d11, double d12, double d13,
                                      double d14, double d15, double d16, double d17, double d18,
                                      boolean flag, boolean flag1, boolean flag2, boolean flag3) {
        // Not Connected to Anything
        if(!flag && !flag1 && !flag2 && !flag3){

            double d00 = (double)posX + 0.4375F;
            double d01 = (double)(posX + (1 - 0.4375F));
            double d000 = (double)posZ + 0.4375F;
            double d001 = (double)(posZ + (1 - 0.4375F));

            tessellator.addVertexWithUV(d00, (double)posY + 0.999D, d17, d0, d3);
            tessellator.addVertexWithUV(d00, (double)posY + 0.001D, d17, d0, d4);
            tessellator.addVertexWithUV(d00, (double)posY + 0.001D, d18, d1, d4);
            tessellator.addVertexWithUV(d00, (double)posY + 0.999D, d18, d1, d3);

            tessellator.addVertexWithUV(d01, (double)posY + 0.999D, d18, d0, d3);
            tessellator.addVertexWithUV(d01, (double)posY + 0.001D, d18, d0, d4);
            tessellator.addVertexWithUV(d01, (double)posY + 0.001D, d17, d1, d4);
            tessellator.addVertexWithUV(d01, (double)posY + 0.999D, d17, d1, d3);

            tessellator.addVertexWithUV(d16, (double)posY + 0.999D, d000, d1, d3);
            tessellator.addVertexWithUV(d16, (double)posY + 0.001D, d000, d1, d4);
            tessellator.addVertexWithUV(d15, (double)posY + 0.001D, d000, d0, d4);
            tessellator.addVertexWithUV(d15, (double)posY + 0.999D, d000, d0, d3);

            tessellator.addVertexWithUV(d15, (double)posY + 0.999D, d001, d0, d3);
            tessellator.addVertexWithUV(d15, (double)posY + 0.001D, d001, d0, d4);
            tessellator.addVertexWithUV(d16, (double)posY + 0.001D, d001, d1, d4);
            tessellator.addVertexWithUV(d16, (double)posY + 0.999D, d001, d1, d3);

            // Top and Bottom
            tessellator.addVertexWithUV(d16, (double)posY + 0.999D, d17, d6, d9);
            tessellator.addVertexWithUV(d15, (double)posY + 0.999D, d17, d5, d9);
            tessellator.addVertexWithUV(d15, (double)posY + 0.999D, d18, d5, d10);
            tessellator.addVertexWithUV(d16, (double)posY + 0.999D, d18, d6, d10);
            tessellator.addVertexWithUV(d15, (double)posY + 0.001D, d17, d5, d9);
            tessellator.addVertexWithUV(d16, (double)posY + 0.001D, d17, d6, d9);
            tessellator.addVertexWithUV(d16, (double)posY + 0.001D, d18, d6, d10);
            tessellator.addVertexWithUV(d15, (double)posY + 0.001D, d18, d5, d10);

            cir.setReturnValue(true);
        }
    }
}
