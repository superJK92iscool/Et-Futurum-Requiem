package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class BlockRedNetherBrickSlab extends BasicSlab {

	public BlockRedNetherBrickSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "red_nether_bricks");
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("red_nether_brick_slab"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}
}
