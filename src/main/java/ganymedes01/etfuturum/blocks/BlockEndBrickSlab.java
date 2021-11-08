package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockEndBrickSlab extends BlockGenericSlab implements IConfigurable {

	public BlockEndBrickSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "");
		setResistance(9);
		setHardness(3.0F);
		setBlockName(Utils.getUnlocalisedName("end_brick_slab"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab)ModBlocks.end_brick_slab, (BlockGenericSlab)ModBlocks.double_end_brick_slab};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {ModBlocks.end_bricks.getIcon(side, 0)};
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableChorusFruit;
	}

}
