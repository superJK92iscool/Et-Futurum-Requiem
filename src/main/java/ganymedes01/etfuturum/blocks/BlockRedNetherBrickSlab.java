package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockRedNetherBrickSlab extends BasicVariantsSlab {

	public BlockRedNetherBrickSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "red_nether_brick_slab");
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("red_nether_brick_slab"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public BasicVariantsSlab[] getSlabTypes() {
		return new BasicVariantsSlab[]{(BasicVariantsSlab) ModBlocks.RED_NETHERBRICK_SLAB.get(), (BasicVariantsSlab) ModBlocks.DOUBLE_RED_NETHERBRICK_SLAB.get()};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {ModBlocks.RED_NETHERBRICK.get().getIcon(side, 0)};
	}

}
