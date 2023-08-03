package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockSmoothQuartzSlab extends BasicVariantsSlab {

	public BlockSmoothQuartzSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "");
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("smooth_quartz_slab"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public BasicVariantsSlab[] getSlabTypes() {
		return new BasicVariantsSlab[]{(BasicVariantsSlab) ModBlocks.SMOOTH_QUARTZ_SLAB.get(), (BasicVariantsSlab) ModBlocks.DOUBLE_SMOOTH_QUARTZ_SLAB.get()};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {ModBlocks.SMOOTH_QUARTZ.get().getIcon(side, 0)};
	}

}
