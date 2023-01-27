package ganymedes01.etfuturum;

import java.util.ArrayList;
import java.util.List;

import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.core.utils.Logger;
import me.eigenraven.lwjgl3ify.api.ConfigUtils;

public class CompatMisc {

	public static void doLwjgl3ifyCompat() {
		try {
			List<String> extensibleEnums = new ArrayList<>();
			if(ConfigMixins.enableSpectatorMode) {
				extensibleEnums.add("net.minecraft.world.WorldSettings$GameType");
			}
			
			if(!extensibleEnums.isEmpty()) {
				ConfigUtils utils = new ConfigUtils(null);
				Logger.trace("Found lwjgl3ify, registering the following extensible enum classes: " + extensibleEnums);
				for(String e : extensibleEnums) {
					utils.addExtensibleEnum(e);
				}
			}
			
		} catch(Throwable t) {
			if(t instanceof NoClassDefFoundError) {
				Logger.trace("Failed to apply lwjgl3ify compat: " + t + ". This is not an error unless lwjgl3ify is present.");
			} else {
				Logger.warn("Failed to apply lwjgl3ify compat");
				t.printStackTrace();
			}
		}
	}

}
