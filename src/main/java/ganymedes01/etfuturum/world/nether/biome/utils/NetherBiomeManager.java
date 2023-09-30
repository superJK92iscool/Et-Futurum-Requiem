package ganymedes01.etfuturum.world.nether.biome.utils;

import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.world.nether.biome.BiomeCrimsonForest;
import ganymedes01.etfuturum.world.nether.biome.BiomeSoulSandValley;
import ganymedes01.etfuturum.world.nether.biome.BiomeWarpedForest;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class NetherBiomeManager {

	public static BiomeGenBase crimsonForest;
	public static BiomeGenBase warpedForest;
	public static BiomeGenBase soulSandValley;
	public static BiomeGenBase basaltDeltas;

	public static void init() {
		if (ConfigWorld.crimsonForestID != -1 && ConfigBlocksItems.enableCrimsonBlocks) {
			crimsonForest = new BiomeCrimsonForest(ConfigWorld.crimsonForestID).setBiomeName("Crimson Forest");
		}
		if (ConfigWorld.warpedForestID != -1 && ConfigBlocksItems.enableWarpedBlocks) {
			warpedForest = new BiomeWarpedForest(ConfigWorld.warpedForestID).setBiomeName("Warped Forest");
		}
		if (ConfigWorld.soulSandValleyID != -1) {
			soulSandValley = new BiomeSoulSandValley(ConfigWorld.soulSandValleyID).setBiomeName("Soul Sand Valley");
		}
		if (ConfigWorld.basaltDeltasID != -1 && ConfigBlocksItems.enableBasalt) {
//			basaltDeltas = (new DelirusCrux.Netherlicious.Biomes.BasaltDeltas(BiomeConfiguration.BasaltDeltasID)).setBiomeName("Basalt Deltas");
		}

		BiomeGenBase.hell.topBlock = Blocks.netherrack;
		BiomeGenBase.hell.fillerBlock = Blocks.netherrack;
		BiomeGenBase.hell.biomeName = "Nether Wastes";
		BiomeGenBase.hell.field_76754_C = 0;
		//Sets filler block meta to 0. Vanilla sets it to a ridiculous value for some reason.
		//This value seems to be unused in vanilla as well.
	}
}