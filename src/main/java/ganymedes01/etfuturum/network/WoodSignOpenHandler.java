package ganymedes01.etfuturum.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.client.gui.GuiEditWoodSign;
import ganymedes01.etfuturum.tileentities.TileEntityWoodSign;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;

public class WoodSignOpenHandler implements IMessageHandler<WoodSignOpenMessage, IMessage> {

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(WoodSignOpenMessage message, MessageContext ctx) {
        WorldClient world = FMLClientHandler.instance().getClient().theWorld;
        if (world.blockExists(message.tileX, message.tileY, message.tileZ)) {
            TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.tileX, message.tileY, message.tileZ);

            if (tileEntity == null || !(tileEntity instanceof TileEntityWoodSign)) {
                tileEntity = new TileEntityWoodSign();
                int i = message.id % ModBlocks.signs.length;
                System.out.println(i);
                tileEntity.blockType = message.id >= ModBlocks.signs.length ? ModBlocks.wall_signs[i] : ModBlocks.signs[i];
                tileEntity.setWorldObj(FMLClientHandler.instance().getClient().theWorld);
                tileEntity.xCoord = message.tileX;
                tileEntity.yCoord = message.tileY;
                tileEntity.zCoord = message.tileZ;
            }

            tileEntity.markDirty();

            FMLClientHandler.instance().getClient().displayGuiScreen(new GuiEditWoodSign((TileEntityWoodSign) tileEntity));
        }
		return null;
	}

}
