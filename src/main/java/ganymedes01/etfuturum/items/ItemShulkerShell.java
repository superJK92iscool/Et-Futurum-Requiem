package ganymedes01.etfuturum.items;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import ganymedes01.etfuturum.world.generate.RawOreDropMapping;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class ItemShulkerShell extends Item implements IConfigurable {
	
	public ItemShulkerShell() {
		super();
		setUnlocalizedName(Utils.getUnlocalisedName("shulker_shell"));
		setTextureName("shulker_shell");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableShulkerBoxes;
	}

}
