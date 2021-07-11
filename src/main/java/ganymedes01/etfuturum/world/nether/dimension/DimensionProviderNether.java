package ganymedes01.etfuturum.world.nether.dimension;

import net.minecraftforge.common.DimensionManager;

public class DimensionProviderNether {

	public static void init() {
		unregisterProviders();
		registerProviders();
		registerDimensions();
	}

	private static void unregisterProviders() {
		DimensionManager.unregisterProviderType(-1);
	}

	private static void registerProviders() {
		DimensionManager.registerProviderType(-1, NetherWorldProvider.class, true);
	}

	private static void registerDimensions() {
	}

}