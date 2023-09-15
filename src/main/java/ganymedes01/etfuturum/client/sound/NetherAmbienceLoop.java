package ganymedes01.etfuturum.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;

public class NetherAmbienceLoop extends PositionedSound implements ITickableSound {

	public boolean isStopping;

	public NetherAmbienceLoop(String loc) {
		super(new ResourceLocation(loc));
		xPosF = yPosF = zPosF = 0;
		repeat = true;
		field_147666_i = ISound.AttenuationType.NONE;
		volume = 0.02F;
	}

	@Override
	public void update() {
		if (isStopping) {
			volume -= 0.02F;
		} else if (volume != 1 && (volume += 0.02F) > 1) {
			volume = 1;
		}
	}

	public void stop() {
		isStopping = true;
	}

	@Override
	public boolean isDonePlaying() {
		return Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().theWorld.provider.dimensionId != -1 || volume <= 0.0F;
	}
}
