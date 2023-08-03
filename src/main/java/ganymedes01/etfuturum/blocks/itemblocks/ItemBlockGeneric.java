package ganymedes01.etfuturum.blocks.itemblocks;

import ganymedes01.etfuturum.blocks.ISubBlocksBlock;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockGeneric extends ItemBlock {

	public ItemBlockGeneric(Block block) {
		super(block);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return addTilePrefix(((ISubBlocksBlock) field_150939_a).getNameFor(stack));
	}

	@Override
	public int getMetadata(int meta) {
		return meta % ((ISubBlocksBlock) field_150939_a).getTypes().length;
	}

	private String addTilePrefix(String name) {
		return "tile." + Utils.getUnlocalisedName(name);
	}

	private String[] getTypes() {
		return ((ISubBlocksBlock) field_150939_a).getTypes();
	}
}