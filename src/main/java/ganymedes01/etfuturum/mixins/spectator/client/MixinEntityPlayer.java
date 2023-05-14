package ganymedes01.etfuturum.mixins.spectator.client;

import ganymedes01.etfuturum.spectator.SpectatorMode;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {
	@Inject(method = "isInvisibleToPlayer", at = @At("HEAD"), cancellable = true)
	public void isInvisibleToPlayer(EntityPlayer p_98034_1_, CallbackInfoReturnable<Boolean> cir) {
		if(SpectatorMode.isSpectator(p_98034_1_)) {
			cir.setReturnValue(false);
		}
	}
}
