package ganymedes01.etfuturum.world.generate;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Logger;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.helpers.BlockState;
import net.minecraft.nbt.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.*;

public abstract class NBTStructure {
    //TODO: Add crashes when the wrong args are used
    //Should trigger whenever the palette count does not match the map entry count, etc to prevent misuse and reduce possibility of user error

    private final NBTTagCompound compound;
    private final Map<Integer, BlockState> palette;
    private final Set<Pair<BlockPos, BlockState>> blocksInStructure;
    private final BlockPos size;
    private final float theIntegrity;

    public NBTStructure(String loc) {
        this(loc, 1);
    }

    public NBTStructure(String loc, float integrity) {
        InputStream file = EtFuturum.class.getResourceAsStream(loc);
        NBTTagCompound tempCompound = null;
        try {
            assert file.available() > 0;
            tempCompound = CompressedStreamTools.readCompressed(file);
            file.close();
        } catch (IOException e) {
            Logger.error("Failed to find or read structure NBT file for " + loc);
            e.printStackTrace();
        }
        compound = tempCompound;
        size = getPosFromTagList(compound.getTagList("size", 3));
        //TODO: Try compound.getIntArray on the above
        palette = createPalette();
        blocksInStructure = buildStructureMap();
        theIntegrity = MathHelper.clamp_float(integrity, 0, 1);
    }

    private Set<Pair<BlockPos, BlockState>> buildStructureMap() {
        HashSet<Pair<BlockPos, BlockState>> set = new HashSet<>();
        NBTTagList list = getCompound().getTagList("blocks", 10);
        for(int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound comp = list.getCompoundTagAt(i);
            set.add(new ImmutablePair<>(getPosFromTagList(comp.getTagList("pos", 3)), getPalette().get(comp.getInteger("state"))));
        }

        return set;
    }

    public NBTTagCompound getCompound() {
        return compound;
    }

    public Map<Integer, BlockState> getPalette() {
        return palette;
    }

    public void buildStructure(World world, int x, int y, int z, Random rand) {
        for(Pair<BlockPos, BlockState> pair : blocksInStructure) {
            if(getIntegrity() == 1 || rand.nextFloat() < getIntegrity()) {
                BlockPos pos = pair.getLeft();
                BlockState state = getBlockState(pair.getRight());
                Logger.info(state.getBlock() + " " + state.getMeta());
                world.setBlock(pos.getX() + x, pos.getY() + y, pos.getZ() + z, state.getBlock(), state.getMeta(), 3);
            }
        }
    }

    /**
     * Intended to be overridden by structures that have special placement requirements for blocks
     * Example: Fossils use this for their chance to replace the block with coal.
     */
    public BlockState getBlockState(BlockState state) {
        return state;
    }

    public float getIntegrity() {
        return theIntegrity;
    }

    public BlockPos getSize() {
        return size;
    }

    public NBTTagCompound getPaletteFromIndex(int i) {
        return getCompound().getTagList("palette", 10).getCompoundTagAt(i);
    }

    public String getBlockNamespaceFromPaletteEntry(int i) {
        return getPaletteFromIndex(i).getString("Name");
    }

    public int getPaletteCount() {
        return getCompound().getTagList("palette", 10).tagCount();
    }

    public Set<Pair<Integer, NBTTagCompound>> getPaletteNBT() {
        Set<Pair<Integer, NBTTagCompound>> set = new HashSet<>();
        for(int i = 0; i < getPaletteCount(); i++) {
            set.add(new ImmutablePair<>(i, getPaletteFromIndex(i)));
        }
        return set;
    }

    /**
     * Gets a properties from the "properties" portion of a BlockState palette entry in structure NBT
     * Returns NULL if the block has no state properties.
     */
    public String getBlockProperty(NBTTagCompound nbt, String property) {
        if (!nbt.hasKey("properties")) return null;
        return nbt.getCompoundTag("properties").getString(property);
    }

    /**
     * Used for converting the NBTTagLists found in structure NBT that contain exactly 3 ints, to a BlockPos.
     * This should ONLY be used on NBTTagLists that contain no more or less than 3 integers.
     */
    public BlockPos getPosFromTagList(NBTTagList list) {
        if(list.tagCount() != 3 && list.getId() != 3) {
            throw new IllegalArgumentException("This is not a BlockPos taglist!");
        }
        return new BlockPos(getIntFromTagList(list, 0), getIntFromTagList(list, 1), getIntFromTagList(list, 2));
    }

    protected int getIntFromTagList(NBTTagList list, int index) {
        if (index >= 0 && index < list.tagCount())
        {
            NBTBase nbtbase = (NBTBase)list.tagList.get(index);
            return nbtbase.getId() == 3 ? ((NBTTagInt) nbtbase).func_150287_d() : 0;
        }
        else
        {
            return 0;
        }
    }

    public abstract Map<Integer, BlockState> createPalette();
}
