package ganymedes01.etfuturum.configuration.configs;

import ganymedes01.etfuturum.configuration.ConfigBase;

import java.io.File;

public class ConfigModCompat extends ConfigBase {

	public static int elytraBaublesExpandedCompat;
	public static boolean shulkerBoxesIronChest;

	public static boolean moddedRawOres;
	public static boolean moddedDeepslateOres;

	static final String catMisc = "misc";

	public ConfigModCompat(File file) {
		super(file);

		getCategory(catMisc).setComment("When modded deepslate or raw ore support is enabled, OreDictionary tags will be given to the needed Lord of the Rings ores since they don't have any tags for some reason.\nLoTR Ores that have no raw/deepslate modded alternative will not be tagged.");

		configCats.add(getCategory(catMisc));
	}


	//TODO: Move Iron Chest checks here
	@Override
	protected void syncConfigOptions() {
		shulkerBoxesIronChest = getBoolean("shulkerBoxesIronChest", catMisc, true, "If Iron Chests is installed, allow Iron Shulker boxes to be crafted having all the same tiers as Iron Chests. This option does nothing if Iron Chests is not installed.");
		elytraBaublesExpandedCompat = getInt("elytraBaublesExpandedCompat", catMisc, 1, 0, 2, "Adds compat for Baubles Expanded. Does nothing for standard baubles, this REQUIRES Baubles Expanded! It uses the new \"wings\" slot added by the expanded version. https://modrinth.com/mod/baubles-expanded\nWhen enabled, this allows the player to equip an elytra with a chestplate, by placing the elytra in a \"wings\" slot instead of the chestplate slot. Note that the player can only equip one elytra at a time.\n0 = No compat, do not allow the elytra to be placed in a wings slot.\n1 = Elytra will be placeable in a wings slot. Will enable the slot, if it isn't there.\n2 = The elytra can ONLY go in the wings slots, not the chestplate slot.");

		moddedRawOres = getBoolean("moddedRawOres", catMisc, true, "Raw ores for modded metals."
						/*"\nIncludes the following items, and blocks for them: "
				+ Utils.getNamesForConfig(ItemModdedRawOre.names) + "\nIf you don't have any of these in your game you should probably turn this off to save IDs. Only the variations for metals your game actually has will appear in-game."*/);
		moddedDeepslateOres = getBoolean("moddedDeepslateOres", catMisc, true, "Deepslate ores for modded ores."
				/*"Includes the following blocks: "*/
				/*+ Utils.getNamesForConfig(BlockModdedDeepslateOre.names) + "\nIf you don't have any of these in your game you should probably turn this off to save IDs. Only the variations for ores your game actually has will appear in-game."*/);
		//For some reason these cause the Entity class to shit itself and the game fails to launch even if I move getNamesForConfig to this class.
	}
}
