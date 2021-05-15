package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockPurpurSlab extends BlockGenericSlab implements IConfigurable {

	public BlockPurpurSlab(boolean isDouble) {
		super(isDouble, Material.rock, "");
		setResistance(6);
		setHardness(2.0F);
		setBlockName(Utils.getUnlocalisedName("purpur_slab"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
		return !(entity instanceof EntityDragon);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableChorusFruit;
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab)ModBlocks.purpur_slab, (BlockGenericSlab)ModBlocks.double_purpur_slab};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {ModBlocks.purpur_block.getIcon(side, 0)};
	}
}