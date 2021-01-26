package ganymedes01.etfuturum.world.generate;

import java.util.Random;

import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenHugeFungus extends WorldGenAbstractTree {
    /** The minimum height of a generated tree. */
    private final int minTreeHeight;
    /** The minimum height of a generated tree. */
    private final int maxTreeHeight;
    /** True if this tree should grow Vines. */
    private final boolean vinesGrow;

    public WorldGenHugeFungus(boolean p_i2027_1_, boolean crimson)
    {
        this(p_i2027_1_, 4, 14, crimson);
    }

    public WorldGenHugeFungus(boolean p_i2028_1_, int minHeight, int maxHeight, boolean growVines)
    {
        super(p_i2028_1_);
        minTreeHeight = minHeight;
        maxTreeHeight = maxHeight;
        vinesGrow = growVines;
    }
    
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
    	return true;
//        int l = Math.abs(rand.nextInt(maxTreeHeight) - rand.nextInt(maxTreeHeight)) + this.minTreeHeight;
//        boolean flag = true;
//
//        if (y >= 1 && y + l + 1 <= 256)
//        {
//            byte b0;
//            int k1;
//            Block block;
//
//            for (int i1 = y; i1 <= y + 1 + l; ++i1)
//            {
//                b0 = 1;
//
//                if (i1 == y)
//                {
//                    b0 = 0;
//                }
//
//                if (i1 >= y + 1 + l - 2)
//                {
//                    b0 = 2;
//                }
//
//                for (int j1 = x - b0; j1 <= x + b0 && flag; ++j1)
//                {
//                    for (k1 = z - b0; k1 <= z + b0 && flag; ++k1)
//                    {
//                        if (i1 >= 0 && i1 < 256)
//                        {
//                            block = world.getBlock(j1, i1, k1);
//
//                            if (!this.isReplaceable(world, j1, i1, k1))
//                            {
//                                flag = false;
//                            }
//                        }
//                        else
//                        {
//                            flag = false;
//                        }
//                    }
//                }
//            }
//
//            if (!flag)
//            {
//                return false;
//            }
//            else
//            {
//                Block block2 = world.getBlock(x, y - 1, z);
//
//                boolean isSoil = block2 instanceof BlockNylium;
//                if (isSoil && y < 256 - l - 2)
//                {
//                    block2.onPlantGrow(world, x, y - 1, z, x, y, z);
//
//                    for (int i1 = 0; i1 < 3; i1++) {
//                        for (int j1 = -2; j1 <= 2; j1++) {
//                            for (int k2 = -2; k2 <= 2; k2++) {
//                            	if(world.isAirBlock(x + j1, y + l - i1, z + k2)) {
//                                	boolean flag1 = ((Math.abs(j1) < 2 && Math.abs(k2) < 2)) && (i1 > 1 || (i1 == 1 && rand.nextInt(2) == 1));
//                                	boolean flag2 = ((Math.abs(j1) == 2 && Math.abs(k2) == 2) && ((i1 > 1) || (i1 == 1 && rand.nextFloat() > .30F)));
//                                	if(((flag1 && i1 == 1) || ((Math.abs(j1) == 1 ^ Math.abs(k2) == 1) && i1 == 2)) && rand.nextInt(4) == 0)
//                                        this.setBlockAndNotifyAdequately(world, x + j1, y + l - i1, z + k2, ModBlocks.shroomlight, 0);
//                                	if(((Math.abs(j1) > 1 || Math.abs(k2) > 1) && i1 == 0) || flag2 || flag1)
//                                		continue;
//                                	if(!flag2 && i1 == 2) {
//                                		for(int i2 = 0; i2 < rand.nextInt(4) + 1; i2++) {
//                                            this.setBlockAndNotifyAdequately(world, x + j1, y + l - i1 - i2, z + k2, ModBlocks.wart_block, vinesGrow ? 0 : 1);
//                                		}
//                                	}
//                                    this.setBlockAndNotifyAdequately(world, x + j1, y + l - i1, z + k2, ModBlocks.wart_block, vinesGrow ? 0 : 1);
//                            	}
//                            }
//                        }
//                    }
//                    for (k1 = 0; k1 < l; ++k1)
//                    {
//                        block = world.getBlock(x, y + k1, z);
//
//                        if (block.isAir(world, x, y + k1, z) || block instanceof BlockWart)
//                        {
//                            this.setBlockAndNotifyAdequately(world, x, y + k1, z, vinesGrow ? ModBlocks.crimson_stem : ModBlocks.warped_stem, 0);
//                        }
//                    }
//
//                    return true;
//                }
//                else
//                {
//                    return false;
//                }
//            }
//        }
//        else
//        {
//            return false;
//        }
    }
    
//    protected boolean isReplaceable(World world, int x, int y, int z)
//    {
//        return super.isReplaceable(world, x, y, z) || world.getBlock(x, y, z) instanceof BlockNylium || world.getBlock(x, y, z) instanceof BlockFungus;
//    }
}
