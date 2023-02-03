package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDripstone extends Block implements IConfigurable {

	public BlockDripstone() {
		super(Material.rock);
		this.setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundDripstoneBlock : Block.soundTypeStone);
		this.setHardness(1.5F);
		this.setResistance(1F);
		this.setHarvestLevel("pickaxe", 0);
		this.setBlockName(Utils.getUnlocalisedName("dripstone_block"));
		this.setBlockTextureName("dripstone_block");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.TESTING;
	}

}
