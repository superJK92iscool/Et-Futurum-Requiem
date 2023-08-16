package ganymedes01.etfuturum.compat.waila;

import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class ShulkerDataProvider implements IWailaDataProvider
{

	@Override
	public ItemStack getWailaStack (IWailaDataAccessor accessor, IWailaConfigHandler config)
	{
		ItemStack stack = accessor.getStack();
		if(accessor.getTileEntity() instanceof TileEntityShulkerBox) {
			TileEntityShulkerBox box = (TileEntityShulkerBox)accessor.getTileEntity();
			if(!stack.hasTagCompound()) {
				stack.setTagCompound(new NBTTagCompound());
			}
			stack.getTagCompound().setByte("Type", (byte) box.type.ordinal());
			stack.getTagCompound().setByte("Color", box.color);
		}
		return stack;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP arg0, TileEntity arg1, NBTTagCompound arg2, World arg3, int arg4, int arg5, int arg6) {
		return arg2;
	}
}