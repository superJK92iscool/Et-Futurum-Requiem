package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemPrismarineCrystals extends Item implements IConfigurable {

	public ItemPrismarineCrystals() {
		setTextureName("prismarine_crystals");
		setUnlocalizedName(Utils.getUnlocalisedName("prismarine_crystals"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enablePrismarine;
	}
}