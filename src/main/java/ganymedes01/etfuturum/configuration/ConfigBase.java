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
import ganymedes01.etfuturum.enchantment.FrostWalker;
import ganymedes01.etfuturum.enchantment.Mending;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigBase {

	public static ConfigBase INSTANCE = new ConfigBase();
	public Configuration cfg;
	
	public static final boolean hasIronChest = Loader.isModLoaded("IronChest");
	public static final boolean hasNetherlicious = Loader.isModLoaded("netherlicious");
	
	public static boolean enableNewNether;
	public static boolean enableBowRendering;
	public static boolean enableSilkTouchingMushrooms;
	public static boolean enableStray;
	public static boolean enableBrownMooshroom;
	public static boolean enableSkullDrop;
	public static boolean enableNetheriteFlammable;
	

	public static boolean enableArmourStand;
	public static boolean enableRecipeForPrismarine;
	public static boolean enableEndermite;
	public static boolean enableSticksFromDeadBushes;
	public static boolean enableBurnableBlocks;
	public static boolean enableFancySkulls;
	public static boolean enableDmgIndicator;
	public static boolean enableTransparentAmour;
	public static boolean enableUpdatedFoodValues;
	public static boolean enableUpdatedHarvestLevels;
	public static boolean enableVillagerZombies;
	public static boolean enableStoneBrickRecipes;
	public static boolean enableBabyGrowthBoost;
	public static boolean enableVillagerTurnsIntoWitch;
	public static boolean enableFrostWalker;
	public static boolean enableMending;
	public static boolean enableDragonRespawn;
	public static boolean enablePlayerSkinOverlay;
	public static boolean enableShearableGolems;
	public static boolean enableShearableCobwebs;
	public static boolean enableFloatingTrapDoors;
	public static boolean enableAutoAddSmoker;
	public static boolean enableAutoAddBlastFurnace;
	public static boolean enableMeltGear = false;
	public static int totemHealPercent;
	public static boolean enableHoeMining;
	public static boolean enableAirDebris;
	public static int smallDebrisMax = 2;
	public static int debrisMax = 3;
	public static int maxNetherGoldPerCluster;
	public static int maxMagmaPerCluster;
	public static boolean enableHayBaleFalls;
	public static int hayBaleReducePercent;
	public static int maxCopperPerCluster;
	public static boolean enableExtraCopper;
	public static boolean registerRawItemAsOre;
	public static boolean replaceOldBoats;
	public static float boatMaxLandSpeed;
	public static boolean fullGrassPath;
	public static boolean newBoatPassengerSeat;

	public static boolean enableHusk;
	public static boolean enableNetherEndermen;
	public static boolean enableRecipeForTotem;
	public static boolean enableOceanMonuments;
	public static int deepslateGenerationMode;
	public static int maxDeepslatePerCluster;
	public static int deepslateMaxY;
	public static boolean deepslateReplacesStones;
	public static boolean deepslateReplacesDirt;
	public static boolean enableFossils;
	public static Block fossilBoneBlock;
	public static int maxTuffPerCluster;
	public static int[] fossilDimensionBlacklist;

	public static boolean enableTileReplacement;
	public static int maxStonesPerCluster;

	public static int netheriteToolDurability;
	public static int netheriteEnchantability;
	public static int netheriteHarvestLevel;
	public static float netheriteSpeed;
	public static float netheriteDamageBase;
	public static int netheriteArmourDurabilityFactor;
	public static int netheritePickaxeDurability;
	public static int netheriteSwordDurability;
	public static int netheriteHoeDurability;
	public static int netheriteAxeDurability;
	public static int netheriteSpadeDurability;
	public static int netheriteHelmetDurability;
	public static int netheriteChestplateDurability;
	public static int netheriteLeggingsDurability;
	public static int netheriteBootsDurability;
	public static boolean enableNewBoats;
	
	public static boolean enableNewBlocksSounds;
	public static boolean enableNewMiscSounds;
	public static boolean enableNewAmbientSounds;
	public static boolean enableNetherAmbience;
	public static boolean enableExtraF3HTooltips;
	
	public static final String catClientLegacy = "client";
	public static final String catBlockLegacy = "blocks";
	public static final String catItemsLegacy = "items";
	public static final String catEquipmentLegacy = "equipment";
	public static final String catEnchantsLegacy = "enchants";
	public static final String catEntityLegacy = "entity";
	public static final String catReplacementLegacy = "replacement";
	public static final String catFunctionLegacy = "function";
	public static final String catWorldLegacy = "world";
	
	public static final List<ConfigCategory> configCats = new ArrayList<ConfigCategory>();
	
	public void init(File file) {
		cfg = new Configuration(file);

		syncConfigs();
	}

	private void syncConfigs() {
		
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
		enableNewBoats = cfg.getBoolean("enableNewBoats", catItemsLegacy, true, "");
		
		//equipment
		netheriteToolDurability = cfg.getInt("netheriteToolDurability", catEquipmentLegacy, 2031, 1, Integer.MAX_VALUE, "");
		netheriteEnchantability = cfg.getInt("netheriteEnchantability", catEquipmentLegacy, 15, 1, Integer.MAX_VALUE, "");
		netheriteHarvestLevel = cfg.getInt("netheriteHarvestLevel", catEquipmentLegacy, 4, 0, Integer.MAX_VALUE, "Netherite harvest level, Diamond is 3");
		netheriteSpeed = cfg.getFloat("netheriteSpeed", catEquipmentLegacy, 9.0f, 0.1f, Float.MAX_VALUE, "Netherite mining speed, Diamond is 8.0");
		netheriteDamageBase  = cfg.getFloat("netheriteDamageBase", catEquipmentLegacy, 4.0f, 0.0f, Float.MAX_VALUE, "Neterite base damage, Diamond is 3.0");
		netheriteArmourDurabilityFactor = cfg.getInt("netheriteArmourDurabilityFactor", catEquipmentLegacy, 37, 1, Integer.MAX_VALUE, "");
		netheritePickaxeDurability = cfg.getInt("netheritePickaxeDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Pickaxe Durability, -1 to disable");
		netheriteSwordDurability = cfg.getInt("netheriteSwordDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Sword Durability, -1 to disable");
		netheriteHoeDurability = cfg.getInt("netheriteHoeDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Hoe Durability, -1 to disable");
		netheriteAxeDurability = cfg.getInt("netheriteAxeDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Axe Durability, -1 to disable");
		netheriteSpadeDurability = cfg.getInt("netheriteSpadeDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Shovel Durability, -1 to disable");
		netheriteHelmetDurability = cfg.getInt("netheriteHelmetDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Helmet Durability, -1 to disable");
		netheriteChestplateDurability = cfg.getInt("netheriteChestplateDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Chestplate Durability, -1 to disable");
		netheriteLeggingsDurability = cfg.getInt("netheriteLeggingsDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Leggings Durability, -1 to disable");
		netheriteBootsDurability = cfg.getInt("netheriteBootsDurability", catEquipmentLegacy, -1, -1, Integer.MAX_VALUE, "Override Netherite Boots Durability, -1 to disable");
		enableNetheriteFlammable = cfg.getBoolean("enableNetheriteFlammable", catEquipmentLegacy, false, "Set to true to disable the fireproof item entity Netherite/ancient debris etc uses");
		
		//enchants
		enableFrostWalker = cfg.getBoolean("frostWalker", catEnchantsLegacy, true, "");
		FrostWalker.ID = cfg.getInt("frostWalkerID", catEnchantsLegacy, 36, 0, 1023, "");
		
		enableMending = cfg.getBoolean("mending", catEnchantsLegacy, true, "");
		Mending.ID = cfg.getInt("mendingID", catEnchantsLegacy, 37, 0, 1023, "");
		
		//mobs
		ConfigBlocksItems.enableRabbit = cfg.getBoolean("enableRabbits", catEntityLegacy, true, "");
		enableArmourStand = cfg.getBoolean("enableArmorStand", catEntityLegacy, true, "");
		enableEndermite = cfg.getBoolean("enableEndermite", catEntityLegacy, true, "");
		enableVillagerZombies = cfg.getBoolean("enableZombieVillager", catEntityLegacy, true, "");
		
		enableHusk = cfg.getBoolean("enableHusks", catEntityLegacy, true, "Desert zombie variant");
		enableStray = cfg.getBoolean("enableStrays", catEntityLegacy, true, "Tundra skeleton variant");
		enableBrownMooshroom = cfg.getBoolean("enableBrownMooshroom", catEntityLegacy, true, "Brown mooshroom variant");
		
		//function
		enableSilkTouchingMushrooms = cfg.getBoolean("enableSilkMushroom", catFunctionLegacy, true, "Mushroom blocks can be silk-touched");
		enableRecipeForPrismarine = cfg.getBoolean("enablePrismarineRecipes", catFunctionLegacy, true, "");
		enableSticksFromDeadBushes = cfg.getBoolean("enableBushSticks", catFunctionLegacy, true, "Dead Bushes drop sticks");
		enableSkullDrop = cfg.getBoolean("enableSkullDrop", catFunctionLegacy, true, "Skulls drop from charged creeper kills");
		enableBurnableBlocks = cfg.getBoolean("enableBurnables", catFunctionLegacy, true, "Fences, gates and dead bushes burn");
		enableUpdatedFoodValues = cfg.getBoolean("enableUpdatedFood", catFunctionLegacy, true, "Use updated food values");
		enableUpdatedHarvestLevels = cfg.getBoolean("enableUpdatedHarvestLevels", catFunctionLegacy, true, "Packed Ice, ladders and melons have preferred tools");
		enableShearableGolems = cfg.getBoolean("enableShearableSnowGolems", catFunctionLegacy, true, "");
		enableShearableCobwebs = cfg.getBoolean("enableShearableCobwebs", catFunctionLegacy, true, "");
		enableFloatingTrapDoors = cfg.getBoolean("enableFloatingTrapDoors", catFunctionLegacy, true, "");
		enableStoneBrickRecipes = cfg.getBoolean("enableStoneBrickRecipes", catFunctionLegacy, true, "Makes mossy, cracked and chiseled stone brick craftable");
		enableBabyGrowthBoost = cfg.getBoolean("enableBabyGrowthBoost", catFunctionLegacy, true, "");
		enableVillagerTurnsIntoWitch = cfg.getBoolean("enableVillagerTurnsIntoWitch", catFunctionLegacy, true, "Villagers turn into Witches when struck by lightning");
		
		enableDragonRespawn = cfg.getBoolean("enableDragonRespawn", catFunctionLegacy, true, "");
		enableNetherEndermen = cfg.getBoolean("enableNetherEndermen", catFunctionLegacy, true, "Allow endermen to rarely spawn in the Nether");
		
		enableAutoAddSmoker = cfg.getBoolean("enableAutoAddSmoker", catFunctionLegacy, true, "Auto-adds smeltable foods to the smoker, turn off for only vanilla food");
		enableAutoAddBlastFurnace = cfg.getBoolean("enableAutoAddBlastFurnace", catFunctionLegacy, true, "Auto-adds ores to the blast furnace, detected if the input has the \"ore\" oreDictionary prefix and is smeltable. Turn off for only vanilla ores");
		totemHealPercent = cfg.getInt("totemHealPercent", catFunctionLegacy, 5, 5, 100, "Percentage of max health for totem to set you at if you die with it. (5% is 0.05, 20 * 0.05 = 1, 1 health is one half-heart)");
		enableHoeMining = cfg.getBoolean("enableHoeMining", catFunctionLegacy, true, "Allows blocks like hay bales, leaves etc to mine faster with hoes");
		enableRecipeForTotem = cfg.getBoolean("enableRecipeForTotem", catFunctionLegacy, false, "Recipe for totems since there's no other way to get them currently.");
		enableHayBaleFalls = cfg.getBoolean("enableHayBaleFalls", catFunctionLegacy, true, "If true, fall damage on a hay bale will be reduced");
		hayBaleReducePercent = cfg.getInt("hayBaleReducePercent", catFunctionLegacy, 20, 0, 99, "If enableHayBaleFalls is true, what percent should we keep for the fall damage?");
//        enableDyeReplacement = cfg.getBoolean("enableDyeReplacement", catFunction, true, "Removes lapis, bone meal, ink sac and cocoa bean's ore dictionary entries as dyes, making the Et Futurum dyes the dyes instead. Disable if this causes weirdisms with modded recipes. (If false both items can be used)");
		enableExtraCopper = cfg.getBoolean("enableExtraCopper", catFunctionLegacy, true, "If true, copper will drop 2-3 and fortune will yield more than normal.");
		registerRawItemAsOre = cfg.getBoolean("registerRawItemAsOre", catFunctionLegacy, true, "Registers raw ores as \"ore____\" in the OreDictionary. Configurable in case it causes crafting issues.");
		replaceOldBoats = cfg.getBoolean("replaceOldBoats", catFunctionLegacy, true, "If true, old boats will be replaced with the new oak boat and the item sprite will also be changned. False means the new and old boat and item for it exists separately, and the new boats will use a wooden shovel in their crafting recipe. If this is enabled, a boat that has an entity in it will not be replaced until the entity gets out. THIS WILL NOT WORK PROPERLY WITH BETTER BOATS INSTALLED");
		boatMaxLandSpeed = cfg.getFloat("boatMaxLandSpeed", catFunctionLegacy, 0.986F, 0.1F, Float.POSITIVE_INFINITY, "The maximum speed a boat can travel by while on land. This option exists because boats are very very fast when travelling on slippery blocks. Land speed = 0.6, Regular/Packed Ice Speed = 0.98, Packed Ice Speed = 0.986. Anything smaller than 0.6 is really, REALLY slow on land. Any value above 1 is exponential speed growth, and is discouraged. (Quicksoil from Aether Legacy is 1.1) The speed values are just block slipperiness values, and are averaged by the slippery blocks around the bottom of the boat.");
		newBoatPassengerSeat = cfg.getBoolean("newBoatPassengerSeat", catFunctionLegacy, true, "If disabled, only one person can sit in the passenger seat at a time.");
		fullGrassPath = cfg.getBoolean("fullGrassPath", catFunctionLegacy, false, "Set to true if you're having issues with stepping over grass paths. Temporary option until fixes are implemented to 1.7's stepping system.");
		
		
		//replacement
		enableTileReplacement = cfg.getBoolean("enableTileReplacement", catReplacementLegacy, true, "Replace old Brewing Stands/Enchanting Tables/Daylight Sensors/Beacons with new one on the fly. (Note, as of 2.1.0 this option has been reworked to have better performance. If you disabled it due to lag, please consider trying it again!)"); //Requires enableNewTileEntities. If you want to switch your tile entities from the \"new\" ones to the vanilla blocks, disable this, load the chunks with your tile entities and then disable enableNewTileEntities.
//        enableNewTileEntities = cfg.getBoolean("enableNewTileEntities", catReplacement, true, "(NOT IMPLEMENTED, THIS IS JUST HERE IN ANTICIPATION FOR UPCOMING CHANGES, this currently just disables the \"new\" tile entities without doing anything to the vanilla blocks!) If disabled, the functionality for the anvil, beacon, daylight sensors and enchant tables will append vanilla blocks instead of being swapped out with modded versions.");
		
		ConfigBlocksItems.enableEnchantingTable = cfg.getBoolean("enableEnchantingTable", catReplacementLegacy, true, "");
		ConfigBlocksItems.enableAnvil = cfg.getBoolean("enableAnvil", catReplacementLegacy, true, "");
		ConfigBlocksItems.enableBrewingStands = cfg.getBoolean("enableBrewingStand", catReplacementLegacy, true, "");
		ConfigBlocksItems.enableColourfulBeacons = cfg.getBoolean("enableBeacon", catReplacementLegacy, true, "Beacon beam can be colored using stained glass");
		
		//client
		enableDmgIndicator = cfg.getBoolean("enableDmgIndicator", catClientLegacy, true, "Heart Damage Indicator");
		enableTransparentAmour = cfg.getBoolean("enableTransparentAmour", catClientLegacy, true, "Allow non-opaque armour");
		enableBowRendering = cfg.getBoolean("enableBowRendering", catClientLegacy, true, "Bows render pulling animation on inventory");
		enableFancySkulls = cfg.getBoolean("enableFancySkulls", catClientLegacy, true, "Skulls render 3D in inventory");
		enablePlayerSkinOverlay = cfg.getBoolean("enablePlayerSkinOverlay", catClientLegacy, true, "Allows use of 1.8 skin format, and Alex skins. Also includes some fixes from SkinPort. (Per SkinPort author's permission) Disable if skin is displaying oddly. Not compatible with OptiFine, use FastCraft instead.");
		enableNewBlocksSounds = cfg.getBoolean("enableNewBlocksSounds", catClientLegacy, true, "New Blocks sounds, such as the new place/break sounds added alongside new blocks, or ones added to existing blocks");
		enableNewMiscSounds = cfg.getBoolean("enableNewMiscSounds", catClientLegacy, true, "New sounds like furnace crackling, chests etc.");
		enableNewAmbientSounds = cfg.getBoolean("enableNewAmbientSounds", catClientLegacy, true, "New ambient sounds like rain, cave sounds");
		enableNetherAmbience = cfg.getBoolean("enableNetherAmbience", catClientLegacy, true, "");
		enableExtraF3HTooltips = cfg.getBoolean("enableExtraF3HTooltips", catClientLegacy, true, "Enables NBT tag count and item namespace label on F3 + H debug item labels");
	 
		//world
		enableAirDebris = cfg.getBoolean("enableAirDebris", catWorldLegacy, false, "Can ancient debris generate next to air?");
		maxStonesPerCluster = cfg.getInt("maxStonesPerCluster", catWorldLegacy, 33, 0, 64, "Max vein size for Granite/Andesite/Diorite blocks in a cluster");
		smallDebrisMax = cfg.getInt("smallDebrisMax", catWorldLegacy, 2, 0, 64, "The max vein size for the first, typically smaller debris veins which generate from Y 8 to 119");
		debrisMax = cfg.getInt("debrisMax", catWorldLegacy, 3, 0, 64, "The max vein size for the second, typically bigger debris veins, which generate from Y 8 to 22");
		maxNetherGoldPerCluster = cfg.getInt("maxNetherGoldPerCluster", catWorldLegacy, 10, 0, 64, "Max vein size for nether gold ore blocks in a cluster");
		maxMagmaPerCluster = cfg.getInt("maxMagmaPerCluster", catWorldLegacy, 33, 0, 64, "Max vein size for magma blocks in a cluster");
		maxCopperPerCluster = cfg.getInt("copperClusterSize", catWorldLegacy, 14, 0, 64, "Max vein size for copper ore blocks in a cluster");
		maxDeepslatePerCluster = cfg.getInt("deepslateClusterSize", catWorldLegacy, 64, 0, 128, "If deepslateGenerationMode is set to 1, this value is used to determine how big the clusters are. Otherwise this value is unused.");
		deepslateMaxY = cfg.getInt("deepslateMaxY", catWorldLegacy, 22, 0, 256, "How high up deepslate and tuff goes. If deepslateGenerationMode is 0, all stone up to this y level (with a scattering effect a few blocks before then) are replaced with deepslate. If it's 1, the patches can generate to that Y level.");
		deepslateReplacesStones = cfg.getBoolean("deepslateReplacesStones", catWorldLegacy, true, "Whether or not Deepslate will overwrite granite, diorite, andesite (Only works when deepslate generation mode is set to 0)");
		deepslateReplacesDirt = cfg.getBoolean("deepslateReplacesDirt", catWorldLegacy, true, "Whether or not Deepslate will overwrite dirt (Only works when deepslate generation mode is set to 0)");
		deepslateGenerationMode = cfg.getInt("deepslateGenerationMode", catWorldLegacy, 0, -1, 1, "If 0, deepslate replaces all stone below the specified value. If 1, it generates in clusters using the above cluster settings. -1 disables deepslate generation entirely");
		ConfigBlocksItems.enableDeepslateOres = cfg.getBoolean("enableDeepslateOres", catWorldLegacy, true, "Enable deepslate ores for copper ore and vanilla ores when deepslate generates at it.");
		enableOceanMonuments = cfg.getBoolean("enableOceanMonuments", catWorldLegacy, true, "Note: Ocean monuments currently do not have guardians");
		enableFossils = cfg.getBoolean("enableFossils", catWorldLegacy, true, "Note: Fossils currently do not rotate");
		int m  = cfg.getInt("fossilBoneBlock", catWorldLegacy, 0, 0, 2, "0 = Et Futurum bone block, 1 = Netherlicious bone block, 2 = UpToDateMod bone block. If mod is not installed Et Futurum bone block will be used instead");
		Block block = m == 1 ? block = GameRegistry.findBlock("netherlicious", "BoneBlock") : GameRegistry.findBlock("uptodate", "bone_block");
		fossilBoneBlock = m == 0 || block == null ? ModBlocks.bone_block : block;
		Property prop = cfg.get(catWorldLegacy, "fossilDimensionBlacklist", new int[] {-1, 1});
		prop.comment = "The dimensions the fossil structures should not spawn in.";
		fossilDimensionBlacklist = prop.getIntList();
		maxTuffPerCluster = cfg.getInt("tuffClusterSize", catWorldLegacy, 48, 0, 64, "Max vein size for tuff blocks in a cluster");
//        enableNewNether = cfg.getBoolean("enableNewNether", catWorld, true, "When false, the new Nether completely stops to generate, regardless of if the new Nether blocks are on. (Will be ignored if Netherlicious is installed)");
		
		if(Loader.isModLoaded("netherlicious")) // Come back to
			ConfigBase.enableNewNether = false;
		
		if (cfg.hasChanged()) {
			cfg.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
//		cfg.getConfigFile().deleteOnExit();
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfigs();
	}
}
