package ganymedes01.etfuturum.api.brewing;

import ganymedes01.etfuturum.core.utils.BrewingFuelRegistry;
import net.minecraft.item.ItemStack;

public class BrewingFuelRegistryHelper {

	@Deprecated
	/**
	 * 
	 * Use BrewingFuelRegistry.registerFuel instead.
	 * 
	 * @param fuel
	 * @param brews
	 */
	public static void registerFuel(ItemStack fuel, int brews) {
//      try {
			BrewingFuelRegistry.registerFuel(fuel, brews);
//          Class<?> cls = Class.forName("ganymedes01.etfuturum.recipes.BrewingFuelRegistry");
//          Method m = cls.getMethod("registerFuel", ItemStack.class, int.class);
//          m.invoke(null, fuel, brews);
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
	}

	@Deprecated
	/**
	 * 
	 * Use BrewingFuelRegistry.registerAdvancedFuel instead.
	 * 
	 * @param fuel
	 * @param brews
	 */
	public static void registerAdvancedFuel(IBrewingFuel fuel) {
//      try {
			BrewingFuelRegistry.registerAdvancedFuel(fuel);
//          Class<?> cls = Class.forName("ganymedes01.etfuturum.recipes.BrewingFuelRegistry");
//          Method m = cls.getMethod("registerAdvancedFuel", IBrewingFuel.class);
//          m.invoke(null, fuel);
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
	}
}