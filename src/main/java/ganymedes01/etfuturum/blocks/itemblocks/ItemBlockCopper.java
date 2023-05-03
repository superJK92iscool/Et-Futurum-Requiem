package ganymedes01.etfuturum.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockCopper extends ItemBlockGeneric {

	public ItemBlockCopper(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String name = super.getUnlocalizedName(stack);
		if(stack.getItemDamage() % 8 > 0)
			name = name.replace("_block", "");
		return name;
	}
}
