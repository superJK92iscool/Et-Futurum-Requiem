package ganymedes01.etfuturum.client.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;

public abstract class BlockModelBase implements ISimpleBlockRenderingHandler {
	
	/**
	 * Renders the YNeg face with proper shading like renderStandardBlock.
	 */
	public void renderFaceYNeg(RenderBlocks renderer, Block block, double dx, double dy, double dz)
	{
		this.renderFaceYNeg(renderer, block, dx, dy, dz, 0, 0, 0);
	}

	/**
	 * Renders the YNeg face with proper shading like renderStandardBlock.
	 */
	public void renderFaceYNeg(RenderBlocks renderer, Block block, double dx, double dy, double dz, double offx, double offy, double offz)
	{
		renderer.enableAO = false;
		Tessellator tessellator = Tessellator.instance;
		
		int x = MathHelper.floor_double(dx);
		int y = MathHelper.floor_double(dy);
		int z = MathHelper.floor_double(dz);
		
		int m = block.colorMultiplier(renderer.blockAccess, x, y, z);
		float f = (float)(m >> 16 & 255) / 255.0F;
		float f1 = (float)(m >> 8 & 255) / 255.0F;
		float f2 = (float)(m & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}
		
		float f3 = 0.5F;
		float f10 = f3;
		float f13 = f3;
		float f16 = f3;
		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

//        if(Minecraft.isAmbientOcclusionEnabled() && block.getLightValue() == 0) {
//          if(renderer.partialRenderBounds) {
//              
//          } else {
//
//
//                boolean flag2;
//                boolean flag3;
//                boolean flag4;
//                boolean flag5;
//                int i1;
//                float f7;
//
//                if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y - 1, z, 0))
//                {
//                    if (renderer.renderMinY <= 0.0D)
//                    {
//                        --y;
//                    }
//
//                    renderer.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z);
//                    renderer.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1);
//                    renderer.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1);
//                    renderer.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z);
//                    renderer.aoLightValueScratchXYNN = renderer.blockAccess.getBlock(x - 1, y, z).getAmbientOcclusionLightValue();
//                    renderer.aoLightValueScratchYZNN = renderer.blockAccess.getBlock(x, y, z - 1).getAmbientOcclusionLightValue();
//                    renderer.aoLightValueScratchYZNP = renderer.blockAccess.getBlock(x, y, z + 1).getAmbientOcclusionLightValue();
//                    renderer.aoLightValueScratchXYPN = renderer.blockAccess.getBlock(x + 1, y, z).getAmbientOcclusionLightValue();
//                    flag2 = renderer.blockAccess.getBlock(x + 1, y - 1, z).getCanBlockGrass();
//                    flag3 = renderer.blockAccess.getBlock(x - 1, y - 1, z).getCanBlockGrass();
//                    flag4 = renderer.blockAccess.getBlock(x, y - 1, z + 1).getCanBlockGrass();
//                    flag5 = renderer.blockAccess.getBlock(x, y - 1, z - 1).getCanBlockGrass();
//
//                    if (!flag5 && !flag3)
//                    {
//                        renderer.aoLightValueScratchXYZNNN = renderer.aoLightValueScratchXYNN;
//                        renderer.aoBrightnessXYZNNN = renderer.aoBrightnessXYNN;
//                    }
//                    else
//                    {
//                        renderer.aoLightValueScratchXYZNNN = renderer.blockAccess.getBlock(x - 1, y, z - 1).getAmbientOcclusionLightValue();
//                        renderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z - 1);
//                    }
//
//                    if (!flag4 && !flag3)
//                    {
//                        renderer.aoLightValueScratchXYZNNP = renderer.aoLightValueScratchXYNN;
//                        renderer.aoBrightnessXYZNNP = renderer.aoBrightnessXYNN;
//                    }
//                    else
//                    {
//                        renderer.aoLightValueScratchXYZNNP = renderer.blockAccess.getBlock(x - 1, y, z + 1).getAmbientOcclusionLightValue();
//                        renderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z + 1);
//                    }
//
//                    if (!flag5 && !flag2)
//                    {
//                        renderer.aoLightValueScratchXYZPNN = renderer.aoLightValueScratchXYPN;
//                        renderer.aoBrightnessXYZPNN = renderer.aoBrightnessXYPN;
//                    }
//                    else
//                    {
//                        renderer.aoLightValueScratchXYZPNN = renderer.blockAccess.getBlock(x + 1, y, z - 1).getAmbientOcclusionLightValue();
//                        renderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z - 1);
//                    }
//
//                    if (!flag4 && !flag2)
//                    {
//                        renderer.aoLightValueScratchXYZPNP = renderer.aoLightValueScratchXYPN;
//                        renderer.aoBrightnessXYZPNP = renderer.aoBrightnessXYPN;
//                    }
//                    else
//                    {
//                        renderer.aoLightValueScratchXYZPNP = renderer.blockAccess.getBlock(x + 1, y, z + 1).getAmbientOcclusionLightValue();
//                        renderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z + 1);
//                    }
//
//                    if (renderer.renderMinY <= 0.0D)
//                    {
//                        ++y;
//                    }
//
//                    i1 = l;
//
//                    if (renderer.renderMinY <= 0.0D || !renderer.blockAccess.getBlock(x, y - 1, z).isOpaqueCube())
//                    {
//                        i1 = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y - 1, z);
//                    }
//
//                    f7 = renderer.blockAccess.getBlock(x, y - 1, z).getAmbientOcclusionLightValue();
//                    f3 = (renderer.aoLightValueScratchXYZNNP + renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchYZNP + f7) / 4.0F;
//                    f6 = (renderer.aoLightValueScratchYZNP + f7 + renderer.aoLightValueScratchXYZPNP + renderer.aoLightValueScratchXYPN) / 4.0F;
//                    f5 = (f7 + renderer.aoLightValueScratchYZNN + renderer.aoLightValueScratchXYPN + renderer.aoLightValueScratchXYZPNN) / 4.0F;
//                    f4 = (renderer.aoLightValueScratchXYNN + renderer.aoLightValueScratchXYZNNN + f7 + renderer.aoLightValueScratchYZNN) / 4.0F;
//                    renderer.brightnessTopLeft = renderer.getAoBrightness(renderer.aoBrightnessXYZNNP, renderer.aoBrightnessXYNN, renderer.aoBrightnessYZNP, i1);
//                    renderer.brightnessTopRight = renderer.getAoBrightness(renderer.aoBrightnessYZNP, renderer.aoBrightnessXYZPNP, renderer.aoBrightnessXYPN, i1);
//                    renderer.brightnessBottomRight = renderer.getAoBrightness(renderer.aoBrightnessYZNN, renderer.aoBrightnessXYPN, renderer.aoBrightnessXYZPNN, i1);
//                    renderer.brightnessBottomLeft = renderer.getAoBrightness(renderer.aoBrightnessXYNN, renderer.aoBrightnessXYZNNN, renderer.aoBrightnessYZNN, i1);
//
//                    if (flag1)
//                    {
//                        renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = r * 0.5F;
//                        renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = g * 0.5F;
//                        renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = b * 0.5F;
//                    }
//                    else
//                    {
//                        renderer.colorRedTopLeft = renderer.colorRedBottomLeft = renderer.colorRedBottomRight = renderer.colorRedTopRight = 0.5F;
//                        renderer.colorGreenTopLeft = renderer.colorGreenBottomLeft = renderer.colorGreenBottomRight = renderer.colorGreenTopRight = 0.5F;
//                        renderer.colorBlueTopLeft = renderer.colorBlueBottomLeft = renderer.colorBlueBottomRight = renderer.colorBlueTopRight = 0.5F;
//                    }
//
//                    renderer.colorRedTopLeft *= f3;
//                    renderer.colorGreenTopLeft *= f3;
//                    renderer.colorBlueTopLeft *= f3;
//                    renderer.colorRedBottomLeft *= f4;
//                    renderer.colorGreenBottomLeft *= f4;
//                    renderer.colorBlueBottomLeft *= f4;
//                    renderer.colorRedBottomRight *= f5;
//                    renderer.colorGreenBottomRight *= f5;
//                    renderer.colorBlueBottomRight *= f5;
//                    renderer.colorRedTopRight *= f6;
//                    renderer.colorGreenTopRight *= f6;
//                    renderer.colorBlueTopRight *= f6;
//                    renderer.renderFaceYNeg(block, dx + offx, dy + offy, dz + offz, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 0));
//                }
//          }
//        } else {
			if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y - 1, z, 0))
			{
				tessellator.setBrightness(renderer.renderMinY + Math.abs(offy) > 0.0D ? l :
					block.getMixedBrightnessForBlock(renderer.blockAccess, x, MathHelper.floor_double(y - 1), z));
				tessellator.setColorOpaque_F(f10, f13, f16);
				renderer.renderFaceYNeg(block, dx + offx, dy + offy, dz + offz, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 0));
			}
//        }
	}
	
	/**
	 * Renders the YPos face with proper shading like renderStandardBlock.
	 */
	public void renderFaceYPos(RenderBlocks renderer, Block block, double dx, double dy, double dz)
	{
		this.renderFaceYPos(renderer, block, dx, dy, dz, 0, 0, 0);
	}

	/**
	 * Renders the YPos face with proper shading like renderStandardBlock.
	 */
	public void renderFaceYPos(RenderBlocks renderer, Block block, double dx, double dy, double dz, double offx, double offy, double offz)
	{
		renderer.enableAO = false;
		Tessellator tessellator = Tessellator.instance;
		
		int x = MathHelper.floor_double(dx);
		int y = MathHelper.floor_double(dy);
		int z = MathHelper.floor_double(dz);
		
		int m = block.colorMultiplier(renderer.blockAccess, x, y, z);
		float f = (float)(m >> 16 & 255) / 255.0F;
		float f1 = (float)(m >> 8 & 255) / 255.0F;
		float f2 = (float)(m & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}
		
		float f4 = 1.0F;
		float f7 = f4 * f;
		float f8 = f4 * f1;
		float f9 = f4 * f2;
		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y + 1, z, 1))
		{
			tessellator.setBrightness(renderer.renderMaxY - Math.abs(offy) < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y + 1, z));
			tessellator.setColorOpaque_F(f7, f8, f9);
			renderer.renderFaceYPos(block, dx + offx, dy + offy, dz + offz, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 1));
		}
	}
	
	/**
	 * Renders the ZNeg face with proper shading like renderStandardBlock.
	 */
	public void renderFaceZNeg(RenderBlocks renderer, Block block, double dx, double dy, double dz)
	{
		this.renderFaceZNeg(renderer, block, dx, dy, dz, 0, 0, 0);
	}

	/**
	 * Renders the ZNeg face with proper shading like renderStandardBlock.
	 */
	public void renderFaceZNeg(RenderBlocks renderer, Block block, double dx, double dy, double dz, double offx, double offy, double offz)
	{
		renderer.enableAO = false;
		Tessellator tessellator = Tessellator.instance;
		
		int x = MathHelper.floor_double(dx);
		int y = MathHelper.floor_double(dy);
		int z = MathHelper.floor_double(dz);
		
		int m = block.colorMultiplier(renderer.blockAccess, x, y, z);
		float f = (float)(m >> 16 & 255) / 255.0F;
		float f1 = (float)(m >> 8 & 255) / 255.0F;
		float f2 = (float)(m & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}
		
		float f5 = 0.8F;
		float f11 = f5;
		float f14 = f5;
		float f17 = f5;
		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z - 1, 2))
		{
			tessellator.setBrightness(renderer.renderMinZ + Math.abs(offz) > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
			tessellator.setColorOpaque_F(f11, f14, f17);
			renderer.renderFaceZNeg(block, dx + offx, dy + offy, dz + offz, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 2));
		}
	}
	
	/**
	 * Renders the ZPos face with proper shading like renderStandardBlock.
	 */
	public void renderFaceZPos(RenderBlocks renderer, Block block, double dx, double dy, double dz)
	{
		this.renderFaceZPos(renderer, block, dx, dy, dz, 0, 0, 0);
	}

	/**
	 * Renders the ZPos face with proper shading like renderStandardBlock.
	 */
	public void renderFaceZPos(RenderBlocks renderer, Block block, double dx, double dy, double dz, double offx, double offy, double offz)
	{
		renderer.enableAO = false;
		Tessellator tessellator = Tessellator.instance;
		
		int x = MathHelper.floor_double(dx);
		int y = MathHelper.floor_double(dy);
		int z = MathHelper.floor_double(dz);
		
		int m = block.colorMultiplier(renderer.blockAccess, x, y, z);
		float f = (float)(m >> 16 & 255) / 255.0F;
		float f1 = (float)(m >> 8 & 255) / 255.0F;
		float f2 = (float)(m & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}
		
		float f5 = 0.8F;
		float f11 = f5;
		float f14 = f5;
		float f17 = f5;
		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z + 1, 3))
		{
			tessellator.setBrightness(renderer.renderMaxZ - Math.abs(offz) < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
			tessellator.setColorOpaque_F(f11, f14, f17);
			renderer.renderFaceZPos(block, dx + offx, dy + offy, dz + offz, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 3));
		}
	}

	/**
	 * Renders the XNeg face with proper shading like renderStandardBlock.
	 */
	public void renderFaceXNeg(RenderBlocks renderer, Block block, double dx, double dy, double dz)
	{
		this.renderFaceXNeg(renderer, block, dx, dy, dz, 0, 0, 0);
	}

	/**
	 * Renders the XNeg face with proper shading like renderStandardBlock.
	 */
	public void renderFaceXNeg(RenderBlocks renderer, Block block, double dx, double dy, double dz, double offx, double offy, double offz)
	{
		renderer.enableAO = false;
		Tessellator tessellator = Tessellator.instance;
		
		int x = MathHelper.floor_double(dx);
		int y = MathHelper.floor_double(dy);
		int z = MathHelper.floor_double(dz);
		
		int m = block.colorMultiplier(renderer.blockAccess, x, y, z);
		float f = (float)(m >> 16 & 255) / 255.0F;
		float f1 = (float)(m >> 8 & 255) / 255.0F;
		float f2 = (float)(m & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}
		
		float f6 = 0.6F;
		float f12 = f6;
		float f15 = f6;
		float f18 = f6;

		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x - 1, y, z, 4))
		{
			tessellator.setBrightness(renderer.renderMinX + Math.abs(offx) > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
			tessellator.setColorOpaque_F(f12, f15, f18);
			renderer.renderFaceXNeg(block, dx + offx, dy + offy, dz + offz, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 4));
		}
	}

	/**
	 * Renders the XPos face with proper shading like renderStandardBlock.
	 */
	public void renderFaceXPos(RenderBlocks renderer, Block block, double dx, double dy, double dz)
	{
		this.renderFaceXPos(renderer, block, dx, dy, dz, 0, 0, 0);
	}
	
	/**
	 * Renders the XPos face with proper shading like renderStandardBlock.
	 */
	public void renderFaceXPos(RenderBlocks renderer, Block block, double dx, double dy, double dz, double offx, double offy, double offz)
	{
		renderer.enableAO = false;
		Tessellator tessellator = Tessellator.instance;
		
		int x = MathHelper.floor_double(dx);
		int y = MathHelper.floor_double(dy);
		int z = MathHelper.floor_double(dz);
		
		int m = block.colorMultiplier(renderer.blockAccess, x, y, z);
		float f = (float)(m >> 16 & 255) / 255.0F;
		float f1 = (float)(m >> 8 & 255) / 255.0F;
		float f2 = (float)(m & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}
		
		float f6 = 0.6F;
		float f12 = f6;
		float f15 = f6;
		float f18 = f6;

		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x + 1, y, z, 5))
		{
			tessellator.setBrightness(renderer.renderMaxX - Math.abs(offx) < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
			tessellator.setColorOpaque_F(f12, f15, f18);
			renderer.renderFaceXPos(block, dx + offx, dy + offy, dz + offz, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 5));
		}
	}
}
