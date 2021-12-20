package ganymedes01.etfuturum.core.utils.helpers;

import net.minecraft.item.Item;

public class ItemAndMetadataMapping {
	
	private Item item;
	private int meta;
	
	/**
	 * Used by certain areas of Et Futurum to store an item and meta mapping that can be matched with a new instance.
	 * Unused, was created for composter originally but wasn't used. Will be staying in the code for possible future use.
	 */
	public ItemAndMetadataMapping(Item ore, int meta) {
		this.item = ore;
		this.meta = meta;
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getMeta() {
		return meta;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof ItemAndMetadataMapping && item == ((ItemAndMetadataMapping)obj).item && meta == ((ItemAndMetadataMapping)obj).meta;
	}
	
	@Override
	public int hashCode() {
		return item.hashCode() + meta;
	}
}