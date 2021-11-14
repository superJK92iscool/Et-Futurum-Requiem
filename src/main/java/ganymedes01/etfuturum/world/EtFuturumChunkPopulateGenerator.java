package ganymedes01.etfuturum.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.DeepslateOreRegistry;
import ganymedes01.etfuturum.entities.ai.BlockPos;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class EtFuturumChunkPopulateGenerator {
	
	public static final EtFuturumChunkPopulateGenerator INSTANCE = new EtFuturumChunkPopulateGenerator();
	
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

//							if(block.isOpaqueCube()) { Debug code, used to confirm that it really was bordering chunks and not just stupid code
//								chunk.func_150807_a(x, y, z, ModBlocks.deepslate, 0);
//							}
						}
					}
				}
			}
	}
	
	private void replaceBlockInChunk(Chunk chunk, int x, int z, int worldX, int worldY, int worldZ) {
		this.replaceBlockInChunk(chunk, chunk.getBlock(x, worldY, z), x, z, worldX, worldY, worldZ);
	}
	
	private void replaceBlockInChunk(Chunk chunk, Block block, int x, int z, int worldX, int worldY, int worldZ) {
		if(block.isReplaceableOreGen(chunk.worldObj, worldX , worldY, worldZ, Blocks.stone)
				|| (ConfigWorld.deepslateReplacesDirt && block == Blocks.dirt)) {
			chunk.func_150807_a(x, worldY, z, ModBlocks.deepslate, 0);
		} else if(ConfigTweaks.deepslateReplacesCobblestone && block.isReplaceableOreGen(chunk.worldObj, x, worldY, z, Blocks.cobblestone)) {
			chunk.func_150807_a(x, worldY, z, ModBlocks.cobbled_deepslate, 0);
		} else if(block != Blocks.air && !DeepslateOreRegistry.getOreMap().isEmpty()) {
			BlockAndMetadataMapping mapping;
			if((mapping = DeepslateOreRegistry.getOre(block, chunk.getBlockMetadata(x, worldY, z))) != null) {
				chunk.func_150807_a(x, worldY, z, mapping.getBlock(), mapping.getMeta());
			}
		}
	}
	
	private final Map<Integer, List<BlockPos>> redos = new HashMap<Integer, List<BlockPos>>();
	public boolean stopRecording;
	
	public void clearRedos() {
		for(Integer key : redos.keySet()) {
			getRedoList(key).clear();
		}
	}
	
	public List<BlockPos> getRedoList(int dim) {
		if(redos.get(dim) == null) {
			redos.put(dim, new ArrayList<BlockPos>());
		}
		return redos.get(dim);
	}
	
	private boolean doesChunkSupportDeepslate(WorldType terrain, int dimId) {
		if(ConfigBlocksItems.enableDeepslate) {
			if (ConfigWorld.deepslateGenerationMode == 0 && ConfigWorld.deepslateMaxY > 0) {
				if(terrain != WorldType.FLAT && !ArrayUtils.contains(ConfigWorld.deepslateLayerDimensionBlacklist, dimId)) {
					return true;
				}
			}
		}
		return false;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void populatedChunk(DecorateBiomeEvent.Post event) {
		if(doesChunkSupportDeepslate(event.world.getWorldInfo().getTerrainType(), event.world.provider.dimensionId)) {
			Chunk chunk = event.world.getChunkFromChunkCoords(event.chunkX >> 4, event.chunkZ >> 4);
			doDeepslateGen(chunk);
			final List<BlockPos> redo = getRedoList(event.world.provider.dimensionId);
			if(!redo.isEmpty()) {
				//Turn off recording here so the world gen isn't an infinite recursion loop of constantly checking the same blocks
				stopRecording = true;
				Chunk newChunk = null;
				//Iterate this way to avoid ConcurrentModificationException
				for(int i = 0; i < redo.size(); i++) {
					BlockPos pos = redo.get(i);
					int chunkX = pos.getX() >> 4;
					int chunkZ = pos.getZ() >> 4;
					if((newChunk == null || newChunk.xPosition != chunkX >> 4 || newChunk.zPosition != chunkZ >> 4)) {
						newChunk = event.world.getChunkFromChunkCoords(chunkX, chunkZ);
					}
					this.replaceBlockInChunk(newChunk, pos.getX() & 15, pos.getZ() & 15, pos.getX(), pos.getY(), pos.getZ());
				}
				redo.clear();
			}
			stopRecording = false;
		}
	}
}
