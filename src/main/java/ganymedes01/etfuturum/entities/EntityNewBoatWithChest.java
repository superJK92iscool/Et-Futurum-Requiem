package ganymedes01.etfuturum.entities;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

public class EntityNewBoatWithChest extends EntityNewBoat implements IInventory {
	private ItemStack[] boatItems = new ItemStack[27];
	private boolean dropContentsWhenDead = true;

	public EntityNewBoatWithChest(World world) {
		super(world);
	}

	@Override
	protected boolean shouldHaveSeat() {
		return false;
	}

	@Override
	public int getSizeInventory() {
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return boatItems[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack returnValue = null;
		if (boatItems[slot] != null) {
			if (boatItems[slot].stackSize <= amount) {
				returnValue = boatItems[slot];
				boatItems[slot] = null;
			} else {
				returnValue = boatItems[slot].splitStack(amount);
				if (boatItems[slot].stackSize == 0)
					boatItems[slot] = null;
			}
		}
		return returnValue;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		if (boatItems[p_70304_1_] != null) {
			ItemStack is = boatItems[p_70304_1_];
			boatItems[p_70304_1_] = null;
			return is;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		this.boatItems[p_70299_1_] = p_70299_2_;

		if (p_70299_2_ != null && p_70299_2_.stackSize > this.getInventoryStackLimit()) {
			p_70299_2_.stackSize = this.getInventoryStackLimit();
		}
	}

	public boolean hasCustomInventoryName() {
		return getBoatName() != null;
	}

	@Override
	public String getInventoryName() {
		return hasCustomInventoryName() ? getBoatName() : "container.etfuturum.chest_boat";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return !this.isDead && p_70300_1_.getDistanceSqToEntity(this) <= 64.0D;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		return true;
	}

	public void travelToDimension(int p_71027_1_) {
		this.dropContentsWhenDead = false;
		super.travelToDimension(p_71027_1_);
	}

	/**
	 * Will get destroyed next tick.
	 */
	public void setDead() {
		if (this.dropContentsWhenDead) {
			for (int i = 0; i < this.getSizeInventory(); ++i) {
				ItemStack itemstack = this.getStackInSlot(i);

				if (itemstack != null) {
					float f = this.rand.nextFloat() * 0.8F + 0.1F;
					float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
					float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						int j = this.rand.nextInt(21) + 10;

						if (j > itemstack.stackSize) {
							j = itemstack.stackSize;
						}

						itemstack.stackSize -= j;
						EntityItem entityitem = new EntityItem(this.worldObj, this.posX + (double) f, this.posY + (double) f1, this.posZ + (double) f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (float) this.rand.nextGaussian() * f3;
						entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
						entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
						this.worldObj.spawnEntityInWorld(entityitem);
					}
				}
			}
		}

		super.setDead();
	}

	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		super.writeEntityToNBT(p_70014_1_);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.boatItems.length; ++i) {
			if (this.boatItems[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.boatItems[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		p_70014_1_.setTag("Items", nbttaglist);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		super.readEntityFromNBT(p_70037_1_);
		NBTTagList nbttaglist = p_70037_1_.getTagList("Items", 10);
		this.boatItems = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound1.getByte("Slot") & 255;

			if (j < this.boatItems.length) {
				this.boatItems[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	/**
	 * First layer of player interaction
	 */
	public boolean interactFirst(EntityPlayer p_130002_1_) {
		if (!this.worldObj.isRemote && p_130002_1_.isSneaking()) {
			p_130002_1_.displayGUIChest(this);
			return true;
		}
		return super.interactFirst(p_130002_1_);
	}

	@Override
	protected float getDefaultRiderOffset() {
		return 0.2f;
	}

	public float getChestHeight() {
		return isRaft() ? 0.15625f : -0.2f;
	}

	public float getChestXOffset() {
		return isRaft() ? -0.46875f : -0.5f;
	}

	public float getChestZOffset() {
		return isRaft() ? -1.15f : -1.1f;
	}
}
