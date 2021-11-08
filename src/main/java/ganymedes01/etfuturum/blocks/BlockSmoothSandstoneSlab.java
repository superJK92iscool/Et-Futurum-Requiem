package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockSmoothSandstoneSlab extends BlockGenericSlab implements IConfigurable {

	private final int meta;
	public BlockSmoothSandstoneSlab(int theMeta, boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "");
		meta = theMeta;
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("smooth" + (meta == 1 ? "_red" : "") + "_sandstone_slab"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab)(meta == 0 ? ModBlocks.smooth_sandstone_slab : ModBlocks.smooth_red_sandstone_slab),
				(BlockGenericSlab)(meta == 0 ? ModBlocks.double_smooth_sandstone_slab : ModBlocks.double_smooth_red_sandstone_slab)};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		if(meta == 1)
			return new IIcon[] {ModBlocks.smooth_red_sandstone.getIcon(side, 0)};
		return new IIcon[] {ModBlocks.smooth_sandstone.getIcon(side, 0)};
	}

	@Override
	public boolean isEnabled() {
		return (ConfigBlocksItems.enableSmoothSandstone && meta == 0) || (ConfigBlocksItems.enableSmoothSandstone && ConfigBlocksItems.enableRedSandstone && meta == 1);
	}

}
