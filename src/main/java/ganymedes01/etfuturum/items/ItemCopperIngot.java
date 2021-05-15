package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemCopperIngot extends Item implements IConfigurable {
	
	public ItemCopperIngot() {
		setTextureName("copper_ingot");
		setUnlocalizedName(Utils.getUnlocalisedName("copper_ingot"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableCopper;
	}

}
