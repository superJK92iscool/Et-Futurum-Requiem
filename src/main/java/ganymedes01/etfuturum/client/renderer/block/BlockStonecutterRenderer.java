package ganymedes01.etfuturum.client.renderer.block;

import org.lwjgl.opengl.GL11;

import ganymedes01.etfuturum.blocks.BlockStonecutter;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class BlockStonecutterRenderer extends BlockModelBase {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;
		renderInInventory(tessellator, renderer, block, metadata);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return RenderIDs.STONECUTTER;
	}

	// To render a ISBRH part in the inventory - Credits to MinecraftForgeFrance
	private void renderInInventory(Tessellator tessellator, RenderBlocks renderer, Block block, int metadata)
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, .5625F, 1.0F);

		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		
		renderer.setRenderBounds(0.0F, 0, 0.5F, 1.0F, 0.4375F, 0.5F);
		renderer.renderFaceZNeg(block, 0, .5625D, 0, ((BlockStonecutter)block).sawIcon);
		renderer.renderFaceZPos(block, 0, .5625D, 0, ((BlockStonecutter)block).sawIcon);
		
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		int metadata = world.getBlockMetadata(x, y, z) % 4;

		switch (metadata)
		{
			case 1:
				renderer.uvRotateTop = 3;
				break;
			case 2:
				renderer.uvRotateTop = 2;
				break;
			case 3:
				renderer.uvRotateTop = 1;
				break;
			case 0:
			default:
		}
		
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, .5625F, 1.0F);
		renderer.renderStandardBlock(block, x, y, z);

		if(!renderer.hasOverrideBlockTexture())
		renderer.setOverrideBlockTexture(((BlockStonecutter)block).sawIcon);
		renderer.setRenderAllFaces(true);
		
		if(metadata < 2) {
			renderer.setRenderBounds(0F, 0.0F, 0.5F, 1F, 0.4375F, 0.5F);
//          renderer.renderStandardBlock(block, x, y + 1, z);
			if(metadata == 1) {
				renderer.flipTexture = true;
			}
			renderFaceZNeg(renderer, block, x, y, z, 0, .5625D, 0);
			if(metadata == 0) {
				renderer.flipTexture = true;
			} else {
				renderer.flipTexture = false;
			}
			renderFaceZPos(renderer, block, x, y, z, 0, .5625D, 0);
		} else {
			renderer.setRenderBounds(0.5F, 0.0F, 0F, 0.5F, 0.4375F, 1F);
			if(metadata == 3) {
				renderer.flipTexture = true;
			}
			renderFaceXNeg(renderer, block, x, y, z, 0, .5625D, 0);
			if(metadata == 2) {
				renderer.flipTexture = true;
			} else {
				renderer.flipTexture = false;
			}
			renderFaceXPos(renderer, block, x, y, z, 0, .5625D, 0);
		}

		renderer.setRenderAllFaces(false);
		renderer.clearOverrideBlockTexture();
		renderer.flipTexture = false;
		renderer.uvRotateTop = 0;
		
		return true;
	}

}
