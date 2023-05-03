package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemStack;

public class ItemNetherite extends ItemUninflammable implements IConfigurable {
	
	public ItemNetherite(int type) {
		super();
		setTextureName("netherite" + (type == 1 ? "_ingot" : "_scrap"));
		setUnlocalizedName(Utils.getUnlocalisedName("netherite" + (type == 1 ? "_ingot" : "_scrap")));
		setCreativeTab(EtFuturum.creativeTabItems);
	}
	
	@Override
	public boolean isBeaconPayment(ItemStack stack) {
		return this == ModItems.NETHERITE_INGOT.get();
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableNetherite;
	}
}
