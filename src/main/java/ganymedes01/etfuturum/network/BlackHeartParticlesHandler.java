package ganymedes01.etfuturum.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.client.particle.ParticleHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class BlackHeartParticlesHandler implements IMessageHandler<BlackHeartParticlesMessage, IMessage> {

	@Override
	public IMessage onMessage(BlackHeartParticlesMessage message, MessageContext ctx) {
		handleMessage(message);
		return null;
	}

	@SideOnly(Side.CLIENT)
	private void handleMessage(BlackHeartParticlesMessage message) {
		World world = Minecraft.getMinecraft().theWorld;
		double x = message.x;
		double y = message.y;
		double z = message.z;

		ParticleHandler.DAMAGE_HEART.spawn(world, x, y, z);
	}
}