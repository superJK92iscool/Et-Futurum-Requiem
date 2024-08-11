package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.recipes.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDyedBed extends BlockBed {

	public BlockDyedBed(int dye) {
		super();
		disableStats();
		setHardness(0.2F);
		String dyeName = ModRecipes.dye_names[dye];
		setBlockName(Utils.getUnlocalisedName(dyeName + "_bed"));
		setBlockTextureName(dyeName + "_bed");
		setCreativeTab(EtFuturum.creativeTabBlocks);
		setStepSound(Block.soundTypeWood);
		blockMaterial = Material.wood;
	}

	public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player) {
		return true;
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return isBlockHeadOfBed(p_149650_1_) ? Item.getItemById(0) : Item.getItemFromBlock(this);
	}

	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public String getItemIconName() {
		return getTextureName();
	}

}
