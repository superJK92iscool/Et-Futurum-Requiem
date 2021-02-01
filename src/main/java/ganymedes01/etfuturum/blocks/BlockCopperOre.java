package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCopperOre extends Block implements IConfigurable {

	public BlockCopperOre() {
		super(Material.rock);
		setHardness(3);
		setResistance(3);
		setBlockName(Utils.getUnlocalisedName("copper_ore"));
		setBlockTextureName("copper_ore");
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableCopper;
	}

}
