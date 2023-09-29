package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockMud extends BaseBlock {

	public BlockMud() {
		super(Material.ground);
		setHardness(0.5F);
		setResistance(0.5F);
		setBlockTextureName("mud");
		setBlockName(Utils.getUnlocalisedName("mud"));
		setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundMud : Block.soundTypeGravel);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

}
