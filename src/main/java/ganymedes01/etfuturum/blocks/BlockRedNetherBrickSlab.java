package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockRedNetherBrickSlab extends BlockGenericSlab implements IConfigurable {

	public BlockRedNetherBrickSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "");
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("red_netherbrick_slab"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab)ModBlocks.red_nether_brick_slab, (BlockGenericSlab)ModBlocks.double_red_nether_brick_slab};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {ModBlocks.new_nether_brick.getIcon(side, 0)};
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableNewNetherBricks;
	}

}
