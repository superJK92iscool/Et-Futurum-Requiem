package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.Random;

public class BlockBlueIce extends Block {

	public BlockBlueIce() {
		super(Material.ice);
		this.slipperiness = 0.989F;
		this.setHarvestLevel("pickaxe", 0);
		this.setStepSound(soundTypeGlass);
		this.setHardness(2.8F);
		this.setResistance(2.8F);
		this.setBlockName(Utils.getUnlocalisedName("blue_ice"));
		this.setBlockTextureName("blue_ice");
		this.setCreativeTab(EtFuturum.creativeTabBlocks);
	}
	
	@Override
	public int quantityDropped(Random p_149745_1_)
	{
		return 0;
	}

}
