package ganymedes01.etfuturum.lib.sounds;

import ganymedes01.etfuturum.lib.ModSounds;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block.SoundType;

public class CustomSoundFungus extends SoundType {

	public CustomSoundFungus(String p_i45393_1_, float p_i45393_2_, float p_i45393_3_) {
		super(p_i45393_1_, p_i45393_2_, p_i45393_3_);
	}

    
	@Override
	public String getBreakSound() {
		return Reference.MOD_ID + ":" + super.getBreakSound();
	}

	@Override
	public String getStepResourcePath() {
		return Reference.MOD_ID + ":step.netherwart";
	}
}
