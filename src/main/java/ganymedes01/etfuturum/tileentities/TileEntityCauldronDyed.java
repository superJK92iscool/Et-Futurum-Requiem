package ganymedes01.etfuturum.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityCauldronDyed extends TileEntityCauldronColoredWater {
	
	public int dyeColor;

	@Override
	public int getWaterColor() {
		return dyeColor;
	}
	
	public void readFromNBT(NBTTagCompound p_145839_1_)
	{
		super.readFromNBT(p_145839_1_);
		dyeColor = p_145839_1_.getInteger("dyeColor");
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
		p_145841_1_.setInteger("dyeColor", Math.max(0, dyeColor));
	}

}
