package ganymedes01.etfuturum.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;

public class TileEntityCauldronPotion extends TileEntityCauldronColoredWater {
	
	public Potion potion;

	@Override
	public int getWaterColor() {
		if(potion != null) {
			return potion.getLiquidColor();
		}
		return 0;
	}

    public void readFromNBT(NBTTagCompound p_145839_1_)
    {
    	super.readFromNBT(p_145839_1_);
    	try {
    		potion = Potion.potionTypes[p_145839_1_.getByte("potionID")];
    	} catch (ArrayIndexOutOfBoundsException e) {
    		System.err.println("Potion ID was out of range! Did you uninstall a potion ID extender?");
    		e.printStackTrace();
    	}
    	
    	if(potion == null) {
    		System.err.println("Cauldron @ " + xCoord + " " + yCoord + " " + zCoord + " had an invalid potion ID. Resetting to a normal cauldron.");
    		resetCauldron();
    		return;
    	}
    }
    
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		
		this.writeToNBT(nbt);
    	if(potion != null) {
    		nbt.setByte("potionID", (byte) potion.getId());
    	}
		
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
	}

    public void writeToNBT(NBTTagCompound p_145841_1_)
    {
    	super.writeToNBT(p_145841_1_);
    	if(potion != null) {
        	p_145841_1_.setByte("potionID", (byte) potion.getId());
    	}
    }
}
