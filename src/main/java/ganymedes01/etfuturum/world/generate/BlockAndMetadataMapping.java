package ganymedes01.etfuturum.world.generate;

import net.minecraft.block.Block;

public class BlockAndMetadataMapping {
	
	/**
	 * Used by certain areas of Et Futurum to store a block and meta mapping that can be matched with a new instance.
	 * Used by raw ores and deepslate generation.
	 */
	public BlockAndMetadataMapping(Block ore, int meta) {
		this.ore = ore;
		this.meta = (meta & 15);
	}
	
	private Block ore;
	private int meta;
	
	public Block getOre() {
		return ore;
	}
	
	public int getMeta() {
		return meta;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof BlockAndMetadataMapping && ore == ((BlockAndMetadataMapping)obj).ore && meta == ((BlockAndMetadataMapping)obj).meta;
	}
	
	@Override
	public int hashCode() {
		return System.identityHashCode(ore) + meta;
	}
}