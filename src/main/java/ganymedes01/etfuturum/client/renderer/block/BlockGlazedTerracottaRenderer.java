package ganymedes01.etfuturum.client.renderer.block;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockGlazedTerracottaRenderer extends FlippedUVCubeModel implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.uvRotateTop = 1;
		renderer.uvRotateNorth = 2;
		//renderer.uvRotateEast = 3;
		renderer.uvRotateSouth = 1;
		renderer.uvRotateWest = 3;
		renderer.uvRotateBottom = 1;
		
		uvFlipBottom = true;
		uvFlipSouth = true;
		
		renderInInventory(tessellator, renderer, block, metadata);
		renderer.uvRotateTop = 0;
		renderer.uvRotateNorth = 0;
		//renderer.uvRotateEast = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateBottom = 0;
		
		uvFlipBottom = false;
		uvFlipSouth = false;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		int orient = world.getBlockMetadata(x, y, z); // Direction block is facing: 0=N, 1=E, 2=S, 3=W

		renderer.uvRotateTop = orient > 1 ? 5 - orient : orient; // Rotate top side (NO NEED FOR MIRROR)
		renderer.uvRotateNorth = orient > 0 ? (orient % 3) + 1 : 0; // Rotate west-facing side (NO NEED FOR MIRROR)
		renderer.uvRotateEast = orient < 2 ? 1 - orient : orient; // Rotate north-facing side (N&S orientations are flipped)
		renderer.uvRotateSouth = orient > 1 ? 2 * orient - 4 : 3 - 2 * orient; // Rotate east-facing side (E&W orientations are flipped)
		renderer.uvRotateWest = orient > 1 ? 3 - orient : orient + 2; // Rotate south-facing side (NO NEED FOR MIRROR)
		renderer.uvRotateBottom = orient > 1 ? 2 * orient - 4 : 3 - 2 * orient; // (ALL orientations are wrong)
		
//        System.out.println("Meta: " + Integer.toString(orient) + " Rotation: " + Integer.toString(orient > 1 ? 2 * orient - 4 : 3 - 2 * orient));
		
		int l = block.colorMultiplier(world, x, y, z);
		float f = (l >> 16 & 255) / 255.0F;
		float f1 = (l >> 8 & 255) / 255.0F;
		float f2 = (l & 255) / 255.0F;

		if (EntityRenderer.anaglyphEnable)
		{
			float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
			float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
			float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
			f = f3;
			f1 = f4;
			f2 = f5;
		}
		
		uvFlipBottom = true;
		uvFlipEast = orient % 2 == 0;
		uvFlipSouth = orient % 2 == 1;

		boolean flag = Minecraft.isAmbientOcclusionEnabled() && block.getLightValue() == 0 ? (renderer.partialRenderBounds ? renderStandardBlockWithAmbientOcclusionPartial(renderer, block, x, y, z, f, f1, f2) : renderStandardBlockWithAmbientOcclusion(renderer, block, x, y, z, f, f1, f2)) : renderStandardBlockWithColorMultiplier(renderer, block, x, y, z, f, f1, f2);
		
		// Must reset the rotation or it will mess up all rotating blocks around
		renderer.uvRotateTop = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateBottom = 0;
		
		uvFlipTop = false;
		uvFlipBottom = false;
		uvFlipNorth = false;
		uvFlipSouth = false;
		uvFlipEast = false;
		uvFlipWest = false;
		
		return flag;
		
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return RenderIDs.GLAZED_TERRACOTTA;
	}

	// To render a ISBRH part in the inventory - Credits to MinecraftForgeFrance
	private void renderInInventory(Tessellator tessellator, RenderBlocks renderer, Block block, int metadata)
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		//renderer.flipTexture=true;
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1F, 0.0F);
		renderFaceYNeg(renderer, block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderFaceYPos(renderer, block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1F);
		renderFaceZNeg(renderer, block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderFaceZPos(renderer, block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata)); //DO NOT SWITCH
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderFaceXNeg(renderer, block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderFaceXPos(renderer, block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata));
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
