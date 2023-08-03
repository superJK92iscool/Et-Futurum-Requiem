package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class BlockRawOre extends BasicVariantsBlock {

	public BlockRawOre() {
		super(Material.rock, "raw_copper_block", "raw_iron_block", "raw_gold_block");
		setStepSound(soundTypePiston);
		setHarvestLevel("pickaxe", 1, 0);
		setHarvestLevel("pickaxe", 1, 1);
		setHarvestLevel("pickaxe", 2, 2);
		setHardness(5);
		setResistance(6);
		setBlockName(Utils.getUnlocalisedName("raw_ore_block"));
		setBlockTextureName("raw_ore_block");
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}
}
