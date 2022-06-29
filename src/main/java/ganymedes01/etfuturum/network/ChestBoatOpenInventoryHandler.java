package ganymedes01.etfuturum.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ganymedes01.etfuturum.entities.EntityChestBoat;
import net.minecraft.entity.player.EntityPlayerMP;

public class ChestBoatOpenInventoryHandler implements IMessageHandler<ChestBoatOpenInventoryMessage, IMessage> {
    @Override
    public IMessage onMessage(ChestBoatOpenInventoryMessage message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().playerEntity;
        if(player.ridingEntity instanceof EntityChestBoat)
            player.displayGUIChest((EntityChestBoat)player.ridingEntity);
        return null;
    }
}
