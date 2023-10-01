package ganymedes01.etfuturum.world.generate.feature;

import com.google.common.collect.Lists;
import ganymedes01.etfuturum.api.mappings.RegistryMapping;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.structurenbt.BlockStateContainer;
import ganymedes01.etfuturum.core.utils.structurenbt.BlockStateUtils;
import ganymedes01.etfuturum.world.generate.NBTStructure;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class WorldGenFossil extends WorldGenerator {

	private final List<Pair<Fossil, Fossil>> fossils;
	private final RegistryMapping<Block> bone;

	public WorldGenFossil(RegistryMapping<Block> bone) {

		this.bone = bone;

		fossils = Lists.newArrayList();

		for (int i = 1; i <= 4; i++) {
			fossils.add(new ImmutablePair<>(new Fossil("/data/structure/fossil/skull_" + i + ".nbt"), new Fossil("/data/structure/fossil/skull_" + i + "_coal.nbt")));
			fossils.add(new ImmutablePair<>(new Fossil("/data/structure/fossil/spine_" + i + ".nbt"), new Fossil("/data/structure/fossil/spine_" + i + "_coal.nbt")));
		}
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		Pair<Fossil, Fossil> fossilPair = fossils.get(rand.nextInt(fossils.size()));
		ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(4) + 2);
		if (!canFossilGenerateHere(world, x, y, z, fossilPair.getLeft().getSize(dir))) return false;
		fossilPair.getLeft().placeStructure(world, rand, x, y, z, dir, 0.9F);
		fossilPair.getRight().placeStructure(world, rand, x, y, z, dir, 0.1F);
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
			air++;
		}
		if (!validCorner(world, x, y + corners.getY(), z)) {
			air++;
		}
		if (!validCorner(world, x + corners.getX(), y + corners.getY(), z)) {
			if (air++ >= 5) return false;
		}
		if (!validCorner(world, x, y + corners.getY(), z + corners.getZ())) {
			if (air++ >= 5) return false;
		}
		if (!validCorner(world, x + corners.getX(), y + corners.getY(), z + corners.getZ())) {
			if (air++ >= 5) return false;
		}
		return air < 5;
	}

	protected boolean validCorner(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return block != Blocks.air && block.isOpaqueCube();
	}

	public class Fossil extends NBTStructure {

		public Fossil(String loc) {
			super(loc);
		}

		@Override
		public Map<Integer, BlockStateContainer> createPalette(ForgeDirection facing, Set<Pair<Integer, NBTTagCompound>> paletteNBT) {
			Map<Integer, BlockStateContainer> map = new HashMap<>();
			for (Pair<Integer, NBTTagCompound> pair : paletteNBT) {
				String namespace = getBlockNamespaceFromNBT(pair.getRight());
				if (namespace.equals("minecraft:air")) {
					map.put(pair.getLeft(), new BlockStateContainer(Blocks.air, 0));
				} else if (namespace.equals("minecraft:coal_ore")) {
					map.put(pair.getLeft(), new BlockStateContainer(Blocks.coal_ore, 0));
				} else {
					String axis = getProperties(pair.getRight()).get("axis");
					map.put(pair.getLeft(), new BlockStateContainer(bone.getObject(), getMetaFromStateOrQuartzPillar("axis", axis, facing) + bone.getMeta()));
				}
			}
			return map;
		}
	}

	private int getMetaFromStateOrQuartzPillar(String stateName, String state, ForgeDirection facing) {
		if (bone.getObject() == Blocks.quartz_block || bone.getMeta() == 2) {
			//For rotatable pillars like logs
			if (stateName.equals("axis")) {
				//NOTE: Different meta values for quartz pillar needed
				switch (state) {
					case "y":
						return 2;
					case "x":
						return facing == ForgeDirection.NORTH || facing == ForgeDirection.SOUTH ? 3 : 4;
					case "z":
						return facing == ForgeDirection.NORTH || facing == ForgeDirection.SOUTH ? 4 : 3;
				}
			}
		} else if (bone.getObject() instanceof BlockRotatedPillar) {
			return BlockStateUtils.getMetaFromState(stateName, state, facing);
		}
		return bone.getMeta();
	}
}
