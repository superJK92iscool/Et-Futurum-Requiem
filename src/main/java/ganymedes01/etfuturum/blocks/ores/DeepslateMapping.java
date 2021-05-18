package ganymedes01.etfuturum.blocks.ores;

import net.minecraft.block.Block;

public class DeepslateMapping {
	
	public DeepslateMapping(Block ore, int meta) {
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
		return obj instanceof DeepslateMapping && ore == ((DeepslateMapping)obj).ore && meta == ((DeepslateMapping)obj).meta;
	}
	
	@Override
	public int hashCode() {
		return System.identityHashCode(ore) + meta;
	}
}