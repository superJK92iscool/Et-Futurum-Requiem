package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.Item;

public class ItemAmethystShard extends Item implements IConfigurable {

	public ItemAmethystShard() {
		setTextureName("amethyst_shard");
		setUnlocalizedName(Utils.getUnlocalisedName("amethyst_shard"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableAmethyst;
	}

}
