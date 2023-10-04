package ganymedes01.etfuturum.mixins.soulfire;

import ganymedes01.etfuturum.ducks.ISoulFireInfo;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
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
	@Shadow
	public double renderMaxZ;
	@Unique
	private int etfuturum$renderX;
	@Unique
	private int etfuturum$renderY;
	@Unique
	private int etfuturum$renderZ;

	@Inject(method = "renderBlockFire", at = @At(value = "HEAD"))
	private void captureRenderPos(BlockFire pot, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
		etfuturum$renderX = x;
		etfuturum$renderY = y;
		etfuturum$renderZ = z;
	}

	@Redirect(method = "renderBlockFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockFire;getFireIcon(I)Lnet/minecraft/util/IIcon;"))
	private IIcon getFireIcon(BlockFire instance, int p_149840_1_) {
		if (instance == Blocks.fire && instance instanceof ISoulFireInfo) { //Only apply to vanilla fires
			if (((ISoulFireInfo) instance).isSoulFire(blockAccess, etfuturum$renderX, etfuturum$renderY, etfuturum$renderZ)) {
				return ((ISoulFireInfo) instance).getSoulFireIcon(p_149840_1_);
			}
		}
		return instance.getFireIcon(p_149840_1_);
	}
}
