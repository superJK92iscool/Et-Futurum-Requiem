package ganymedes01.etfuturum.client.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ganymedes01.etfuturum.client.OpenGLHelper;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class BlockObserverRenderer implements ISimpleBlockRenderingHandler {

	private void setRotation(int meta, RenderBlocks renderer) {
		int orientation = BlockPistonBase.getPistonOrientation(meta);
		switch (orientation)
		{
			case 2:
				renderer.uvRotateTop = 3;
				renderer.uvRotateBottom = 3;
				break;
			case 5:
				renderer.uvRotateTop = 2;
				renderer.uvRotateBottom = 1;
				break;
			case 4:
				renderer.uvRotateTop = 1;
				renderer.uvRotateBottom = 2;
				break;
			case 1:
				renderer.uvRotateNorth = 1;
				renderer.uvRotateSouth = 2;
				renderer.uvRotateWest = 3;
				renderer.uvRotateEast = 3;
				break;
			case 0:
				renderer.uvRotateNorth = 1;
				renderer.uvRotateSouth = 2;
				break;
		}
	}

	private void resetRotation(RenderBlocks renderer) {
		renderer.flipTexture=false;
		renderer.uvRotateNorth=0; //west
		renderer.uvRotateSouth=0; //east
		renderer.uvRotateWest=0; //south
		renderer.uvRotateEast=0; //north
		renderer.uvRotateTop=0;
		renderer.uvRotateBottom=0;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);

		int metadata = BlockPistonBase.getPistonOrientation(meta);
		switch(metadata) {
			case 0: metadata = 5;
				break;
			case 5: metadata = 0;
				break;

			default: metadata = meta;
				break;
		}
		setRotation(metadata, renderer);
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		tessellator.startDrawingQuads();
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
		tessellator.draw();
		resetRotation(renderer);

		OpenGLHelper.translate(0.5F, 0.5F, 0.5F);
		OpenGLHelper.disableBlend();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		int meta = world.getBlockMetadata(x, y, z);
		setRotation(meta, renderer);
		boolean flag = renderer.renderStandardBlock(block, x, y, z);
		resetRotation(renderer);
		return flag;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.OBSERVER;
	}
}
