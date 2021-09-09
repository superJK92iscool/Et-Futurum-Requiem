package ganymedes01.etfuturum.mixins;

import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenVillage;

@Mixin(value = ChunkProviderGenerate.class)
public class MixinChunkProviderGenerate {
    
    @Inject(method = "provideChunk", at = @At(value = "NEW", target = "net/minecraft/world/chunk/Chunk"))
    private void generateDeepslate(int x, int z, CallbackInfoReturnable<Chunk> info) {
    	System.out.println(x + " " + z);
//    	int x2, z2, array;
//    	for(int x1 = 0; x1 < 16; x1++) {
//        	for(int z1 = 0; z1 < 16; z1++) {
//            	for(int y = 0; y <= ConfigBase.deepslateMaxY; y++) {
//            		x2 = (x * 16) + x1;
//            		z2 = (z * 16) + z1;
//            		
//                    array = (z1 * 16 + x1) * (blocks.length / 256) + y;
//
//                    if(blocks[array] != null && blocks[array] != Blocks.air && shouldBeDeepslate(y, rand) && blocks[array].isReplaceableOreGen(worldObj, x2, y, z2, Blocks.stone)) {
//                    	blocks[array] = ModBlocks.deepslate;
//                    }
//            	}
//        	}
//    	}
    }
    
    /**
     * Chooses between deepslate or stone for generation.
     * 
     * @param l1, y level
     * @param rand
     * @return Stone, or deepslate
     */
    private boolean shouldBeDeepslate(int y, Random rand) {
		return y < ConfigBase.deepslateMaxY - 4 || y <= ConfigBase.deepslateMaxY - rand.nextInt(4);
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
