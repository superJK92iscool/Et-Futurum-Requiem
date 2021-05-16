package ganymedes01.etfuturum.client.particle;

import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BarrierParticleFX extends EtFuturumFXParticle {

	public BarrierParticleFX(World world, double x, double y, double z, double mx, double my, double mz, int maxAge,
			float scale, int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, 0, 0, 0, maxAge, scale, color, texture, textures);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		particleMaxAge = maxAge + MathHelper.getRandomIntegerInRange(new Random(), 0, 10);
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		if (this.particleAge++ >= this.particleMaxAge/* || this.worldObj.getBlock((int)posX, (int)posY, (int)posZ) != ModBlocks.barrier*/)
		{
			this.setDead();
		}
	}
}
