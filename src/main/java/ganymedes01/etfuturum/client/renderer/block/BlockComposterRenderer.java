package ganymedes01.etfuturum.client.renderer.block;

import org.lwjgl.opengl.GL11;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockComposter;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockComposterRenderer extends BlockModelBase {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		renderInInventory(Tessellator.instance, renderer, block, metadata);
	}
	// To render a ISBRH part in the inventory - Credits to MinecraftForgeFrance
	private void renderInInventory(Tessellator tessellator, RenderBlocks renderer, Block block, int metadata)
	{
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		tessellator.startDrawingQuads();
		renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

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

		renderer.setRenderBounds(0.875D, 0.125D, 0.125D, 0.125D, 1, 0.875D);
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		
		renderer.setRenderBounds(0, 0, 0, 1, 0.125D, 1);
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0, 0, 0, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		
		tessellator.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		renderer.renderStandardBlock(block, x, y, z);

		if(block.shouldSideBeRendered(renderer.blockAccess, x, y + 1, z, 1)) {
			boolean prevRenderFaces = renderer.renderAllFaces;
			renderer.renderAllFaces = true;
			renderer.setRenderBounds(0.875D, 0.125D, 0.125D, 0.125D, 1, 0.875D);
			this.renderFaceZNeg(renderer, block, x, y, z);
			this.renderFaceXPos(renderer, block, x, y, z);
			this.renderFaceZPos(renderer, block, x, y, z);
			this.renderFaceXNeg(renderer, block, x, y, z);
			renderer.renderAllFaces = prevRenderFaces;
			
			int meta = Math.min(world.getBlockMetadata(x, y, z), 9);
			if(!renderer.hasOverrideBlockTexture()) {
				if(meta == 0) {
					renderer.setOverrideBlockTexture(ModBlocks.composter.getIcon(0, 0));
				} else if (meta == 7){
					renderer.setOverrideBlockTexture(((BlockComposter)block).fullCompostIcon);
				} else {
					renderer.setOverrideBlockTexture(((BlockComposter)block).compostIcon);
				}
				
				double offset = 0.125D * Math.min(meta + 1, 7);
				if(meta > 0) {
					offset += 0.0625D;
				}
				
				renderer.setRenderBounds(0, 0, 0, 1, offset, 1);
				this.renderFaceYPos(renderer, block, x, y, z);
			}
			
			renderer.clearOverrideBlockTexture();
		}
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.COMPOSTER;
	}

}
