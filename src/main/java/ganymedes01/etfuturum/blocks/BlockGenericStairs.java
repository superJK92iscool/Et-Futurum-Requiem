package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockGenericStairs extends BlockStairs implements IConfigurable {
	
	protected final int meta;
	
	public BlockGenericStairs(Block p_i45428_1_, int p_i45428_2_) {
		super(p_i45428_1_, p_i45428_2_);
		useNeighborBrightness = true;
		meta = p_i45428_2_;
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@Override
	public boolean isEnabled() {
		return field_150149_b instanceof IConfigurable ? ((IConfigurable)field_150149_b).isEnabled() : ConfigBlocksItems.enableGenericStairs;
	}

}
