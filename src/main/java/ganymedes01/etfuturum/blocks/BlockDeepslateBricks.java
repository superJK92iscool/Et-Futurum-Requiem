package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockDeepslateBricks extends BasicSubtypesBlock implements IMultiStepSound {

	public BlockDeepslateBricks() {
		super(Material.rock, "deepslate_bricks", "cracked_deepslate_bricks", "deepslate_tiles", "cracked_deepslate_tiles", "chiseled_deepslate");
		this.setHardness(1.5F);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("deepslate_bricks"));
		this.setBlockTextureName("deepslate_bricks");
		this.setCreativeTab(EtFuturum.creativeTabBlocks);
		this.setStepSound(ConfigSounds.newBlockSounds ? ModSounds.soundDeepslateBricks : soundTypeStone);
	}

	@Override
	public SoundType getStepSound(IBlockAccess world, int x, int y, int z, int meta) {
		if(meta == 2 || meta == 3) {
			return ModSounds.soundDeepslateTiles;
		}
		return this.stepSound;
	}

	@Override
	public boolean requiresNewBlockSounds() {
		return true;
	}
}
