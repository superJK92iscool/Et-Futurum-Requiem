package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockQuartzBricks extends Block {

	public BlockQuartzBricks() {
		super(Material.rock);
		this.setHardness(.8F);
		this.setResistance(.8F);
		this.setStepSound(soundTypePiston);
		this.setBlockName(Utils.getUnlocalisedName("quartz_bricks"));
		setBlockTextureName("quartz_bricks");
		this.setCreativeTab(EtFuturum.creativeTabBlocks);
	}

}
