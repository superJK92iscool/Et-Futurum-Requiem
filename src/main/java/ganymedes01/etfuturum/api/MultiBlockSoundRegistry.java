package ganymedes01.etfuturum.api;

import ganymedes01.etfuturum.api.mappings.MultiBlockSoundContainer;
import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.Map;

public class MultiBlockSoundRegistry {

	public static final Map<Block, MultiBlockSoundContainer> multiBlockSounds = new HashMap<>();

	public enum BlockSoundType {
		BREAK,
		PLACE,
		WALK,
		HIT
	}
}
