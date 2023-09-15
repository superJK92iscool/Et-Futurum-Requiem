package ganymedes01.etfuturum.client.particle;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CopperGlowFX extends EtFuturumFXParticle {

	public CopperGlowFX(World world, double x, double y, double z, double mx, double my, double mz, int maxAge,
						float scale, int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, 0, 0, 0, maxAge, scale, color, texture, textures);
		motionX = (particleRand.nextDouble() * 0.01D) - 0.005D;
		motionY = (particleRand.nextDouble() * 0.01D) - 0.005D;
		motionZ = (particleRand.nextDouble() * 0.01D) - 0.005D;
		this.particleGravity = 0;
	}
}
