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
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.blocks.ores.DeepslateMapping;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.proxy.CommonProxy;
import ganymedes01.etfuturum.core.utils.HoeHelper;
import ganymedes01.etfuturum.entities.ModEntityList;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.network.ArmourStandInteractHandler;
import ganymedes01.etfuturum.network.ArmourStandInteractMessage;
import ganymedes01.etfuturum.network.BlackHeartParticlesHandler;
import ganymedes01.etfuturum.network.BlackHeartParticlesMessage;
import ganymedes01.etfuturum.network.WoodSignOpenHandler;
import ganymedes01.etfuturum.network.WoodSignOpenMessage;
import ganymedes01.etfuturum.recipes.BlastFurnaceRecipes;
import ganymedes01.etfuturum.recipes.BrewingFuelRegistry;
import ganymedes01.etfuturum.recipes.ModRecipes;
import ganymedes01.etfuturum.recipes.SmokerRecipes;
import ganymedes01.etfuturum.world.EtFuturumLateWorldGenerator;
import ganymedes01.etfuturum.world.EtFuturumWorldGenerator;
import ganymedes01.etfuturum.world.generate.OceanMonument;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
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

	public static Map<DeepslateMapping, DeepslateMapping> deepslateOres = new HashMap<DeepslateMapping, DeepslateMapping>();
	public static CreativeTabs creativeTabItems = new CreativeTabs(Reference.MOD_ID + ".items") {
		@Override
		public Item getTabIconItem() {
			return ConfigurationHandler.enableNetherite ? ModItems.netherite_scrap : ConfigurationHandler.enablePrismarine ? ModItems.prismarine_shard : Items.magma_cream;
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
			return ConfigurationHandler.enableSmoker ? Item.getItemFromBlock(ModBlocks.smoker) : ConfigurationHandler.enableChorusFruit ? Item.getItemFromBlock(ModBlocks.chorus_flower) : Item.getItemFromBlock(Blocks.ender_chest);
		}
	};
	
	public static boolean netherAmbienceNetherlicious;
	public static boolean netherMusicNetherlicious;
	private Configuration Netherlicious;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		File oldFile = new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_ID + ".cfg");
		File configFile = new File(event.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.MOD_ID + File.separator + Reference.MOD_ID + ".cfg");

		configFile.getParentFile().mkdirs();
		if(oldFile.exists()) {
			try {
				Files.copy(oldFile.toPath(), configFile.toPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			oldFile.delete();
		}
		ConfigurationHandler.INSTANCE.init(configFile);

		ModBlocks.init();
		ModItems.init();
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
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		ModRecipes.init();
//        if(ConfigurationHandler.enableNewNether)
//            DimensionProvider.init(); // Come back to

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

		if (ConfigurationHandler.enableUpdatedFoodValues) {
			setFinalField(ItemFood.class, Items.carrot, 3, "healAmount", "field_77853_b");
			setFinalField(ItemFood.class, Items.baked_potato, 5, "healAmount", "field_77853_b");
		}

		if (ConfigurationHandler.enableUpdatedHarvestLevels) {
			Blocks.packed_ice.setHarvestLevel("pickaxe", 0);
			Blocks.ladder.setHarvestLevel("axe", 0);
			Blocks.melon_block.setHarvestLevel("axe", 0);
		}
		
		if(ConfigurationHandler.enableFloatingTrapDoors) {
			BlockTrapDoor.disableValidation = true;
		}
		
		if (Loader.isModLoaded("Thaumcraft")) {
			CompatTC.doAspects();
		}
		
		Items.blaze_rod.setFull3D();
		Blocks.trapped_chest.setCreativeTab(CreativeTabs.tabRedstone);
		
		if(ConfigurationHandler.enableHoeMining) {
			HoeHelper.init();
		}
		
		if(ConfigurationHandler.enableNewBlocksSounds) {
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
				}
			}
			
			Blocks.quartz_ore.setStepSound(ModSounds.soundNetherOre);
			Blocks.soul_sand.setStepSound(ModSounds.soundSoulSand);
		}
		
		ModRecipes.initDeepslate();
		SmokerRecipes.init();
		BlastFurnaceRecipes.init();
		
		// This causes a null pointer
//		System.out.println(EtFuturum.deepslateOres.get(new DeepslateMapping(ModBlocks.copper_ore, 0)).getOre() + " " + EtFuturum.deepslateOres.get(new DeepslateMapping(ModBlocks.copper_ore, 0)).getMeta());
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
		
		if(stack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && stack.getItemDamage() == 2) {
			return new PotionEffect(Potion.fireResistance.id, 80, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && stack.getItemDamage() == 3) {
			return new PotionEffect(Potion.blindness.id, 160, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && stack.getItemDamage() == 1) {
			return new PotionEffect(Potion.field_76443_y.id, 7, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(Blocks.yellow_flower)) {
			return new PotionEffect(Potion.field_76443_y.id, 7, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && stack.getItemDamage() == 8) {
			return new PotionEffect(Potion.regeneration.id, 160, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && stack.getItemDamage() == 0) {
			return new PotionEffect(Potion.nightVision.id, 100, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && stack.getItemDamage() == 4) {
			return new PotionEffect(Potion.weakness.id, 180, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && stack.getItemDamage() == 5) {
			return new PotionEffect(Potion.weakness.id, 180, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && stack.getItemDamage() == 6) {
			return new PotionEffect(Potion.weakness.id, 180, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(Blocks.red_flower) && stack.getItemDamage() == 7) {
			return new PotionEffect(Potion.weakness.id, 180, 0);
		}
		
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.cornflower)) {
			return new PotionEffect(Potion.jump.id, 120, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.lily_of_the_valley)) {
			return new PotionEffect(Potion.poison.id, 240, 0);
		}

		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.wither_rose)) {
			return new PotionEffect(Potion.wither.id, 160, 0);
		}
		
		return null;
		
	}
}