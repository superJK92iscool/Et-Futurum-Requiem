package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BasicStairs extends BlockStairs {

	protected final int meta;

	public BasicStairs(Block p_i45428_1_, int p_i45428_2_) {
		super(p_i45428_1_, p_i45428_2_);
		useNeighborBrightness = true;
		meta = p_i45428_2_;
		setCreativeTab(EtFuturum.creativeTabBlocks);
		String name = p_i45428_1_.getUnlocalizedName().replace("tile.", "").replace("etfuturum.", "");
		if (name.toLowerCase().endsWith("bricks") || name.toLowerCase().endsWith("tiles")) {
			name = name.substring(0, name.length() - 1);
		}
		setBlockName(name);
	}

	@Override
	public Block setBlockName(String p_149663_1_) {
		return super.setBlockName(Utils.getUnlocalisedName(p_149663_1_) + "_stairs");
	}
}
