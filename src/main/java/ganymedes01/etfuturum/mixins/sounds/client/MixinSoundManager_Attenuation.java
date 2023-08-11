package ganymedes01.etfuturum.mixins.sounds.client;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.HashMap;

@Mixin(SoundManager.class)
public class MixinSoundManager_Attenuation {

	@Unique
	private static final HashMap<String, Float> CUSTOM_ATTENUATION_VALUES = new HashMap<>();

	static {
		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":block.beacon.ambient", 8F);
		CUSTOM_ATTENUATION_VALUES.put("minecraft:portal.portal", 8F);
	}

	@ModifyConstant(method = "playSound", constant = @Constant(floatValue = 16f, ordinal = 0))
	public float captureAttenuation(float f1, ISound sound) {
		return CUSTOM_ATTENUATION_VALUES.getOrDefault(sound.getPositionedSoundLocation().toString(), f1);
	}
}
