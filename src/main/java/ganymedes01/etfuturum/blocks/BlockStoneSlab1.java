package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockStoneSlab1 extends BlockGenericSlab implements IConfigurable {

	public BlockStoneSlab1(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.rock, "stone", "mossy_cobblestone", "mossy_stone_brick", "cut_sandstone");
		setHardness(2F);
		setResistance(6F);
		setStepSound(soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("stone_slab"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableGenericSlabs;
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab) ModBlocks.generic_slab, (BlockGenericSlab) ModBlocks.double_generic_slab};
	}

	@Override
	public IIcon[] getSlabIcons(int side) {
		return new IIcon[] {Blocks.stone.getIcon(side, 0),Blocks.mossy_cobblestone.getIcon(side, 0),
				Blocks.stonebrick.getIcon(side, 1), Blocks.sandstone.getIcon(side, 2)};
	}
    
}
