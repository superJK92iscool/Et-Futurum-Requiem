package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;

public class BlockWoodTrapdoor extends BlockTrapDoor {

	public BlockWoodTrapdoor(String type) {
		super(Material.wood);
		disableStats();
		setHardness(3.0F);
		setBlockName(Utils.getUnlocalisedName(type + "_trapdoor"));
		setBlockTextureName(type + "_trapdoor");
		setCreativeTab(EtFuturum.creativeTabBlocks);
		if (type.equals("crimson") || type.equals("warped")) {
			Utils.setBlockSound(this, ModSounds.soundNetherWood);
		} else if (type.equals("cherry")) {
			Utils.setBlockSound(this, ModSounds.soundCherryWood);
		} else if (type.equals("bamboo")) {
			Utils.setBlockSound(this, ModSounds.soundBambooWood);
		} else {
			setStepSound(Block.soundTypeWood);
		}

	}

	@Override
	public int getRenderType() {
		return RenderIDs.TRAPDOOR;
	}

}
