package ganymedes01.etfuturum.client.sound.step;

import ganymedes01.etfuturum.client.sound.ModSounds.CustomSound;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;

public class CustomSoundBerryBush extends CustomSound {

	public CustomSoundBerryBush() {
		super("sweet_berry_bush", 0.8F, 1, true);
	}

	@Override
	public String getStepResourcePath() {
		return Block.soundTypeGrass.getStepResourcePath();
	}
}
