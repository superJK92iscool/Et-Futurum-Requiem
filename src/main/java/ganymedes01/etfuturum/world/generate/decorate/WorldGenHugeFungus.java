package ganymedes01.etfuturum.world.generate.decorate;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockNylium;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenHugeFungus extends WorldGenAbstractTree {

	/**
	 * True for non-natural grown trees
	 */
	private final boolean planted;

	public final Block log;
	private final Block hat;
	private final int logMeta;
	private final int hatMeta;

	public WorldGenHugeFungus(boolean notify, int metaLog, int metaLeaf, Block log, Block leaf) {
		this(notify, metaLog, metaLeaf, log, leaf, false);
	}

	public WorldGenHugeFungus(boolean notify, int metaLog, int metaLeaf, Block log, Block leaf, boolean planted) {
		super(notify);
		this.logMeta = metaLog;
		this.hatMeta = metaLeaf;
		this.log = log;
		this.hat = leaf;
		this.planted = planted;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z) {
		BlockPos blockpos = new BlockPos(x, y, z);
		BlockPos blockpos1 = null;
		Block blockstate = world.getBlock(x, y - 1, z);
		if (blockstate instanceof BlockNylium) {
			blockpos1 = blockpos;
		}

		if (blockpos1 == null) {
			return false;
		} else {
			int i = MathHelper.getRandomIntegerInRange(random, 4, 13);
			if (random.nextInt(12) == 0) {
				i *= 2;
			}

//			if (!planted) {
//				int j = chunkgenerator.getGenDepth(); //Probably refers to generating below y 0 which we will not need
//				if (blockpos1.getY() + i + 1 >= j) {
//					return false;
//				}
//			}

			boolean flag = !planted && random.nextFloat() < 0.06F;
			world.setBlock(x, y, z, Blocks.air);
			if (!planted) {
				world.setBlock(x, y - 1, z, Blocks.netherrack);
			}
			this.placeStem(world, random, blockpos1, i, flag);
			this.placeHat(world, random, blockpos1, i, flag);
			return true;
		}
	}

	private static boolean isReplaceable(World p_285049_, BlockPos p_285309_) {
		return p_285049_.getBlock(p_285309_.getX(), p_285309_.getY(), p_285309_.getZ()).isReplaceable(p_285049_, p_285309_.getX(), p_285309_.getY(), p_285309_.getZ());
	}

	private void placeStem(World p_285364_, Random p_285032_, BlockPos p_285090_, int p_285249_, boolean p_285355_) {
		BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();
		int i = p_285355_ ? 1 : 0;

		for (int j = -i; j <= i; ++j) {
			for (int k = -i; k <= i; ++k) {
				boolean flag = p_285355_ && MathHelper.abs(j) == i && MathHelper.abs(k) == i;

				for (int l = 0; l < p_285249_; ++l) {
					mutableblockpos.setWithOffset(p_285090_, j, l, k);
					if (isReplaceable(p_285364_, mutableblockpos)) {
						if (planted) {
							if (!p_285364_.isAirBlock(mutableblockpos.getX(), mutableblockpos.getY() - 1, mutableblockpos.getZ())) {
								p_285364_.setBlockToAir(mutableblockpos.getX(), mutableblockpos.getY(), mutableblockpos.getZ());
							}

							setBlockAndNotifyAdequately(p_285364_, mutableblockpos.getX(), mutableblockpos.getY(), mutableblockpos.getZ(), log, logMeta);
						} else if (flag) {
							if (p_285032_.nextFloat() < 0.1F) {
								setBlockAndNotifyAdequately(p_285364_, mutableblockpos.getX(), mutableblockpos.getY(), mutableblockpos.getZ(), log, logMeta);
							}
						} else {
							setBlockAndNotifyAdequately(p_285364_, mutableblockpos.getX(), mutableblockpos.getY(), mutableblockpos.getZ(), log, logMeta);
						}
					}
				}
			}
		}

	}

	private void placeHat(World p_285200_, Random p_285456_, BlockPos p_285097_, int p_285156_, boolean p_285265_) {
		BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos();
		boolean flag = hat == ModBlocks.NETHER_WART.get() && hatMeta == 0;
		int i = Math.min(p_285456_.nextInt(1 + p_285156_ / 3) + 5, p_285156_);
		int j = p_285156_ - i;

		for (int k = j; k <= p_285156_; ++k) {
			int l = k < p_285156_ - p_285456_.nextInt(3) ? 2 : 1;
			if (i > 8 && k < j + 4) {
				l = 3;
			}

			if (p_285265_) {
				++l;
			}

			for (int i1 = -l; i1 <= l; ++i1) {
				for (int j1 = -l; j1 <= l; ++j1) {
					boolean flag1 = i1 == -l || i1 == l;
					boolean flag2 = j1 == -l || j1 == l;
					boolean flag3 = !flag1 && !flag2 && k != p_285156_;
					boolean flag4 = flag1 && flag2;
					boolean flag5 = k < j + 3;
					mutableblockpos.setWithOffset(p_285097_, i1, k, j1);
					if (isReplaceable(p_285200_, mutableblockpos.getX(), mutableblockpos.getY(), mutableblockpos.getZ())) {
//						if (planted && !p_285200_.isAirBlock(mutableblockpos.getX(), mutableblockpos.getY() - 1, mutableblockpos.getZ())) {
//							p_285200_.setBlockToAir(mutableblockpos.getX(), mutableblockpos.getY(), mutableblockpos.getZ());
//						}

						if (flag5) {
							if (!flag3) {
								this.placeHatDropBlock(p_285200_, p_285456_, mutableblockpos, flag);
							}
						} else if (flag3) {
							this.placeHatBlock(p_285200_, p_285456_, mutableblockpos, 0.1F, 0.2F, flag ? 0.1F : 0.0F);
						} else if (flag4) {
							this.placeHatBlock(p_285200_, p_285456_, mutableblockpos, 0.01F, 0.7F, flag ? 0.083F : 0.0F);
						} else {
							this.placeHatBlock(p_285200_, p_285456_, mutableblockpos, 5.0E-4F, 0.98F, flag ? 0.07F : 0.0F);
						}
					}
				}
			}
		}
	}

	private void placeHatBlock(World world, Random rand, BlockPos.MutableBlockPos pos, float p_225054_, float p_225055_, float p_225056_) {
		if (rand.nextFloat() < p_225054_) {
			world.setBlock(pos.getX(), pos.getY(), pos.getZ(), ModBlocks.SHROOMLIGHT.get(), 0, 3);
		} else if (rand.nextFloat() < p_225055_) {
			setBlockAndNotifyAdequately(world, pos.getX(), pos.getY(), pos.getZ(), hat, hatMeta);
			if (rand.nextFloat() < p_225056_) {
				tryPlaceWeepingVines(pos, world, rand);
			}
		}
	}

	private void placeHatDropBlock(World world, Random rand, BlockPos pos, boolean p_225069_) {
		if (world.getBlock(pos.getX(), pos.getY() - 1, pos.getZ()) == hat
				|| world.getBlockMetadata(pos.getX(), pos.getY() - 1, pos.getZ()) == hatMeta) {
			setBlockAndNotifyAdequately(world, pos.getX(), pos.getY(), pos.getZ(), hat, hatMeta);
		} else if ((double) rand.nextFloat() < 0.15D) {
			setBlockAndNotifyAdequately(world, pos.getX(), pos.getY(), pos.getZ(), hat, hatMeta);
			if (p_225069_ && rand.nextInt(11) == 0) {
				tryPlaceWeepingVines(pos, world, rand);
			}
		}

	}

	private static void tryPlaceWeepingVines(BlockPos pos, World world, Random rand) {
		if (world.getBlock(pos.getX(), pos.getY() - 1, pos.getZ()).getMaterial() == Material.air) {
			int i = MathHelper.getRandomIntegerInRange(rand, 1, 5);
			if (rand.nextInt(7) == 0) {
				i *= 2;
			}
			WorldGenWeepingVines.placeWeepingVinesColumn(world, rand, pos.getX(), pos.getY(), pos.getZ(), i/*, 23, 25*/);
		}
	}
}