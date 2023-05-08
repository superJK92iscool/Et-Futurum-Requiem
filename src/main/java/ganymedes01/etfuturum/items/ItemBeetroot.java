package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class ItemBeetroot extends ItemFood {

	public ItemBeetroot() {
		super(1, 0.6F, false);
		setTextureName("beetroot");
		setUnlocalizedName(Utils.getUnlocalisedName("beetroot"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}
}