package ganymedes01.etfuturum.client.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EtFuturumFXParticle extends EntityFX {

	protected ResourceLocation particleTexture;
	protected float scale;
	protected int color;
	protected int textures;
	protected double relativeTextureHeight;
	protected int currentTexture = 0;
	protected int textureCounter = 0;
	protected float entityBrightness;
	private final String textureName;
	
	protected boolean usesSheet;
	
	protected static Random particleRand = new Random();
	
	public EtFuturumFXParticle(World world, double x, double y, double z, double mx, double my, double mz, int maxAge,
			float scale, int color, ResourceLocation texture, int textures) {
		super(world, x, y, z, 0, 0, 0);
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.motionX = this.motionX * 0.009999999776482582D + mx;
		this.motionY = this.motionY * 0.009999999776482582D + my;
		this.motionZ = this.motionZ * 0.009999999776482582D + mz;
		this.particleMaxAge = maxAge;
		this.noClip = true;
		this.color = color;
		this.scale = scale;
		this.textures = textures;
		this.relativeTextureHeight = usesSheet ? (1.0D / this.textures) : 1;
		this.particleTexture = texture;
		this.usesSheet = true;
		if(texture != null) {
			textureName = texture.getResourcePath();
		} else {
			textureName = "null";
		}
	}

	@Override
	public void renderParticle(Tessellator par1Tessellator, float partialTicks, float rx, float rxz, float rz,
			float ryz, float rxy) {
		float ipx = (float) ((this.prevPosX + (this.posX - this.prevPosX) * partialTicks) - EntityFX.interpPosX);
		float ipy = (float) ((this.prevPosY + (this.posY - this.prevPosY) * partialTicks) - EntityFX.interpPosY);
		float ipz = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks) - EntityFX.interpPosZ);

		int prevTex = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		
		if(!usesSheet) {
			String domain = particleTexture.getResourceDomain();

	        if (domain == null || domain.length() == 0)
	        {
	            domain = "minecraft";
	        }
			particleTexture = new ResourceLocation(domain + ":" +
	        textureName.substring(0, textureName.length()-4) + "_" + currentTexture + textureName.substring(textureName.length()-4, textureName.length()));
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.particleTexture);

		float a = (this.color >> 24 & 0xff) / 255F;
		float r = (this.color >> 16 & 0xff) / 255F;
		float g = (this.color >> 8 & 0xff) / 255F;
		float b = (this.color & 0xff) / 255F;

		int prevCurrentTexture = usesSheet ? currentTexture : 0;
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setBrightness(getBrightnessForRender(entityBrightness));
		par1Tessellator.setColorRGBA_F(r, g, b, a);
		par1Tessellator.addVertexWithUV(ipx - rx * this.scale - ryz * this.scale, ipy - rxz * this.scale,
				ipz - rz * this.scale - rxy * this.scale, 1.0D, (prevCurrentTexture + 1) * this.relativeTextureHeight);
		par1Tessellator.addVertexWithUV(ipx - rx * this.scale + ryz * this.scale, ipy + rxz * this.scale,
				ipz - rz * this.scale + rxy * this.scale, 1.0D, prevCurrentTexture * this.relativeTextureHeight);
		par1Tessellator.addVertexWithUV(ipx + rx * this.scale + ryz * this.scale, ipy + rxz * this.scale,
				ipz + rz * this.scale + rxy * this.scale, 0.0D, prevCurrentTexture * this.relativeTextureHeight);
		par1Tessellator.addVertexWithUV(ipx + rx * this.scale - ryz * this.scale, ipy - rxz * this.scale,
				ipz + rz * this.scale - rxy * this.scale, 0.0D, (prevCurrentTexture + 1) * this.relativeTextureHeight);
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
		if (!this.onGround && usesSheet) {
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