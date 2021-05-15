package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDeepslatePolished extends Block implements IConfigurable {

	public BlockDeepslatePolished() {
		super(Material.rock);
		this.setHardness(3.5F);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("polished_deepslate"));
		this.setBlockTextureName("polished_deepslate");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		this.setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundDeepslate : soundTypeStone);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableDeepslate;
	}
}
