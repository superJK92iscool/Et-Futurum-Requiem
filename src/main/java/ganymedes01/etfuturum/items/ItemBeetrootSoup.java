package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSoup;

public class ItemBeetrootSoup extends ItemSoup implements IConfigurable {

	public ItemBeetrootSoup() {
		super(6);
		setTextureName("beetroot_soup");
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot_soup"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableBeetroot;
	}
}