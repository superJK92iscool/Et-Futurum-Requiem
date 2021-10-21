package ganymedes01.etfuturum.configuration.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class ConfigTweaks extends ConfigBase {

	public static boolean dyableShulkers;

	public static boolean enableOldGravel;
	public static boolean enableRoses;
	
	public static boolean shulkersSpawnAnywhere;
	public static final String catAbandoned = "abandoned ideas";
	public static final String catCustomTweaks = "custom tweaks";
	public static final String catBedrockParity = "bedrock parity";
	
	private static final List<ConfigCategory> configCats = new ArrayList<ConfigCategory>();
	
	public static List<ConfigCategory> getConfigCats() {
		return configCats;
	}

	public static final String PATH = configDir + File.separator + "tweaks.cfg";
	public static final ConfigTweaks configInstance = new ConfigTweaks(new File(Launch.minecraftHome, PATH));
	
	public ConfigTweaks(File file) {
		super(file);
		setCategoryComment(catBedrockParity, "Features that differ from Bedrock Edition in one way or another, or new content that isn't in Java Edition at all.");
		setCategoryComment(catAbandoned, "Scrapped concepts, abandoned ideas, old versions of changed content, etc.");
		setCategoryComment(catCustomTweaks, "Tweaks made that are original and not vanilla in any way.");
		
		configCats.add(getCategory(catBedrockParity));
		configCats.add(getCategory(catAbandoned));
	}

	protected void syncConfigs() {
		Configuration cfg = configInstance;

		dyableShulkers = cfg.getBoolean("dyableShulkers", catBedrockParity, true, "Clicking a Shulker to dye them. As an added bonus, you can also click them with a water bucket (water is not consumed) or pour water on them to remove the dye.");
		
		enableRoses = cfg.getBoolean("enableOldRoses", catAbandoned, true, "");
		enableOldGravel = cfg.getBoolean("enableOldGravel", catAbandoned, true, "");
		
		shulkersSpawnAnywhere = cfg.getBoolean("shulkersSpawnAnywhere", catCustomTweaks, false, "For compatibility reasons, you may want the Shulker to spawn anywhere in the End in random groups like Endermen. These are uncommon.\nShulkers spawned in this way will despawn naturally, unless seated, given armor through a dispenser, or name tagged. Right now Shulkers are otherwise inacessible.");	
	}

}
