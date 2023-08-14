package ganymedes01.etfuturum.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Raw ore class that doesn't add the raw ores to the Creative menu if there would be no ore for them to base off of.
 * Currently unused.
 */
public class ItemRawOre extends BaseSubtypesItem {

	public ItemRawOre() {
		super("raw_tin", "raw_aluminum", "raw_lead", "raw_silver");
		setNames("modded_raw_ore");
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		for (int i = 0; i < types.length; i++) {
			if (!OreDictionary.getOres("ingot" + StringUtils.capitalize(types[i].replaceFirst("^raw_", ""))).isEmpty()) {
				list.add(new ItemStack(item, 1, i));
			}
		}
	}

}
