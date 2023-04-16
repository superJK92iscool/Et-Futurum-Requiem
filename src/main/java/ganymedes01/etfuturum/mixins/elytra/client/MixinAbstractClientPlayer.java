package ganymedes01.etfuturum.mixins.elytra.client;

import ganymedes01.etfuturum.api.elytra.IClientElytraPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractClientPlayer.class)
public class MixinAbstractClientPlayer implements IClientElytraPlayer {
	@Override
	public float getRotateElytraX() {
		return rotateElytraX;
	}

	@Override
	public void setRotateElytraX(float rotateElytraX) {
		this.rotateElytraX = rotateElytraX;
	}

	@Override
	public float getRotateElytraY() {
		return rotateElytraY;
	}

	@Override
	public void setRotateElytraY(float rotateElytraY) {
		this.rotateElytraY = rotateElytraY;
	}

	@Override
	public float getRotateElytraZ() {
		return rotateElytraZ;
	}

	@Override
	public void setRotateElytraZ(float rotateElytraZ) {
		this.rotateElytraZ = rotateElytraZ;
	}

	float rotateElytraX, rotateElytraY, rotateElytraZ;

}
