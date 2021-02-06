package ganymedes01.etfuturum.world;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.IWorldGenerator;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.ChorusFlower;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.world.generate.OceanMonument;
import ganymedes01.etfuturum.world.generate.WorldGenMinableNoAir;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;

public class EtFuturumWorldGenerator implements IWorldGenerator {

	private final List<WorldGenMinable> generators = new LinkedList<WorldGenMinable>();
	private final List<WorldGenFlowers> flowers = new LinkedList<WorldGenFlowers>();

	public EtFuturumWorldGenerator() {
		generators.add(new WorldGenMinable(ModBlocks.stone, 1, ConfigurationHandler.maxStonesPerCluster, Blocks.stone));
		generators.add(new WorldGenMinable(ModBlocks.stone, 3, ConfigurationHandler.maxStonesPerCluster, Blocks.stone));
		generators.add(new WorldGenMinable(ModBlocks.stone, 5, ConfigurationHandler.maxStonesPerCluster, Blocks.stone));
		flowers.add(new WorldGenFlowers(ModBlocks.lily_of_the_valley));
		flowers.add(new WorldGenFlowers(ModBlocks.cornflower));
		flowers.add(new WorldGenFlowers(ModBlocks.sweet_berry_bush));
		flowers.get(2).func_150550_a(ModBlocks.sweet_berry_bush, 3);
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (ConfigurationHandler.enableCoarseDirt && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
			//TODO Add checks so it doesn't run this code in biomes that don't generate coarse dirt
			for (int x = chunkX * 16; x < chunkX * 16 + 16; x++) {
				for (int z = chunkZ * 16; z < chunkZ * 16 + 16; z++) {
					for (int y = 0; y < world.getActualHeight(); y++) {
						if (world.getBlock(x, y, z) == Blocks.dirt && world.getBlockMetadata(x, y, z) == 1)
							world.setBlock(x, y, z, ModBlocks.coarse_dirt, 0, 2);
					}
				}
			}
		}

		if (ConfigurationHandler.enableStones && ConfigurationHandler.maxStonesPerCluster > 0 && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
			for (WorldGenMinable generator : generators) {
				for (int i = 0; i < 10; i++) {
					int x = chunkX * 16 + rand.nextInt(16);
					int y = rand.nextInt(80);
					int z = chunkZ * 16 + rand.nextInt(16);

					generator.generate(world, rand, x, y, z);
				}
			}
			
			if(ConfigurationHandler.enableCopper) {
				this.generateOre(ModBlocks.copper_ore, 0, world, rand, chunkX, chunkZ, 1, ConfigurationHandler.maxCopperPerCluster, 20, 4, 63, Blocks.stone);
			}
			
			//TODO Bone meal
			int x = chunkX * 16 + rand.nextInt(16);
			int z = chunkZ * 16 + rand.nextInt(16);
			BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
			Type[] biomeList = BiomeDictionary.getTypesForBiome(biome);
			if(ArrayUtils.contains(biomeList, Type.FOREST) && !ArrayUtils.contains(biomeList, Type.SNOWY)) {
				flowers.get(0).generate(world, rand, x, rand.nextInt(world.getHeightValue(x, z) * 2), z);
			}
			if(biome.biomeID == 132 || (ArrayUtils.contains(biomeList, Type.PLAINS) && !ArrayUtils.contains(biomeList, Type.SNOWY) && !ArrayUtils.contains(biomeList, Type.SAVANNA))) {
				flowers.get(1).generate(world, rand, x, rand.nextInt(world.getHeightValue(x, z) * 2), z);
			}
			if(ArrayUtils.contains(biomeList, Type.CONIFEROUS)) {
				flowers.get(2).generate(world, rand, x, rand.nextInt(world.getHeightValue(x, z) * 2), z);
			}
			
		}
		
		if(world.provider.dimensionId == -1) {
			if(ConfigurationHandler.enableMagmaBlock)
				this.generateOre(ModBlocks.magma_block, 0, world, rand, chunkX, chunkZ, 1, ConfigurationHandler.maxMagmaPerCluster, 4, 23, 37, Blocks.netherrack);

//			if(ConfigurationHandler.enableBlackstone)
//				this.generateOre(ModBlocks.blackstone, 0, world, rand, chunkX, chunkZ, 1, ConfigurationHandler.maxBlackstonePerCluster, 2, 5, 28, Blocks.netherrack);
			
			if(ConfigurationHandler.enableNetherGold)
				this.generateOre(ModBlocks.nether_gold_ore, 0, world, rand, chunkX, chunkZ, 1, ConfigurationHandler.maxNetherGoldPerCluster, 10, 10, 117, Blocks.netherrack);

			if(ConfigurationHandler.enableNetherite) {
				this.generateOre(ModBlocks.ancient_debris, 0, world, rand, chunkX, chunkZ, 1, ConfigurationHandler.smallDebrisMax, 2, 8, 119, Blocks.netherrack);
				this.generateOre(ModBlocks.ancient_debris, 0, world, rand, chunkX, chunkZ, 1, ConfigurationHandler.debrisMax, 3, 8, 22, Blocks.netherrack);
			}
		}

		if (ConfigurationHandler.enableOceanMonuments && ConfigurationHandler.enablePrismarine && world.provider.dimensionId != -1 && world.provider.dimensionId != 1)
			if (OceanMonument.canSpawnAt(world, chunkX, chunkZ)) {
				int x = chunkX * 16 + rand.nextInt(16);
				int y = 256;
				int z = chunkZ * 16 + rand.nextInt(16);
				for (; y > 0; y--)
					if (!world.getBlock(x, y, z).isAir(world, x, y, z))
						break;
				int monumentCeiling = y - (1 + rand.nextInt(3));
				OceanMonument.buildTemple(world, x, monumentCeiling - 22, z);
				return;
			}

		if (ConfigurationHandler.enableChorusFruit && world.provider.dimensionId == 1) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = 256;
			int z = chunkZ * 16 + rand.nextInt(16);
			for (; y > 0; y--)
				if (!world.getBlock(x, y, z).isAir(world, x, y, z))
					break;
			if (y > 0 && ChorusFlower.canPlantStay(world, x, y + 1, z))
				generateChorusPlant(world, x, y + 1, z, 0);
		}
	}

	public static void generateChorusPlant(World world, int x, int y, int z, int pass) {
		int height;
		for (height = 0; height < 4; height++) {
			if (!ChorusFlower.canPlantStay(world, x, y + height, z)) {
				world.setBlock(x, y + height, z, ModBlocks.chorus_flower, 5, 2);
				break;
			}
			world.setBlock(x, y + height, z, ModBlocks.chorus_plant);
		}
		if (height > 1) {
			world.setBlock(x, y + height, z, ModBlocks.chorus_plant);
			boolean grew = false;

			if (pass < 5) {
				ForgeDirection[] dirs = new ForgeDirection[] { ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.SOUTH };
				for (int j = 0; j < world.rand.nextInt(4); j++) {
					ForgeDirection dir = dirs[world.rand.nextInt(dirs.length)];
					int xx = x + dir.offsetX;
					int yy = y + height + dir.offsetY;
					int zz = z + dir.offsetZ;
					if (world.isAirBlock(xx, yy, zz) && ChorusFlower.isSpaceAroundFree(world, xx, yy, zz, dir.getOpposite())) {
						generateChorusPlant(world, xx, yy, zz, pass + 1);
						grew = true;
					}
				}
			}

			if (!grew)
				world.setBlock(x, y + height, z, ModBlocks.chorus_flower, 5, 2);
		}
	}
	
	public void generateOre(Block block, int meta, World world, Random random, int chunkX, int chunkZ, int minVeinSize, int maxVeinSize, float chance, int minY, int maxY, Block generateIn) {
		if(maxVeinSize == 0)
			return;
		
		if(maxVeinSize == 1) {
			int heightRange = maxY - minY;
			int xRand = chunkX * 16 + random.nextInt(16);
			int yRand = random.nextInt(heightRange) + minY;
			int zRand = chunkZ * 16 + random.nextInt(16);
			if(world.getBlock(xRand, yRand, zRand).isReplaceableOreGen(world, xRand, yRand, zRand, generateIn))
				world.setBlock(xRand, yRand, zRand, block, meta, 3);
			return;
		}
		
		int veinSize = minVeinSize + random.nextInt(maxVeinSize - minVeinSize);
		int heightRange = maxY - minY;
			WorldGenerator gen;
			if(block == ModBlocks.ancient_debris)
				gen = new WorldGenMinableNoAir(block, meta, veinSize, generateIn);
			else
				gen = new WorldGenMinable(block, meta, veinSize, generateIn);
			for(int i = 0; i < chance; i++) {
				int xRand = chunkX * 16 + random.nextInt(16);
				int yRand = random.nextInt(heightRange) + minY;
				int zRand = chunkZ * 16 + random.nextInt(16);
				
				gen.generate(world, random, xRand, yRand, zRand);
		}
	}
}