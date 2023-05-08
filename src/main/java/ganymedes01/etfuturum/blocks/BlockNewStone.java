package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockNewStone extends BlockGeneric {

	public BlockNewStone() {
		super(Material.rock, "", "granite", "granite_smooth", "diorite", "diorite_smooth", "andesite", "andesite_smooth");
		startMeta = 1;
		setHardness(1.5F);
		setResistance(6.0F);
		setBlockTextureName("stone");
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("stone"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return this == target || target == Blocks.stone;
	}
}