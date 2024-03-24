package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public interface IEmissiveLayerBlock {
	@SideOnly(Side.CLIENT)
	IIcon getEmissiveLayerIcon(int side, int meta);

	default int getEmissiveMinBrightness(IBlockAccess world, int x, int y, int z) {
		return getEmissiveMinBrightness(world.getBlockMetadata(x, y, z));
	}

	/**
	 * For the ItemBlock
	 */
	int getEmissiveMinBrightness(int meta);

	/**
	 * Used by Thaumcraft ores so the amber and cinnibar deepslate ores render normally.
	 *
	 * @param meta
	 * @return
	 */
	default boolean isMetaNormalBlock(int meta) {
		return false;
	}

	default boolean doesEmissiveLayerHaveDirShading(int meta) {
		return true;
	}

	default int getEmissiveLayerColor(int meta) {
		return 0xFFFFFF;
	}

	default boolean isEmissiveLayerAbove(int meta) {
		return false;
	}

	default boolean itemBlockGlows(int meta) {
		return false;
	}
}
