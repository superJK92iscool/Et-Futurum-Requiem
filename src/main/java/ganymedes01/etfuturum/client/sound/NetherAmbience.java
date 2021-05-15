package ganymedes01.etfuturum.client.sound;

import java.util.Random;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;

public class NetherAmbience extends PositionedSound implements ITickableSound {
	
	public NetherAmbience(String string, float theVolume, float thePitch) {
		super(new ResourceLocation(string));
		volume = theVolume;
		field_147663_c = thePitch;
		field_147666_i = ISound.AttenuationType.NONE;
		if(string.equals(Reference.MOD_ID + ":ambient.warped_forest.additions.w3_p0.8") || (string.equals(Reference.MOD_ID + ":ambient.crimson_forest.additions.w4") && new Random().nextInt(2) == 0)) {
			field_147663_c = 0.8F;
		} else
		if(string.equals(Reference.MOD_ID + ":ambient.warped_forest.additions.w3_p0.7") ||
				string.equals(Reference.MOD_ID + ":ambient.soul_sand_valley.additions.w5_p0.7") ||
				((string.equals(Reference.MOD_ID + ":ambient.warped_forest.additions.w2") ||
						string.equals(Reference.MOD_ID + ":ambient.warped_forest.additions.w2")) && new Random().nextInt(2) == 0)) {
			field_147663_c = 0.7F;
		} else
		if(string.equals(Reference.MOD_ID + ":ambient.soul_sand_valley.additions.w25_p0.75")) {
			field_147663_c = 0.75F;
		} else
		if(string.equals(Reference.MOD_ID + ":ambient.warped_forest.additions.w3_p0.1")) {
			field_147663_c = 0.1F;
		}
		if(string.equals(Reference.MOD_ID + ":ambient.crimson_forest.additions.w6")) {
			field_147663_c = 0.5F;
		} else
		if(string.equals(Reference.MOD_ID + ":ambient.warped_forest.additions.w6") || string.equals(Reference.MOD_ID + ":ambient.warped_forest.additions.w10")) {
			field_147663_c = 0.2F;
		}
	}

	@Override
	public boolean isDonePlaying() {
		return Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer.dimension != -1;
	}

	@Override
	public void update() {
	}
}
