package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class BlockRawOre extends BlockGeneric {

	public BlockRawOre() {
		super(Material.rock, "copper_block", "iron_block", "gold_block");
		setStepSound(soundTypePiston);
		setHarvestLevel("pickaxe", 1, 0);
		setHarvestLevel("pickaxe", 1, 1);
		setHarvestLevel("pickaxe", 2, 2);
		setHardness(5);
		setResistance(6);
		setBlockName(Utils.getUnlocalisedName("raw_ore_block"));
		setBlockTextureName("raw");
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	public String getNameFor(int meta) {
		return types[Math.max(Math.min(meta, types.length - 1), 0)].replace("_ore_block", "");
	}

}
