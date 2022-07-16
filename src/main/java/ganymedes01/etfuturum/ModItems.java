package ganymedes01.etfuturum;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.entities.EntityNewBoat;
import ganymedes01.etfuturum.items.IRegistryName;
import ganymedes01.etfuturum.items.ItemAmethystShard;
import ganymedes01.etfuturum.items.ItemArmorElytra;
import ganymedes01.etfuturum.items.ItemArmorStand;
import ganymedes01.etfuturum.items.ItemArrowTipped;
import ganymedes01.etfuturum.items.ItemBeetroot;
import ganymedes01.etfuturum.items.ItemBeetrootSeeds;
import ganymedes01.etfuturum.items.ItemBeetrootSoup;
import ganymedes01.etfuturum.items.ItemChorusFruit;
import ganymedes01.etfuturum.items.ItemCopperIngot;
import ganymedes01.etfuturum.items.ItemDragonBreath;
import ganymedes01.etfuturum.items.ItemEndCrystal;
import ganymedes01.etfuturum.items.ItemEtFuturumRecord;
import ganymedes01.etfuturum.items.ItemLingeringPotion;
import ganymedes01.etfuturum.items.ItemMuttonCooked;
import ganymedes01.etfuturum.items.ItemMuttonRaw;
import ganymedes01.etfuturum.items.ItemNetherite;
import ganymedes01.etfuturum.items.ItemNewBoat;
import ganymedes01.etfuturum.items.ItemNewDye;
import ganymedes01.etfuturum.items.ItemNuggetIron;
import ganymedes01.etfuturum.items.ItemPoppedChorusFruit;
import ganymedes01.etfuturum.items.ItemPrismarineCrystals;
import ganymedes01.etfuturum.items.ItemPrismarineShard;
import ganymedes01.etfuturum.items.ItemRabbitCooked;
import ganymedes01.etfuturum.items.ItemRabbitFoot;
import ganymedes01.etfuturum.items.ItemRabbitHide;
import ganymedes01.etfuturum.items.ItemRabbitRaw;
import ganymedes01.etfuturum.items.ItemRabbitStew;
import ganymedes01.etfuturum.items.ItemRawOre;
import ganymedes01.etfuturum.items.ItemShulkerBoxUpgrade;
import ganymedes01.etfuturum.items.ItemShulkerShell;
import ganymedes01.etfuturum.items.ItemSuspiciousStew;
import ganymedes01.etfuturum.items.ItemSweetBerries;
import ganymedes01.etfuturum.items.ItemTotemUndying;
import ganymedes01.etfuturum.items.block.ItemWoodSign;
import ganymedes01.etfuturum.items.equipment.ItemEFRArmour;
import ganymedes01.etfuturum.items.equipment.ItemEFRAxe;
import ganymedes01.etfuturum.items.equipment.ItemEFRHoe;
import ganymedes01.etfuturum.items.equipment.ItemEFRPickaxe;
import ganymedes01.etfuturum.items.equipment.ItemEFRSpade;
import ganymedes01.etfuturum.items.equipment.ItemEFRSword;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {
	
	public static final Item.ToolMaterial NETHERITE_TOOL = EnumHelper.addToolMaterial("Netherite_Tool", ConfigBlocksItems.netheriteHarvestLevel, ConfigBlocksItems.netheriteToolDurability, ConfigBlocksItems.netheriteSpeed, ConfigBlocksItems.netheriteDamageBase, ConfigBlocksItems.netheriteEnchantability);
	public static final ItemArmor.ArmorMaterial NETHERITE_ARMOUR = EnumHelper.addArmorMaterial("Netherite_Armour", ConfigBlocksItems.netheriteArmourDurabilityFactor, new int[]{3, 6, 8, 3}, ConfigBlocksItems.netheriteEnchantability);

	private static final List<Item> initList = new ArrayList<Item>();
	
	static {
		NETHERITE_TOOL.setRepairItem(new ItemStack(ModItems.netherite_ingot));
		NETHERITE_ARMOUR.customCraftingMaterial = ModItems.netherite_ingot;
	}

	public static final Item raw_mutton = initItem(new ItemMuttonRaw());
	public static final Item cooked_mutton = initItem(new ItemMuttonCooked());
	public static final Item prismarine_shard = initItem(new ItemPrismarineShard());
	public static final Item prismarine_crystals = initItem(new ItemPrismarineCrystals());
	public static final Item armour_stand = initItem(new ItemArmorStand());
	public static final Item raw_rabbit = initItem(new ItemRabbitRaw());
	public static final Item cooked_rabbit = initItem(new ItemRabbitCooked());
	public static final Item rabbit_foot = initItem(new ItemRabbitFoot());
	public static final Item rabbit_hide = initItem(new ItemRabbitHide());
	public static final Item rabbit_stew = initItem(new ItemRabbitStew());
	public static final Item beetroot = initItem(new ItemBeetroot());
	public static final Item beetroot_seeds = initItem(new ItemBeetrootSeeds());
	public static final Item beetroot_soup = initItem(new ItemBeetrootSoup());
	public static final Item chorus_fruit = initItem(new ItemChorusFruit());
	public static final Item popped_chorus_fruit = initItem(new ItemPoppedChorusFruit());
	public static final Item tipped_arrow = initItem(new ItemArrowTipped());
	public static final Item lingering_potion = initItem(new ItemLingeringPotion());
	public static final Item dragon_breath = initItem(new ItemDragonBreath());
	public static final Item elytra = initItem(new ItemArmorElytra());
	public static final Item end_crystal = initItem(new ItemEndCrystal());
	
	public static final Item iron_nugget = initItem(new ItemNuggetIron());
	public static final Item raw_ore = initItem(new ItemRawOre(false));
//  public static final Item modded_raw_ore = initItem(new ItemRawOre(true));
	public static final Item netherite_scrap = initItem(new ItemNetherite(0));
	public static final Item netherite_ingot = initItem(new ItemNetherite(1));
	public static final Item netherite_helmet = initItem(new ItemEFRArmour(NETHERITE_ARMOUR, 0, ConfigBlocksItems.netheriteHelmetDurability));
	public static final Item netherite_chestplate = initItem(new ItemEFRArmour(NETHERITE_ARMOUR, 1, ConfigBlocksItems.netheriteChestplateDurability));
	public static final Item netherite_leggings = initItem(new ItemEFRArmour(NETHERITE_ARMOUR, 2, ConfigBlocksItems.netheriteLeggingsDurability));
	public static final Item netherite_boots = initItem(new ItemEFRArmour(NETHERITE_ARMOUR, 3, ConfigBlocksItems.netheriteBootsDurability));
	public static final Item netherite_pickaxe = initItem(new ItemEFRPickaxe(NETHERITE_TOOL, ConfigBlocksItems.netheritePickaxeDurability));
	public static final Item netherite_spade = initItem(new ItemEFRSpade(NETHERITE_TOOL, ConfigBlocksItems.netheriteSpadeDurability));
	public static final Item netherite_axe = initItem(new ItemEFRAxe(NETHERITE_TOOL, ConfigBlocksItems.netheriteAxeDurability));
	public static final Item netherite_hoe = initItem(new ItemEFRHoe(NETHERITE_TOOL, ConfigBlocksItems.netheriteHoeDurability));
	public static final Item netherite_sword = initItem(new ItemEFRSword(NETHERITE_TOOL, ConfigBlocksItems.netheriteSwordDurability));
	public static final Item[] signs = new Item[ModBlocks.woodTypes.length - 1];
	public static final Item totem = initItem(new ItemTotemUndying());
	public static final Item new_dye = initItem(new ItemNewDye());
	public static final Item copper_ingot = initItem(new ItemCopperIngot());
	public static final Item suspicious_stew = initItem(new ItemSuspiciousStew());
	public static final Item sweet_berries = initItem(new ItemSweetBerries());
	public static final Item shulker_shell = initItem(new ItemShulkerShell());
	public static final Item[] boats = new Item[EntityNewBoat.Type.values().length];
	public static final Item[] chest_boats = new Item[EntityNewBoat.Type.values().length];
	public static final Item pigstep_record = initItem(new ItemEtFuturumRecord("pigstep"));
	public static final Item otherside_record = initItem(new ItemEtFuturumRecord("otherside"));
	public static final Item amethyst_shard = initItem(new ItemAmethystShard());
	public static final Item shulker_box_upgrade = initItem(new ItemShulkerBoxUpgrade());

	static {
		for (int i = 0; i < signs.length; i++)
			signs[i] = initItem(new ItemWoodSign(i + 1));
		for (int i = 0; i < boats.length; i++)
			boats[i] = initItem(new ItemNewBoat(EntityNewBoat.Type.byId(i), false));
		for (int i = 0; i < chest_boats.length; i++)
			chest_boats[i] = initItem(new ItemNewBoat(EntityNewBoat.Type.byId(i), true));
	}
	
	private static Item initItem(Item item) {
		if(!(item instanceof IConfigurable) || ((IConfigurable)item).isEnabled())
			initList.add(item);
		return item;
	}
	
	public static void init() {
		for(Item item : initList) {
			String name = item.getUnlocalizedName();
			String[] strings = name.split("\\.");
			String registryName = item instanceof IRegistryName ? ((IRegistryName)item).getRegistryName() : strings[strings.length - 1];
			GameRegistry.registerItem(item, registryName);
		}
	}
}