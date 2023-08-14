package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class BaseItem extends Item {

	public BaseItem() {
		super();
		setCreativeTab(EtFuturum.creativeTabItems);
	}

	public BaseItem(String names) {
		this();
		setNames(names);
	}

	public BaseItem setUnlocalizedNameWithPrefix(String name) {
		setUnlocalizedName(Utils.getUnlocalisedName(name));
		return this;
	}

	public BaseItem setNames(String name) {
		setUnlocalizedNameWithPrefix(name);
		setTextureName(name);
		return this;
	}
}
