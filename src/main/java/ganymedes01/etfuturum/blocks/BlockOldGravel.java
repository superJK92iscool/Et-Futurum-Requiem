package ganymedes01.etfuturum.blocks;

import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockGravel;
import net.minecraft.item.Item;

public class BlockOldGravel extends BlockGravel implements IConfigurable {

	public BlockOldGravel() {
		setHardness(0.6F);
		setStepSound(soundTypeGravel);
		setBlockTextureName("old_gravel");
		setBlockName(Utils.getUnlocalisedName("old_gravel"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public boolean isEnabled() {
		return ConfigTweaks.enableOldGravel;
	}
}