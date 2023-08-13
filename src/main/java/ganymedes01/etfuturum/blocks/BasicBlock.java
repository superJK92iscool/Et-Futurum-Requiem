package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * Because the standard block constructor is protected...
 * I also provide some extra helper functions and stuff :3
 */
public class BasicBlock extends Block {

	private Block mapColorBase;

	public BasicBlock(Material p_i45394_1_) {
		super(p_i45394_1_);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	public BasicBlock setUnlocalizedNameWithPrefix(String name) {
		setBlockName(Utils.getUnlocalisedName(name));
		return this;
	}

	public BasicBlock setNames(String name) {
		setUnlocalizedNameWithPrefix(name);
		setBlockTextureName(name);
		return this;
	}

	public BasicBlock setToolClass(String toolClass, int level) {
		for (int m = 0; m < 16; m++) {
			setHarvestLevel(toolClass, level, m);
		}
		return this;
	}

	public BasicBlock setToolClass(String toolClass, int level, int meta) {
		setHarvestLevel(toolClass, level, meta);
		return this;
	}

	public BasicBlock setBlockSound(SoundType type) {
		if (type instanceof ModSounds.CustomSound) {
			setStepSound(ConfigSounds.newBlockSounds ? type : ((ModSounds.CustomSound) type).getDisabledSound());
		}
		setStepSound(type);
		return this;
	}

	public BasicBlock setMapColorBaseBlock(Block block) {
		mapColorBase = block;
		return this;
	}

	@Override
	public MapColor getMapColor(int p_149728_1_) {
		return mapColorBase == null ? super.getMapColor(p_149728_1_) : mapColorBase.getMapColor(p_149728_1_);
	}
}
