package ganymedes01.etfuturum.world.generate.feature;

import com.google.common.collect.Lists;
import ganymedes01.etfuturum.api.mappings.RegistryMapping;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class WorldGenNetherFossil extends WorldGenFossil {

	private final List<Fossil> fossils;

	public WorldGenNetherFossil(RegistryMapping<Block> bone) {
		super(bone);
		fossils = Lists.newArrayList();
		for (int i = 1; i <= 14; i++) {
			fossils.add(new Fossil("/data/structure/nether_fossils/fossil_" + i + ".nbt"));
		}
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		Fossil fossil = fossils.get(rand.nextInt(fossils.size()));
		ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(4) + 2);
		if (!canFossilGenerateHere(world, x, y, z, fossil.getSize(dir))) return false;
		fossil.placeStructure(world, rand, x, y, z, dir);
		return true;
	}

	protected boolean canFossilGenerateHere(World world, int x, int y, int z, BlockPos corners) {
		int air = 0;
		if (!validCorner(world, x, y, z)) {
			air++;
		}
		if (!validCorner(world, x + corners.getX(), y, z)) {
			air++;
		}
		if (!validCorner(world, x, y, z + corners.getZ())) {
			air++;
		}
		if (!validCorner(world, x + corners.getX(), y, z + corners.getZ())) {
			if (air++ >= 3) return false;
		}
		if (!validCorner(world, x + (corners.getX() >> 1), y, z + (corners.getZ() >> 1))) {
			if (air++ >= 3) return false;
		}
		return air <= 3;
	}

	@Override
	protected boolean validCorner(World world, int x, int y, int z) {
		return world.getBlock(x, y - 1, z).isOpaqueCube() && world.isAirBlock(x, y, z);
	}
}
