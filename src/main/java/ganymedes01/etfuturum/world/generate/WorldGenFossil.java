package ganymedes01.etfuturum.world.generate;

import java.util.Random;

import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFossil extends WorldGenerator {
	
	private Fossil fossil = new Fossil();
	public int type;
	
	public WorldGenFossil(Random rand) {
		buildFossilList();
	}
	
	private void buildFossilList() {
	}

	public boolean generateSpecificFossil(World world, Random random, int x, int y, int z, int facing, int type, boolean hasCoal) {
		return fossil.buildForType(world, random, x, y, z, type, facing, hasCoal);
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		return fossil.buildWithRandomType(world, random, x, y, z);
	}
	
	public static class Fossil {

		private static final int types = 8;
		
		private boolean hasCoal;
		private int facing;

		public boolean buildWithRandomType(World world, Random rand, int x, int y, int z) {
			return buildForType(world, rand, x, y, z, rand.nextInt(types), rand.nextInt(4), rand.nextInt(2) == 1 ? true : false);
		}
		
		public boolean buildForType(World world, Random rand, int x, int y, int z, int type, int facing, boolean hasCoal) {
			if(type == 0) {
				return true;
			}
			return false;
		}
		
	}

}
