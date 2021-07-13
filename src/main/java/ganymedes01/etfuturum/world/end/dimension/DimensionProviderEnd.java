package ganymedes01.etfuturum.world.end.dimension;

import net.minecraftforge.common.DimensionManager;

public class DimensionProviderEnd {

	public static void init() {
		unregisterProviders();
		registerProviders();
		registerDimensions();
	}

	private static void unregisterProviders() {
		DimensionManager.unregisterProviderType(1);
	}

	private static void registerProviders() {
		DimensionManager.registerProviderType(1, EndWorldProvider.class, true);
	}

	private static void registerDimensions() {
	}

}