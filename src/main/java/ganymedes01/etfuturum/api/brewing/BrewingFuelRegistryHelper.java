package ganymedes01.etfuturum.api.brewing;

import ganymedes01.etfuturum.core.utils.BrewingFuelRegistry;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Method;

public class BrewingFuelRegistryHelper {

	/**
	 * 
	 * Reflection-based function mods can copy to use this function without making EFR a compile dep
	 * 
	 * @param fuel
	 * @param brews
	 */
	public static void registerFuel(ItemStack fuel, int brews) {
	  try {
		  Class<?> cls = Class.forName("ganymedes01.etfuturum.core.utils.BrewingFuelRegistry");
		  Method m = cls.getMethod("registerFuel", ItemStack.class, int.class);
		  m.invoke(null, fuel, brews);
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	}

//  @Deprecated
//  /**
//   *
//   * Use BrewingFuelRegistry.registerAdvancedFuel instead.
//   *
//   * @param fuel
//   * @param brews
//   */
//  public static void registerAdvancedFuel(IBrewingFuel fuel) {
//      try {
//          Class<?> cls = Class.forName("ganymedes01.etfuturum.core.utils.BrewingFuelRegistry");
//          Method m = cls.getMethod("registerAdvancedFuel", IBrewingFuel.class);
//          m.invoke(null, fuel);
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//  }
}