package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemTotemUndying extends Item implements IConfigurable {

	public ItemTotemUndying() {
		setTextureName("totem");
		setMaxStackSize(1);
		setUnlocalizedName(Utils.getUnlocalisedName("totem_of_undying"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableTotemUndying;
	}
}