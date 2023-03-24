package ganymedes01.etfuturum.core.utils.helpers;

import ganymedes01.etfuturum.core.utils.Logger;
import ganymedes01.etfuturum.entities.EntityShulker;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

import java.lang.reflect.InvocationTargetException;

/**Used to store the results of an unflattening in meta, to easily store it in a map after reading a structure NBT file.*/
public class BlockState {

    private final Object theObject;
    private final NBTTagCompound theCompound;
    private final BlockStateType type;

    private int theMeta;
    private Object[] entityContructorArgs;
    private ChestGenHooks lootTable;

    public BlockState(Block block, int meta, NBTTagCompound compound) {
        if(!(block instanceof ITileEntityProvider)) {
            throw new IllegalArgumentException("Tried to create a BlockState NBT with a block that isn't a block entity!");
        }
        theObject = block;
        theMeta = meta;
        type = BlockStateType.BLOCK_ENTITY;
        theCompound = compound;
    }

    public BlockState(Block block, int meta, NBTTagCompound compound, ChestGenHooks info) {
        if(!(block instanceof ITileEntityProvider)) {
            throw new IllegalArgumentException("Tried to create a BlockState NBT with a block that isn't a block entity!");
        }
        theObject = block;
        theMeta = meta;
        type = BlockStateType.BLOCK_ENTITY;
        theCompound = compound;
        lootTable = info;
    }

    public BlockState(Block block, int meta) {
        theObject = block;
        theMeta = meta;
        type = block instanceof ITileEntityProvider ? BlockStateType.BLOCK_ENTITY : BlockStateType.BLOCK;
        theCompound = block instanceof ITileEntityProvider ? new NBTTagCompound() : null;
    }

    public BlockState(Class<? extends Entity> entity, NBTTagCompound compound) {
        theObject = entity;
        theCompound = compound;
        type = BlockStateType.ENTITY;
    }

    public Block getBlock() {
        if(type == BlockStateType.ENTITY) {
            throw new IllegalArgumentException("Tried to get block instance from ENTITY in a BlockState object!");
        }
        return (Block) theObject;
    }

    public int getMeta() {
        if(type == BlockStateType.ENTITY) {
            throw new IllegalArgumentException("Tried to get metadata from ENTITY in a BlockState object!");
        }
        return theMeta;
    }

    public NBTTagCompound getCompound() {
        return theCompound;
    }
    public Entity createNewEntity(World world) {
        if(type != BlockStateType.ENTITY) {
            throw new IllegalArgumentException("Tried to get entity instance from a block in a BlockState object!");
        }
        return EntityList.createEntityByName((String) EntityList.classToStringMapping.get(theObject), world);
    }

    /**
     * Gets the loot table meant for stuff like chests, dispensers etc
     * Only works on IInventory block entities.
     */
    public ChestGenHooks getLootTable() {
        if(type != BlockStateType.BLOCK_ENTITY) {
            throw new IllegalArgumentException("Tried to get a loot table from a non-entity block!");
        }
        return lootTable;
    }

    public BlockStateType getType() {
        return type;
    }

    public enum BlockStateType {
        BLOCK,
        BLOCK_ENTITY,
        ENTITY;
    }
}
