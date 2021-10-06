package ganymedes01.etfuturum.configuration.configs;

import java.io.File;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraft.block.Block;

public class ConfigWorld extends ConfigBase {
	
	public static final String[] usedCategories = {};
	public static boolean enableNewNether;
	public static boolean enableNetheriteFlammable;
	public static boolean enableBurnableBlocks;
	public static boolean enableDmgIndicator;
	public static boolean enableUpdatedHarvestLevels;
	public static boolean enableDragonRespawn;
	public static boolean enableFloatingTrapDoors;
	public static int totemHealPercent;
	public static boolean enableHoeMining;
	public static boolean enableAirDebris;
	public static int debrisMax = 3;
	public static int maxNetherGoldPerCluster;
	public static int maxMagmaPerCluster;
	public static boolean enableHayBaleFalls;
	public static int hayBaleReducePercent;
	public static int maxCopperPerCluster;
	public static String[] extraDropRawOres = new String[] {"oreCopper", "oreTin"};
	public static boolean replaceOldBoats;
	public static boolean fullGrassPath;
	public static int fossilBlockID;
	public static boolean enableNetherEndermen;
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
	public static boolean enableNewBlocksSounds;
	public static boolean enableNewMiscSounds;
	public static boolean enableNewAmbientSounds;
	public static boolean enableNetherAmbience;
	@Deprecated
	public static boolean enableExtraCopper;
	public static int smallDebrisMax = 2;


	public ConfigWorld(File file) {
		super(file);
		// TODO Auto-generated constructor stub
	}

	protected void syncConfigs() {
		
	}
}
