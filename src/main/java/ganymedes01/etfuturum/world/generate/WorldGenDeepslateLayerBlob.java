package ganymedes01.etfuturum.world.generate;
import java.util.Random;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigTweaks;
import ganymedes01.etfuturum.core.utils.DeepslateOreRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class WorldGenDeepslateLayerBlob extends WorldGenMinable
{
	/** The number of blocks to generate. */
	private int numberOfBlocks;
	private boolean tuff;

	public WorldGenDeepslateLayerBlob(int numberOfBlocks, boolean tuff)
	{
		super(tuff ? ModBlocks.tuff : ModBlocks.deepslate, numberOfBlocks);
		this.numberOfBlocks = numberOfBlocks;
		this.tuff = tuff;
	}
	
	@Override
	public boolean generate(World world, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_)
	{
		float f = p_76484_2_.nextFloat() * (float)Math.PI;
		double d0 = p_76484_3_ + 8 + MathHelper.sin(f) * numberOfBlocks / 8.0F;
		double d1 = p_76484_3_ + 8 - MathHelper.sin(f) * numberOfBlocks / 8.0F;
		double d2 = p_76484_5_ + 8 + MathHelper.cos(f) * numberOfBlocks / 8.0F;
		double d3 = p_76484_5_ + 8 - MathHelper.cos(f) * numberOfBlocks / 8.0F;
		double d4 = p_76484_4_ + p_76484_2_.nextInt(3) - 2;
		double d5 = p_76484_4_ + p_76484_2_.nextInt(3) - 2;

		BlockAndMetadataMapping mapping;
		Block block;
		
		for (int l = 0; l <= numberOfBlocks; ++l)
		{
			double d6 = d0 + (d1 - d0) * l / numberOfBlocks;
			double d7 = d4 + (d5 - d4) * l / numberOfBlocks;
			double d8 = d2 + (d3 - d2) * l / numberOfBlocks;
			double d9 = p_76484_2_.nextDouble() * numberOfBlocks / 16.0D;
			double d10 = (MathHelper.sin(l * (float)Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
			double d11 = (MathHelper.sin(l * (float)Math.PI / numberOfBlocks) + 1.0F) * d9 + 1.0D;
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

								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
								{
									block = world.getBlock(x, y, z);
									
									if(block.getMaterial() != Material.air && block != ModBlocks.deepslate && block != ModBlocks.tuff && block != ModBlocks.cobbled_deepslate) {
										if((block.isReplaceableOreGen(world, x, y, z, Blocks.stone))) {
											world.setBlock(x, y, z, field_150519_a, 0, 2);
										} else if(!tuff && ConfigTweaks.deepslateReplacesCobblestone && (block.isReplaceableOreGen(world, x, y, z, Blocks.cobblestone))) {
											world.setBlock(x, y, z, ModBlocks.cobbled_deepslate, 0, 2);
										} else if(ConfigBlocksItems.enableDeepslateOres && block != Blocks.air) {
											if((mapping = DeepslateOreRegistry.getOre(block, world.getBlockMetadata(x, y, z))) != null) {
												world.setBlock(x, y, z, mapping.getBlock(), mapping.getMeta(), 2);
											}
										}
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
