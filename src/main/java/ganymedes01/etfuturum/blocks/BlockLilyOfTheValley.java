package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;

public class BlockLilyOfTheValley extends BlockFlowerBase implements IConfigurable {

	public BlockLilyOfTheValley() {
		setBlockName(Utils.getUnlocalisedName("lily_of_the_valley"));
		setStepSound(soundTypeGrass);
		setBlockTextureName("lily_of_the_valley");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableLilyOfTheValley;
	}
}
