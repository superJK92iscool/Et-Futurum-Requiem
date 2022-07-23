package ganymedes01.etfuturum.tileentities;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityCauldronPotion extends TileEntityCauldronColoredWater {
	
	public ItemStack potion;

	@Override
	public int getWaterColor() {
		if(potion != null && potion.getItem() instanceof ItemPotion) {
			return ((ItemPotion)potion.getItem()).getColorFromDamage(potion.getItemDamage());
		}
		return 0;
	}

    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
    	super.readFromNBT(p_145839_1_);
        this.potion = ItemStack.loadItemStackFromNBT(p_145839_1_.getCompoundTag("Potion"));
    	
    	if(potion == null || !(potion.getItem() instanceof ItemPotion)) {
    		System.err.println("Cauldron @ " + xCoord + " " + yCoord + " " + zCoord + " had an invalid potion ItemStack. Resetting to a normal cauldron.");
    		resetCauldron();
    		return;
    	}
    }
    
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		
		this.writeToNBT(nbt);
		
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
    	super.writeToNBT(p_145841_1_);
    	if(potion != null) {
    		p_145841_1_.setTag("Potion", this.potion.writeToNBT(new NBTTagCompound()));
    	}
    }
}
