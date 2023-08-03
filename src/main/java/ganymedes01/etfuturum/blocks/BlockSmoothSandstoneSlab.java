package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockSmoothSandstoneSlab extends BasicVariantsSlab {

	private final int meta;

	public BlockSmoothSandstoneSlab(int theMeta, boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "");
		meta = theMeta;
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("smooth" + (meta == 1 ? "_red" : "") + "_sandstone_slab"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public BasicVariantsSlab[] getSlabTypes() {
		return new BasicVariantsSlab[]{(BasicVariantsSlab) (meta == 0 ? ModBlocks.SMOOTH_SANDSTONE_SLAB.get() : ModBlocks.SMOOTH_RED_SANDSTONE_SLAB.get()),
				(BasicVariantsSlab) (meta == 0 ? ModBlocks.DOUBLE_SMOOTH_SANDSTONE_SLAB.get() : ModBlocks.DOUBLE_SMOOTH_RED_SANDSTONE_SLAB.get())};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		if(meta == 1)
			return new IIcon[] {ModBlocks.SMOOTH_RED_SANDSTONE.get().getIcon(side, 0)};
		return new IIcon[] {ModBlocks.SMOOTH_SANDSTONE.get().getIcon(side, 0)};
	}

}
