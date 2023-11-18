package ganymedes01.etfuturum.world.generate.decorate;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class WorldGenCherryTrees extends WorldGenAbstractTree {

	private final int branchCountMin;
	private final int branchCountMax;
	private final int branchHorizontalLengthMin;
	private final int branchHorizontalLengthMax;
	//	private final int secondBranchStartOffsetFromTop;
	private final int branchStartOffsetFromTopMin;
	private final int branchStartOffsetFromTopMax;
	private final int branchEndOffsetFromTopMin;
	private final int branchEndOffsetFromTopMax;

	public WorldGenCherryTrees(boolean p_i2008_1_) {
		super(p_i2008_1_);
		this.branchCountMin = 1;
		this.branchCountMax = 3;
		this.branchHorizontalLengthMin = 2;
		this.branchHorizontalLengthMax = 4;
		this.branchStartOffsetFromTopMin = -4;
		this.branchStartOffsetFromTopMax = -3;
//		this.secondBranchStartOffsetFromTop = p_272917_;
		this.branchEndOffsetFromTopMin = -1;
		this.branchEndOffsetFromTopMax = 0;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		int height = 7;

		world.setBlock(x, y - 1, z, Blocks.dirt);

		int secondBranchOffsetFromTop = Math.max(0, height - 1 + MathHelper.getRandomIntegerInRange(rand, branchEndOffsetFromTopMin, branchEndOffsetFromTopMax)); //second branch
		int firstBranchOffsetFromTop = Math.max(0, height - 1 + MathHelper.getRandomIntegerInRange(rand, branchEndOffsetFromTopMin, branchEndOffsetFromTopMax));
		if (firstBranchOffsetFromTop >= secondBranchOffsetFromTop) {
			++firstBranchOffsetFromTop;
		}

		int branchCount = MathHelper.getRandomIntegerInRange(rand, branchCountMin, branchCountMax);
		boolean flag = branchCount == 3;
		boolean flag1 = branchCount >= 2;
		int treeHeight;
		if (flag) {
			treeHeight = height;
		} else if (flag1) {
			treeHeight = Math.max(secondBranchOffsetFromTop, firstBranchOffsetFromTop) + 1;
		} else {
			treeHeight = secondBranchOffsetFromTop + 1;
		}

		for (int i1 = 0; i1 < treeHeight; ++i1) {
			world.setBlock(x, y + i1, z, ModBlocks.CHERRY_LOG.get());
		}

		if (flag) {
			placeFoliage(world, x, y + treeHeight, z, rand);
		}

		ForgeDirection direction = ForgeDirection.VALID_DIRECTIONS[rand.nextInt(4) + 2];
		this.generateBranch(world, x, y, z, rand, height, direction, secondBranchOffsetFromTop, secondBranchOffsetFromTop < treeHeight - 1);
		if (flag1) {
			this.generateBranch(world, x, y, z, rand, height, direction.getOpposite(), firstBranchOffsetFromTop, firstBranchOffsetFromTop < treeHeight - 1);
		}

		return true;
	}

	private void generateBranch(World world, int x, int y, int z, Random rand, int height, ForgeDirection dir, int offsetFromTop, boolean lower) {
		int mutableX = x;
		int mutableY = y + offsetFromTop;
		int mutableZ = z;
		int i = height - 1 + MathHelper.getRandomIntegerInRange(rand, branchEndOffsetFromTopMin, branchEndOffsetFromTopMax);
		boolean flag = lower || i < offsetFromTop;
		int j = MathHelper.getRandomIntegerInRange(rand, branchHorizontalLengthMin, branchHorizontalLengthMax) + (flag ? 1 : 0);
		int k = flag ? 2 : 1;
		int branchEndX = mutableX + (dir.offsetX * j);
		int branchEndY = mutableY + i;
		int branchEndZ = mutableZ + (dir.offsetZ * j);
		for (int l = 0; l < k; ++l) {
			mutableX += dir.offsetX;
			mutableZ += dir.offsetZ;
			setBlockAndNotifyAdequately(world, mutableX, mutableY, mutableZ, ModBlocks.CHERRY_LOG.get(), dir.offsetX != 0 ? 4 : dir.offsetZ != 0 ? 8 : 0);
		}

		ForgeDirection branchDir = branchEndY > mutableY ? ForgeDirection.UP : ForgeDirection.DOWN;

		while (true) {
			int i1 = Utils.distManhattan(branchEndX, branchEndY, branchEndZ, mutableX, mutableY, mutableZ);
			if (i1 == 0) {
				placeFoliage(world, branchEndX, branchEndY + 1, branchEndZ, rand);
				return;
			}

			float f = (float) Math.abs(branchEndY - mutableY) / (float) i1;
			boolean flag1 = rand.nextFloat() < f;
			ForgeDirection logDir = flag1 ? branchDir : dir;
			mutableX += logDir.offsetX;
			mutableY += logDir.offsetY;
			mutableZ += logDir.offsetZ;
			setBlockAndNotifyAdequately(world, mutableX, mutableY, mutableZ, ModBlocks.CHERRY_LOG.get(), logDir.offsetX != 0 ? 4 : logDir.offsetZ != 0 ? 8 : 0);
		}
	}

	private void placeFoliage(World world, int x, int y, int z, Random rand) {
		//Empty for now; the cherry leaf placer code is impossible to read
	}

	@Override
	protected void setBlockAndNotifyAdequately(World p_150516_1_, int p_150516_2_, int p_150516_3_, int p_150516_4_, Block p_150516_5_, int p_150516_6_) {
		if (isReplaceable(p_150516_1_, p_150516_2_, p_150516_3_, p_150516_4_)) {
			super.setBlockAndNotifyAdequately(p_150516_1_, p_150516_2_, p_150516_3_, p_150516_4_, p_150516_5_, p_150516_6_);
		}
	}

	private void placeLeaves(World world, int x, int y, int z) {
		if (!(world.getBlock(x, y, z) instanceof BlockLog)) {
			setBlockAndNotifyAdequately(world, x, y, z, ModBlocks.LEAVES.get(), 1);
		}
	}
}
