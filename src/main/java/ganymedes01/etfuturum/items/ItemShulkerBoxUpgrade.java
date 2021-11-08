package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;

public class ItemShulkerBoxUpgrade extends ItemGeneric implements IConfigurable {

	public ItemShulkerBoxUpgrade() {
		super("copper", "iron", "gold");
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableIronShulkerBoxes;
	}

}
