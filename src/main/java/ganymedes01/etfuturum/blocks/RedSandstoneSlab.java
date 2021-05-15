package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class RedSandstoneSlab extends BlockGenericSlab implements IConfigurable {

	public RedSandstoneSlab(boolean isDouble) {
		super(isDouble, Material.rock, "", "cut");
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("red_sandstone_slab"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableRedSandstone;
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab)ModBlocks.red_sandstone_slab, (BlockGenericSlab)ModBlocks.double_red_sandstone_slab};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {ModBlocks.red_sandstone.getIcon(side, 0), ModBlocks.red_sandstone.getIcon(side, 2)};
	}
	
	public String func_150002_b(int meta)
	{
		meta %= 8;
				
		if(meta >= metaBlocks.length) {
			meta = 0;
		}

		if(metaBlocks[meta].equals("")) {
			return super.getUnlocalizedName();
		} else {
			return "tile.etfuturum." + metaBlocks[meta] + "_" + super.getUnlocalizedName().split("\\.")[2];
		}
	}
}