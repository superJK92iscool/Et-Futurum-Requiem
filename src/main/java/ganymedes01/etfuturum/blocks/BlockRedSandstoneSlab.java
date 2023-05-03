package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockRedSandstoneSlab extends BlockGenericSlab implements IConfigurable {

	public BlockRedSandstoneSlab(boolean isDouble) {
		super(isDouble, Material.rock, "", "cut");
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("red_sandstone_slab"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableRedSandstone;
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab) ModBlocks.RED_SANDSTONE_SLAB.get(), (BlockGenericSlab) ModBlocks.DOUBLE_RED_SANDSTONE_SLAB.get()};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {ModBlocks.RED_SANDSTONE.get().getIcon(side, 0), ModBlocks.RED_SANDSTONE.get().getIcon(side, 2)};
	}
	
	@Override
	public String func_150002_b(int meta)
	{
		meta %= 8;
				
		if(meta >= metaBlocks.length) {
			meta = 0;
		}

		if(metaBlocks[meta].equals("")) {
			return super.getUnlocalizedName();
		}
		return "tile.etfuturum." + metaBlocks[meta] + "_" + super.getUnlocalizedName().split("\\.")[2];
	}
}