package ganymedes01.etfuturum.world.generate.feature;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.helpers.BlockState;
import ganymedes01.etfuturum.core.utils.helpers.BlockStateUtils;
import ganymedes01.etfuturum.world.generate.NBTStructure;
import net.minecraft.block.Block;
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

    public WorldGenFossil() {
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

        fossils.add(new ImmutablePair<Fossil, Fossil>(fossilSkull1, fossilSkullCoal1));
        fossils.add(new ImmutablePair<Fossil, Fossil>(fossilSkull2, fossilSkullCoal2));
        fossils.add(new ImmutablePair<Fossil, Fossil>(fossilSkull3, fossilSkullCoal3));
        fossils.add(new ImmutablePair<Fossil, Fossil>(fossilSkull4, fossilSkullCoal4));

        fossils.add(new ImmutablePair<Fossil, Fossil>(fossilSpine1, fossilSpineCoal1));
        fossils.add(new ImmutablePair<Fossil, Fossil>(fossilSpine2, fossilSpineCoal2));
        fossils.add(new ImmutablePair<Fossil, Fossil>(fossilSpine3, fossilSpineCoal3));
        fossils.add(new ImmutablePair<Fossil, Fossil>(fossilSpine4, fossilSpineCoal4));
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        Pair<Fossil, Fossil> fossilPair = fossils.get(rand.nextInt(fossils.size()));
        ForgeDirection dir = ForgeDirection.getOrientation(rand.nextInt(4) + 2);
        if(!canFossilGenerateHere(world, x, y, z, fossilPair.getLeft().getSize(dir), dir)) return false;
        fossilPair.getLeft().buildStructure(world, rand, x, y, z, dir);
        fossilPair.getRight().buildStructure(world, rand, x, y, z, dir);
        return true;
    }

    private boolean canFossilGenerateHere(World world, int x, int y, int z, BlockPos corners, ForgeDirection dir) {
        int air = 0;
        if(isInvalidCorner(world, x, y, z)) {
            air++;
        }
        if(isInvalidCorner(world, x + corners.getX(), y, z)) {
            air++;
        }
        if(isInvalidCorner(world, x, y, z + corners.getZ())) {
            air++;
        }
        if(isInvalidCorner(world, x + corners.getX(), y, z + corners.getZ())) {
            air++;
        }
        if(isInvalidCorner(world, x, y + corners.getY(), z)) {
            air++;
        }
        if(isInvalidCorner(world, x + corners.getX(), y + corners.getY(), z)) {
            if(air++ >= 5) return false;
        }
        if(isInvalidCorner(world, x, y + corners.getY(), z + corners.getZ())) {
            if(air++ >= 5) return false;
        }
        if(isInvalidCorner(world, x + corners.getX(), y + corners.getY(), z + corners.getZ())) {
            if(air++ >= 5) return false;
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
        public Map<Integer, BlockState> createPalette(ForgeDirection facing) {
            Map<Integer, BlockState> map = new HashMap<>();
            //Coal fossil, I check if the integrity is 0.1
            //We check this way because Java compilers absolutely INSIST the super MUST be first for some stupid reason
            if (getIntegrity() == 0.1F) {
                map.put(0, new BlockState(Blocks.coal_ore, 0));
            } else { //TODO: Configurable bone block
                for (Pair<Integer, NBTTagCompound> pair : getPaletteNBT()) {
                    String axis = pair.getRight().getCompoundTag("Properties").getString("axis");//Todo: Add helper function to get the properties
                    map.put(pair.getLeft(), new BlockState(ModBlocks.bone_block, BlockStateUtils.getMetaFromState("axis", axis, facing)));
                }
            }
            return map;
        }
    }
}
