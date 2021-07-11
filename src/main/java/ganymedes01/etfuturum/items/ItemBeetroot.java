package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class ItemBeetroot extends ItemFood implements IConfigurable {

	public ItemBeetroot() {
		super(1, 0.6F, false);
		setTextureName("beetroot");
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableBeetroot;
	}
}