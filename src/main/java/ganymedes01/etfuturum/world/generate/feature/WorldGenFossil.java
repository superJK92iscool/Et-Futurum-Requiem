package ganymedes01.etfuturum.world.generate.feature;

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

		Fossil fossilSkull1 = new Fossil("/data/structure/fossil/skull_1.nbt", false);
		Fossil fossilSkullCoal1 = new Fossil("/data/structure/fossil/skull_1_coal.nbt", true);
		Fossil fossilSkull2 = new Fossil("/data/structure/fossil/skull_2.nbt", false);
		Fossil fossilSkullCoal2 = new Fossil("/data/structure/fossil/skull_2_coal.nbt", true);
		Fossil fossilSkull3 = new Fossil("/data/structure/fossil/skull_3.nbt", false);
		Fossil fossilSkullCoal3 = new Fossil("/data/structure/fossil/skull_3_coal.nbt", true);
		Fossil fossilSkull4 = new Fossil("/data/structure/fossil/skull_4.nbt", false);
		Fossil fossilSkullCoal4 = new Fossil("/data/structure/fossil/skull_4_coal.nbt", true);

		Fossil fossilSpine1 = new Fossil("/data/structure/fossil/spine_1.nbt", false);
		Fossil fossilSpineCoal1 = new Fossil("/data/structure/fossil/spine_1_coal.nbt", true);
		Fossil fossilSpine2 = new Fossil("/data/structure/fossil/spine_2.nbt", false);
		Fossil fossilSpineCoal2 = new Fossil("/data/structure/fossil/spine_2_coal.nbt", true);
		Fossil fossilSpine3 = new Fossil("/data/structure/fossil/spine_3.nbt", false);
		Fossil fossilSpineCoal3 = new Fossil("/data/structure/fossil/spine_3_coal.nbt", true);
		Fossil fossilSpine4 = new Fossil("/data/structure/fossil/spine_4.nbt", false);
		Fossil fossilSpineCoal4 = new Fossil("/data/structure/fossil/spine_4_coal.nbt", true);

		fossils = new ArrayList<>();

		fossils.add(new ImmutablePair<>(fossilSkull1, fossilSkullCoal1));
		fossils.add(new ImmutablePair<>(fossilSkull2, fossilSkullCoal2));
		fossils.add(new ImmutablePair<>(fossilSkull3, fossilSkullCoal3));
		fossils.add(new ImmutablePair<>(fossilSkull4, fossilSkullCoal4));

		fossils.add(new ImmutablePair<>(fossilSpine1, fossilSpineCoal1));
		fossils.add(new ImmutablePair<>(fossilSpine2, fossilSpineCoal2));
		fossils.add(new ImmutablePair<>(fossilSpine3, fossilSpineCoal3));
		fossils.add(new ImmutablePair<>(fossilSpine4, fossilSpineCoal4));
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		Pair<Fossil, Fossil> fossilPair = fossils.get(rand.nextInt(fossils.size()));
		ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(4) + 2);
		if (!canFossilGenerateHere(world, x, y, z, fossilPair.getLeft().getSize(dir))) return false;
		fossilPair.getLeft().buildStructure(world, rand, x, y, z, dir);
		fossilPair.getRight().buildStructure(world, rand, x, y, z, dir);
		return true;
	}

	protected boolean canFossilGenerateHere(World world, int x, int y, int z, BlockPos corners) {
		int air = 0;
		if (isInvalidCorner(world, x, y, z)) {
			air++;
		}
		if (isInvalidCorner(world, x + corners.getX(), y, z)) {
			air++;
		}
		if (isInvalidCorner(world, x, y, z + corners.getZ())) {
			air++;
		}
		if (isInvalidCorner(world, x + corners.getX(), y, z + corners.getZ())) {
			air++;
		}
		if (isInvalidCorner(world, x, y + corners.getY(), z)) {
			air++;
		}
		if (isInvalidCorner(world, x + corners.getX(), y + corners.getY(), z)) {
			if (air++ >= 5) return false;
		}
		if (isInvalidCorner(world, x, y + corners.getY(), z + corners.getZ())) {
			if (air++ >= 5) return false;
		}
		if (isInvalidCorner(world, x + corners.getX(), y + corners.getY(), z + corners.getZ())) {
			if (air++ >= 5) return false;
		}
		return air < 5;
	}

	private boolean isInvalidCorner(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		return block == Blocks.air || !block.isOpaqueCube();
	}

	public class Fossil extends NBTStructure {

		private boolean coal;

		public Fossil(String loc, boolean isCoal) {
			super(loc, isCoal ? 0.1F : 0.9F);
		}

		@Override
		public void init() {
			coal = getLocation().contains("coal");
		}

		@Override
		public Map<Integer, BlockStateContainer> createPalette(ForgeDirection facing) {
			Map<Integer, BlockStateContainer> map = new HashMap<>();
			if (coal) {
				map.put(0, new BlockStateContainer(Blocks.coal_ore, 0));
			} else {
				for (Pair<Integer, NBTTagCompound> pair : getPaletteNBT()) {
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
