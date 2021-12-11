package ganymedes01.etfuturum.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class NetherAmbienceLoop extends PositionedSoundRecord implements ITickableSound {

	public boolean isStopping;
	
	public NetherAmbienceLoop(String loc) {
		super(new ResourceLocation(loc), 1, 1, 0, 0, 0);
		volume = 1;
		repeat = true;
		field_147666_i = ISound.AttenuationType.NONE;
	}

	@Override
	public void update() {
		if(isStopping) {
			volume -= 0.02F;
		} else
		if(volume != 1 && (volume += 0.02F) > 1 && !isStopping) {
			volume = 1;
		}
	}
	
	public void fadeIn() {
		volume = 0.02F;
	}
	
	public void stop() {
		isStopping = true;
	}

	@Override
	public boolean isDonePlaying() {
		return Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer.dimension != -1 || volume <= 0.0F;
	}
}
