package ganymedes01.etfuturum.core.utils.helpers;

public class CachedChunkCoords {
	
	private int x;
	private int z;
	private int dim;

	public CachedChunkCoords(int xPos, int zPos, int dimension) {
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
	
	public int dim() {
		return dim;
	}
	
	@Override
	public boolean equals(Object p_equals_1_) {
		if(p_equals_1_ == this) return true;
		
		if(p_equals_1_ instanceof CachedChunkCoords) {
			CachedChunkCoords coords = (CachedChunkCoords) p_equals_1_;
			return coords.x == x && coords.z == z && coords.dim == dim;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (x + z * 31) * 31 + dim;
	}
	
	@Override
	public String toString() {
		return "Chunk coords: " + x + ", " + z + "... In dim: " + dim;
	}
	
}
