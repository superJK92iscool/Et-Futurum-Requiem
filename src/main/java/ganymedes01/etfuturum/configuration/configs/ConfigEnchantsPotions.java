package ganymedes01.etfuturum.configuration.configs;

import java.io.File;

import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;

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

	public static final String PATH = configDir + File.separator + "enchantspotions.cfg";
	public static final ConfigEnchantsPotions configInstance = new ConfigEnchantsPotions(new File(Launch.minecraftHome, PATH));

	public ConfigEnchantsPotions(File file) {
		super(file);
		setCategoryComment(catEnchants, "Settings for enchantments.");
		setCategoryComment(catPotions, "Settings for potions. Potion IDs are very limited so it is highly recommended to use a potion ID expansion.");
		
		configCats.add(getCategory(catEnchants));
		configCats.add(getCategory(catPotions));
	}

	@Override
	protected void syncConfigOptions() {
		Configuration cfg = configInstance;
		
		//enchants
		enableFrostWalker = cfg.getBoolean("frostWalker", catEnchants, true, "");
		frostWalkerID = cfg.getInt("frostWalkerID", catEnchants, 2000, 0, Short.MAX_VALUE, "");
		enableMending = cfg.getBoolean("mending", catEnchants, true, "");
		mendingID = cfg.getInt("mendingID", catEnchants, 2001, 0, Short.MAX_VALUE, "");
		enableSwiftSneak = cfg.getBoolean("swiftSneak", catEnchants, true, "");
		swiftSneakID = cfg.getInt("swiftSneakID", catEnchants, 2002, 0, Short.MAX_VALUE, "");
		
		//potions
		levitationID  = cfg.getInt("levitationID", catPotions, 27, 0, Byte.MAX_VALUE, "Since this is essential for Shulkers, this is tied to Shulkers being enabled instead of having its own option.");
	}

}
