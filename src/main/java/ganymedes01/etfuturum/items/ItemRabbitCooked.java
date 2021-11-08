package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigEntities;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class ItemRabbitCooked extends ItemFood implements IConfigurable {

	public ItemRabbitCooked() {
		super(5, 0.6F, true);
		setTextureName("rabbit_cooked");
		setUnlocalizedName(Utils.getUnlocalisedName("rabbit_cooked"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigEntities.enableRabbit;
	}
}