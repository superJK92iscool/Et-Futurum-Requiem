package ganymedes01.etfuturum.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.BlockGlazedTerracotta;
import ganymedes01.etfuturum.blocks.BlockNewStone;
import ganymedes01.etfuturum.blocks.BlockStoneSlab2;
import ganymedes01.etfuturum.configuration.ConfigBase;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityNewBoat;
import ganymedes01.etfuturum.items.ItemSuspiciousStew;
import ganymedes01.etfuturum.lib.EnumColour;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.world.generate.BlockAndMetadataMapping;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRecipes {

	public static String[] dyes = new String[] { "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite" };
	//private static final ItemStack _SAND_ = new ItemStack(Blocks.sand), _GRAVEL_ = new ItemStack(Blocks.gravel); // unused variables
	
	
	public static void init() {
		if (ConfigBlocksItems.enableBanners) {
			RecipeSorter.register(Reference.MOD_ID + ".RecipeDuplicatePattern", RecipeDuplicatePattern.class, Category.SHAPELESS, "after:minecraft:shapeless");
			RecipeSorter.register(Reference.MOD_ID + ".RecipeAddPattern", RecipeAddPattern.class, Category.SHAPED, "after:minecraft:shaped");
		}
		RecipeSorter.register("etfuturum:shaped", ShapedEtFuturumRecipe.class, RecipeSorter.Category.SHAPED, "before:minecraft:shaped");
		RecipeSorter.register("etfuturum:shapeless", ShapelessEtFuturumRecipe.class, RecipeSorter.Category.SHAPELESS, "before:minecraft:shapeless");

		registerOreDictionary();
		registerRecipes();
		tweakRecipes();
	}

	private static void tweakRecipes() {
		if (ConfigBlocksItems.enableGenericSlabs) {
			removeFirstRecipeFor(Blocks.stone_slab, 0);
		}
		
		if (ConfigBlocksItems.enableDoors) {
			Items.wooden_door.setMaxStackSize(64);
			Items.iron_door.setMaxStackSize(64);
		}
		
		if (ConfigBlocksItems.enableFences) {
			removeFirstRecipeFor(Blocks.fence);
			removeFirstRecipeFor(Blocks.nether_brick_fence);
		}
		
		if (ConfigBase.replaceOldBoats && ConfigBase.enableNewBoats) {
			removeFirstRecipeFor(Items.boat);
			Items.boat.setTextureName("minecraft:oak_boat");
			Items.boat.setUnlocalizedName(Utils.getUnlocalisedName("oak_boat"));
		}

		if (ConfigBase.enableBurnableBlocks) {
			Blocks.fire.setFireInfo(Blocks.fence_gate, 5, 20);
			Blocks.fire.setFireInfo(Blocks.fence, 5, 20);
			Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
		}
	}

	private static void registerOreDictionary() {
		OreDictionary.registerOre("chestWood", new ItemStack(Blocks.chest));
		OreDictionary.registerOre("bookshelfWood", new ItemStack(Blocks.bookshelf));
		OreDictionary.registerOre("doorWood", new ItemStack(Items.wooden_door));
		OreDictionary.registerOre("trapdoorWood", Blocks.trapdoor);
		OreDictionary.registerOre("fenceWood", new ItemStack(Blocks.fence));
		OreDictionary.registerOre("fenceGateWood", new ItemStack(Blocks.fence_gate));
		OreDictionary.registerOre("buttonWood", new ItemStack(Blocks.wooden_button));
		OreDictionary.registerOre("pressurePlateWood", new ItemStack(Blocks.wooden_pressure_plate));
		OreDictionary.registerOre("doorIron", new ItemStack(Items.iron_door));
		OreDictionary.registerOre("buttonStone", new ItemStack(Blocks.stone_button));
		OreDictionary.registerOre("pressurePlateStone", new ItemStack(Blocks.stone_pressure_plate));
		OreDictionary.registerOre("pressurePlateIron", new ItemStack(Blocks.heavy_weighted_pressure_plate));
		OreDictionary.registerOre("pressurePlateGold", new ItemStack(Blocks.light_weighted_pressure_plate));
		OreDictionary.registerOre("bowlWood", new ItemStack(Items.bowl));

		if (ConfigBlocksItems.enableIronNugget) {
			OreDictionary.registerOre("nuggetIron", new ItemStack(ModItems.iron_nugget));
		}
		
		if (ConfigBlocksItems.enableMutton) {
			OreDictionary.registerOre("foodMuttonraw", new ItemStack(ModItems.raw_mutton));
			OreDictionary.registerOre("listAllmeatraw", new ItemStack(ModItems.raw_mutton));
			OreDictionary.registerOre("listAllmuttonraw", new ItemStack(ModItems.raw_mutton));

			OreDictionary.registerOre("foodMuttoncooked", new ItemStack(ModItems.cooked_mutton));
			OreDictionary.registerOre("listAllmeatcooked", new ItemStack(ModItems.cooked_mutton));
			OreDictionary.registerOre("listAllmuttoncooked", new ItemStack(ModItems.cooked_mutton));
		}

		if (ConfigBlocksItems.enablePrismarine) {
			OreDictionary.registerOre("shardPrismarine", new ItemStack(ModItems.prismarine_shard));
			OreDictionary.registerOre("crystalPrismarine", new ItemStack(ModItems.prismarine_crystals));
			OreDictionary.registerOre("blockPrismarine", new ItemStack(ModBlocks.prismarine, 1, OreDictionary.WILDCARD_VALUE));
		}

		if (ConfigBlocksItems.enableDoors) {
			for (int i=0;i<5;i++)
				OreDictionary.registerOre("doorWood", new ItemStack(ModBlocks.doors[i]));
		}

		if (ConfigBlocksItems.enableTrapdoors) {
			for (int i=0;i<5;i++)
				OreDictionary.registerOre("trapdoorWood", ModBlocks.trapdoors[i]);
		}

		if (ConfigBlocksItems.enableFences) {
			for (int i = 0; i < ModBlocks.fences.length; i++)
				OreDictionary.registerOre("fenceWood", new ItemStack(ModBlocks.fences[i]));
			for (int i = 0; i < ModBlocks.gates.length; i++)
				OreDictionary.registerOre("fenceGateWood", new ItemStack(ModBlocks.gates[i]));
		}

		if (ConfigBlocksItems.enableWoodRedstone) {
			for (int i = 0; i < ModBlocks.buttons.length; i++)
				OreDictionary.registerOre("buttonWood", new ItemStack(ModBlocks.buttons[i]));
			for (int i = 0; i < ModBlocks.pressure_plates.length; i++)
				OreDictionary.registerOre("pressurePlateWood", new ItemStack(ModBlocks.pressure_plates[i]));
		}

		if (ConfigBlocksItems.enableStones) {
			OreDictionary.registerOre("stoneGranite", new ItemStack(ModBlocks.stone, 1, BlockNewStone.GRANITE));
			OreDictionary.registerOre("stoneDiorite", new ItemStack(ModBlocks.stone, 1, BlockNewStone.DIORITE));
			OreDictionary.registerOre("stoneAndesite", new ItemStack(ModBlocks.stone, 1, BlockNewStone.ANDESITE));
			OreDictionary.registerOre("stoneGranitePolished", new ItemStack(ModBlocks.stone, 1, BlockNewStone.POLISHED_GRANITE));
			OreDictionary.registerOre("stoneDioritePolished", new ItemStack(ModBlocks.stone, 1, BlockNewStone.POLISHED_DIORITE));
			OreDictionary.registerOre("stoneAndesitePolished", new ItemStack(ModBlocks.stone, 1, BlockNewStone.POLISHED_ANDESITE));
		}

		if (ConfigBlocksItems.enableSlimeBlock)
			OreDictionary.registerOre("blockSlime", new ItemStack(ModBlocks.slime));

		if (ConfigBlocksItems.enableIronTrapdoor)
			OreDictionary.registerOre("trapdoorIron", ModBlocks.iron_trapdoor);

		if (ConfigBlocksItems.enableBeetroot) {
			OreDictionary.registerOre("cropBeetroot", ModItems.beetroot);
			OreDictionary.registerOre("listAllseed", ModItems.beetroot_seeds);
			OreDictionary.registerOre("seedBeetroot", ModItems.beetroot_seeds);
		}

		if (ConfigBlocksItems.enableRabbit) {
			OreDictionary.registerOre("foodRabbitraw", new ItemStack(ModItems.raw_rabbit));
			OreDictionary.registerOre("listAllmeatraw", new ItemStack(ModItems.raw_rabbit));
			OreDictionary.registerOre("listAllrabbitraw", new ItemStack(ModItems.raw_rabbit));

			OreDictionary.registerOre("foodRabbitcooked", new ItemStack(ModItems.cooked_rabbit));
			OreDictionary.registerOre("listAllmeatcooked", new ItemStack(ModItems.cooked_rabbit));
			OreDictionary.registerOre("listAllrabbitcooked", new ItemStack(ModItems.cooked_rabbit));
		}

		if (ConfigBlocksItems.enableChorusFruit)
			OreDictionary.registerOre("brickEndStone", ModBlocks.end_bricks);
		
		if(ConfigBlocksItems.enableNetherite) {
			OreDictionary.registerOre("ingotNetherite", ModItems.netherite_ingot);
			OreDictionary.registerOre("scrapNetherite", ModItems.netherite_scrap);
			OreDictionary.registerOre("oreNetherite", ModBlocks.ancient_debris);
			OreDictionary.registerOre("blockNetherite", ModBlocks.netherite_block);
		}

		for(int i = 0; i < 4; i++) {
			if (ConfigBlocksItems.enableStrippedLogs) {
				OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.log_stripped, 1, i));
				OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.log2_stripped, 1, i));
				if(ConfigBlocksItems.enableBarkLogs) {
					OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.wood_stripped, 1, i));
					OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.wood2_stripped, 1, i));
				}
			}
			if(ConfigBlocksItems.enableBarkLogs) {
				OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.log_bark, 1, i));
				OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.log2_bark, 1, i));
			}
		}
		
		if(ConfigBlocksItems.enableNewDyes) {
				OreDictionary.registerOre("dyeWhite", new ItemStack(ModItems.new_dye, 1, 0));
				OreDictionary.registerOre("dyeBlue", new ItemStack(ModItems.new_dye, 1, 1));
				OreDictionary.registerOre("dyeBrown", new ItemStack(ModItems.new_dye, 1, 2));
				OreDictionary.registerOre("dyeBlack", new ItemStack(ModItems.new_dye, 1, 3));
		}
		
		if(ConfigBlocksItems.enableCopper) {
			OreDictionary.registerOre("oreCopper", new ItemStack(ModBlocks.copper_ore, 1, 0));
			OreDictionary.registerOre("ingotCopper", new ItemStack(ModItems.copper_ingot, 1, 0));
			OreDictionary.registerOre("blockCopper", new ItemStack(ModBlocks.copper_block, 1, 0));
			OreDictionary.registerOre("blockCopperCut", new ItemStack(ModBlocks.copper_block, 1, 4));
		}

		if(ConfigBlocksItems.enableDeepslate) {
			OreDictionary.registerOre("cobblestone", new ItemStack(ModBlocks.cobbled_deepslate, 1, 0));
		}
		
		if(ConfigBlocksItems.enableRawOres) {
			if(ConfigBlocksItems.enableCopper) {
				OreDictionary.registerOre("rawCopper", new ItemStack(ModItems.raw_ore, 1, 0));
				OreDictionary.registerOre("blockRawCopper", new ItemStack(ModBlocks.raw_ore_block, 1, 0));
			}
			OreDictionary.registerOre("rawIron", new ItemStack(ModItems.raw_ore, 1, 1));
			OreDictionary.registerOre("blockRawIron", new ItemStack(ModBlocks.raw_ore_block, 1, 1));
			OreDictionary.registerOre("rawGold", new ItemStack(ModItems.raw_ore, 1, 2));
			OreDictionary.registerOre("blockRawGold", new ItemStack(ModBlocks.raw_ore_block, 1, 2));
		}
		
		if(ConfigBase.registerRawItemAsOre) {
			if(ConfigBlocksItems.enableCopper) {
				OreDictionary.registerOre("oreCopper", new ItemStack(ModItems.raw_ore, 1, 0));
			}
			OreDictionary.registerOre("oreIron", new ItemStack(ModItems.raw_ore, 1, 1));
			OreDictionary.registerOre("oreGold", new ItemStack(ModItems.raw_ore, 1, 2));
		}
		
//      if(ConfigurationHandler.enableCrimsonBlocks || ConfigurationHandler.enableWarpedBlocks) {
//          OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.nether_planks, 1, 0));
//          OreDictionary.registerOre("plankWood", new ItemStack(ModBlocks.nether_planks, 1, 1));
//          OreDictionary.registerOre("slabWood", new ItemStack(ModBlocks.nether_planks_slab, 1, 0));
//          OreDictionary.registerOre("slabWood", new ItemStack(ModBlocks.nether_planks_slab, 1, 3));
//          OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.crimson_stem, 1, 0));
//          OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.warped_stem, 1, 0));
//          if(ConfigurationHandler.enableBarkLogs) {
//              OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.crimson_stem, 1, 1));
//              OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.warped_stem, 1, 1));
//              if(ConfigurationHandler.enableStrippedLogs) {
//                  OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.crimson_stem, 1, 3));
//                  OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.warped_stem, 1, 3));
//              }
//          }
//          if(ConfigurationHandler.enableStrippedLogs) {
//              OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.crimson_stem, 1, 2));
//              OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.warped_stem, 1, 2));
//          }
//      }
		
//      if (ConfigurationHandler.enableBlackstone)
//          OreDictionary.registerOre("cobblestone", ModBlocks.blackstone);
	}

	private static void registerRecipes() {
		
		if (ConfigBase.enableStoneBrickRecipes) {
			addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.vine));
			addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.vine));
			addShapedRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "x", "x", 'x', new ItemStack(Blocks.stone_slab, 1, 5));
			GameRegistry.addSmelting(new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stonebrick, 1, 2), 0.0F);
		}

		if (ConfigBlocksItems.enableSlimeBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.slime), "xxx", "xxx", "xxx", 'x', new ItemStack(Items.slime_ball));
			addShapelessRecipe(new ItemStack(Items.slime_ball, 9), ModBlocks.slime);
		}

		if (ConfigBlocksItems.enableCoarseDirt)
			addShapedRecipe(new ItemStack(ModBlocks.coarse_dirt, 4), "xy", "yx", 'x', new ItemStack(Blocks.dirt), 'y', new ItemStack(Blocks.gravel));

		if (ConfigBlocksItems.enableMutton)
			GameRegistry.addSmelting(ModItems.raw_mutton, new ItemStack(ModItems.cooked_mutton), 0.35F);
		
		if (ConfigBlocksItems.enableIronNugget) {
			addShapedRecipe(new ItemStack(Items.iron_ingot), "xxx", "xxx", "xxx", 'x', "nuggetIron");
			addShapelessRecipe(new ItemStack(ModItems.iron_nugget, 9), new Object[] { "ingotIron" });
		}

		if (ConfigBlocksItems.enableIronTrapdoor) {
			addShapedRecipe(new ItemStack(ModBlocks.iron_trapdoor), "xx", "xx", 'x', "ingotIron");
		}

		if (ConfigBlocksItems.enableStones) {
			// Diorite
			addShapedRecipe(new ItemStack(ModBlocks.stone, 2, BlockNewStone.DIORITE), "xy", "yx", 'x', new ItemStack(Blocks.cobblestone), 'y', "gemQuartz");
			addShapedRecipe(new ItemStack(ModBlocks.stone, 4, BlockNewStone.POLISHED_DIORITE), "xx", "xx", 'x', "stoneDiorite");
			// Andesite
			addShapelessRecipe(new ItemStack(ModBlocks.stone, 2, BlockNewStone.ANDESITE), new ItemStack(Blocks.cobblestone), "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.stone, 4, BlockNewStone.POLISHED_ANDESITE), "xx", "xx", 'x', "stoneAndesite");
			// Granite
			addShapelessRecipe(new ItemStack(ModBlocks.stone, 2, BlockNewStone.GRANITE), "gemQuartz", "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.stone, 4, BlockNewStone.POLISHED_GRANITE), "xx", "xx", 'x', "stoneGranite");
			Block[] stairs = new Block[] {ModBlocks.granite_stairs, ModBlocks.polished_granite_stairs, ModBlocks.diorite_stairs, ModBlocks.polished_diorite_stairs, ModBlocks.andesite_stairs, ModBlocks.polished_andesite_stairs};
			for(int i = 0; i < stairs.length; i++) {
				String dictName = "stone" + StringUtils.capitalize(((BlockStoneSlab2)ModBlocks.stone_slab).metaBlocks[(i / 2) * 2]) + (i % 2 == 1 ? "Polished" : "");
				addShapedRecipe(new ItemStack(ModBlocks.stone_slab, 6, i), "xxx", 'x', dictName);
				addShapedRecipe(new ItemStack(stairs[i], 4), "x  ", "xx ", "xxx", 'x', dictName);
			}
		}

		if (ConfigBlocksItems.enablePrismarine) {
			int PLAIN = 0;
			int BRICKS = 1;
			int DARK = 2;

			addShapedRecipe(new ItemStack(ModBlocks.prismarine, 1, DARK), "xxx", "xyx", "xxx", 'x', "shardPrismarine", 'y', "dyeBlack");
			addShapedRecipe(new ItemStack(ModBlocks.prismarine, 1, PLAIN), "xx", "xx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.prismarine, 1, BRICKS), "xxx", "xxx", "xxx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.sea_lantern), "xyx", "yyy", "xyx", 'x', "shardPrismarine", 'y', "crystalPrismarine");

			addShapedRecipe(new ItemStack(ModBlocks.rough_prismarine_stairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.prismarine, 1, PLAIN));
			addShapedRecipe(new ItemStack(ModBlocks.prismarine_brick_stairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.prismarine, 1, BRICKS));
			addShapedRecipe(new ItemStack(ModBlocks.dark_prismarine_stairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.prismarine, 1, DARK));
			
			for(int i = 0; i < 3; i++)
				addShapedRecipe(new ItemStack(ModBlocks.prismarine_slab, 6, i), "xxx", 'x', new ItemStack(ModBlocks.prismarine, 1, i));
			
			if (ConfigBase.enableRecipeForPrismarine && !Loader.isModLoaded("Botania")) {
				addShapedRecipe(new ItemStack(ModItems.prismarine_shard, 4), "xy", "zx", 'x', "gemQuartz", 'y', "dyeBlue", 'z', "dyeGreen");
				addShapedRecipe(new ItemStack(ModItems.prismarine_crystals, 4), "xy", "yx", 'x', "gemQuartz", 'y', "dustGlowstone");
			}
		}

		if (ConfigBlocksItems.enableGenericSlabs) {
			Block[] metaBlocks = new Block[] {Blocks.stone, Blocks.mossy_cobblestone, Blocks.stonebrick, Blocks.sandstone};
			for(int i = 0; i < metaBlocks.length; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.generic_slab, 6, i), "xxx", 'x', new ItemStack(metaBlocks[i], 1, i != 0 ? i - 1 : i));
			}
			addShapedRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.smooth_stone, 1, 0));
		}

		
		if (ConfigBlocksItems.enableGenericStairs) {
			addShapedRecipe(new ItemStack(ModBlocks.stone_stairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(Blocks.stone, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.mossy_cobblestone_stairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(Blocks.mossy_cobblestone, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.mossy_stone_brick_stairs, 4), "x  ", "xx ", "xxx", 'x', new ItemStack(Blocks.stonebrick, 1, 1));
		}

		if (ConfigBlocksItems.enableDoors) {
			for (int i = 0; i < ModBlocks.doors.length; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.doors[i], 3), "xx", "xx", "xx", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			}
			removeFirstRecipeFor(Items.wooden_door);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.wooden_door, 3), "xx", "xx", "xx", 'x', "plankWood"));
			removeFirstRecipeFor(Items.iron_door);
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.iron_door, 3), "xx", "xx", "xx", 'x', "ingotIron"));
		}
		
		if(ConfigBlocksItems.enableTrapdoors) {
			for (int i = 0; i < ModBlocks.trapdoors.length; i++) {
				if((i == 5 && !ConfigBlocksItems.enableCrimsonBlocks) || (i == 6 && !ConfigBlocksItems.enableWarpedBlocks))
					continue;
				ItemStack planks = /* i < 5 ? */new ItemStack(Blocks.planks, 1, i + 1)/* : new ItemStack(ModBlocks.nether_planks, 1, i - 5) */;
				addShapedRecipe(new ItemStack(ModBlocks.trapdoors[i], 2), "xxx", "xxx", 'x', planks);
			}
		}

		if (ConfigBlocksItems.enableSigns) {
			for (int i = 0; i < ModItems.signs.length; i++) {
//              if((i == 5 && !EtFuturum.enableCrimsonBlocks) || (i == 6 && !EtFuturum.enableWarpedBlocks))
//                  continue;
				ItemStack planks = /* i < 5 ? */new ItemStack(Blocks.planks, 1, i + 1)/* : new ItemStack(ModBlocks.nether_planks, 1, i - 5)*/;
				addShapedRecipe(new ItemStack(ModItems.signs[i], 3), "xxx", "xxx", " y ", 'x', planks, 'y', "stickWood");
			}
		}
		
		if (ConfigBlocksItems.enableRedSandstone) {
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone), "xx", "xx", 'x', new ItemStack(Blocks.sand, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone, 1, 1), "x", "x", 'x', new ItemStack(ModBlocks.red_sandstone_slab));
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone, 4, 2), "xx", "xx", 'x', new ItemStack(ModBlocks.red_sandstone));
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone_slab, 6, 1), "xxx", 'x', new ItemStack(ModBlocks.red_sandstone, 1, 2));
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone_slab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.red_sandstone, 1, OreDictionary.WILDCARD_VALUE));
			addShapedRecipe(new ItemStack(ModBlocks.red_sandstone_stairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.red_sandstone);
		}

		if (ConfigBlocksItems.enableFences) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence, 3), "xyx", "xyx", 'x', "plankWood", 'y', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.nether_brick_fence, 6), "xyx", "xyx", 'x', Blocks.nether_brick, 'y', "ingotBrickNether"));
			
			for (int i = 0; i < ModBlocks.fences.length; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.fences[i], 3), "xyx", "xyx", 'x', new ItemStack(Blocks.planks, 1, i + 1), 'y', "stickWood");
			}

			for (int i = 0; i < ModBlocks.gates.length; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.gates[i]), "yxy", "yxy", 'x', new ItemStack(Blocks.planks, 1, i + 1), 'y', "stickWood");
			}
		}

		if (ConfigBlocksItems.enableBanners) {
			for (EnumColour colour : EnumColour.values()) {
				addShapedRecipe(new ItemStack(ModBlocks.banner, 1, colour.getDamage()), new Object[] { "xxx", "xxx", " y ", 'x', new ItemStack(Blocks.wool, 1, colour.getDamage()), 'y', "stickWood" });
			}
			GameRegistry.addRecipe(new RecipeDuplicatePattern());
			GameRegistry.addRecipe(new RecipeAddPattern());
		}

		if (ConfigBase.enableArmourStand) {
			addShapedRecipe(new ItemStack(ModItems.armour_stand), "xxx", " x ", "xyx", 'x', "stickWood", 'y', new ItemStack(Blocks.stone_slab));
		}

		if (ConfigBlocksItems.enableRabbit) {
			addShapedRecipe(new ItemStack(ModItems.rabbit_stew), " R ", "CPM", " B ", 'R', new ItemStack(ModItems.cooked_rabbit), 'C', Items.carrot, 'P', Items.baked_potato, 'M', Blocks.brown_mushroom, 'B', "bowlWood");
			addShapedRecipe(new ItemStack(ModItems.rabbit_stew), " R ", "CPD", " B ", 'R', new ItemStack(ModItems.cooked_rabbit), 'C', Items.carrot, 'P', Items.baked_potato, 'D', Blocks.red_mushroom, 'B', "bowlWood");
			GameRegistry.addSmelting(ModItems.raw_rabbit, new ItemStack(ModItems.cooked_rabbit), 0.35F);
			addShapedRecipe(new ItemStack(Items.leather), "xx", "xx", 'x', ModItems.rabbit_hide);
		}

		if (ConfigBlocksItems.enableOldGravel) {
			addShapedRecipe(new ItemStack(ModBlocks.old_gravel, 4), "xy", "yx", 'x', ModBlocks.coarse_dirt, 'y', Blocks.gravel);
		}

		if (ConfigBlocksItems.enableSponge) {
			addShapelessRecipe(new ItemStack(ModBlocks.sponge), Blocks.sponge);
			addShapelessRecipe(new ItemStack(Blocks.sponge), ModBlocks.sponge);
			GameRegistry.addSmelting(new ItemStack(ModBlocks.sponge, 1, 1), new ItemStack(ModBlocks.sponge), 0.0F);
		}

		if (ConfigBlocksItems.enableBeetroot) {
			addShapedRecipe(new ItemStack(ModItems.beetroot_soup), "xxx", "xxx", " y ", 'x', "cropBeetroot", 'y', "bowlWood");
			addShapelessRecipe(new ItemStack(Items.dye, 1, 1), "cropBeetroot");
		}

		if (ConfigBlocksItems.enableChorusFruit) {
			addShapedRecipe(new ItemStack(ModBlocks.purpur_block, 4), "xx", "xx", 'x', ModItems.popped_chorus_fruit);
			addShapedRecipe(new ItemStack(ModBlocks.purpur_stairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.purpur_block);
			addShapedRecipe(new ItemStack(ModBlocks.purpur_slab, 6), "xxx", 'x', ModBlocks.purpur_block);
			addShapedRecipe(new ItemStack(ModBlocks.purpur_pillar), "x", "x", 'x', ModBlocks.purpur_slab);
			addShapedRecipe(new ItemStack(ModBlocks.end_bricks), "xx", "xx", 'x', Blocks.end_stone);
			addShapedRecipe(new ItemStack(ModBlocks.end_brick_slab, 6), "xxx", 'x', ModBlocks.end_bricks);
			addShapedRecipe(new ItemStack(ModBlocks.end_brick_stairs, 4), "x  ", "xx ", "xxx", 'x', ModBlocks.end_bricks);
			GameRegistry.addSmelting(new ItemStack(ModItems.chorus_fruit), new ItemStack(ModItems.popped_chorus_fruit), 0.0F);
			addShapedRecipe(new ItemStack(ModBlocks.end_rod), "x", "y", 'x', Items.blaze_rod, 'y', ModItems.popped_chorus_fruit);
		}

		if (ConfigBlocksItems.enableCryingObsidian) {
			addShapelessRecipe(new ItemStack(ModBlocks.crying_obsidian), Blocks.obsidian, "gemLapis");
		}

		if (ConfigBlocksItems.enableLingeringPotions) {
			addShapelessRecipe(new ItemStack(ModItems.dragon_breath), new ItemStack(Items.potionitem, 1, 8195), ModItems.chorus_fruit, ModItems.chorus_fruit);
		}

		if (ConfigBase.enableDragonRespawn) {
			addShapedRecipe(new ItemStack(ModItems.end_crystal), "xxx", "xyx", "xzx", 'x', "blockGlassColorless", 'y', Items.ender_eye, 'z', Items.ghast_tear);
		}

		if (ConfigBlocksItems.enableRoses) {
			addShapelessRecipe(new ItemStack(Items.dye, 1, 1), ModBlocks.rose);
			addShapedRecipe(new ItemStack(Blocks.double_plant, 1, 4), "xx", "xx", "xx", 'x', new ItemStack(ModBlocks.rose));
			addShapedRecipe(new ItemStack(ModBlocks.rose, 12), "xx", 'x', new ItemStack(Blocks.double_plant, 1, 4));
		}

		if (ConfigBlocksItems.enableTippedArrows && ConfigBlocksItems.enableLingeringPotions) {
			RecipeSorter.register(Reference.MOD_ID + ".RecipeTippedArrow", RecipeTippedArrow.class, Category.SHAPED, "after:minecraft:shaped");
			GameRegistry.addRecipe(new RecipeTippedArrow(new ItemStack(ModItems.tipped_arrow), "xxx", "xyx", "xxx", 'x', Items.arrow, 'y', new ItemStack(ModItems.lingering_potion, 1, OreDictionary.WILDCARD_VALUE)));
		}


//		if(!ConfigurationHandler.enableTileReplacement) { // Add recipes for updated tiles if tile replacement is disabled.
//			if (ConfigurationHandler.enableColourfulBeacons) {
//				removeFirstRecipeFor(Blocks.beacon); // Remove recipe for Minecrafts Beacon
//				addShapedRecipe(new ItemStack(ModBlocks.beacon), "GGG", "GSG", "OOO", 'G', Blocks.glass, 'S', Items.nether_star, 'O', Blocks.obsidian);
//				addShapelessRecipe(new ItemStack(ModBlocks.beacon), Blocks.beacon); // Minecraft Beacon -> EtFuturum Beacon
//				addShapelessRecipe(new ItemStack(Blocks.beacon), ModBlocks.beacon); // EtFuturm Beacon -> Minecraft Beacon
//			}
//	
//			if (ConfigurationHandler.enableEnchants) {
//				removeFirstRecipeFor(Blocks.enchanting_table); // Remove recipe for Minecrafts Enchanting Table
//				addShapedRecipe(new ItemStack(ModBlocks.enchantment_table), " B ", "D#D", "###", '#', Blocks.obsidian, 'B', Items.book, 'D', "gemDiamond");
//				addShapelessRecipe(new ItemStack(ModBlocks.enchantment_table), Blocks.enchanting_table); // Minecraft Enchanting Table -> EtFuturum Enchanting Table (For any old leftovers)
//				addShapelessRecipe(new ItemStack(Blocks.enchanting_table), ModBlocks.enchantment_table); // EtFuturum Enchanting Table -> Minecraft Enchanting Table (For when you need to to craft something that has it as a component (ChickenChunks))
//			}
//
//			if (ConfigurationHandler.enableInvertedDaylightSensor) {
//				removeFirstRecipeFor(Blocks.daylight_detector); // Remove recipe for Minecrafts Daylight Sensor
//				addShapedRecipe(new ItemStack(ModBlocks.daylight_sensor), "GGG", "QQQ", "WWW", 'G', "blockGlassColorless", 'Q', "gemQuartz", 'W', "slabWood");
//				addShapelessRecipe(new ItemStack(ModBlocks.daylight_sensor), Blocks.daylight_detector);
//				addShapelessRecipe(new ItemStack(Blocks.daylight_detector), ModBlocks.daylight_sensor);
//			}
//
//			if (ConfigurationHandler.enableBrewingStands) {
//				removeFirstRecipeFor(Items.brewing_stand); //gany pls, Blocks.brewing_stand is invalid
//				addShapedRecipe(new ItemStack(ModBlocks.brewing_stand), " i ", "xxx", 'i', Items.blaze_rod, 'x', "cobblestone");
//				addShapelessRecipe(new ItemStack(ModBlocks.brewing_stand), Items.brewing_stand); //Minecraft Brewing Stand -> EtFuturum Brewing Stand
//				addShapelessRecipe(new ItemStack(Items.brewing_stand), ModBlocks.brewing_stand); //EtFuturum Brewing Stand -> Minecraft Brewing Stand
//			}
//		}

		if (ConfigBlocksItems.enableMagmaBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.magma_block), "xx", "xx", 'x', new ItemStack(Items.magma_cream) );
		}

		if (ConfigBlocksItems.enableNewNetherBricks) {
			addShapedRecipe(new ItemStack(ModBlocks.new_nether_brick, 1, 0), "xi", "ix", 'x', Items.nether_wart, 'i', "ingotBrickNether");
			addShapedRecipe(new ItemStack(ModBlocks.new_nether_brick, 1, 2), "x", "x", 'x', new ItemStack(Blocks.stone_slab, 1, 6));
			GameRegistry.addSmelting(new ItemStack(Blocks.nether_brick, 1, 1), new ItemStack(ModBlocks.new_nether_brick), .1F);
			addShapedRecipe(new ItemStack(ModBlocks.red_nether_brick_stairs, 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.new_nether_brick, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.red_nether_brick_slab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.new_nether_brick, 1, 0));
		}

		if (ConfigBlocksItems.enableNetherwartBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.nether_wart_block), "xxx", "xxx", "xxx", 'x', Items.nether_wart);
			addShapelessRecipe(new ItemStack(Items.nether_wart, 9), ModBlocks.nether_wart_block);
		}

		if (ConfigBlocksItems.enableBoneBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.bone_block), "xxx", "xxx", "xxx", 'x', new ItemStack(Items.dye, 1, 15));
//            addShapelessRecipe(new ItemStack(Items.dye, 9, 15), new ItemStack(ModBlocks.bone_block));
		}

		for (int i = 0; i < dyes.length; i++) {
			if (ConfigBlocksItems.enableConcrete) {
					int dye = ~i & 15;
					addShapelessRecipe(new ItemStack(ModBlocks.concrete_powder, 8, i), 
							new Object[]{dyes[dye], new ItemStack(Blocks.sand, 1, 0), new ItemStack(Blocks.sand, 1, 0), new ItemStack(Blocks.sand, 1, 0), 
									new ItemStack(Blocks.sand, 1, 0), Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel});
			}
			if(ConfigBlocksItems.enableGlazedTerracotta)
				GameRegistry.addSmelting(new ItemStack(Blocks.stained_hardened_clay, 1, i), new ItemStack(GameRegistry.findBlock(Reference.MOD_ID, BlockGlazedTerracotta.subBlock[i] + "_glazed_terracotta")), 0.1F);
		}
		
		if (ConfigBase.enableRecipeForTotem) {
			addShapedRecipe(new ItemStack(ModItems.totem), "EBE", "GBG", " G ", 'E', "gemEmerald", 'G', "ingotGold", 'B', "blockGold" );
		}
		
		if (ConfigBlocksItems.enableWoodRedstone) {
			for (int i = 0; i < ModBlocks.buttons.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.buttons[i], 1), "x", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			addShapedRecipe(new ItemStack(Blocks.wooden_button, 1), "x", 'x', "plankWood");
			
			for (int i = 0; i < ModBlocks.pressure_plates.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.pressure_plates[i], 1), "xx", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			addShapedRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), "xx", 'x', "plankWood");
		}

		for(int i = 0; i < 6; i++) {
			Block slog = i >= 4 ? ModBlocks.log2_stripped : ModBlocks.log_stripped;
			Block log = i >= 4 ? Blocks.log2 : Blocks.log;
			Block sbark = i >= 4 ? ModBlocks.wood2_stripped : ModBlocks.wood_stripped;
			Block bark = i >= 4 ? ModBlocks.log2_bark : ModBlocks.log_bark;
			
			if (ConfigBlocksItems.enableStrippedLogs) {
				addShapedRecipe(new ItemStack(Blocks.planks, 4, i), "x", 'x', new ItemStack(slog, 1, i % 4));
				if(ConfigBlocksItems.enableBarkLogs) {
					addShapedRecipe(new ItemStack(Blocks.planks, 4, i), "x", 'x', new ItemStack(sbark, 1, i % 4));
					addShapedRecipe(new ItemStack(sbark, 3, i % 4), "xx", "xx", 'x', new ItemStack(slog, 1, i % 4));
//                  if(ConfigurationHandler.enableCrimsonBlocks && i == 0)
//                      addShapedRecipe(new ItemStack(ModBlocks.crimson_stem, 3, 3), "xx", "xx", 'x', new ItemStack(ModBlocks.crimson_stem, 1, 2));
//                  if(ConfigurationHandler.enableWarpedBlocks && i == 0)
//                      addShapedRecipe(new ItemStack(ModBlocks.warped_stem, 3, 3), "xx", "xx", 'x', new ItemStack(ModBlocks.warped_stem, 1, 2));
				}
			}

			if(ConfigBlocksItems.enableBarkLogs) {
				addShapedRecipe(new ItemStack(Blocks.planks, 4, 4), "x", 'x', new ItemStack(bark, 1, i % 4));
				addShapedRecipe(new ItemStack(bark, 3, i % 4), "xx", "xx", 'x', new ItemStack(log, 1, i % 4));
//              if(ConfigurationHandler.enableCrimsonBlocks && i == 0)
//                  addShapedRecipe(new ItemStack(ModBlocks.crimson_stem, 3, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.crimson_stem, 1, 0));
//              if(ConfigurationHandler.enableWarpedBlocks && i == 0)
//                  addShapedRecipe(new ItemStack(ModBlocks.warped_stem, 3, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.warped_stem, 1, 0));
			}
		}
		
		if (ConfigBlocksItems.enableLantern)
			if(!OreDictionary.getOres("nuggetIron").isEmpty())
				addShapedRecipe(new ItemStack(ModBlocks.lantern), "xxx", "xix", "xxx", 'x', "nuggetIron", 'i', Blocks.torch);
			else
				addShapedRecipe(new ItemStack(ModBlocks.lantern), "i", "x", 'x', "ingotIron", 'i', Blocks.torch);
				
		
//      if (ConfigurationHandler.enableCrimsonBlocks)
//          addShapedRecipe(new ItemStack(ModBlocks.nether_planks, 4, 0), "x", 'x', ModBlocks.crimson_stem);
//      
//      if (ConfigurationHandler.enableWarpedBlocks)
//          addShapedRecipe(new ItemStack(ModBlocks.nether_planks, 4, 1), "x", 'x', ModBlocks.warped_stem);
		
		if (ConfigBlocksItems.enableBarrel)
			addShapedRecipe(new ItemStack(ModBlocks.barrel), "xsx", "x x", "xsx", 'x', "plankWood", 's', "slabWood");
		
		if(ConfigBlocksItems.enableBlueIce)
			addShapedRecipe(new ItemStack(ModBlocks.blue_ice), "xxx", "xxx", "xxx", 'x', Blocks.packed_ice);
		
		if(ConfigBlocksItems.enableSmoker) {
			addShapedRecipe(new ItemStack(ModBlocks.smoker), " l ", "lxl", " l ", 'x', Blocks.furnace, 'l', "logWood");
		}
		
		if(ConfigBlocksItems.enableBlastFurnace) {
			addShapedRecipe(new ItemStack(ModBlocks.blast_furnace), "iii", "ixi", "sss", 'x', Blocks.furnace, 'i', "ingotIron", 's', ConfigBlocksItems.enableSmoothStone ? ModBlocks.smooth_stone : Blocks.stone);
		}
		
//      if(ConfigurationHandler.enableGenericBuildingBlocks) {
//          GameRegistry.addSmelting(Blocks.stone, new ItemStack(ModBlocks.smooth_stone), .1F);
//      }
		
		if(ConfigBlocksItems.enableNetherite) {
			GameRegistry.addSmelting(ModBlocks.ancient_debris, new ItemStack(ModItems.netherite_scrap), 2F);
			if(ConfigBlocksItems.enableBlastFurnace) {
				BlastFurnaceRecipes.smelting().addRecipe(ModBlocks.ancient_debris, new ItemStack(ModItems.netherite_scrap), 2F);
			}
			addShapelessRecipe(new ItemStack(ModItems.netherite_ingot), new ItemStack(ModItems.netherite_scrap), new ItemStack(ModItems.netherite_scrap),
			new ItemStack(ModItems.netherite_scrap), new ItemStack(ModItems.netherite_scrap), "ingotGold", "ingotGold", "ingotGold", "ingotGold");
			addShapedRecipe(new ItemStack(ModBlocks.netherite_block), "xxx", "xxx", "xxx", 'x', ModItems.netherite_ingot);
			addShapedRecipe(new ItemStack(ModItems.netherite_ingot, 9), "x", 'x', ModBlocks.netherite_block);
		}
		
		if(ConfigBlocksItems.enableNetherite) {
			Item[][] items = new Item[][] {
				{Items.diamond_helmet, Items.diamond_chestplate, Items.diamond_leggings, Items.diamond_boots, Items.diamond_pickaxe, Items.diamond_axe, Items.diamond_hoe, Items.diamond_shovel, Items.diamond_sword},
				{ModItems.netherite_helmet, ModItems.netherite_chestplate, ModItems.netherite_leggings, ModItems.netherite_boots, ModItems.netherite_pickaxe, ModItems.netherite_axe, ModItems.netherite_hoe, ModItems.netherite_spade, ModItems.netherite_sword}};
				for(int i = 0; i < items[0].length; i++) {
					addShapelessRecipe(new ItemStack(items[1][i]), "ingotNetherite", items[0][i]);
				}
		}
		
//      if (ConfigurationHandler.enableBasalt)
//          addShapedRecipe(new ItemStack(ModBlocks.basalt, 4, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.basalt, 1, 0));
//      
//      if(ConfigurationHandler.enableCrimsonBlocks)
//          addShapedRecipe(new ItemStack(ModBlocks.nether_planks_slab, 6), "xxx", 'x', new ItemStack(ModBlocks.nether_planks, 1, 0));
//      
//      if(ConfigurationHandler.enableWarpedBlocks)
//          addShapedRecipe(new ItemStack(ModBlocks.nether_planks_slab, 6, 3), "xxx", 'x', new ItemStack(ModBlocks.nether_planks, 1, 1));
//
		if(ConfigBlocksItems.enableNetherGold)
			GameRegistry.addSmelting(new ItemStack(ModBlocks.nether_gold_ore), new ItemStack(Items.gold_ingot), .1F);
		
		if(ConfigBlocksItems.enableSmoothStone)
			GameRegistry.addSmelting(new ItemStack(Blocks.stone), new ItemStack(ModBlocks.smooth_stone), .1F);
		
		if(ConfigBlocksItems.enableSmoothSandstone) {
			GameRegistry.addSmelting(new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(ModBlocks.smooth_sandstone), .1F);
			addShapedRecipe(new ItemStack(ModBlocks.smooth_sandstone_stairs, 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.smooth_sandstone, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.smooth_sandstone_slab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.smooth_sandstone, 1, 0));
			if(ConfigBlocksItems.enableRedSandstone) {
				GameRegistry.addSmelting(new ItemStack(ModBlocks.red_sandstone, 1, 0), new ItemStack(ModBlocks.smooth_red_sandstone), .1F);
				addShapedRecipe(new ItemStack(ModBlocks.smooth_red_sandstone_stairs, 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.smooth_red_sandstone, 1, 0));
				addShapedRecipe(new ItemStack(ModBlocks.smooth_red_sandstone_slab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.smooth_red_sandstone, 1, 0));
			}
		}
		
		if(ConfigBlocksItems.enableSmoothQuartz) {
			GameRegistry.addSmelting(new ItemStack(Blocks.quartz_block, 1, 0), new ItemStack(ModBlocks.smooth_quartz), .1F);
			addShapedRecipe(new ItemStack(ModBlocks.smooth_quartz_stairs, 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.smooth_quartz, 1, 0));
		}
//      
		if(ConfigBlocksItems.enableQuartzBricks)
			addShapedRecipe(new ItemStack(ModBlocks.quartz_bricks), "xx", "xx", 'x', new ItemStack(Blocks.quartz_block, 1, 0));
		
		if(ConfigBlocksItems.enableNewDyes) {
			if(ConfigBlocksItems.enableLilyOfTheValley) {
				addShapelessRecipe(new ItemStack(ModItems.new_dye, 1, 0), new ItemStack(ModBlocks.lily_of_the_valley, 1, 0));
			}
			if(ConfigBlocksItems.enableCornflower) {
				addShapelessRecipe(new ItemStack(ModItems.new_dye, 1, 1), new ItemStack(ModBlocks.cornflower, 1, 0));
			}
			if(ConfigBlocksItems.enableWitherRose) {
				addShapelessRecipe(new ItemStack(ModItems.new_dye, 1, 3), new ItemStack(ModBlocks.wither_rose, 1, 0));
			}
			addShapelessRecipe(new ItemStack(ModItems.new_dye, 1, 0), new ItemStack(Items.dye, 1, 15));
			addShapelessRecipe(new ItemStack(ModItems.new_dye, 1, 1), new ItemStack(Items.dye, 1, 4));
			addShapelessRecipe(new ItemStack(ModItems.new_dye, 1, 2), new ItemStack(Items.dye, 1, 3));
			addShapelessRecipe(new ItemStack(ModItems.new_dye, 1, 3), new ItemStack(Items.dye, 1, 0));
			
		}
		
		if(ConfigBlocksItems.enableCopper) {
			Block[] stairs = new Block[] {ModBlocks.cut_copper_stairs, ModBlocks.exposed_cut_copper_stairs, ModBlocks.weathered_cut_copper_stairs, ModBlocks.oxidized_cut_copper_stairs, ModBlocks.waxed_cut_copper_stairs, ModBlocks.waxed_exposed_cut_copper_stairs, ModBlocks.waxed_weathered_cut_copper_stairs, ModBlocks.waxed_oxidized_cut_copper_stairs};
			addShapedRecipe(new ItemStack(ModBlocks.copper_block, 1), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.copper_ingot, 1, 0));
			addShapedRecipe(new ItemStack(ModItems.copper_ingot, 9), "x", 'x', new ItemStack(ModBlocks.copper_block, 1, 0));
			addShapedRecipe(new ItemStack(ModItems.copper_ingot, 9), "x", 'x', new ItemStack(ModBlocks.copper_block, 1, 8));
			GameRegistry.addSmelting(new ItemStack(ModBlocks.copper_ore), new ItemStack(ModItems.copper_ingot), .7F);
			for(int i = 0; i <= 7; i++) {
				int j = i;
				if(i > 3)
					j = i + 4;
				if(j != 11) {
					addShapedRecipe(new ItemStack(ModBlocks.copper_block, 4, j + 4), "xx", "xx", 'x', new ItemStack(ModBlocks.copper_block, 1, j));
				}
				if(i != 7) {
					addShapedRecipe(new ItemStack(ModBlocks.cut_copper_slab, 6, i), "xxx", 'x', new ItemStack(ModBlocks.copper_block, 1, j + 4));
					addShapedRecipe(new ItemStack(stairs[i], 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.copper_block, 1, j + 4));
				}
				if(OreDictionary.doesOreNameExist("materialWax") || OreDictionary.doesOreNameExist("materialWaxcomb") || OreDictionary.doesOreNameExist("itemBeeswax")) {
					addShapelessRecipe(new ItemStack(ModBlocks.copper_block, 1, i + 8), "itemBeeswax", new ItemStack(ModBlocks.copper_block, 1, i));
					addShapelessRecipe(new ItemStack(ModBlocks.copper_block, 1, i + 8), "materialWax", new ItemStack(ModBlocks.copper_block, 1, i));
					addShapelessRecipe(new ItemStack(ModBlocks.copper_block, 1, i + 8), "materialWaxcomb", new ItemStack(ModBlocks.copper_block, 1, i));
					if(i > 3) {
						addShapelessRecipe(new ItemStack(ModBlocks.cut_copper_slab, 1, i), "itemBeeswax", new ItemStack(ModBlocks.cut_copper_slab, 1, i - 4));
						addShapelessRecipe(new ItemStack(ModBlocks.cut_copper_slab, 1, i), "materialWax", new ItemStack(ModBlocks.cut_copper_slab, 1, i - 4));
						addShapelessRecipe(new ItemStack(ModBlocks.cut_copper_slab, 1, i), "materialWaxcomb", new ItemStack(ModBlocks.cut_copper_slab, 1, i - 4));
						addShapelessRecipe(new ItemStack(stairs[i], 1), "itemBeeswax", new ItemStack(stairs[i - 4], 1));
						addShapelessRecipe(new ItemStack(stairs[i], 1), "materialWax", new ItemStack(stairs[i - 4], 1));
						addShapelessRecipe(new ItemStack(stairs[i], 1), "materialWaxcomb", new ItemStack(stairs[i - 4], 1));
					}
				} else {
					addShapelessRecipe(new ItemStack(ModBlocks.copper_block, 1, i + 8), "slimeball", new ItemStack(ModBlocks.copper_block, 1, i));
					if(i > 3) {
						addShapelessRecipe(new ItemStack(ModBlocks.cut_copper_slab, 1, i), "slimeball", new ItemStack(stairs[i], 1, i - 4));
						addShapelessRecipe(new ItemStack(stairs[i], 1), "slimeball", new ItemStack(stairs[i - 4], 1));
					}
				}
			}
		}
		
		if(ConfigBlocksItems.enableDeepslate) {
			GameRegistry.addSmelting(ModBlocks.cobbled_deepslate, new ItemStack(ModBlocks.deepslate), 0.1F);
			GameRegistry.addSmelting(new ItemStack(ModBlocks.deepslate_bricks, 1, 0), new ItemStack(ModBlocks.deepslate_bricks, 1, 1), 0.1F);
			GameRegistry.addSmelting(new ItemStack(ModBlocks.deepslate_bricks, 1, 2), new ItemStack(ModBlocks.deepslate_bricks, 1, 3), 0.1F);
			
			addShapedRecipe(new ItemStack(ModBlocks.polished_deepslate, 4, 0), "xx", "xx", 'x', new ItemStack(ModBlocks.cobbled_deepslate, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.deepslate_bricks, 4, 0), "xx", "xx", 'x', new ItemStack(ModBlocks.polished_deepslate, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.deepslate_bricks, 4, 2), "xx", "xx", 'x', new ItemStack(ModBlocks.deepslate_bricks, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.deepslate_bricks, 1, 4), "x", "x", 'x', new ItemStack(ModBlocks.deepslate_slab, 1, 0));

			addShapedRecipe(new ItemStack(ModBlocks.cobbled_deepslate_stairs, 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.cobbled_deepslate, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.polished_deepslate_stairs, 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.polished_deepslate, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.deepslate_brick_stairs, 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.deepslate_bricks, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.deepslate_tile_stairs, 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.deepslate_bricks, 1, 2));
			addShapedRecipe(new ItemStack(ModBlocks.deepslate_slab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.cobbled_deepslate, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.deepslate_slab, 6, 1), "xxx", 'x', new ItemStack(ModBlocks.polished_deepslate, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.deepslate_brick_slab, 4, 0), "xxx", 'x', new ItemStack(ModBlocks.deepslate_bricks, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.deepslate_brick_slab, 4, 1), "xxx", 'x', new ItemStack(ModBlocks.deepslate_bricks, 1, 2));
		}
		
//      if(ConfigurationHandler.enableBlackstone) {
//          GameRegistry.addSmelting(new ItemStack(ModBlocks.blackstone, 1, 3), new ItemStack(ModBlocks.blackstone, 1, 4), .1F);
//          GameRegistry.addSmelting(new ItemStack(ModBlocks.gilded_blackstone), new ItemStack(Items.gold_ingot), 1F);
//          addShapedRecipe(new ItemStack(ModBlocks.blackstone, 4, 2), "x", "x", 'x', new ItemStack(ModBlocks.blackstone_slab, 1, 3));
//          addShapedRecipe(new ItemStack(ModBlocks.blackstone, 4, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.blackstone, 1, 0));
//          addShapedRecipe(new ItemStack(ModBlocks.blackstone, 4, 3), "xx", "xx", 'x', new ItemStack(ModBlocks.blackstone, 1, 1));
//          addShapedRecipe(new ItemStack(ModBlocks.blackstone_slab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.blackstone, 1, 0));
//          addShapedRecipe(new ItemStack(ModBlocks.blackstone_slab, 6, 3), "xxx", 'x', new ItemStack(ModBlocks.blackstone, 1, 1));
//          addShapedRecipe(new ItemStack(ModBlocks.blackstone_slab, 6, 6), "xxx", 'x', new ItemStack(ModBlocks.blackstone, 1, 3));
//          if(ConfigurationHandler.enableStairs) {
//              addShapedRecipe(new ItemStack(ModBlocks.blackstone_stairs, 4, 1), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.blackstone, 1, 0));
//              addShapedRecipe(new ItemStack(ModBlocks.polished_blackstone_stairs, 4, 1), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.blackstone, 1, 1));
//              addShapedRecipe(new ItemStack(ModBlocks.polished_blackstone_brick_stairs, 4, 1), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.blackstone, 1, 3));
//          }
//      }
		
		if (ConfigBlocksItems.enableRawOres) {
			if(ConfigBlocksItems.enableCopper) {
				addShapedRecipe(new ItemStack(ModBlocks.raw_ore_block, 1, 0), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.raw_ore, 1, 0));
				addShapedRecipe(new ItemStack(ModItems.raw_ore, 9, 0), "x", 'x', new ItemStack(ModBlocks.raw_ore_block, 1, 0));
				GameRegistry.addSmelting(new ItemStack(ModItems.raw_ore, 1, 0), new ItemStack(ModItems.copper_ingot, 1, 0), 0.7F);
			}
			addShapedRecipe(new ItemStack(ModBlocks.raw_ore_block, 1, 1), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.raw_ore, 1, 1));
			addShapedRecipe(new ItemStack(ModItems.raw_ore, 9, 1), "x", 'x', new ItemStack(ModBlocks.raw_ore_block, 1, 1));
			GameRegistry.addSmelting(new ItemStack(ModItems.raw_ore, 1, 1), new ItemStack(Items.iron_ingot, 1, 0), 0.7F);
			addShapedRecipe(new ItemStack(ModBlocks.raw_ore_block, 1, 2), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.raw_ore, 1, 2));
			addShapedRecipe(new ItemStack(ModItems.raw_ore, 9, 2), "x", 'x', new ItemStack(ModBlocks.raw_ore_block, 1, 2));
			GameRegistry.addSmelting(new ItemStack(ModItems.raw_ore, 1, 2), new ItemStack(Items.gold_ingot, 1, 0), 0.7F);
		}
		
		if(ConfigBlocksItems.enableSuspiciousStew) {
			for(int i = 0; i < getStewFlowers().size(); i++) {
				ItemStack stew = new ItemStack(ModItems.suspicious_stew, 1, 0);
				
				PotionEffect effect = EtFuturum.getSuspiciousStewEffect(getStewFlowers().get(i));
				
				stew.stackTagCompound = new NBTTagCompound();
				NBTTagList effectsList = new NBTTagList();
				stew.stackTagCompound.setTag(ItemSuspiciousStew.effectsList, effectsList);
				NBTTagCompound potionEffect = new NBTTagCompound();
				potionEffect.setByte(ItemSuspiciousStew.stewEffect, (byte)effect.getPotionID());
				potionEffect.setInteger(ItemSuspiciousStew.stewEffectDuration, effect.getDuration());
				effectsList.appendTag(potionEffect);
				addShapelessRecipe(stew, Blocks.red_mushroom, Blocks.brown_mushroom, Items.bowl, getStewFlowers().get(i));
			}
		}
		
		if(ConfigBase.enableNewBoats) {
			for(int i = EntityNewBoat.Type.values().length - 1; i >= 0; i--) {
				addShapedRecipe(new ItemStack(i == 0 && ConfigBase.replaceOldBoats ? Items.boat : ModItems.boats[i], 1),
						(ConfigBase.replaceOldBoats ? "x x" : "xyx"), "xxx", 'x', i == 0 ? "plankWood" : new ItemStack(Blocks.planks, 1, i), 'y', new ItemStack(Items.wooden_shovel, 1));
			}
			if(!ConfigBase.replaceOldBoats) {
				addShapelessRecipe(new ItemStack(Items.boat), ModItems.boats[0]);
				addShapelessRecipe(new ItemStack(ModItems.boats[0]), Items.wooden_shovel, Items.boat);
			}
		}
	}
	
	public static void initDeepslate() {
		if (ConfigBlocksItems.enableDeepslateOres) { //Copy block settings from deepslate base blocks
			for (Entry<BlockAndMetadataMapping, BlockAndMetadataMapping> entry : EtFuturum.deepslateOres.entrySet()) {
				Block oreNorm = entry.getKey().getOre();
				if (oreNorm == null || oreNorm == Blocks.air) continue;
				Block oreDeep = entry.getValue().getOre();
				if (oreDeep == null || oreDeep == Blocks.air) continue;
				ItemStack
				stackNorm = new ItemStack(oreNorm, 1, entry.getKey().getMeta()),
				stackDeep = new ItemStack(oreDeep, 1, entry.getValue().getMeta());
				
				if (((oreNorm instanceof IConfigurable) && !((IConfigurable)oreNorm).isEnabled()) || ((oreDeep instanceof IConfigurable) && !((IConfigurable)oreDeep).isEnabled()))
					return;
				for (int i : OreDictionary.getOreIDs(stackNorm)) {
					String oreName = OreDictionary.getOreName(i);
					for(int k = 0; k < OreDictionary.getOres(oreName).size(); k++) {
						if (ItemStack.areItemStacksEqual(OreDictionary.getOres(oreName).get(k), stackNorm)) {
							OreDictionary.registerOre(oreName.replace("Vanillastone", "Deepslate"), stackDeep.copy()); // Yes the .copy() is required!
						}
					}
				}
				
				if (FurnaceRecipes.smelting().getSmeltingResult(stackNorm) != null) {
					GameRegistry.addSmelting(stackDeep, FurnaceRecipes.smelting().getSmeltingResult(stackNorm), FurnaceRecipes.smelting().func_151398_b(stackNorm));
				}
			}
		}
	}
	
	private static List<ItemStack> getStewFlowers() {
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		list.add(new ItemStack(Blocks.red_flower, 1, 2));
		list.add(new ItemStack(Blocks.red_flower, 1, 3));
		list.add(new ItemStack(Blocks.red_flower, 1, 1));
		list.add(new ItemStack(Blocks.yellow_flower, 1));
		list.add(new ItemStack(Blocks.red_flower, 1, 8));
		list.add(new ItemStack(Blocks.red_flower, 1, 0));
		list.add(new ItemStack(Blocks.red_flower, 1, 4));
		list.add(new ItemStack(Blocks.red_flower, 1, 5));
		list.add(new ItemStack(Blocks.red_flower, 1, 6));
		list.add(new ItemStack(Blocks.red_flower, 1, 7));
		
		if(ConfigBlocksItems.enableLilyOfTheValley) {
			list.add(new ItemStack(ModBlocks.lily_of_the_valley, 1, 0));
		}
		if(ConfigBlocksItems.enableCornflower) {
			list.add(new ItemStack(ModBlocks.cornflower, 1, 0));
		}
		if(ConfigBlocksItems.enableWitherRose) {
			list.add(new ItemStack(ModBlocks.wither_rose, 1, 0));
		}
		
		return list;
}

	private static void addShapedRecipe(ItemStack output, Object... objects) {
		GameRegistry.addRecipe(new ShapedEtFuturumRecipe(output, objects));
	}

	private static void addShapelessRecipe(ItemStack output, Object... objects) {
		GameRegistry.addRecipe(new ShapelessEtFuturumRecipe(output, objects));
	}

	private static void removeFirstRecipeFor(Block block) {
		removeFirstRecipeFor(Item.getItemFromBlock(block));
	}

	private static void removeFirstRecipeFor(Block block, int meta) {
		removeFirstRecipeFor(Item.getItemFromBlock(block), meta);
	}

	private static void removeFirstRecipeFor(Item item) {
		removeFirstRecipeFor(item, -1);
	}
	
	private static void removeFirstRecipeFor(Item item, int meta) {
		for (Object recipe : CraftingManager.getInstance().getRecipeList())
			if (recipe != null) {
				ItemStack stack = ((IRecipe) recipe).getRecipeOutput();
				if (stack != null && stack.getItem() == item && (meta == -1 || meta == stack.getItemDamage())) {
					CraftingManager.getInstance().getRecipeList().remove(recipe);
					return;
				}
			}
	}

}