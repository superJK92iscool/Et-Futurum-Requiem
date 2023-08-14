package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BaseStairs extends BlockStairs {

	protected final int meta;

	public BaseStairs(Block p_i45428_1_, int p_i45428_2_) {
		super(p_i45428_1_, p_i45428_2_);
		useNeighborBrightness = true;
		meta = p_i45428_2_;
		String name = p_i45428_1_.getUnlocalizedName().replace("tile.", "").replace("etfuturum.", "");
		if (name.toLowerCase().endsWith("bricks") || name.toLowerCase().endsWith("tiles")) {
			name = name.substring(0, name.length() - 1);
		}
		setUnlocalizedNameWithPrefix(name);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	public BaseStairs setUnlocalizedNameWithPrefix(String name) {
		setBlockName(Utils.getUnlocalisedName(name) + "_stairs");
		return this;
	}

	public BaseStairs setBlockSound(SoundType type) {
		Utils.setBlockSound(this, type);
		return this;
	}
}
