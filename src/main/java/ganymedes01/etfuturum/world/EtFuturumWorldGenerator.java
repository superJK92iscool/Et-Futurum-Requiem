package ganymedes01.etfuturum.world;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.IWorldGenerator;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockChorusFlower;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.world.end.dimension.EndWorldProvider;
import ganymedes01.etfuturum.world.generate.OceanMonument;
import ganymedes01.etfuturum.world.generate.WorldGenMinableNoAir;
import ganymedes01.etfuturum.world.generate.feature.WorldGenFossil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.util.ForgeDirection;

public class EtFuturumWorldGenerator implements IWorldGenerator {

	private final List<WorldGenMinable> stoneGen = new LinkedList<WorldGenMinable>();
	private final List<WorldGenFlowers> flowers = new LinkedList<WorldGenFlowers>();

	public EtFuturumWorldGenerator() {
		stoneGen.add(new WorldGenMinable(ModBlocks.stone, 1, ConfigWorld.maxStonesPerCluster, Blocks.stone));
		stoneGen.add(new WorldGenMinable(ModBlocks.stone, 3, ConfigWorld.maxStonesPerCluster, Blocks.stone));
		stoneGen.add(new WorldGenMinable(ModBlocks.stone, 5, ConfigWorld.maxStonesPerCluster, Blocks.stone));
		flowers.add(new WorldGenFlowers(ModBlocks.lily_of_the_valley));
		flowers.add(new WorldGenFlowers(ModBlocks.cornflower));
		flowers.add(new WorldGenFlowers(ModBlocks.sweet_berry_bush));
		flowers.get(2).func_150550_a(ModBlocks.sweet_berry_bush, 3);
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.getWorldInfo().getTerrainType() != WorldType.FLAT || world.getWorldInfo().getGeneratorOptions().contains("decoration") || world.provider.dimensionId != 0) {

			if (ConfigBlocksItems.enableStones && ConfigWorld.maxStonesPerCluster > 0 && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
				for (int j = 0; j < stoneGen.size(); j++) {
					WorldGenMinable generator = stoneGen.get(j);
					for (int i = 0; i < 10; i++) {
						int x = chunkX * 16 + rand.nextInt(16);
						int y = rand.nextInt(80);
						int z = chunkZ * 16 + rand.nextInt(16);

						generator.generate(world, rand, x, y, z);
					}
				}
			}
			
			if(ConfigBlocksItems.enableCopper) {
				this.generateOre(ModBlocks.copper_ore, 0, world, rand, chunkX, chunkZ, 1, ConfigWorld.maxCopperPerCluster, 20, 4, 63, Blocks.stone);
			}
			
			{
				int x;
				int z;
				BiomeGenBase biome;
				Type[] biomeList;
				//TODO Bone meal
				if(ConfigBlocksItems.enableLilyOfTheValley) {
					x = chunkX * 16 + rand.nextInt(16);
					z = chunkZ * 16 + rand.nextInt(16);
					biome = world.getBiomeGenForCoords(x, z);
					biomeList = BiomeDictionary.getTypesForBiome(biome);
					if(ArrayUtils.contains(biomeList, Type.FOREST) && !ArrayUtils.contains(biomeList, Type.SNOWY) && world.getHeightValue(x, z) > 0) {
						flowers.get(0).generate(world, rand, x, rand.nextInt(world.getHeightValue(x, z) * 2), z);
					}
				}

				if(ConfigBlocksItems.enableCornflower) {
					x = chunkX * 16 + rand.nextInt(16);
					z = chunkZ * 16 + rand.nextInt(16);
					biome = world.getBiomeGenForCoords(x, z);
					biomeList = BiomeDictionary.getTypesForBiome(biome);
					if (biome.biomeID == 132 || (ArrayUtils.contains(biomeList, Type.PLAINS) && !ArrayUtils.contains(biomeList, Type.SNOWY) && !ArrayUtils.contains(biomeList, Type.SAVANNA)) && world.getHeightValue(x, z) > 0) {
						flowers.get(1).generate(world, rand, x, rand.nextInt(world.getHeightValue(x, z) * 2), z);
					}
				}
				
				if(ConfigBlocksItems.enableSweetBerryBushes) {
					x = chunkX * 16 + rand.nextInt(16);
					z = chunkZ * 16 + rand.nextInt(16);
					biome = world.getBiomeGenForCoords(x, z);
					biomeList = BiomeDictionary.getTypesForBiome(biome);
					if(ArrayUtils.contains(biomeList, Type.CONIFEROUS) && world.getHeightValue(x, z) > 0) {
						flowers.get(2).generate(world, rand, x, rand.nextInt(world.getHeightValue(x, z) * 2), z);
					}
				}
				
				if(!ArrayUtils.contains(ConfigWorld.fossilDimensionBlacklist, world.provider.dimensionId)) {
					x = chunkX * 16 + rand.nextInt(16);
					z = chunkZ * 16 + rand.nextInt(16);
					biome = world.getBiomeGenForCoords(x, z);
					biomeList = BiomeDictionary.getTypesForBiome(biome);
					if(ConfigWorld.enableFossils && rand.nextInt(64) == 0 && (ArrayUtils.contains(biomeList, Type.SANDY) && ArrayUtils.contains(biomeList, Type.DRY) || ArrayUtils.contains(biomeList, Type.SWAMP))) {
						new WorldGenFossil().generate(world, rand, x, rand.nextInt(9) + 41, z);
					}
				}
			}
		}
		
		if(world.provider.dimensionId == -1) {
			if(ConfigBlocksItems.enableMagmaBlock)
				this.generateOre(ModBlocks.magma_block, 0, world, rand, chunkX, chunkZ, 1, ConfigWorld.maxMagmaPerCluster, 4, 23, 37, Blocks.netherrack);

//          if(ConfigurationHandler.enableBlackstone)
//              this.generateOre(ModBlocks.blackstone, 0, world, rand, chunkX, chunkZ, 1, ConfigurationHandler.maxBlackstonePerCluster, 2, 5, 28, Blocks.netherrack);
			
			if(ConfigBlocksItems.enableNetherGold)
				this.generateOre(ModBlocks.nether_gold_ore, 0, world, rand, chunkX, chunkZ, 1, ConfigWorld.maxNetherGoldPerCluster, 10, 10, 117, Blocks.netherrack);

			if(ConfigBlocksItems.enableNetherite) {
				this.generateOre(ModBlocks.ancient_debris, 0, world, rand, chunkX, chunkZ, 1, ConfigWorld.smallDebrisMax, 2, 8, 119, Blocks.netherrack);
				this.generateOre(ModBlocks.ancient_debris, 0, world, rand, chunkX, chunkZ, 1, ConfigWorld.debrisMax, 3, 8, 22, Blocks.netherrack);
			}
		}

		if (ConfigWorld.enableOceanMonuments && ConfigBlocksItems.enablePrismarine && world.provider.dimensionId != -1 && world.provider.dimensionId != 1)
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

		if (ConfigBlocksItems.enableChorusFruit && !(world.provider instanceof EndWorldProvider) && world.provider.dimensionId == 1) {
			int x = chunkX * 16 + rand.nextInt(16);
			int y = 256;
			int z = chunkZ * 16 + rand.nextInt(16);
			for (; y > 0; y--)
				if (!world.getBlock(x, y, z).isAir(world, x, y, z))
					break;
			if (y > 0 && BlockChorusFlower.canPlantStay(world, x, y + 1, z))
				BlockChorusFlower.generatePlant(world, x, y + 1, z, rand, 8);
		}
	}
	
	public void generateOre(Block block, int meta, World world, Random random, int chunkX, int chunkZ, int minVeinSize, int maxVeinSize, float chance, int minY, int maxY, Block generateIn) {
		if(maxVeinSize <= 0)
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