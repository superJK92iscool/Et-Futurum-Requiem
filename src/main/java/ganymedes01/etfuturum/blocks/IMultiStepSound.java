package ganymedes01.etfuturum.blocks;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

/*
 * Used by Et Futurum Requiem to set a custom step sound for different states of a block.
 */
public interface IMultiStepSound {

	public abstract Block.SoundType getStepSound(IBlockAccess world, int x, int y, int z, int meta);
	
	/*
	 * If new block sounds are turned off, the new function is not used.
	 */
	public abstract boolean requiresNewBlockSounds();
	
}
