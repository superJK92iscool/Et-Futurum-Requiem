package ganymedes01.etfuturum.client.particle;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FallingDustFX extends EtFuturumFXParticle {
	float oSize;
	final float rotSpeed;

	public FallingDustFX(World world, double x, double y, double z, double mx, double my, double mz, int maxAge, float scale, int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, mx, my, mz, maxAge, scale, color, texture, textures);
		float f = 0.9F;
		this.particleScale *= 0.75F;
		this.particleScale *= 0.9F;
		this.oSize = this.particleScale;
		this.particleMaxAge = (int) (32.0D / (Math.random() * 0.8D + 0.2D));
		this.particleMaxAge = (int) ((float) this.particleMaxAge * 0.9F);
		this.rotSpeed = ((float) Math.random() - 0.5F) * 0.1F;
		this.particleAngle = (float) Math.random() * ((float) Math.PI * 2F);
		currentTexture = 7;
		usesSheet = false;
		noClip = false;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.particleAge % Math.round(particleMaxAge / 8) == 0) {
			if (currentTexture > 0)
				this.currentTexture--;
		}

		this.prevParticleAngle = this.particleAngle;
		this.particleAngle += (float) Math.PI * this.rotSpeed * 2.0F;

		if (this.isCollided) {
			this.prevParticleAngle = this.particleAngle = 0.0F;
		}
		this.motionY -= 0.003000000026077032D;
		this.motionY = Math.max(this.motionY, -0.14000000059604645D);
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float partialTicks, float rx, float rxz, float rz,
							   float ryz, float rxy) {
		float f = ((float) this.particleAge + partialTicks) / (float) this.particleMaxAge * 32.0F;
		f = MathHelper.clamp_float(f, 0.0F, 1.0F);
		this.particleScale = this.oSize * f;
		super.renderParticle(par1Tessellator, partialTicks, rx, rxz, rz, ryz, rxy);
	}
}
