package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemAmethystShard extends Item {

	public ItemAmethystShard() {
		setTextureName("amethyst_shard");
		setUnlocalizedName(Utils.getUnlocalisedName("amethyst_shard"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}

}
