package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockObsidian;

public class BlockCryingObsidian extends BlockObsidian implements IConfigurable {

	public BlockCryingObsidian() {
		setHardness(50.0F);
		setResistance(2000.0F);
		setStepSound(soundTypePiston);
		setHarvestLevel("pickaxe", 3);
		setBlockTextureName("crying_obsidian");
		setBlockName(Utils.getUnlocalisedName("crying_obsidian"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableCryingObsidian;
	}
}