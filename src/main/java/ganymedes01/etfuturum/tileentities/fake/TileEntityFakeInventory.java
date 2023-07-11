package ganymedes01.etfuturum.tileentities.fake;

import ganymedes01.etfuturum.api.inventory.FakeTileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Simple tile entity class for use with {@link FakeTileEntityProvider} that delegates all inventory calls
 * to the provided {@link IInventory}.
 */
public final class TileEntityFakeInventory extends TileEntity implements IInventory {
    private final IInventory delegate;

    public TileEntityFakeInventory(IInventory delegate) {
        this.delegate = delegate;
    }

    @Override
    public int getSizeInventory() {
        return delegate.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return delegate.getStackInSlot(p_70301_1_);
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return delegate.decrStackSize(p_70298_1_, p_70298_2_);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return delegate.getStackInSlotOnClosing(p_70304_1_);
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        delegate.setInventorySlotContents(p_70299_1_, p_70299_2_);
    }

    @Override
    public String getInventoryName() {
        return delegate.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return delegate.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return delegate.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return delegate.isUseableByPlayer(p_70300_1_);
    }

    @Override
    public void openInventory() {
        delegate.openInventory();
    }

    @Override
    public void closeInventory() {
        delegate.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return delegate.isItemValidForSlot(p_94041_1_, p_94041_2_);
    }

    @Override
    public void markDirty() {
        delegate.markDirty();
    }
}
