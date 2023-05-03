package ganymedes01.etfuturum.blocks.itemblocks;

import ganymedes01.etfuturum.blocks.BlockGeneric;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockRawOre extends ItemBlockGeneric {

	public ItemBlockRawOre(Block block) {
		super(block);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String name = ((BlockGeneric) field_150939_a).getNameFor(stack.getItemDamage());
		return getUnlocalizedName().replace("ore_block", "") + name;
	}
}
