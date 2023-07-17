package ganymedes01.etfuturum.client.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

/**
 * @author TheMasterCaver
 * 3D Inventory model for beds
 */
public class BlockBed3DInventoryModelRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
//		boolean useInventoryTint = renderer.useInventoryTint;
		tessellator.startDrawingQuads();
		meta = 10;   // head facing north, foot is to the south
		float xOffset;
		float yOffset;
		float zOffset;
		float scale;

//		if (CustomItemRenderer.rotateItem)
//		{
//			// Rendered in item frame
//			GL11.glRotatef(270.0F, 0.0F, 0.0F, 1.0F);
//			xOffset = -0.35F;
//			yOffset = -0.1F;
//			zOffset = -0.72F;
//			scale = 0.7F;
//		}
//		else if (CustomItemRenderer.heldInFirstPerson)
//		{
//			// Rendered in first person
//			xOffset = -0.5F;
//			yOffset = 0.0F;
//			zOffset = -0.5F;
//			scale = 0.7F;
//		}
//		else if (CustomItemRenderer.heldByEntity)
//		{
//			// Rendered in 3rd person
//			xOffset = -0.25F;
//			yOffset = -0.15F;
//			zOffset = -0.6F;
//			scale = 0.7F;
//		}
//		else if (CustomItemRenderer.droppedItem)
//		{
//			// Rendered as a dropped item
//			xOffset = -0.35F;
//			yOffset = -0.5F;
//			zOffset = -0.7F;
//			scale = 0.7F;
//		}
//		else
		{
			// Rendered in GUI
			GL11.glRotatef(20.0F, 0.0F, 1.0F, 0.0F);
			xOffset = -0.02F;
			yOffset = 0.24F;
			zOffset = 0.05F;
			scale = 0.8F;
		}

		float minX = xOffset;
		float minZ = zOffset;
		float maxX = minX + scale;
		float maxZ = minZ + scale;

		// Renders head first, then foot
		for (int i = 0; i < 2; ++i)
		{
			float minY = yOffset + 0.1875F * scale;
			float maxY = yOffset + 0.5625F * scale;
			IIcon icon = block.getIcon(0, meta);
			float minU = icon.getMinU();
			float maxU = icon.getMaxU();
			float minV = icon.getMinV();
			float maxV = icon.getMaxV();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			tessellator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
			tessellator.addVertexWithUV(minX, minY, minZ, minU, minV);
			tessellator.addVertexWithUV(maxX, minY, minZ, maxU, minV);
			tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
			icon = block.getIcon(1, meta);
			minU = icon.getMinU();
			maxU = icon.getMaxU();
			minV = icon.getMinV();
			maxV = icon.getMaxV();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
			tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
			tessellator.addVertexWithUV(minX, maxY, minZ, maxU, maxV);
			tessellator.addVertexWithUV(minX, maxY, maxZ, minU, maxV);
			int var49 = (i == 0 ? Direction.directionToFacing[Direction.rotateOpposite[2]] : Direction.directionToFacing[2]);
			minY = yOffset;
			maxY = yOffset + scale;

			if (var49 != 2)
			{
				icon = block.getIcon(2, meta);
				minU = icon.getMinU();
				maxU = icon.getMaxU();
				minV = icon.getMinV();
				maxV = icon.getMaxV();
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				tessellator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
				tessellator.addVertexWithUV(maxX, maxY, minZ, minU, minV);
				tessellator.addVertexWithUV(maxX, minY, minZ, minU, maxV);
				tessellator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
			}

			if (var49 != 3)
			{
				icon = block.getIcon(3, meta);
				minU = icon.getMinU();
				maxU = icon.getMaxU();
				minV = icon.getMinV();
				maxV = icon.getMaxV();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				tessellator.addVertexWithUV(minX, maxY, maxZ, minU, minV);
				tessellator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
				tessellator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
				tessellator.addVertexWithUV(maxX, maxY, maxZ, maxU, minV);
			}

			if (var49 != 4)
			{
				icon = block.getIcon(4, meta);
				minU = icon.getMaxU();
				maxU = icon.getMinU();
				minV = icon.getMinV();
				maxV = icon.getMaxV();
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
				tessellator.addVertexWithUV(minX, maxY, minZ, minU, minV);
				tessellator.addVertexWithUV(minX, minY, minZ, minU, maxV);
				tessellator.addVertexWithUV(minX, minY, maxZ, maxU, maxV);
			}

			if (var49 != 5)
			{
				icon = block.getIcon(5, meta);
				minU = icon.getMinU();
				maxU = icon.getMaxU();
				minV = icon.getMinV();
				maxV = icon.getMaxV();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
				tessellator.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
				tessellator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
				tessellator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
			}

			meta -= 8;
			minZ += scale;
			maxZ += scale;
		}

		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
			RenderBlocks renderer) {
		return renderer.renderBlockBed(block, x, y, z);
		}
	

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIDs.BED_INVENTORY_MODEL;
	}
}
