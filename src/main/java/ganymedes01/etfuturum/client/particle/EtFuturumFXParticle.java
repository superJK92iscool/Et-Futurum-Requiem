package ganymedes01.etfuturum.client.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import ganymedes01.etfuturum.client.OpenGLHelper;
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
	private boolean fadingColor;
	protected boolean fadeAway;
	private float fadeTargetRed;
	private float fadeTargetGreen;
	private float fadeTargetBlue;
	
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
		fadingColor = false;
		if(texture != null) {
			textureName = texture.getResourcePath();
		} else {
			textureName = "null";
		}

		particleAlpha = (this.color >> 24 & 0xff) / 255F;
		particleRed = (this.color >> 16 & 0xff) / 255F;
		particleGreen = (this.color >> 8 & 0xff) / 255F;
		particleBlue = (this.color & 0xff) / 255F;
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
        
		int prevCurrentTexture = usesSheet ? currentTexture : 0;
		OpenGLHelper.enableBlend();
		OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setBrightness(getBrightnessForRender(entityBrightness));
		par1Tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
		par1Tessellator.addVertexWithUV(ipx - rx * this.scale - ryz * this.scale, ipy - rxz * this.scale,
				ipz - rz * this.scale - rxy * this.scale, 1.0D, (prevCurrentTexture + 1) * this.relativeTextureHeight);
		par1Tessellator.addVertexWithUV(ipx - rx * this.scale + ryz * this.scale, ipy + rxz * this.scale,
				ipz - rz * this.scale + rxy * this.scale, 1.0D, prevCurrentTexture * this.relativeTextureHeight);
		par1Tessellator.addVertexWithUV(ipx + rx * this.scale + ryz * this.scale, ipy + rxz * this.scale,
				ipz + rz * this.scale + rxy * this.scale, 0.0D, prevCurrentTexture * this.relativeTextureHeight);
		par1Tessellator.addVertexWithUV(ipx + rx * this.scale - ryz * this.scale, ipy - rxz * this.scale,
				ipz + rz * this.scale - rxy * this.scale, 0.0D, (prevCurrentTexture + 1) * this.relativeTextureHeight);
		par1Tessellator.draw();

		OpenGLHelper.disableBlend();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevTex);
	}

	@Override
	public int getFXLayer() {
		return 3;
	}
	
    public void setColorFade(int rgb)
    {
        this.fadeTargetRed = (float)((rgb & 16711680) >> 16) / 255.0F;
        this.fadeTargetGreen = (float)((rgb & 65280) >> 8) / 255.0F;
        this.fadeTargetBlue = (float)((rgb & 255) >> 0) / 255.0F;
        this.fadingColor = true;
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

        if (particleAge > particleMaxAge / 2)
        {
        	if(fadeAway) {
                setAlphaF(1.0F - ((float)particleAge - (float)(particleMaxAge / 2)) / (float)particleMaxAge);
        	}

            if (fadingColor)
            {
                particleRed += (fadeTargetRed - particleRed) * 0.2F;
                particleGreen += (fadeTargetGreen - particleGreen) * 0.2F;
                particleBlue += (fadeTargetBlue - particleBlue) * 0.2F;
            }
        }
	}

}