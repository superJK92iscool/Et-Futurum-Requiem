package ganymedes01.etfuturum.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import ganymedes01.etfuturum.world.generate.RawOreDropMapping;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ItemRawOre extends ItemGeneric implements IConfigurable {
	
	public static final Map<String, RawOreDropMapping> rawOreRegistry = new HashMap<String, RawOreDropMapping>();
	private boolean modded;

	public ItemRawOre(boolean mod) {
		super(mod ? new String[] {"tin", "aluminum", "lead", "silver"} : new String[] {"copper", "iron", "gold"});
		modded = mod;
		setUnlocalizedName(Utils.getUnlocalisedName((mod ? "modded_" : "") + "raw_ore"));
		setTextureName("raw");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName().replace("_ore", "") + "_" + types[Math.max(Math.min(stack.getItemDamage(), types.length - 1), 0)];
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableRawOres;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		if(modded) {
			super.getSubItems(item, tabs, list);
		} else {
			for (int i = 0; i < types.length; i++) {
				if(!OreDictionary.getOres("ore" + StringUtils.capitalize(types[i])).isEmpty()) {
					list.add(new ItemStack(item, 1, i));
				}
			}
		}
	}
	
	public static void initRawOres() {
		if(((IConfigurable)(ModItems.raw_ore)).isEnabled()) {
			CreativeTabs tab = ModItems.raw_ore.getCreativeTab();
			if(ConfigBlocksItems.enableCopper || !OreDictionary.getOres("oreCopper").isEmpty()) {
				ItemRawOre.rawOreRegistry.put("oreCopper", new RawOreDropMapping(ModItems.raw_ore, 0, ConfigWorld.enableExtraCopper || ArrayUtils.contains(ConfigWorld.extraDropRawOres, "oreCopper")));
			}
			ItemRawOre.rawOreRegistry.put("oreIron", new RawOreDropMapping(ModItems.raw_ore, 1));
			ItemRawOre.rawOreRegistry.put("oreGold", new RawOreDropMapping(ModItems.raw_ore, 2));
		}
	}

}
