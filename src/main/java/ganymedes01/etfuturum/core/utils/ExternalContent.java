package ganymedes01.etfuturum.core.utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class ExternalContent {

	public static boolean hasEnderlicious = Loader.isModLoaded("enderlicious");
	public static boolean hasIronChest = Loader.isModLoaded("IronChest");
	public static boolean hasNetherlicious = Loader.isModLoaded("netherlicious");
	public static boolean hasAetherLegacy = Loader.isModLoaded("aether_legacy");
	
	public static final Block enderlicious_end_rock = GameRegistry.findBlock("enderlicious", "EndRock");
	public static final Block hee_end_stone = GameRegistry.findBlock("HardcoreEnderExpansion", "end_stone_terrain");
	
	public static final Block enderlicious_sand = GameRegistry.findBlock("enderlicious", "EndSand");
	
	public static final Block netherlicious_bone_block = GameRegistry.findBlock("netherlicious", "BoneBlock");
	public static final Block utd_bone_block = GameRegistry.findBlock("uptodate", "bone_block");
	
	public static final Block netherlicious_basalt_bricks = GameRegistry.findBlock("netherlicious", "BasaltBricks");

}
