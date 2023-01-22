package ganymedes01.etfuturum.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;

public class AttackYawHandler implements IMessageHandler<AttackYawMessage, IMessage> {

	@Override
	public IMessage onMessage(AttackYawMessage message, MessageContext ctx) {
		Minecraft.getMinecraft().thePlayer.attackedAtYaw = (float) message.attackedAtYaw;
		return null;
	}

}
