package ganymedes01.etfuturum.mixins.thinpanes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraftforge.common.util.ForgeDirection.*;
import static net.minecraftforge.common.util.ForgeDirection.EAST;

@Mixin(RenderBlocks.class)
public abstract class MixinRenderBlocks {

    @Shadow
    public IBlockAccess blockAccess;

    @Shadow
    public IIcon overrideBlockTexture;

    @Inject(method = "renderBlockPane", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPane;shouldSideBeRendered(Lnet/minecraft/world/IBlockAccess;IIII)Z", ordinal = 1, shift = At.Shift.AFTER), cancellable = true)
    private void remapCollisionsBoxes(BlockPane pane, int posX, int posY, int posZ, CallbackInfoReturnable<Boolean> cir) {
        boolean flag  = pane.canPaneConnectTo(this.blockAccess, posX, posY, posZ - 1, NORTH);
        boolean flag1 = pane.canPaneConnectTo(this.blockAccess, posX, posY, posZ + 1, SOUTH);
        boolean flag2 = pane.canPaneConnectTo(this.blockAccess, posX - 1, posY, posZ, WEST );
        boolean flag3 = pane.canPaneConnectTo(this.blockAccess, posX + 1, posY, posZ, EAST );

        // Not Connected to anything
        if(!flag && !flag1 && !flag2 && !flag3){

            IIcon iicon;
            IIcon iicon1;
            if (this.hasOverrideBlockTexture())
            {
                iicon = this.overrideBlockTexture;
                iicon1 = this.overrideBlockTexture;
            }
            else
            {
                int j1 = this.blockAccess.getBlockMetadata(posX, posY, posZ);
                iicon = this.getBlockIconFromSideAndMetadata(pane, 0, j1);
                iicon1 = pane.func_150097_e();
            }

            double d0 = (double)iicon.getInterpolatedU(7.0D);
            double d00 = (double)iicon.getInterpolatedU(9.0D);
            double d2 = (double)iicon.getMinV();
            double d3 = (double)iicon.getMaxV();
            double d4 = (double)iicon1.getInterpolatedU(7.0D);
            double d5 = (double)iicon1.getInterpolatedU(9.0D);
            double d21 = (double)iicon1.getInterpolatedV(7.0D);
            double d22 = (double)iicon1.getInterpolatedV(9.0D);
            double d10 = (double) posX + 0.5D;
            double d13 = (double)posZ + 0.5D;
            double d15 = (double) posX + 0.5D - 0.0625D;
            double d16 = (double) posX + 0.5D + 0.0625D;
            double d17 = (double)posZ + 0.5D - 0.0625D;
            double d18 = (double)posZ + 0.5D + 0.0625D;

            Tessellator tessellator = Tessellator.instance;

            tessellator.addVertexWithUV(d16, (double)(posY + 1), d13, d0, d2);
            tessellator.addVertexWithUV(d16, (double)(posY + 0), d13, d0, d3);
            tessellator.addVertexWithUV(d15, (double)(posY + 0), d13, d00, d3);
            tessellator.addVertexWithUV(d15, (double)(posY + 1), d13, d00, d2);

            tessellator.addVertexWithUV(d15, (double)(posY + 1), d13, d0, d2);
            tessellator.addVertexWithUV(d15, (double)(posY + 0), d13, d0, d3);
            tessellator.addVertexWithUV(d16, (double)(posY + 0), d13, d00, d3);
            tessellator.addVertexWithUV(d16, (double)(posY + 1), d13, d00, d2);

            tessellator.addVertexWithUV(d10, (double)(posY + 1), d17, d0, d2);
            tessellator.addVertexWithUV(d10, (double)(posY + 0), d17, d0, d3);
            tessellator.addVertexWithUV(d10, (double)(posY + 0), d18, d00, d3);
            tessellator.addVertexWithUV(d10, (double)(posY + 1), d18, d00, d2);

            tessellator.addVertexWithUV(d10, (double)(posY + 1), d18, d0, d2);
            tessellator.addVertexWithUV(d10, (double)(posY + 0), d18, d0, d3);
            tessellator.addVertexWithUV(d10, (double)(posY + 0), d17, d00, d3);
            tessellator.addVertexWithUV(d10, (double)(posY + 1), d17, d00, d2);

            // Top-Over
            tessellator.addVertexWithUV(d15, (double)(posY + 1 - 0.001D), d18, d5, d21);
            tessellator.addVertexWithUV(d16, (double)(posY + 1 - 0.001D), d18, d5, d22);
            tessellator.addVertexWithUV(d16, (double)(posY + 1 - 0.001D), d17, d4, d22);
            tessellator.addVertexWithUV(d15, (double)(posY + 1 - 0.001D), d17, d4, d21);

            // Top-Under
            tessellator.addVertexWithUV(d16, (double)(posY + 1 - 0.001D), d18, d5, d21);
            tessellator.addVertexWithUV(d15, (double)(posY + 1 - 0.001D), d18, d5, d22);
            tessellator.addVertexWithUV(d15, (double)(posY + 1 - 0.001D), d17, d4, d22);
            tessellator.addVertexWithUV(d16, (double)(posY + 1 - 0.001D), d17, d4, d21);

            // Bottom-Over
            tessellator.addVertexWithUV(d15, (double)(posY + 0.001D), d18, d5, d21);
            tessellator.addVertexWithUV(d16, (double)(posY + 0.001D), d18, d5, d22);
            tessellator.addVertexWithUV(d16, (double)(posY + 0.001D), d17, d4, d22);
            tessellator.addVertexWithUV(d15, (double)(posY + 0.001D), d17, d4, d21);

            // Bottom-Under
            tessellator.addVertexWithUV(d16, (double)(posY + 0.001D), d18, d5, d21);
            tessellator.addVertexWithUV(d15, (double)(posY + 0.001D), d18, d5, d22);
            tessellator.addVertexWithUV(d15, (double)(posY + 0.001D), d17, d4, d22);
            tessellator.addVertexWithUV(d16, (double)(posY + 0.001D), d17, d4, d21);

            cir.setReturnValue(true);
            cir.cancel();
            return;
        }
    }

    @Inject(method = "renderBlockStainedGlassPane", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPane;canPaneConnectTo(Lnet/minecraft/world/IBlockAccess;IIILnet/minecraftforge/common/util/ForgeDirection;)Z", ordinal = 3, shift = At.Shift.AFTER), cancellable = true)
    private void remapCollisionsBoxes(Block p_147767_1_, int posX, int posY, int posZ, CallbackInfoReturnable<Boolean> cir) {

        boolean flag  = ((BlockPane) p_147767_1_).canPaneConnectTo(this.blockAccess, posX, posY, posZ - 1, NORTH);
        boolean flag1 = ((BlockPane) p_147767_1_).canPaneConnectTo(this.blockAccess, posX, posY, posZ + 1, SOUTH);
        boolean flag2 = ((BlockPane) p_147767_1_).canPaneConnectTo(this.blockAccess, posX - 1, posY, posZ, WEST );
        boolean flag3 = ((BlockPane) p_147767_1_).canPaneConnectTo(this.blockAccess, posX + 1, posY, posZ, EAST );

        // Not Connected to Anything
        if(!flag && !flag1 && !flag2 && !flag3){

            IIcon iicon;
            IIcon iicon1;
            boolean flag5 = p_147767_1_ instanceof BlockStainedGlassPane;

            if (this.hasOverrideBlockTexture())
            {
                iicon = this.overrideBlockTexture;
                iicon1 = this.overrideBlockTexture;
            }
            else
            {
                int j1 = this.blockAccess.getBlockMetadata(posX, posY, posZ);
                iicon = this.getBlockIconFromSideAndMetadata(p_147767_1_, 0, j1);
                iicon1 = flag5 ? ((BlockStainedGlassPane)p_147767_1_).func_150104_b(j1) : ((BlockPane)p_147767_1_).func_150097_e();
            }

            double d0 = (double)iicon.getInterpolatedU(7.0D);
            double d1 = (double)iicon.getInterpolatedU(9.0D);
            double d3 = (double)iicon.getMinV();
            double d4 = (double)iicon.getMaxV();
            double d5 = (double)iicon1.getInterpolatedU(7.0D);
            double d6 = (double)iicon1.getInterpolatedU(9.0D);
            double d9 = (double)iicon1.getInterpolatedV(7.0D);
            double d10 = (double)iicon1.getInterpolatedV(9.0D);
            double d11 = (double)posX + 0.4375F;
            double d12 = (double)(posX + (1 - 0.4375F));
            double d13 = (double)posZ + 0.4375F;
            double d14 = (double)(posZ + (1 - 0.4375F));
            double d15 = (double)posX + 0.5D - 0.0625D;
            double d16 = (double)posX + 0.5D + 0.0625D;
            double d17 = (double)posZ + 0.5D - 0.0625D;
            double d18 = (double)posZ + 0.5D + 0.0625D;

            Tessellator tessellator = Tessellator.instance;

            tessellator.addVertexWithUV(d11, (double)posY + 0.999D, d17, d0, d3);
            tessellator.addVertexWithUV(d11, (double)posY + 0.001D, d17, d0, d4);
            tessellator.addVertexWithUV(d11, (double)posY + 0.001D, d18, d1, d4);
            tessellator.addVertexWithUV(d11, (double)posY + 0.999D, d18, d1, d3);

            tessellator.addVertexWithUV(d12, (double)posY + 0.999D, d18, d0, d3);
            tessellator.addVertexWithUV(d12, (double)posY + 0.001D, d18, d0, d4);
            tessellator.addVertexWithUV(d12, (double)posY + 0.001D, d17, d1, d4);
            tessellator.addVertexWithUV(d12, (double)posY + 0.999D, d17, d1, d3);

            tessellator.addVertexWithUV(d16, (double)posY + 0.999D, d13, d1, d3);
            tessellator.addVertexWithUV(d16, (double)posY + 0.001D, d13, d1, d4);
            tessellator.addVertexWithUV(d15, (double)posY + 0.001D, d13, d0, d4);
            tessellator.addVertexWithUV(d15, (double)posY + 0.999D, d13, d0, d3);

            tessellator.addVertexWithUV(d15, (double)posY + 0.999D, d14, d0, d3);
            tessellator.addVertexWithUV(d15, (double)posY + 0.001D, d14, d0, d4);
            tessellator.addVertexWithUV(d16, (double)posY + 0.001D, d14, d1, d4);
            tessellator.addVertexWithUV(d16, (double)posY + 0.999D, d14, d1, d3);

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
            cir.cancel();
            return;
        }
    }

    @Shadow
    public boolean hasOverrideBlockTexture(){ return true; }

    @Shadow
    public IIcon getBlockIconFromSideAndMetadata(Block p_147787_1_, int p_147787_2_, int p_147787_3_){ return null; }
}
