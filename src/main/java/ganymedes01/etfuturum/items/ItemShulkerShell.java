package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemShulkerShell extends Item implements IConfigurable {
	
	public ItemShulkerShell() {
		super();
		setUnlocalizedName(Utils.getUnlocalisedName("shulker_shell"));
		setTextureName("shulker_shell");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableShulkerBoxes;
	}

}
