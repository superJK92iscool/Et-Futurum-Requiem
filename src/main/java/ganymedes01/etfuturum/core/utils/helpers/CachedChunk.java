package ganymedes01.etfuturum.core.utils.helpers;

import net.minecraft.world.chunk.Chunk;

public class CachedChunk {
	
	private int x;
	private int z;
	private int dim;
	private Chunk chunk;

	public CachedChunk(int xPos, int zPos, int dimension) {
		x = xPos;
		z = zPos;
		dim = dimension;
	}
	
	public int xPos() {
		return x;
	}
	
	public int zPos() {
		return z;
	}
	
	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
		x = chunk.xPosition;
		z = chunk.zPosition;
		dim = chunk.worldObj.provider.dimensionId;
	}
	
	public Chunk getChunk() {
		return chunk;
	}
	
	@Override
	public boolean equals(Object p_equals_1_) {
		if(p_equals_1_ == this) return true;
		
		if(p_equals_1_ instanceof CachedChunk) {
			CachedChunk coords = (CachedChunk) p_equals_1_;
			return coords.x == x && coords.z == z && coords.dim == dim;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (x + z * 31) * 31 + dim;
	}
	
}
