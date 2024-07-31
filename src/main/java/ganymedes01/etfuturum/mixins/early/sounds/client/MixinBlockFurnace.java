package ganymedes01.etfuturum.mixins.early.sounds.client;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.BlockFurnace;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = BlockFurnace.class)
public class MixinBlockFurnace {

	@Shadow
	@Final
	private boolean field_149932_b;

	@Inject(method = "randomDisplayTick(Lnet/minecraft/world/World;IIILjava/util/Random;)V",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockMetadata(III)I", shift = At.Shift.BEFORE))
	private void randomDisplayTickMixin(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_, CallbackInfo ci) {
		if (p_149734_5_.nextDouble() < 0.1D) {
			p_149734_1_.playSound(p_149734_2_ + .5D, p_149734_3_ + .5D, p_149734_4_ + .5D, Reference.MCAssetVer + ":block.furnace.fire_crackle", 1, 1, false);
		}
	}
}
