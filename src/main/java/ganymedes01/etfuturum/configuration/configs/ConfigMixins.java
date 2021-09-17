package ganymedes01.etfuturum.configuration.configs;

import java.io.File;

import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraftforge.common.config.Configuration;

public class ConfigMixins extends ConfigBase {
	
	public ConfigMixins(File file) {
		super(file);
	}

	public static boolean fireArrowsDetonateTNTCarts;
	
	public static boolean deepslateLayerOptimization;
	
	static final String categoryBackport = "backports";
	static final String categoryOptimizations = "optimizations";

	@Override
	protected void syncConfigs() {
		fireArrowsDetonateTNTCarts = cfg.getBoolean("fireArrowsDetonateTNTCarts", categoryBackport, true, "Minecarts with TNT explode when hit by fire arrows.\nFrom MC 1.8, fixes MC-8987");

		deepslateLayerOptimization = cfg.getBoolean("deepslateLayerOptimization", categoryOptimizations, true, "If \"deepslateGenerationMode\" is set to 0, this config option is used. This optimizes deepslate generation by adding on to the stone generator instead of sending lots of setblocks, making the performance impact of using the layer deepslate option from fairly noticeable, to none.");
	}
	
}
