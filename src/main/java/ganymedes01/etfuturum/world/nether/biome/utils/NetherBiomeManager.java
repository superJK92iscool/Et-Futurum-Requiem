package ganymedes01.etfuturum.world.nether.biome.utils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.Loader;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.utils.Logger;
import ganymedes01.etfuturum.world.nether.biome.BiomeBasaltDeltas;
import ganymedes01.etfuturum.world.nether.biome.BiomeCrimsonForest;
import ganymedes01.etfuturum.world.nether.biome.BiomeSoulSandValley;
import ganymedes01.etfuturum.world.nether.biome.BiomeWarpedForest;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeModContainer;

public class NetherBiomeManager {

	public static boolean netherliciousCompat;

	public static BiomeGenBase crimsonForest;
	public static BiomeGenBase warpedForest;
	public static BiomeGenBase soulSandValley;
	public static BiomeGenBase basaltDeltas;

	public static void init() {
		if (ConfigWorld.crimsonForestID != -1 && ConfigBlocksItems.enableCrimsonBlocks) {
			crimsonForest = new BiomeCrimsonForest(ConfigWorld.crimsonForestID).setBiomeName("Crimson Forest");
		}
		if (ConfigWorld.warpedForestID != -1 && ConfigBlocksItems.enableWarpedBlocks) {
			warpedForest = new BiomeWarpedForest(ConfigWorld.warpedForestID).setBiomeName("Warped Forest");
		}
		if (ConfigWorld.soulSandValleyID != -1) {
			soulSandValley = new BiomeSoulSandValley(ConfigWorld.soulSandValleyID).setBiomeName("Soul Sand Valley");
		}
		if (ConfigWorld.basaltDeltasID != -1 && ModBlocks.BASALT.isEnabled() && ModBlocks.BLACKSTONE.isEnabled()) {
			basaltDeltas = new BiomeBasaltDeltas(ConfigWorld.basaltDeltasID).setBiomeName("Basalt Deltas");
		}

		BiomeGenBase.hell.topBlock = Blocks.netherrack;
		BiomeGenBase.hell.fillerBlock = Blocks.netherrack;
		BiomeGenBase.hell.biomeName = "Nether Wastes";
		BiomeGenBase.hell.field_76754_C = 0;
		//Sets filler block meta to 0. Vanilla sets it to a ridiculous value for some reason.
		//This value seems to be unused in vanilla as well.
	}

	/**
	 * THIS IS FOR DELIRUSCRUX TO INVOKE TO ENABLE NETHERLICIOUS COMPAT! OTHER MODS DO NOT TOUCH!!!
	 *
	 * @return
	 */
	public static void enableNetherliciousCompat() {
		if (EtFuturum.hasNetherlicious && Loader.instance().activeModContainer().equals("netherlicious")) {
			if (ConfigWorld.netherDimensionProvider) {
				Logger.info("Enabling Netherlicious compat. Et Futurum Requiem's vanilla style Nether biomes will generate with Netherlicious blocks.");
				Logger.info("To turn this functionality off, head over to Et Futurum Requiem's world.cfg, and turn off the provider, or each biome separately.");
				netherliciousCompat = true;
				if (provideWarpedForestID() != -1) {
				}
			}
		} else {
			throw new RuntimeException("This is for DelirusCrux to invoke for Netherlicious compat, NOT other mods.");
		}
	}

	public static int provideCrimsonForestID() {
		return crimsonForest != null ? crimsonForest.biomeID : -1;
	}

	public static int provideWarpedForestID() {
		return warpedForest != null ? warpedForest.biomeID : -1;
	}

	public static int provideSoulSandValleyID() {
		return soulSandValley != null ? soulSandValley.biomeID : -1;
	}

	public static int provideBasaltDeltasID() {
		return basaltDeltas != null ? basaltDeltas.biomeID : -1;
	}

	public static int provideCrimsonForestWeight() {
		return crimsonForest != null ? ConfigWorld.crimsonForestWeight : -1;
	}

	public static int provideWarpedForestWeight() {
		return warpedForest != null ? ConfigWorld.warpedForestWeight : -1;
	}

	public static int provideSoulSandValleyWeight() {
		return soulSandValley != null ? ConfigWorld.soulSandValleyWeight : -1;
	}

	public static int provideBasaltDeltasWeight() {
		return basaltDeltas != null ? ConfigWorld.basaltDeltasWeight : -1;
	}
}