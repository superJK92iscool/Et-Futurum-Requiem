package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.item.ItemFood;

public class ItemMuttonCooked extends ItemFood implements IConfigurable {

	public ItemMuttonCooked() {
		super(6, 0.8F, true);
		setTextureName("mutton_cooked");
		setUnlocalizedName(Utils.getUnlocalisedName("mutton_cooked"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableMutton;
	}
}