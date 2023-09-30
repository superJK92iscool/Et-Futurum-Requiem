package ganymedes01.etfuturum.client.renderer.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.blocks.BlockAzalea;
import ganymedes01.etfuturum.blocks.BlockNetherRoots;
import ganymedes01.etfuturum.blocks.BlockNetherSprouts;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderBlockAzaleaBush extends BlockModelBase {

	public RenderBlockAzaleaBush(int modelID) {
		super(modelID);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		IIcon sideIcon = ((BlockAzalea) block).sideIcon;
		IIcon topIcon = ((BlockAzalea) block).topIcon;

		// Prepare the tessellator
		tessellator.startDrawingQuads();

		// Render the sides and top with the specified icons
		renderer.setRenderBoundsFromBlock(block);
		renderer.setOverrideBlockTexture(sideIcon); // Set the side icon
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, sideIcon);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, sideIcon);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, sideIcon);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, sideIcon);
		renderer.clearOverrideBlockTexture(); // Clear the override texture

		renderer.setOverrideBlockTexture(topIcon); // Set the top icon
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, topIcon);
		renderer.clearOverrideBlockTexture(); // Clear the override texture

		// Finish tessellating
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		IIcon sideIcon = ((BlockAzalea) block).sideIcon;
		IIcon topIcon = ((BlockAzalea) block).topIcon;

		// Render the sides and top with the specified icons
		renderer.setRenderBoundsFromBlock(block);
		renderer.setOverrideBlockTexture(sideIcon); // Set the side icon
		renderer.renderFaceXPos(block, x, y, z, sideIcon);
		renderer.renderFaceXNeg(block, x, y, z, sideIcon);
		renderer.renderFaceZPos(block, x, y, z, sideIcon);
		renderer.renderFaceZNeg(block, x, y, z, sideIcon);

		renderer.setOverrideBlockTexture(topIcon); // Set the top icon
		renderer.renderFaceYPos(block, x, y, z, topIcon);

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

}