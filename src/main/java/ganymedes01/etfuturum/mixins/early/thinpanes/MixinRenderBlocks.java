package ganymedes01.etfuturum.mixins.early.thinpanes;

import com.llamalad7.mixinextras.sugar.Local;
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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static net.minecraftforge.common.util.ForgeDirection.*;

@Mixin(value = RenderBlocks.class, priority = 5000)
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
    @Inject(method = "renderBlockPane", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPane;shouldSideBeRendered(Lnet/minecraft/world/IBlockAccess;IIII)Z", ordinal = 1, shift = At.Shift.BY, by = 2),
            cancellable = true)
    private void tweakPaneRenderer(BlockPane block, int posX, int posY, int posZ, CallbackInfoReturnable<Boolean> cir,
                                   @Local(name = "tessellator") Tessellator tessellator,
                                   @Local(name = "iicon") IIcon iicon, @Local(name = "iicon") IIcon iicon1,
                                   @Local(name = "d21") double d21, @Local(name = "d2") double d2,
                                   @Local(name = "d3")double d3, @Local(name = "d4") double d4, @Local(name = "d5") double d5,
                                   @Local(name = "d10") double d10, @Local(name = "d13") double d13, @Local(name = "d15") double d15,
                                   @Local(name = "d16") double d16, @Local(name = "d17") double d17, @Local(name = "d18") double d18,
                                   @Local(name = "flag") boolean flag, @Local(name = "flag1") boolean flag1,
                                   @Local(name = "flag2") boolean flag2, @Local(name = "flag3") boolean flag3,
                                   @Local(name = "flag4") boolean flag4, @Local(name = "flag5") boolean flag5) {

        // Not Connected to anything
        if(!flag && !flag1 && !flag2 && !flag3){
            double d00 = iicon.getInterpolatedU(7.0D);
            double d01 = iicon.getInterpolatedU(9.0D);
            double d000 = iicon1.getInterpolatedV(7.0D);
            double d001 = iicon1.getInterpolatedV(9.0D);

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

            tessellator.addVertexWithUV(d16, posY + 1, d13, d00, d2);
            tessellator.addVertexWithUV(d16, posY, d13, d00, d3);
            tessellator.addVertexWithUV(d15, posY, d13, d01, d3);
            tessellator.addVertexWithUV(d15, posY + 1, d13, d01, d2);

            tessellator.addVertexWithUV(d15, posY + 1, d13, d00, d2);
            tessellator.addVertexWithUV(d15, posY, d13, d00, d3);
            tessellator.addVertexWithUV(d16, posY, d13, d01, d3);
            tessellator.addVertexWithUV(d16, posY + 1, d13, d01, d2);

            tessellator.addVertexWithUV(d10, posY + 1, d17, d00, d2);
            tessellator.addVertexWithUV(d10, posY, d17, d00, d3);
            tessellator.addVertexWithUV(d10, posY, d18, d01, d3);
            tessellator.addVertexWithUV(d10, posY + 1, d18, d01, d2);

            tessellator.addVertexWithUV(d10, posY + 1, d18, d00, d2);
            tessellator.addVertexWithUV(d10, posY, d18, d00, d3);
            tessellator.addVertexWithUV(d10, posY, d17, d01, d3);
            tessellator.addVertexWithUV(d10, posY + 1, d17, d01, d2);

            if (!skipTop) {
                // Top-Over
                tessellator.addVertexWithUV(d15, posY + 1 - 0.001D, d18, d5, d000);
                tessellator.addVertexWithUV(d16, posY + 1 - 0.001D, d18, d5, d001);
                tessellator.addVertexWithUV(d16, posY + 1 - 0.001D, d17, d4, d001);
                tessellator.addVertexWithUV(d15, posY + 1 - 0.001D, d17, d4, d000);

                // Top-Under
                tessellator.addVertexWithUV(d16, posY + 1 - 0.001D, d18, d5, d000);
                tessellator.addVertexWithUV(d15, posY + 1 - 0.001D, d18, d5, d001);
                tessellator.addVertexWithUV(d15, posY + 1 - 0.001D, d17, d4, d001);
                tessellator.addVertexWithUV(d16, posY + 1 - 0.001D, d17, d4, d000);
            }

            if (!skipBottom) {
                // Bottom-Over
                tessellator.addVertexWithUV(d15, posY + 0.001D, d18, d5, d000);
                tessellator.addVertexWithUV(d16, posY + 0.001D, d18, d5, d001);
                tessellator.addVertexWithUV(d16, posY + 0.001D, d17, d4, d001);
                tessellator.addVertexWithUV(d15, posY + 0.001D, d17, d4, d000);

                // Bottom-Under
                tessellator.addVertexWithUV(d16, posY + 0.001D, d18, d5, d000);
                tessellator.addVertexWithUV(d15, posY + 0.001D, d18, d5, d001);
                tessellator.addVertexWithUV(d15, posY + 0.001D, d17, d4, d001);
                tessellator.addVertexWithUV(d16, posY + 0.001D, d17, d4, d000);
            }

            cir.setReturnValue(true);
        }
    }

    @SuppressWarnings("UnreachableCode")
    @Inject(method = "renderBlockStainedGlassPane", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockPane;canPaneConnectTo(Lnet/minecraft/world/IBlockAccess;IIILnet/minecraftforge/common/util/ForgeDirection;)Z", ordinal = 3, shift = At.Shift.BY, by = 2),
            cancellable = true)
    private void tweakStainedPaneRenderer(Block block, int posX, int posY, int posZ, CallbackInfoReturnable<Boolean> cir,
                                          @Local(name = "tessellator") Tessellator tessellator,
                                          @Local(name = "iicon") IIcon iicon, @Local(name = "iicon") IIcon iicon1,
                                          @Local(name = "d0") double d0, @Local(name = "d1") double d1, @Local(name = "d3")double d3,
                                          @Local(name = "d4") double d4, @Local(name = "d5") double d5, @Local(name = "d6") double d6,
                                          @Local(name = "d9") double d9, @Local(name = "d10") double d10, @Local(name = "d15") double d15,
                                          @Local(name = "d16") double d16, @Local(name = "d17") double d17, @Local(name = "d18") double d18,
                                          @Local(name = "flag") boolean flag, @Local(name = "flag1") boolean flag1,
                                          @Local(name = "flag2") boolean flag2, @Local(name = "flag3") boolean flag3) {

        // Not Connected to Anything
        if(!flag && !flag1 && !flag2 && !flag3){
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

            double d00 = (double)posX + 0.4375F;
            double d01 = posX + (1 - 0.4375F);
            double d000 = (double)posZ + 0.4375F;
            double d001 = posZ + (1 - 0.4375F);

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

            if (!skipTop) {
                // Top
                tessellator.addVertexWithUV(d16, (double)posY + 0.999D, d17, d6, d9);
                tessellator.addVertexWithUV(d15, (double)posY + 0.999D, d17, d5, d9);
                tessellator.addVertexWithUV(d15, (double)posY + 0.999D, d18, d5, d10);
                tessellator.addVertexWithUV(d16, (double)posY + 0.999D, d18, d6, d10);
            }

            if (!skipBottom) {
                // Bottom
                tessellator.addVertexWithUV(d15, (double)posY + 0.001D, d17, d5, d9);
                tessellator.addVertexWithUV(d16, (double)posY + 0.001D, d17, d6, d9);
                tessellator.addVertexWithUV(d16, (double)posY + 0.001D, d18, d6, d10);
                tessellator.addVertexWithUV(d15, (double)posY + 0.001D, d18, d5, d10);
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
}