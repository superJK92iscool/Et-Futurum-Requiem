package ganymedes01.etfuturum.client.particle;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CopperGlowFX extends EtFuturumFXParticle {

	public CopperGlowFX(World world, double x, double y, double z, double mx, double my, double mz, int maxAge,
			float scale, int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, 0, 0, 0, maxAge, scale, color, texture, textures);
		//Cannot divide by 0 (divideByZeroException), so in the unlikely event nextDouble outputs zero, don't divide and just put 0 as the result.
		double xrand = particleRand.nextDouble();
		double yrand = particleRand.nextDouble();
		double zrand = particleRand.nextDouble();
		motionX = (xrand == 0.0D ? 0D : (xrand / 100D)) - 0.005D;
		motionY = (yrand == 0.0D ? 0D : (yrand / 100D)) - 0.005D;
		motionZ = (zrand == 0.0D ? 0D : (zrand / 100D)) - 0.005D;
		this.particleGravity = 0;
		this.noClip = false;
	}
}
