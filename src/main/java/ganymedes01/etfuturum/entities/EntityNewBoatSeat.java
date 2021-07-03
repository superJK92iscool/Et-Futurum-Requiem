package ganymedes01.etfuturum.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityNewBoatSeat extends Entity {
	
	private EntityNewBoat boat;

	public EntityNewBoatSeat(World p_i1582_1_) {
		super(p_i1582_1_);
		this.setSize(0, 0);
		this.setInvisible(true);
	}
	
	public EntityNewBoatSeat(World p_i1582_1_, EntityNewBoat boat) {
		this(p_i1582_1_);
		setBoat(boat);
	}

	@Override
	public void entityInit() {
		dataWatcher.addObject(17, boat == null ? new Integer(0) : boat.getEntityId()); //Attached boat ID
	}
	
	public void setBoat(EntityNewBoat newBoat) {
		boat = newBoat;
		if(newBoat != null)
			dataWatcher.updateObject(17, new Integer(newBoat.getEntityId()));
	}
	
	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}
	
	public void sitEntity(Entity entity) {
		entity.mountEntity(this);
	}
	
	@Override
	public void onUpdate() {
		if(isDead) {
			return;
		}
		
    	if(this.ridingEntity != null) {
    		ridingEntity.mountEntity(null);
    		ridingEntity = null;
    	}
		
		int boatID = dataWatcher.getWatchableObjectInt(17);
		
		if(boatID > 0) {
			Entity entity = worldObj.getEntityByID(boatID);
			if(entity instanceof EntityNewBoat && !entity.isDead) {
				setBoat((EntityNewBoat)entity);
			}
		}
		
		if(boat == null || boat.isDead || !canThisSeatStay(boat.getSeat())) {
			kill();
			return;
		}
		
		if(worldObj.isRemote && boat != null && boat.getSeat() == null) {
			boat.setSeat(this);
		}
		
		copyLocationAndAnglesFrom(boat);
	}
	
	/**
	 * Triggers if an existing boat seat is linked to the same boat
	 * this boat seat is.
	 * 
	 * Used to determine if this seat instance should replace the seat
	 * or if this one should be removed instead.
	 * 
	 * Compares the input seat with this seat.
	 */
	public boolean canThisSeatStay(EntityNewBoatSeat compare) {
		if(compare == null || compare == this || compare.isDead) {
			return true;
		}
		if(compare.riddenByEntity == null && riddenByEntity != null) {
			boat.setSeat(this);
			System.out.println("Destroying linked seat in favor of one that has an entity sitting in it");
			compare.kill();
			return true;
		}
		return false;
	}

	@Override
	protected void kill() {
		if(riddenByEntity != null) {
			riddenByEntity.copyLocationAndAnglesFrom(this);
			riddenByEntity.posY += 0.5D;
			riddenByEntity.mountEntity(null);
		}
		isDead = true;
		boat = null;
	}
    
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_)
    {
    	if(boat != null)
		this.copyLocationAndAnglesFrom(boat);
    }
    
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		dataWatcher.updateObject(17, nbt.getInteger("attachedBoat"));
		setBoat((EntityNewBoat)worldObj.getEntityByID(nbt.getInteger("attachedBoat")));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("attachedBoat", boat != null ? boat.getEntityId() : new Integer(0));
	}
	
	/**
	 * The boat this entity is following.
	 */
	public EntityNewBoat getBoat() {
		return boat;
	}
    
    public boolean writeToNBTOptional(NBTTagCompound p_70039_1_)
    {
    	if(this.ridingEntity != null) {
    		ridingEntity.mountEntity(null);
    		ridingEntity = null;
    	}
    	//This entity does not save on disk, we save it through the boat instead.
        return false;
    }

    @Deprecated
    @Override
    public void updateRiderPosition()
    {
    	if(boat != null) {
    		boat.updatePassenger(riddenByEntity);
    	}
    }
    
    /* 
     * ====================DUMMY START====================
     *  
     *  Any code beyond this point has been canceled out with
     *  empty methods for performance reasons. This is to
     *  ensure that code that doesn't matter isn't running.
     *  
	 * ===================================================
	 */


	@Deprecated
    @Override
	public void onEntityUpdate() {
		
	}

	@Deprecated
    @Override
    public void moveEntity(double p_70091_1_, double p_70091_3_, double p_70091_5_) {
    	
    }

    @Deprecated
    @Override
    public void updateRidden()
    {
    	
    }

    @Deprecated
    @Override
    public boolean isBurning()
    {
    	return false;
    }

    @Deprecated
    @Override
    public boolean isRiding()
    {
    	return false;
    	
    }
    
    @SideOnly(Side.CLIENT)
    @Deprecated
    @Override
    public boolean isInvisibleToPlayer(EntityPlayer p_98034_1_)
    {
    	return true;
    }

    @Deprecated
    @Override
    public boolean isInvisible() {
    	return true;
    }

    @Deprecated
    @Override
    public void setInWeb()
    {
    	
    }

    @Deprecated
    @Override
    public boolean hitByEntity(Entity p_85031_1_)
    {
    	return true;
    }

    @Deprecated
    @Override
    public AxisAlignedBB getCollisionBox(Entity p_70114_1_)
    {
    	return null;
    }

    @Deprecated
    @Override
    public void applyEntityCollision(Entity p_70108_1_)
    {
    	
    }
    
    @Deprecated
    @Override
    protected boolean canTriggerWalking()
    {
    	return false;
    }
    
    @Deprecated
    @Override
    public boolean canBePushed()
    {
    	return false;
    }

}
