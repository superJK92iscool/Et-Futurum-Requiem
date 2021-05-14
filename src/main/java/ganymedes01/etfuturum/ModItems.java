package ganymedes01.etfuturum;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.items.Beetroot;
import ganymedes01.etfuturum.items.BeetrootSeeds;
import ganymedes01.etfuturum.items.BeetrootSoup;
import ganymedes01.etfuturum.items.ChorusFruit;
import ganymedes01.etfuturum.items.DragonBreath;
import ganymedes01.etfuturum.items.Elytra;
import ganymedes01.etfuturum.items.EndCrystal;
import ganymedes01.etfuturum.items.ItemArmourStand;
import ganymedes01.etfuturum.items.ItemCopperIngot;
import ganymedes01.etfuturum.items.ItemNetherite;
import ganymedes01.etfuturum.items.ItemNewDye;
import ganymedes01.etfuturum.items.ItemSuspiciousStew;
import ganymedes01.etfuturum.items.ItemSweetBerries;
import ganymedes01.etfuturum.items.LingeringPotion;
import ganymedes01.etfuturum.items.MuttonCooked;
import ganymedes01.etfuturum.items.MuttonRaw;
import ganymedes01.etfuturum.items.NuggetIron;
import ganymedes01.etfuturum.items.PoppedChorusFruit;
import ganymedes01.etfuturum.items.PrismarineCrystals;
import ganymedes01.etfuturum.items.PrismarineShard;
import ganymedes01.etfuturum.items.RabbitCooked;
import ganymedes01.etfuturum.items.RabbitFoot;
import ganymedes01.etfuturum.items.RabbitHide;
import ganymedes01.etfuturum.items.RabbitRaw;
import ganymedes01.etfuturum.items.RabbitStew;
import ganymedes01.etfuturum.items.TippedArrow;
import ganymedes01.etfuturum.items.TotemUndying;
import ganymedes01.etfuturum.items.block.ItemWoodSign;
import ganymedes01.etfuturum.items.equipment.ItemEFRArmour;
import ganymedes01.etfuturum.items.equipment.ItemEFRAxe;
import ganymedes01.etfuturum.items.equipment.ItemEFRHoe;
import ganymedes01.etfuturum.items.equipment.ItemEFRPickaxe;
import ganymedes01.etfuturum.items.equipment.ItemEFRSpade;
import ganymedes01.etfuturum.items.equipment.ItemEFRSword;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	
	public static final Item.ToolMaterial NETHERITE_TOOL = EnumHelper.addToolMaterial("Netherite_Tool", ConfigurationHandler.netheriteHarvestLevel, ConfigurationHandler.netheriteToolDurability, ConfigurationHandler.netheriteSpeed, ConfigurationHandler.netheriteDamageBase, ConfigurationHandler.netheriteEnchantability);
	public static final ItemArmor.ArmorMaterial NETHERITE_ARMOUR = EnumHelper.addArmorMaterial("Netherite_Armour", ConfigurationHandler.netheriteArmourDurabilityFactor, new int[]{3, 6, 8, 3}, ConfigurationHandler.netheriteEnchantability);

<<<<<<< HEAD
	static {
		NETHERITE_TOOL.setRepairItem(new ItemStack(ModItems.netherite_ingot));
		NETHERITE_ARMOUR.customCraftingMaterial = ModItems.netherite_ingot;
	}

	public static final Item raw_mutton = new MuttonRaw();
	public static final Item cooked_mutton = new MuttonCooked();
	public static final Item prismarine_shard = new PrismarineShard();
	public static final Item prismarine_crystals = new PrismarineCrystals();
	public static final Item armour_stand = new ItemArmourStand();
	public static final Item raw_rabbit = new RabbitRaw();
	public static final Item cooked_rabbit = new RabbitCooked();
	public static final Item rabbit_foot = new RabbitFoot();
	public static final Item rabbit_hide = new RabbitHide();
	public static final Item rabbit_stew = new RabbitStew();
	public static final Item beetroot = new Beetroot();
	public static final Item beetroot_seeds = new BeetrootSeeds();
	public static final Item beetroot_soup = new BeetrootSoup();
	public static final Item chorus_fruit = new ChorusFruit();
	public static final Item popped_chorus_fruit = new PoppedChorusFruit();
	public static final Item tipped_arrow = new TippedArrow();
	public static final Item lingering_potion = new LingeringPotion();
	public static final Item dragon_breath = new DragonBreath();
	public static final Item elytra = new Elytra();
	public static final Item end_crystal = new EndCrystal();
	
	public static final Item iron_nugget = new NuggetIron();
	public static final Item netherite_scrap = new ItemNetherite(0);
	public static final Item netherite_ingot = new ItemNetherite(1);
	public static final Item netherite_helmet = new ItemEFRArmour(NETHERITE_ARMOUR, 0, ConfigurationHandler.netheriteHelmetDurability);
	public static final Item netherite_chestplate = new ItemEFRArmour(NETHERITE_ARMOUR, 1, ConfigurationHandler.netheriteChestplateDurability);
	public static final Item netherite_leggings = new ItemEFRArmour(NETHERITE_ARMOUR, 2, ConfigurationHandler.netheriteLeggingsDurability);
	public static final Item netherite_boots = new ItemEFRArmour(NETHERITE_ARMOUR, 3, ConfigurationHandler.netheriteBootsDurability);
	public static final Item netherite_pickaxe = new ItemEFRPickaxe(NETHERITE_TOOL, ConfigurationHandler.netheritePickaxeDurability);
	public static final Item netherite_spade = new ItemEFRSpade(NETHERITE_TOOL, ConfigurationHandler.netheriteSpadeDurability);
	public static final Item netherite_axe = new ItemEFRAxe(NETHERITE_TOOL, ConfigurationHandler.netheriteAxeDurability);
	public static final Item netherite_hoe = new ItemEFRHoe(NETHERITE_TOOL, ConfigurationHandler.netheriteHoeDurability);
	public static final Item netherite_sword = new ItemEFRSword(NETHERITE_TOOL, ConfigurationHandler.netheriteSwordDurability);
	public static final Item[] signs = new Item[ModBlocks.woodTypes.length - 1];
	public static final Item totem = new TotemUndying();
	public static final Item new_dye = new ItemNewDye();
	public static final Item copper_ingot = new ItemCopperIngot();
	public static final Item suspicious_stew = new ItemSuspiciousStew();
	public static final Item sweet_berries = new ItemSweetBerries();
=======
	private static final List<Item> initList = new ArrayList<Item>();
	
	static {
		NETHERITE_TOOL.setRepairItem(new ItemStack(ModItems.netherite_ingot));
		NETHERITE_ARMOUR.customCraftingMaterial = ModItems.netherite_ingot;
	}

	public static final Item raw_mutton = initItem(new MuttonRaw());
	public static final Item cooked_mutton = initItem(new MuttonCooked());
	public static final Item prismarine_shard = initItem(new PrismarineShard());
	public static final Item prismarine_crystals = initItem(new PrismarineCrystals());
	public static final Item armour_stand = initItem(new ItemArmourStand());
	public static final Item raw_rabbit = initItem(new RabbitRaw());
	public static final Item cooked_rabbit = initItem(new RabbitCooked());
	public static final Item rabbit_foot = initItem(new RabbitFoot());
	public static final Item rabbit_hide = initItem(new RabbitHide());
	public static final Item rabbit_stew = initItem(new RabbitStew());
	public static final Item beetroot = initItem(new Beetroot());
	public static final Item beetroot_seeds = initItem(new BeetrootSeeds());
	public static final Item beetroot_soup = initItem(new BeetrootSoup());
	public static final Item chorus_fruit = initItem(new ChorusFruit());
	public static final Item popped_chorus_fruit = initItem(new PoppedChorusFruit());
	public static final Item tipped_arrow = initItem(new TippedArrow());
	public static final Item lingering_potion = initItem(new LingeringPotion());
	public static final Item dragon_breath = initItem(new DragonBreath());
	public static final Item elytra = initItem(new Elytra());
	public static final Item end_crystal = initItem(new EndCrystal());
	
	public static final Item iron_nugget = initItem(new NuggetIron());
	public static final Item netherite_scrap = initItem(new ItemNetherite(0));
	public static final Item netherite_ingot = initItem(new ItemNetherite(1));
	public static final Item netherite_helmet = initItem(new ItemEFRArmour(NETHERITE_ARMOUR, 0, ConfigurationHandler.netheriteHelmetDurability));
	public static final Item netherite_chestplate = initItem(new ItemEFRArmour(NETHERITE_ARMOUR, 1, ConfigurationHandler.netheriteChestplateDurability));
	public static final Item netherite_leggings = initItem(new ItemEFRArmour(NETHERITE_ARMOUR, 2, ConfigurationHandler.netheriteLeggingsDurability));
	public static final Item netherite_boots = initItem(new ItemEFRArmour(NETHERITE_ARMOUR, 3, ConfigurationHandler.netheriteBootsDurability));
	public static final Item netherite_pickaxe = initItem(new ItemEFRPickaxe(NETHERITE_TOOL, ConfigurationHandler.netheritePickaxeDurability));
	public static final Item netherite_spade = initItem(new ItemEFRSpade(NETHERITE_TOOL, ConfigurationHandler.netheriteSpadeDurability));
	public static final Item netherite_axe = initItem(new ItemEFRAxe(NETHERITE_TOOL, ConfigurationHandler.netheriteAxeDurability));
	public static final Item netherite_hoe = initItem(new ItemEFRHoe(NETHERITE_TOOL, ConfigurationHandler.netheriteHoeDurability));
	public static final Item netherite_sword = initItem(new ItemEFRSword(NETHERITE_TOOL, ConfigurationHandler.netheriteSwordDurability));
	public static final Item[] signs = new Item[ModBlocks.woodTypes.length - 1];
	public static final Item totem = initItem(new TotemUndying());
	public static final Item new_dye = initItem(new ItemNewDye());
	public static final Item copper_ingot = initItem(new ItemCopperIngot());
	public static final Item suspicious_stew = initItem(new ItemSuspiciousStew());
	public static final Item sweet_berries = initItem(new ItemSweetBerries());
>>>>>>> a6a589a14e3fcb63991a2664e15e0aa5f4f5d66d

	static {
		for (int i = 0; i < signs.length; i++)
			signs[i] = initItem(new ItemWoodSign(i + 1));
	}
	
	private static Item initItem(Item item) {
		if(!(item instanceof IConfigurable) || ((IConfigurable)item).isEnabled())
			initList.add(item);
		return item;
	}
	
<<<<<<< HEAD
	public static void init() {
		try {
			for (Field f : ModItems.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof Item)
					registerItem((Item) obj);
				else if (obj instanceof Item[])
					for (Item item : (Item[]) obj)
						registerItem(item);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private static void registerItem(Item item) {
		if (!(item instanceof IConfigurable) || ((IConfigurable) item).isEnabled()) {
			String name = item.getUnlocalizedName();
			String[] strings = name.split("\\.");
			GameRegistry.registerItem(item, strings[strings.length - 1]);
		}
	}
=======
	public static void init() {
		for(Item item : initList) {
			String name = item.getUnlocalizedName();
			String[] strings = name.split("\\.");
			GameRegistry.registerItem(item, strings[strings.length - 1]);
		}
	}
>>>>>>> a6a589a14e3fcb63991a2664e15e0aa5f4f5d66d
}