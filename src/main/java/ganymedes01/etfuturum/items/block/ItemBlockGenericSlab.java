package ganymedes01.etfuturum.items.block;

import ganymedes01.etfuturum.blocks.BlockGenericSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemBlockGenericSlab extends ItemSlab {

	
	final String[] metaBlocks;

	public ItemBlockGenericSlab(Block block) {
		super(block, (BlockSlab)((BlockGenericSlab)block).getSlabTypes()[0], (BlockSlab)((BlockGenericSlab)block).getSlabTypes()[1], ((BlockGenericSlab)block).isDouble());
		this.setHasSubtypes(true);
		metaBlocks = (String[]) ((BlockGenericSlab)block).metaBlocks;
	}

	public String getUnlocalizedName(ItemStack itemStack) {
		return ((BlockGenericSlab)Block.getBlockFromItem(itemStack.getItem())).func_150002_b(itemStack.getItemDamage()).replace("double_", "");
	}

	
	public int getMetadata(int meta) {
		return meta % 8 == 7 ? 0 : meta;
	}

}
