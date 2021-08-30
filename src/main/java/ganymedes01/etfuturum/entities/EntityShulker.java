package ganymedes01.etfuturum.entities;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Optional;

import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.entities.ai.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class EntityShulker extends EntityGolem implements IMob {

    private static final UUID COVERED_ARMOR_BONUS_ID = UUID.fromString("7E0292F2-9434-48D5-A29F-9583AF7DF27F");
    private static final AttributeModifier COVERED_ARMOR_BONUS_MODIFIER = (new AttributeModifier(COVERED_ARMOR_BONUS_ID, "Covered armor bonus", 20.0D, 0)).setSaved(false);
    private static final int ATTACHED_FACE = 12;
    private static final int PEEK_TICK = 13;
    private static final int ATTACHED_X = 14;
    private static final int ATTACHED_Y = 15;
    private static final int ATTACHED_Z = 16;
    private float currentPeekAmount0;
    private float currentPeekAmount;
    private BlockPos currentAttachmentPosition;
    private int clientSideTeleportInterpolation;
    
	public EntityShulker(World p_i1686_1_) {
		super(p_i1686_1_);
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
//        this.tasks.addTask(4, new EntityShulker.AIAttack());
//        this.tasks.addTask(7, new EntityShulker.AIPeek());
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
//        this.targetTasks.addTask(2, new EntityShulker.AIAttackNearest(this));
//        this.targetTasks.addTask(3, new EntityShulker.AIDefenseAttack(this));
	}
	
	@Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_)
    {

        this.renderYawOffset = 180.0F;
        this.prevRenderYawOffset = 180.0F;
        this.rotationYaw = 180.0F;
        this.prevRotationYaw = 180.0F;
        this.rotationYawHead = 180.0F;
        this.prevRotationYawHead = 180.0F;
        this.setAttachmentPos(new BlockPos((int)posX, (int)posY, (int)posZ));
		return super.onSpawnWithEgg(p_110161_1_);
    	
    }

    protected void entityInit()
    {
    	super.entityInit();
        this.getDataWatcher().addObject(ATTACHED_FACE, (byte)0);
        this.getDataWatcher().addObject(PEEK_TICK, (byte)0);
        this.getDataWatcher().addObject(ATTACHED_X, 0);
        this.getDataWatcher().addObject(ATTACHED_Y, -1);
        this.getDataWatcher().addObject(ATTACHED_Z, 0);
    }
    
    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    public boolean canBeCollidedWith()
    {
        return this.isEntityAlive();
    }

    @Override
    public void playLivingSound()
    {
        if (!this.isClosed())
        {
            super.playLivingSound();
        }
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
    }
    
//    protected EntityBodyHelper createBodyHelper()
//    { //TODO: Check what the fuck this is supposed to do
//        return new EntityShulker.BodyHelper(this);
//    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.getDataWatcher().updateObject(ATTACHED_FACE, (byte)EnumFacing.getFront(compound.getByte("AttachFace")).ordinal());
        this.getDataWatcher().updateObject(PEEK_TICK, Byte.valueOf(compound.getByte("Peek")));

        this.setAttachmentPos(new BlockPos(compound.getInteger("APX"), compound.getInteger("APY"), compound.getInteger("APZ")));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setByte("AttachFace", this.getDataWatcher().getWatchableObjectByte(ATTACHED_FACE));
        compound.setByte("Peek", this.getDataWatcher().getWatchableObjectByte(PEEK_TICK));
        
        compound.setInteger("APX", this.getDataWatcher().getWatchableObjectInt(ATTACHED_X));
        compound.setInteger("APY", this.getDataWatcher().getWatchableObjectInt(ATTACHED_Y));
        compound.setInteger("APZ", this.getDataWatcher().getWatchableObjectInt(ATTACHED_Z));
    }
    
    public BlockPos getAttachmentPos()
    {
    	return new BlockPos(getDataWatcher().getWatchableObjectInt(ATTACHED_X), getDataWatcher().getWatchableObjectInt(ATTACHED_Y), getDataWatcher().getWatchableObjectInt(ATTACHED_Z));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        BlockPos blockpos = getAttachmentPos();

        if (blockpos.getY() == -1 && !this.worldObj.isRemote)
        {
            blockpos = new BlockPos(this);
            this.getDataWatcher().updateObject(ATTACHED_X, blockpos.getX());
            this.getDataWatcher().updateObject(ATTACHED_Y, blockpos.getY());
            this.getDataWatcher().updateObject(ATTACHED_Z, blockpos.getZ());
        }

        if (this.isRiding())
        {
            blockpos = null;
            float f = this.ridingEntity.rotationYaw;
            this.rotationYaw = f;
            this.renderYawOffset = f;
            this.prevRenderYawOffset = f;
            this.clientSideTeleportInterpolation = 0;
        }
        else if (!this.worldObj.isRemote)
        {
            Block iblockstate = this.worldObj.getBlock(blockpos.getX(), blockpos.getY(), blockpos.getZ());

            if (iblockstate != Blocks.air)
            {
//                if (iblockstate == Blocks.piston_extension)
//                {
//                    EnumFacing enumfacing = (EnumFacing)iblockstate.getValue(BlockPistonBase.FACING);
//                    blockpos = blockpos.offset(enumfacing);
//                    this.setAttachmentPos(blockpos);
//                }
//                else if (iblockstate == Blocks.piston_head)
//                {
//                    EnumFacing enumfacing3 = (EnumFacing)iblockstate.getValue(BlockPistonExtension.FACING);
//                    blockpos = blockpos.offset(enumfacing3);
//                    this.setAttachmentPos(blockpos);
//                }
                if (iblockstate == Blocks.piston_extension)
                {
                    EnumFacing enumfacing = EnumFacing.getFront(worldObj.getBlockMetadata(blockpos.getX(), blockpos.getY(), blockpos.getZ()));
                    blockpos = blockpos.offset(enumfacing);
                    this.setAttachmentPos(blockpos);
                }
                else if (iblockstate == Blocks.piston_head)
                {
                    EnumFacing enumfacing3 = EnumFacing.getFront(worldObj.getBlockMetadata(blockpos.getX(), blockpos.getY(), blockpos.getZ()));
                    blockpos = blockpos.offset(enumfacing3);
                    this.setAttachmentPos(blockpos);
                }
                else
                {
                    this.tryTeleportToNewPosition();
                }
            }

            BlockPos blockpos1 = blockpos.offset(this.getAttachmentFacing());

            if (!this.worldObj.isBlockNormalCubeDefault(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ(), false))
            {
                boolean flag = false;

                
                for (EnumFacing enumfacing1 : EnumFacing.values())
                {
                    if (this.worldObj.isBlockNormalCubeDefault(blockpos.getX() + enumfacing1.getFrontOffsetX(), blockpos.getY() + enumfacing1.getFrontOffsetY(),
                    		blockpos.getZ() + enumfacing1.getFrontOffsetZ(), false))
                    {
                        this.getDataWatcher().updateObject(ATTACHED_FACE, (byte)enumfacing1.ordinal());
                        flag = true;
                        break;
                    }
                }

                if (!flag)
                {
                    this.tryTeleportToNewPosition();
                }
            }

        	int ordinal = getAttachmentFacing().ordinal();
        	int opposite = ordinal = ordinal % 2 == 0 ? ordinal + 1 : ordinal - 1;
            BlockPos blockpos2 = blockpos.offset(EnumFacing.getFront(opposite));

            if (this.worldObj.isBlockNormalCubeDefault(blockpos2.getX(), blockpos2.getY(), blockpos2.getZ(), false))
            {
                this.tryTeleportToNewPosition();
            }
        }

        float f1 = (float)this.getPeekTick() * 0.01F;
        this.currentPeekAmount0 = this.currentPeekAmount;

        if (this.currentPeekAmount > f1)
        {
            this.currentPeekAmount = MathHelper.clamp_float(this.currentPeekAmount - 0.05F, f1, 1.0F);
        }
        else if (this.currentPeekAmount < f1)
        {
            this.currentPeekAmount = MathHelper.clamp_float(this.currentPeekAmount + 0.05F, 0.0F, f1);
        }

        if (blockpos != null)
        {
            if (this.worldObj.isRemote)
            {
                if (this.clientSideTeleportInterpolation > 0 && this.currentAttachmentPosition != null)
                {
                    --this.clientSideTeleportInterpolation;
                }
                else
                {
                    this.currentAttachmentPosition = blockpos;
                }
            }

            this.lastTickPosX = this.prevPosX = this.posX = (double)blockpos.getX() + 0.5D;
            this.lastTickPosY = this.prevPosY = this.posY = (double)blockpos.getY();
            this.lastTickPosZ = this.prevPosZ = this.posZ = (double)blockpos.getZ() + 0.5D;
            double d3 = 0.5D - (double)MathHelper.sin((0.5F + this.currentPeekAmount) * (float)Math.PI) * 0.5D;
            double d4 = 0.5D - (double)MathHelper.sin((0.5F + this.currentPeekAmount0) * (float)Math.PI) * 0.5D;
            double d5 = d3 - d4;
            double d0 = 0.0D;
            double d1 = 0.0D;
            double d2 = 0.0D;
            EnumFacing enumfacing2 = this.getAttachmentFacing();

            switch (enumfacing2)
            {
                case DOWN:
                default:
                    this.boundingBox.setBounds(this.posX - 0.5D, this.posY, this.posZ - 0.5D, this.posX + 0.5D, this.posY + 1.0D + d3, this.posZ + 0.5D);
                    d1 = d5;
                    break;

                case UP:
                    this.boundingBox.setBounds(this.posX - 0.5D, this.posY - d3, this.posZ - 0.5D, this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D);
                    d1 = -d5;
                    break;

                case NORTH:
                    this.boundingBox.setBounds(this.posX - 0.5D, this.posY, this.posZ - 0.5D, this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D + d3);
                    d2 = d5;
                    break;

                case SOUTH:
                    this.boundingBox.setBounds(this.posX - 0.5D, this.posY, this.posZ - 0.5D - d3, this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D);
                    d2 = -d5;
                    break;

                case WEST:
                    this.boundingBox.setBounds(this.posX - 0.5D, this.posY, this.posZ - 0.5D, this.posX + 0.5D + d3, this.posY + 1.0D, this.posZ + 0.5D);
                    d0 = d5;
                    break;
                case EAST:
                    this.boundingBox.setBounds(this.posX - 0.5D - d3, this.posY, this.posZ - 0.5D, this.posX + 0.5D, this.posY + 1.0D, this.posZ + 0.5D);
                    d0 = -d5;
            }

            if (d5 > 0.0D)
            {
                List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox());

                if (!list.isEmpty())
                {
                    for (Entity entity : list)
                    {
                        if (!(entity instanceof EntityShulker) && !entity.noClip)
                        {
                            entity.moveEntity(d0, d1, d2);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void setPosition(double x, double y, double z)
    {
        if (this.getDataWatcher() != null && this.ticksExisted != 0)
        {
            if (this.getDataWatcher().getWatchableObjectInt(ATTACHED_X) != x || this.getDataWatcher().getWatchableObjectInt(ATTACHED_Y) != y
            		|| this.getDataWatcher().getWatchableObjectInt(ATTACHED_Z) != z)
            {
                this.getDataWatcher().updateObject(ATTACHED_X, (int)x);
                this.getDataWatcher().updateObject(ATTACHED_Y, (int)y);
                this.getDataWatcher().updateObject(ATTACHED_Z, (int)z);
                this.getDataWatcher().updateObject(PEEK_TICK, Byte.valueOf((byte)0));
                this.isAirBorne = true;
            }
        }
        
        super.setPosition(x, y, z);
    }
    
    protected boolean tryTeleportToNewPosition()
    {
		if (/* !this.isAIDisabled() && */this.isEntityAlive())
        {

            for (int i = 0; i < 5; ++i)
            {
                int newx = (int) (this.posX + 8 - this.rand.nextInt(17));
                int newy = (int) (this.posY + 8 - this.rand.nextInt(17));
                int newz = (int) (this.posZ + 8 - this.rand.nextInt(17));

				if (newy > 0 && this.worldObj.isAirBlock(newx, newy, newz)
						&& /* this.worldObj.isInsideBorder(this.worldObj.getWorldBorder(), this) && */this.worldObj.getCollidingBoundingBoxes(this, AxisAlignedBB.getBoundingBox(newx, newy, newz, newx + 1, newy + 1, newz + 1)).isEmpty())
                {
                    boolean flag = false;

                    for (EnumFacing enumfacing : EnumFacing.values())
                    {
                        if (this.worldObj.isBlockNormalCubeDefault(newx + enumfacing.getFrontOffsetX(), newy + enumfacing.getFrontOffsetY(),
                        		newz + enumfacing.getFrontOffsetZ(), false))
                        {
                            this.getDataWatcher().updateObject(ATTACHED_FACE, (byte)enumfacing.ordinal());
                            flag = true;
                            break;
                        }
                    }

                    if (flag)
                    {
//                        this.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, 1.0F, 1.0F);
                    	this.setAttachmentPos(new BlockPos(newx, newy, newz));
                        this.getDataWatcher().updateObject(PEEK_TICK, Byte.valueOf((byte)0));
                        this.setAttackTarget(null);
                        return true;
                    }
                }
            }

            return false;
        }
		return true;
    }
    
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevRenderYawOffset = 180.0F;
        this.renderYawOffset = 180.0F;
        this.rotationYaw = 180.0F;
    }

    public void func_145781_i(int key) {
        if ((key == ATTACHED_X || key == ATTACHED_Y || key == ATTACHED_Z) && this.worldObj.isRemote && !this.isRiding())
        {
            BlockPos blockpos = this.getAttachmentPos();

            if (blockpos.getY() != -1)
            {
                if (this.currentAttachmentPosition == null)
                {
                    this.currentAttachmentPosition = blockpos;
                }
                else if(this.ticksExisted > 0)
                {
                    this.clientSideTeleportInterpolation = 6;
                }

                this.lastTickPosX = this.prevPosX = this.posX = (double)blockpos.getX() + 0.5D;
                this.lastTickPosY = this.prevPosY = this.posY = (double)blockpos.getY();
                this.lastTickPosZ = this.prevPosZ = this.posZ = (double)blockpos.getZ() + 0.5D;
            }
        }

        super.func_145781_i(key);
    }

    /**
     * Called when the entity is attacked.
     */
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isClosed())
        {
            Entity entity = source.getSourceOfDamage();

            if (entity instanceof EntityArrow)
            {
                return false;
            }
        }

        if (super.attackEntityFrom(source, amount))
        {
            if ((double)this.getHealth() < (double)this.getMaxHealth() * 0.5D && this.rand.nextInt(4) == 0)
            {
                this.tryTeleportToNewPosition();
            }

            return true;
        }
		return false;
    }

    private boolean isClosed()
    {
        return this.getPeekTick() == 0;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox()
    {
        return this.isEntityAlive() ? boundingBox : null;
    }

    public EnumFacing getAttachmentFacing()
    {
        return EnumFacing.getFront(this.getDataWatcher().getWatchableObjectByte(ATTACHED_FACE));
    }

    public void setAttachmentPos(BlockPos pos)
    {
        this.getDataWatcher().updateObject(ATTACHED_X, pos.getX());
        this.getDataWatcher().updateObject(ATTACHED_Y, pos.getY());
        this.getDataWatcher().updateObject(ATTACHED_Z, pos.getZ());
    }

    public int getPeekTick()
    {
        return this.getDataWatcher().getWatchableObjectByte(PEEK_TICK);
    }

    
//  @Override
//  public void updateArmorModifier(int p_184691_1_)
//  {
//      if (!this.worldObj.isRemote)
//      {
//          this.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(COVERED_ARMOR_BONUS_MODIFIER);
//
//          if (p_184691_1_ == 0)
//          {
//              this.getEntityAttribute(SharedMonsterAttributes.ARMOR).applyModifier(COVERED_ARMOR_BONUS_MODIFIER);
//              this.playSound(SoundEvents.ENTITY_SHULKER_CLOSE, 1.0F, 1.0F);
//          }
//          else
//          {
//              this.playSound(SoundEvents.ENTITY_SHULKER_OPEN, 1.0F, 1.0F);
//          }
//      }
//
//      this.dataManager.set(PEEK_TICK, Byte.valueOf((byte)p_184691_1_));
//  }
    
    public int getTotalArmorValue()
    {
        return isClosed() ? 20 : getTotalArmorValue();
    }

    public float getClientPeekAmount(float p_184688_1_)
    {
        return this.currentPeekAmount0 + (this.currentPeekAmount - this.currentPeekAmount0) * p_184688_1_;
    }

    public int getClientTeleportInterp()
    {
        return this.clientSideTeleportInterpolation;
    }

    public BlockPos getOldAttachPos()
    {
        return this.currentAttachmentPosition;
    }

    @Override
    public float getEyeHeight()
    {
        return 0.5F;
    }

    /**
     * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
     * use in wolves.
     */
    @Override
    public int getVerticalFaceSpeed()
    {
        return 180;
    }

//    @Override
//    public int getHorizontalFaceSpeed()
//    {
//        return 180;
//    }
    
    @Override
    public void applyEntityCollision(Entity entityIn)
    {
    }

    @Override
    public float getCollisionBorderSize()
    {
        return 0.0F;
    }
    
    public boolean isAttachedToBlock()
    {
        return this.currentAttachmentPosition != null && this.getAttachmentPos().getY() != -1;
    }

    protected Item getDropItem()
    {
        return ModItems.shulker_shell;
    }
    
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        Item item = this.getDropItem();

        if (item != null)
        {
            int j = this.rand.nextInt(2 + p_70628_2_);

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(item, 1);
            }
        }
    }
}
