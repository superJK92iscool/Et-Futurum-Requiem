package ganymedes01.etfuturum.core.utils;

import java.util.HashMap;
import java.util.Map;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.world.generate.RawOreDropMapping;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;


/**
 * The registry Et Futurum's block breaking handler uses to find out if we should drop
 * a raw ore item. When broken, that blocks OreDictionary tags are checked and if it
 * matches a tag that is mapped here, it will drop the respective item you mapped.
 * 
 * @apiNote
 * Usage example: {addOre("oreIron", ModItems.raw_ore, 1);} would cause any ore with the tag
 * "oreIron" to drop Et Futurum's raw ore item, with a meta value of 1, which is the raw iron item.
 * 
 * @author roadhog360
 */
public class RawOreRegistry {
	
	public static final Map<String, RawOreDropMapping> rawOreRegistry = new HashMap<String, RawOreDropMapping>();

	/**
	 * What item should blocks that have this OreDict tag drop?
	 * Assumes a meta value of 0 for the specified drop.
	 * 
	 * @param oreDict
	 * @param ore
	 */
	public static void addOre(String oreDict, Item ore) {
		addOre(oreDict, ore, 0);
	}


	/**
	 * What item should blocks that have this OreDict tag drop?
	 * 
	 * @param oreDict
	 * @param ore
	 */
	public static void addOre(String oreDict, Item ore, int meta) {
		addOre(oreDict, new RawOreDropMapping(ore, meta));
	}
	
	private static void addOre(String oreDict, RawOreDropMapping map) {
		rawOreRegistry.put(oreDict, map);
	}
	
	/**
	 * @param oreDict
	 * @return Returns true if this oreDictionary tag has a raw ore drop assigned to it.
	 * Otherwise it returns false
	 */
	public static boolean hasOre(String oreDict) {
		return rawOreRegistry.containsKey(oreDict);
	}
	
	/**
	 * Gets the raw ore this should drop.
	 * 
	 * @param oreDict
	 * @return As the class RawOreDropMapping, which
	 * contains the instance of the item the specified OreDictionary tag should drop,
	 * the meta value it should drop as and a boolean which determines if it should drop
	 * extra instead of just one per block, which is used by copper, as well as how much
	 * it will drop, inputting a Random instance and the fortune level.
	 */
	public static RawOreDropMapping getOre(String oreDict) {
		return rawOreRegistry.get(oreDict);
	}


	/**
	 * @return
	 * The entire raw ore mapping, where an OreDictionary tag is the key.
	 * The key's return value is of the class RawOreDropMapping, which contains
	 * the instance of the item the specified OreDictionary tag should drop,
	 * the meta value it should drop as and a boolean which determines if it should drop
	 * extra instead of just one per block, which is used by copper, as well as how much
	 * it will drop, inputting a Random instance and the fortune level.
	 * 
	 * Do not use this to add or get items from the map this way,
	 * in case the key changes.
	 */
	public static Map<String, RawOreDropMapping> getOreMap() {
		return rawOreRegistry;
	}

	public static void init() {
		if(((IConfigurable)(ModItems.raw_ore)).isEnabled()) {
			if(ConfigBlocksItems.enableCopper || !OreDictionary.getOres("oreCopper").isEmpty()) {
				addOre("oreCopper", ModItems.raw_ore);
			}
			addOre("oreIron", ModItems.raw_ore, 1);
			addOre("oreGold", ModItems.raw_ore, 2);
		}
	}

}
