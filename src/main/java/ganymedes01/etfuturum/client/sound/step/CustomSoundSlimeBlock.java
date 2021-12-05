package ganymedes01.etfuturum.client.sound.step;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block.SoundType;

public class CustomSoundSlimeBlock extends SoundType {

	public CustomSoundSlimeBlock() {
		super("minecraft:mob.slime.big", 1, 1);
	}

	@Override
	public String getBreakSound() {
		return soundName;
	}

	@Override
	public String getStepResourcePath() {
		return "minecraft:mob.slime.small";
	}

	@Override
	public String func_150496_b()
	{
		return getBreakSound();
	}
}
