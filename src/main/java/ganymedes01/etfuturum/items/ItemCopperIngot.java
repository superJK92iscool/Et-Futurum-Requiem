package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemCopperIngot extends Item {
	
	public ItemCopperIngot() {
		setTextureName("copper_ingot");
		setUnlocalizedName(Utils.getUnlocalisedName("copper_ingot"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}

}
