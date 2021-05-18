package ganymedes01.etfuturum.world;

import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockNewStone;
import ganymedes01.etfuturum.blocks.BlockTuff;
import ganymedes01.etfuturum.blocks.ores.DeepslateMapping;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.world.generate.WorldGenDeepslateBlob;
import ganymedes01.etfuturum.world.generate.WorldGenTuffBlob;
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
		if (world.getWorldInfo().getGeneratorOptions().length() <= 0 && ConfigurationHandler.enableDeepslate && ConfigurationHandler.deepslateGenerationMode == 0 && world.provider.dimensionId == 0) {
			for (int x = chunkX * 16; x < (chunkX * 16) + 16; x++) {
				for (int z = chunkZ * 16; z < (chunkZ * 16) + 16; z++) {
					for (int y = 0; y <= ConfigurationHandler.deepslateMaxY; y++) {
						Block block = world.getBlock(x, y, z);
						if(y < ConfigurationHandler.deepslateMaxY - 4 || world.rand.nextInt(y > ConfigurationHandler.deepslateMaxY - 2 ? 3 : 2) == 0) {
							DeepslateMapping mapping = EtFuturum.deepslateOres.get(new DeepslateMapping(block, world.getBlockMetadata(x, y, z)));
							if(mapping != null) {
								world.setBlock(x, y, z, mapping.getOre(), mapping.getMeta(), 2);
							} else
							if((ConfigurationHandler.deepslateReplacesDirt && block.isReplaceableOreGen(world, x, y, z, Blocks.dirt))
									|| (block.isReplaceableOreGen(world, x, y, z, Blocks.stone) && (ConfigurationHandler.deepslateReplacesStones ? true : !(block instanceof BlockNewStone)))
									&& !(block instanceof BlockTuff)) {
								world.setBlock(x, y, z, ModBlocks.deepslate, 0, 2);
							}
						}
					}
				}
			}
		}
		if(world.getWorldInfo().getGeneratorOptions().length() <= 0 || world.getWorldInfo().getGeneratorOptions().contains("decoration") || world.provider.dimensionId != 0) {
			if(ConfigurationHandler.enableDeepslate && ConfigurationHandler.deepslateGenerationMode == 1 && world.provider.dimensionId == 0) {
				int x = chunkX * 16 + rand.nextInt(16);
				int z = chunkZ * 16 + rand.nextInt(16);
				new WorldGenDeepslateBlob(ConfigurationHandler.maxDeepslatePerCluster).generate(world, rand, x, rand.nextInt(ConfigurationHandler.deepslateMaxY), z);
			}
			if(ConfigurationHandler.enableTuff && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
				int x = chunkX * 16 + rand.nextInt(16);
				int z = chunkZ * 16 + rand.nextInt(16);
				new WorldGenTuffBlob(ConfigurationHandler.maxTuffPerCluster).generate(world, rand, x, rand.nextInt(ConfigurationHandler.deepslateMaxY), z);
			}
		}
	}
}
