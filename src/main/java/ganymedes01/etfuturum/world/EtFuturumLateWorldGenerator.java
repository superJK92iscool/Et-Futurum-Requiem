package ganymedes01.etfuturum.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.DeepslateOreRegistry;
import ganymedes01.etfuturum.core.utils.helpers.BlockAndMetadataMapping;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.helpers.CachedChunk;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class EtFuturumLateWorldGenerator extends EtFuturumWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if(world.getWorldInfo().getTerrainType() != WorldType.FLAT || world.provider.dimensionId != 0 || world.getWorldInfo().getGeneratorOptions().contains("decoration")) {
			if(ConfigBlocksItems.enableDeepslate && ConfigWorld.deepslateGenerationMode == 1 && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
				generateOre(deepslateBlobGen, world, rand, chunkX, chunkZ, 1, 6, ConfigWorld.deepslateMaxY);
			}
			if(ConfigBlocksItems.enableTuff && world.provider.dimensionId != -1 && world.provider.dimensionId != 1) {
				generateOre(tuffGen, world, rand, chunkX, chunkZ, 1, 6, ConfigWorld.deepslateMaxY);
			}
		}
		
		if(doesChunkSupportLayerDeepslate(world.getWorldInfo().getTerrainType(), world.provider.dimensionId)) {
			//Turn off recording here so the world gen isn't an infinite recursion loop of constantly checking the same blocks
			stopRecording = true;
			
			List<BlockPos> redo;
			for(Entry<CachedChunk, List<BlockPos>> set : redos.entrySet()) {
				redo = set.getValue();
				while(!redo.isEmpty()) {
					//Iterate this way to avoid ConcurrentModificationException
					BlockPos pos = redo.get(0);
					this.replaceBlockInChunk(set.getKey().getChunk(), pos.getX() & 15, pos.getZ() & 15, pos.getX(), pos.getY(), pos.getZ());
					redo.remove(0);
				}
			}

			Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
			doDeepslateGen(chunk);
			
			stopRecording = false;
		}
	}
	
	
	private void doDeepslateGen(Chunk chunk) {
			
			Block block;
			
			int worldX;
			int worldZ;
			final int chunkMultiplier = chunk.xPosition * 16;

			for (int y = 0; y <= ConfigWorld.deepslateMaxY; y++) {
				for (int x = 0; x < 16; x++) {
					for (int z = 0; z < 16; z++) {
						if(y < ConfigWorld.deepslateMaxY - 4 || y <= ConfigWorld.deepslateMaxY - chunk.worldObj.rand.nextInt(4)) {
							
							block = chunk.getBlock(x, y, z);
							
							worldX = x + chunkMultiplier;
							worldZ = z + chunkMultiplier;
							
							this.replaceBlockInChunk(chunk, block, x, z, worldX, y, worldZ);
						}
					}
				}
			}
	}
	
	private void replaceBlockInChunk(Chunk chunk, int x, int z, int worldX, int worldY, int worldZ) {
		this.replaceBlockInChunk(chunk, chunk.getBlock(x, worldY, z), x, z, worldX, worldY, worldZ);
	}
	
	private void replaceBlockInChunk(Chunk chunk, Block block, int x, int z, int worldX, int worldY, int worldZ) {
		if(block.getMaterial() != Material.air && block != ModBlocks.deepslate && block != ModBlocks.tuff && block != ModBlocks.cobbled_deepslate) {
			if((block.isReplaceableOreGen(chunk.worldObj, worldX , worldY, worldZ, Blocks.stone) && (ConfigWorld.deepslateReplacesStones || block != ModBlocks.stone))
					|| (ConfigWorld.deepslateReplacesDirt && block == Blocks.dirt)) {
				chunk.func_150807_a(x, worldY, z, ModBlocks.deepslate, 0);
			} else if(ConfigTweaks.deepslateReplacesCobblestone && block.isReplaceableOreGen(chunk.worldObj, worldX, worldY, worldZ, Blocks.cobblestone)) {
				chunk.func_150807_a(x, worldY, z, ModBlocks.cobbled_deepslate, 0);
			} else if(!DeepslateOreRegistry.getOreMap().isEmpty()) {
				BlockAndMetadataMapping mapping;
				if((mapping = DeepslateOreRegistry.getOre(block, chunk.getBlockMetadata(x, worldY, z))) != null) {
					chunk.func_150807_a(x, worldY, z, mapping.getBlock(), mapping.getMeta());
				}
			}
		}
	}
	
	private static final Map<CachedChunk, List<BlockPos>> redos = new HashMap<CachedChunk, List<BlockPos>>();
	public static boolean stopRecording;
	
	public static void clearRedos() {
		redos.clear();
	}
	
	public static List<BlockPos> getRedoList(World world, int x, int z, int dim) {
		CachedChunk chunk = new CachedChunk(x, z, dim);
		
		if(redos.get(chunk) == null) {
			redos.put(chunk, new ArrayList<BlockPos>());
		}
		
		if(chunk.getChunk() == null) {
			chunk.setChunk(world.getChunkFromChunkCoords(x, z));
		}
		
		return redos.get(chunk);
	}
	
	public static List<BlockPos> getRedoList(CachedChunk chunk) {
		
		if(redos.get(chunk) == null) {
			redos.put(chunk, new ArrayList<BlockPos>());
		}
		
		return redos.get(chunk);
	}
	
	private boolean doesChunkSupportLayerDeepslate(WorldType terrain, int dimId) {
		if(ConfigBlocksItems.enableDeepslate) {
			if (ConfigWorld.deepslateGenerationMode == 0 && ConfigWorld.deepslateMaxY > 0) {
				if(terrain != WorldType.FLAT && !ArrayUtils.contains(ConfigWorld.deepslateLayerDimensionBlacklist, dimId)) {
					return true;
				}
			}
		}
		return false;
	}

}
