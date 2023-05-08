package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSmoothSandstone extends Block {

	private final int meta;
	public BlockSmoothSandstone(int i) {
		super(Material.rock);
		meta = i;
		setHardness(2F);
		setResistance(6F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("smooth_" + (i == 1 ? "red_" : "") + "sandstone"));
		setBlockTextureName((i == 1 ? "red_" : "") + "sandstone_top");
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

}
