package ganymedes01.etfuturum.entities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import ganymedes01.etfuturum.ModItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityNewBoat extends Entity {
//    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.<Integer>createKey(EntityNewBoat.class, DataSerializers.VARINT);
//    private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.<Integer>createKey(EntityNewBoat.class, DataSerializers.VARINT);
//    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.<Float>createKey(EntityNewBoat.class, DataSerializers.FLOAT);
//    private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.<Integer>createKey(EntityNewBoat.class, DataSerializers.VARINT);
//    private static final DataParameter<Boolean>[] DATA_ID_PADDLE = new DataParameter[] {EntityDataManager.createKey(EntityNewBoat.class, DataSerializers.BOOLEAN), EntityDataManager.createKey(EntityNewBoat.class, DataSerializers.BOOLEAN)};
	private static final int[] DATA_ID_PADDLE = new int[] {21, 22};
	private float[] paddlePositions;

    /** How much of current speed to retain. Value zero to one. */
    private float momentum;
    private float outOfControlTicks;
    private float deltaRotation;
    private int lerpSteps;
    private double boatPitch;
    private double lerpY;
    private double lerpZ;
    private double boatYaw;
    private double lerpXRot;
    private boolean leftInputDown;
    private boolean rightInputDown;
    private boolean forwardInputDown;
    private boolean backInputDown;
    private double waterLevel;

    /**
     * How much the boat should glide given the slippery blocks it's currently gliding over.
     * Halved every tick.
     */
    private float boatGlide;
    private EntityNewBoat.Status status;
    private EntityNewBoat.Status previousStatus;
    private double lastYd;
    
    private List<Entity> passengers = new ArrayList<Entity>();

    public EntityNewBoat(World p_i1704_1_)
    {
        super(p_i1704_1_);
        this.paddlePositions = new float[2];
        this.preventEntitySpawning = true;
        this.setSize(1.375F, 0.5625F);
    }

    public EntityNewBoat(World p_i1705_1_, double p_i1705_2_, double p_i1705_4_, double p_i1705_6_)
    {
        this(p_i1705_1_);
        this.setPosition(p_i1705_2_, p_i1705_4_, p_i1705_6_);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = p_i1705_2_;
        this.prevPosY = p_i1705_4_;
        this.prevPosZ = p_i1705_6_;
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
        this.dataWatcher.addObject(17, new Integer(0)); //Time since hit?
        this.dataWatcher.addObject(18, new Integer(1)); //Forward direction?
        this.dataWatcher.addObject(19, new Float(0.0F)); //Damage taken?
        // ET FUTURUM START
        this.dataWatcher.addObject(20, new Integer(0)); //Boat Type
        for (int i = 0; i < DATA_ID_PADDLE.length; ++i)
        {
            dataWatcher.addObject(DATA_ID_PADDLE[i], new Byte((byte) 0));
        }
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB getCollisionBox(Entity p_70114_1_)
    {
        return p_70114_1_.boundingBox;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB getBoundingBox()
    {
        return this.boundingBox;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return true;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return -0.1D;
    }
    
    public double getYOffset()
    {
        return 0;
    }

    public boolean isPassenger(Entity entity) {
    	return getPassengers().contains(entity);
    }
    
    public void removePassengers() {
    	riddenByEntity.ridingEntity = null;
    	riddenByEntity = null;
    	for(Entity passenger : passengers) {
    		passenger.ridingEntity = null;
    	}
    	passengers.clear();
    }
    
    public List<Entity> getPassengers() {
//    	List<Entity> passengersCopy = new ArrayList<Entity>(passengers);
//    	List<Entity> passengers2 = new ArrayList<Entity>();
//    	if(passengers.isEmpty() && passengers.get(0) != riddenByEntity) {
//        	passengers2.add(riddenByEntity);
//        	passengersCopy.remove(0);
//    	}
//    	passengers2.addAll(passengersCopy);
//    	return passengers2;
    	List<Entity> dummylist = new ArrayList<Entity>();
    	if(riddenByEntity != null) {
        	dummylist.add(riddenByEntity);
    	}
    	if(!dummylist.isEmpty()) {
    		System.out.println(dummylist.get(0).getCommandSenderName());
    	}
    	return dummylist;
    }
    
    public void addToBoat(Entity entity) {
    	if(getPassengers().size() > 2) return;
    	if(riddenByEntity == null) {
    		entity.mountEntity(this);
    	}
//    	else if (riddenByEntity instanceof EntityPlayer) {
//    		passengers.add(entity);
//    	} //dummy
    }
    
    public boolean isBeingRidden() {
    	return !getPassengers().isEmpty();
    }
    
    public boolean canPassengerSteer()
    {
    	//Changed as a test, dummy.
        return getControllingPassenger() instanceof EntityPlayer;
    }
    
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else if (!this.worldObj.isRemote && !this.isDead)
        {
            if (source instanceof EntityDamageSourceIndirect && source.getEntity() != null && this.isPassenger(source.getEntity()))
            {
                return false;
            }
			this.setForwardDirection(-this.getForwardDirection());
			this.setTimeSinceHit(10);
			this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
			this.setBeenAttacked();
			boolean flag = source.getEntity() instanceof EntityPlayer && ((EntityPlayer)source.getEntity()).capabilities.isCreativeMode;

			if (flag || this.getDamageTaken() > 40.0F)
			{
			    if (!flag && this.worldObj.getGameRules().getGameRuleBooleanValue("doEntityDrops"))
			    {
			        this.dropItem(this.getItemBoat(), 1);
			    }

			    this.setDead();
			}

			return true;
        }
        else
        {
            return true;
        }
    }

    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    public void applyEntityCollision(Entity entityIn)
    {
        if (entityIn instanceof EntityNewBoat)
        {
            if (entityIn.boundingBox.minY < this.boundingBox.maxY)
            {
                super.applyEntityCollision(entityIn);
            }
        }
        else if (entityIn.boundingBox.minY <= this.boundingBox.minY)
        {
            super.applyEntityCollision(entityIn);
        }
    }

    public Item getItemBoat()
    {
    	if(getBoatType() == Type.OAK) {
    		return Items.boat; //Keep this here as a placeholder for boat compatibility options, dummy...
    	}
		return ModItems.signs[getBoatType().ordinal() - 1]; //Use signs as a stand-in for the different wood types, dummy.
    }

	/**
     * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
     */
    public void performHurtAnimation()
    {
        this.setForwardDirection(-this.getForwardDirection());
        this.setTimeSinceHit(10);
        this.setDamageTaken(this.getDamageTaken() * 11.0F);
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    /**
     * Set the position and rotation values directly without any clamping.
     */
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        this.boatPitch = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.boatYaw = (double)yaw;
        this.lerpXRot = (double)pitch;
        this.lerpSteps = 10;
    }

    /**
     * Gets the horizontal facing direction of this Entity, adjusted to take specially-treated entity types into
     * account.
     */
    public EnumFacing getAdjustedHorizontalFacing()
    {
        return EnumFacing.NORTH; //Dummy
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.previousStatus = this.status;
        this.status = this.getBoatStatus();

//        if (this.status != EntityNewBoat.Status.UNDER_WATER && this.status != EntityNewBoat.Status.UNDER_FLOWING_WATER)
//        {
            this.outOfControlTicks = 0.0F;
//        }
//        else
//        {
//            ++this.outOfControlTicks;
//        }

        if (!this.worldObj.isRemote && this.outOfControlTicks >= 60.0F)
        {
            this.removePassengers();
        }

        if (this.getTimeSinceHit() > 0)
        {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }

        if (this.getDamageTaken() > 0.0F)
        {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        super.onUpdate();
        this.tickLerp();

        if (this.canPassengerSteer())
        {
            if (this.getPassengers().size() == 0 || !(this.getPassengers().get(0) instanceof EntityPlayer))
            {
                this.setPaddleState(false, false);
            }

            this.updateMotion();

            if (this.worldObj.isRemote)
            {
                this.controlBoat();
//                this.worldObj.sendPacketToServer(new CPacketSteerBoat(this.getPaddleState(0), this.getPaddleState(1)));
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
        }
        else
        {
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
        }

        for (int i = 0; i <= 1; ++i)
        {
            if (this.getPaddleState(i))
            {
                this.paddlePositions[i] = (float)((double)this.paddlePositions[i] + 0.01D);
            }
            else
            {
                this.paddlePositions[i] = 0.0F;
            }
        }

        this.func_145775_I();
		List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
				this.boundingBox.expand(0.20000000298023224D, -0.009999999776482582D,
						0.20000000298023224D)/* , EntitySelectors.<Entity>getTeamCollisionPredicate(this) */);

        if (!list.isEmpty())
        {
            boolean flag = !this.worldObj.isRemote && !(this.getControllingPassenger() instanceof EntityPlayer);

            for (int j = 0; j < list.size(); ++j)
            {
                Entity entity = (Entity)list.get(j);

                if (entity.ridingEntity != this)
                {
                    if (flag && this.getPassengers().size() < 2 && !entity.isRiding() && entity.width < this.width && entity instanceof EntityLivingBase && !(entity instanceof EntityWaterMob) && !(entity instanceof EntityPlayer))
                    {
                        addToBoat(entity);
                    }
                    else
                    {
                        this.applyEntityCollision(entity);
                    }
                }
            }
        }
    }

    private void tickLerp()
    {
        if (this.lerpSteps > 0 && !this.canPassengerSteer())
        {
            double d0 = this.posX + (this.boatPitch - this.posX) / (double)this.lerpSteps;
            double d1 = this.posY + (this.lerpY - this.posY) / (double)this.lerpSteps;
            double d2 = this.posZ + (this.lerpZ - this.posZ) / (double)this.lerpSteps;
            double d3 = MathHelper.wrapAngleTo180_double(this.boatYaw - (double)this.rotationYaw);
            this.rotationYaw = (float)((double)this.rotationYaw + d3 / (double)this.lerpSteps);
            this.rotationPitch = (float)((double)this.rotationPitch + (this.lerpXRot - (double)this.rotationPitch) / (double)this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }

    public void setPaddleState(boolean p_184445_1_, boolean p_184445_2_)
    {
        this.dataWatcher.updateObject(DATA_ID_PADDLE[0], new Byte((byte) (p_184445_1_ ? 1 : 0)));
        this.dataWatcher.updateObject(DATA_ID_PADDLE[1], new Byte((byte) (p_184445_2_ ? 1 : 0)));
    }

    public float getRowingTime(int p_184448_1_, float limbSwing)
    {
        return this.getPaddleState(p_184448_1_) ? (float)MathHelper.denormalizeClamp((double)this.paddlePositions[p_184448_1_] - 0.01D, (double)this.paddlePositions[p_184448_1_], (double)limbSwing) : 0.0F;
    }

    /**
     * Determines whether the boat is in water, gliding on land, or in air
     */
    private EntityNewBoat.Status getBoatStatus()
    {
        EntityNewBoat.Status EntityNewBoat$status = this.getUnderwaterStatus();

        if (EntityNewBoat$status != null)
        {
            this.waterLevel = this.boundingBox.maxY;
            return EntityNewBoat$status;
        }
        else if (this.checkInWater())
        {
            return EntityNewBoat.Status.IN_WATER;
        }
        else
        {
            float f = this.getBoatGlide();

            if (f > 0.0F)
            {
                this.boatGlide = f;
                return EntityNewBoat.Status.ON_LAND;
            }
			return EntityNewBoat.Status.IN_AIR;
        }
    }

    /**
     * Dummy method
     */
    public float getWaterLevelAbove()
    {
        AxisAlignedBB axisalignedbb = this.boundingBox;
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.ceiling_double_int(axisalignedbb.maxX);
        int k = MathHelper.floor_double(axisalignedbb.maxY);
        int l = MathHelper.ceiling_double_int(axisalignedbb.maxY - this.lastYd);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.ceiling_double_int(axisalignedbb.maxZ);
//        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

        try
        {
//            label78:
//
//            for (int k1 = k; k1 < l; ++k1)
//            {
//                float f = 0.0F;
//                int l1 = i;
//
//                while (true)
//                {
//                    if (l1 >= j)
//                    {
//                        if (f < 1.0F)
//                        {
//                            float f2 = (float)blockpos$pooledmutableblockpos.getY() + f;
//                            return f2;
//                        }
//
//                        break;
//                    }
//
//                    for (int i2 = i1; i2 < j1; ++i2)
//                    {
//                        blockpos$pooledmutableblockpos.set(l1, k1, i2);
//                        IBlockState iblockstate = this.worldObj.getBlockState(blockpos$pooledmutableblockpos);
//
//                        if (iblockstate.getMaterial() == Material.WATER)
//                        {
//                            f = Math.max(f, getBlockLiquidHeight(iblockstate, this.worldObj, blockpos$pooledmutableblockpos));
//                        }
//
//                        if (f >= 1.0F)
//                        {
//                            continue label78;
//                        }
//                    }
//
//                    ++l1;
//                }
//            }

            float f1 = (float)(l + 1);
            return f1;
        }
        finally
        {
//            blockpos$pooledmutableblockpos.release();
        }
    }

    /**
     * Decides how much the boat should be gliding on the land (based on any slippery blocks)
     * Dummy method
     */
    public float getBoatGlide()
    {
        AxisAlignedBB axisalignedbb = this.boundingBox;
        AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(axisalignedbb.minX, axisalignedbb.minY - 0.001D, axisalignedbb.minZ, axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        int i = MathHelper.floor_double(axisalignedbb1.minX) - 1;
        int j = MathHelper.ceiling_double_int(axisalignedbb1.maxX) + 1;
        int k = MathHelper.floor_double(axisalignedbb1.minY) - 1;
        int l = MathHelper.ceiling_double_int(axisalignedbb1.maxY) + 1;
        int i1 = MathHelper.floor_double(axisalignedbb1.minZ) - 1;
        int j1 = MathHelper.ceiling_double_int(axisalignedbb1.maxZ) + 1;
        List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
        float f = 0.0F;
        int k1 = 0;
//        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

//        try
//        {
//            for (int l1 = i; l1 < j; ++l1)
//            {
//                for (int i2 = i1; i2 < j1; ++i2)
//                {
//                    int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
//
//                    if (j2 != 2)
//                    {
//                        for (int k2 = k; k2 < l; ++k2)
//                        {
//                            if (j2 <= 0 || k2 != k && k2 != l - 1)
//                            {
//                                blockpos$pooledmutableblockpos.set(l1, k2, i2);
//                                IBlockState iblockstate = this.worldObj.getBlockState(blockpos$pooledmutableblockpos);
//                                iblockstate.addCollisionBoxToList(this.worldObj, blockpos$pooledmutableblockpos, axisalignedbb1, list, this);
//
//                                if (!list.isEmpty())
//                                {
//                                    f += iblockstate.getBlock().slipperiness;
//                                    ++k1;
//                                }
//
//                                list.clear();
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        finally
//        {
//            blockpos$pooledmutableblockpos.release();
//        }

        return f / (float)k1;
    }

    /**
     * Dummy method
     */
    private boolean checkInWater()
    {
        AxisAlignedBB axisalignedbb = this.boundingBox;
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.ceiling_double_int(axisalignedbb.maxX);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.ceiling_double_int(axisalignedbb.minY + 0.001D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.ceiling_double_int(axisalignedbb.maxZ);
        boolean flag = false;
        this.waterLevel = Double.MIN_VALUE;
//        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

//        try
//        {
//            for (int k1 = i; k1 < j; ++k1)
//            {
//                for (int l1 = k; l1 < l; ++l1)
//                {
//                    for (int i2 = i1; i2 < j1; ++i2)
//                    {
//                        blockpos$pooledmutableblockpos.set(k1, l1, i2);
//                        IBlockState iblockstate = this.worldObj.getBlockState(blockpos$pooledmutableblockpos);
//
//                        if (iblockstate.getMaterial() == Material.WATER)
//                        {
//                            float f = getLiquidHeight(iblockstate, this.worldObj, blockpos$pooledmutableblockpos);
//                            this.waterLevel = Math.max((double)f, this.waterLevel);
//                            flag |= axisalignedbb.minY < (double)f;
//                        }
//                    }
//                }
//            }
//        }
//        finally
//        {
//            blockpos$pooledmutableblockpos.release();
//        }

        return flag;
    }

    /**
     * Decides whether the boat is currently underwater.
     * Dummy method
     */
    private EntityNewBoat.Status getUnderwaterStatus()
    {
        AxisAlignedBB axisalignedbb = this.boundingBox;
        double d0 = axisalignedbb.maxY + 0.001D;
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.ceiling_double_int(axisalignedbb.maxX);
        int k = MathHelper.floor_double(axisalignedbb.maxY);
        int l = MathHelper.ceiling_double_int(d0);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.ceiling_double_int(axisalignedbb.maxZ);
        boolean flag = false;
//        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
//
//        try
//        {
//            for (int k1 = i; k1 < j; ++k1)
//            {
//                for (int l1 = k; l1 < l; ++l1)
//                {
//                    for (int i2 = i1; i2 < j1; ++i2)
//                    {
//                        blockpos$pooledmutableblockpos.set(k1, l1, i2);
//                        IBlockState iblockstate = this.worldObj.getBlockState(blockpos$pooledmutableblockpos);
//
//                        if (iblockstate.getMaterial() == Material.WATER && d0 < (double)getLiquidHeight(iblockstate, this.worldObj, blockpos$pooledmutableblockpos))
//                        {
//                            if (((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() != 0)
//                            {
//                                EntityNewBoat.Status EntityNewBoat$status = EntityNewBoat.Status.UNDER_FLOWING_WATER;
//                                return EntityNewBoat$status;
//                            }
//
//                            flag = true;
//                        }
//                    }
//                }
//            }
//        }
//        finally
//        {
//            blockpos$pooledmutableblockpos.release();
//        }

        return flag ? EntityNewBoat.Status.UNDER_WATER : null;
    }

//    public static float getBlockLiquidHeight(IBlockState p_184456_0_, IBlockAccess p_184456_1_, BlockPos p_184456_2_)
//    {
//        int i = ((Integer)p_184456_0_.getValue(BlockLiquid.LEVEL)).intValue();
//        return (i & 7) == 0 && p_184456_1_.getBlockState(p_184456_2_.up()).getMaterial() == Material.WATER ? 1.0F : 1.0F - BlockLiquid.getLiquidHeightPercent(i);
//    }
////Dummy
//    public static float getLiquidHeight(IBlockState p_184452_0_, IBlockAccess p_184452_1_, BlockPos p_184452_2_)
//    {
//        return (float)p_184452_2_.getY() + getBlockLiquidHeight(p_184452_0_, p_184452_1_, p_184452_2_);
//    }

    /**
     * Update the boat's speed, based on momentum.
     */
    private void updateMotion()
    {
        double d0 = -0.03999999910593033D;
        double d1 = d0;
        double d2 = 0.0D;
        this.momentum = 0.05F;

        if (this.previousStatus == EntityNewBoat.Status.IN_AIR && this.status != EntityNewBoat.Status.IN_AIR && this.status != EntityNewBoat.Status.ON_LAND)
        {
            this.waterLevel = this.boundingBox.minY + (double)this.height;
            this.setPosition(this.posX, (double)(this.getWaterLevelAbove() - this.height) + 0.101D, this.posZ);
            this.motionY = 0.0D;
            this.lastYd = 0.0D;
            this.status = EntityNewBoat.Status.IN_WATER;
        }
        else
        {
            if (this.status == EntityNewBoat.Status.IN_WATER)
            {
                d2 = (this.waterLevel - this.boundingBox.minY) / (double)this.height;
                this.momentum = 0.9F;
            }
            else if (this.status == EntityNewBoat.Status.UNDER_FLOWING_WATER)
            {
                d1 = -7.0E-4D;
                this.momentum = 0.9F;
            }
            else if (this.status == EntityNewBoat.Status.UNDER_WATER)
            {
                d2 = 0.009999999776482582D;
                this.momentum = 0.45F;
            }
            else if (this.status == EntityNewBoat.Status.IN_AIR)
            {
                this.momentum = 0.9F;
            }
            else if (this.status == EntityNewBoat.Status.ON_LAND)
            {
                this.momentum = this.boatGlide;

                if (this.getControllingPassenger() instanceof EntityPlayer)
                {
                    this.boatGlide /= 2.0F;
                }
            }

            this.motionX *= (double)this.momentum;
            this.motionZ *= (double)this.momentum;
            this.deltaRotation *= this.momentum;
            this.motionY += d1;

            if (d2 > 0.0D)
            {
                double d3 = 0.65D;
                this.motionY += d2 * (-d0 / 0.65D);
                double d4 = 0.75D;
                this.motionY *= 0.75D;
            }
        }
    }

    private void controlBoat()
    {
    	if(!getPassengers().isEmpty() && getPassengers().get(0) instanceof EntityLiving) {
    		EntityLiving living = (EntityLiving)getPassengers().get(0);
        	this.updateInputs(living.moveStrafing < 0, living.moveStrafing > 0, living.moveForward > 0, living.moveForward < 0);
    	}
        if (this.isBeingRidden())
        {
            float f = 0.0F;

            if (this.leftInputDown)
            {
                this.deltaRotation += -1.0F;
            }

            if (this.rightInputDown)
            {
                ++this.deltaRotation;
            }

            if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown)
            {
                f += 0.005F;
            }

            this.rotationYaw += this.deltaRotation;

            if (this.forwardInputDown)
            {
                f += 0.04F;
            }

            if (this.backInputDown)
            {
                f -= 0.005F;
            }

            this.motionX += (double)(MathHelper.sin(-this.rotationYaw * 0.017453292F) * f);
            this.motionZ += (double)(MathHelper.cos(this.rotationYaw * 0.017453292F) * f);
            this.setPaddleState(this.rightInputDown || this.forwardInputDown, this.leftInputDown || this.forwardInputDown);
        }
    }

    public void updatePassenger(Entity passenger)
    {
        if (this.isPassenger(passenger))
        {
            float f = 0.0F;
            float f1 = (float)((this.isDead ? 0.009999999776482582D : this.getMountedYOffset()) + passenger.getYOffset());

            if (this.getPassengers().size() > 1)
            {
                int i = this.getPassengers().indexOf(passenger);

                if (i == 0)
                {
                    f = 0.2F;
                }
                else
                {
                    f = -0.6F;
                }

                if (passenger instanceof EntityAnimal)
                {
                    f = (float)((double)f + 0.2D);
                }
            }

            Vec3 vec3d = Vec3.createVectorHelper((double)f, 0.0D, 0.0D);
            vec3d.rotateAroundY(-this.rotationYaw * 0.017453292F - ((float)Math.PI / 2F));
            passenger.setPosition(this.posX + vec3d.xCoord, this.posY + (double)f1, this.posZ + vec3d.zCoord);
            passenger.rotationYaw += this.deltaRotation;
            passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
            this.applyYawToEntity(passenger);

            if (passenger instanceof EntityAnimal && this.getPassengers().size() > 1)
            {
                int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
                ((EntityAnimal)passenger).renderYawOffset = ((EntityAnimal)passenger).renderYawOffset + (float)j; //dummy
                passenger.setRotationYawHead(passenger.getRotationYawHead() + (float)j);
            }
        }
    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    protected void applyYawToEntity(Entity entityToUpdate)
    {
    	if(entityToUpdate instanceof EntityLiving) {
            ((EntityLiving)entityToUpdate).renderYawOffset = this.rotationYaw; //Dummy
    	}
        float f = MathHelper.wrapAngleTo180_float(entityToUpdate.rotationYaw - this.rotationYaw);//WrapDegrees, check if correct. Dummy
        float f1 = MathHelper.clamp_float(f, -105.0F, 105.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    /**
     * Applies this entity's orientation (pitch/yaw) to another entity. Used to update passenger orientation.
     */
    public void applyOrientationToEntity(Entity entityToUpdate)
    {
        this.applyYawToEntity(entityToUpdate);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setString("Type", this.getBoatType().getName());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        if (compound.hasKey("Type", 8))
        {
            this.setBoatType(EntityNewBoat.Type.getTypeFromString(compound.getString("Type")));
        }
    }

    public boolean interactFirst(EntityPlayer player)
    {
        if (!this.worldObj.isRemote && !player.isSneaking() && this.outOfControlTicks < 60.0F)
        {
            addToBoat(player);
        }

        return true;
    }

    protected void updateFallState(double y, boolean onGroundIn)
    {
        this.lastYd = this.motionY;

        if (!this.isRiding())
        {
            if (onGroundIn)
            {
                if (this.fallDistance > 3.0F)
                {
                    if (this.status != EntityNewBoat.Status.ON_LAND)
                    {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.fall(this.fallDistance); // Float 1.0F removed... Dummy

                    if (!this.worldObj.isRemote && !this.isDead)
                    {
                        this.setDead();

                        if (this.worldObj.getGameRules().getGameRuleBooleanValue("doEntityDrops"))
                        {
                            for (int i = 0; i < 3; ++i)
                            {
                                this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.planks), 1, this.getBoatType().getMetadata()), 0.0F);
                            }

                            for (int j = 0; j < 2; ++j)
                            {
                                this.entityDropItem(new ItemStack(Items.stick), 0.0F);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            }
            else if (this.worldObj.getBlock((int)posX, (int)y - 1, (int)posZ).getMaterial() != Material.water && y < 0.0D)
            {
                this.fallDistance = (float)((double)this.fallDistance - y);
            }
        }
    }

    public boolean getPaddleState(int p_184457_1_)
    {
        return false;//Dummy
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float p_70266_1_)
    {
        this.dataWatcher.updateObject(19, Float.valueOf(p_70266_1_));
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken()
    {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int p_70265_1_)
    {
        this.dataWatcher.updateObject(17, Integer.valueOf(p_70265_1_));
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit()
    {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    /**
     * Sets the forward direction of the entity.
     */
    public void setForwardDirection(int p_70269_1_)
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(p_70269_1_));
    }

    /**
     * Gets the forward direction of the entity.
     */
    public int getForwardDirection()
    {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public void setBoatType(EntityNewBoat.Type boatType)
    {
        getDataWatcher().updateObject(20, Integer.valueOf(boatType.ordinal()));
    }

    public EntityNewBoat.Type getBoatType()
    {
        return EntityNewBoat.Type.byId(getDataWatcher().getWatchableObjectInt(20));
    }

    protected boolean canFitPassenger(Entity passenger)
    {
        return this.getPassengers().size() < 2;
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    public Entity getControllingPassenger()
    {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : (Entity)list.get(0);
    }

    public void updateInputs(boolean p_184442_1_, boolean p_184442_2_, boolean p_184442_3_, boolean p_184442_4_)
    {
        this.leftInputDown = p_184442_1_;
        this.rightInputDown = p_184442_2_;
        this.forwardInputDown = p_184442_3_;
        this.backInputDown = p_184442_4_;
    }

    public static enum Status
    {
        IN_WATER,
        UNDER_WATER,
        UNDER_FLOWING_WATER,
        ON_LAND,
        IN_AIR;
    }

    public static enum Type
    {
        OAK(0, "oak"),
        SPRUCE(1, "spruce"),
        BIRCH(2, "birch"),
        JUNGLE(3, "jungle"),
        ACACIA(4, "acacia"),
        DARK_OAK(5, "dark_oak");

        private final String name;
        private final int metadata;

        private Type(int metadataIn, String nameIn)
        {
            this.name = nameIn;
            this.metadata = metadataIn;
        }

        public String getName()
        {
            return this.name;
        }

        public int getMetadata()
        {
            return this.metadata;
        }

        public String toString()
        {
            return this.name;
        }

        public static EntityNewBoat.Type byId(int id)
        {
            if (id < 0 || id >= values().length)
            {
                id = 0;
            }

            return values()[id];
        }

        public static EntityNewBoat.Type getTypeFromString(String nameIn)
        {
            for (int i = 0; i < values().length; ++i)
            {
                if (values()[i].getName().equals(nameIn))
                {
                    return values()[i];
                }
            }

            return values()[0];
        }
    }
}
