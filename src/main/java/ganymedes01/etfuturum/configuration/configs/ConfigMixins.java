package ganymedes01.etfuturum.configuration.configs;

import java.io.File;

import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;

public class ConfigMixins extends ConfigBase {
	
	public ConfigMixins(File file) {
		super(file);
	}

	static final String categoryBackport = "backported features";
	static final String categoryOptimization = "optimizations";
	static final String categoryMisc = "miscellaneous";
	private static ConfigMixins configInstance = new ConfigMixins(new File(Launch.minecraftHome, "config" + File.separator + Reference.MOD_ID + File.separator + "mixins.cfg"));
	
	public static boolean fireArrowsDetonateTNTCarts;
	
	public static boolean deepslateLayerOptimization;

	protected void syncConfigs() {
		loadMixinConfig(getConfigFile());
	}
	
	public static void loadMixinConfig(File file) {
		
		Configuration cfg = configInstance;
		
		fireArrowsDetonateTNTCarts = cfg.getBoolean("fireArrowsDetonateTNTCarts", categoryBackport, true, "Minecarts with TNT explode when hit by fire arrows.\nFrom MC 1.8, fixes MC-8987");
		deepslateLayerOptimization = cfg.getBoolean("deepslateLayerOptimization", categoryOptimization, true, "If \"deepslateGenerationMode\" is set to 0, this config option is used. This optimizes deepslate generation by adding on to the stone generator instead of sending lots of setblocks, making the performance impact of using the layer deepslate option from fairly noticeable, to none.");
		
		if(cfg.hasChanged()) {
            cfg.save();
        }
	}
	
}