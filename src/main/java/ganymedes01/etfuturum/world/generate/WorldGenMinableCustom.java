package ganymedes01.etfuturum.world.generate;

import java.util.Random;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenMinableCustom extends WorldGenMinable
{
	private final boolean shouldAirGen;

	public WorldGenMinableCustom(int p_i45459_2_)
	{
		super(ModBlocks.ancient_debris, p_i45459_2_, Blocks.netherrack);
		shouldAirGen = ConfigWorld.enableAirDebris;
	}

	@Override
	public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
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

			for (int k2 = i1; k2 <= l1; ++k2)
			{
				double d12 = (k2 + 0.5D - d6) / (d10 / 2.0D);

				if (d12 * d12 < 1.0D)
				{
					for (int l2 = j1; l2 <= i2; ++l2)
					{
						double d13 = (l2 + 0.5D - d7) / (d11 / 2.0D);

						if (d12 * d12 + d13 * d13 < 1.0D)
						{
							for (int i3 = k1; i3 <= j2; ++i3)
							{
								if (canGenerate(p_76484_1_, k2, l2, i3))
								{
									setBlock(p_76484_1_, k2, l2, i3, ModBlocks.ancient_debris, 0, 2);
								}
							}
						}
					}
				}
			}
		}

		return true;
	}
	
	private boolean canGenerate(World world, int x, int y, int z) {
		if(!world.getBlock(x, y, z).isReplaceableOreGen(world, x, y, z, field_150518_c)) return false;
		
		if(!shouldAirGen) {
			for(EnumFacing facing : EnumFacing.values()) {
				if(world.isAirBlock(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ())) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void setBlock(World world, int x, int y, int z, Block block, int meta, int flag) {
		world.setBlock(x, y, z, block, meta, flag);
	}
}