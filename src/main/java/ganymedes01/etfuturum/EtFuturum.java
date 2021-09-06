package ganymedes01.etfuturum;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLModIdMappingEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.ExistingSubstitutionException;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.blocks.replace.BlockNewEndPortal;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.proxy.CommonProxy;
import ganymedes01.etfuturum.core.utils.HoeHelper;
import ganymedes01.etfuturum.entities.ModEntityList;
import ganymedes01.etfuturum.items.ItemRawOre;
import ganymedes01.etfuturum.items.block.ItemEndPortal;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.network.ArmourStandInteractHandler;
import ganymedes01.etfuturum.network.ArmourStandInteractMessage;
import ganymedes01.etfuturum.network.BlackHeartParticlesHandler;
import ganymedes01.etfuturum.network.BlackHeartParticlesMessage;
import ganymedes01.etfuturum.network.WoodSignOpenHandler;
import ganymedes01.etfuturum.network.WoodSignOpenMessage;
import ganymedes01.etfuturum.potion.EtFuturumPotions;
import ganymedes01.etfuturum.recipes.BlastFurnaceRecipes;
import ganymedes01.etfuturum.recipes.BrewingFuelRegistry;
import ganymedes01.etfuturum.recipes.ModRecipes;
import ganymedes01.etfuturum.recipes.SmokerRecipes;
import ganymedes01.etfuturum.world.EtFuturumLateWorldGenerator;
import ganymedes01.etfuturum.world.EtFuturumWorldGenerator;
import ganymedes01.etfuturum.world.end.dimension.DimensionProviderEnd;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import ganymedes01.etfuturum.world.generate.OceanMonument;
import ganymedes01.etfuturum.world.generate.RawOreDropMapping;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockVine;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
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

	public static Map<BlockAndMetadataMapping, BlockAndMetadataMapping> deepslateOres = new HashMap<BlockAndMetadataMapping, BlockAndMetadataMapping>();
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
	private Configuration Netherlicious;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//File from before Et Futurum Requiem (Not in a subdirectory)
		File olderFile = new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_ID + ".cfg");
		//File from before Et Futurum Requiem 2.3.0
		File oldFile = new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_ID + File.separator + Reference.MOD_ID + ".cfg");

		oldFile.getParentFile().mkdirs();
		if(olderFile.exists()) {
			try {
				Files.copy(olderFile.toPath(), oldFile.toPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			olderFile.delete();
		}
		if(oldFile.exists()) {
			ConfigBase.INSTANCE.init(oldFile);
		}

		ModBlocks.init();
		ModItems.init();
		try {
			Block block = new BlockNewEndPortal();
			GameRegistry.addSubstitutionAlias("minecraft:end_portal", GameRegistry.Type.BLOCK, block);
			GameRegistry.addSubstitutionAlias("minecraft:end_portal", GameRegistry.Type.ITEM, new ItemEndPortal(block));
		} catch (ExistingSubstitutionException e) {
			System.err.println("Something already overrides the end portal block, can't use backported item icon!");
			e.printStackTrace();
		}
		ModEnchantments.init();

		GameRegistry.registerWorldGenerator(new EtFuturumWorldGenerator(), 0);
		GameRegistry.registerWorldGenerator(new EtFuturumLateWorldGenerator(), Integer.MAX_VALUE);
		
		OceanMonument.makeMap();

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
		networkWrapper.registerMessage(ArmourStandInteractHandler.class, ArmourStandInteractMessage.class, 0, Side.SERVER);
		networkWrapper.registerMessage(BlackHeartParticlesHandler.class, BlackHeartParticlesMessage.class, 1, Side.CLIENT);
		networkWrapper.registerMessage(WoodSignOpenHandler.class, WoodSignOpenMessage.class, 3, Side.CLIENT);   
		{
			if (Loader.isModLoaded("netherlicious")) {
				Netherlicious = new Configuration(
						new File(event.getModConfigurationDirectory() + "/netherlicious/Biome_Sound_Configuration.cfg"));
				netherAmbienceNetherlicious = Netherlicious.get("1 nether ambience", "Allow Biome specific sounds to play", true).getBoolean();
				netherMusicNetherlicious = Netherlicious.get("2 biome music", "1 Replace the Music System in the Nether, to allow Biome specific Music. Default Music will still play sometimes", true).getBoolean();
			}
		}      
//        if(ConfigurationHandler.enableNewNether)
//          NetherBiomeManager.init(); // Come back to
		
		//Define mod data here instead of in mcmod.info, adapted from Village Names.
        event.getModMetadata().autogenerated = false ; // stops it from complaining about missing mcmod.info
        
        event.getModMetadata().name = 			// name 
        		EnumChatFormatting.GREEN.toString() + EnumChatFormatting.ITALIC.toString() + Reference.MOD_NAME;
        
        event.getModMetadata().version = Reference.VERSION_NUMBER; // version 
        
        event.getModMetadata().credits = 		// credits 
        		EnumChatFormatting.AQUA +
        		"DelirusCrux, AstroTibs";
        
        event.getModMetadata().authorList.clear();
        event.getModMetadata().authorList.add(  // authorList - added as a list
        		EnumChatFormatting.BLUE +
        		"ganymedes01");
        event.getModMetadata().authorList.add(  // authorList - added as a list
        		EnumChatFormatting.AQUA +
        		"Roadhog360");
        event.getModMetadata().authorList.add(  // authorList - added as a list
        		EnumChatFormatting.GREEN +
        		"jss2a98aj");
        event.getModMetadata().authorList.add(  // authorList - added as a list
        		EnumChatFormatting.GREEN +
        		"KryptonCapitan");
        
        event.getModMetadata().url = EnumChatFormatting.GRAY +
        		Reference.MOD_URL;
        
        event.getModMetadata().description = 	// description
	       		EnumChatFormatting.BLUE +
	       		"Brings the future to now, for real this time.";
        
        event.getModMetadata().logoFile = "assets/etfuturum/logo.png";

	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		ModRecipes.init();
//        if(ConfigurationHandler.enableNewNether)
//            DimensionProviderNether.init(); // Come back to
		
		DimensionProviderEnd.init(); // Come back to

		proxy.registerEvents();
		proxy.registerEntities();
		proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		for(BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
			if(biome == null)
				continue;
			Type[] biomeList = BiomeDictionary.getTypesForBiome(biome);
				if(biome.biomeID == 132 || (ArrayUtils.contains(biomeList, Type.PLAINS) && !ArrayUtils.contains(biomeList, Type.SNOWY) && !ArrayUtils.contains(biomeList, Type.SAVANNA)))
					biome.addFlower(ModBlocks.cornflower, 0, 12);
				if(biome.biomeID == 132)
					biome.addFlower(ModBlocks.lily_of_the_valley, 0, 5);
		}

		if (ConfigBase.enableUpdatedFoodValues) {
			setFinalField(ItemFood.class, Items.carrot, 3, "healAmount", "field_77853_b");
			setFinalField(ItemFood.class, Items.baked_potato, 5, "healAmount", "field_77853_b");
		}

		if (ConfigBase.enableUpdatedHarvestLevels) {
			Blocks.packed_ice.setHarvestLevel("pickaxe", 0);
			Blocks.ladder.setHarvestLevel("axe", 0);
			Blocks.melon_block.setHarvestLevel("axe", 0);
		}
		
		if(ConfigBase.enableFloatingTrapDoors) {
			BlockTrapDoor.disableValidation = true;
		}
		
		if (Loader.isModLoaded("Thaumcraft")) {
			CompatTC.doAspects();
		}
		
		Items.blaze_rod.setFull3D();
		Blocks.trapped_chest.setCreativeTab(CreativeTabs.tabRedstone);
		
		if(ConfigBase.enableHoeMining) {
			HoeHelper.init();
		}
		
		if(ConfigBase.enableNewBlocksSounds) {
			Iterator<Block> iterator = Block.blockRegistry.iterator();
			while(iterator.hasNext()) {
				Block block = iterator.next();
				if(block == null || block.stepSound == null || Block.blockRegistry.getNameForObject(block) == null)
					continue;
				String blockID = Block.blockRegistry.getNameForObject(block).split(":")[1].toLowerCase();
				
				if(block.stepSound == Block.soundTypePiston || block.stepSound == Block.soundTypeStone) {
					if(blockID.contains("nether") && blockID.contains("brick"))
						block.setStepSound(ModSounds.soundNetherBricks);
					else if(blockID.contains("netherrack"))
							block.setStepSound(ModSounds.soundNetherrack);
					else if(blockID.contains("nether") && (block instanceof BlockOre || blockID.contains("ore")))
						block.setStepSound(ModSounds.soundNetherOre);
					else if(block instanceof BlockNetherWart || (blockID.contains("nether") && blockID.contains("wart")))
						block.setStepSound(ModSounds.soundCropWarts);
				}
				if(block.stepSound == Block.soundTypeGrass) {
					if(block instanceof BlockCrops)
						block.setStepSound(ModSounds.soundCrops);
					else if(block instanceof BlockVine)
						block.setStepSound(ModSounds.soundVines);
				}
			}
			
			Blocks.quartz_ore.setStepSound(ModSounds.soundNetherOre);
			Blocks.soul_sand.setStepSound(ModSounds.soundSoulSand);
		}
		
		EtFuturumPotion.init();
	}
	
	@EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent e){
		ModRecipes.initDeepslate();
		SmokerRecipes.init();
		BlastFurnaceRecipes.init();
		ConfigBase.setupShulkerBanlist();
		ItemRawOre.initRawOres();
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
	
	@EventHandler
	public void idMappingEvent(FMLModIdMappingEvent event) {
		// update end portal reference in StatList
		ReflectionHelper.setPrivateValue(StatCrafting.class, (StatCrafting)StatList.mineBlockStatArray[119], Item.getItemFromBlock(Block.getBlockById(119)), "field_150960_a");
	}
	
	public static void copyAttribs(Block to, Block from) {
		to.setHardness(getBlockHardness(from));
		to.setResistance(getBlockResistance(from));
		to.setBlockName(from.getUnlocalizedName().replace("tile.", ""));
		Field tab = ReflectionHelper.findField(Block.class, "field_149772_a", "displayOnCreativeTab");
		tab.setAccessible(true);
		try {
			to.setCreativeTab((CreativeTabs) tab.get(from));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		to.setStepSound(from.stepSound);
		to.setBlockTextureName(getTextureName(from));
		Field harvestLevel = ReflectionHelper.findField(Block.class, "harvestLevel");
		try {
			harvestLevel.set(to, harvestLevel.get(from));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Field harvestTool = ReflectionHelper.findField(Block.class, "harvestTool");
		try {
			harvestTool.set(to, harvestTool.get(from));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static float getBlockHardness(Block block) {
		return ReflectionHelper.getPrivateValue(Block.class, block, "field_149782_v", "blockHardness");
	}
	
	public static float getBlockResistance(Block block) {
		return ReflectionHelper.getPrivateValue(Block.class, block, "field_149781_w", "blockResistance");
	}
	
	public static String getTextureName(Block block) {
		return ReflectionHelper.getPrivateValue(Block.class, block, "field_149768_d", "textureName");
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