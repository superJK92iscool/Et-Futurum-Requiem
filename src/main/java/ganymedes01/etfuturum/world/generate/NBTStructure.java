package ganymedes01.etfuturum.world.generate;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Logger;
import ganymedes01.etfuturum.core.utils.helpers.BlockPos;
import ganymedes01.etfuturum.core.utils.helpers.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.*;

public abstract class NBTStructure {
    //TODO: Add crashes when the wrong args are used
    //Should trigger whenever the palette count does not match the map entry count, etc to prevent misuse and reduce possibility of user error

    private final NBTTagCompound compound;
    private final Map<Integer, BlockState>[] palettes;
    private final Set<Pair<BlockPos, BlockState>>[] blocksInStructure;
    private final BlockPos[] sizes;
    private final float theIntegrity;

    public NBTStructure(String loc) {
        this(loc, 1);
    }

    public NBTStructure(String loc, float integrity) {
        InputStream file = EtFuturum.class.getResourceAsStream(loc);
        NBTTagCompound tempCompound = null;
        try {
            tempCompound = CompressedStreamTools.readCompressed(file);
            file.close();
        } catch (IOException e) {
            Logger.error("Failed to find or read structure NBT file for " + loc);
            e.printStackTrace();
        }
        theIntegrity = MathHelper.clamp_float(integrity, 0, 1);
        compound = tempCompound;
        BlockPos size = getPosFromTagList(compound.getTagList("size", 3));
        sizes = new BlockPos[] {size, new BlockPos(size.getZ(), size.getY(), size.getX())};
        //sizes[0] is north and south, [1] (z/x flipped) is east/west
        palettes = createPalettes();
        blocksInStructure = buildStructureMaps();
    }

    private Set<Pair<BlockPos, BlockState>>[] buildStructureMaps() {
        Set<Pair<BlockPos, BlockState>>[] sets = new Set[] {null, null, null, null};
        for(int facing = 0; facing < 4; facing++) {
            Set<Pair<BlockPos, BlockState>> set = new HashSet<>();
            NBTTagList list = getCompound().getTagList("blocks", 10);
            BlockPos size = getSize(getFacingFromInt(facing));
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound comp = list.getCompoundTagAt(i);
                BlockPos pos = getPosFromTagList(comp.getTagList("pos", 3));
                if(facing == 1) { //South
                    pos = new BlockPos(size.getX() - pos.getX(), pos.getY(), size.getZ() - pos.getZ());
                } else if (facing == 2) { //West
                    pos = new BlockPos(pos.getZ(), pos.getY(), pos.getX());
                } else if (facing == 3) { //East
                    pos = new BlockPos(size.getX() - pos.getZ(), pos.getY(), size.getZ() - pos.getX());
                }
                set.add(new ImmutablePair<>(pos, getPalettes()[facing].get(comp.getInteger("state"))));
            }
            sets[facing] = set; //Add this to the structure map for this facing direction
        }

        return sets;
    }

    public NBTTagCompound getCompound() {
        return compound;
    }

    public Map<Integer, BlockState>[] getPalettes() {
        return palettes;
    }

    public void buildStructure(World world, Random rand, int x, int y, int z) {
        buildStructure(world, rand, x, y, z, getFacingFromInt(rand.nextInt(4)));
    }
    public void buildStructure(World world, Random rand, int x, int y, int z, ForgeDirection facing) {
        Set<Pair<BlockPos, BlockState>> blockSet = blocksInStructure[getIntFromFacing(facing)];
        Set<Pair<BlockPos, BlockState>> entitySet = new HashSet<>(); //Yes entities are stored in the BlockState class for simplicity because I don't want the map to use Object...
        for(Pair<BlockPos, BlockState> pair : blockSet) {
            if (pair.getRight().getType() == BlockState.BlockStateType.ENTITY) { //We want to do entities last
                entitySet.add(pair);
                continue;
            }
            if (getIntegrity() == 1 || rand.nextFloat() < getIntegrity()) {
                BlockPos pos = pair.getLeft();
                setBlockState(world, rand, pos.getX() + x, pos.getY() + y, pos.getZ() + z, facing, pair.getRight());
            }
        }
        for(Pair<BlockPos, BlockState> pair : entitySet) {
            Entity entity = pair.getRight().createNewEntity(world);
            entity.readFromNBT(pair.getRight().getCompound());
            entity.setLocationAndAngles(pair.getLeft().getX() + x, pair.getLeft().getY() + y, pair.getLeft().getZ() + z, 0, 0);
            world.spawnEntityInWorld(entity);
        }
    }

    /**
     * Intended to be overridden by structures that have special placement requirements for blocks
     * You can also override this if you want more than one loot table per structure.
     */
    public void setBlockState(World world, Random rand, int x, int y, int z, ForgeDirection facing, BlockState state) {
        world.setBlock(x, y, z, state.getBlock(), state.getMeta(), 3);
        world.markBlockForUpdate(x, y, z);
        if(state.getType() == BlockState.BlockStateType.BLOCK_ENTITY) {
            TileEntity te = world.getTileEntity(x, y, z);
            if(te != null) {
                if (state.getCompound() != null) {
                    state.getCompound().setInteger("x", x);
                    state.getCompound().setInteger("y", y);
                    state.getCompound().setInteger("z", z);
                    te.readFromNBT(state.getCompound());
                }
                if(te instanceof IInventory && state.getLootTable() != null) {
                    WeightedRandomChestContent.generateChestContents(world.rand, state.getLootTable().getItems(world.rand), (IInventory) te, state.getLootTable().getCount(world.rand));
                }
            }
        }
    }

    public float getIntegrity() {
        return theIntegrity;
    }

    public BlockPos getSize(ForgeDirection facing) {
        if(facing.ordinal() < 2 || facing.ordinal() > 5) {
            throw new IllegalArgumentException("ForgeDirection object must be NORTH, SOUTH, EAST or WEST!");
        }
        return facing == ForgeDirection.NORTH || facing == ForgeDirection.SOUTH ? sizes[0] : sizes[1];
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

    /**
     * 0 = North (-z)
     * 1 = South (+z)
     * 2 = West (-x)
     * 3 = East (+x)
     */
    public ForgeDirection getFacingFromInt(int i) {
        if(i < 0 || i > 3) {
            throw new IllegalArgumentException("Facing int must be at least 0 and at most 3!");
        }
        return ForgeDirection.values()[i + 2];
    }

    public int getIntFromFacing(ForgeDirection dir) {
        if(dir.ordinal() < 2 || dir.ordinal() > 5) {
            throw new IllegalArgumentException("ForgeDirection object must be NORTH, SOUTH, EAST or WEST!");
        }
        return dir.ordinal() - 2;
    }

    private Map<Integer, BlockState>[] createPalettes() {
        return new Map[] {createPalette(ForgeDirection.NORTH), createPalette(ForgeDirection.SOUTH), createPalette(ForgeDirection.WEST), createPalette(ForgeDirection.EAST)};
    }

    /**
     * Ran four times for each direction. This populates four different versions of the palette for the different rotations.
     */
    public abstract Map<Integer, BlockState> createPalette(ForgeDirection facing);
}
