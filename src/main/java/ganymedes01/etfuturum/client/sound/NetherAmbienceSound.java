package ganymedes01.etfuturum.client.sound;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class NetherAmbienceSound extends PositionedSoundRecord {

	public NetherAmbienceSound(ResourceLocation resourceLoc) {
		super(resourceLoc, 1, 1, 0, 0, 0);
		this.field_147666_i = ISound.AttenuationType.NONE;
	}

}
