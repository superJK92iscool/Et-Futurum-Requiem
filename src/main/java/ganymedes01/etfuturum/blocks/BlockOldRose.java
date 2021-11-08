package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.Reference;

public class BlockOldRose extends BlockFlowerBase implements IConfigurable {

	public BlockOldRose() {
		setBlockName(Utils.getUnlocalisedName("rose"));
		setBlockTextureName(Reference.MOD_ID + ":flower_rose");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigTweaks.enableRoses;
	}

}