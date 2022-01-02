package ganymedes01.etfuturum.world.generate.feature;

import java.util.Random;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAmethystGeode extends WorldGenerator {

	//Add 2 to the shape, because otherwise it will generate with a block sticking out of the middle of each side of the circle.
    private static final int DISTANCE_BASALT_SQ = 8 * 8 + 2;
    private static final int DISTANCE_CALCITE_SQ = 7 * 7 + 2;
    private static final int DISTANCE_AMETHYST_SQ = 6 * 6 + 2;
    private static final int DISTANCE_INNER_SQ = 5 * 5 + 2;
    
	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		int holeX = -1;
		int holeY = -1;
		int holeZ = -1;
		int holeSize = random.nextFloat() < .95F ? random.nextInt(3) + 4 : -1;
		if(holeSize > -1) {
			holeX = random.nextInt(16) - 8;
			holeY = random.nextInt(16) - 8;
			holeZ = random.nextInt(16) - 8;
			int holeDistSq = holeX * holeX + 2 + holeY * holeY + 2 + holeZ * holeZ + 2;
			while(holeDistSq > DISTANCE_AMETHYST_SQ || holeDistSq < DISTANCE_INNER_SQ) {
				holeX = random.nextInt(16) - 8;
				holeY = random.nextInt(16) - 8;
				holeZ = random.nextInt(16) - 8;
				holeDistSq = holeX * holeX + 2 + holeY * holeY + 2 + holeZ * holeZ + 2;
			}
		}
		
        for (int i = -8; i < 8; i++) {
            for (int j = -8; j < 8; j++) {
                for (int k = -8; k < 8; k++) {
                    int distSq = i * i + 2 + j * j + 2 + k * k + 2;
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
                        world.setBlock(x + i, y + j, z + k, ModBlocks.smooth_basalt, 0, 2);
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
