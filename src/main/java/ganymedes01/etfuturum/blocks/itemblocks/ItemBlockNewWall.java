package ganymedes01.etfuturum.blocks.itemblocks;

import ganymedes01.etfuturum.blocks.BasicWall;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockNewWall extends ItemBlock {

	public ItemBlockNewWall(Block p_i45328_1_) {
		super(p_i45328_1_);
		setHasSubtypes(((BasicWall) field_150939_a).variations > 1);
	}
	
	public int getMetadata(int p_77647_1_)
	{
		return p_77647_1_ % ((BasicWall) field_150939_a).variations;
	}
	
	public String getUnlocalizedName(ItemStack p_77667_1_)
	{
		return ((BasicWall) field_150939_a).getWallName(p_77667_1_);
	}

}
