package ganymedes01.etfuturum.blocks;

import net.minecraft.item.ItemBlock;

public interface ISubBlocksBlock {
	Class<? extends ItemBlock> getItemBlockClass();
}
