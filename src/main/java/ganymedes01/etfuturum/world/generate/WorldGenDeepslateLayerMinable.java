package ganymedes01.etfuturum.world.generate;
import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.ores.DeepslateMapping;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenDeepslateLayerMinable extends WorldGenerator
{
	private Block field_150519_a;
	/** The number of blocks to generate. */
	private int numberOfBlocks;
	private Block field_150518_c;
	private int mineableBlockMeta;

	public WorldGenDeepslateLayerMinable(Block p_i45459_1_, int p_i45459_2_)
	{
		this(p_i45459_1_, p_i45459_2_, Blocks.stone);
	}

	public WorldGenDeepslateLayerMinable(Block p_i45460_1_, int p_i45460_2_, Block p_i45460_3_)
	{
		this.field_150519_a = p_i45460_1_;
		this.numberOfBlocks = p_i45460_2_;
		this.field_150518_c = p_i45460_3_;
	}

	public WorldGenDeepslateLayerMinable(Block block, int meta, int number, Block target)
	{
		this(block, number, target);
		this.mineableBlockMeta = meta;
	}

	@Override
	public boolean generate(World world, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		float f = p_76484_2_.nextFloat() * (float)Math.PI;
		double d0 = p_76484_3_ + 8 + MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
		double d1 = p_76484_3_ + 8 - MathHelper.sin(f) * this.numberOfBlocks / 8.0F;
		double d2 = p_76484_5_ + 8 + MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
		double d3 = p_76484_5_ + 8 - MathHelper.cos(f) * this.numberOfBlocks / 8.0F;
		double d4 = p_76484_4_ + p_76484_2_.nextInt(3) - 2;
		double d5 = p_76484_4_ + p_76484_2_.nextInt(3) - 2;

		for (int l = 0; l <= this.numberOfBlocks; ++l)
		{
			double d6 = d0 + (d1 - d0) * l / this.numberOfBlocks;
			double d7 = d4 + (d5 - d4) * l / this.numberOfBlocks;
			double d8 = d2 + (d3 - d2) * l / this.numberOfBlocks;
			double d9 = p_76484_2_.nextDouble() * this.numberOfBlocks / 16.0D;
			double d10 = (MathHelper.sin(l * (float)Math.PI / this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin(l * (float)Math.PI / this.numberOfBlocks) + 1.0F) * d9 + 1.0D;
			int i1 = MathHelper.floor_double(d6 - d10 / 2.0D);
			int j1 = MathHelper.floor_double(d7 - d11 / 2.0D);
			int k1 = MathHelper.floor_double(d8 - d10 / 2.0D);
			int l1 = MathHelper.floor_double(d6 + d10 / 2.0D);
			int i2 = MathHelper.floor_double(d7 + d11 / 2.0D);
			int j2 = MathHelper.floor_double(d8 + d10 / 2.0D);

			for (int x = i1; x <= l1; ++x)
			{
				double d12 = (x + 0.5D - d6) / (d10 / 2.0D);

				if (d12 * d12 < 1.0D)
				{
					for (int y = j1; y <= i2; ++y)
					{
						double d13 = (y + 0.5D - d7) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D)
						{
							for (int z = k1; z <= j2; ++z)
							{
								double d14 = (z + 0.5D - d8) / (d10 / 2.0D);

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D && world.getBlock(x, y, z).isReplaceableOreGen(world, x, y, z, field_150518_c))
								{
									DeepslateMapping mapping = EtFuturum.deepslateOres.get(new DeepslateMapping(world.getBlock(x, y, z), world.getBlockMetadata(x, y, z)));
									if(ConfigurationHandler.enableDeepslate && mapping != null) {
										world.setBlock(x, y, z, mapping.getOre(), mapping.getMeta(), 2);
									} else {
										world.setBlock(x, y, z, this.field_150519_a, mineableBlockMeta, 2);
									}
								}
							}
						}
					}
				}
			}
		}

		return true;
	}
}
