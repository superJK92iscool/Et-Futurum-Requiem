package ganymedes01.etfuturum.client.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockEndRodRender implements ISimpleBlockRenderingHandler {
	
	float r;
	float g;
	float b;

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);

		double x = 7 / 16.0;
		double y = 0;
		double z = 7 / 16.0;
		renderer.setRenderBounds(0, 1 / 16F, 0, 2 / 16F, 1, 2 / 16F);
		tessellator.startDrawingQuads();
		tessellator.setTextureUV(1, 1);
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
		tessellator.draw();

		x = 4 / 16.0;
		y = 0;
		z = 4 / 16.0;
		renderer.setRenderBounds(2 / 16F, 0, 2 / 16F, 6 / 16F, 1 / 16F, 6 / 16F);
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
		x = .25;
		y = -.5625;
		z = .25;
		renderer.setRenderBounds(0.125D, 0.5625, 0.125D, 0.375D, 0.625D, 0.375D);
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, x, y, z, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
		tessellator.draw();
    	
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		int meta = world.getBlockMetadata(x, y, z) % 6;

	    float f1 = 0.125F;
	    float f2 = 0.0625F;

	    int m = block.getMixedBrightnessForBlock(world, x, y, z);
		int l = block.colorMultiplier(world, x, y, z);

		float i = (l >> 16 & 255) / 255.0F;
		float i1 = (l >> 8 & 255) / 255.0F;
		float i2 = (l & 255) / 255.0F;
		float i3;

		if (EntityRenderer.anaglyphEnable)
		{
			i3 = (i * 30.0F + i1 * 59.0F + f2 * 11.0F) / 100.0F;
			float i4 = (i * 30.0F + f1 * 70.0F) / 100.0F;
			float i5 = (i * 30.0F + f2 * 70.0F) / 100.0F;
			i = i3;
			i1 = i4;
			i2 = i5;
		}

		tessellator.setColorOpaque_F(i, i1, i2);
		
		r = i;
		g = i1;
		b = i2;
		
		
		i3 = 0.1865F;
		i3 = 1;
		if (l != 16777215)
		{
			i = (l >> 16 & 255) / 255.0F;
			i1 = (l >> 8 & 255) / 255.0F;
			i2 = (l & 255) / 255.0F;
			tessellator.setColorOpaque_F(i * i3, i1 * i3, i2 * i3);
			
			r = i;
			g = i1;
			b = i2;
		}
		
	    tessellator.setBrightness(m);

	    double x1 = 0;
	    double y1 = 0;
	    double z1 = 0;
	    
	    if (meta == 0 || meta == 1)
	    {
			x1 = .4375D;
			y1 = meta == 0 ? -.0625D : 0;
			z1 = .4375D;
			renderer.setRenderBounds(0D, 0.0625D, 0D, 0.125D, 1D, 0.125D);
	    } else if (meta == 2 || meta == 3) {
			x1 = -.4375D;
			y1 = .4375D;
			z1 = meta == 2 ? -.0625D : 0;
	    	renderer.uvRotateTop = 3;
	    	renderer.uvRotateBottom = 3;
	    	renderer.uvRotateEast = 2;
	    	renderer.uvRotateWest = 1;
	    	renderer.uvRotateNorth = 1;
	    	renderer.uvRotateSouth = 2;
			renderer.setRenderBounds(0.875D, 0, 0.0625D, 1, 0.125D, 1);
	    } else if (meta == 4 || meta == 5) {
			x1 = meta == 4 ? -0.0625D : 0;
			y1 = .4375D;
			z1 = .4375D;
	    	renderer.uvRotateTop = 1;
	    	renderer.uvRotateBottom = 2;
	    	renderer.uvRotateEast = 2;
	    	renderer.uvRotateWest = 1;
	    	renderer.uvRotateNorth = 0;
	    	renderer.uvRotateSouth = 0;
			renderer.setRenderBounds(0.0625D, 0, 0, 1D, 0.125D, 0.125D);
	    }
	    
	    x1 += x;
	    y1 += y;
	    z1 += z;

	    if (meta == 0 || meta == 1) {
	        multiplyColorBy(tessellator, 0.8F);
			renderer.renderFaceZNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
			renderer.renderFaceZPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
	        multiplyColorBy(tessellator, 0.6F);
			renderer.renderFaceXNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
			renderer.renderFaceXPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
	    } else if (meta == 2 || meta == 3) {
	        multiplyColorBy(tessellator, 0.5F);
			renderer.renderFaceYNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
	        multiplyColorBy(tessellator, 1);
			renderer.renderFaceYPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
	        multiplyColorBy(tessellator, 0.6F);
			renderer.renderFaceXNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
			renderer.renderFaceXPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
	    } else if (meta == 4 || meta == 5) {
	        multiplyColorBy(tessellator, 0.5F);
			renderer.renderFaceYNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
	        multiplyColorBy(tessellator, 1);
			renderer.renderFaceYPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
	        multiplyColorBy(tessellator, 0.8F);
			renderer.renderFaceZNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
			renderer.renderFaceZPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
	    }
	    
	    if (meta == 0 || meta == 1)
	    {
			x1 = .3125D;
			y1 = meta == 0 ? -.0625D : 0;
			z1 = .4375D;
			renderer.setRenderBounds(.125D, 0.0625D, 0D, .25, 1D, .125);
	    } else if (meta == 2 || meta == 3) {
			x1 = -.4375D;
			y1 = .3125D;
			z1 = meta == 2 ? -.0625D : 0;
			renderer.setRenderBounds(0.875D, 0.125D, 0.0625D, 1, 0.25D, 1);
	    } else if (meta == 4 || meta == 5) {
			x1 = meta == 4 ? -0.0625D : 0;
			y1 = -.4375D;
			z1 = .3125D;
			renderer.setRenderBounds(0.0625D, 0.875D, 0.125D, 1D, 1, 0.25D);
	    }
	    
	    x1 += x;
	    y1 += y;
	    z1 += z;

	    if (meta == 0 || meta == 1) {
	        multiplyColorBy(tessellator, 0.5F);
			renderer.renderFaceYNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
	        multiplyColorBy(tessellator, 1);
			renderer.renderFaceYPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
	    } else if (meta == 2 || meta == 3) {
	        multiplyColorBy(tessellator, 0.8F);
			renderer.renderFaceZNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
			renderer.renderFaceZPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
	    } else if (meta == 4 || meta == 5) {
	        multiplyColorBy(tessellator, 0.6F);
			renderer.renderFaceXNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
			renderer.renderFaceXPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
	    }
		
		x1 = 0;
		y1 = 0;
		z1 = 0;
    	renderer.uvRotateTop = 0;
    	renderer.uvRotateBottom = 0;
    	renderer.uvRotateEast = 0;
    	renderer.uvRotateWest = 0;
    	renderer.uvRotateNorth = 0;
    	renderer.uvRotateSouth = 0;

	    if (meta == 0 || meta == 1)
	    {
			x1 = .25;
			y1 = meta == 0 ? .9375 : 0;
			z1 = .25;
			renderer.setRenderBounds(0.125D, 0, 0.125D, 0.375D, 0.0625D, 0.375D);
	    } else  if (meta == 2 || meta == 3) {
			x1 = .25;
			y1 = -.25;
			z1 = meta == 2 ? .9375 : 0;
			renderer.setRenderBounds(0.125D, 0.625D, 0, .375, .875, .0625);
	    } else  if (meta == 4 || meta == 5) {
			x1 = meta == 4 ? 0 : -.9375;
			y1 = -.25;
			z1 = .25;
	    	renderer.uvRotateNorth = 2;
	    	renderer.uvRotateSouth = 1;
			renderer.setRenderBounds(0.9375, 0.625D, 0.125D, 1, .875, .375);
	    }
	    
	    x1 += x;
	    y1 += y;
	    z1 += z;

	    if (meta == 0 || meta == 1)
	    {
	        multiplyColorBy(tessellator, 0.5F);
			renderer.renderFaceYNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
	        multiplyColorBy(tessellator, 1);
			renderer.renderFaceYPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
	    } else if (meta == 2 || meta == 3) {
			multiplyColorBy(tessellator, 0.8F);
			renderer.renderFaceZNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
			renderer.renderFaceZPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
	    } else if (meta == 4 || meta == 5) {
			multiplyColorBy(tessellator, 0.6F);
			renderer.renderFaceXNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
			renderer.renderFaceXPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
	    }

		x1 = 0;
		y1 = 0;
		z1 = 0;
    	renderer.uvRotateTop = 0;
    	renderer.uvRotateBottom = 0;
    	renderer.uvRotateEast = 0;
    	renderer.uvRotateWest = 0;
    	renderer.uvRotateNorth = 0;
    	renderer.uvRotateSouth = 0;
		
		if(meta == 0 || meta == 1) {
			x1 = .25;
			y1 = meta == 0 ? .375 : -.5625;
			z1 = .25;
			renderer.setRenderBounds(0.125D, 0.5625, 0.125D, 0.375D, 0.625D, 0.375D);
		} else if (meta == 2 || meta == 3) {
			x1 = -.25;
			y1 = .25;
			z1 = meta == 2 ? .375 : -.5625;
	    	renderer.uvRotateTop = 3;
	    	renderer.uvRotateBottom = 3;
	    	renderer.uvRotateNorth = 1;
	    	renderer.uvRotateSouth = 2;
			renderer.setRenderBounds(0.625, .125, .5625, .875, .375, .625);
		} else if (meta == 4 || meta == 5) {
			x1 = meta == 4 ? .375 : -.5625;
			y1 = .25;
			z1 = .25;
	    	renderer.uvRotateTop = 1;
	    	renderer.uvRotateBottom = 2;
	    	renderer.uvRotateEast = 2;
	    	renderer.uvRotateWest = 1;
			renderer.setRenderBounds(0.5625, 0.125, 0.125, 0.625, 0.375, 0.375);
		}

	    x1 += x;
	    y1 += y;
	    z1 += z;

		if(meta == 0 || meta == 1) {
	        multiplyColorBy(tessellator, 0.8F);
			renderer.renderFaceZNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
			renderer.renderFaceZPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
	        multiplyColorBy(tessellator, 0.6F);
			renderer.renderFaceXNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
			renderer.renderFaceXPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
		} else if (meta == 2 || meta == 3) {
	        multiplyColorBy(tessellator, 0.5F);
			renderer.renderFaceYNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
	        multiplyColorBy(tessellator, 1);
			renderer.renderFaceYPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
	        multiplyColorBy(tessellator, 0.6F);
			renderer.renderFaceXNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
			renderer.renderFaceXPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
		}  else if (meta == 4 || meta == 5) {
	        multiplyColorBy(tessellator, 0.5F);
			renderer.renderFaceYNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
	        multiplyColorBy(tessellator, 1);
			renderer.renderFaceYPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
	        multiplyColorBy(tessellator, 0.8F);
			renderer.renderFaceZNeg(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
			renderer.renderFaceZPos(block, x1, y1, z1, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		}
		
		

		renderer.uvRotateBottom = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
    	
		return true;
	}
	
	private void multiplyColorBy(Tessellator tessellator, float m) {
		tessellator.setColorOpaque_F(r * m, g * m, b * m);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.END_ROD;
	}
}