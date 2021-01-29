package ganymedes01.etfuturum;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.util.Iterator;

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
import cpw.mods.fml.relauncher.Side;
import ganymedes01.etfuturum.command.SetPlayerModelCommand;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.proxy.CommonProxy;
import ganymedes01.etfuturum.core.utils.HoeHelper;
import ganymedes01.etfuturum.entities.ModEntityList;
import ganymedes01.etfuturum.items.ItemEntityEgg;
import ganymedes01.etfuturum.lib.ModSounds;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.network.ArmourStandInteractHandler;
import ganymedes01.etfuturum.network.ArmourStandInteractMessage;
import ganymedes01.etfuturum.network.BlackHeartParticlesHandler;
import ganymedes01.etfuturum.network.BlackHeartParticlesMessage;
import ganymedes01.etfuturum.network.SetPlayerModelHandler;
import ganymedes01.etfuturum.network.SetPlayerModelMessage;
import ganymedes01.etfuturum.network.WoodSignOpenHandler;
import ganymedes01.etfuturum.network.WoodSignOpenMessage;
import ganymedes01.etfuturum.recipes.BrewingFuelRegistry;
import ganymedes01.etfuturum.recipes.ModRecipes;
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
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

@Mod(
        modid = "etfuturum", 
        name = "Et Futurum", 
        version = "@VERSION@", 
        dependencies = "required-after:Forge@[10.13.4.1558,);after:Thaumcraft@[4.2.3.5,);after:netherlicious", 
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
            return ConfigurationHandler.enableNetherite ? ModItems.netherite_scrap : ConfigurationHandler.enablePrismarine ? ModItems.prismarine_shard : Items.magma_cream;
        }
    };
    
    public static CreativeTabs creativeTabBlocks = new CreativeTabs(Reference.MOD_ID + ".blocks") {
        @Override
        public Item getTabIconItem() {
            return ConfigurationHandler.enableSmoker ? Item.getItemFromBlock(ModBlocks.smoker) : ConfigurationHandler.enableChorusFruit ? Item.getItemFromBlock(ModBlocks.chorus_flower) : Item.getItemFromBlock(Blocks.ender_chest);
        }
    };
    
	public static boolean netherAmbienceNetherlicious;
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

        GameRegistry.registerWorldGenerator(new EtFuturumWorldGenerator(), 0);

        ModBlocks.init();
        ModItems.init();
        ModEnchantments.init();

        OceanMonument.makeMap();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
        networkWrapper.registerMessage(ArmourStandInteractHandler.class, ArmourStandInteractMessage.class, 0, Side.SERVER);
        networkWrapper.registerMessage(BlackHeartParticlesHandler.class, BlackHeartParticlesMessage.class, 1, Side.CLIENT);
        networkWrapper.registerMessage(SetPlayerModelHandler.class, SetPlayerModelMessage.class, 2, Side.CLIENT);
		networkWrapper.registerMessage(WoodSignOpenHandler.class, WoodSignOpenMessage.class, 3, Side.CLIENT);   
    	{
    		if (Loader.isModLoaded("netherlicious")) {
    			Netherlicious = new Configuration(
    					new File(event.getModConfigurationDirectory() + "/netherlicious/Biome_Sound_Configuration.cfg"));
    			netherAmbienceNetherlicious = Netherlicious.get("1 nether ambience", "Allow Biome specific sounds to play", true).getBoolean();

    		}
    	}       
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);

        ModRecipes.init();

        proxy.registerEvents();
        proxy.registerEntities();
        proxy.registerRenderers();

        if (ModEntityList.hasEntitiesWithEggs()) {
            ModEntityList.entity_egg = new ItemEntityEgg();
            GameRegistry.registerItem(ModEntityList.entity_egg, "entity_egg");
            OreDictionary.registerOre("mobEgg", ModEntityList.entity_egg);
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Items.blaze_rod.setFull3D();
        Blocks.trapped_chest.setCreativeTab(CreativeTabs.tabRedstone);

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

        if(ConfigurationHandler.enableHoeMining) {
        	HoeHelper.init();
        }
        
        if(ConfigurationHandler.enableNewBlocksSounds) {
    		Iterator iterator = Block.blockRegistry.iterator();
    		while(iterator.hasNext()) {
    			Block block = (Block) iterator.next();
    			if(block == null || block.stepSound == null || Block.blockRegistry.getNameForObject(block) == null)
    				continue;
    			
    			if(block.stepSound == Block.soundTypePiston || block.stepSound == Block.soundTypeStone) {
    				if(Block.blockRegistry.getNameForObject(block).toLowerCase().contains("nether") &&
    					Block.blockRegistry.getNameForObject(block).toLowerCase().contains("brick"))
        				block.setStepSound(ModSounds.soundNetherBricks);
    				else if(Block.blockRegistry.getNameForObject(block).toLowerCase().contains("netherrack"))
            				block.setStepSound(ModSounds.soundNetherrack);
    			}
    		}
    		
    		Blocks.quartz_ore.setStepSound(ModSounds.soundNetherOre);
    		Blocks.soul_sand.setStepSound(ModSounds.soundSoulSand);
        }
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
        if (ConfigurationHandler.enablePlayerSkinOverlay)
            event.registerServerCommand(new SetPlayerModelCommand());
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
}