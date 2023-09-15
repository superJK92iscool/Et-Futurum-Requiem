package ganymedes01.etfuturum.core.utils.structurenbt;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;

/**Used to store the results of an unflattening in meta, to easily store it in a map after reading a structure NBT file.*/
public class BlockState {

	private final Object theObject;
	private NBTTagCompound theCompound;
	private final BlockStateType type;

	private int theMeta;
	private ChestGenHooks lootTable;

	public BlockState(Block block, int meta, ChestGenHooks info) {
		this(block, meta);
		setLootTable(info);
	}

	public BlockState(Block block, int meta) {
		theObject = block;
		theMeta = meta;
		type = block instanceof ITileEntityProvider ? BlockStateType.BLOCK_ENTITY : BlockStateType.BLOCK;
	}

	public BlockState(Block block) {
		this(block, 0);
	}

	public BlockState(Class<? extends Entity> entity, NBTTagCompound compound) {
		theObject = entity;
		theCompound = compound;
		type = BlockStateType.ENTITY;
	}

	public BlockState(Class<? extends Entity> entity) {
		this(entity, new NBTTagCompound());
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

	public Entity createNewEntity(World world) {
		if(type != BlockStateType.ENTITY) {
			throw new IllegalArgumentException("Tried to get entity instance from a block in a BlockState object!");
		}
		return EntityList.createEntityByName((String) EntityList.classToStringMapping.get(theObject), world);
	}

	public BlockStateType getType() {
		return type;
	}

	public NBTTagCompound getCompound() {
		return theCompound;
	}

	public void setCompound(NBTTagCompound blockTags) {
		theCompound = blockTags;
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

	public void setLootTable(ChestGenHooks info) {
		if(type != BlockStateType.BLOCK_ENTITY) {
			throw new IllegalArgumentException("Tried to set a loot table from a non-entity block!");
		}
		lootTable = info;
	}

	public enum BlockStateType {
		BLOCK,
		BLOCK_ENTITY,
		ENTITY;
	}
}
