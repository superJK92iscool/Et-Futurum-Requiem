package ganymedes01.etfuturum.client.sound.step;

import java.util.Random;

import ganymedes01.etfuturum.client.sound.ModSounds.CustomSound;
import ganymedes01.etfuturum.lib.Reference;

public class CustomSoundNetherite extends CustomSound {

	private Random random = new Random();
	
	public CustomSoundNetherite() {
		super("netherite_block");
	}

	@Override
	public float getPitch()
	{
		return this.volume - (random.nextBoolean() ? 0 : .1F);
	}

}
