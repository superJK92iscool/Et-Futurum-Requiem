package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigEntities;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemRabbitHide extends Item implements IConfigurable {

	public ItemRabbitHide() {
		setTextureName("rabbit_hide");
		setUnlocalizedName(Utils.getUnlocalisedName("rabbit_hide"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigEntities.enableRabbit;
	}
}