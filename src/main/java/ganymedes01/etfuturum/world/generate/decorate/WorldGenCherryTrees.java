package ganymedes01.etfuturum.world.generate.decorate;

import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;

public class WorldGenCherryTrees extends WorldGenBigTree {
	public WorldGenCherryTrees(boolean p_i2008_1_) {
		super(p_i2008_1_);
	}

	@Override
	protected void setBlockAndNotifyAdequately(World p_150516_1_, int p_150516_2_, int p_150516_3_, int p_150516_4_, Block p_150516_5_, int p_150516_6_) {
		int meta = p_150516_6_;
		Block block = p_150516_5_;
		if (block == Blocks.log) {
			block = ModBlocks.CHERRY_LOG.get();
		} else if (block == Blocks.leaves) {
			block = ModBlocks.LEAVES.get();
			meta = 1;
		}
		super.setBlockAndNotifyAdequately(p_150516_1_, p_150516_2_, p_150516_3_, p_150516_4_, block, meta);
	}
}
