package ganymedes01.etfuturum.items.block;

import ganymedes01.etfuturum.blocks.BlockGeneric;
import ganymedes01.etfuturum.blocks.BlockGenericSand;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockCopper extends ItemBlockGeneric {

	public ItemBlockCopper(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String name = super.getUnlocalizedName(stack);
		if(stack.getItemDamage() % 8 > 0 && stack.getItemDamage() != 15)
			name = name.replace("_block", "");
		return name;
	}
}
