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
	private int etfuturum$renderX;
	@Unique
	private int etfuturum$renderY;
	@Unique
	private int etfuturum$renderZ;

	@Inject(method = "renderBlockFlowerpot", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
	private void captureRenderPos(BlockFlowerPot pot, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		etfuturum$renderX = x;
		etfuturum$renderY = y;
		etfuturum$renderZ = z;
	}


	@Redirect(method = "renderBlockFlowerpot", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
	private IIcon useProperIcon(RenderBlocks instance, Block block, int side, int meta) {
		return block.getIcon(blockAccess, etfuturum$renderX, etfuturum$renderY, etfuturum$renderZ, side);
	}
}