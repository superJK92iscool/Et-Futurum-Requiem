package ganymedes01.etfuturum.items.block;

import ganymedes01.etfuturum.blocks.BlockNewWall;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockNewWall extends ItemBlock {

	public ItemBlockNewWall(Block p_i45328_1_) {
		super(p_i45328_1_);
		setHasSubtypes(((BlockNewWall)field_150939_a).variations > 1);
	}
	
	public int getMetadata(int p_77647_1_)
	{
		return p_77647_1_ % ((BlockNewWall)field_150939_a).variations;
	}
	
	public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		return ((BlockNewWall)field_150939_a).getWallName(p_77667_1_);
	}

}
