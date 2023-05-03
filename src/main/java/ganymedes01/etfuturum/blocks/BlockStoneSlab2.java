package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockStoneSlab2 extends BlockGenericSlab implements IConfigurable {

	public BlockStoneSlab2(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "granite", "polished_granite", "diorite", "polished_diorite", "andesite", "polished_andesite");
		setHardness(2F);
		setResistance(6F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("stone_slab_2"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableStones;
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab) ModBlocks.STONE_SLAB_2.get(), (BlockGenericSlab) ModBlocks.DOUBLE_STONE_SLAB_2.get()};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		IIcon[] blocks = new IIcon[6];
		for(int i = 0; i < 6; i++) {
			blocks[i] = ModBlocks.STONE.get().getIcon(side, i + 1);
		};
		return blocks;
	}

}
