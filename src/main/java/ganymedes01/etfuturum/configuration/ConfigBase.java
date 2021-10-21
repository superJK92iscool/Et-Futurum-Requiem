package ganymedes01.etfuturum.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigEnchantsPotions;
import ganymedes01.etfuturum.configuration.configs.ConfigEntities;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class ConfigBase extends Configuration {
	
	public static boolean hasIronChest;
	public static boolean hasNetherlicious;
	
	private static final String catClientLegacy = "client";
	private static final String catBlockLegacy = "blocks";
	private static final String catItemsLegacy = "items";
	private static final String catEquipmentLegacy = "equipment";
	private static final String catEnchantsLegacy = "enchants";
	private static final String catEntityLegacy = "entity";
	private static final String catReplacementLegacy = "replacement";
	private static final String catFunctionLegacy = "function";
	private static final String catWorldLegacy = "world";

	protected static String configDir = "config" + File.separator + Reference.MOD_ID;
	public static final String PATH = configDir + File.separator + "etfuturum.cfg";
	private static ConfigBase configInstance = new ConfigBase(new File(Launch.minecraftHome, PATH));
	
	public ConfigBase(File file) {
		super(file);
	}
	
	public void syncOptions() {
		syncConfigs();
		
		if (hasChanged()) {
			save();
		}
	}

	protected void syncConfigs() {
		loadBaseConfig();
	}
	
	public static void loadBaseConfig() {
		
		Configuration cfg = configInstance;

//      enableDyeReplacement = cfg.getBoolean("enableDyeReplacement", catFunction, true, "Removes lapis, bone meal, ink sac and cocoa bean's ore dictionary entries as dyes, making the Et Futurum dyes the dyes instead. Disable if this causes weirdisms with modded recipes. (If false both items can be used)");
		
		ConfigEntities.enableDragonRespawn = cfg.getBoolean("enableDragonRespawn", catEntityLegacy, true, "");
		ConfigEntities.enableNetherEndermen = cfg.getBoolean("enableNetherEndermen", catEntityLegacy, true, "Allow endermen to rarely spawn in the Nether");
		
		//mobs
		ConfigEntities.enableRabbit = cfg.getBoolean("enableRabbits", catEntityLegacy, true, "");
		ConfigEntities.enableEndermite = cfg.getBoolean("enableEndermite", catEntityLegacy, true, "");
		ConfigEntities.enableVillagerZombies = cfg.getBoolean("enableZombieVillager", catEntityLegacy, true, "");
		
		ConfigEntities.enableHusk = cfg.getBoolean("enableHusks", catEntityLegacy, true, "Desert zombie variant");
		ConfigEntities.enableStray = cfg.getBoolean("enableStrays", catEntityLegacy, true, "Tundra skeleton variant");
		ConfigEntities.enableBrownMooshroom = cfg.getBoolean("enableBrownMooshroom", catEntityLegacy, true, "Brown mooshroom variant");
		ConfigEntities.enableShulker = cfg.getBoolean("enableShulker", catEntityLegacy, true, "Shell-lurking mobs from the End.");
		
		//function
		ConfigEntities.enableShearableSnowGolems = cfg.getBoolean("enableShearableSnowGolems", catFunctionLegacy, true, "");
		ConfigEntities.enableBabyGrowthBoost = cfg.getBoolean("enableBabyGrowthBoost", catFunctionLegacy, true, "");
		ConfigEntities.enableVillagerTurnsIntoWitch = cfg.getBoolean("enableVillagerTurnsIntoWitch", catFunctionLegacy, true, "Villagers turn into Witches when struck by lightning");
		
		//replacement //Requires enableNewTileEntities. If you want to switch your tile entities from the \"new\" ones to the vanilla blocks, disable this, load the chunks with your tile entities and then disable enableNewTileEntities.
//        enableNewTileEntities = cfg.getBoolean("enableNewTileEntities", catReplacement, true, "(NOT IMPLEMENTED, THIS IS JUST HERE IN ANTICIPATION FOR UPCOMING CHANGES, this currently just disables the \"new\" tile entities without doing anything to the vanilla blocks!) If disabled, the functionality for the anvil, beacon, daylight sensors and enchant tables will append vanilla blocks instead of being swapped out with modded versions.");

	 
//        enableNewNether = cfg.getBoolean("enableNewNether", catWorldLegacy, true, "When false, the new Nether will not generate, regardless of if the new Nether blocks are on. (Will be ignored if Netherlicious is installed)");

		if(cfg.hasChanged()) {
            cfg.save();
        }
	}
	
	public static void preInit() {
		Block block = ConfigWorld.fossilBlockID == 1 ? block = GameRegistry.findBlock("netherlicious", "BoneBlock") : GameRegistry.findBlock("uptodate", "bone_block");
		ConfigWorld.fossilBoneBlock = ConfigWorld.fossilBlockID == 0 || block == null ? ModBlocks.bone_block : block;
		
		hasIronChest = Loader.isModLoaded("IronChest");
		hasNetherlicious = Loader.isModLoaded("netherlicious");
//		if(hasNetherlicious) // Come back to
//			ConfigWorld.enableNewNether = false;
	}
	
	public static void setupShulkerBanlist() {
		ConfigFunctions.shulkerBans = new ArrayList<Item>();
		for(String itemName : ConfigFunctions.shulkerBansString) {
			String[] nameAndID;
			if(itemName.contains(":") && (nameAndID = itemName.split(":")).length == 2) {
				Item item = GameRegistry.findItem(nameAndID[0], nameAndID[1]);
				if(item != null) {
					ConfigFunctions.shulkerBans.add(item);
				}
			} else {
				System.err.println("Shulker ban list entry \"" + itemName + "\" is formatted incorrectly!");
			}
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
//		getConfigFile().deleteOnExit();
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncOptions();
	}
}
