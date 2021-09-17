package ganymedes01.etfuturum.configuration.configs;

import java.io.File;

import ganymedes01.etfuturum.configuration.ConfigBase;

public class ConfigBlocksItems extends ConfigBase {
	
	public ConfigBlocksItems(File file) {
		super(file);
		configCats.add(cfg.getCategory(catNaturalBlock));
		configCats.add(cfg.getCategory(catMiscBlock));
		configCats.add(cfg.getCategory(catFuncBlock));
		configCats.add(cfg.getCategory(catItems));
		configCats.add(cfg.getCategory(catEntity));
		
		cfg.getCategory(catNaturalBlock).setComment("Blocks that can generate naturally in your world. Check world.cfg for more generation values.");
		cfg.getCategory(catMiscBlock).setComment("Blocks that can generate naturally in your world. Check world.cfg for more generation values.");
		cfg.getCategory(catFuncBlock).setComment("Blocks that can generate naturally in your world. Check world.cfg for more generation values.");
		cfg.getCategory(catItems).setComment("Blocks that can generate naturally in your world. Check world.cfg for more generation values.");
		cfg.getCategory(catEntity).setComment("Blocks that can generate naturally in your world. Check world.cfg for more generation values.");
	}
	
	public static boolean enableBanners;
	public static boolean enableBarkLogs;
	public static boolean enableStones;
	public static boolean enableIronTrapdoor;
	public static boolean enableMutton;
	public static boolean enableSponge;
	public static boolean enablePrismarine;
	public static boolean enableDoors;
	public static boolean enableTrapdoors;
	public static boolean enableInvertedDaylightSensor;
	public static boolean enableCoarseDirt;
	public static boolean enableRedSandstone;
	public static boolean enableFences;
	public static boolean enableSlimeBlock;
	public static boolean enableRabbit;
	public static boolean enableOldGravel;
	public static boolean enableBeetroot;
	public static boolean enableChorusFruit;
	public static boolean enableGrassPath;
	public static boolean enableTippedArrows;
	public static boolean enableLingeringPotions;
	public static boolean enableCryingObsidian;
	public static boolean enableElytra;
	public static boolean enableBrewingStands;
	public static boolean enableRoses;
	public static boolean enableColourfulBeacons;
	public static boolean enableBarrel;
	public static boolean enableLantern;
	public static boolean enableSmoker;
	public static boolean enableBlastFurnace;
	public static boolean enableBarrier;
	public static boolean enableNetherGold;
	public static boolean enableSigns;
	public static boolean enableSmoothStone;
	public static boolean enableSmoothSandstone;
	public static boolean enableSmoothQuartz;
	public static boolean enableQuartzBricks;
	public static boolean enableGenericSlabs;
	public static boolean enableGenericStairs;
	public static boolean enableLilyOfTheValley;
	public static boolean enableCornflower;
	public static boolean enableWitherRose;
	public static boolean enableSweetBerryBushes;
	public static boolean enableLavaCauldrons;
	public static boolean enableNewNetherBricks;
	public static boolean enableNetherwartBlock;
	public static boolean enableMagmaBlock;
	public static boolean enableNetherite;
	public static boolean enableBoneBlock;
	public static boolean enableConcrete;
	public static boolean enableTotemUndying;
	public static boolean enableRawOres;
	public static boolean enableNewDyes;
	public static boolean enableWoodRedstone;
	public static boolean enableStrippedLogs;
	public static boolean enableBlueIce;
	public static boolean enableCopper;
	public static boolean enableCopperSubItems;
	public static boolean enableDeepslate;
	public static boolean enableDeepslateOres;
	public static boolean enableTuff;
	public static boolean enableNewTileEntities;
	public static boolean enableSuspiciousStew;
	public static boolean enableGlazedTerracotta;
	public static boolean enableIronNugget;
	//Nether Update temp disabled
	public static boolean enableCrimsonBlocks = false;
	public static boolean enableWarpedBlocks = false;
	public static boolean enableEnchantingTable;
	public static boolean enableAnvil;
	
	public static boolean enableShulkerBoxes = true;
	public static boolean enableDyedShulkerBoxes = true;
	public static boolean enableIronShulkerBoxes = false;

	public static final String catNaturalBlock = "Natural Blocks";
	public static final String catMiscBlock = "Misc Blocks";
	public static final String catFuncBlock = "Function Blocks";
	public static final String catItems = "Items";
	public static final String catEntity = "Entity Items";
}
