package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RedNetherBrick extends Block implements IConfigurable {

	public RedNetherBrick()
	{
		super(Material.rock);
		setHardness(2F);
		setResistance(10F);
		setStepSound(soundTypePiston);
		setBlockTextureName("red_nether_brick");
		setBlockName(Utils.getUnlocalisedName("red_netherbrick"));
		setCreativeTab(EtFuturum.enableNetherBlocks ? EtFuturum.creativeTab : null);
	}
	
	@Override
	public boolean isEnabled() {
		return EtFuturum.enableNetherBlocks;
	}
	
}
