package ganymedes01.etfuturum.client.particle;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EndRodFX extends EtFuturumFXParticle {

	public EndRodFX(World world, double x, double y, double z, double mx, double my, double mz, int maxAge, float scale,
			int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, mx, my, mz, maxAge, scale, color, texture, textures);
		motionX = particleRand.nextGaussian() * 0.005D;
		motionY = particleRand.nextGaussian() * 0.005D;
		motionZ = particleRand.nextGaussian() * 0.005D;
		particleGravity = 0.005F;
		usesSheet = false;
		this.currentTexture = 7;
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float partialTicks, float rx, float rxz, float rz,
			float ryz, float rxy) {
		super.renderParticle(par1Tessellator, partialTicks, rx, rxz, rz, ryz, rxy);
		float a = (this.color >> 24 & 0xff) / 255F;
		float r = (this.color >> 16 & 0xff) / 255F;
		float g = (this.color >> 8 & 0xff) / 255F;
		float b = (this.color & 0xff) / 255F;
		System.out.println(a + " " + r + " " + g + " " + b + " ");
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.particleAge % 8 == 0) {
			if(currentTexture > 0)
				this.currentTexture--;
		}
	}

}
