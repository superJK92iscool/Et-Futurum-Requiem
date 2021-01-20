package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockQuartzBricks extends Block implements IConfigurable {

	public BlockQuartzBricks() {
		super(Material.rock);
		this.setHardness(.8F);
		this.setResistance(.8F);
		this.setStepSound(soundTypePiston);
		this.setBlockName(Utils.getUnlocalisedName("quartz_bricks"));
        setBlockTextureName("quartz_bricks");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableSmoothQuartz;
	}

}
