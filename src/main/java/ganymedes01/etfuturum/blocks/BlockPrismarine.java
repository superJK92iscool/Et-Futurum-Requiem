package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class BlockPrismarine extends BasicSubtypesBlock {

	public BlockPrismarine() {
		super(Material.rock, "prismarine", "prismarine_bricks", "dark_prismarine");
		setHardness(1.5F);
		setResistance(10.0F);
		setBlockTextureName("prismarine");
		setBlockName(Utils.getUnlocalisedName("prismarine_block"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}
}