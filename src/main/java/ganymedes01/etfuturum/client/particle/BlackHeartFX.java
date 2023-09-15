package ganymedes01.etfuturum.client.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class BlackHeartFX extends EtFuturumFXParticle {

	public BlackHeartFX(World world, double x, double y, double z, double mx, double my, double mz, int maxAge,
						float scale, int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, (particleRand.nextFloat() - 0.5F) * 0.1D, 0.15D, (particleRand.nextFloat() - 0.5F) * 0.1D, maxAge, scale, color, texture, textures);
		particleGravity = 0.5F;
	}
}