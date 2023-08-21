package ganymedes01.etfuturum.client.renderer.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

@SideOnly(Side.CLIENT)
public class BlockDoubleLayerRenderer extends BlockModelBase {

	private final float innerSizeMax;
	private final float innerSizeMin;

	public BlockDoubleLayerRenderer(int secondSize, int modelID) {
		super(modelID);
		innerSizeMax = secondSize * 0.0625F;
		innerSizeMin = (16 - secondSize) * 0.0625F;
	}

	protected void renderStandardInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer, double minX, double minY, double minZ, double maxF, double maxY, double maxZ) {
		super.renderStandardInventoryBlock(block, meta, modelID, renderer, innerSizeMin, innerSizeMin, innerSizeMin, innerSizeMax, innerSizeMax, innerSizeMax);
		super.renderStandardInventoryBlock(block, meta, modelID, renderer, minX, minY, minZ, maxF, maxY, maxZ);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		renderer.renderStandardBlock(block, x, y, z);
		renderer.setRenderBounds(innerSizeMin, innerSizeMin, innerSizeMin, innerSizeMax, innerSizeMax, innerSizeMax);
		renderer.renderAllFaces = true;
		boolean flag = renderer.renderStandardBlock(block, x, y, z);
		renderer.renderAllFaces = false;
		return flag;
	}
}