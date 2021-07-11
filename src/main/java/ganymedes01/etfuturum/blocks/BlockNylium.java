package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockNylium extends Block implements IConfigurable {

	public BlockNylium(int meta) {
		super(Material.rock);
		setHardness(3);
		setResistance(6);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("copper_block"));
		setBlockTextureName("copper_block");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setStepSound(ConfigBase.enableNewBlocksSounds ? ModSounds.soundCopper : Block.soundTypeMetal);
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

}
