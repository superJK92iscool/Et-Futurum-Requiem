package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockStoneSlab1 extends BasicVariantsSlab {

	public BlockStoneSlab1(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "stone", "mossy_cobblestone", "mossy_stone_brick", "cut_sandstone");
		setHardness(2F);
		setResistance(6F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("stone_slab"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public BasicVariantsSlab[] getSlabTypes() {
		return new BasicVariantsSlab[]{(BasicVariantsSlab) ModBlocks.DOUBLE_STONE_SLAB.get(), (BasicVariantsSlab) ModBlocks.DOUBLE_STONE_SLAB.get()};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {Blocks.stone.getIcon(side, 0),Blocks.mossy_cobblestone.getIcon(side, 0),
				Blocks.stonebrick.getIcon(side, 1), Blocks.sandstone.getIcon(side, 2)};
	}
	
}
