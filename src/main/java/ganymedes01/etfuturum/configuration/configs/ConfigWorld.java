package ganymedes01.etfuturum.configuration.configs;

import java.io.File;

import ganymedes01.etfuturum.configuration.ConfigBase;
import net.minecraft.block.Block;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigWorld extends ConfigBase {

	//TODO: CHANGE THIS
	public static boolean enableNewNether;

	public static Block fossilBoneBlock;
	public static int fossilBlockID;
	public static Block amethystOuterBlock;
	public static int amethystOuterID;
	
	public static boolean enableDmgIndicator;
	public static boolean enableAirDebris;
	public static int debrisMax = 3;
	public static int maxNetherGoldPerCluster;
	public static int maxMagmaPerCluster;
	public static int maxCopperPerCluster;
	public static boolean fullGrassPath;
	public static int deepslateGenerationMode;
	public static int maxDeepslatePerCluster;
	public static int deepslateMaxY;
	public static boolean deepslateReplacesStones;
	public static boolean deepslateReplacesDirt;
	public static boolean enableFossils;
	public static int maxTuffPerCluster;
	public static int[] fossilDimensionBlacklist;
	public static int tileReplacementMode;
	public static int maxStonesPerCluster;
	public static boolean enableNewBlocksSounds;
	public static boolean enableNewMiscSounds;
	public static boolean enableNewAmbientSounds;
	public static boolean enableNetherAmbience;
	public static int smallDebrisMax = 2;
	public static boolean enableOceanMonuments;
	public static int[] deepslateLayerDimensionBlacklist;
	public static boolean enableExtraMesaGold;
	public static boolean enableMesaMineshaft;
	public static boolean enableCoarseDirtReplacement;
	public static boolean enableAmethystGeodes;
	public static int buddingAmethystMode;

	public static final String catClient = "client";
	public static final String catGeneration = "generation";
	public static final String catMisc = "miscellaneous";
	
	public static final String PATH = configDir + File.separator + "world.cfg";
	public static final ConfigWorld configInstance = new ConfigWorld(new File(Launch.minecraftHome, PATH));
	
	public ConfigWorld(File file) {
		super(file);
		setCategoryComment(catClient, "Client-side only effects.");
		setCategoryComment(catGeneration, "Generation settings.");
		setCategoryComment(catMisc, "For things that affect the world but don't belong in any other category.");
		
		configCats.add(getCategory(catClient));
		configCats.add(getCategory(catGeneration));
		configCats.add(getCategory(catMisc));
	}

	@Override
	protected void syncConfigOptions() {
		Configuration cfg = configInstance;
		
		enableDmgIndicator = cfg.getBoolean("enableDmgIndicator", catClient, true, "Heart Damage Indicator");
		enableNewBlocksSounds = cfg.getBoolean("enableNewBlocksSounds", catClient, true, "New Blocks sounds, such as the new place/break sounds added alongside new blocks. Additionally, this will attempt to apply the sounds to existing blocks, like custom Netherrack variants may have the step sound if named right.");
		enableNewMiscSounds = cfg.getBoolean("enableNewMiscSounds", catClient, true, "New sounds like furnace crackling, chests etc.");
		enableNewAmbientSounds = cfg.getBoolean("enableNewAmbientSounds", catClient, true, "New ambient sounds like rain, cave sounds");
		enableNetherAmbience = cfg.getBoolean("enableNetherAmbience", catClient, true, "");
		
		enableAirDebris = cfg.getBoolean("enableAirDebris", catGeneration, false, "Can ancient debris generate next to air?");
		maxStonesPerCluster = cfg.getInt("maxStonesPerCluster", catGeneration, 32, 0, 64, "Max vein size for Granite/Andesite/Diorite blocks in a cluster");
		smallDebrisMax = cfg.getInt("smallDebrisMax", catGeneration, 2, 0, 64, "The max vein size for the first, typically smaller debris veins which generate from Y 8 to 119");
		debrisMax = cfg.getInt("debrisMax", catGeneration, 3, 0, 64, "The max vein size for the second, typically bigger debris veins, which generate from Y 8 to 22");
		maxNetherGoldPerCluster = cfg.getInt("maxNetherGoldPerCluster", catGeneration, 10, 0, 64, "Max vein size for nether gold ore blocks in a cluster");
		maxMagmaPerCluster = cfg.getInt("maxMagmaPerCluster", catGeneration, 33, 0, 64, "Max vein size for magma blocks in a cluster");
		maxCopperPerCluster = cfg.getInt("copperClusterSize", catGeneration, 8, 0, 64, "Max vein size for copper ore blocks in a cluster");
		maxDeepslatePerCluster = cfg.getInt("deepslateClusterSize", catGeneration, 64, 0, 128, "If deepslateGenerationMode is set to 1, this value is used to determine how big the clusters are. Otherwise this value is unused.");
		deepslateMaxY = cfg.getInt("deepslateMaxY", catGeneration, 22, 0, 256, "How high up deepslate and tuff goes. If deepslateGenerationMode is 0, all stone up to this y level (with a scattering effect a few blocks before then) are replaced with deepslate. If it's 1, the patches can generate to that Y level.");
		deepslateReplacesStones = cfg.getBoolean("deepslateReplacesStones", catGeneration, true, "Whether or not Deepslate will overwrite granite, diorite, andesite (Only works when deepslate generation mode is set to 0)");
		deepslateReplacesDirt = cfg.getBoolean("deepslateReplacesDirt", catGeneration, true, "Whether or not Deepslate will overwrite dirt (Only works when deepslate generation mode is set to 0)");
		deepslateGenerationMode = cfg.getInt("deepslateGenerationMode", catGeneration, 0, -1, 1, "If 0, deepslate replaces all stone below the specified value, with a shattering effect near the top similar to bedrock. If 1, it generates in clusters using the deepslate cluster settings. -1 disables Et Futurum deepslate generation entirely.");
		enableOceanMonuments = cfg.getBoolean("enableOceanMonuments", catGeneration, true, "Note: Ocean monuments currently do not have guardians");
		enableFossils = cfg.getBoolean("enableFossils", catGeneration, true, "Note: Fossils currently do not rotate");
		fossilBlockID  = cfg.getInt("fossilBoneBlock", catGeneration, 0, 0, 2, "0 = cfg.Et Futurum bone block\n1 = Netherlicious bone block\n2 = UpToDateMod bone block.\nIf the block does not exist, this option is ignored.");
		amethystOuterID  = cfg.getInt("amethystOuterBlock", catGeneration, 0, 0, 2, "0 = cfg.Et Futurum smooth basalt block\n1 = Et Futurum tuff block\n2 = Netherlicious smooth basalt block\nSince there's no other way to get Et Futurum's smooth basalt, using an option other than 0, if they exist, will disable Et Futurum smooth basalt. If the selected block does not exist (disabled or mod not installed), this option does nothing.");
		Property fossilBlacklistProp = cfg.get(catGeneration, "fossilDimensionBlacklist", new int[] {-1, 1});
		fossilBlacklistProp.comment = "The dimensions the fossil structures should not spawn in.";
		fossilDimensionBlacklist = fossilBlacklistProp.getIntList();
		Property deepslateBlacklistProp = cfg.get(catGeneration, "deepslateLayerDimensionBlacklist", new int[] {-1, 1});
		deepslateBlacklistProp.comment = "The dimensions the deepslate layer (deepslate generation mode 0) should not spawn in. Does nothing if other deepslate generation modes are used.";
		deepslateLayerDimensionBlacklist = deepslateBlacklistProp.getIntList();
		maxTuffPerCluster = cfg.getInt("tuffClusterSize", catGeneration, 32, 0, 64, "Max vein size for tuff blocks in a cluster");
		enableExtraMesaGold = cfg.getBoolean("enableExtraMesaGold", catGeneration, true, "Generate 20 more veins of gold ore from Y 32 to Y 80 in any Mesa biome.");
		enableMesaMineshaft = cfg.getBoolean("enableMesaMineshaft", catGeneration, true, "Generates extra mineshafts in mesa biomes up to y80. If fences are enabled, dark oak wood is used.");
		enableCoarseDirtReplacement = cfg.getBoolean("enableCoarseDirtReplacement", catGeneration, true, "Replaces coarse dirt in biomes it (dirt:1) generates in such as shattered savannas or mesa plateaus.");
		enableAmethystGeodes = cfg.getBoolean("enableAmethystGeodes", catGeneration, true, "");
		buddingAmethystMode  = cfg.getInt("buddingAmethystMode", catGeneration, 0, 0, 2, "0 = Budding amethyst cannot be obtained at all even with silk touch. When using this option, attempting to push them using a piston will break it.\n1 = Budding amethyst will drop if you use a silk touch pickaxe.\n2 = Budding amethyst does not need silk touch, just a pickaxe.");
		
		
		fullGrassPath = cfg.getBoolean("fullGrassPath", catMisc, false, "Set to true if you're having issues with stepping over grass paths. Temporary option until fixes are implemented to 1.7's stepping system.");
		tileReplacementMode = cfg.getInt("tileReplacementMode", catMisc, 0, -1, 1, "Replace old Brewing Stands/Enchanting Tables/Daylight Sensors/Beacons with new one on the fly.\n-1 = Disabled, no conversion even if the replacement tile entities are on\n0 = Convert the vanilla tile entities to their Et Futurum versions\n1 = Convert Et Futurum replacement tile entities back to default ones. Useful if you want to turn those off.");
	}
}
