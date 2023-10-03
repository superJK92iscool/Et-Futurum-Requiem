package ganymedes01.etfuturum.mixins.flowerpotfix.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderBlocks.class)
public abstract class MixinRenderBlocks {

	@Shadow
	public IBlockAccess blockAccess;
	@Unique
	private int renderX;
	@Unique
	private int renderY;
	@Unique
	private int renderZ;

	@Inject(method = "renderBlockFlowerpot", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
	private void captureRenderPos(BlockFlowerPot pot, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		renderX = x;
		renderY = y;
		renderZ = z;
	}


	@Redirect(method = "renderBlockFlowerpot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
	private IIcon useProperIcon(RenderBlocks instance, Block block, int side, int meta) {
		return block.getIcon(blockAccess, renderX, renderY, renderZ, side);
	}
}