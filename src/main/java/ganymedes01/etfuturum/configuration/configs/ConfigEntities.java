package ganymedes01.etfuturum.configuration.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class ConfigEntities extends ConfigBase {

	public static boolean enableStray;
	public static boolean enableEndermite;
	public static boolean enableVillagerZombies;
	public static boolean enableVillagerTurnsIntoWitch;
	public static boolean enableHusk;
	public static boolean enableShulker;
	public static boolean enableBrownMooshroom;
	public static boolean enableBabyGrowthBoost;
	public static boolean enableRabbit;
	public static boolean enableDragonRespawn;
	public static boolean enableNetherEndermen;
	public static boolean enableShearableSnowGolems;
	
	private static final List<ConfigCategory> configCats = new ArrayList<ConfigCategory>();
	
	public static List<ConfigCategory> getConfigCats() {
		return configCats;
	}

	static final String catHostile = "hostile";
	static final String catNeutral = "neutral";
	static final String catPassive = "passive";
	static final String catMisc = "misc";

	public static final String PATH = configDir + File.separator + "entities.cfg";
	public static final ConfigEntities configInstance = new ConfigEntities(new File(Launch.minecraftHome, PATH));

	public ConfigEntities(File file) {
		super(file);
		setCategoryComment(catHostile, "Hostile entities.");
		setCategoryComment(catNeutral, "Neutral entities.");
		setCategoryComment(catPassive, "Passive entities.");
		setCategoryComment(catMisc, "Entity settings that don't fit into any other category, typically entity settings.");
		
		configCats.add(getCategory(catHostile));
		configCats.add(getCategory(catNeutral));
		configCats.add(getCategory(catPassive));
		configCats.add(getCategory(catMisc));
	}

	protected void syncConfigs() {
		Configuration cfg = configInstance;
		
		//passive
		ConfigEntities.enableRabbit = cfg.getBoolean("enableRabbits", catPassive, true, "");
		ConfigEntities.enableBrownMooshroom = cfg.getBoolean("enableBrownMooshroom", catPassive, true, "Brown mooshroom variant.");

		//neutral
		ConfigEntities.enableEndermite = cfg.getBoolean("enableEndermite", catHostile, true, "Rarely spawns when the player lands from Ender Pearl throws");
		ConfigEntities.enableHusk = cfg.getBoolean("enableHusks", catHostile, true, "Desert zombie variant");
		ConfigEntities.enableStray = cfg.getBoolean("enableStrays", catHostile, true, "Tundra skeleton variant");
		ConfigEntities.enableShulker = cfg.getBoolean("enableShulker", catHostile, true, "Shell-lurking mobs from the End.");
		
		//function
		ConfigEntities.enableShearableSnowGolems = cfg.getBoolean("enableShearableSnowGolems", catMisc, true, "");
		ConfigEntities.enableBabyGrowthBoost = cfg.getBoolean("enableBabyGrowthBoost", catMisc, true, "");
		ConfigEntities.enableVillagerTurnsIntoWitch = cfg.getBoolean("enableVillagerTurnsIntoWitch", catMisc, true, "Villagers turn into Witches when struck by lightning");
		ConfigEntities.enableVillagerZombies = cfg.getBoolean("enableZombieVillager", catMisc, true, "");
		ConfigEntities.enableDragonRespawn = cfg.getBoolean("enableDragonRespawn", catMisc, true, "Crude implementation of respawning the dragon using four End crystals.");
		ConfigEntities.enableNetherEndermen = cfg.getBoolean("enableNetherEndermen", catMisc, true, "Allow endermen to rarely spawn in the Nether");
	}

}
