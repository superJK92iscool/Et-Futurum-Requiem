package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ItemDragonBreath extends Item {

	public ItemDragonBreath() {
		setPotionEffect("-14+13");
		setTextureName("dragon_breath");
		setContainerItem(Items.glass_bottle);
		setUnlocalizedName(Utils.getUnlocalisedName("dragon_breath"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}
}