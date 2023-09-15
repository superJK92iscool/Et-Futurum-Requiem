package ganymedes01.etfuturum.configuration;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ganymedes01.etfuturum.EtFuturumMixinPlugin;
import ganymedes01.etfuturum.configuration.configs.*;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ConfigBase extends Configuration {
	protected final List<ConfigCategory> configCats = new ArrayList<>();
	private static final Set<ConfigBase> CONFIGS = new HashSet<>();

	public static final String configDir = "config" + File.separator + Reference.MOD_ID + File.separator;

	public static final ConfigBase BLOCKS_ITEMS = new ConfigBlocksItems(new File(Launch.minecraftHome,  configDir + "blocksitems.cfg"));
	public static final ConfigBase ENCHANTS_POTIONS = new ConfigEnchantsPotions(new File(Launch.minecraftHome, configDir + "enchantspotions.cfg"));
	public static final ConfigBase FUNCTIONS = new ConfigFunctions(new File(Launch.minecraftHome, configDir + "functions.cfg"));
	public static final ConfigBase TWEAKS = new ConfigTweaks(new File(Launch.minecraftHome, configDir + "tweaks.cfg"));
	public static final ConfigBase WORLD = new ConfigWorld(new File(Launch.minecraftHome, configDir + "world.cfg"));
	public static final ConfigBase ENTITIES = new ConfigEntities(new File(Launch.minecraftHome, configDir + "entities.cfg"));
	public static final ConfigBase SOUNDS = new ConfigSounds(new File(Launch.minecraftHome, configDir + "sounds.cfg"));
	public static final ConfigBase MOD_COMPAT = new ConfigModCompat(new File(Launch.minecraftHome, configDir + "modcompat.cfg"));
	
	public static final ConfigBase MIXINS = new ConfigMixins(new File(Launch.minecraftHome, configDir + "mixins.cfg"));

	/**
	 *      ConfigBlocksItems.configInstance.syncConfig();
	 *      ConfigEnchantsPotions.configInstance.syncConfig();
	 *      ConfigFunctions.configInstance.syncConfig();
	 *      ConfigTweaks.configInstance.syncConfig();
	 *      ConfigWorld.configInstance.syncConfig();
	 *      ConfigEntities.configInstance.syncConfig();
	 *      ConfigSounds.configInstance.syncConfig();
	 *      ConfigModCompat.configInstance.syncConfig();
	 *
	 *      ConfigMixins.configInstance.syncConfig();
	 */
	
	public ConfigBase(File file) {
		super(file);
		CONFIGS.add(this);
	}

	public static void initializeConfigs() {
		for(ConfigBase config : CONFIGS) {
			config.syncConfig();
		}
	}
	
	private void syncConfig() {
		syncConfigOptions();
		
		for(ConfigCategory cat : configCats) {
			if(EtFuturumMixinPlugin.side == MixinEnvironment.Side.SERVER) {
				if(cat.getName().contains("client")) {
					for(Property prop : cat.getOrderedValues()) {
						cat.remove(prop.getName());
					}
				}
			}
			
			if(cat.isEmpty()) {
				removeCategory(cat);
			}
		}
		
		if (hasChanged()) {
			save();
		}
	}

	protected abstract void syncConfigOptions();

	/**
	 * Used in case we need to wait till later to initialize some config values.
	 */
	protected void initValues() {}
	
	public static void postInit() {
		for(ConfigBase config : CONFIGS) {
			config.initValues();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfig();
	}
}
