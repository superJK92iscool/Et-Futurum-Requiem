package ganymedes01.etfuturum.world.generate.feature;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.ExternalContent;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.helpers.DoublePerlinNoiseSampler;
import ganymedes01.etfuturum.core.utils.helpers.Vec3i;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenAmethystGeode extends WorldGenerator {
	//I don't know how to make the sphere slightly warped in shape and I didn't understand the massive nest of noise-related classes
	//in vanilla, so I am just using a perfect circle for now, sorry!
	
	private final int outerMeta;
	private final List<Block> innerBlocks;
	
	public WorldGenAmethystGeode() {
		outerMeta = ConfigWorld.amethystOuterBlock == ExternalContent.netherlicious_basalt_bricks ? 6 : 0;
		innerBlocks = ImmutableList.of(ModBlocks.amethyst_cluster_1, ModBlocks.amethyst_cluster_2);
	}
	
	/*
	 * This is used when generating amethyst to check all 8 corners.
	 * This is so it doesn't generate in the middle of the air, ocean, hanging in trees, etc.
	 */
	private boolean isInvalidCorner(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return  block.isAir(world, x, y, z) || !block.isOpaqueCube() || world.canBlockSeeTheSky(x, y, z);
	}
	
	public boolean generate(World world, Random random, int x, int y, int z) {
//	      GeodeFeatureConfig geodeFeatureConfig = (GeodeFeatureConfig)context.getConfig();
	      BlockPos blockPos = new BlockPos(x, y, z);
//	      int i = geodeFeatureConfig.minGenOffset;
//	      int j = geodeFeatureConfig.maxGenOffset;
	      int i = -16;
	      int j = 16;
	      List<Pair<BlockPos, Integer>> list = Lists.newLinkedList();
//	      int k = geodeFeatureConfig.distributionPoints.get(random);
	      int k = random.nextBoolean() ? 3 : 4;
	      DoublePerlinNoiseSampler doublePerlinNoiseSampler = DoublePerlinNoiseSampler.create(random, -4, 1.0D);
	      List<BlockPos> list2 = Lists.newLinkedList();
//	      double d = (double)k / (double)geodeFeatureConfig.outerWallDistance.getMax();
	      double d = (double)k / (double)6;
//	      GeodeLayerThicknessConfig geodeLayerThicknessConfig = geodeFeatureConfig.layerThicknessConfig;
//	      GeodeLayerConfig geodeLayerConfig = geodeFeatureConfig.layerConfig;
//	      GeodeCrackConfig geodeCrackConfig = geodeFeatureConfig.crackConfig;
//	      double e = 1.0D / Math.sqrt(geodeLayerThicknessConfig.filling);
//	      double f = 1.0D / Math.sqrt(geodeLayerThicknessConfig.innerLayer + d);
//	      double g = 1.0D / Math.sqrt(geodeLayerThicknessConfig.middleLayer + d);
//	      double h = 1.0D / Math.sqrt(geodeLayerThicknessConfig.outerLayer + d);
//	      double l = 1.0D / Math.sqrt(geodeCrackConfig.baseCrackSize + random.nextDouble() / 2.0D + (k > 3 ? d : 0.0D));
//	      boolean bl = (double)random.nextFloat() < geodeCrackConfig.generateCrackChance;
	      double e = 1.0D / Math.sqrt(1.7D);
	      double f = 1.0D / Math.sqrt(2.2D + d);
	      double g = 1.0D / Math.sqrt(3.2D + d);
	      double h = 1.0D / Math.sqrt(4.2D + d);
	      double l = 1.0D / Math.sqrt(2.0D + random.nextDouble() / 2.0D + (k > 3 ? d : 0.0D));
	      boolean bl = (double)random.nextFloat() < 0.95D;
	      int m = 0;

	      int r;
	      int s;
	      BlockPos blockPos6;
	      Block blockState2;
	      for(r = 0; r < k; ++r) {
//	         s = geodeFeatureConfig.outerWallDistance.get(random);
//	         int p = geodeFeatureConfig.outerWallDistance.get(random);
//	         int q = geodeFeatureConfig.outerWallDistance.get(random);
	         s = MathHelper.getRandomIntegerInRange(random, 4, 6);
	         int p = MathHelper.getRandomIntegerInRange(random, 4, 6);
	         int q = MathHelper.getRandomIntegerInRange(random, 4, 6);
	         blockPos6 = blockPos.add(s, p, q);
//	         blockState2 = world.getBlock(blockPos6.getX(), blockPos6.getY(), blockPos6.getZ()); //We don't need this because we'll just get the block in my isInvalidCorner
	         if (isInvalidCorner(world, blockPos6.getX(), blockPos6.getY(), blockPos6.getZ())) {
	            ++m;
//	            if (m > geodeFeatureConfig.invalidBlocksThreshold) {
	            if (m > 1) {
	               return false; // Comment this line to disable the valid generation check for testing purposes.
	            }
	         }

//	         list.add(Pair.of(blockPos6, geodeFeatureConfig.pointOffset.get(random)));
	         list.add(Pair.of(blockPos6, random.nextBoolean() ? 1 : 2)); //Unused list, see below
	      }

	      if (bl) {
	         r = random.nextInt(4);
	         s = k * 2 + 1;
	         if (r == 0) {
	            list2.add(blockPos.add(s, 7, 0));
	            list2.add(blockPos.add(s, 5, 0));
	            list2.add(blockPos.add(s, 1, 0));
	         } else if (r == 1) {
	            list2.add(blockPos.add(0, 7, s));
	            list2.add(blockPos.add(0, 5, s));
	            list2.add(blockPos.add(0, 1, s));
	         } else if (r == 2) {
	            list2.add(blockPos.add(s, 7, s));
	            list2.add(blockPos.add(s, 5, s));
	            list2.add(blockPos.add(s, 1, s));
	         } else {
	            list2.add(blockPos.add(0, 7, 0));
	            list2.add(blockPos.add(0, 5, 0));
	            list2.add(blockPos.add(0, 1, 0));
	         }
	      }

	      List<BlockPos> list3 = Lists.newArrayList();
//	      Predicate<BlockState> predicate = notInBlockTagPredicate(geodeFeatureConfig.layerConfig.cannotReplace);
	      Iterator var48 = BlockPos.iterate(blockPos.add(i, i, i), blockPos.add(j, j, j)).iterator();

	      while(true) {
	         while(true) {
	            double u;
	            double v;
	            BlockPos blockPos3;
	            do {
	               if (!var48.hasNext()) {
//	                  List<Block> list4 = innerBlocks;
	                  Iterator var51 = list3.iterator();

	                  while(true) {
	                     while(var51.hasNext()) {
	                        blockPos6 = (BlockPos)var51.next();
	                        blockState2 = innerBlocks.get(random.nextInt(innerBlocks.size()));
	                        EnumFacing[] var53 = EnumFacing.values();
	                        int var37 = var53.length;

	                        for(int var54 = 0; var54 < var37; ++var54) {
	                        	EnumFacing direction2 = var53[var54];
//	                           if (blockState2.contains(Properties.FACING)) {
//	                              blockState2 = blockState2.with(Properties.FACING, direction2);
//	                           }

	                           BlockPos blockPos7 = blockPos6.offset(direction2);
	                           //Not needed; no waterlogging
//	                           Block blockState3 = world.getBlock(blockPos7.getX(), blockPos7.getY(), blockPos7.getZ());
//	                           if (blockState2.contains(Properties.WATERLOGGED)) {
//	                              blockState2 = (BlockState)blockState2.with(Properties.WATERLOGGED, blockState3.getFluidState().isStill());
//	                           }

	                           if (world.isAirBlock(blockPos7.getX(), blockPos7.getY(), blockPos7.getZ())) {
	                        	   world.setBlock(blockPos7.getX(), blockPos7.getY(), blockPos7.getZ(), blockState2, (random.nextBoolean() ? 0 : 6) + direction2.ordinal(), 2);
	                              break;
	                           }
	                        }
	                     }

	                     return true;
	                  }
	               }

	               blockPos3 = (BlockPos)var48.next();
//	               double t = doublePerlinNoiseSampler.sample((double)blockPos3.getX(), (double)blockPos3.getY(), (double)blockPos3.getZ()) * geodeFeatureConfig.noiseMultiplier;
	               double t = doublePerlinNoiseSampler.sample((double)blockPos3.getX(), (double)blockPos3.getY(), (double)blockPos3.getZ()) * 0.05D;
	               u = 0.0D;
	               v = 0.0D;

	               Iterator var40;
	               Pair pair;
	               for(var40 = list.iterator(); var40.hasNext(); u += Utils.fastInverseSqrt(blockPos3.getSquaredDistance((Vec3i)pair.getLeft()) + (double)(Integer)pair.getRight()) + t) {
	                  pair = (Pair)var40.next();
	               } //Almost deleted this code for being unused, but the variable in the for loop is vital to later parts of the code.

	               BlockPos blockPos4;
//	               for(var40 = list2.iterator(); var40.hasNext(); v += Utils.fastInverseSqrt(blockPos3.getSquaredDistance(blockPos4) + (double)geodeCrackConfig.crackPointOffset) + t) {
	               for(var40 = list2.iterator(); var40.hasNext(); v += Utils.fastInverseSqrt(blockPos3.getSquaredDistance(blockPos4) + (double)2) + t) {
	                  blockPos4 = (BlockPos)var40.next();
	               } //Almost deleted this code for being unused, but the variable in the for loop is vital to later parts of the code.
	            } while(u < h);

            	if(world.getBlock(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ()).getBlockHardness(world, blockPos3.getX(), blockPos3.getY(), blockPos3.getZ()) != -1) {
    	            if (bl && v >= l && u < e) {
                		world.setBlockToAir(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ());
//                       EnumFacing[] var56 = EnumFacing.values();
//    	               int var59 = var56.length;Possibly unneeded? variables

//    	               for(int var42 = 0; var42 < var59; ++var42) {Unneeded? for loop
//    	            	   EnumFacing direction = var56[var42];Unused variable
//    	                  BlockPos blockPos5 = blockPos3.offset(direction);Unused variable
    	            	   
    	            	   //Possibly unneeded code?
//    	                  FluidState fluidState = world.getFluidState(blockPos5);
//    	                  if (!fluidState.isEmpty()) {
//    	                     world.getFluidTickScheduler().schedule(blockPos5, fluidState.getFluid(), 0);
//    	                  }
//    	               }
    	            } else if (u >= e) {
    	               world.setBlockToAir(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ());//FillingProvider
    	               //Fun fact, comment out this line for some really odd shapes lol
    	            } else if (u >= f) {
//    	               boolean bl2 = (double)random.nextFloat() < geodeFeatureConfig.useAlternateLayer0Chance;
     	               boolean bl2 = (double)random.nextFloat() < 0.083D;
    	               if (bl2) {
        	               world.setBlock(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ(), ModBlocks.budding_amethyst);//AlternateInnerLayerProvider
    	               } else {
        	               world.setBlock(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ(), ModBlocks.amethyst_block);//InnerLayerProvider
    	               }

    	               //This boolean is always true and !true == false
//						if ((/* !geodeFeatureConfig.placementsRequireLayer0Alternate || */bl2) && (double)random.nextFloat() < geodeFeatureConfig.usePotentialPlacementsChance) {
						if ((/* !geodeFeatureConfig.placementsRequireLayer0Alternate || */bl2) && (double)random.nextFloat() < 0.35D) {
    	                  list3.add(new BlockPos(blockPos3));
    	               }
    	            } else if (u >= g) {
     	               world.setBlock(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ(), ModBlocks.calcite, 0, 2);//MiddleLayerProvider also TODO I need to make this layer configurable
    	            } else if (u >= h) {
     	               world.setBlock(blockPos3.getX(), blockPos3.getY(), blockPos3.getZ(), ConfigWorld.amethystOuterBlock, outerMeta, 2);//OuterLayerProvider
    	            }
    	         }
	         }
	      }
	   }
}
