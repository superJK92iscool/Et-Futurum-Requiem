package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import net.minecraft.block.material.Material;

public class BlockRawOre extends BasicSubtypesBlock {

	public BlockRawOre() {
		super(Material.rock, "raw_copper_block", "raw_iron_block", "raw_gold_block");
		setNames("raw_ore_block");
		setHarvestLevel("pickaxe", 1, 0);
		setHarvestLevel("pickaxe", 1, 1);
		setHarvestLevel("pickaxe", 2, 2);
		setHardness(5);
		setResistance(6);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}
}
