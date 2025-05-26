package ganymedes01.etfuturum.api;

import net.minecraft.block.Block;
import net.minecraftforge.oredict.OreDictionary;
import roadhog360.hogutils.api.hogtags.HogTagsHelper;

import java.util.ArrayList;

@Deprecated
public class HoeRegistry {

	@Deprecated
	public static void addToHoeArray(Block block) {
		HogTagsHelper.BlockTags.addTags(block, "minecraft:mineable/hoe");
	}

	@Deprecated
	public static boolean hoeArrayHas(Block block) {
		return HogTagsHelper.BlockTags.hasAnyTag(block, OreDictionary.WILDCARD_VALUE,"minecraft:mineable/hoe");
	}

	/// No longer works. TODO: Make it work maybe
	@Deprecated
	public static ArrayList<Block> getHoeArray() {
		return new ArrayList<>();
	}
}
