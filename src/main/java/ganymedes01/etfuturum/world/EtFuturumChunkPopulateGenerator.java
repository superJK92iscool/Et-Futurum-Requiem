package ganymedes01.etfuturum.world;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.DeepslateOreRegistry;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class EtFuturumChunkPopulateGenerator {
	
	public static final EtFuturumChunkPopulateGenerator INSTANCE = new EtFuturumChunkPopulateGenerator();
	
	private void doDeepslateGen(Chunk chunk) {
		if(chunk.worldObj.getWorldInfo().getTerrainType() != WorldType.FLAT && !ArrayUtils.contains(ConfigWorld.deepslateLayerDimensionBlacklist, chunk.worldObj.provider.dimensionId)) {

			BlockAndMetadataMapping mapping;
			
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
							
							if((block.isReplaceableOreGen(chunk.worldObj, worldX , y, worldZ, Blocks.stone) && (ConfigWorld.deepslateReplacesStones || block != ModBlocks.stone))
									|| (ConfigWorld.deepslateReplacesDirt && block == Blocks.dirt)) {
								chunk.func_150807_a(x, y, z, ModBlocks.deepslate, 0);
							} else if(ConfigTweaks.deepslateReplacesCobblestone && block.isReplaceableOreGen(chunk.worldObj, x, y, z, Blocks.cobblestone)) {
								chunk.func_150807_a(x, y, z, ModBlocks.cobbled_deepslate, 0);
							} else if(ConfigBlocksItems.enableDeepslateOres){
								if((mapping = DeepslateOreRegistry.getOre(block, chunk.getBlockMetadata(x, y, z))) != null) {
									chunk.func_150807_a(x, y, z, mapping.getBlock(), mapping.getMeta());
								}
							}
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void populatedChunk(OreGenEvent.Post event) {
		if(ConfigBlocksItems.enableDeepslate) {
			if (ConfigWorld.deepslateGenerationMode == 0 && ConfigWorld.deepslateMaxY > 0) {
				doDeepslateGen(event.world.getChunkFromBlockCoords(event.worldX, event.worldZ));
			}
		}
	}
}
