package ganymedes01.etfuturum.world.nether.biome.utils;

import ganymedes01.etfuturum.world.nether.biome.BiomeCrimsonForest;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

public class NetherBiomeManager {

	public static BiomeGenBase CrimsonForest;
	public static BiomeGenBase WarpedForest;
	public static BiomeGenBase SoulSandValley;
	public static BiomeGenBase BasaltDeltas;

	public static void init() {

//      if (BiomeConfiguration.CrimsonForestID != -1) {
		CrimsonForest = (new BiomeCrimsonForest(200)).setBiomeName("Crimson Forest");
//      }
//      if (BiomeConfiguration.WarpedForestID != -1) {
//          WarpedForest = (new DelirusCrux.Netherlicious.Biomes.WarpedForest(
//                  BiomeConfiguration.WarpedForestID)).setBiomeName("Warped Forest");
//      }
//      if (BiomeConfiguration.SoulSandValleyID != -1) {
//          SoulSandValley = (new DelirusCrux.Netherlicious.Biomes.SoulSandValley(
//                  BiomeConfiguration.SoulSandValleyID)).setBiomeName("Soul Sand Valley");
//      }
//      if (BiomeConfiguration.BasaltDeltasID != -1) {
//          BasaltDeltas = (new DelirusCrux.Netherlicious.Biomes.BasaltDeltas(
//                  BiomeConfiguration.BasaltDeltasID)).setBiomeName("Basalt Deltas");
//      } //Come back to

		BiomeGenBase.hell.topBlock = Blocks.netherrack;
		BiomeGenBase.hell.fillerBlock = Blocks.netherrack;

	}
}