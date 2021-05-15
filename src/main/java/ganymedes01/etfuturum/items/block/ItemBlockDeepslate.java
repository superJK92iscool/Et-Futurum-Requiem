package ganymedes01.etfuturum.items.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockDeepslate  extends ItemBlockGeneric {

	public ItemBlockDeepslate(Block block) {
		super(block);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		String name = super.getUnlocalizedName(stack);
		if(stack.getItemDamage() > 1)
			name = name.replace((stack.getItemDamage() == 4 ? "_" : "") + "bricks", (stack.getItemDamage() == 4 ? "" : "tiles"));
		return name;
	}
}
