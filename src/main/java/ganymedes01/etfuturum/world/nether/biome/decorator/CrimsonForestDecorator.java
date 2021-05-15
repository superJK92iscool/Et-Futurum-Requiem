package ganymedes01.etfuturum.world.nether.biome.decorator;

import ganymedes01.etfuturum.world.nether.generate.HugeCrimsonFungi0;
import net.minecraft.init.Blocks;
import net.minecraft.world.gen.feature.WorldGenerator;

public class CrimsonForestDecorator extends EtFuturumBiomeDecorator {
//
//  private final WorldGenerator genNetherrackSplatter = new BlockSplatter(Blocks.netherrack, 0, 128, ModBlocks.CrimsonNylium);
//  private final WorldGenerator genWartSplatter = new BlockSplatter(ModBlocks.nether_wart_block, 0, 128, Blocks.grass);
//
	private final WorldGenerator genHugeCrimsonFungi = new HugeCrimsonFungi0(false, 8, 0, 0, true, Blocks.log, Blocks.leaves); // Come back to
//  private final WorldGenerator genHugeCrimsonFungi1 = new HugeCrimsonFungi1(false, 5, 0, 0, true, ModBlocks.BackportLogs, ModBlocks.WartBlock);
//  private final WorldGenerator genHugeCrimsonFungi2 = new HugeCrimsonFungi2(false, 15, 0, 0, true, ModBlocks.BackportLogs, ModBlocks.WartBlock);
//  private final WorldGenerator genHugeCrimsonFungi3 = new HugeCrimsonFungi3(false, 8, 0, 0, true, ModBlocks.BackportLogs, ModBlocks.WartBlock);
//  private final WorldGenerator genHugeCrimsonFungi4 = new HugeCrimsonFungi4(false, 3, 0, 0, true, ModBlocks.BackportLogs, ModBlocks.WartBlock);
//  private final WorldGenerator genHugeCrimsonFungi5 = new HugeCrimsonFungi5(false, 15, 0, 0, true, ModBlocks.BackportLogs, ModBlocks.WartBlock);
//  private final WorldGenerator genHugeCrimsonFungi6 = new HugeCrimsonFungi3(false, 15, 0, 0, true, ModBlocks.BackportLogs, ModBlocks.WartBlock);
//
//  private final WorldGenerator genCrimsonRoots = new NormalGroupCrimsonRoots();
//  private final WorldGenerator genCrimsonSprouts = new NormalGroupCrimsonSprouts();
//  private final WorldGenerator genCrimsonFungi = new NormalGroupCrimsonFungi();
//
//  private final WorldGenerator genCrimsonWart = new CropWartPatchCrimson();
//
//  private final WorldGenerator genWarpedFungi = new SmallGroupWarpedFungi();
//
//  private final WorldGenerator genWeepingVines = new WeepingVinesGenerator();
//  
//  
//  
//
//  @Override
	protected void populate() {
//// lakes and stuff
	}
//
	@Override
	protected void decorate() {
//      
//      for (attempt = 0; attempt < 40; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//              genNetherrackSplatter.generate(world, rand, xx, yy, zz);
//          }
//      }
//
//      
//
//      for (attempt = 0; attempt < 40; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//              genWartSplatter.generate(world, rand, xx, yy, zz);
//          }
//      }
//
		for (attempt = 0; attempt < 1050; attempt++) {
			xx = x + offsetXZ();
			yy = 4 + rand.nextInt(116); // Come back to: Add higher Nether support
			zz = z + offsetXZ();

			if (world.getBlock(xx, yy - 1, zz) == Blocks.grass) {
				WorldGenerator treeGen = null;
				int var3 = rand.nextInt(3);

				if (var3 == 0) {
					xx = x + 9 + rand.nextInt(14);
					zz = z + 9 + rand.nextInt(14);
					treeGen = genHugeCrimsonFungi;

					if (treeGen != null)
						treeGen.generate(world, rand, xx, yy, zz);
				}

//              else if (var3 == 1) {
//
//                  xx = x + 9 + rand.nextInt(14);
//                  zz = z + 9 + rand.nextInt(14);
//                  treeGen = genHugeCrimsonFungi1;
//
//                  if (treeGen != null)
//                      treeGen.generate(world, rand, xx, yy, zz);
//              }
//
//              else {
//
//                  xx = x + 9 + rand.nextInt(14);
//                  zz = z + 9 + rand.nextInt(14);
//                  treeGen = genHugeCrimsonFungi3;
//
//                  if (treeGen != null)
//                      treeGen.generate(world, rand, xx, yy, zz);
//
//              }

			}
		}
//
//      for (attempt = 0; attempt < 750; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//              WorldGenerator treeGen = null;
//              int var2 = rand.nextInt(3);
//
//              if (var2 == 0) {
//
//                  xx = x + 9 + rand.nextInt(14);
//                  zz = z + 9 + rand.nextInt(14);
//                  treeGen = genHugeCrimsonFungi2;
//
//                  if (treeGen != null)
//                      treeGen.generate(world, rand, xx, yy, zz);
//
//              }
//
//              else if (var2 == 1) {
//
//                  xx = x + 9 + rand.nextInt(14);
//
//                  zz = z + 9 + rand.nextInt(14);
//                  treeGen = genHugeCrimsonFungi5;
//
//                  if (treeGen != null)
//                      treeGen.generate(world, rand, xx, yy, zz);
//
//              }
//
//              else {
//
//                  xx = x + 9 + rand.nextInt(14);
//
//                  zz = z + 9 + rand.nextInt(14);
//                  treeGen = genHugeCrimsonFungi6;
//
//                  if (treeGen != null)
//                      treeGen.generate(world, rand, xx, yy, zz);
//
//              }
//
//          }
//      }
//
//      for (attempt = 0; attempt < 750; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//              WorldGenerator treeGen = null;
//
//              xx = x + 9 + rand.nextInt(14);
//
//              zz = z + 9 + rand.nextInt(14);
//              treeGen = genHugeCrimsonFungi4;
//
//              if (treeGen != null)
//                  treeGen.generate(world, rand, xx, yy, zz);
//          }
//      }
//      
//
//      for (attempt = 0; attempt < 220; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//              genCrimsonRoots.generate(world, rand, xx, yy, zz);
//          }
//      }
//
//      for (attempt = 0; attempt < 320; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//              genCrimsonSprouts.generate(world, rand, xx, yy, zz);
//          }
//      }
//
//      for (attempt = 0; attempt < 70; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//              genCrimsonFungi.generate(world, rand, xx, yy, zz);
//          }
//      }
//
//      for (attempt = 0; attempt < 35; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//              genWarpedFungi.generate(world, rand, xx, yy, zz);
//          }
//      }
//
//      for (attempt = 0; attempt < 80; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy + 1, zz) == ModBlocks.CrimsonNylium
//                  || world.getBlock(xx, yy + 1, zz) == Blocks.netherrack) {
//              genWeepingVines.generate(world, rand, xx, yy, zz);
//          }
//      }
//
//      for (attempt = 0; attempt < 800; attempt++) {
//          xx = x + offsetXZ();
//          yy = 4 + rand.nextInt(116);
//          zz = z + offsetXZ();
//
//          if (world.getBlock(xx, yy + 1, zz) == ModBlocks.WartBlock) {
//              genWeepingVines.generate(world, rand, xx, yy, zz);
//          }
//      }
//
//      if (CropConfiguration.WartPatch) {
//          for (attempt = 0; attempt < 35; attempt++) {
//              xx = x + offsetXZ();
//              yy = 5 + rand.nextInt(115);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  genCrimsonWart.generate(world, rand, xx, yy, zz);
//              }
//          }
//      }
//
//
//      
//      if (WorldgenConfiguration.BigNether) {
//
//          for (attempt = 0; attempt < 120; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(120);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  genNetherrackSplatter.generate(world, rand, xx, yy, zz);
//              }
//          }
//
//          for (attempt = 0; attempt < 120; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(120);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  genWartSplatter.generate(world, rand, xx, yy, zz);
//              }
//          }
//
//          for (attempt = 0; attempt < 1200; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(120);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  WorldGenerator treeGen = null;
//                  int var3 = rand.nextInt(3);
//
//                  if (var3 == 0) {
//                      xx = x + 9 + rand.nextInt(14);
//                      zz = z + 9 + rand.nextInt(14);
//                      treeGen = genHugeCrimsonFungi;
//
//                      if (treeGen != null)
//                          treeGen.generate(world, rand, xx, yy, zz);
//                  }
//
//                  else if (var3 == 1) {
//
//                      xx = x + 9 + rand.nextInt(14);
//                      zz = z + 9 + rand.nextInt(14);
//                      treeGen = genHugeCrimsonFungi1;
//
//                      if (treeGen != null)
//                          treeGen.generate(world, rand, xx, yy, zz);
//                  }
//
//                  else {
//
//                      xx = x + 9 + rand.nextInt(14);
//                      zz = z + 9 + rand.nextInt(14);
//                      treeGen = genHugeCrimsonFungi3;
//
//                      if (treeGen != null)
//                          treeGen.generate(world, rand, xx, yy, zz);
//
//                  }
//
//              }
//          }
//
//          for (attempt = 0; attempt < 800; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(120);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  WorldGenerator treeGen = null;
//                  int var2 = rand.nextInt(3);
//
//                  if (var2 == 0) {
//
//                      xx = x + 9 + rand.nextInt(14);
//                      zz = z + 9 + rand.nextInt(14);
//                      treeGen = genHugeCrimsonFungi2;
//
//                      if (treeGen != null)
//                          treeGen.generate(world, rand, xx, yy, zz);
//
//                  }
//
//                  else if (var2 == 1) {
//
//                      xx = x + 9 + rand.nextInt(14);
//
//                      zz = z + 9 + rand.nextInt(14);
//                      treeGen = genHugeCrimsonFungi5;
//
//                      if (treeGen != null)
//                          treeGen.generate(world, rand, xx, yy, zz);
//
//                  }
//
//                  else {
//
//                      xx = x + 9 + rand.nextInt(14);
//
//                      zz = z + 9 + rand.nextInt(14);
//                      treeGen = genHugeCrimsonFungi6;
//
//                      if (treeGen != null)
//                          treeGen.generate(world, rand, xx, yy, zz);
//
//                  }
//
//              }
//          }
//
//          for (attempt = 0; attempt < 666; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(120);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  WorldGenerator treeGen = null;
//
//                  xx = x + 9 + rand.nextInt(14);
//
//                  zz = z + 9 + rand.nextInt(14);
//                  treeGen = genHugeCrimsonFungi4;
//
//                  if (treeGen != null)
//                      treeGen.generate(world, rand, xx, yy, zz);
//              }
//          }
//
//          for (attempt = 0; attempt < 220; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(120);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  genCrimsonRoots.generate(world, rand, xx, yy, zz);
//              }
//          }
//
//          for (attempt = 0; attempt < 366; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(120);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  genCrimsonSprouts.generate(world, rand, xx, yy, zz);
//              }
//          }
//
//          for (attempt = 0; attempt < 53; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(120);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  genCrimsonFungi.generate(world, rand, xx, yy, zz);
//              }
//          }
//
//          for (attempt = 0; attempt < 26; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(130);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                  genWarpedFungi.generate(world, rand, xx, yy, zz);
//              }
//          }
//
//          for (attempt = 0; attempt < 70; attempt++) {
//              xx = x + offsetXZ();
//              yy = 121 + rand.nextInt(130);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy + 1, zz) == ModBlocks.CrimsonNylium
//                      || world.getBlock(xx, yy + 1, zz) == Blocks.netherrack) {
//                  genWeepingVines.generate(world, rand, xx, yy, zz);
//              }
//          }
//
//          for (attempt = 0; attempt < 800; attempt++) {
//              xx = x + offsetXZ();
//              yy = 120 + rand.nextInt(130);
//              zz = z + offsetXZ();
//
//              if (world.getBlock(xx, yy + 1, zz) == ModBlocks.WartBlock) {
//                  genWeepingVines.generate(world, rand, xx, yy, zz);
//              }
//          }
//
//          if (CropConfiguration.WartPatch) {
//              for (attempt = 0; attempt < 20; attempt++) {
//                  xx = x + offsetXZ();
//                  yy = 120 + rand.nextInt(130);
//                  zz = z + offsetXZ();
//
//                  if (world.getBlock(xx, yy - 1, zz) == ModBlocks.CrimsonNylium) {
//                      genCrimsonWart.generate(world, rand, xx, yy, zz);
//                  }
//              }
//          }
//
		}
//
//  }

}