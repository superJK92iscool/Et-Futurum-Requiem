package ganymedes01.etfuturum.api;

import ganymedes01.etfuturum.Tags;
import ganymedes01.etfuturum.core.utils.ItemStackSet;
import ganymedes01.etfuturum.core.utils.Logger;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import roadhog360.hogutils.api.RegistryMapping;
import roadhog360.hogutils.api.hogtags.HogTagsHelper;

import java.util.List;

@Deprecated
public class EnchantingFuelRegistry {

	@Deprecated
	public static void registerFuel(Object itemObj) {
		if (itemObj instanceof ItemStack stack) {
			HogTagsHelper.ItemTags.addTags(stack.getItem(), stack.getItemDamage(), Tags.MOD_ID + ":enchantment_fuel");
		} else if (itemObj instanceof String) {
			for (ItemStack oreStack : OreDictionary.getOres((String) itemObj)) {
				HogTagsHelper.ItemTags.addTags(oreStack.getItem(), oreStack.getItemDamage(), Tags.MOD_ID + ":enchantment_fuel");
			}
		} else if (itemObj instanceof Item item) {
			HogTagsHelper.ItemTags.addTags(item, Tags.MOD_ID + ":enchantment_fuel");
		} else if (itemObj instanceof Block block && Item.getItemFromBlock(block) != null) {
			HogTagsHelper.ItemTags.addTags(Item.getItemFromBlock(block), Tags.MOD_ID + ":enchantment_fuel");
		} else {
			throw new IllegalArgumentException("Tried to add " + itemObj + " as an enchanting fuel, which is not an Itemstack, item, block or string.");
		}
	}

	@Deprecated
	public static void registerFuel(List<Object> list, int count) {
		list.forEach(EnchantingFuelRegistry::registerFuel);
	}

	@Deprecated
	public static void remove(String fuelOreDict) {
		for (ItemStack stack : OreDictionary.getOres(fuelOreDict)) {
			remove(stack);
		}
	}

	@Deprecated
	public static void remove(ItemStack fuel) {
		HogTagsHelper.ItemTags.removeTags(fuel.getItem(), fuel.getItemDamage(), Tags.MOD_ID + ":enchantment_fuel");
	}

	@Deprecated
	public static boolean isFuel(String fuelOreDict) {
		for (ItemStack stack : OreDictionary.getOres(fuelOreDict)) {
			if (HogTagsHelper.ItemTags.hasAnyTag(stack.getItem(), stack.getItemDamage(), Tags.MOD_ID + ":enchantment_fuel")) return true;
		}
		return false;
	}

	@Deprecated
	public static boolean isFuel(ItemStack fuel) {
		return HogTagsHelper.ItemTags.hasAnyTag(fuel.getItem(), fuel.getItemDamage(), Tags.MOD_ID + ":enchantment_fuel");
	}

	@Deprecated
	public static ItemStackSet getFuels() {
		ItemStackSet set = new ItemStackSet();
		HogTagsHelper.ItemTags.getInTag(Tags.MOD_ID + ":enchantment_fuel").stream().map(RegistryMapping::newItemStack).forEach(set::add);
		return set;
	}

	/**
	 * Print all entries in enchanting fuel registry. Used for debugging purposes.
	 */
	@Deprecated
	public void printFuels() {
		getFuels().forEach((key, value) -> Logger.info("Enchanting fuel entry: " + Item.itemRegistry.getNameForObject(key.getItem()) + " Meta: " + (key.getItemDamage() == OreDictionary.WILDCARD_VALUE ? "any" : key.getItemDamage())));
	}
}