package ganymedes01.etfuturum.world;

import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockTuff;
import ganymedes01.etfuturum.blocks.Stone;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.world.generate.WorldGenDeepslateLayerMinable;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class EtFuturumLateWorldGenerator extends EtFuturumWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (ConfigurationHandler.enableCoarseDirt && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
			//TODO Add checks so it doesn't run this code in biomes that don't generate coarse dirt
			for (int x = chunkX * 16; x < (chunkX * 16) + 16; x++) {
				for (int z = chunkZ * 16; z < (chunkZ * 16) + 16; z++) {
					for (int y = 0; y < world.getActualHeight(); y++) {
						if (world.getBlock(x, y, z) == Blocks.dirt && world.getBlockMetadata(x, y, z) == 1) {
							world.setBlock(x, y, z, ModBlocks.coarse_dirt, 0, 2);
						}
					}
				}
			}
		}
		if (ConfigurationHandler.enableDeepslate && ConfigurationHandler.deepslateGenerationMode == 0 && world.provider.dimensionId == 0) {
			for (int x = chunkX * 16; x < (chunkX * 16) + 16; x++) {
				for (int z = chunkZ * 16; z < (chunkZ * 16) + 16; z++) {
					for (int y = 0; y <= ConfigurationHandler.deepslateMaxY; y++) {
						Block block = world.getBlock(x, y, z);
						if(y < ConfigurationHandler.deepslateMaxY - 4 || world.rand.nextInt(y > ConfigurationHandler.deepslateMaxY - 2 ? 3 : 2) == 0) {
							if(EtFuturum.deepslateOres.containsKey(block)) {
								world.setBlock(x, y, z, EtFuturum.deepslateOres.get(block));
							} else
							if((ConfigurationHandler.deepslateReplacesDirt && block.isReplaceableOreGen(world, x, y, z, Blocks.dirt))
									|| (block.isReplaceableOreGen(world, x, y, z, Blocks.stone) && (ConfigurationHandler.deepslateReplacesStones ? true : !(block instanceof Stone)))
									&& !(block instanceof BlockTuff)) {
								world.setBlock(x, y, z, ModBlocks.deepslate, 0, 2);
							}
						}
					}
				}
			}
		}
		if(ConfigurationHandler.enableDeepslate && ConfigurationHandler.deepslateGenerationMode == 1 && world.provider.dimensionId == 0) {
			int x = chunkX * 16 + rand.nextInt(16);
			int z = chunkZ * 16 + rand.nextInt(16);
			new WorldGenDeepslateLayerMinable(ModBlocks.deepslate, ConfigurationHandler.maxDeepslatePerCluster).generate(world, rand, x, rand.nextInt(ConfigurationHandler.deepslateMaxY), z);
		}
		if(ConfigurationHandler.enableTuff && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
			int x = chunkX * 16 + rand.nextInt(16);
			int z = chunkZ * 16 + rand.nextInt(16);
			new WorldGenDeepslateLayerMinable(ModBlocks.tuff, ConfigurationHandler.maxTuffPerCluster).generate(world, rand, x, rand.nextInt(ConfigurationHandler.deepslateMaxY), z);
		}
	}
}
