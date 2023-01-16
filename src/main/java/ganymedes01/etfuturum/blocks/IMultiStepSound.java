package ganymedes01.etfuturum.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

/*
 * Used by Et Futurum Requiem to set a custom step sound for different states of a block.
 */
public interface IMultiStepSound {

	/**
	 * Used for blocks that should have a different SoundType based on meta or other factors.
	 * IBlockAccess can provide meta too, it's just provided on its own for convenience.
	 * This is called in ClientEventHandler to override the sound type when the sound event is played.
	 * Return null if you want the sound to not be overridden and use the block's default SoundType instead
	 * 
	 * @author Roadhog360
	 */
	@Nullable
	public abstract Block.SoundType getStepSound(IBlockAccess world, int x, int y, int z, int meta);
	
	/**
	 * If new block sounds are turned off, the new function is not used.
	 */
	public abstract boolean requiresNewBlockSounds();
	
}
