package ganymedes01.etfuturum.tileentities;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.BlockBarrel;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityBarrel extends TileEntity implements IInventory {
	private int ticksSinceSync;
	private float soundTimer;
	private String customName;
	private ItemStack[] chestContents = new ItemStack[36];
	public int numPlayersUsing;
	//TODO: Fish barrel Easter Egg

	public int getSizeInventory()
	{
		return 27;
	}

	public boolean isUseableByPlayer(EntityPlayer p_70300_1_)
	{
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	public void readFromNBT(NBTTagCompound p_145839_1_)
	{
		super.readFromNBT(p_145839_1_);
		NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
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

		if (p_145839_1_.hasKey("CustomName", 8))
		{
			this.customName = p_145839_1_.getString("CustomName");
		}
	}

	public void writeToNBT(NBTTagCompound p_145841_1_)
	{
		super.writeToNBT(p_145841_1_);
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

		p_145841_1_.setTag("Items", nbttaglist);

		if (this.hasCustomInventoryName())
		{
			p_145841_1_.setString("CustomName", this.customName);
		}
	}
	
	public ItemStack getStackInSlot(int p_70301_1_)
	{
		return this.chestContents[p_70301_1_];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
	 * new stack.
	 */
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
			else
			{
				itemstack = this.chestContents[p_70298_1_].splitStack(p_70298_2_);

				if (this.chestContents[p_70298_1_].stackSize == 0)
				{
					this.chestContents[p_70298_1_] = null;
				}

				this.markDirty();
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
	 * like when you close a workbench GUI.
	 */
	public ItemStack getStackInSlotOnClosing(int p_70304_1_)
	{
		if (this.chestContents[p_70304_1_] != null)
		{
			ItemStack itemstack = this.chestContents[p_70304_1_];
			this.chestContents[p_70304_1_] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}
	
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_)
	{
		this.chestContents[p_70299_1_] = p_70299_2_;

		if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit())
		{
			p_70299_2_.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	public void updateEntity()
	{
		BlockBarrel barrel = null;
		if(worldObj.getBlock(xCoord, yCoord, zCoord) == ModBlocks.barrel)
			barrel = (BlockBarrel)worldObj.getBlock(xCoord, yCoord, zCoord);
		++this.ticksSinceSync;
		float f;

		if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0)
		{
			this.numPlayersUsing = 0;
			f = 5.0F;
			List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)((float)this.xCoord - f), (double)((float)this.yCoord - f), (double)((float)this.zCoord - f), (double)((float)(this.xCoord + 1) + f), (double)((float)(this.yCoord + 1) + f), (double)((float)(this.zCoord + 1) + f)));
			Iterator iterator = list.iterator();

			while (iterator.hasNext())
			{
				EntityPlayer entityplayer = (EntityPlayer)iterator.next();

				if (entityplayer.openContainer instanceof ContainerChest)
				{
					++this.numPlayersUsing;
				}
			}
		}

		f = 0.1F;
		double d2;

		if (this.numPlayersUsing > 0 && this.soundTimer <= 0.0F)
		{
			
			double d1 = (double)this.xCoord + 0.5D;
			d2 = (double)this.zCoord + 0.5D;

			this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) % 8 + 8, 2);
			this.worldObj.playSoundEffect(d1, (double)this.yCoord + 0.5D, d2, Reference.MOD_ID + ":block.barrel.open", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			
		}

		if (this.numPlayersUsing == 0 && this.soundTimer > 0.0F || this.numPlayersUsing > 0 && this.soundTimer < 1.0F)
		{
			d2 = (double)this.xCoord + 0.5D;
			float f1 = 0.5F;

			if (this.numPlayersUsing > 0)
			{
				this.soundTimer += f1;
			}
			else
			{
				this.soundTimer -= f1;
			}

			if (this.soundTimer > 10)
			{
				this.soundTimer = 10;
			}

			float f2 = 5;

			if (this.soundTimer < f1 && worldObj.getBlockMetadata(xCoord, yCoord, zCoord) > 7)
			{
				this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, this.worldObj.getBlockMetadata(xCoord, yCoord, zCoord) % 8, 2);
				this.worldObj.playSoundEffect(d2, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D, Reference.MOD_ID + ":block.barrel.close", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if (this.soundTimer < 0)
			{
				this.soundTimer = 0;
			}
		}
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName :  "container." + Reference.MOD_ID + ".barrel";
	}
	
	public boolean hasCustomInventoryName()
	{
		return this.customName != null && this.customName.length() > 0;
	}

	public void func_145976_a(String p_145976_1_)
	{
		this.customName = p_145976_1_;
	}
	public boolean receiveClientEvent(int p_145842_1_, int p_145842_2_)
	{
		if (p_145842_1_ == 1)
		{
			this.numPlayersUsing = p_145842_2_;
			return true;
		}
		else
		{
			return super.receiveClientEvent(p_145842_1_, p_145842_2_);
		}
	}

	public void openInventory()
	{
		if (this.numPlayersUsing < 0)
		{
			this.numPlayersUsing = 0;
		}

		++this.numPlayersUsing;
		this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
	}

	public void closeInventory()
	{
		if (this.getBlockType() instanceof BlockBarrel)
		{
			--this.numPlayersUsing;
			this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
			this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
		}
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
	 */
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_)
	{
		return true;
	}

	/**
	 * invalidates a tile entity
	 */
	public void invalidate()
	{
		super.invalidate();
		this.updateContainingBlockInfo();
	}

}
