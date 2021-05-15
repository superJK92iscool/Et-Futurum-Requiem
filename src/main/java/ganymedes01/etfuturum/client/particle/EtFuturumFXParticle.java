package ganymedes01.etfuturum.client.particle;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EtFuturumFXParticle extends EntityFX {

	private ResourceLocation particleTexture;
	private float scale;
	private int color;
	private int textures;
	private double relativeTextureHeight;
	private int currentTexture = 0;
	private int textureCounter = 0;
	private float entityBrightness;
	
	public EtFuturumFXParticle(World world, double x, double y, double z, double mx, double my, double mz, int maxAge,
			float scale, int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, 0, 0, 0);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.motionX = this.motionX * 0.009999999776482582D + mx;
		this.motionY = this.motionY * 0.009999999776482582D + my;
		this.motionZ = this.motionZ * 0.009999999776482582D + mz;
		this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
		this.noClip = true;
		this.color = color;
		this.scale = scale;
		this.textures = textures;
		this.relativeTextureHeight = 1.0D / this.textures;
		this.particleTexture = texture;
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float partialTicks, float rx, float rxz, float rz,
			float ryz, float rxy) {
		float ipx = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks) - this.interpPosX);
		float ipy = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks) - this.interpPosY);
		float ipz = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks) - this.interpPosZ);

		int prevTex = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.particleTexture);

		float a = (float) (this.color >> 24 & 0xff) / 255F;
		float r = (float) (this.color >> 16 & 0xff) / 255F;
		float g = (float) (this.color >> 8 & 0xff) / 255F;
		float b = (float) (this.color & 0xff) / 255F;

		par1Tessellator.startDrawingQuads();
		par1Tessellator.setBrightness(getBrightnessForRender(entityBrightness));
		par1Tessellator.setColorRGBA_F(r, g, b, a);
		par1Tessellator.addVertexWithUV(ipx - rx * this.scale - ryz * this.scale, ipy - rxz * this.scale,
				ipz - rz * this.scale - rxy * this.scale, 1.0D, (this.currentTexture + 1) * this.relativeTextureHeight);
		par1Tessellator.addVertexWithUV(ipx - rx * this.scale + ryz * this.scale, ipy + rxz * this.scale,
				ipz - rz * this.scale + rxy * this.scale, 1.0D, this.currentTexture * this.relativeTextureHeight);
		par1Tessellator.addVertexWithUV(ipx + rx * this.scale + ryz * this.scale, ipy + rxz * this.scale,
				ipz + rz * this.scale + rxy * this.scale, 0.0D, this.currentTexture * this.relativeTextureHeight);
		par1Tessellator.addVertexWithUV(ipx + rx * this.scale - ryz * this.scale, ipy - rxz * this.scale,
				ipz + rz * this.scale - rxy * this.scale, 0.0D, (this.currentTexture + 1) * this.relativeTextureHeight);
		par1Tessellator.draw();

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevTex);
	}

	@Override
	public int getFXLayer() {
		return 3;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		entityBrightness = worldObj.getLightBrightness((int)posX, (int)posY, (int)posZ);
		if (!this.onGround) {
			this.textureCounter++;
			if (this.textureCounter >= 3) {
				this.textureCounter = 0;
				this.currentTexture++;
				if (this.currentTexture >= this.textures) {
					this.currentTexture = 0;
				}
			}
		}
	}

}