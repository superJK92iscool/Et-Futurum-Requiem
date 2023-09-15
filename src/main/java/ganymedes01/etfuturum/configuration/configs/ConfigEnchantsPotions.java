package ganymedes01.etfuturum.configuration.configs;

import ganymedes01.etfuturum.configuration.ConfigBase;

import java.io.File;

public class ConfigEnchantsPotions extends ConfigBase {

	public static boolean enableFrostWalker;
	public static boolean enableMending;
	public static boolean enableSwiftSneak;
	public static int mendingID;
	public static int frostWalkerID;
	public static int swiftSneakID;
	public static int levitationID;

	static final String catEnchants = "enchantments";
	static final String catPotions = "potions";


	public ConfigEnchantsPotions(File file) {
		super(file);
		setCategoryComment(catEnchants, "Settings for enchantments.");
		setCategoryComment(catPotions, "Settings for potions. Potion IDs are very limited so it is highly recommended to use a potion ID expansion.");
		
		configCats.add(getCategory(catEnchants));
		configCats.add(getCategory(catPotions));
	}

	@Override
	protected void syncConfigOptions() {
		//enchants
		enableFrostWalker = getBoolean("frostWalker", catEnchants, true, "");
		frostWalkerID = getInt("frostWalkerID", catEnchants, 200, 0, 255, "");
		enableMending = getBoolean("mending", catEnchants, true, "");
		mendingID = getInt("mendingID", catEnchants, 201, 0, 255, "");
		enableSwiftSneak = getBoolean("swiftSneak", catEnchants, true, "");
		swiftSneakID = getInt("swiftSneakID", catEnchants, 202, 0, 255, "");
		
		//potions
		levitationID  = getInt("levitationID", catPotions, 27, 0, Byte.MAX_VALUE, "Since this is essential for Shulkers, this is tied to Shulkers being enabled instead of having its own option.");
	}

}
