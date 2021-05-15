package ganymedes01.etfuturum.world.nether.generate;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class HugeCrimsonFungi0 extends WorldGenAbstractTree {
	/** The minimum height of a generated tree. */
	private final int minTreeHeight;
	/** True if this tree should grow Vines. */
	private final boolean vinesGrow;
	/** The metadata value of the wood to use in tree generation. */
	private final int metaWood;
	/** The metadata value of the leaves to use in tree generation. */
	private final int metaLeaves;

	private static Block Log;
	private static Block Leaf;

	public HugeCrimsonFungi0(boolean notify) {
		this(notify, 4, 0, 0, false, Log, Leaf);
	}

	public HugeCrimsonFungi0(boolean notify, int height, int metaLog, int metaLeaf, boolean vine, Block log,
			Block leaf) {
		super(notify);
		this.minTreeHeight = height;
		this.metaWood = metaLog;
		this.metaLeaves = metaLeaf;
		this.Log = log;
		this.Leaf = leaf;
		this.vinesGrow = vine;
	}

	public boolean generate(World world, Random rand, int x, int y, int z) {
		int l = rand.nextInt(3) + this.minTreeHeight;
		boolean flag = true;

		if (y >= 1 && y + l + 1 <= 256) {
			byte b0;
			int k1;
			Block block;

			for (int i1 = y; i1 <= y + 1 + l; ++i1) {
				b0 = 1;

				if (i1 == y) {
					b0 = 0;
				}

				if (i1 >= y + 1 + l - 2) {
					b0 = 2;
				}

				for (int j1 = x - b0; j1 <= x + b0 && flag; ++j1) {
					for (k1 = z - b0; k1 <= z + b0 && flag; ++k1) {
						if (i1 >= 0 && i1 < 256) {
							block = world.getBlock(j1, i1, k1);

							if (!this.isReplaceable(world, j1, i1, k1)) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else {
				Block block2 = world.getBlock(x, y - 1, z);

				boolean isSoil = block2 == Blocks.grass; // Come back to
				if (isSoil && y < 256 - l - 1) {
					block2.onPlantGrow(world, x, y - 1, z, x, y, z);
					b0 = 3;
					byte b1 = 0;
					int l1;
					int i2;
					int j2;
					int i3;

					for (k1 = y - b0 + l; k1 <= y + l; ++k1) {
						i3 = k1 - (y + l);
						l1 = b1 + 1 - i3 / 2;

						for (i2 = x - l1; i2 <= x + l1; ++i2) {
							j2 = i2 - x;

							for (int k2 = z - l1; k2 <= z + l1; ++k2) {
								int l2 = k2 - z;

								if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || rand.nextInt(2) != 0 && i3 != 0) {
									Block block1 = world.getBlock(i2, k1, k2);

									if (block1.isAir(world, i2, k1, k2) || block1.isLeaves(world, i2, k1, k2)) {
										int var999 = rand.nextInt(40);

										if (var999 == 0) {
											this.setBlockAndNotifyAdequately(world, i2, k1, k2, Blocks.glowstone, // Come back to
													0);
											// Come back to: Fix lighting
										}

										else {

											this.setBlockAndNotifyAdequately(world, i2, k1, k2, this.Leaf,
													this.metaLeaves);
										}
									}
								}
							}
						}
					}

					for (k1 = 0; k1 < l; ++k1) {
						block = world.getBlock(x, y + k1, z);

						if (block.isAir(world, x, y + k1, z) || block == Leaf) {
							this.setBlockAndNotifyAdequately(world, x, y + k1, z, Log, this.metaWood);
//                        }
//                            if (this.vinesGrow && k1 > 0)
//                            {
//                                if (rand.nextInt(3) > 0 && world.isAirBlock(x - 1, y + k1, z))
//                                {
//                                    this.setBlockAndNotifyAdequately(world, x - 1, y + k1, z, ModBlocks.WartBlock, metaLeaves);
//                                }
//
//                                if (rand.nextInt(3) > 0 && world.isAirBlock(x + 1, y + k1, z))
//                                {
//                                    this.setBlockAndNotifyAdequately(world, x + 1, y + k1, z, ModBlocks.WartBlock, metaLeaves);
//                                }
//
//                                if (rand.nextInt(3) > 0 && world.isAirBlock(x, y + k1, z - 1))
//                                {
//                                    this.setBlockAndNotifyAdequately(world, x, y + k1, z - 1, ModBlocks.WartBlock, metaLeaves);
//                                }
//
//                                if (rand.nextInt(3) > 0 && world.isAirBlock(x, y + k1, z + 1))
//                                {
//                                    this.setBlockAndNotifyAdequately(world, x, y + k1, z + 1, ModBlocks.WartBlock, metaLeaves);
//                                }
//                            }
						}
					}
//
					if (this.vinesGrow) {
						for (k1 = y - 3 + l; k1 <= y + l; ++k1) {
							i3 = k1 - (y + l);
							l1 = 2 - i3 / 2;

							for (i2 = x - l1; i2 <= x + l1; ++i2) {
								for (j2 = z - l1; j2 <= z + l1; ++j2) {
									if (world.getBlock(i2, k1 + 1, j2) == Leaf) {
										if (rand.nextInt(1) == 0
												&& world.getBlock(i2, k1 - 1, j2).isAir(world, i2, k1 - 1, j2)
												&& !(world.getBlock(i2 - 1, k1, j2) == Log)
												&& !(world.getBlock(i2 + 1, k1, j2) == Log)
												&& !(world.getBlock(i2, k1, j2 - 1) == Log)
												&& !(world.getBlock(i2, k1, j2 + 1) == Log)
												&& !(world.getBlock(i2 - 1, k1, j2 + 1) == Log)
												&& !(world.getBlock(i2 - 1, k1, j2 - 1) == Log)
												&& !(world.getBlock(i2 + 1, k1, j2 + 1) == Log)
												&& !(world.getBlock(i2 + 1, k1, j2 - 1) == Log)) {
											this.growVines(world, rand, i2, k1, j2, metaLeaves);
										}

//                                        if (rand.nextInt(4) == 0 && world.getBlock(i2 , k1 -1, j2).isAir(world, i2 , k1 -1, j2) && !(world.getBlock(i2 , k1 , j2 - 1) == ModBlocks.BackportLogs))
//                                        {
//                                            this.growVines(world, i2 , k1, j2, 1);
//                                        }
//
//                                        if (rand.nextInt(4) == 0 && world.getBlock(i2 , k1 -1, j2).isAir(world, i2 , k1 -1, j2) && !(world.getBlock(i2 -1 , k1 , j2 +1) == ModBlocks.BackportLogs))
//                                        {
//                                            this.growVines(world, i2 , k1, j2, 1);
//                                        }
//
//                                        if (rand.nextInt(4) == 0 && world.getBlock(i2 , k1 -1, j2).isAir(world, i2 , k1 -1, j2) && !(world.getBlock(i2 -1, k1 , j2 -1) == ModBlocks.BackportLogs))
//                                        {
//                                            this.growVines(world, i2 , k1, j2, 1);
//                                        }
//                                        
//                                        if (rand.nextInt(4) == 0 && world.getBlock(i2 , k1 -1, j2).isAir(world, i2 , k1 -1, j2) && !(world.getBlock(i2 +1 , k1 , j2 +1) == ModBlocks.BackportLogs))
//                                        {
//                                            this.growVines(world, i2 , k1, j2, 1);
//                                        }
//
//                                        if (rand.nextInt(4) == 0 && world.getBlock(i2 , k1 -1, j2).isAir(world, i2 , k1 -1, j2) && !(world.getBlock(i2 +1, k1 , j2 -1) == ModBlocks.BackportLogs))
//                                        {
//                                            this.growVines(world, i2 , k1, j2, 1);
//                                        }
									}
								}
							}
						}
//
////                        if (rand.nextInt(5) == 0 && l > 5)
////                        {
////                            for (k1 = 0; k1 < 2; ++k1)
////                            {
////                                for (i3 = 0; i3 < 4; ++i3)
////                                {
////                                    if (rand.nextInt(4 - k1) == 0)
////                                    {
////                                        l1 = rand.nextInt(3);
////                                        this.setBlockAndNotifyAdequately(world, x , y + l - 5 + k1, z, Blocks.dirt, l1 << 2 | i3);
////                                    }
////                                }
					}

					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	/**
	 * Grows vines downward from the given block for a given length. Args: World, x,
	 * starty, z, vine-length
	 */
	private void growVines(World world, Random random, int x, int y, int z, int length) {
		this.setBlockAndNotifyAdequately(world, x, y, z, Leaf, length);
		int i1 = (1 + random.nextInt(3));

		while (true) {
			--y;

			if (!world.getBlock(x, y, z).isAir(world, x, y, z) && world.getBlock(x, y + 1, z) == Leaf || i1 <= 0) {
				return;
			}

			this.setBlockAndNotifyAdequately(world, x, y, z, Leaf, length);
			--i1;
		}
	}
}