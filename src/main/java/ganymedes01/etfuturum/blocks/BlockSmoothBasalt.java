package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSmoothBasalt extends Block implements IConfigurable {

	public BlockSmoothBasalt() {
		super(Material.rock);
		setHardness(1.25F);
		setResistance(4.2F);
		setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundBasalt : soundTypePiston);
		setBlockTextureName("smooth_basalt");
		setBlockName(Utils.getUnlocalisedName("smooth_basalt"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableAmethyst && isSmoothBasaltUsed();
	}
	
	/*
	 * Since the block ID check runs after the game loads, we need to write separate code to check for the config here.
	 * This is because since the block check runs after the game loads, the variable won't be populated yet.
	 */
	private boolean isSmoothBasaltUsed() {
		if(ConfigWorld.amethystOuterID == 0) {
			return true;
		}
		return ConfigWorld.amethystOuterID == 1 ? !ConfigBlocksItems.enableTuff : !EtFuturum.hasNetherlicious;
	}

}
