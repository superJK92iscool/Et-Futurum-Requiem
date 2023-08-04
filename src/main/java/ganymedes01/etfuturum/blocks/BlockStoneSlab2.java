package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class BlockStoneSlab2 extends BasicSlab {

	public BlockStoneSlab2(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "granite", "polished_granite", "diorite", "polished_diorite", "andesite", "polished_andesite");
		setHardness(2F);
		setResistance(6F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("stone_slab_2"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}
}
