package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemTotemUndying extends Item {

	public ItemTotemUndying() {
		setTextureName("totem_of_undying");
		setMaxStackSize(1);
		setUnlocalizedName(Utils.getUnlocalisedName("totem_of_undying"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}
}