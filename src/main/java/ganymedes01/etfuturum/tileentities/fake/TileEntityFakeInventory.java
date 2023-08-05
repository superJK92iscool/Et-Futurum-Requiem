package ganymedes01.etfuturum.tileentities.fake;

import ganymedes01.etfuturum.api.inventory.FakeTileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Simple tile entity class for use with {@link FakeTileEntityProvider} that delegates all inventory calls
 * to the provided {@link IInventory}.
 */
public final class TileEntityFakeInventory extends TileEntity implements IInventory, ISidedInventory {
    private final ISidedInventory delegate;

    public TileEntityFakeInventory(ISidedInventory delegate) {
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

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return delegate.getAccessibleSlotsFromSide(p_94128_1_);
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return delegate.canInsertItem(p_102007_1_, p_102007_2_, p_102007_3_);
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return delegate.canExtractItem(p_102008_1_, p_102008_2_, p_102008_3_);
    }
}
