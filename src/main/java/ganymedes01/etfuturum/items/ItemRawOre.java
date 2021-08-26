package ganymedes01.etfuturum.items;

import java.util.HashMap;
import java.util.Map;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import ganymedes01.etfuturum.world.generate.RawOreDropMapping;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class ItemRawOre extends ItemGeneric implements IConfigurable {
	
	public static final Map<String, RawOreDropMapping> rawOreRegistry = new HashMap<String, RawOreDropMapping>();

	public ItemRawOre() {
		super("copper", "iron", "gold");
		setUnlocalizedName(Utils.getUnlocalisedName("raw_ore"));
		setTextureName("raw");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
		if(isEnabled()) {
			if(ConfigBlocksItems.enableCopper) {
				rawOreRegistry.put("oreCopper", new RawOreDropMapping(this, 0, ConfigBase.enableExtraCopper));
			}
			rawOreRegistry.put("oreIron", new RawOreDropMapping(this, 1, false));
			rawOreRegistry.put("oreGold", new RawOreDropMapping(this, 2, false));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName().replace("_ore", "") + "_" + types[Math.max(Math.min(stack.getItemDamage(), types.length - 1), 0)];
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableRawOres;
	}

}
