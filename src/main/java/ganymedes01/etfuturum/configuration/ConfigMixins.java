package ganymedes01.etfuturum.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigMixins {
	
	public static boolean fireArrowsDetonateTNTCarts;
	
	static final String categoryBackport = "backported features";

	public static void loadMixinConfig(File configFile) {
		Configuration config = new Configuration(configFile);
		
		fireArrowsDetonateTNTCarts = config.getBoolean("fireArrowsDetonateTNTCarts", categoryBackport, true, "Minecarts with TNT explode when hit by fire arrows.\nFrom MC 1.8, fixes MC-8987");
		
		if(config.hasChanged()) {
            config.save();
        }
	}
	
}
