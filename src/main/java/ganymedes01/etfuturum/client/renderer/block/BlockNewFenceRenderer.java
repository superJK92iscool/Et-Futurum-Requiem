package ganymedes01.etfuturum.client.renderer.block;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;
import ganymedes01.etfuturum.blocks.BlockModernWoodFence;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

@ThreadSafeISBRH(perThread = false)
public class BlockNewFenceRenderer extends BlockModelBase {

	public BlockNewFenceRenderer() {
		super(RenderIDs.FENCE);
	}

	@Override
	protected void renderInventoryModel(Block block, int meta, int modelId, RenderBlocks renderer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		final Tessellator tessellator = Tessellator.instance;
		for (int k = 0; k < 4; ++k) {
			float f2 = 0.125F;

			if (k == 0) {
				renderer.setRenderBounds(0.5F - f2, 0.0D, 0.0D, 0.5F + f2, 1.0D, f2 * 2.0F);
			}

			if (k == 1) {
				renderer.setRenderBounds(0.5F - f2, 0.0D, 1.0F - f2 * 2.0F, 0.5F + f2, 1.0D, 1.0D);
			}

			f2 = 0.0625F;

			if (k == 2) {
				renderer.setRenderBounds(0.5F - f2, 1.0F - f2 * 3.0F, -f2 * 2.0F, 0.5F + f2, 1.0F - f2, 1.0F + f2 * 2.0F);
			}

			if (k == 3) {
				renderer.setRenderBounds(0.5F - f2, 0.5F - f2 * 3.0F, -f2 * 2.0F, 0.5F + f2, 0.5F - f2, 1.0F + f2 * 2.0F);
			}

			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));

			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));

			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));

			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));

			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));

			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		}

		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if(block instanceof BlockModernWoodFence && world.getBlockMetadata(x, y, z) == 4){
			return renderBambooFence(renderer, (BlockModernWoodFence) block, x, y, z);
		}

		return renderer.renderBlockFence((BlockFence) block, x, y, z);
	}

	// TODO: Make Bamboo Fence Rendering Better
	// This version is currently more accurate than the regular bamboo planks fence variation
	// But still has a slight flaw on the top edges of the double lane posts peaking out from
	// a fence
	public boolean renderBambooFence(RenderBlocks renderer, BlockModernWoodFence blockFence, int posX, int posY, int posZ)
	{
		boolean flag = false;
		float f = 0.375F;
		float f1 = 0.625F;
		renderer.setRenderBounds((double)f, 0.0D, (double)f, (double)f1, 1.0D, (double)f1);
		renderer.renderStandardBlock(blockFence, posX, posY, posZ);
		flag = true;
		boolean flag1 = false;
		boolean flag2 = false;

		if (blockFence.canConnectFenceTo(renderer.blockAccess, posX - 1, posY, posZ) || blockFence.canConnectFenceTo(renderer.blockAccess, posX + 1, posY, posZ))
		{
			flag1 = true;
		}

		if (blockFence.canConnectFenceTo(renderer.blockAccess, posX, posY, posZ - 1) || blockFence.canConnectFenceTo(renderer.blockAccess, posX, posY, posZ + 1))
		{
			flag2 = true;
		}

		boolean flag3 = blockFence.canConnectFenceTo(renderer.blockAccess, posX - 1, posY, posZ);
		boolean flag4 = blockFence.canConnectFenceTo(renderer.blockAccess, posX + 1, posY, posZ);
		boolean flag5 = blockFence.canConnectFenceTo(renderer.blockAccess, posX, posY, posZ - 1);
		boolean flag6 = blockFence.canConnectFenceTo(renderer.blockAccess, posX, posY, posZ + 1);

		if (!flag1 && !flag2)
		{
			flag1 = true;
		}

		f = 0.4375F;
		f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = flag3 ? 0.0F : f;
		float f5 = flag4 ? 1.0F : f1;
		float f6 = flag5 ? 0.0F : f;
		float f7 = flag6 ? 1.0F : f1;
		renderer.field_152631_f = true;
		BlockModernWoodFence.renderCustom = true;

		if (flag1)
		{
			// Top Row
			renderer.setRenderBounds((double)f4, (double)f2, (double)f, (double)f5, (double)f3, (double)f1);
			renderer.renderStandardBlock(blockFence, posX, posY, posZ);
			flag = true;
		}

		if (flag2)
		{
			// Top Row - NS
			renderer.setRenderBounds((double)f, (double)f2, (double)f6, (double)f1, (double)f3, (double)f7);
			renderer.renderStandardBlock(blockFence, posX, posY, posZ);
			flag = true;
		}

		f2 = 0.375F;
		f3 = 0.5625F;

		if (flag1)
		{
			// Bottom Row
			renderer.setRenderBounds((double)f4, (double)f2, (double)f, (double)f5, (double)f3, (double)f1);
			renderer.renderStandardBlock(blockFence, posX, posY, posZ);
			flag = true;
		}

		if (flag2)
		{
			// Bottom Row - NS
			renderer.setRenderBounds((double)f, (double)f2, (double)f6, (double)f1, (double)f3, (double)f7);
			renderer.renderStandardBlock(blockFence, posX, posY, posZ);
			flag = true;
		}
		BlockModernWoodFence.renderCustom = false;

		renderer.field_152631_f = false;
		blockFence.setBlockBoundsBasedOnState(renderer.blockAccess, posX, posY, posZ);
		return flag;
	}
}
