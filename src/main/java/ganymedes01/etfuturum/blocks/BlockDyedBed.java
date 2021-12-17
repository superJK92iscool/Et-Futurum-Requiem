package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemDyedBed;
import ganymedes01.etfuturum.recipes.ModRecipes;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;

public class BlockDyedBed extends BlockBed implements IConfigurable, ISubBlocksBlock {
	
	public BlockDyedBed(int dye) {
		super();
		if(dye == 14) {
			dye = 15;
		}
		blockMaterial = Material.wood;
		setHardness(0.2F);
		String dyeName = ModRecipes.dye_names[dye];
		setBlockName(Utils.getUnlocalisedName(dyeName + "_bed"));
		disableStats();
		setBlockTextureName(dyeName + "_bed");
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return isBlockHeadOfBed(p_149650_1_) ? Item.getItemById(0) : Item.getItemFromBlock(this);
	}

	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	{
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public String getItemIconName() {
		String[] name = getTextureName().split("_");
		return name[name.length - 1] + "_" + name[0];
	}
	
	@Override
	public boolean isEnabled() {
		return EtFuturum.TESTING;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		// TODO Auto-generated method stub
		return ItemDyedBed.class;
	}
	
}
