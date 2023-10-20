package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public interface IEmissiveLayerBlock {
	@SideOnly(Side.CLIENT)
	IIcon getEmissiveLayerIcon(int side, int meta);

	int getEmissiveMinBrightness(IBlockAccess world, int x, int y, int z);

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
}
