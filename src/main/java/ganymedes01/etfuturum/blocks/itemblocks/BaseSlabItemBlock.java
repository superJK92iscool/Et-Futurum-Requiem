package ganymedes01.etfuturum.blocks.itemblocks;

import ganymedes01.etfuturum.blocks.BaseSlab;
import ganymedes01.etfuturum.blocks.ISubBlocksBlock;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class BaseSlabItemBlock extends ItemSlab {

	public BaseSlabItemBlock(Block block) {
		super(block, ((BaseSlab) block).getSingleSlab(), ((BaseSlab) block).getDoubleSlab(), block == ((BaseSlab) block).getDoubleSlab());
		this.setHasSubtypes(((ISubBlocksBlock) field_150939_a).getTypes().length > 1);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return addTilePrefix(((ISubBlocksBlock) field_150939_a).getNameFor(stack));
	}

	private String addTilePrefix(String name) {
		return name.startsWith("tile.") ? name : "tile." + Utils.getUnlocalisedName(name);
	}

	@Override
	public int getMetadata(int meta) {
		return meta % 8;
	}

}
