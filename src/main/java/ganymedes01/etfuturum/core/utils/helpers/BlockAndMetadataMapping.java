package ganymedes01.etfuturum.core.utils.helpers;

import net.minecraft.block.Block;

public class BlockAndMetadataMapping {
	
	private Block block;
	private int meta;
	
	/**
	 * Used by certain areas of Et Futurum to store a block and meta mapping that can be matched with a new instance.
	 * Used by raw ores and deepslate generation.
	 */
	public BlockAndMetadataMapping(Block ore, int meta) {
		this.block = ore;
		this.meta = (meta & 15);
	}
	
	public Block getBlock() {
		return block;
	}
	
	public int getMeta() {
		return meta;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof BlockAndMetadataMapping && block == ((BlockAndMetadataMapping)obj).block && meta == ((BlockAndMetadataMapping)obj).meta;
	}
	
	@Override
	public int hashCode() {
		return block.hashCode() + meta;
	}
}