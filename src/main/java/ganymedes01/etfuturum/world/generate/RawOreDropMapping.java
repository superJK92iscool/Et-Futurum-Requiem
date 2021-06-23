package ganymedes01.etfuturum.world.generate;

import net.minecraft.item.Item;

public class RawOreDropMapping {
	
	/**
	 * Used by raw ores to keep a registry of what block drops which raw ores.
	 * Args: Block to drop raw ore from, does it drop 2-3? (Used by raw copper)
	 */
	public RawOreDropMapping(Item ore, int meta, boolean exdrops) {
		this.ore = ore;
		this.meta = meta;
		this.exdrops = exdrops;
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