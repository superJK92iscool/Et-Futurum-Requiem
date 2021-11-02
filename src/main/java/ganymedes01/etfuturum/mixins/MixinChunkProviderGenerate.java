package ganymedes01.etfuturum.mixins;

import java.util.Random;

import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderGenerate;

@Mixin(value = ChunkProviderGenerate.class)
public class MixinChunkProviderGenerate {
	
	@Shadow
	public World worldObj;
	@Shadow
	public Random rand;
    
    @Inject(method = "provideChunk", at = @At(value = "NEW", target = "net/minecraft/world/chunk/Chunk"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void generateDeepslate(int x, int z, CallbackInfoReturnable<Chunk> info, Block[] ablock) {
    	if(worldObj.getWorldInfo().getTerrainType() != WorldType.FLAT && !ArrayUtils.contains(ConfigWorld.deepslateLayerDimensionBlacklist, worldObj.provider.dimensionId)) {
        	int x2, z2, array;
        	for(int x1 = 0; x1 < 16; x1++) {
            	for(int z1 = 0; z1 < 16; z1++) {
                	for(int y = 0; y <= ConfigWorld.deepslateMaxY; y++) {
                		x2 = (x * 16) + x1;
                		z2 = (z * 16) + z1;
                		
                        array = (z1 * 16 + x1) * (ablock.length / 256) + y;

                        if(ablock[array] != null && ablock[array] != Blocks.air && shouldBeDeepslate(y, rand)) {
                        	if(ablock[array].isReplaceableOreGen(worldObj, x2, y, z2, Blocks.stone)) {
                            	ablock[array] = ModBlocks.deepslate;
                        	} else if(ConfigTweaks.deepslateReplacesCobblestone && ablock[array].isReplaceableOreGen(worldObj, x2, y, z2, Blocks.cobblestone)) {
                            	ablock[array] = ModBlocks.cobbled_deepslate;
                        	}
                        }
                	}
            	}
        	}
    	}
    }
    
    /**
     * Chooses between deepslate or stone for generation.
     * 
     * @param l1, y level
     * @param rand
     * @return Stone, or deepslate
     */
    private boolean shouldBeDeepslate(int y, Random rand) {
		return y < ConfigWorld.deepslateMaxY - 4 || y <= ConfigWorld.deepslateMaxY - rand.nextInt(4);
    }
    
//	public boolean chunkExists(int p_73149_1_, int p_73149_2_) {return false;}
//	public Chunk loadChunk(int p_73158_1_, int p_73158_2_) {return null;}
//	public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {}
//	public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {return false;}
//	public boolean unloadQueuedChunks() {return false;}
//	public boolean canSave() {return false;}
//	public String makeString() {return "jss2a98aj";}
//	public List getPossibleCreatures(EnumCreatureType p_73155_1_, int p_73155_2_, int p_73155_3_, int p_73155_4_) {return null;}
//	public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_) {return null;}
//	public int getLoadedChunkCount() {return 0;}
//	public void recreateStructures(int p_82695_1_, int p_82695_2_) {}
//	public void saveExtraData() {}
//	public Chunk provideChunk(int p_73154_1_, int p_73154_2_) {return null;}
}
