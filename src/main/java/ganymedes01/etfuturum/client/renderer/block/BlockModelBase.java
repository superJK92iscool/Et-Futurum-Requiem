package ganymedes01.etfuturum.client.renderer.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import ganymedes01.etfuturum.client.OpenGLHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public abstract class BlockModelBase implements ISimpleBlockRenderingHandler {

	protected static final Tessellator tessellator = Tessellator.instance;
	private final int modelID;
	private boolean inventory3D = true;

	public BlockModelBase(int modelID) {
		this.modelID = modelID;
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		if (block.getRenderType() == 2) {
			OpenGLHelper.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			OpenGLHelper.enableBlend();
		}
		OpenGLHelper.translate(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		renderInventoryModel(block, meta, modelID, renderer, block.getBlockBoundsMinX(), block.getBlockBoundsMinY(), block.getBlockBoundsMinZ(), block.getBlockBoundsMaxX(), block.getBlockBoundsMaxY(), block.getBlockBoundsMaxZ());
		tessellator.draw();

		OpenGLHelper.translate(0.5F, 0.5F, 0.5F);
		OpenGLHelper.disableBlend();
	}

	protected void renderInventoryModel(Block block, int meta, int modelId, RenderBlocks renderer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		renderStandardInventoryCube(block, meta, modelID, renderer, block.getBlockBoundsMinX(), block.getBlockBoundsMinY(), block.getBlockBoundsMinZ(), block.getBlockBoundsMaxX(), block.getBlockBoundsMaxY(), block.getBlockBoundsMaxZ());
	}

	protected void renderStandardInventoryCube(Block block, int meta, int modelId, RenderBlocks renderer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
		renderer.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return renderStandardWorldCube(world, x, y, z, block, modelId, renderer, block.getBlockBoundsMinX(), block.getBlockBoundsMinY(), block.getBlockBoundsMinZ(), block.getBlockBoundsMaxX(), block.getBlockBoundsMaxY(), block.getBlockBoundsMaxZ());
	}

	protected boolean renderStandardWorldCube(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer, double minX, double minY, double minZ, double maxF, double maxY, double maxZ) {
		renderer.setRenderBounds(minX, minY, minZ, maxF, maxY, maxZ);
		return renderer.renderStandardBlock(block, x, y, z);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return inventory3D;
	}

	@Override
	public int getRenderId() {
		return modelID;
	}

	public BlockModelBase set2DInventory() {
		inventory3D = false;
		return this;
	}

	public void renderRawFace(double x, double y, double z, double startX, double endX, double startY, double endY, double startZ, double endZ, double startU, double startV, double endU, double endV, IIcon iicon) {
		double startUInterpolated = iicon.getInterpolatedU(startU);
		double startVInterpolated = iicon.getInterpolatedV(startV);
		double endUInterpolated = iicon.getInterpolatedU(endU);
		double endVInterpolated = iicon.getInterpolatedV(endV);

		tessellator.addVertexWithUV(x + startX, y + startY, z + startZ, startUInterpolated, endVInterpolated);
		tessellator.addVertexWithUV(x + startX, y + endY, z + startZ, startUInterpolated, startVInterpolated);
		tessellator.addVertexWithUV(x + endX, y + endY, z + endZ, endUInterpolated, startVInterpolated);
		tessellator.addVertexWithUV(x + endX, y + startY, z + endZ, endUInterpolated, endVInterpolated);
	}

	public void renderRawDoubleSidedFace(double x, double y, double z, double startX, double endX, double startY, double endY, double startZ, double endZ, double startU, double startV, double endU, double endV, IIcon iicon) {
		renderRawFace(x, y, z, startX, endX, startY, endY, startZ, endZ, startU, startV, endU, endV, iicon);
		renderRawFace(x, y, z, endX, startX, startY, endY, endZ, startZ, startU, startV, endU, endV, iicon);
	}

	/**
	 * Renders the YNeg face with proper shading like renderStandardBlock.
	 */
	public void renderFaceYNeg(RenderBlocks renderer, Block block, double dx, double dy, double dz) {
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
		}

		float f3 = 0.5F;
		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);
		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y - 1, z, 0)) {
			tessellator.setBrightness(renderer.renderMinY + Math.abs(offy) > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, MathHelper.floor_double(y - 1), z));
			tessellator.setColorOpaque_F(f3, f3, f3);
			renderer.renderFaceYNeg(block, dx + offx, dy + offy, dz + offz, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 0));
		}
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
		}

		float f5 = 0.8F;
		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z - 1, 2)) {
			tessellator.setBrightness(renderer.renderMinZ + Math.abs(offz) > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z - 1));
			tessellator.setColorOpaque_F(f5, f5, f5);
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
		}

		float f5 = 0.8F;
		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x, y, z + 1, 3)) {
			tessellator.setBrightness(renderer.renderMaxZ - Math.abs(offz) < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z + 1));
			tessellator.setColorOpaque_F(f5, f5, f5);
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
		}

		float f6 = 0.6F;

		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x - 1, y, z, 4)) {
			tessellator.setBrightness(renderer.renderMinX + Math.abs(offx) > 0.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x - 1, y, z));
			tessellator.setColorOpaque_F(f6, f6, f6);
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
		}

		float f6 = 0.6F;

		int l = block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z);

		if (renderer.renderAllFaces || block.shouldSideBeRendered(renderer.blockAccess, x + 1, y, z, 5)) {
			tessellator.setBrightness(renderer.renderMaxX - Math.abs(offx) < 1.0D ? l : block.getMixedBrightnessForBlock(renderer.blockAccess, x + 1, y, z));
			tessellator.setColorOpaque_F(f6, f6, f6);
			renderer.renderFaceXPos(block, dx + offx, dy + offy, dz + offz, renderer.getBlockIcon(block, renderer.blockAccess, x, y, z, 5));
		}
	}
}
