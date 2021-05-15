package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockTuff extends Block implements IConfigurable {

	public BlockTuff() {
		super(Material.rock);
		setHardness(1.5F);
		setResistance(6.0F);
		setBlockTextureName("tuff");
		setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundTuff : soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("tuff"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return this == target || target == Blocks.stone || target == ModBlocks.deepslate;
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableTuff;
	}

}
