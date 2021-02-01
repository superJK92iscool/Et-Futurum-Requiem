package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;

public class BlockGenericStairs extends BlockStairs implements IConfigurable {

	private final boolean enabled;
	protected final int meta;
	
	public BlockGenericStairs(Block p_i45428_1_, int p_i45428_2_, boolean isEnabled) {
		super(p_i45428_1_, p_i45428_2_);
		enabled = isEnabled;
        useNeighborBrightness = true;
        meta = p_i45428_2_;
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}

}
