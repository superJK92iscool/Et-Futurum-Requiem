package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemPoppedChorusFruit extends Item implements IConfigurable {

	public ItemPoppedChorusFruit() {
		setTextureName("chorus_fruit_popped");
		setUnlocalizedName(Utils.getUnlocalisedName("chorus_fruit_popped"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableChorusFruit;
	}
}