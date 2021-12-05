package ganymedes01.etfuturum;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.gson.JsonObject;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.api.waila.WailaRegistrar;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigFunctions;
import ganymedes01.etfuturum.configuration.configs.ConfigWorld;
import ganymedes01.etfuturum.core.proxy.CommonProxy;
import ganymedes01.etfuturum.core.utils.BrewingFuelRegistry;
import ganymedes01.etfuturum.core.utils.DeepslateOreRegistry;
import ganymedes01.etfuturum.core.utils.HoeHelper;
import ganymedes01.etfuturum.core.utils.RawOreRegistry;
import ganymedes01.etfuturum.core.utils.StrippedLogRegistry;
import ganymedes01.etfuturum.entities.ModEntityList;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.network.ArmourStandInteractHandler;
import ganymedes01.etfuturum.network.ArmourStandInteractMessage;
import ganymedes01.etfuturum.network.BlackHeartParticlesHandler;
import ganymedes01.etfuturum.network.BlackHeartParticlesMessage;
import ganymedes01.etfuturum.network.WoodSignOpenHandler;
import ganymedes01.etfuturum.network.WoodSignOpenMessage;
import ganymedes01.etfuturum.potion.ModPotions;
import ganymedes01.etfuturum.recipes.BlastFurnaceRecipes;
import ganymedes01.etfuturum.recipes.ModRecipes;
import ganymedes01.etfuturum.recipes.SmokerRecipes;
import ganymedes01.etfuturum.world.EtFuturumLateWorldGenerator;
import ganymedes01.etfuturum.world.EtFuturumWorldGenerator;
import ganymedes01.etfuturum.world.end.dimension.DimensionProviderEnd;
import ganymedes01.etfuturum.world.structure.OceanMonument;
import makamys.mclib.core.MCLib;
import makamys.mclib.ext.assetdirector.ADJsonHelper;
import makamys.mclib.ext.assetdirector.AssetDirectorAPI;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockHay;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.config.Configuration;

@Mod(
		modid = Reference.MOD_ID, 
		name = Reference.MOD_NAME, 
		version = Reference.VERSION_NUMBER, 
		dependencies = Reference.DEPENDENCIES, 
		guiFactory = "ganymedes01.etfuturum.configuration.ConfigGuiFactory"
	)

public class EtFuturum {

	@Instance("etfuturum")
	public static EtFuturum instance;

	@SidedProxy(clientSide = "ganymedes01.etfuturum.core.proxy.ClientProxy", serverSide = "ganymedes01.etfuturum.core.proxy.CommonProxy")
	public static CommonProxy proxy;

	public static SimpleNetworkWrapper networkWrapper;

	public static CreativeTabs creativeTabItems = new CreativeTabs(Reference.MOD_ID + ".items") {
		@Override
		public Item getTabIconItem() {
			return ConfigBlocksItems.enableNetherite ? ModItems.netherite_scrap : ConfigBlocksItems.enablePrismarine ? ModItems.prismarine_shard : Items.magma_cream;
		}
		
		@SideOnly(Side.CLIENT)
		@Override
		public void displayAllReleventItems(List p_78018_1_)
		{
			for(int i : ModEntityList.eggIDs)
				p_78018_1_.add(new ItemStack(Items.spawn_egg, 1, i));
			super.displayAllReleventItems(p_78018_1_);
		}
	};
	
	public static CreativeTabs creativeTabBlocks = new CreativeTabs(Reference.MOD_ID + ".blocks") {
		@Override
		public Item getTabIconItem() {
			return ConfigBlocksItems.enableSmoker ? Item.getItemFromBlock(ModBlocks.smoker) : ConfigBlocksItems.enableChorusFruit ? Item.getItemFromBlock(ModBlocks.chorus_flower) : Item.getItemFromBlock(Blocks.ender_chest);
		}
	};
	
	public static boolean netherAmbienceNetherlicious;
	public static boolean netherMusicNetherlicious;
	
	public static boolean hasIronChest;
	public static boolean hasNetherlicious;
	public static boolean hasEnderlicious;
	public static final boolean isTesting = Reference.VERSION_NUMBER.equals("@VERSION@");
	
	@EventHandler
	public void onConstruction(FMLConstructionEvent event) {
		MCLib.init();
		
		JsonObject soundsJson = new JsonObject();
		String ver = "1.18";
		
		if(ConfigWorld.enableNewAmbientSounds) {
			ADJsonHelper.addObject(soundsJson, ver, "minecraft/sounds/ambient/cave/cave14.ogg");
			ADJsonHelper.addObject(soundsJson, ver, "minecraft/sounds/ambient/cave/cave15.ogg");
			ADJsonHelper.addObject(soundsJson, ver, "minecraft/sounds/ambient/cave/cave16.ogg");
			ADJsonHelper.addObject(soundsJson, ver, "minecraft/sounds/ambient/cave/cave17.ogg");
			ADJsonHelper.addObject(soundsJson, ver, "minecraft/sounds/ambient/cave/cave18.ogg");
			ADJsonHelper.addObject(soundsJson, ver, "minecraft/sounds/ambient/cave/cave19.ogg");
			
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "weather.rain", "weather");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "weather.rain.above", "weather");
		}
		
		
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.boat.paddle_land", "player");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.boat.paddle_water", "player");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.rabbit.ambient", "neutral");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.rabbit.jump", "neutral");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.rabbit.attack", "neutral");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.rabbit.hurt", "neutral");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.rabbit.death", "neutral");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.zombie_villager.ambient", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.zombie_villager.step", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.zombie_villager.hurt", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.zombie_villager.death", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.husk.ambient", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.husk.step", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.husk.hurt", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.husk.death", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.zombie.converted_to_drowned", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.husk.converted_to_zombie", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.stray.ambient", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.stray.step", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.stray.hurt", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.stray.death", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.skeleton.converted_to_stray", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker_bullet.hurt", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker_bullet.hit", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker.ambient", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker.open", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker.close", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker.shoot", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker.hurt", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker.hurt_closed", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker.death", "hostile");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "entity.shulker.teleport", "hostile");
		
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "item.axe.scrape", "player");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "item.axe.wax_off", "player");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "item.axe.strip", "player");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "item.honeycomb.wax_on", "player");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "item.totem.use", "player");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "item.shovel.flatten", "player");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "item.chorus_fruit.teleport", "player");
		
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.barrel.open", "block");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.barrel.close", "block");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.chorus_flower.grow", "block");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.chorus_flower.death", "block");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.end_portal.spawn", "ambient");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.end_portal_frame.fill", "block");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.shulker_box.open", "block");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.shulker_box.close", "block");
		ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.sweet_berry_bush.pick_berries", "player");
		if(ConfigWorld.enableNewMiscSounds) {
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.brewing_stand.brew", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.furnace.fire_crackle", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.blastfurnace.fire_crackle", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.smoker.smoke", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.chest.close", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.ender_chest.open", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.ender_chest.close", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.wooden_door.open", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.wooden_door.close", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.iron_door.open", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.iron_door.close", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.wooden_trapdoor.open", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.wooden_trapdoor.close", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.iron_trapdoor.open", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.iron_trapdoor.close", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.fence_gate.open", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.fence_gate.close", "block");
		}
		
		if(ConfigWorld.enableNewBlocksSounds) {
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "item.crop.plant", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.crop.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "item.nether_wart.plant", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.nether_wart.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.lantern.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.lantern.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.lantern.place", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.deepslate.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.deepslate.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.deepslate.place", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.sweet_berry_bush.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.sweet_berry_bush.place", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.deepslate_bricks.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.deepslate_bricks.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.soul_sand.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.soul_sand.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.wart_block.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.wart_block.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.nether_bricks.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.nether_bricks.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.bone_block.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.bone_block.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.netherrack.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.netherrack.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.nether_ore.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.nether_ore.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.ancient_debris.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.ancient_debris.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.netherite_block.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.netherite_block.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.nylium.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.nylium.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.basalt.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.basalt.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.honey_block.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.honey_block.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.fungus.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.fungus.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.stem.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.stem.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.shroomlight.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.shroomlight.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.shroomlight.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.shroomlight.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.copper.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.copper.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.tuff.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.tuff.break", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.vine.step", "block");
			ADJsonHelper.addSoundEvent(soundsJson, ver,  "block.vine.break", "block");
		}
		
		AssetDirectorAPI.register(soundsJson);
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		hasIronChest = Loader.isModLoaded("IronChest");
		hasNetherlicious = Loader.isModLoaded("netherlicious");
		hasEnderlicious = Loader.isModLoaded("enderlicious");
		
		ModBlocks.init();
		ModItems.init();
		ModEnchantments.init();
		ModPotions.init();
		
//      if(ConfigurationHandler.enableNewNether) {
//          NetherBiomeManager.init(); // Come back to
//      }

		GameRegistry.registerWorldGenerator(EtFuturumWorldGenerator.INSTANCE, 0);
		GameRegistry.registerWorldGenerator(EtFuturumLateWorldGenerator.INSTANCE, Integer.MAX_VALUE);
		
		OceanMonument.makeMap();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		networkWrapper.registerMessage(ArmourStandInteractHandler.class, ArmourStandInteractMessage.class, 0, Side.SERVER);
		networkWrapper.registerMessage(BlackHeartParticlesHandler.class, BlackHeartParticlesMessage.class, 1, Side.CLIENT);
		networkWrapper.registerMessage(WoodSignOpenHandler.class, WoodSignOpenMessage.class, 3, Side.CLIENT);   
		{
			if (Loader.isModLoaded("netherlicious")) {
				File file = new File(event.getModConfigurationDirectory() + "/Netherlicious/Biome_Sound_Configuration.cfg");
				if(file.exists()) {
					Configuration netherliciousSoundConfig = new Configuration(file);
					netherAmbienceNetherlicious = netherliciousSoundConfig.get("1 nether ambience", "Allow Biome specific sounds to play", true).getBoolean();
					netherMusicNetherlicious = netherliciousSoundConfig.get("2 biome music", "1 Replace the Music System in the Nether, to allow Biome specific Music. Default Music will still play sometimes", true).getBoolean();
				}
			}
		}
		
		//Define mod data here instead of in mcmod.info, adapted from Village Names.
		//Thanks AstroTibs!
		//Updated by Makamys to use mcmod.info again but also have color values without glitches.
		
		event.getModMetadata().autogenerated = false; // stops it from complaining about missing mcmod.info
		
		event.getModMetadata().name = "\u00a75\u00a7o" + Reference.MOD_NAME; // name 
		
		event.getModMetadata().version = "\u00a7e" + Reference.VERSION_NUMBER; // version 
		
		event.getModMetadata().credits = Reference.CREDITS; // credits 
		
		event.getModMetadata().authorList.clear();
		event.getModMetadata().authorList.addAll(Arrays.asList(Reference.AUTHOR_LIST)); // authorList - added as a list
		
		event.getModMetadata().url = Reference.MOD_URL;
		
		event.getModMetadata().description = Reference.DESCRIPTION; // description
				
		
		event.getModMetadata().logoFile = Reference.LOGO_FILE;
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ModRecipes.init();
		
		if(Loader.isModLoaded("Waila")) {
			WailaRegistrar.register();
		}
		
		proxy.registerEvents();
		proxy.registerEntities();
		proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		for(BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
			if(biome == null)
				continue;
			//TODO: Change to bone meal event because the description of addFlower is a literal lie, as it affects worldgen too!
			Type[] biomeList = BiomeDictionary.getTypesForBiome(biome);
				if(biome.biomeID == 132 || (ArrayUtils.contains(biomeList, Type.PLAINS) && !ArrayUtils.contains(biomeList, Type.SNOWY) && !ArrayUtils.contains(biomeList, Type.SAVANNA)))
					biome.addFlower(ModBlocks.cornflower, 0, 12);
				if(biome.biomeID == 132)
					biome.addFlower(ModBlocks.lily_of_the_valley, 0, 5);
		}

		if (ConfigFunctions.enableUpdatedFoodValues) {
			((ItemFood)Items.carrot).healAmount = 3;
			((ItemFood)Items.baked_potato).healAmount = 5;
		}

		if (ConfigFunctions.enableUpdatedHarvestLevels) {
			Blocks.packed_ice.setHarvestLevel("pickaxe", 0);
			Blocks.ladder.setHarvestLevel("axe", 0);
			Blocks.melon_block.setHarvestLevel("axe", 0);
		}
		
		if(ConfigFunctions.enableFloatingTrapDoors) {
			BlockTrapDoor.disableValidation = true;
		}
		
		if (Loader.isModLoaded("Thaumcraft")) {
			CompatTC.doAspects();
		}
		
		Items.blaze_rod.setFull3D();
		Blocks.trapped_chest.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent e){
		DeepslateOreRegistry.init();
		StrippedLogRegistry.init();
		RawOreRegistry.init();
		SmokerRecipes.init();
		BlastFurnaceRecipes.init();
		ConfigBase.postInit();
		
		//Block registry iterator
		Iterator<Block> iterator = Block.blockRegistry.iterator();
		while(iterator.hasNext()) {
			Block block = iterator.next();

			if(ConfigFunctions.enableHoeMining) {
				/*
				 * HOE MINING
				 */
				if(block instanceof BlockLeaves || block instanceof BlockHay || block instanceof BlockSponge || block instanceof BlockNetherWart) {
					HoeHelper.addToHoeArray(block);
				}
			}

			if(ConfigWorld.enableNewBlocksSounds) {
				/*
				 * SOUNDS
				 */
				String blockID = Block.blockRegistry.getNameForObject(block).split(":")[1].toLowerCase();
				
				SoundType sound = getCustomStepSound(block, blockID);
				if(sound != null) {
					block.setStepSound(sound);
				}
			}
			
			/*
			 * MATERIALS
			 */
			if(block == Blocks.bed && isTesting) {
				block.blockMaterial = Material.wood;
			}
		}
		
//      if(ConfigurationHandler.enableNewNether)
//        DimensionProviderNether.init(); // Come back to
		
		if(isTesting) {
			DimensionProviderEnd.init(); // Come back to
		}
	}
	
	public SoundType getCustomStepSound(Block block, String namespace) {
		
		if(block.stepSound == Block.soundTypePiston || block.stepSound == Block.soundTypeStone) {
			
			if(namespace.contains("nether") && namespace.contains("brick")) {
				return ModSounds.soundNetherBricks;
			}
			
			else if(namespace.contains("netherrack")) {
					return ModSounds.soundNetherrack;
			}
			
			else if(block == Blocks.quartz_ore || (namespace.contains("nether") && (block instanceof BlockOre || namespace.contains("ore")))) {
				return ModSounds.soundNetherOre;
			}
			
			else if(namespace.contains("deepslate")) {
				return namespace.contains("brick") ? ModSounds.soundDeepslateBricks : ModSounds.soundDeepslate;
			}
			
			else if(block instanceof BlockNetherWart || (namespace.contains("nether") && namespace.contains("wart"))) {
				return ModSounds.soundCropWarts;
			}
			
			else if(namespace.contains("bone") || namespace.contains("ivory")) {
				return ModSounds.soundBoneBlock;
			}
			
		}
		
		if(block instanceof BlockCrops || block instanceof BlockStem) {
			return ModSounds.soundCrops;
		}
		
		if(block.stepSound == Block.soundTypeGrass && block instanceof BlockVine) {
			return ModSounds.soundVines;
		}
		
		if(block.stepSound == Block.soundTypeSand && namespace.contains("soul") && namespace.contains("sand")) {
			return ModSounds.soundSoulSand;
		}
		
		if(block.stepSound == Block.soundTypeMetal && (namespace.contains("copper") || namespace.contains("tin"))) {
			return ModSounds.soundCopper;
		}
		
		return null;
	}

	@EventHandler
	public void processIMCRequests(IMCEvent event) {
		for (IMCMessage message : event.getMessages())
			if (message.key.equals("register-brewing-fuel")) {
				NBTTagCompound nbt = message.getNBTValue();
				ItemStack stack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Fuel"));
				int brews = nbt.getInteger("Brews");
				BrewingFuelRegistry.registerFuel(stack, brews);
			}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
//        if (ConfigurationHandler.enablePlayerSkinOverlay)
//            event.registerServerCommand(new SetPlayerModelCommand());
	}
	
	public static void copyAttribs(Block to, Block from) {
		to.setHardness(from.blockHardness);
		to.setResistance(from.blockResistance);
		to.setBlockName(from.getUnlocalizedName().replace("tile.", ""));
		to.setCreativeTab(from.displayOnCreativeTab);
		to.setStepSound(from.stepSound);
		to.setBlockTextureName(from.textureName);
		//We do this because Forge methods cannot be Access Transformed
		for(int i = 0; i < 16; i++) {
			String tool = from.getHarvestTool(i);
			int level = from.getHarvestLevel(i);
			to.setHarvestLevel(tool, level, i);
		}
	}

	public static void setFinalField(Class<?> cls, Object obj, Object newValue, String... fieldNames) {
		try {
			Field field = null;
			Field[] fieldList = cls.getFields();
			for(int i = 0; i < fieldList.length; i++) {
				for(int j = 0; j < fieldNames.length; j++)
					if(fieldList[i].getName().equals(fieldNames[j])) {
						field = fieldList[i];
						break;
					}
				if(field == null)
					continue;
				field.setAccessible(true);

				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

				field.set(obj, newValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PotionEffect getSuspiciousStewEffect(ItemStack stack) {
		
		if(stack == null)
			return null;

		Item item = stack.getItem();

		if(item == Item.getItemFromBlock(Blocks.red_flower)) {
			switch(stack.getItemDamage()) {
			default:
			case 0:
				return new PotionEffect(Potion.nightVision.id, 100, 0);
			case 1:
				return new PotionEffect(Potion.field_76443_y.id, 7, 0);
			case 2:
				return new PotionEffect(Potion.fireResistance.id, 80, 0);
			case 3:
				return new PotionEffect(Potion.blindness.id, 160, 0);
			case 4:
			case 5:
			case 6:
			case 7:
				return new PotionEffect(Potion.weakness.id, 180, 0);
			case 8:
				return new PotionEffect(Potion.regeneration.id, 160, 0);
			}
		}

		if(item == Item.getItemFromBlock(Blocks.yellow_flower)) {
			return new PotionEffect(Potion.field_76443_y.id, 7, 0);
		}

		if(item == Item.getItemFromBlock(ModBlocks.cornflower)) {
			return new PotionEffect(Potion.jump.id, 120, 0);
		}

		if(item == Item.getItemFromBlock(ModBlocks.lily_of_the_valley)) {
			return new PotionEffect(Potion.poison.id, 240, 0);
		}

		if(item == Item.getItemFromBlock(ModBlocks.wither_rose)) {
			return new PotionEffect(Potion.wither.id, 160, 0);
		}
		return null;
	}

}
