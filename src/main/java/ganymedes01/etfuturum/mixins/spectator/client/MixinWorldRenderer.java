package ganymedes01.etfuturum.mixins.spectator.client;

import ganymedes01.etfuturum.spectator.SpectatorMode;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public class MixinWorldRenderer {
	@Redirect(method = "updateRenderer", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getRenderType()I", ordinal = 0))
	private int skipInsideRenderForSpectator(Block instance) {
		if(SpectatorMode.isSpectator(Minecraft.getMinecraft().thePlayer)) {
			return -1;
		}
		return instance.getRenderType();
	}
}
