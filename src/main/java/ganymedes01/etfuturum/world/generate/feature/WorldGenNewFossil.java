package ganymedes01.etfuturum.world.generate.feature;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.helpers.BlockState;
import ganymedes01.etfuturum.core.utils.helpers.BlockStateUtils;
import ganymedes01.etfuturum.world.generate.NBTStructure;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class WorldGenNewFossil extends WorldGenerator {

    private final Fossil fossilSkull1 = new Fossil("/data/structure/fossil/skull_1.nbt", false);
    private final Fossil fossilSkullCoal1 = new Fossil("/data/structure/fossil/skull_1_coal.nbt", true);
    private final Fossil fossilSkull2 = new Fossil("/data/structure/fossil/skull_2.nbt", false);
    private final Fossil fossilSkullCoal2 = new Fossil("/data/structure/fossil/skull_2_coal.nbt", true);
    private final Fossil fossilSkull3 = new Fossil("/data/structure/fossil/skull_3.nbt", false);
    private final Fossil fossilSkullCoal3 = new Fossil("/data/structure/fossil/skull_3_coal.nbt", true);
    private final Fossil fossilSkull4 = new Fossil("/data/structure/fossil/skull_4.nbt", false);
    private final Fossil fossilSkullCoal4 = new Fossil("/data/structure/fossil/skull_4_coal.nbt", true);

    private final Fossil fossilSpine1 = new Fossil("/data/structure/fossil/spine_1.nbt", false);
    private final Fossil fossilSpineCoal1 = new Fossil("/data/structure/fossil/spine_1_coal.nbt", true);
    private final Fossil fossilSpine2 = new Fossil("/data/structure/fossil/spine_2.nbt", false);
    private final Fossil fossilSpineCoal2 = new Fossil("/data/structure/fossil/spine_2_coal.nbt", true);
    private final Fossil fossilSpine3 = new Fossil("/data/structure/fossil/spine_3.nbt", false);
    private final Fossil fossilSpineCoal3 = new Fossil("/data/structure/fossil/spine_3_coal.nbt", true);
    private final Fossil fossilSpine4 = new Fossil("/data/structure/fossil/spine_4.nbt", false);
    private final Fossil fossilSpineCoal4 = new Fossil("/data/structure/fossil/spine_4_coal.nbt", true);

    private final List<Pair<Fossil, Fossil>> fossils;

    public WorldGenNewFossil() {
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
        fossilPair.getLeft().buildStructure(world, x, y, z, rand);
//        fossilPair.getRight().buildStructure(world, x, y, z, rand);//This is meant to place coal over the fossils, for some reason it does not work...?
        //For some reason it just places upright fossils instead. Will investigate soon
        return true;
    }

    public class Fossil extends NBTStructure {

        private final boolean coal;

        public Fossil(String loc, boolean isCoal) {
            super(loc, isCoal ? 0.1F : 0.9F);
            coal = isCoal;
        }

        @Override
        public Map<Integer, BlockState> createPalette() {
            Map<Integer, BlockState> map = new HashMap<>();
            if(coal) {
                map.put(0, new BlockState(Blocks.coal_ore, 0));
            } else { //TODO: Configurable bone block
                for(Pair<Integer, NBTTagCompound> pair : getPaletteNBT()) {
                    String axis = pair.getRight().getCompoundTag("Properties").getString("axis");//Todo: Add helper function to get the properties
                    map.put(pair.getLeft(), new BlockState(ModBlocks.bone_block, BlockStateUtils.getMetaFromState("axis", axis)));
                }
            }
            return map;
        }
    }

}
