package ganymedes01.etfuturum.world.nether.biome.decorator;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.world.generate.decorate.WorldGenHugeFungus;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class SoulSandValleyDecorator extends NetherBiomeDecorator {

	private final WorldGenerator crimsonGrassGenerator;

	public SoulSandValleyDecorator() {
		crimsonGrassGenerator = new WorldGenFlowers(ModBlocks.NETHER_ROOTS.get());
	}

	@Override
	public void populate(World world, Random rand, int chunkX, int chunkZ) {
		if (ModBlocks.NETHER_ROOTS.isEnabled() && rand.nextInt(3) == 0) {
			int x = chunkX + rand.nextInt(16) + 8;
			int y = 32 + rand.nextInt(88);
			int z = chunkZ + rand.nextInt(16) + 8;

			crimsonGrassGenerator.generate(world, rand, x, y, z);
		}
	}

	@Override
	public void populateBig(World world, Random rand, int chunkX, int chunkZ) {

	}

	@Override
	public void decorate(World world, Random rand, int chunkX, int chunkZ) {
	}
}
