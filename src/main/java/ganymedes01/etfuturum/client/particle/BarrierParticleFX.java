package ganymedes01.etfuturum.client.particle;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BarrierParticleFX extends EtFuturumFXParticle {

	public BarrierParticleFX(World world, double x, double y, double z, double mx, double my, double mz, int maxAge,
			float scale, int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, 0, 0, 0, maxAge, scale, color, texture, textures);
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
	}
	
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
