package ganymedes01.etfuturum.api;

import net.minecraft.block.Block;

import java.util.ArrayList;

public class HoeRegistry {
	
	private static final ArrayList<Block> hoeBlocks = new ArrayList<>();
	
	public static void addToHoeArray(Block block) {
		hoeBlocks.add(block);
	}
	
	public static boolean hoeArrayHas(Block block) {
		return hoeBlocks.contains(block);
	}
	
	public static ArrayList<Block> getHoeArray() {
		return hoeBlocks;
	}
}
