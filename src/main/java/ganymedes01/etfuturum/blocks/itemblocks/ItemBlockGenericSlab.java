package ganymedes01.etfuturum.blocks.itemblocks;

import ganymedes01.etfuturum.blocks.BasicVariantsSlab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemBlockGenericSlab extends ItemSlab {

	final String[] metaBlocks;

	public ItemBlockGenericSlab(Block block) {
		super(block, ((BasicVariantsSlab) block).getSlabTypes()[0], ((BasicVariantsSlab) block).getSlabTypes()[1], ((BasicVariantsSlab) block).isDouble());
		this.setHasSubtypes(true);
		metaBlocks = ((BasicVariantsSlab) block).metaBlocks;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return ((BasicVariantsSlab) Block.getBlockFromItem(itemStack.getItem())).func_150002_b(itemStack.getItemDamage()).replace("double_", "");
	}

	
	@Override
	public int getMetadata(int meta) {
		return meta % 8;
	}

}
