package ganymedes01.etfuturum.api.mappings;

import net.minecraft.block.Block;
import net.minecraftforge.oredict.OreDictionary;

public class BlockAndMetadataMapping {
	
	private Block block;
	private int meta;
	
	/**
	 * Used by certain areas of Et Futurum to store a block and meta mapping that can be matched with a new instance.
	 * Used by raw ores and deepslate generation.
	 */
	public BlockAndMetadataMapping(Block ore, int meta) {
		this.block = ore;
		if (meta != OreDictionary.WILDCARD_VALUE) {
			this.meta = (meta & 15);
		} else {
			this.meta = OreDictionary.WILDCARD_VALUE;
		}
	}
	
	public Block getBlock() {
		return block;
	}
	
	public int getMeta() {
		return meta;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof BlockAndMetadataMapping && block == ((BlockAndMetadataMapping) obj).block && (meta == OreDictionary.WILDCARD_VALUE || ((BlockAndMetadataMapping) obj).meta == OreDictionary.WILDCARD_VALUE || meta == ((BlockAndMetadataMapping) obj).meta);
	}
	
	@Override
	public int hashCode() {
		return block.hashCode() + meta;
	}
}