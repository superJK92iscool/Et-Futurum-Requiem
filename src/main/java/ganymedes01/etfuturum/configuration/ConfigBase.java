package ganymedes01.etfuturum.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigEnchants;
import ganymedes01.etfuturum.configuration.configs.ConfigEntities;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigBase extends Configuration {
	
	public ConfigBase(File file) {
		super(file);
	}
	
	public static final String catClientLegacy = "client";
	public static final String catBlockLegacy = "blocks";
	public static final String catItemsLegacy = "items";
	public static final String catEquipmentLegacy = "equipment";
	public static final String catEnchantsLegacy = "enchants";
	public static final String catEntityLegacy = "entity";
	public static final String catReplacementLegacy = "replacement";
	public static final String catFunctionLegacy = "function";
	public static final String catWorldLegacy = "world";

	private static String configDir = "config" + File.separator + Reference.MOD_ID;
	private static ConfigBase configInstance = new ConfigBase(new File(Launch.minecraftHome, configDir + File.separator + "etfuturum.cfg"));
	
	public static boolean hasIronChest;
	public static boolean hasNetherlicious;
	
	public static final List<ConfigCategory> configCats = new ArrayList<ConfigCategory>();
	
	private void syncOptions() {
		syncConfigs();
		
		if (hasChanged()) {
			save();
		}
	}

	protected void syncConfigs() {
		loadBaseConfig(getConfigFile());
	}
	
	public static void loadBaseConfig(File file) {
		
		Configuration cfg = configInstance;
		
		//blocks
		ConfigBlocksItems.enableStones = cfg.getBoolean("enableStones", catBlockLegacy, true, "Enable Granite/Andesite/Diorite");

		ConfigBlocksItems.enableNetherGold = cfg.getBoolean("enableNetherGold", catBlockLegacy, true, "");
		
		ConfigBlocksItems.enableIronTrapdoor = cfg.getBoolean("enableIronTrapdoor", catBlockLegacy, true, "");
		ConfigBlocksItems.enableSponge = cfg.getBoolean("enableSponge", catBlockLegacy, true, "");
		ConfigBlocksItems.enablePrismarine = cfg.getBoolean("enablePrismarine", catBlockLegacy, true, "");
		ConfigBlocksItems.enableDoors = cfg.getBoolean("enableDoors", catBlockLegacy, true, "Enables wood variant doors");
		ConfigBlocksItems.enableTrapdoors = cfg.getBoolean("enableTrapdoors", catBlockLegacy, true, "Enables wood variant trapdoors");
		ConfigBlocksItems.enableInvertedDaylightSensor = cfg.getBoolean("enableInvertedSensor", catBlockLegacy, true, "Inverted Daylight Sensor");
		ConfigBlocksItems.enableCoarseDirt = cfg.getBoolean("enableCoarseDirt", catBlockLegacy, true, "");
		ConfigBlocksItems.enableRedSandstone = cfg.getBoolean("enableRedSandstone", catBlockLegacy, true, "");
		ConfigBlocksItems.enableFences = cfg.getBoolean("enableFences", catBlockLegacy, true, "Enables wood variant fences and gates");
		ConfigBlocksItems.enableBanners = cfg.getBoolean("enableBanners", catBlockLegacy, true, "");
		ConfigBlocksItems.enableSlimeBlock = cfg.getBoolean("enableSlimeBlock", catBlockLegacy, true, "Just bouncy, does not pull blocks.");
		ConfigBlocksItems.enableOldGravel = cfg.getBoolean("enableOldGravel", catBlockLegacy, true, "");
		ConfigBlocksItems.enableChorusFruit = cfg.getBoolean("enableChorusBlocks", catBlockLegacy, true, "Enables chorus plants and purpur blocks");
		ConfigBlocksItems.enableGrassPath = cfg.getBoolean("enableGrassPath", catBlockLegacy, true, "");
		ConfigBlocksItems.enableCryingObsidian = cfg.getBoolean("enableCryingObsidian", catBlockLegacy, true, "");
		ConfigBlocksItems.enableRoses = cfg.getBoolean("enableOldRoses", catBlockLegacy, true, "");
		
		ConfigBlocksItems.enableNewNetherBricks = cfg.getBoolean("enableRedNetherBricks", catBlockLegacy, true, "");
		ConfigBlocksItems.enableNetherwartBlock = cfg.getBoolean("enableNetherwartBlock", catBlockLegacy, true, "");
		ConfigBlocksItems.enableNetherite = cfg.getBoolean("enableNetherite", catBlockLegacy, true, "");
		ConfigBlocksItems.enableMagmaBlock = cfg.getBoolean("enableMagmaBlock", catBlockLegacy, true, "");
		ConfigBlocksItems.enableBoneBlock = cfg.getBoolean("enableBoneBlock", catBlockLegacy, true, "");
		ConfigBlocksItems.enableConcrete = cfg.getBoolean("enableConcrete", catBlockLegacy, true, "");
		
		ConfigBlocksItems.enableWoodRedstone = cfg.getBoolean("enableWoodRedstone", catBlockLegacy, true, "Enables wood variant buttons and pressure plates");
		ConfigBlocksItems.enableStrippedLogs = cfg.getBoolean("enableStrippedLogs", catBlockLegacy, true, "Enables stripped log blocks");
		ConfigBlocksItems.enableBarkLogs = cfg.getBoolean("enableBarkLogs", catBlockLegacy, true, "Enables log blocks with bark on all sides");
		
		ConfigBlocksItems.enableBarrel = cfg.getBoolean("enableBarrel", catBlockLegacy, true, "");
		ConfigBlocksItems.enableLantern = cfg.getBoolean("enableLantern", catBlockLegacy, true, "");
		ConfigBlocksItems.enableSmoker = cfg.getBoolean("enableSmoker", catBlockLegacy, true, "");
		ConfigBlocksItems.enableBlastFurnace = cfg.getBoolean("enableBlastFurnace", catBlockLegacy, true, "");
		ConfigBlocksItems.enableSigns = cfg.getBoolean("enableSigns", catBlockLegacy, true, "");
		ConfigBlocksItems.enableBlueIce = cfg.getBoolean("enableBlueIce", catBlockLegacy, true, "");
		ConfigBlocksItems.enableSmoothStone = cfg.getBoolean("enableSmoothStone", catBlockLegacy, true, "");
		ConfigBlocksItems.enableSmoothSandstone = cfg.getBoolean("enableSmoothSandStone", catBlockLegacy, true, "");
		ConfigBlocksItems.enableSmoothQuartz = cfg.getBoolean("enableSmoothQuartz", catBlockLegacy, true, "");
		ConfigBlocksItems.enableQuartzBricks = cfg.getBoolean("enableQuartzBricks", catBlockLegacy, true, "");
		ConfigBlocksItems.enableGenericSlabs = cfg.getBoolean("enableGenericSlabs", catBlockLegacy, true, "Slabs for vanilla blocks: stone, mossy stone brick, mossy cobble, cut sandstone");
		ConfigBlocksItems.enableGenericStairs = cfg.getBoolean("enableGenericStairs", catBlockLegacy, true, "Stairs for vanilla blocks: stone, mossy stone brick, mossy cobble");
		ConfigBlocksItems.enableLilyOfTheValley = cfg.getBoolean("enableLilyOfTheValley", catBlockLegacy, true, "");
		ConfigBlocksItems.enableCornflower = cfg.getBoolean("enableCornflower", catBlockLegacy, true, "");
		ConfigBlocksItems.enableWitherRose = cfg.getBoolean("enableWitherRose", catBlockLegacy, true, "");
		ConfigBlocksItems.enableCopper = cfg.getBoolean("enableCopper", catBlockLegacy, true, "Copper ore and copper blocks, variants, and waxed variants. (Slime balls are used if no mod introduces wax)");
		ConfigBlocksItems.enableCopperSubItems = cfg.getBoolean("enableCopperSubItems", catBlockLegacy, true, "Copper sub-blocks and items. Disable copper but keep this on if you want the new copper items and blocks made of it, without the main ingot, ore or copper block itself.");
		ConfigBlocksItems.enableSweetBerryBushes = cfg.getBoolean("enableSweetBerryBushes", catBlockLegacy, true, "");
		ConfigBlocksItems.enableGlazedTerracotta = cfg.getBoolean("enableGlazedTerracotta", catBlockLegacy, true, "");
		
		ConfigBlocksItems.enableBarrier = cfg.getBoolean("enableBarrier", catBlockLegacy, true, "");
		ConfigBlocksItems.enableLavaCauldrons = cfg.getBoolean("enableLavaCauldrons", catBlockLegacy, true, "Allow lava buckets to fill cauldrons");
		ConfigBlocksItems.enableDeepslate = cfg.getBoolean("enableDeepslate", catBlockLegacy, true, "");
		ConfigBlocksItems.enableTuff = cfg.getBoolean("enableTuff", catBlockLegacy, true, "");
		
		//items
		ConfigBlocksItems.enableMutton = cfg.getBoolean("enableMutton", catItemsLegacy, true, "");
		ConfigBlocksItems.enableBeetroot = cfg.getBoolean("enableBeetroot", catItemsLegacy, true, "");
		ConfigBlocksItems.enableElytra = cfg.getBoolean("enableElytra", catItemsLegacy, true, "");
		ConfigBlocksItems.enableIronNugget = cfg.getBoolean("enableIronNugget", catItemsLegacy, true, "");
		ConfigBlocksItems.enableTippedArrows = cfg.getBoolean("enableTippedArrows", catItemsLegacy, true, "");
		ConfigBlocksItems.enableLingeringPotions = cfg.getBoolean("enableLingeringPotions", catItemsLegacy, true, "");
		ConfigBlocksItems.enableRawOres = cfg.getBoolean("enableRawOres", catItemsLegacy, true, "If true, vanilla and Et Futurum copper ores will drop raw ore items.");
		
		ConfigBlocksItems.enableTotemUndying = cfg.getBoolean("enableTotemUndying", catItemsLegacy, true, "");
		ConfigBlocksItems.enableSuspiciousStew = cfg.getBoolean("enableSuspiciousStew", catItemsLegacy, true, "");
		ConfigBlocksItems.enableNewDyes = cfg.getBoolean("enableNewDyes", catItemsLegacy, true, "");
		ConfigEntities.enableNewBoats = cfg.getBoolean("enableNewBoats", catItemsLegacy, true, "");
		
		//equipment
		ConfigBlocksItems.netheriteToolDurability = cfg.getInt("netheriteToolDurability", catEquipmentLegacy, 2031, 1, Integer.MAX_VALUE, "");
		ConfigBlocksItems.netheriteEnchantability = cfg.getInt("netheriteEnchantability", catEquipmentLegacy, 15, 1, Integer.MAX_VALUE, "");
		ConfigBlocksItems.netheriteHarvestLevel = cfg.getInt("netheriteHarvestLevel", catEquipmentLegacy, 4, 0, Integer.MAX_VALUE, "Netherite harvest level, Diamond is 3");
		ConfigBlocksItems.netheriteSpeed = cfg.getFloat("netheriteSpeed", catEquipmentLegacy, 9.0f, 0.1f, Float.MAX_VALUE, "Netherite mining speed, Diamond is 8.0");
		ConfigBlocksItems.netheriteDamageBase  = cfg.getFloat("netheriteDamageBase", catEquipmentLegacy, 4.0f, 0.0f, Float.MAX_VALUE, "Neterite base damage, Diamond is 3.0");
		ConfigBlocksItems.netheriteArmourDurabilityFactor = cfg.getInt("netheriteArmourDurabilityFactor", catEquipmentLegacy, 37, 1, Integer.MAX_VALUE, "");
		ConfigBlocksItems.netheritePickaxeDurability = cfg.getInt("netheritePickaxeDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Pickaxe Durability, -1 to disable");
		ConfigBlocksItems.netheriteSwordDurability = cfg.getInt("netheriteSwordDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Sword Durability, -1 to disable");
		ConfigBlocksItems.netheriteHoeDurability = cfg.getInt("netheriteHoeDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Hoe Durability, -1 to disable");
		ConfigBlocksItems.netheriteAxeDurability = cfg.getInt("netheriteAxeDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Axe Durability, -1 to disable");
		ConfigBlocksItems.netheriteSpadeDurability = cfg.getInt("netheriteSpadeDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Shovel Durability, -1 to disable");
		ConfigBlocksItems.netheriteHelmetDurability = cfg.getInt("netheriteHelmetDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Helmet Durability, -1 to disable");
		ConfigBlocksItems.netheriteChestplateDurability = cfg.getInt("netheriteChestplateDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Chestplate Durability, -1 to disable");
		ConfigBlocksItems.netheriteLeggingsDurability = cfg.getInt("netheriteLeggingsDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Leggings Durability, -1 to disable");
		ConfigBlocksItems.netheriteBootsDurability = cfg.getInt("netheriteBootsDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Boots Durability, -1 to disable");
		ConfigWorld.enableNetheriteFlammable = cfg.getBoolean("enableNetheriteFlammable", catEquipmentLegacy, false, "Set to true to disable the fireproof item entity Netherite/ancient debris etc uses");
		
		//enchants
		ConfigEnchants.enableFrostWalker = cfg.getBoolean("frostWalker", catEnchantsLegacy, true, "");
		ConfigEnchants.frostWalkerID = cfg.getInt("frostWalkerID", catEnchantsLegacy, 36, 0, 1023, "");
		
		ConfigEnchants.enableMending = cfg.getBoolean("mending", catEnchantsLegacy, true, "");
		ConfigEnchants.mendingID = cfg.getInt("mendingID", catEnchantsLegacy, 37, 0, 1023, "");
		
		//mobs
		ConfigBlocksItems.enableRabbit = cfg.getBoolean("enableRabbits", catEntityLegacy, true, "");
		ConfigBlocksItems.enableArmourStand = cfg.getBoolean("enableArmorStand", catEntityLegacy, true, "");
		ConfigEntities.enableEndermite = cfg.getBoolean("enableEndermite", catEntityLegacy, true, "");
		ConfigEntities.enableVillagerZombies = cfg.getBoolean("enableZombieVillager", catEntityLegacy, true, "");
		
		ConfigEntities.enableHusk = cfg.getBoolean("enableHusks", catEntityLegacy, true, "Desert zombie variant");
		ConfigEntities.enableStray = cfg.getBoolean("enableStrays", catEntityLegacy, true, "Tundra skeleton variant");
		ConfigEntities.enableBrownMooshroom = cfg.getBoolean("enableBrownMooshroom", catEntityLegacy, true, "Brown mooshroom variant");
		ConfigEntities.enableShulker = cfg.getBoolean("enableShulker", catEntityLegacy, true, "Shell-lurking mobs from the End.");
		
		//function
		ConfigFunctions.enableSilkTouchingMushrooms = cfg.getBoolean("enableSilkMushroom", catFunctionLegacy, true, "Mushroom blocks can be silk-touched");
		ConfigFunctions.enableRecipeForPrismarine = cfg.getBoolean("enablePrismarineRecipes", catFunctionLegacy, true, "");
		ConfigFunctions.enableSticksFromDeadBushes = cfg.getBoolean("enableBushSticks", catFunctionLegacy, true, "Dead Bushes drop sticks");
		ConfigFunctions.enableSkullDrop = cfg.getBoolean("enableSkullDrop", catFunctionLegacy, true, "Skulls drop from charged creeper kills");
		ConfigWorld.enableBurnableBlocks = cfg.getBoolean("enableBurnables", catFunctionLegacy, true, "Fences, gates and dead bushes burn");
		ConfigFunctions.enableUpdatedFoodValues = cfg.getBoolean("enableUpdatedFood", catFunctionLegacy, true, "Use updated food values");
		ConfigWorld.enableUpdatedHarvestLevels = cfg.getBoolean("enableUpdatedHarvestLevels", catFunctionLegacy, true, "Packed Ice, ladders and melons have preferred tools");
		ConfigFunctions.enableShearableGolems = cfg.getBoolean("enableShearableSnowGolems", catFunctionLegacy, true, "");
		ConfigFunctions.enableShearableCobwebs = cfg.getBoolean("enableShearableCobwebs", catFunctionLegacy, true, "");
		ConfigWorld.enableFloatingTrapDoors = cfg.getBoolean("enableFloatingTrapDoors", catFunctionLegacy, true, "");
		ConfigFunctions.enableStoneBrickRecipes = cfg.getBoolean("enableStoneBrickRecipes", catFunctionLegacy, true, "Makes mossy, cracked and chiseled stone brick craftable");
		ConfigEntities.enableBabyGrowthBoost = cfg.getBoolean("enableBabyGrowthBoost", catFunctionLegacy, true, "");
		ConfigEntities.enableVillagerTurnsIntoWitch = cfg.getBoolean("enableVillagerTurnsIntoWitch", catFunctionLegacy, true, "Villagers turn into Witches when struck by lightning");
		
		ConfigWorld.enableDragonRespawn = cfg.getBoolean("enableDragonRespawn", catFunctionLegacy, true, "");
		ConfigWorld.enableNetherEndermen = cfg.getBoolean("enableNetherEndermen", catFunctionLegacy, true, "Allow endermen to rarely spawn in the Nether");
		
		ConfigFunctions.enableAutoAddSmoker = cfg.getBoolean("enableAutoAddSmoker", catFunctionLegacy, true, "Auto-adds smeltable foods to the smoker, turn off for only vanilla food");
		ConfigFunctions.enableAutoAddBlastFurnace = cfg.getBoolean("enableAutoAddBlastFurnace", catFunctionLegacy, true, "Auto-adds ores to the blast furnace, detected if the input has the \"ore\" oreDictionary prefix and is smeltable. Turn off for only vanilla ores");
		ConfigWorld.totemHealPercent = cfg.getInt("totemHealPercent", catFunctionLegacy, 5, 5, 100, "Percentage of max health for totem to set you at if you die with it. (5% is 0.05, 20 * 0.05 = cfg.1, 1 health is one half-heart)");
		ConfigWorld.enableHoeMining = cfg.getBoolean("enableHoeMining", catFunctionLegacy, true, "Allows blocks like hay bales, leaves etc to mine faster with hoes");
		ConfigFunctions.enableRecipeForTotem = cfg.getBoolean("enableRecipeForTotem", catFunctionLegacy, false, "Recipe for totems since there's no other way to get them currently.");
		ConfigWorld.enableHayBaleFalls = cfg.getBoolean("enableHayBaleFalls", catFunctionLegacy, true, "If true, fall damage on a hay bale will be reduced");
		ConfigWorld.hayBaleReducePercent = cfg.getInt("hayBaleReducePercent", catFunctionLegacy, 20, 0, 99, "If enableHayBaleFalls is true, what percent should we keep for the fall damage?");
//        enableDyeReplacement = cfg.getBoolean("enableDyeReplacement", catFunction, true, "Removes lapis, bone meal, ink sac and cocoa bean's ore dictionary entries as dyes, making the Et Futurum dyes the dyes instead. Disable if this causes weirdisms with modded recipes. (If false both items can be used)");
		ConfigWorld.enableExtraCopper = cfg.getBoolean("enableExtraCopper", catFunctionLegacy, true, "[DEPRECATED, WILL BE REMOVED IN A FUTURE RELEASE, USE EXTRA DROPS LIST] If true, copper will drop 2-3 and fortune will yield more than normal.");
		ConfigBlocksItems.registerRawItemAsOre = cfg.getBoolean("registerRawItemAsOre", catFunctionLegacy, true, "Registers raw ores as \"ore____\" in the OreDictionary. Configurable in case it causes crafting issues.");
		ConfigWorld.replaceOldBoats = cfg.getBoolean("replaceOldBoats", catFunctionLegacy, true, "If true, old boats will be replaced with the new oak boat and the item sprite will also be changned. False means the new and old boat and item for it exists separately, and the new boats will use a wooden shovel in their crafting recipe. If this is enabled, a boat that has an entity in it will not be replaced until the entity gets out. THIS WILL NOT WORK PROPERLY WITH BETTER BOATS INSTALLED");
		ConfigEntities.boatMaxLandSpeed = cfg.getFloat("boatMaxLandSpeed", catFunctionLegacy, 0.986F, 0.1F, 1, "The maximum speed a boat can travel by while on land. This option exists because boats are very very fast when travelling on slippery blocks. Land speed = cfg.0.6, Regular/Packed Ice Speed = cfg.0.98, Packed Ice Speed = cfg.0.986. Anything smaller than 0.6 is really, REALLY slow on land. Any value above 1 is exponential speed growth, and is discouraged. (Quicksoil from Aether Legacy is 1.1) The speed values are just block slipperiness values, and are averaged by the slippery blocks around the bottom of the boat.");
		ConfigEntities.newBoatPassengerSeat = cfg.getBoolean("newBoatPassengerSeat", catFunctionLegacy, true, "If disabled, only one person can sit in the passenger seat at a time.");
		ConfigWorld.fullGrassPath = cfg.getBoolean("fullGrassPath", catFunctionLegacy, false, "Set to true if you're having issues with stepping over grass paths. Temporary option until fixes are implemented to 1.7's stepping system.");
		ConfigFunctions.shulkerBansString = cfg.getStringList("shulkerBans", catFunctionLegacy, ConfigFunctions.shulkerDefaultBans, "Things (namespaced:id) that should not go inside a Shulker Box. Used to ensure recursive storage, boon banning and data overloads with certain items can be stopped. A default list is provided, but it might not cover everything so be sure to check with the mods you have. Be sure to check the default list for this frequently, it will be updated frequently.");
		
		//replacement
		ConfigWorld.enableTileReplacement = cfg.getBoolean("enableTileReplacement", catReplacementLegacy, true, "Replace old Brewing Stands/Enchanting Tables/Daylight Sensors/Beacons with new one on the fly. (Note, as of 2.1.0 this option has been reworked to have better performance. If you disabled it due to lag, please consider trying it again!)"); //Requires enableNewTileEntities. If you want to switch your tile entities from the \"new\" ones to the vanilla blocks, disable this, load the chunks with your tile entities and then disable enableNewTileEntities.
//        enableNewTileEntities = cfg.getBoolean("enableNewTileEntities", catReplacement, true, "(NOT IMPLEMENTED, THIS IS JUST HERE IN ANTICIPATION FOR UPCOMING CHANGES, this currently just disables the \"new\" tile entities without doing anything to the vanilla blocks!) If disabled, the functionality for the anvil, beacon, daylight sensors and enchant tables will append vanilla blocks instead of being swapped out with modded versions.");
		
		ConfigBlocksItems.enableEnchantingTable = cfg.getBoolean("enableEnchantingTable", catReplacementLegacy, true, "");
		ConfigBlocksItems.enableAnvil = cfg.getBoolean("enableAnvil", catReplacementLegacy, true, "");
		ConfigBlocksItems.enableBrewingStands = cfg.getBoolean("enableBrewingStand", catReplacementLegacy, true, "");
		ConfigBlocksItems.enableColourfulBeacons = cfg.getBoolean("enableBeacon", catReplacementLegacy, true, "Beacon beam can be colored using stained glass");
		
		//client
		ConfigWorld.enableDmgIndicator = cfg.getBoolean("enableDmgIndicator", catClientLegacy, true, "Heart Damage Indicator");
		ConfigFunctions.enableTransparentAmour = cfg.getBoolean("enableTransparentAmour", catClientLegacy, true, "Allow non-opaque armour");
		ConfigFunctions.enableBowRendering = cfg.getBoolean("enableBowRendering", catClientLegacy, true, "Bows render pulling animation on inventory");
		ConfigFunctions.enableFancySkulls = cfg.getBoolean("enableFancySkulls", catClientLegacy, true, "Skulls render 3D in inventory");
		ConfigFunctions.enablePlayerSkinOverlay = cfg.getBoolean("enablePlayerSkinOverlay", catClientLegacy, true, "Allows use of 1.8 skin format, and Alex skins. Also includes some fixes from SkinPort. (Per SkinPort author's permission) Disable if skin is displaying oddly. Not compatible with OptiFine, use FastCraft instead.");
		ConfigWorld.enableNewBlocksSounds = cfg.getBoolean("enableNewBlocksSounds", catClientLegacy, true, "New Blocks sounds, such as the new place/break sounds added alongside new blocks, or ones added to existing blocks");
		ConfigWorld.enableNewMiscSounds = cfg.getBoolean("enableNewMiscSounds", catClientLegacy, true, "New sounds like furnace crackling, chests etc.");
		ConfigWorld.enableNewAmbientSounds = cfg.getBoolean("enableNewAmbientSounds", catClientLegacy, true, "New ambient sounds like rain, cave sounds");
		ConfigWorld.enableNetherAmbience = cfg.getBoolean("enableNetherAmbience", catClientLegacy, true, "");
		ConfigFunctions.enableExtraF3HTooltips = cfg.getBoolean("enableExtraF3HTooltips", catClientLegacy, true, "Enables NBT tag count and item namespace label on F3 + H debug item labels");
		ConfigFunctions.shulkerBoxTooltipLines = cfg.getInt("shulkerBoxTooltipLines", catClientLegacy, 5, 0, Byte.MAX_VALUE, "The maximum amount of items a Shulker box can display on its tooltip. When the box has more stacks inside than this number, the rest of the stacks are displayed as \"And x more...\". Set to 0 to disable Shulker Box tooltips.");
	 
		//world
		ConfigWorld.enableAirDebris = cfg.getBoolean("enableAirDebris", catWorldLegacy, false, "Can ancient debris generate next to air?");
		ConfigWorld.maxStonesPerCluster = cfg.getInt("maxStonesPerCluster", catWorldLegacy, 33, 0, 64, "Max vein size for Granite/Andesite/Diorite blocks in a cluster");
		ConfigWorld.smallDebrisMax = cfg.getInt("smallDebrisMax", catWorldLegacy, 2, 0, 64, "The max vein size for the first, typically smaller debris veins which generate from Y 8 to 119");
		ConfigWorld.debrisMax = cfg.getInt("debrisMax", catWorldLegacy, 3, 0, 64, "The max vein size for the second, typically bigger debris veins, which generate from Y 8 to 22");
		ConfigWorld.maxNetherGoldPerCluster = cfg.getInt("maxNetherGoldPerCluster", catWorldLegacy, 10, 0, 64, "Max vein size for nether gold ore blocks in a cluster");
		ConfigWorld.maxMagmaPerCluster = cfg.getInt("maxMagmaPerCluster", catWorldLegacy, 33, 0, 64, "Max vein size for magma blocks in a cluster");
		ConfigWorld.maxCopperPerCluster = cfg.getInt("copperClusterSize", catWorldLegacy, 14, 0, 64, "Max vein size for copper ore blocks in a cluster");
		ConfigWorld.maxDeepslatePerCluster = cfg.getInt("deepslateClusterSize", catWorldLegacy, 64, 0, 128, "If deepslateGenerationMode is set to 1, this value is used to determine how big the clusters are. Otherwise this value is unused.");
		ConfigWorld.deepslateMaxY = cfg.getInt("deepslateMaxY", catWorldLegacy, 22, 0, 256, "How high up deepslate and tuff goes. If deepslateGenerationMode is 0, all stone up to this y level (with a scattering effect a few blocks before then) are replaced with deepslate. If it's 1, the patches can generate to that Y level.");
		ConfigWorld.deepslateReplacesStones = cfg.getBoolean("deepslateReplacesStones", catWorldLegacy, true, "Whether or not Deepslate will overwrite granite, diorite, andesite (Only works when deepslate generation mode is set to 0)");
		ConfigWorld.deepslateReplacesDirt = cfg.getBoolean("deepslateReplacesDirt", catWorldLegacy, true, "Whether or not Deepslate will overwrite dirt (Only works when deepslate generation mode is set to 0)");
		ConfigWorld.deepslateGenerationMode = cfg.getInt("deepslateGenerationMode", catWorldLegacy, 0, -1, 1, "If 0, deepslate replaces all stone below the specified value, with a shattering effect near the top similar to bedrock. If 1, it generates in clusters using the deepslate cluster settings. -1 disables Et Futurum deepslate generation entirely.");
		ConfigBlocksItems.enableDeepslateOres = cfg.getBoolean("enableDeepslateOres", catWorldLegacy, true, "Enable deepslate ores for copper ore and vanilla ores when deepslate generates over them.");
		ConfigFunctions.enableOceanMonuments = cfg.getBoolean("enableOceanMonuments", catWorldLegacy, true, "Note: Ocean monuments currently do not have guardians");
		ConfigWorld.enableFossils = cfg.getBoolean("enableFossils", catWorldLegacy, true, "Note: Fossils currently do not rotate");
		ConfigWorld.fossilBlockID  = cfg.getInt("fossilBoneBlock", catWorldLegacy, 0, 0, 2, "0 = cfg.Et Futurum bone block, 1 = cfg.Netherlicious bone block, 2 = cfg.UpToDateMod bone block. If mod is not installed Et Futurum bone block will be used instead");
		Property prop = cfg.get(catWorldLegacy, "fossilDimensionBlacklist", new int[] {-1, 1});
		prop.comment = "The dimensions the fossil structures should not spawn in.";
		ConfigWorld.fossilDimensionBlacklist = prop.getIntList();
		ConfigWorld.maxTuffPerCluster = cfg.getInt("tuffClusterSize", catWorldLegacy, 48, 0, 64, "Max vein size for tuff blocks in a cluster");
//        enableNewNether = cfg.getBoolean("enableNewNether", catWorldLegacy, true, "When false, the new Nether completely stops to generate, regardless of if the new Nether blocks are on. (Will be ignored if Netherlicious is installed)");
		
		if(cfg.hasChanged()) {
            cfg.save();
        }
	}
	
	public static void preInit() {
		Block block = ConfigWorld.fossilBlockID == 1 ? block = GameRegistry.findBlock("netherlicious", "BoneBlock") : GameRegistry.findBlock("uptodate", "bone_block");
		ConfigWorld.fossilBoneBlock = ConfigWorld.fossilBlockID == 0 || block == null ? ModBlocks.bone_block : block;
		
		hasIronChest = Loader.isModLoaded("IronChest");
		hasNetherlicious = Loader.isModLoaded("netherlicious");
		if(hasNetherlicious) // Come back to
			ConfigWorld.enableNewNether = false;
	}
	
	public static void setupShulkerBanlist() {
		ConfigFunctions.shulkerBans = new ArrayList<Item>();
		for(String itemName : ConfigFunctions.shulkerBansString) {
			String[] nameAndID = itemName.split(":");
			if(nameAndID.length == 2) {
				Item item = GameRegistry.findItem(nameAndID[0], nameAndID[1]);
				if(item != null) {
					ConfigFunctions.shulkerBans.add(item);
				}
			} else {
				System.err.println("Shulker ban list item \"" + itemName + "\" is formatted incorrectly!");
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
