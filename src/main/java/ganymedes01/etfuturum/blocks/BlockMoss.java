package ganymedes01.etfuturum.blocks;

import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.sound.ModSounds;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BlockMoss extends BaseBlock implements IGrowable {

	public BlockMoss() {
		super(Material.plants);
		setHardness(0.1F);
		setResistance(0.1F);
		setNames("moss_block");
		setHarvestLevel("hoe", 0);
		setBlockSound(ModSounds.soundMoss);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean isClient) {
		// Implement your logic for whether the block can grow here.
		// For example, return true if growth conditions are met.
		return true;
	}

	@Override
	public boolean func_149852_a(World world, Random rand, int x, int y, int z) {
		// Implement your logic for the random chance of growth here.
		// For example, return true based on a random chance.
		return true;
	}

	// Adjust the weights as needed
	private static final int AZALEA_BUSH = 10;
	private static final int FLOWERING_AZALEA = 10;
	private static final int DOUBLE_TALL_GRASS = 10;
	private static final int TALL_GRASS_WEIGHT = 40;
	private static final int MOSS_CARPET_WEIGHT = 30;

	@Override
	public void func_149853_b(World world, Random rand, int xCoord, int yCoord, int zCoord) {
		int l = 0;
		int maxHeight = world.getHeight();

		while (l < 128) {
			int i1 = xCoord;
			int j1 = yCoord - 1; // Decrease Y to check below the current block
			int k1 = zCoord;
			int l1 = 0;

			while (true) {
				if (l1 < l / 16) {
					i1 += rand.nextInt(3) - 1;
					j1 += (rand.nextInt(3) - 1) * rand.nextInt(3) / 2;
					k1 += rand.nextInt(3) - 1;

					if (j1 >= maxHeight) {
						// Ensure we're not above the world height
						break;
					}

					Block blockBelow = world.getBlock(i1, j1, k1);
					Block blockAtPos = world.getBlock(i1, j1 + 1, k1); // Check the block above

					if ((blockBelow != Blocks.grass && blockBelow != Blocks.stone && blockBelow != Blocks.dirt && blockBelow != ModBlocks.MOSS_BLOCK.get()) &&
							!blockAtPos.isNormalCube()) {
						++l1;
						continue;
					}
					else {
						// Replace ground block fully with Moss Block
						world.setBlock(i1, j1, k1, ModBlocks.MOSS_BLOCK.get(), 0, 2);

						if (blockAtPos == Blocks.air) {
							// Randomly choose what to place on top based on weighting percentages
							int randomOption = rand.nextInt(AZALEA_BUSH + FLOWERING_AZALEA + DOUBLE_TALL_GRASS + MOSS_CARPET_WEIGHT + TALL_GRASS_WEIGHT);
							if (randomOption < AZALEA_BUSH) {
								if (Blocks.red_flower.canBlockStay(world, i1, j1 + 1, k1)) {
									world.setBlock(i1, j1 + 1, k1, Blocks.red_flower, 0, 2);
								}
							} else if (randomOption < AZALEA_BUSH + FLOWERING_AZALEA) {
								if (Blocks.yellow_flower.canBlockStay(world, i1, j1 + 1, k1)) {
									world.setBlock(i1, j1 + 1, k1, Blocks.yellow_flower, 0, 2);
								}
							} else if (randomOption < AZALEA_BUSH + FLOWERING_AZALEA + DOUBLE_TALL_GRASS) {
								// Extreme Tall Grass
								if (Blocks.double_plant.canBlockStay(world, i1, j1 + 1, k1)) {
									world.setBlock(i1, j1 + 1, k1, Blocks.double_plant, 1, 2);
								}
							} else if (randomOption < AZALEA_BUSH + FLOWERING_AZALEA + DOUBLE_TALL_GRASS + MOSS_CARPET_WEIGHT) {
								// Place Moss Carpet
								if (ModBlocks.MOSS_CARPET.get().canBlockStay(world, i1, j1 + 1, k1)) {
									world.setBlock(i1, j1 + 1, k1, ModBlocks.MOSS_CARPET.get(), 0, 2);
								}
							} else {
								// Place Tall Grass
								if (Blocks.tallgrass.canBlockStay(world, i1, j1 + 1, k1)) {
									world.setBlock(i1, j1 + 1, k1, Blocks.tallgrass, 1, 3);
								}
							}
						}
					}
					++l1;
					continue;
				} else {
					++l;
					break;
				}
			}
		}
	}

	public boolean place(World worldIn, Random rand, int x, int y, int z) {
		if (!worldIn.isAirBlock(x, y, z)) {
			return false;
		} else {
			int chance = 0;
			int xRandom = rand.nextInt(3) + 1;
			int zRandom = rand.nextInt(3) + 1;

			for (int xOffset = -xRandom; xOffset < xRandom; ++xOffset) {
				for (int zOffset = -zRandom; zOffset < zRandom; ++zOffset) {
					int newX = x + xOffset;
					int newZ = z + zOffset;
					chance += placeFeature(worldIn, rand, newX, y, newZ);
				}
			}

			return chance > 0;
		}
	}

	private int placeFeature(World worldIn, Random rand, int x, int y, int z) {
		int chance = 0;
		Block blockBelow = worldIn.getBlock(x, y - 1, z);

		if (worldIn.isAirBlock(x, y, z) && isSolid(blockBelow)) {
			createMossPatch(worldIn, rand, x, y - 1, z);

			if (rand.nextFloat() < 0.8F) {
				Block vegetationBlock = getVegetationBlock(rand);

				if (vegetationBlock.canBlockStay(worldIn, x, y, z)) {
					if (vegetationBlock == Blocks.double_plant && worldIn.isAirBlock(x, y + 1, z)) {
						// Place tall grass or other double block manually
						worldIn.setBlock(x, y, z, Blocks.double_plant, 2, 2);
						worldIn.setBlock(x, y + 1, z, Blocks.double_plant, 10, 2);
					} else {
						worldIn.setBlock(x, y, z, vegetationBlock, 0, 2);
					}

					++chance;
				}
			}
		}

		return chance;
	}

	private void createMossPatch(World worldIn, Random rand, int x, int y, int z) {
		if (worldIn.isAirBlock(x, y, z)) {
			worldIn.setBlock(x, y, z, ModBlocks.MOSS_BLOCK.get(), 0, 2);
		}
	}

	private Block getVegetationBlock(Random rand) {
		int chance = rand.nextInt(100) + 1;
		if (chance < 5) {
			// Flowering Azalea
			return ModBlocks.MOSS_CARPET.get();
		} else if (chance < 15) {
			// Azalea
			return ModBlocks.MOSS_CARPET.get();
		} else if (chance < 40) {
			return ModBlocks.MOSS_CARPET.get();
		} else {
			return chance < 90 ? Blocks.tallgrass : Blocks.double_plant;
		}
	}

	private boolean isSolid(Block block) {
		return block.isNormalCube();
	}
}
