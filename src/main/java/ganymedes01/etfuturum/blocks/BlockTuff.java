package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
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
		setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundTuff : soundTypePiston);
		setBlockName(Utils.getUnlocalisedName("tuff"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		boolean flag = target == Blocks.stone || target == ModBlocks.DEEPSLATE.get() || this == target;
		if(flag) {
			BlockDeepslate.doDeepslateRedoCheck(world, x, y, z);
		}
		return flag;
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableTuff;
	}

}
