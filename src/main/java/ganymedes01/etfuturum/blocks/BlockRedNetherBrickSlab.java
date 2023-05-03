package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
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
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab) ModBlocks.RED_NETHERBRICK_SLAB.get(), (BlockGenericSlab) ModBlocks.DOUBLE_RED_NETHERBRICK_SLAB.get()};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {ModBlocks.RED_NETHERBRICK.get().getIcon(side, 0)};
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableNewNetherBricks;
	}

}
