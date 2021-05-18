package ganymedes01.etfuturum.blocks.ores;

import net.minecraft.block.Block;

public class DeepslateMapping {

	public DeepslateMapping(Block ore, int meta) {
		this.ore = ore;
		this.meta = meta;
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
//    	System.out.println(obj.getClass() + " " + (obj instanceof DeepslateMapping));
        if (obj instanceof DeepslateMapping) {
            return ore == ((DeepslateMapping)obj).ore && meta == ((DeepslateMapping)obj).meta;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (String.valueOf(ore) + meta).hashCode();
    }
}