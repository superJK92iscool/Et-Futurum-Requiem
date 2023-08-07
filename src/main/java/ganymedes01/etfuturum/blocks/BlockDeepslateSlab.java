package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;

public class BlockDeepslateSlab extends BasicSlab implements IMultiStepSound {

	private final boolean brick;

	public BlockDeepslateSlab(boolean isDouble, boolean isBrick) {
		super(isDouble, Material.rock, isBrick ? (new String[]{"deepslate_bricks", "deepslate_tiles"}) : (new String[]{"cobbled_deepslate", "polished_deepslate"}));
		brick = isBrick;
		this.setHardness(3);
		this.setResistance(6);
		this.setBlockName(Utils.getUnlocalisedName("deepslate" + (isBrick ? "_brick" : "") + "_slab"));
		this.setBlockTextureName("deepslate" + (isBrick ? "_brick" : "") + "_slab");
		this.setStepSound(ConfigSounds.newBlockSounds ? isBrick ? ModSounds.soundDeepslateBricks : ModSounds.soundDeepslate : soundTypeStone);
	}

	@Override
	public SoundType getStepSound(IBlockAccess world, int x, int y, int z, int meta) {
		if(brick && (meta % 8) == 1)
			return ModSounds.soundDeepslateTiles;
		return this.stepSound;
	}

	@Override
	public boolean requiresNewBlockSounds() {
		return true;
	}

}
