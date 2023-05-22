package ganymedes01.etfuturum.world;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ganymedes01.etfuturum.ModBlocks;
import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.IWorldGenerator;
import ganymedes01.etfuturum.blocks.BlockChorusFlower;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.world.end.dimension.EndWorldProvider;
import ganymedes01.etfuturum.world.generate.WorldGenDeepslateLayerBlob;
import ganymedes01.etfuturum.world.generate.WorldGenMinableCustom;
import ganymedes01.etfuturum.world.generate.feature.WorldGenAmethystGeode;
import ganymedes01.etfuturum.world.generate.feature.WorldGenFossil;
import ganymedes01.etfuturum.world.structure.MapGenMesaMineshaft;
import ganymedes01.etfuturum.world.structure.OceanMonument;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import thaumcraft.common.config.ConfigBlocks;

public class EtFuturumWorldGenerator implements IWorldGenerator {

	public static final EtFuturumWorldGenerator INSTANCE = new EtFuturumWorldGenerator();

	protected final List<WorldGenMinable> stoneGen = new LinkedList<WorldGenMinable>();
	protected final List<WorldGenFlowers> flowers = new LinkedList<WorldGenFlowers>();
	
	protected final WorldGenMinable copperGen = new WorldGenMinable(ModBlocks.COPPER_ORE.get(), ConfigWorld.maxCopperPerCluster);
	
	protected final WorldGenMinable magmaGen = new WorldGenMinable(ModBlocks.MAGMA.get(), ConfigWorld.maxMagmaPerCluster, Blocks.netherrack);
	protected final WorldGenMinable netherGoldGen = new WorldGenMinable(ModBlocks.NETHER_GOLD_ORE.get(), ConfigWorld.maxNetherGoldPerCluster, Blocks.netherrack);
	protected final WorldGenMinable debrisGen = new WorldGenMinableCustom(ModBlocks.ANCIENT_DEBRIS.get(), ConfigWorld.debrisMax, Blocks.netherrack);
	protected final WorldGenMinable smallDebrisGen = new WorldGenMinableCustom(ModBlocks.ANCIENT_DEBRIS.get(), ConfigWorld.smallDebrisMax, Blocks.netherrack);
	protected final WorldGenMinable mesaGoldGen = new WorldGenMinable(Blocks.gold_ore, 8);
	
	protected final WorldGenMinable deepslateBlobGen = new WorldGenDeepslateLayerBlob(ConfigWorld.maxDeepslatePerCluster, false);
	protected final WorldGenMinable tuffGen = new WorldGenDeepslateLayerBlob(ConfigWorld.maxTuffPerCluster, true);
	protected WorldGenerator amethystGen;
	protected WorldGenerator fossilGen;

	protected EtFuturumWorldGenerator() {
		stoneGen.add(new WorldGenMinableCustom(ModBlocks.STONE.get(), 1, ConfigWorld.maxStonesPerCluster, Blocks.stone));
		stoneGen.add(new WorldGenMinableCustom(ModBlocks.STONE.get(), 3, ConfigWorld.maxStonesPerCluster, Blocks.stone));
		stoneGen.add(new WorldGenMinableCustom(ModBlocks.STONE.get(), 5, ConfigWorld.maxStonesPerCluster, Blocks.stone));
		flowers.add(new WorldGenFlowers(ModBlocks.LILY_OF_THE_VALLEY.get()));
		flowers.add(new WorldGenFlowers(ModBlocks.CORNFLOWER.get()));
		flowers.add(new WorldGenFlowers(ModBlocks.SWEET_BERRY_BUSH.get()));
		flowers.get(2).func_150550_a(ModBlocks.SWEET_BERRY_BUSH.get(), 3);
	}

	public void postInit() {
		if(ConfigWorld.enableAmethystGeodes && ModBlocks.AMETHYST_BLOCK.isEnabled() && ModBlocks.AMETHYST_CLUSTER_1.isEnabled() && ModBlocks.AMETHYST_CLUSTER_2.isEnabled()
				&& ModBlocks.BUDDING_AMETHYST.isEnabled() && ConfigWorld.amethystOuterBlock != null && ConfigWorld.amethystMiddleBlock != null) {
			amethystGen = new WorldGenAmethystGeode(ConfigWorld.amethystOuterBlock, ConfigWorld.amethystMiddleBlock);
		}
		if(ConfigWorld.enableFossils && ConfigWorld.fossilBlock != null) {
			fossilGen = new WorldGenFossil(ConfigWorld.fossilBlock);
		}
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.getWorldInfo().getTerrainType() != WorldType.FLAT || world.getWorldInfo().getGeneratorOptions().contains("decoration") || world.provider.dimensionId != 0) {
			int x;
			int z;
			if (ModBlocks.STONE.isEnabled() && ConfigWorld.maxStonesPerCluster > 0 && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
				for(WorldGenMinable stoneGenerator : stoneGen) {
					for(int i = 0; i < 10; i++) {
						generateOre(stoneGenerator, world, rand, chunkX, chunkZ, 1, 0, 80);
					}
				}
			}
			
			if(amethystGen != null && ArrayUtils.contains(ConfigWorld.amethystDimensionBlacklist, world.provider.dimensionId) == ConfigWorld.amethystDimensionBlacklistAsWhitelist) {
				x = chunkX * 16 + rand.nextInt(16) + 8;
				z = chunkZ * 16 + rand.nextInt(16) + 8;
				if(ConfigWorld.amethystRarity == 1 || rand.nextInt(ConfigWorld.amethystRarity) == 0) {
					amethystGen.generate(world, rand, x, MathHelper.getRandomIntegerInRange(rand, 6, ConfigWorld.amethystMaxY), z);
				}
			}
			
			if(ModBlocks.COPPER_ORE.isEnabled()) {
				generateOre(copperGen, world, rand, chunkX, chunkZ, 8, 4, 80);
			}

			if(ConfigWorld.enableExtraMesaGold) {
				if(ArrayUtils.contains(BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoords(chunkX * 16, chunkZ * 16)), Type.MESA)) {
					generateOre(mesaGoldGen, world, rand, chunkX, chunkZ, 20, 32, 80);
				}
			}

			BiomeGenBase biome;
			Type[] biomeList;
			if(ModBlocks.LILY_OF_THE_VALLEY.isEnabled()) {
				x = chunkX * 16 + rand.nextInt(16) + 8;
				z = chunkZ * 16 + rand.nextInt(16) + 8;
				biome = world.getBiomeGenForCoords(x, z);
				biomeList = BiomeDictionary.getTypesForBiome(biome);
				if(ArrayUtils.contains(biomeList, Type.FOREST) && !ArrayUtils.contains(biomeList, Type.SNOWY) && world.getHeightValue(x, z) > 0) {
					flowers.get(0).generate(world, rand, x, nextHeightInt(rand, world.getHeightValue(x, z) * 2), z);
				}
			}

			if(ModBlocks.CORNFLOWER.isEnabled()) {
				x = chunkX * 16 + rand.nextInt(16) + 8;
				z = chunkZ * 16 + rand.nextInt(16) + 8;
				biome = world.getBiomeGenForCoords(x, z);
				biomeList = BiomeDictionary.getTypesForBiome(biome);
				if (biome.biomeID == 132 || (ArrayUtils.contains(biomeList, Type.PLAINS) && !ArrayUtils.contains(biomeList, Type.SNOWY) && !ArrayUtils.contains(biomeList, Type.SAVANNA)) && world.getHeightValue(x, z) > 0) {
					flowers.get(1).generate(world, rand, x, nextHeightInt(rand, world.getHeightValue(x, z) * 2), z);
				}
			}

			if(ModBlocks.SWEET_BERRY_BUSH.isEnabled()) {
				x = chunkX * 16 + rand.nextInt(16) + 8;
				z = chunkZ * 16 + rand.nextInt(16) + 8;
				biome = world.getBiomeGenForCoords(x, z);
				biomeList = BiomeDictionary.getTypesForBiome(biome);
				if(ArrayUtils.contains(biomeList, Type.CONIFEROUS) && world.getHeightValue(x, z) > 0) {
					flowers.get(2).generate(world, rand, x, nextHeightInt(rand, world.getHeightValue(x, z) * 2), z);
				}
			}

			if(fossilGen != null && ArrayUtils.contains(ConfigWorld.fossilDimensionBlacklist, world.provider.dimensionId) == ConfigWorld.fossilDimensionBlacklistAsWhitelist) {
				x = chunkX * 16 + rand.nextInt(16) + 8;
				z = chunkZ * 16 + rand.nextInt(16) + 8;
				biome = world.getBiomeGenForCoords(x, z);
				biomeList = BiomeDictionary.getTypesForBiome(biome);
				if(rand.nextInt(64) == 0 && (ArrayUtils.contains(biomeList, Type.SANDY) && ArrayUtils.contains(biomeList, Type.DRY) || ArrayUtils.contains(biomeList, Type.SWAMP))) {
					fossilGen.generate(world, rand, x, rand.nextInt(9) + 41, z);
				}
			}
		}
		
		if(world.provider.dimensionId == -1) {
			if(ModBlocks.MAGMA.isEnabled()) {
				this.generateOre(magmaGen, world, rand, chunkX, chunkZ, 4, 23, 37);
			}

//          if(ConfigurationHandler.enableBlackstone)
//              this.generateOre(ModBlocks.blackstone, 0, world, rand, chunkX, chunkZ, 1, ConfigurationHandler.maxBlackstonePerCluster, 2, 5, 28, Blocks.netherrack);
			
			if(ModBlocks.NETHER_GOLD_ORE.isEnabled()) {
				this.generateOre(netherGoldGen, world, rand, chunkX, chunkZ, 10, 10, 117);
			}

			if(ModBlocks.ANCIENT_DEBRIS.isEnabled()) {
				this.generateOre(debrisGen, world, rand, chunkX, chunkZ, 1, 8, 22);
				this.generateOre(smallDebrisGen, world, rand, chunkX, chunkZ, 1, 8, 119);
			}
		}

		if (ConfigWorld.enableOceanMonuments && ModBlocks.PRISMARINE_BLOCK.isEnabled() && ModBlocks.SEA_LANTERN.isEnabled() && world.provider.dimensionId != -1 && world.provider.dimensionId != 1)
			if (OceanMonument.canSpawnAt(world, chunkX, chunkZ)) {
				int x = chunkX * 16 + rand.nextInt(16) + 8;
				int y = 256;
				int z = chunkZ * 16 + rand.nextInt(16) + 8;
				for (; y > 0; y--)
					if (!world.isAirBlock(x, y, z))
						break;
				int monumentCeiling = y - (1 + rand.nextInt(3));
				OceanMonument.buildTemple(world, x, monumentCeiling - 22, z);
				return;
			}

		if (ModBlocks.CHORUS_PLANT.isEnabled() && ModBlocks.CHORUS_FLOWER.isEnabled() && !(world.provider instanceof EndWorldProvider) && world.provider.dimensionId == 1) {
			int x = chunkX * 16 + rand.nextInt(16) + 8;
			int y = 256;
			int z = chunkZ * 16 + rand.nextInt(16) + 8;
			for (; y > 0; y--) {
				if (!world.getBlock(x, y, z).isAir(world, x, y, z)) {
					if (BlockChorusFlower.canPlantStay(world, x, y + 1, z)) {
						BlockChorusFlower.generatePlant(world, x, y + 1, z, rand, 8);
						break;
					}
				}
			}
		}
	}
	
	public void generateSingleOre(Block block, int meta, World world, Random random, int chunkX, int chunkZ, float chance, int minY, int maxY, Block generateIn) {
		if(maxY <= 0 || minY < 0 || maxY < minY || chance <= 0)
			return;
		
		for(int i = 0; i < (chance < 1 ? 1 : chance); i++) {
			if(chance > 1 || random.nextFloat() < chance) {
				int heightRange = maxY - minY;
				int xRand = chunkX * 16 + random.nextInt(16);
				int yRand = random.nextInt(heightRange) + minY;
				int zRand = chunkZ * 16 + random.nextInt(16);
				if(world.getBlock(xRand, yRand, zRand).isReplaceableOreGen(world, xRand, yRand, zRand, generateIn))
					world.setBlock(xRand, yRand, zRand, block, meta, 3);
			}
		}
	}
	
	public void generateOre(WorldGenMinable gen, World world, Random random, int chunkX, int chunkZ, float chance, int minY, int maxY) {
		if(maxY <= 0 || minY < 0 || maxY < minY || gen.numberOfBlocks <= 0 || chance <= 0)
			return;
		
		int heightRange = maxY - minY;
		
		for(int i = 0; i < (chance < 1 ? 1 : (int)chance); i++) {
			if(chance >= 1 || random.nextFloat() < chance) {
				int xRand = chunkX * 16 + random.nextInt(16);
				int yRand = random.nextInt(heightRange) + minY;
				int zRand = chunkZ * 16 + random.nextInt(16);
				
				gen.generate(world, random, xRand, yRand, zRand);
			}
		}
	}

	protected int nextHeightInt(Random rand, int i) {
		if (i <= 1)
			return 1;
		return rand.nextInt(i);
	}
}