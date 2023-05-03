package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemPoppedChorusFruit extends Item implements IConfigurable {

	public ItemPoppedChorusFruit() {
		setTextureName("chorus_fruit_popped");
		setUnlocalizedName(Utils.getUnlocalisedName("chorus_fruit_popped"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableChorusFruit;
	}
}