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
		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":block.beacon.ambient", 7F);
		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":block.beehive.drip", 8F);
		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":block.beehive.enter", 14F);
		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":block.beehive.exit", 14F);
		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":block.beehive.work", 12F);
		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":block.sculk_catalyst.bloom", 12F);

		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":entity.bee.loop", 6F);
		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":entity.bee.loop_aggressive", 10F);
		CUSTOM_ATTENUATION_VALUES.put(Reference.MCAssetVer + ":entity.bee.pollinate", 12F);

		CUSTOM_ATTENUATION_VALUES.put("minecraft:portal.portal", 10F);
	}

	@ModifyConstant(method = "playSound", constant = @Constant(floatValue = 16f, ordinal = 0))
	public float captureAttenuation(float f1, ISound sound) {
		return CUSTOM_ATTENUATION_VALUES.getOrDefault(sound.getPositionedSoundLocation().toString(), f1);
	}
}
