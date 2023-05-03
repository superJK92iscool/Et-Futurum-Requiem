package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigEntities;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class ItemRabbitRaw extends ItemFood implements IConfigurable {

	public ItemRabbitRaw() {
		super(3, 0.3F, true);
		setTextureName("rabbit_raw");
		setUnlocalizedName(Utils.getUnlocalisedName("rabbit_raw"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}

	@Override
	public boolean isEnabled() {
		return ConfigEntities.enableRabbit;
	}
}