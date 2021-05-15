package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockNetherwart extends Block implements IConfigurable {

	public BlockNetherwart()
	{
		super(Material.grass);
		setHardness(1F);
		setResistance(5F);
		setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundWartBlock : soundTypeWood);
		setBlockTextureName("nether_wart_block");
		setBlockName(Utils.getUnlocalisedName("nether_wart"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableNetherwartBlock;
	}
	
}
