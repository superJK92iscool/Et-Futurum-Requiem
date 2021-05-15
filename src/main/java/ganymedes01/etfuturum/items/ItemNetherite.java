package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemStack;

public class ItemNetherite extends ItemUninflammable implements IConfigurable {
	
	public ItemNetherite(int type) {
		super();
		setTextureName("netherite" + (type == 1 ? "_ingot" : "_scrap"));
		setUnlocalizedName(Utils.getUnlocalisedName("netherite" + (type == 1 ? "_ingot" : "_scrap")));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}
	
	@Override
	public boolean isBeaconPayment(ItemStack stack) {
		return this == ModItems.netherite_ingot;
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableNetherite;
	}
}
