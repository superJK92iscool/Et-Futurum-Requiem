package ganymedes01.etfuturum.mixins.thinpanes;

import ganymedes01.etfuturum.compat.ModsList;
import ganymedes01.etfuturum.core.utils.Logger;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static net.minecraftforge.common.util.ForgeDirection.*;

@Mixin(RenderBlocks.class)
public abstract class MixinRenderBlocks {

    @Shadow
    public IBlockAccess blockAccess;
    @Shadow
    public IIcon overrideBlockTexture;

    private boolean errorCaught;
    private boolean populatedFields;
    private Field skipTopEdgeRenderingField;
    private Field skipBottomEdgeRenderingField;
    private Method setupPaneEdgesFunc;
    private Method setupIconsFunc;
//    private Method renderThickFunc;
//    private Method renderThinFunc;

    @SuppressWarnings("UnreachableCode")
    @Inject(method = "renderBlockPane", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPane;shouldSideBeRendered(Lnet/minecraft/world/IBlockAccess;IIII)Z", ordinal = 1, shift = At.Shift.AFTER), cancellable = true)
    private void tweakPaneRenderer(BlockPane block, int posX, int posY, int posZ, CallbackInfoReturnable<Boolean> cir) {
        boolean flag = block.canPaneConnectTo(this.blockAccess, posX, posY, posZ - 1, NORTH);
        boolean flag1 = block.canPaneConnectTo(this.blockAccess, posX, posY, posZ + 1, SOUTH);
        boolean flag2 = block.canPaneConnectTo(this.blockAccess, posX - 1, posY, posZ, WEST);
        boolean flag3 = block.canPaneConnectTo(this.blockAccess, posX + 1, posY, posZ, EAST);

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
                iicon = getBlockIcon(block, this.blockAccess, posX, posY, posZ, 0);
                iicon1 = block.func_150097_e();
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

            boolean skipBottom = false;
            boolean skipTop = false;
            loadReflectionData();
            if (populatedFields && !errorCaught) {
                try {
//                    renderThinField.invoke(null, (RenderBlocks) (Object)this, block, iicon, posX, posY, posZ, flag, flag1, flag2, flag3);
                    if ((boolean) setupIconsFunc.invoke(null, (RenderBlocks) (Object) this, block, iicon, posX, posY, posZ)) {
                        skipBottom = skipBottomEdgeRenderingField.getBoolean(null);
                        skipTop = skipTopEdgeRenderingField.getBoolean(null);
                    }
                } catch (Exception e) {
                    skipBottom = skipTop = populatedFields = false;
                    errorCaught = true;
                    Logger.error("MCPF compat for glass panes threw an error. Pretending it's off for now (rendering may look wonky!)");
                    e.printStackTrace();
                }
            }

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

            if (!skipTop) {
                // Top-Over
                tessellator.addVertexWithUV(d15, (double) (posY + 1 - 0.001D), d18, d5, d21);
                tessellator.addVertexWithUV(d16, (double) (posY + 1 - 0.001D), d18, d5, d22);
                tessellator.addVertexWithUV(d16, (double) (posY + 1 - 0.001D), d17, d4, d22);
                tessellator.addVertexWithUV(d15, (double) (posY + 1 - 0.001D), d17, d4, d21);

                // Top-Under
                tessellator.addVertexWithUV(d16, (double) (posY + 1 - 0.001D), d18, d5, d21);
                tessellator.addVertexWithUV(d15, (double) (posY + 1 - 0.001D), d18, d5, d22);
                tessellator.addVertexWithUV(d15, (double) (posY + 1 - 0.001D), d17, d4, d22);
                tessellator.addVertexWithUV(d16, (double) (posY + 1 - 0.001D), d17, d4, d21);
            }

            if (!skipBottom) {
                // Bottom-Over
                tessellator.addVertexWithUV(d15, (double) (posY + 0.001D), d18, d5, d21);
                tessellator.addVertexWithUV(d16, (double) (posY + 0.001D), d18, d5, d22);
                tessellator.addVertexWithUV(d16, (double) (posY + 0.001D), d17, d4, d22);
                tessellator.addVertexWithUV(d15, (double) (posY + 0.001D), d17, d4, d21);

                // Bottom-Under
                tessellator.addVertexWithUV(d16, (double) (posY + 0.001D), d18, d5, d21);
                tessellator.addVertexWithUV(d15, (double) (posY + 0.001D), d18, d5, d22);
                tessellator.addVertexWithUV(d15, (double) (posY + 0.001D), d17, d4, d22);
                tessellator.addVertexWithUV(d16, (double) (posY + 0.001D), d17, d4, d21);
            }

            cir.setReturnValue(true);
        }
    }

    @SuppressWarnings("UnreachableCode")
    @Inject(method = "renderBlockStainedGlassPane", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPane;canPaneConnectTo(Lnet/minecraft/world/IBlockAccess;IIILnet/minecraftforge/common/util/ForgeDirection;)Z", ordinal = 3, shift = At.Shift.AFTER), cancellable = true)
    private void tweakStainedPaneRenderer(Block block, int posX, int posY, int posZ, CallbackInfoReturnable<Boolean> cir) {

        boolean flag = ((BlockPane) block).canPaneConnectTo(this.blockAccess, posX, posY, posZ - 1, NORTH);
        boolean flag1 = ((BlockPane) block).canPaneConnectTo(this.blockAccess, posX, posY, posZ + 1, SOUTH);
        boolean flag2 = ((BlockPane) block).canPaneConnectTo(this.blockAccess, posX - 1, posY, posZ, WEST);
        boolean flag3 = ((BlockPane) block).canPaneConnectTo(this.blockAccess, posX + 1, posY, posZ, EAST);

        // Not Connected to Anything
        if(!flag && !flag1 && !flag2 && !flag3){
            IIcon iicon;
            IIcon iicon1;
            boolean flag5 = block instanceof BlockStainedGlassPane;

            if (this.hasOverrideBlockTexture())
            {
                iicon = this.overrideBlockTexture;
                iicon1 = this.overrideBlockTexture;
            }
            else
            {
                int j1 = this.blockAccess.getBlockMetadata(posX, posY, posZ);
                iicon = getBlockIcon(block, this.blockAccess, posX, posY, posZ, 0);
                iicon1 = flag5 ? ((BlockStainedGlassPane) block).func_150104_b(j1) : ((BlockPane) block).func_150097_e();
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

            boolean skipBottom = false;
            boolean skipTop = false;
            loadReflectionData();
            if (populatedFields && !errorCaught) {
                try {
//                    renderThickField.invoke(null, (RenderBlocks) (Object)this, block, iicon, posX, posY, posZ, flag, flag1, flag2, flag3);
                    if ((boolean) setupIconsFunc.invoke(null, (RenderBlocks) (Object) this, block, iicon, posX, posY, posZ)) {
                        setupPaneEdgesFunc.invoke(null, (RenderBlocks) (Object) this, block, posX, posY, posZ);
                        skipBottom = skipBottomEdgeRenderingField.getBoolean(null);
                        skipTop = skipTopEdgeRenderingField.getBoolean(null);
                    }
                } catch (Exception e) {
                    skipBottom = skipTop = populatedFields = false;
                    errorCaught = true;
                    Logger.error("MCPF compat for glass panes threw an error. Pretending it's off for now (rendering may look wonky!)");
                    e.printStackTrace();
                }
            }

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

            if (!skipTop) {
                // Top and Bottom
                tessellator.addVertexWithUV(d16, (double) posY + 0.999D, d17, d6, d9);
                tessellator.addVertexWithUV(d15, (double) posY + 0.999D, d17, d5, d9);
                tessellator.addVertexWithUV(d15, (double) posY + 0.999D, d18, d5, d10);
                tessellator.addVertexWithUV(d16, (double) posY + 0.999D, d18, d6, d10);
            }

            if (!skipBottom) {
                tessellator.addVertexWithUV(d15, (double) posY + 0.001D, d17, d5, d9);
                tessellator.addVertexWithUV(d16, (double) posY + 0.001D, d17, d6, d9);
                tessellator.addVertexWithUV(d16, (double) posY + 0.001D, d18, d6, d10);
                tessellator.addVertexWithUV(d15, (double) posY + 0.001D, d18, d5, d10);
            }

            cir.setReturnValue(true);
        }
    }

    private void loadReflectionData() {
        if (ModsList.MC_PATCHER_FORGE.isLoaded() && !populatedFields && !errorCaught) {
            try {
                Class clss = Class.forName("com.prupe.mcpatcher.ctm.GlassPaneRenderer");
                skipTopEdgeRenderingField = clss.getDeclaredField("skipTopEdgeRendering");
                skipBottomEdgeRenderingField = clss.getDeclaredField("skipBottomEdgeRendering");
                setupPaneEdgesFunc = clss.getDeclaredMethod("setupPaneEdges", RenderBlocks.class, Block.class, int.class, int.class, int.class);
                setupIconsFunc = clss.getDeclaredMethod("setupIcons", RenderBlocks.class, Block.class, IIcon.class, int.class, int.class, int.class);
                setupPaneEdgesFunc.setAccessible(true);
                setupIconsFunc.setAccessible(true);
//                renderThickField = clss.getDeclaredMethod("renderThick", RenderBlocks.class, Block.class, IIcon.class, int.class, int.class, int.class, boolean.class, boolean.class, boolean.class, boolean.class);
//                renderThinField = clss.getDeclaredMethod("renderThin", RenderBlocks.class, Block.class, IIcon.class, int.class, int.class, int.class, boolean.class, boolean.class, boolean.class, boolean.class);
                populatedFields = true;
            } catch (Exception e) {
                populatedFields = false;
                errorCaught = true;
                Logger.error("MCPF compat failed to set up for stained glass panes. Pretending it's off for now (rendering may look wonky!)");
                e.printStackTrace();
            }
        }
    }

    @Shadow
    public boolean hasOverrideBlockTexture(){ return true; }

    @Shadow
    public abstract IIcon getBlockIcon(Block block, IBlockAccess access, int x, int y, int z, int side);
}
