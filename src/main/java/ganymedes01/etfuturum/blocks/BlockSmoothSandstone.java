package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSmoothSandstone extends Block implements IConfigurable {

	private final int meta;
	public BlockSmoothSandstone(int i) {
		super(Material.rock);
		meta = i;
		setHardness(2F);
		setResistance(6F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("smooth_" + (i == 1 ? "red_" : "") + "sandstone"));
		setBlockTextureName((i == 1 ? "red_" : "") + "sandstone_top");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return (ConfigBlocksItems.enableSmoothSandstone && meta == 0) || (ConfigBlocksItems.enableSmoothSandstone && ConfigBlocksItems.enableRedSandstone && meta == 1);
	}

}
