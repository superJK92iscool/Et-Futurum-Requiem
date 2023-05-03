package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSmoothQuartz extends Block implements IConfigurable {

	public BlockSmoothQuartz() {
		super(Material.rock);
		this.setHardness(2F);
		this.setResistance(6F);
		this.setStepSound(soundTypePiston);
		this.setBlockName(Utils.getUnlocalisedName("smooth_quartz"));
		setBlockTextureName("quartz_block_bottom");
		this.setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableSmoothQuartz;
	}

}
