package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;

public class BlockCornflower extends BlockFlowerBase {

	public BlockCornflower() {
		setBlockName(Utils.getUnlocalisedName("cornflower"));
		setStepSound(soundTypeGrass);
		setBlockTextureName("cornflower");
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}
}
