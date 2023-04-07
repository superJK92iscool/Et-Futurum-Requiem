package ganymedes01.etfuturum.core.utils;

import java.util.ArrayList;
import java.util.List;

import ganymedes01.etfuturum.api.brewing.IBrewingFuel;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BrewingFuelRegistry {

	private static final ItemStackMap<Integer> fuels = new ItemStackMap<>();
	private static final List<IBrewingFuel> advFuels = new ArrayList<>();

	static {
		fuels.put(new ItemStack(Items.blaze_powder), 30);
	}

	public static void registerFuel(ItemStack fuel, int brews) {
		fuels.put(fuel, brews);
	}

	public static void registerAdvancedFuel(IBrewingFuel fuel) {
		advFuels.add(fuel);
	}

	public static boolean isFuel(ItemStack fuel) {
		return getBrewingAmount(fuel) > 0;
	}

	public static int getBrewingAmount(ItemStack fuel) {
		Integer time = fuels.get(fuel);
		if(time != null && time > 0) {
			return time;
		}

		for (IBrewingFuel advFuel : advFuels) {
			time = advFuel.getBrewingAmount(fuel);
			if (time > 0) {
				return time;
			}
		}

		return 0;
	}
}