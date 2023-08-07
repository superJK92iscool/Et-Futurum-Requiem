package ganymedes01.etfuturum.blocks.itemblocks;

import ganymedes01.etfuturum.blocks.BasicSlab;
import ganymedes01.etfuturum.blocks.ISubBlocksBlock;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemBlockGenericSlab extends ItemSlab {

	public ItemBlockGenericSlab(Block block) {
		super(block, ((BasicSlab) block).getSingleSlab(), ((BasicSlab) block).getDoubleSlab(), block == ((BasicSlab) block).getDoubleSlab());
		this.setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return addTilePrefix(((ISubBlocksBlock) field_150939_a).getNameFor(stack));
	}

	private String addTilePrefix(String name) {
		return "tile." + Utils.getUnlocalisedName(name);
	}

	@Override
	public int getMetadata(int meta) {
		return meta % 8;
	}

}
