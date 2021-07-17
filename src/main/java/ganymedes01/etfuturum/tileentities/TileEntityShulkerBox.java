package ganymedes01.etfuturum.tileentities;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockShulkerBox;
import ganymedes01.etfuturum.inventory.ContainerShulkerBox;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class TileEntityShulkerBox extends TileEntity implements IInventory {
	private int ticksSinceSync;
	private String customName;
	public ItemStack[] chestContents = new ItemStack[36];
	public int numPlayersUsing;
	protected boolean brokenInCreative = false;
	public boolean destroyed = false;
	public int color = 0;

	public TileEntityShulkerBox() {
        this.field_190599_i = TileEntityShulkerBox.AnimationStatus.CLOSED;
	}
	
	public TileEntityShulkerBox(int color) {
		this();
		this.color = color;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 27;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p_70300_1_.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		this.chestContents = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j >= 0 && j < this.chestContents.length)
			{
				this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
		
		if (nbt.hasKey("Color")) {
			color = nbt.getInteger("Color");
		}

		if (nbt.hasKey("CustomName", 8))
		{
			this.customName = nbt.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.chestContents.length; ++i)
		{
			if (this.chestContents[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				this.chestContents[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);
		if(color > 0) {
			nbt.setInteger("Color", color);
		}

		if (this.hasCustomInventoryName())
		{
			nbt.setString("CustomName", this.customName);
		}
	}
	
	@Override
	public ItemStack getStackInSlot(int p_70301_1_)
	{
		return this.chestContents[p_70301_1_];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 */
	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
	{
		if (this.chestContents[p_70298_1_] != null)
		{
			ItemStack itemstack;

			if (this.chestContents[p_70298_1_].stackSize <= p_70298_2_)
			{
				itemstack = this.chestContents[p_70298_1_];
				this.chestContents[p_70298_1_] = null;
				this.markDirty();
				return itemstack;
			}
			itemstack = this.chestContents[p_70298_1_].splitStack(p_70298_2_);

			if (this.chestContents[p_70298_1_].stackSize == 0)
			{
				this.chestContents[p_70298_1_] = null;
			}

			this.markDirty();
			return itemstack;
		}
		return null;
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	 * like when you close a workbench GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if (this.chestContents[p_70304_1_] != null)
		{
			ItemStack itemstack = this.chestContents[p_70304_1_];
			this.chestContents[p_70304_1_] = null;
			return itemstack;
		}
		return null;
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		this.chestContents[p_70299_1_] = p_70299_2_;

		if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
		{
			p_70299_2_.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public void updateEntity()
	{
        this.func_190583_o();

        if (this.field_190599_i == TileEntityShulkerBox.AnimationStatus.OPENING || this.field_190599_i == TileEntityShulkerBox.AnimationStatus.CLOSING)
        {
            this.func_190589_G();
        }
        
		++this.ticksSinceSync;
		float f;

		if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
		{
			this.numPlayersUsing = 0;
			f = 5.0F;
			List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.xCoord - f, this.yCoord - f, this.zCoord - f, this.xCoord + 1 + f, this.yCoord + 1 + f, this.zCoord + 1 + f));
			Iterator<EntityPlayer> iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityPlayer entityplayer = iterator.next();

				if (entityplayer.openContainer instanceof ContainerShulkerBox)
				{
					++this.numPlayersUsing;
				}
			}
		}
	}
	
	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName :  "container." + Reference.MOD_ID + ".shulker_box";
	}
	
	@Override
	public boolean hasCustomInventoryName()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	public void func_145976_a(String p_145976_1_)
	{
		this.customName = p_145976_1_;
	}
	
	@Override
	public boolean receiveClientEvent(int id, int type)
	{
        if (id == 1)
        {
            this.numPlayersUsing = type;

            if (type == 0)
            {
                this.field_190599_i = TileEntityShulkerBox.AnimationStatus.CLOSING;
            }

            if (type == 1)
            {
                this.field_190599_i = TileEntityShulkerBox.AnimationStatus.OPENING;
            }

            return true;
        }
		return super.receiveClientEvent(id, type);
	}

	@Override
	public void openInventory()
	{
		if (this.numPlayersUsing < 0)
		{
			this.numPlayersUsing = 0;
		}

		++this.numPlayersUsing;

		if (this.numPlayersUsing == 1)
		{
			this.worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, Reference.MOD_ID + ":block.shulker_box.open", 1, 1);
		}
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
	}

	@Override
	public void closeInventory()
	{
		if (this.getBlockType() instanceof BlockShulkerBox)
		{
			--this.numPlayersUsing;
			if (this.numPlayersUsing <= 0)
			{
				this.worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, Reference.MOD_ID + ":block.shulker_box.close", 1, 1);
			}
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
		}
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return !(Block.getBlockFromItem(p_94041_2_ == null ? null : p_94041_2_.getItem()) instanceof BlockShulkerBox);
	}

	/**
	 * invalidates a tile entity
	 */
	@Override
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
	}
	
	public boolean onBlockBreak(EntityPlayer player) {
		brokenInCreative = player.capabilities.isCreativeMode;
		return true;
	}
	
	
	public void onBlockDestroyed() {
		if (destroyed) return;
		destroyed = true;
		boolean empty = true;
		for (int i = 0; i < chestContents.length; i++) {
			if (chestContents[i] != null) {
				empty = false;
				break;
			}
		}
		// Don't drop an empty Shulker Box in creative.
		if (!empty || !brokenInCreative) {
			ItemStack stack = new ItemStack(ModBlocks.shulker_box);
    		NBTTagList nbttaglist = new NBTTagList();

    		for (int l = 0; l < this.chestContents.length; ++l)
    		{
    			if (this.chestContents[l] != null)
    			{
    				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
    				nbttagcompound1.setByte("Slot", (byte)l);
    				this.chestContents[l].writeToNBT(nbttagcompound1);
    				nbttaglist.appendTag(nbttagcompound1);
    			}
    		}

    		stack.setTagCompound(new NBTTagCompound());
    		stack.getTagCompound().setTag("Items", nbttaglist);

    		stack.getTagCompound().setInteger("Color", color);
    		
    		if(this.hasCustomInventoryName())
    		{
    			stack.setStackDisplayName(this.getInventoryName());
    		}
    		
			EntityItem item = new EntityItem(worldObj, xCoord + .5D, yCoord + .5D, zCoord + .5D, stack);
			item.motionX = worldObj.rand.nextGaussian() * 0.05F;
			item.motionY = worldObj.rand.nextGaussian() * 0.05F + 0.2F;
			item.motionZ = worldObj.rand.nextGaussian() * 0.05F;
			item.delayBeforeCanPickup = 10;
			worldObj.spawnEntityInWorld(item);
		}
	}
	
    private float field_190600_j;
    private float field_190601_k;
    private TileEntityShulkerBox.AnimationStatus field_190599_i;

    public float func_190585_a(float p_190585_1_)
    {
        return this.field_190601_k + (this.field_190600_j - this.field_190601_k) * p_190585_1_;
    }

    protected void func_190583_o()
    {
        this.field_190601_k = this.field_190600_j;

        switch (this.field_190599_i)
        {
            case CLOSED:
                this.field_190600_j = 0.0F;
                break;

            case OPENING:
                this.field_190600_j += 0.1F;

                if (this.field_190600_j >= 1.0F)
                {
                    this.func_190589_G();
                    this.field_190599_i = TileEntityShulkerBox.AnimationStatus.OPENED;
                    this.field_190600_j = 1.0F;
                }

                break;

            case CLOSING:
                this.field_190600_j -= 0.1F;

                if (this.field_190600_j <= 0.0F)
                {
                    this.field_190599_i = TileEntityShulkerBox.AnimationStatus.CLOSED;
                    this.field_190600_j = 0.0F;
                }

                break;

            case OPENED:
                this.field_190600_j = 1.0F;
        }
    }

    public TileEntityShulkerBox.AnimationStatus func_190591_p()
    {
        return this.field_190599_i;
    }
    
    private void func_190589_G()
    {
//        IBlockState iblockstate = this.world.getBlockState(this.getPos());
//
//        if (iblockstate.getBlock() instanceof BlockShulkerBox)
//        {
//            EnumFacing enumfacing = (EnumFacing)iblockstate.getValue(BlockShulkerBox.field_190957_a);
//            AxisAlignedBB axisalignedbb = this.func_190588_c(enumfacing).offset(this.pos);
//            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);
//
//            if (!list.isEmpty())
//            {
//                for (int i = 0; i < list.size(); ++i)
//                {
//                    Entity entity = (Entity)list.get(i);
//
//                    if (entity.getPushReaction() != EnumPushReaction.IGNORE)
//                    {
//                        double d0 = 0.0D;
//                        double d1 = 0.0D;
//                        double d2 = 0.0D;
//                        AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox();
//
//                        switch (enumfacing.getAxis())
//                        {
//                            case X:
//                                if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
//                                {
//                                    d0 = axisalignedbb.maxX - axisalignedbb1.minX;
//                                }
//                                else
//                                {
//                                    d0 = axisalignedbb1.maxX - axisalignedbb.minX;
//                                }
//
//                                d0 = d0 + 0.01D;
//                                break;
//
//                            case Y:
//                                if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
//                                {
//                                    d1 = axisalignedbb.maxY - axisalignedbb1.minY;
//                                }
//                                else
//                                {
//                                    d1 = axisalignedbb1.maxY - axisalignedbb.minY;
//                                }
//
//                                d1 = d1 + 0.01D;
//                                break;
//
//                            case Z:
//                                if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
//                                {
//                                    d2 = axisalignedbb.maxZ - axisalignedbb1.minZ;
//                                }
//                                else
//                                {
//                                    d2 = axisalignedbb1.maxZ - axisalignedbb.minZ;
//                                }
//
//                                d2 = d2 + 0.01D;
//                        }
//
//                        entity.moveEntity(MoverType.SHULKER_BOX, d0 * (double)enumfacing.getFrontOffsetX(), d1 * (double)enumfacing.getFrontOffsetY(), d2 * (double)enumfacing.getFrontOffsetZ());
//                    }
//                }
//            }
//        }
    }


    public static enum AnimationStatus
    {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING;
    }
}
