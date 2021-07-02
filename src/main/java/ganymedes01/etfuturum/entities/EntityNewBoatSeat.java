package ganymedes01.etfuturum.entities;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNewBoatSeat extends Entity {
	
	private EntityNewBoat boat;
	private int boatID = -1;

	public EntityNewBoatSeat(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public EntityNewBoatSeat(World p_i1582_1_, int boatid) {
		this(p_i1582_1_);
		Entity entity = worldObj.getEntityByID(boatid);
		if(entity != null && entity instanceof EntityNewBoat && !entity.isDead) {
			boatID = boatid;
		}
	}

	/**
	 * The boat this entity is following.
	 */
	public EntityNewBoat getBoat() {
		return boat;
	}
	
	public int getBoatID() {
		return boatID;
	}

	@Override
	protected void entityInit() {
		dataWatcher.addObject(17, new Integer(boatID)); //Attached boat ID
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		
	}

}
