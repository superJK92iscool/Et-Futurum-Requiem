package ganymedes01.etfuturum.mixins.glasspane;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
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
    private void remapCollisionsBoxes(BlockPane p_147767_1_, int p_147767_2_, int p_147767_3_, int p_147767_4_, CallbackInfoReturnable<Boolean> cir) {

        IIcon iicon;
        if (this.hasOverrideBlockTexture())
        {
            iicon = this.overrideBlockTexture;
        }
        else
        {
            int j1 = this.blockAccess.getBlockMetadata(p_147767_2_, p_147767_3_, p_147767_4_);
            iicon = this.getBlockIconFromSideAndMetadata(p_147767_1_, 0, j1);
        }

        double d21 = (double)iicon.getMinU();
        double d0 = (double)iicon.getInterpolatedU(8.0D);
        double d2 = (double)iicon.getMinV();
        double d3 = (double)iicon.getMaxV();
        double d9 = (double)p_147767_2_;
        double d10 = (double)p_147767_2_ + 0.5D;
        double d13 = (double)p_147767_4_ + 0.5D;

        Tessellator tessellator = Tessellator.instance;
        boolean flag  = p_147767_1_.canPaneConnectTo(this.blockAccess, p_147767_2_, p_147767_3_, p_147767_4_ - 1, NORTH);
        boolean flag1 = p_147767_1_.canPaneConnectTo(this.blockAccess, p_147767_2_, p_147767_3_, p_147767_4_ + 1, SOUTH);
        boolean flag2 = p_147767_1_.canPaneConnectTo(this.blockAccess, p_147767_2_ - 1, p_147767_3_, p_147767_4_, WEST );
        boolean flag3 = p_147767_1_.canPaneConnectTo(this.blockAccess, p_147767_2_ + 1, p_147767_3_, p_147767_4_, EAST );
        if(!flag && !flag1 && !flag2 && !flag3){
            tessellator.addVertexWithUV(d9, (double)(p_147767_3_ + 1), d13, d21, d2);
            tessellator.addVertexWithUV(d9, (double)(p_147767_3_ + 0), d13, d21, d3);
            tessellator.addVertexWithUV(d10, (double)(p_147767_3_ + 0), d13, d0, d3);
            tessellator.addVertexWithUV(d10, (double)(p_147767_3_ + 1), d13, d0, d2);
            tessellator.addVertexWithUV(d10, (double)(p_147767_3_ + 1), d13, d21, d2);
            tessellator.addVertexWithUV(d10, (double)(p_147767_3_ + 0), d13, d21, d3);
            tessellator.addVertexWithUV(d9, (double)(p_147767_3_ + 0), d13, d0, d3);
            tessellator.addVertexWithUV(d9, (double)(p_147767_3_ + 1), d13, d0, d2);

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
