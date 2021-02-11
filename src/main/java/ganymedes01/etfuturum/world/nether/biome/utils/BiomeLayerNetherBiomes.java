package ganymedes01.etfuturum.world.nether.biome.utils;

import java.util.ArrayList;
import java.util.List;

import ganymedes01.etfuturum.world.nether.dimension.BiomeLayerNether;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

public class BiomeLayerNetherBiomes extends BiomeLayerNether {

	public List<BiomeEntry> netherBiomes = new ArrayList();

	public BiomeLayerNetherBiomes(long par1, BiomeLayerNether par3GenLayer) {

		super(par1);
		parent = par3GenLayer;

		this.netherBiomes.add(new BiomeEntry(BiomeGenBase.hell, 30)); // Make weight configurable
//
//		if (BiomeConfiguration.CrimsonForestID != -1) {
//			this.netherBiomes.add(
//					new BiomeEntry(NetherBiomeManager.CrimsonForest, BiomeConfiguration.CrimsonForestWeight));
//		}
//
//		if (BiomeConfiguration.WarpedForestID != -1) {
//			this.netherBiomes.add(
//					new BiomeEntry(NetherBiomeManager.WarpedForest, BiomeConfiguration.WarpedForestWeight));
//		}
//
//		if (BiomeConfiguration.BasaltDeltasID != -1) {
//			this.netherBiomes.add(
//					new BiomeEntry(NetherBiomeManager.BasaltDeltas, BiomeConfiguration.BasaltDeltasWeight));
//		}
//
//		if (BiomeConfiguration.SoulSandValleyID != -1) {
//			this.netherBiomes.add(
//					new BiomeEntry(NetherBiomeManager.SoulSandValley, BiomeConfiguration.SoulSandValleyWeight));
//		}
//
//		if (Loader.isModLoaded("BiomesOPlenty")) {
//
//			if (BiomeConfiguration.BoneyardGen) {
//				if (Netherlicious.Boneyard != -1) {
//					this.netherBiomes.add(new BiomeEntry(BOPCBiomes.boneyard,
//							BiomeConfiguration.BoneyardWeight));
//				}
//			}
//
//			if (BiomeConfiguration.CorruptedSandsGen) {
//				if (Netherlicious.CorruptedSands != -1) {
//					this.netherBiomes.add(new BiomeEntry(BOPCBiomes.corruptedSands,
//							BiomeConfiguration.CorruptedSandsWeight));
//				}
//			}
//
//			if (BiomeConfiguration.PhantasmagoricInfernoGen) {
//				if (Netherlicious.PhantasmagoricInferno != -1) {
//					this.netherBiomes.add(new BiomeEntry(BOPCBiomes.phantasmagoricInferno,
//							BiomeConfiguration.PhantasmagoricInfernoWeight));
//				}
//			}
//
//			if (BiomeConfiguration.PolarChasmGen) {
//				if (Netherlicious.PolarChasm != -1) {
//					this.netherBiomes.add(new BiomeEntry(BOPCBiomes.polarChasm,
//							BiomeConfiguration.PolarChasmWeight));
//				}
//			}
//
//			if (BiomeConfiguration.UndergardenGen) {
//				if (Netherlicious.Undergarden != -1) {
//					this.netherBiomes.add(new BiomeEntry(BOPCBiomes.undergarden,
//							BiomeConfiguration.UndergardenWeight));
//				}
//			}
//
//			if (BiomeConfiguration.VisceralHeapGen) {
//				if (Netherlicious.VisceralHeap != -1) {
//					this.netherBiomes.add(new BiomeEntry(BOPCBiomes.visceralHeap,
//							BiomeConfiguration.VisceralHeapWeight));
//				}
//			}
//
//		}

	}

	@Override
	public int[] getInts(int par1, int par2, int par3, int par4) {

		int[] var5 = this.parent.getInts(par1, par2, par3, par4);
		int[] var6 = IntCache.getIntCache(par3 * par4);

		for (int var7 = 0; var7 < par4; ++var7) {

			for (int var8 = 0; var8 < par3; ++var8) {

				this.initChunkSeed((long) (var8 + par1), (long) (var7 + par2));
				int var9 = var5[var8 + var7 * par3];

				var6[var8 + var7 * par3] = getWeightedBiomeFromList(netherBiomes);
			}
		}
		return var6;
	}

	private int getWeightedBiomeFromList(List<BiomeEntry> biomeList) {

		return ((BiomeEntry) WeightedRandom.getItem(biomeList,
				(int) this.nextInt(WeightedRandom.getTotalWeight(biomeList)))).biome.biomeID;
	}

}
