package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class BlockEndBrickSlab extends BasicSlab {

	public BlockEndBrickSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "end_bricks");
		setResistance(9);
		setHardness(3.0F);
		setBlockName(Utils.getUnlocalisedName("end_brick_slab"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

}
