package ganymedes01.etfuturum.mixins.client;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.BlockFurnace;
import net.minecraft.world.World;

@Mixin(value = BlockFurnace.class)
public class MixinBlockFurnace {
	
	@Shadow
	private boolean field_149932_b;

	@Inject(method = "randomDisplayTick(Lnet/minecraft/world/World;IIILjava/util/Random;)V", at = @At(value = "HEAD"))
	private void randomDisplayTickMixin(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_, CallbackInfo ci) {
		//Tried to find out how to inject into the already existing if (this.field_149932_b) clause, gave up.
        if (this.field_149932_b && p_149734_5_.nextDouble() < 0.1D)
        {
        	p_149734_1_.playSound(p_149734_2_ + .5D, p_149734_3_ + .5D, p_149734_4_ + .5D, Reference.MCv118 + ":block.furnace.fire_crackle", 1, 1, false);
        }
	}
}
