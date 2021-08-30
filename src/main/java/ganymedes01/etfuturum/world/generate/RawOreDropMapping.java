package ganymedes01.etfuturum.world.generate;

import org.apache.commons.lang3.ArrayUtils;

import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.items.ItemRawOre;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RawOreDropMapping {
	
	/**
	 * Used by raw ores to keep a registry of what block drops which raw ores.
	 * Args:
	 * ore: Item to drop
	 * meta: Meta
	 * exdrops: Drops 2-3?
	 */
	public RawOreDropMapping(Item ore, int meta, boolean exdrops) {
		this.ore = ore;
		this.meta = meta;
		this.exdrops = exdrops;
	}
	
	public RawOreDropMapping(Item ore, int meta) {
		this.ore = ore;
		this.meta = meta;
		for(int oreID : OreDictionary.getOreIDs(new ItemStack(ore, 1, meta))) {
			String oreName = OreDictionary.getOreName(oreID);
			if(oreName != null && oreName.startsWith("ore")) {
				this.exdrops = ArrayUtils.contains(ConfigBase.extraDropRawOres, oreName);
				break;
			}
		}
	}
	
	private Item ore;
	private int meta;
	private boolean exdrops;
	
	public Item getItem() {
		return ore;
	}
	
	public int getMeta() {
		return meta;
	}
	
	public boolean getDropsExtra() {
		return exdrops;
	}
}