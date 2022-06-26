package ganymedes01.etfuturum.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.MixinEnvironment;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.EtFuturumMixinPlugin;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.blocks.ExternalContent;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public abstract class ConfigBase extends Configuration {
	
	private static final String catClientLegacy = "client";
	private static final String catBlockLegacy = "blocks";
	private static final String catItemsLegacy = "items";
	private static final String catEquipmentLegacy = "equipment";
	private static final String catEnchantsLegacy = "enchants";
	private static final String catEntityLegacy = "entity";
	private static final String catReplacementLegacy = "replacement";
	private static final String catFunctionLegacy = "function";
	private static final String catWorldLegacy = "world";
	
	protected final List<ConfigCategory> configCats = new ArrayList<ConfigCategory>();
	
	public List<ConfigCategory> getConfigCats() {
		return configCats;
	}

	protected static String configDir = "config" + File.separator + Reference.MOD_ID;
	public static final String PATH = configDir + File.separator + "etfuturum.cfg";
	
	public ConfigBase(File file) {
		super(file);
	}
	
	public void syncConfig() {
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
	
	public static void postInit() {
		ConfigFunctions.shulkerBans = new ArrayList<Item>();
		for(String itemName : ConfigFunctions.shulkerBansString) {
			String[] nameAndID;
			if(itemName.contains(":") && (nameAndID = itemName.split(":")).length == 2) {
				Item item = GameRegistry.findItem(nameAndID[0], nameAndID[1]);
				if(item != null) {
					if(!ConfigFunctions.shulkerBans.contains(item)) {
						ConfigFunctions.shulkerBans.add(item);
					} else {
						System.err.println("Shulker ban list entry \"" + itemName + "\" is already added!");
					}
				}
			} else {
				System.err.println("Shulker ban list entry \"" + itemName + "\" is formatted incorrectly!");
			}
		}
		
		Block block = ConfigWorld.fossilBlockID == 1 ? ExternalContent.netherlicious_bone_block : ExternalContent.utd_bone_block;
		ConfigWorld.fossilBoneBlock = ConfigWorld.fossilBlockID == 0 || block == null ? ModBlocks.bone_block : block;
		block = ConfigWorld.amethystOuterID == 1 && ConfigBlocksItems.enableTuff ? ModBlocks.tuff : ExternalContent.netherlicious_smooth_basalt;
		ConfigWorld.amethystOuterBlock = ConfigWorld.amethystOuterID == 0 || block == null ? ModBlocks.smooth_basalt : block;
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (Reference.MOD_ID.equals(eventArgs.modID))
			syncConfig();
	}
}
