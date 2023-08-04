package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class BlockPrismarineSlab extends BasicSlab {

	public BlockPrismarineSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "prismarine", "prismarine_bricks", "dark_prismarine");
		setHardness(1.5F);
		setResistance(6.0F);
		setBlockName(Utils.getUnlocalisedName("prismarine_slab"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}
}