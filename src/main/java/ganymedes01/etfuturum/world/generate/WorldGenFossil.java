package ganymedes01.etfuturum.world.generate;

import java.util.Random;

import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class WorldGenFossil extends WorldGenerator {
	
	private Fossil fossil = new Fossil();
	public int type;
	
	public WorldGenFossil(Random rand) {
	}

	public boolean generateSpecificFossil(World world, Random random, int x, int y, int z, boolean rotated, int type, boolean hasCoal) {
		return fossil.buildForType(world, random, x, y, z, type, rotated, hasCoal);
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		return fossil.buildWithRandomType(world, random, x, y, z);
	}
	
	public static class Fossil {

		private static int types = 8;
		
		private boolean hasCoal;
		private int facing;

		public boolean buildWithRandomType(World world, Random rand, int x, int y, int z) {
			return buildForType(world, rand, x, y, z, rand.nextInt(types), rand.nextInt(2) == 1, rand.nextInt(2) == 1);
		}
		
		public boolean buildForType(World world, Random rand, int x, int y, int z, int type, boolean rotated, boolean hasCoal) {
			if(type == 0) {
				int rotation = rotated ? 8 : 4;
				int rotation2 = ~rotation & 8;
				fillBlocks(world, ModBlocks.bone_block, x+1, y, z+1, 4, 1, 3, rotation, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x+4, y, z+4, 1, 1, 2, rotation, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x+1, y, z+4, 1, 1, 2, rotation, 3, hasCoal, rand);

				fillBlocks(world, ModBlocks.bone_block, x, y+1, z+2, 1, 3, 3, rotation2, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x+5, y+1, z+2, 1, 3, 3, rotation2, 3, hasCoal, rand);
				
				world.setBlock(x+1, y+1, z, ModBlocks.bone_block, 0, 3);
				world.setBlock(x+4, y+1, z, ModBlocks.bone_block, 0, 3);
				
				fillBlocks(world, ModBlocks.bone_block, x+1, y+2, z, 4, 1, 1, 0, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x+2, y+3, z, 2, 1, 1, rotation, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x+1, y+4, z+1, 4, 1, 5, rotation, 3, hasCoal, rand);
				
				fillBlocks(world, ModBlocks.bone_block, x, y+1, z+1, 1, 3, 1, 0, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x, y+1, z+5, 1, 3, 1, 0, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x+5, y+1, z+1, 1, 3, 1, 0, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x+5, y+1, z+5, 1, 3, 1, 0, 3, hasCoal, rand);
				
				fillBlocks(world, ModBlocks.bone_block, x+1, y+1, z+6, 1, 3, 1, 0, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x+2, y+1, z+6, 2, 3, 1, rotation, 3, hasCoal, rand);
				fillBlocks(world, ModBlocks.bone_block, x+4, y+1, z+6, 1, 3, 1, 0, 3, hasCoal, rand);
				return true;
			}
			return false;
		}
		
		public void fillBlocks(World world, Block block, int xfrom, int yfrom, int zfrom, int xto, int yto, int zto, int meta, int flag, boolean hasCoal, Random rand) {
			for(int i = xfrom; i < xfrom + xto; i++) {
				for(int j = yfrom; j < yfrom + yto; j++) {
					for(int k = zfrom; k < zfrom + zto; k++) {
						if(rand.nextFloat() < 0.9) {
							if(rand.nextFloat() > 0.9) {
								world.setBlock(i, j, k, Blocks.coal_ore, 0, flag);
							} else {
								world.setBlock(i, j, k, block, meta, flag);
							}
						}
					}
				}
			}
		}
	}
}
