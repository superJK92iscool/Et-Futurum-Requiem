package ganymedes01.etfuturum.client.sound.step;

import java.util.Random;

import ganymedes01.etfuturum.client.sound.ModSounds.CustomSound;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;

public class CustomSoundPlants extends CustomSound {

	private Random random = new Random();
	
	private final SoundType stepSound;

	public CustomSoundPlants(String name, float volume, float pitch, SoundType sound) {
		super(name, volume, pitch);
		stepSound = sound;
	}

	@Override
	public float getPitch()
	{
		return super.getPitch() + (random.nextBoolean() ? 0 : soundName.equals("crop") ? .2F : .12F);
	}

	@Override
	public String getBreakSound() {
		return Reference.MCv118 + ":block." + soundName + ".break";
	}

	@Override
	public String getStepResourcePath() {
		return stepSound.getStepResourcePath();
	}

	@Override
	public String func_150496_b()
	{
		return Reference.MCv118 + ":item." + soundName + ".plant";
	}

}
