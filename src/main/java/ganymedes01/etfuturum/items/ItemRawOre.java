package ganymedes01.etfuturum.items;

import java.util.HashMap;
import java.util.Map;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import ganymedes01.etfuturum.world.generate.RawOreDropMapping;
import net.minecraft.init.Blocks;

public class ItemRawOre extends ItemGeneric implements IConfigurable {
	
	public static final Map<BlockAndMetadataMapping, RawOreDropMapping> rawOreRegistry = new HashMap<BlockAndMetadataMapping, RawOreDropMapping>();

	public ItemRawOre() {
		super("copper", "iron", "gold");
		setUnlocalizedName(Utils.getUnlocalisedName("raw_ore"));
		setTextureName("raw");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
		if(isEnabled()) {
			rawOreRegistry.put(new BlockAndMetadataMapping(ModBlocks.copper_ore, 0), new RawOreDropMapping(this, 0, ConfigurationHandler.enableExtraCopper));
			rawOreRegistry.put(new BlockAndMetadataMapping(Blocks.iron_ore, 0), new RawOreDropMapping(this, 1, false));
			rawOreRegistry.put(new BlockAndMetadataMapping(Blocks.gold_ore, 0), new RawOreDropMapping(this, 2, false));
		}
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableRawOres;
	}

}
