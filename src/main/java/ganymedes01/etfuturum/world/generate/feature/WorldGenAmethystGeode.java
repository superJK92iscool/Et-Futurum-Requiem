package ganymedes01.etfuturum.world.generate.feature;

import java.util.Random;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.world.generate.feature.WorldGenFossil.Fossil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAmethystGeode extends WorldGenerator {
	//I don't know how to make the sphere slightly warped in shape and I didn't understand the massive nest of noise-related classes
	//in vanilla, so I am just using a perfect circle for now, sorry!
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		final float size = (random.nextInt(3) + 6) + random.nextFloat();
		final float DISTANCE_BASALT_SQ = (size * size);
		final float DISTANCE_CALCITE_SQ = ((size - 1) * (size - 1));
		final float DISTANCE_AMETHYST_SQ = ((size - 2) * (size - 2));
		final float DISTANCE_INNER_SQ = ((size - 3) * (size - 3));
		final int sizeInt = MathHelper.floor_float(size);
		
		if(!canGeodeGenerateHere(world, x, y, z, sizeInt)) {
			return false;
		}
		
		int holeX = -1;
		int holeY = -1;
		int holeZ = -1;
		float radius = -1;
		int holeSize = random.nextFloat() < .95F ? random.nextInt(3) + 5 : -1;
		float holeTheta = 0;
		float holePhi = 0;
		if(holeSize != -1) {
			holeTheta = (float) (Math.acos(1 - 2 * random.nextFloat()) - Math.PI/2);
			holePhi = (float) (random.nextFloat() * Math.PI * 2);
			radius = size - (random.nextFloat() * 2 + 0.5F) + (random.nextFloat() * 0);
			holeX = Math.round(radius * MathHelper.cos(holePhi) * MathHelper.cos(holeTheta));
			holeY = Math.round(radius * MathHelper.sin(holeTheta));
			holeZ = Math.round(radius * MathHelper.sin(holePhi) * MathHelper.cos(holeTheta));
		}
		
//      if(holeSize > -1) {
//          holeX = random.nextInt(sizeInt * 2) - sizeInt;
//          holeY = random.nextInt(sizeInt * 2) - sizeInt;
//          holeZ = random.nextInt(sizeInt * 2) - sizeInt;
//          int holeDistSq = holeX * holeX + holeY * holeY + holeZ * holeZ;
//          //Keep guessing with random numbers until the hole is at the proper spot since I don't know how to do this right, lmao
//          while(holeDistSq > DISTANCE_CALCITE_SQ || holeDistSq < DISTANCE_AMETHYST_SQ) {
//              holeX = random.nextInt(sizeInt * 2) - sizeInt;
//              holeY = random.nextInt(sizeInt * 2) - sizeInt;
//              holeZ = random.nextInt(sizeInt * 2) - sizeInt;
//              holeDistSq = holeX * holeX + holeY * holeY + holeZ * holeZ;
//          }
//      }
		
		for (int i = -sizeInt; i <= sizeInt; i++) {
			for (int j = -sizeInt; j <= sizeInt; j++) {
				for (int k = -sizeInt; k <= sizeInt; k++) {
					int distSq = i * i + j * j + k * k;
					Block block = world.getBlock(x + i, y + j, z + k);
					if(block.getBlockHardness(world, x + i, y + j, z + k) == -1) continue;

					if(holeSize > -1) {
						double deltaX = Math.abs(i - holeX);
						double deltaY = Math.abs(j - holeY);
						double deltaZ = Math.abs(k - holeZ);
						
						if(deltaX + deltaY + deltaZ < holeSize && distSq <= DISTANCE_BASALT_SQ) {
							world.setBlockToAir(x + i, y + j, z + k);
							continue;
						}
					}
					
					if (distSq <= DISTANCE_INNER_SQ) {
						world.setBlockToAir(x + i, y + j, z + k);
					} else if (distSq <= DISTANCE_BASALT_SQ && distSq > DISTANCE_CALCITE_SQ) {
						world.setBlock(x + i, y + j, z + k, ConfigWorld.amethystOuterBlock, 0, 2);
					} else if (distSq <= DISTANCE_CALCITE_SQ && distSq > DISTANCE_AMETHYST_SQ) {
						world.setBlock(x + i, y + j, z + k, ModBlocks.calcite, 0, 2);
					} else if (distSq <= DISTANCE_AMETHYST_SQ) {
						placeAmethyst(world, random, x + i, y + j, z + k);
					}
				}
			}
		}
		return true;
	}
	
	private boolean canGeodeGenerateHere(World world, int x, int y, int z, int size) {
		int air = 0;
		int cornerDist = (int)((size - 0.5F) / MathHelper.sqrt_float(3));
		if(isInvalidCorner(world, x + cornerDist, y + size, z + cornerDist)) {
			air++;
		}
		if(isInvalidCorner(world, x + cornerDist, y + size, z - cornerDist)) { 
			air++;
		}
		if(isInvalidCorner(world, x - cornerDist, y + size, z + cornerDist)) {
			air++;
		}
		if(isInvalidCorner(world, x - cornerDist, y + size, z - cornerDist)) {
			if(air++ >= 4) return false;
		}
		if(isInvalidCorner(world, x + cornerDist, y - cornerDist, z + cornerDist)) {
			if(air++ >= 4) return false;
		}
		if(isInvalidCorner(world, x + cornerDist, y - cornerDist, z - cornerDist)) {
			if(air++ >= 4) return false;
		}
		if(isInvalidCorner(world, x - cornerDist, y - cornerDist, z + cornerDist)) {
			if(air++ >= 4) return false;
		}
		if(isInvalidCorner(world, x - cornerDist, y - cornerDist, z - cornerDist)) {
			if(air++ >= 4) return false;
		}
		return air < 4;
	}
	
	/*
	 * This is used when generating amethyst to check all 8 corners.
	 * This is so it doesn't generate in the middle of the air, ocean, hanging in trees, etc.
	 */
	private boolean isInvalidCorner(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return world.canBlockSeeTheSky(x, y, z) && block.isAir(world, x, y, z) && !block.isOpaqueCube();
	}
	
	private void placeAmethyst(World world, Random random, int x, int y, int z) {
		if(random.nextInt(12) == 0) {
			world.setBlock(x, y, z, ModBlocks.budding_amethyst);
			for(EnumFacing facing : EnumFacing.values()) {
				int clusterSize = random.nextInt(5);
				if(clusterSize > 0) {
					int offX = x + facing.getFrontOffsetX();
					int offY = y + facing.getFrontOffsetY();
					int offZ = z + facing.getFrontOffsetZ();
					if(world.getBlock(offX, offY, offZ).getMaterial() == Material.air || world.getBlock(offX, offY, offZ).getMaterial() == Material.water) {
						Block block = clusterSize > 2 ? ModBlocks.amethyst_cluster_2 : ModBlocks.amethyst_cluster_1;
						int meta = (clusterSize == 1 || clusterSize == 3 ? 0 : 6) + facing.ordinal();
						world.setBlock(offX, offY, offZ, block, meta, 2);
					}
				}
			}
		} else {
			world.setBlock(x, y, z, ModBlocks.amethyst_block);
		}
	}
}
