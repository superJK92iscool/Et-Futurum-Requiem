package ganymedes01.etfuturum.configuration.configs;

import ganymedes01.etfuturum.configuration.ConfigBase;

import java.io.File;

public class ConfigModCompat extends ConfigBase {

	public static int elytraBaublesExpandedCompat;
	public static boolean shulkerBoxesIronChest;

	public static boolean moddedRawOres;

	static final String catMisc = "misc";

	public ConfigModCompat(File file) {
		super(file);

		configCats.add(getCategory(catMisc));
	}


	//TODO: Move Iron Chest checks here
	@Override
	protected void syncConfigOptions() {
		shulkerBoxesIronChest = getBoolean("shulkerBoxesIronChest", catMisc, true, "If Iron Chests is installed, allow Iron Shulker boxes to be crafted having all the same tiers as Iron Chests. This option does nothing if Iron Chests is not installed.");
		elytraBaublesExpandedCompat = getInt("elytraBaublesExpandedCompat", catMisc, 1, 0, 2, "Adds compat for Baubles Expanded. Does nothing for standard baubles, this REQUIRES Baubles Expanded! It uses the new \"wings\" slot added by the expanded version. https://modrinth.com/mod/baubles-expanded\nWhen enabled, this allows the player to equip an elytra with a chestplate, by placing the elytra in a \"wings\" slot instead of the chestplate slot. Note that the player can only equip one elytra at a time.\n0 = No compat, do not allow the elytra to be placed in a wings slot.\n1 = Elytra will be placeable in a wings slot. Will enable the slot, if it isn't there.\n2 = The elytra can ONLY go in the wings slots, not the chestplate slot.");

		moddedRawOres = getBoolean("moddedRawOres", catMisc, true, "Raw ores for modded metals.");
	}
}
