package ganymedes01.etfuturum.client.sound.step;

import java.util.Random;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block.SoundType;

public class CustomSoundNetherite extends SoundType {

	private Random random = new Random();
	
	public CustomSoundNetherite(String p_i45393_1_, float p_i45393_2_, float p_i45393_3_) {
		super(p_i45393_1_, p_i45393_2_, p_i45393_3_);
	}

	public float getPitch()
	{
		return this.volume - (random.nextInt(2) == 0 ? 0 : .1F);
	}
	
	@Override
	public String getBreakSound() {
		return Reference.MOD_ID + ":" + super.getBreakSound();
	}

	@Override
	public String getStepResourcePath() {
		return Reference.MOD_ID + ":" + super.getStepResourcePath();
	}

}
