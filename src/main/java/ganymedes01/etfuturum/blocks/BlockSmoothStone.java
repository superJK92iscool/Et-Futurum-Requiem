package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSmoothStone extends Block implements IConfigurable {

	public BlockSmoothStone() {
		super(Material.rock);
		this.setHardness(2F);
		this.setResistance(6F);
		this.setStepSound(soundTypePiston);
		this.setBlockName(Utils.getUnlocalisedName("smooth_stone"));
        setBlockTextureName("stone_slab_top");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableSmoothStone;
	}

}
