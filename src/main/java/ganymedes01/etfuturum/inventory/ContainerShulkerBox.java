package ganymedes01.etfuturum.inventory;

import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerShulkerBox extends Container {
	
    private IInventory lowerChestInventory;
    private int numRows;
    private int rowSize;

	public ContainerShulkerBox(IInventory p_i1806_1_, IInventory p_i1806_2_) {
        this.lowerChestInventory = p_i1806_2_;
        this.numRows = p_i1806_2_.getSizeInventory() / ((TileEntityShulkerBox)p_i1806_2_).getRowSize();
        this.rowSize = ((TileEntityShulkerBox)p_i1806_2_).getRowSize();
        p_i1806_2_.openInventory();
        int i = (this.numRows - 4) * 18;
        int j;
        int k;

        for (j = 0; j < this.numRows; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new ContainerShulkerBox.ShulkerSlot(p_i1806_2_, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(p_i1806_1_, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(p_i1806_1_, j, 8 + j * 18, 161 + i));
        }
    }

//    protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, ShulkerBoxType type, int xSize, int ySize)
//    {
//        for (int chestRow = 0; chestRow < type.getSize() / type.getRowSize(); chestRow++)
//        {
//            for (int chestCol = 0; chestCol < type.getRowSize(); chestCol++)
//            {
//                addSlotToContainer(new ContainerShulkerBox.ShulkerSlot(chestInventory, chestCol + chestRow * type.getRowSize(), 12 + chestCol * 18, 8 + chestRow * 18));
//            }
//        }
//
//        int leftCol = (xSize - 162) / 2 + 1;
//        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++)
//        {
//            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++)
//            {
//                addSlotToContainer(new Slot(playerInventory, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, ySize - (4 - playerInvRow) * 18
//                        - 10));
//            }
//
//        }
//
//        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++)
//        {
//            addSlotToContainer(new Slot(playerInventory, hotbarSlot, leftCol + hotbarSlot * 18, ySize - 24));
//        }
//    }

    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return this.lowerChestInventory.isUseableByPlayer(p_75145_1_);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            
            if (p_82846_2_ < this.numRows * 9)
            {
                if (!this.mergeItemStack(itemstack1, this.numRows * rowSize, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * rowSize, false)) // Row Size
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
    
    protected boolean mergeItemStack(ItemStack p_75135_1_, int p_75135_2_, int p_75135_3_, boolean p_75135_4_)
    {
        int k = p_75135_2_;

        if (p_75135_4_)
        {
            k = p_75135_3_ - 1;
        }
        
        while (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)
        {
            Slot slot = (Slot)this.inventorySlots.get(k);
            
            if(slot instanceof ShulkerSlot && !slot.isItemValid(p_75135_1_)) {
            	return false;
            }

            if (p_75135_4_)
            {
                --k;
            }
            else
            {
                ++k;
            }
        }

        return super.mergeItemStack(p_75135_1_, p_75135_2_, p_75135_3_, p_75135_4_);
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);
        this.lowerChestInventory.closeInventory();
    }

    class ShulkerSlot extends Slot
    {

        public ShulkerSlot(IInventory p_i1801_2_, int p_i1801_3_, int p_i1801_4_, int p_i1801_5_)
        {
            super(p_i1801_2_, p_i1801_3_, p_i1801_4_, p_i1801_5_);
        }

        /**
         * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
         */
        @Override
        public boolean isItemValid(ItemStack stack)
        {
            return inventory.isItemValidForSlot(getSlotIndex(), stack);
        }

        /**
         * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the
         * case of armor slots)
         */
        public int getSlotStackLimit()
        {
            return 64;
        }
    }
}
