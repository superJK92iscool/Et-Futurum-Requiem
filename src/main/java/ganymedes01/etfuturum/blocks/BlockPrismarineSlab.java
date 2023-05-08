package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockPrismarineSlab extends BlockGenericSlab {

	public BlockPrismarineSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "rough", "brick", "dark");
		setHardness(1.5F);
		setResistance(6.0F);
		setBlockName(Utils.getUnlocalisedName("prismarine_slab"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab) ModBlocks.PRISMARINE_SLAB.get(), (BlockGenericSlab) ModBlocks.DOUBLE_PRISMARINE_SLAB.get()};
	}
	
	@Override
	public String func_150002_b(int meta)
	{
		meta %= 8;
				
		if(meta >= metaBlocks.length) {
			meta = 0;
		}

		return "tile.etfuturum." + metaBlocks[meta] + "_" + super.getUnlocalizedName().split("\\.")[2];
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		IIcon[] blocks = new IIcon[3];
		for(int i = 0; i < 3; i++) {
			blocks[i] = ModBlocks.PRISMARINE_BLOCK.get().getIcon(side, i);
		};
		return blocks;
	}
}