package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;

public class BlockCornflower extends BlockFlowerBase implements IConfigurable {

	public BlockCornflower() {
        setBlockName(Utils.getUnlocalisedName("cornflower"));
        setStepSound(soundTypeGrass);
        setBlockTextureName("cornflower");
        setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableCornflower;
	}
}
