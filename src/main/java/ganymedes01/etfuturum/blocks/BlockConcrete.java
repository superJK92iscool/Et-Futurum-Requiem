package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;

public class BlockConcrete extends BasicSubtypesBlock {

	public BlockConcrete() {
		super(Material.rock, "white_concrete", "orange_concrete", "magenta_concrete", "light_blue_concrete", "yellow_concrete", "lime_concrete", "pink_concrete",
				"gray_concrete", "light_gray_concrete", "cyan_concrete", "purple_concrete", "blue_concrete", "brown_concrete", "green_concrete", "red_concrete", "black_concrete");
		this.setHarvestLevel("pickaxe", 0);
		this.setStepSound(soundTypeStone);
		this.setHardness(1.8F);
		this.setResistance(1.8F);
		this.setBlockName(Utils.getUnlocalisedName("concrete"));
		this.setBlockTextureName("concrete");
		this.setCreativeTab(EtFuturum.creativeTabBlocks);
	}
}
