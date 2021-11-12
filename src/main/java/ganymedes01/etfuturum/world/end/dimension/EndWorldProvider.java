package ganymedes01.etfuturum.world.end.dimension;

import ganymedes01.etfuturum.world.end.EndBiomeDecorator;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;

public class EndWorldProvider extends WorldProviderEnd {
	@Override
	public void registerWorldChunkManager() {
		BiomeGenBase.sky.theBiomeDecorator = new EndBiomeDecorator();
		this.worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F);
		this.dimensionId = 1;
		this.hasNoSky = true;
	}

	@Override
	public IChunkProvider createChunkGenerator() {
//      if (WorldgenConfiguration.BigNether) {
//          return new MaxHeightNetherChunkProvider(this.worldObj, this.worldObj.getSeed());
//      } else {
			return new EndChunkProvider(this.worldObj, this.worldObj.getSeed());
//      }
	}

}