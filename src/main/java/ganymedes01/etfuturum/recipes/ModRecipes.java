package ganymedes01.etfuturum.recipes;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.blocks.BlockStoneSlab2;
import ganymedes01.etfuturum.configuration.configs.*;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.entities.EntityNewBoat;
import ganymedes01.etfuturum.items.ItemSuspiciousStew;
import ganymedes01.etfuturum.lib.EnumColour;
import ganymedes01.etfuturum.lib.Reference;
import ganymedes01.etfuturum.recipes.crafting.*;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ModRecipes {

	public static final String[] ore_dyes = new String[]{"dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray", "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"};
	public static final String[] dye_names = new String[]{"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
	public static final String[] woodTypes = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "dark_oak"/*, "crimson", "warped"*/};

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
		if (ConfigBlocksItems.enableExtraVanillaSlabs) {
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

		if (ConfigBlocksItems.replaceOldBoats && ConfigBlocksItems.enableNewBoats) {
			removeFirstRecipeFor(Items.boat);
			Items.boat.setTextureName("minecraft:oak_boat");
			Items.boat.setUnlocalizedName(Utils.getUnlocalisedName("oak_boat"));
		}

		if (ConfigFunctions.enableExtraBurnableBlocks) {
			Blocks.fire.setFireInfo(Blocks.fence_gate, 5, 20);
			Blocks.fire.setFireInfo(Blocks.fence, 5, 20);
			Blocks.fire.setFireInfo(Blocks.deadbush, 60, 100);
		}

		if (ConfigBlocksItems.enableDyedBeds) {
			Items.bed.setUnlocalizedName(Utils.getUnlocalisedName("red_bed"));
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
			OreDictionary.registerOre("nuggetIron", new ItemStack(ModItems.NUGGET_IRON.get()));
		}

		if (ConfigBlocksItems.enableMutton) {
			OreDictionary.registerOre("foodMuttonraw", new ItemStack(ModItems.MUTTON_RAW.get()));
			OreDictionary.registerOre("listAllmeatraw", new ItemStack(ModItems.MUTTON_RAW.get()));
			OreDictionary.registerOre("listAllmuttonraw", new ItemStack(ModItems.MUTTON_RAW.get()));

			OreDictionary.registerOre("foodMuttoncooked", new ItemStack(ModItems.MUTTON_COOKED.get()));
			OreDictionary.registerOre("listAllmeatcooked", new ItemStack(ModItems.MUTTON_COOKED.get()));
			OreDictionary.registerOre("listAllmuttoncooked", new ItemStack(ModItems.MUTTON_COOKED.get()));
		}

		if (ConfigBlocksItems.enablePrismarine) {
			OreDictionary.registerOre("shardPrismarine", new ItemStack(ModItems.PRISMARINE_SHARD.get()));
			OreDictionary.registerOre("crystalPrismarine", new ItemStack(ModItems.PRISMARINE_CRYSTALS.get()));
			OreDictionary.registerOre("blockPrismarine", new ItemStack(ModBlocks.PRISMARINE_BLOCK.get(), 1, OreDictionary.WILDCARD_VALUE));
		}

		if (ConfigBlocksItems.enableDoors) {
			for (int i = 0; i < 5; i++)
				OreDictionary.registerOre("doorWood", new ItemStack(ModBlocks.doors[i]));
		}

		if (ConfigBlocksItems.enableTrapdoors) {
			for (int i = 0; i < 5; i++)
				OreDictionary.registerOre("trapdoorWood", ModBlocks.trapdoors[i]);
		}

		if (ConfigBlocksItems.enableFences) {
			for (int i = 0; i < ModBlocks.fences.length; i++)
				OreDictionary.registerOre("fenceWood", new ItemStack(ModBlocks.fences[i]));
			for (int i = 0; i < ModBlocks.fence_gates.length; i++)
				OreDictionary.registerOre("fenceGateWood", new ItemStack(ModBlocks.fence_gates[i]));
		}

		if (ConfigBlocksItems.enableWoodRedstone) {
			for (int i = 0; i < ModBlocks.buttons.length; i++)
				OreDictionary.registerOre("buttonWood", new ItemStack(ModBlocks.buttons[i]));
			for (int i = 0; i < ModBlocks.pressure_plates.length; i++)
				OreDictionary.registerOre("pressurePlateWood", new ItemStack(ModBlocks.pressure_plates[i]));
		}

		if (ConfigBlocksItems.enableStones) {
			OreDictionary.registerOre("stoneGranite", new ItemStack(ModBlocks.STONE.get(), 1, 1));
			OreDictionary.registerOre("stoneDiorite", new ItemStack(ModBlocks.STONE.get(), 1, 3));
			OreDictionary.registerOre("stoneAndesite", new ItemStack(ModBlocks.STONE.get(), 1, 5));
			OreDictionary.registerOre("stoneGranitePolished", new ItemStack(ModBlocks.STONE.get(), 1, 2));
			OreDictionary.registerOre("stoneDioritePolished", new ItemStack(ModBlocks.STONE.get(), 1, 4));
			OreDictionary.registerOre("stoneAndesitePolished", new ItemStack(ModBlocks.STONE.get(), 1, 6));
		}

		if (ConfigBlocksItems.enableSlimeBlock)
			OreDictionary.registerOre("blockSlime", new ItemStack(ModBlocks.SLIME.get()));

		if (ConfigBlocksItems.enableIronTrapdoor)
			OreDictionary.registerOre("trapdoorIron", ModBlocks.IRON_TRAPDOOR.get());

		if (ConfigBlocksItems.enableBeetroot) {
			OreDictionary.registerOre("cropBeetroot", ModItems.BEETROOT.get());
			OreDictionary.registerOre("listAllseed", ModItems.BEETROOT_SEEDS.get());
			OreDictionary.registerOre("seedBeetroot", ModItems.BEETROOT_SEEDS.get());
		}

		if (ConfigEntities.enableRabbit) {
			OreDictionary.registerOre("foodRabbitraw", new ItemStack(ModItems.RABBIT_RAW.get()));
			OreDictionary.registerOre("listAllmeatraw", new ItemStack(ModItems.RABBIT_RAW.get()));
			OreDictionary.registerOre("listAllrabbitraw", new ItemStack(ModItems.RABBIT_RAW.get()));

			OreDictionary.registerOre("foodRabbitcooked", new ItemStack(ModItems.RABBIT_COOKED.get()));
			OreDictionary.registerOre("listAllmeatcooked", new ItemStack(ModItems.RABBIT_COOKED.get()));
			OreDictionary.registerOre("listAllrabbitcooked", new ItemStack(ModItems.RABBIT_COOKED.get()));
		}

		if (ConfigBlocksItems.enableChorusFruit)
			OreDictionary.registerOre("brickEndStone", ModBlocks.END_BRICKS.get());

		if (ConfigBlocksItems.enableNetherite) {
			OreDictionary.registerOre("ingotNetherite", ModItems.NETHERITE_INGOT.get());
			OreDictionary.registerOre("scrapDebris", ModItems.NETHERITE_SCRAP.get());
			OreDictionary.registerOre("oreDebris", ModBlocks.ANCIENT_DEBRIS.get());
			OreDictionary.registerOre("blockNetherite", ModBlocks.NETHERITE_BLOCK.get());
		}

		for (int i = 0; i < 4; i++) {
			if (ConfigBlocksItems.enableStrippedLogs) {
				OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.LOG_STRIPPED.get(), 1, i));
				OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.LOG2_STRIPPED.get(), 1, i));
				if (ConfigBlocksItems.enableBarkLogs) {
					OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.WOOD_STRIPPED.get(), 1, i));
					OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.WOOD2_STRIPPED.get(), 1, i));
				}
			}
			if (ConfigBlocksItems.enableBarkLogs) {
				OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.BARK.get(), 1, i));
				OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.BARK2.get(), 1, i));
			}
		}

		if (ConfigBlocksItems.enableNewDyes) {
			OreDictionary.registerOre("dye", new ItemStack(ModItems.DYE.get(), 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("dyeWhite", new ItemStack(ModItems.DYE.get(), 1, 0));
			OreDictionary.registerOre("dyeBlue", new ItemStack(ModItems.DYE.get(), 1, 1));
			OreDictionary.registerOre("dyeBrown", new ItemStack(ModItems.DYE.get(), 1, 2));
			OreDictionary.registerOre("dyeBlack", new ItemStack(ModItems.DYE.get(), 1, 3));
		}

		if (ConfigBlocksItems.enableCopper) {
			OreDictionary.registerOre("oreCopper", new ItemStack(ModBlocks.COPPER_ORE.get(), 1, 0));
			OreDictionary.registerOre("ingotCopper", new ItemStack(ModItems.COPPER_INGOT.get(), 1, 0));
			OreDictionary.registerOre("blockCopper", new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, 0));
			OreDictionary.registerOre("blockCopperCut", new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, 4));
		}

		if (ConfigBlocksItems.enableDeepslate) {
			OreDictionary.registerOre("cobblestone", new ItemStack(ModBlocks.COBBLED_DEEPSLATE.get(), 1, 0));
		}

		if (ConfigBlocksItems.enableRawOres) {
			if (ConfigBlocksItems.enableCopper) {
				OreDictionary.registerOre("rawCopper", new ItemStack(ModItems.RAW_ORE.get(), 1, 0));
				OreDictionary.registerOre("blockRawCopper", new ItemStack(ModBlocks.RAW_ORE_BLOCK.get(), 1, 0));
			}
			OreDictionary.registerOre("rawIron", new ItemStack(ModItems.RAW_ORE.get(), 1, 1));
			OreDictionary.registerOre("blockRawIron", new ItemStack(ModBlocks.RAW_ORE_BLOCK.get(), 1, 1));
			OreDictionary.registerOre("rawGold", new ItemStack(ModItems.RAW_ORE.get(), 1, 2));
			OreDictionary.registerOre("blockRawGold", new ItemStack(ModBlocks.RAW_ORE_BLOCK.get(), 1, 2));
		}

		if (ConfigFunctions.registerRawItemAsOre && ConfigBlocksItems.enableRawOres) {
			if (ConfigBlocksItems.enableCopper) {
				OreDictionary.registerOre("oreCopper", new ItemStack(ModItems.RAW_ORE.get(), 1, 0));
			}
			OreDictionary.registerOre("oreIron", new ItemStack(ModItems.RAW_ORE.get(), 1, 1));
			OreDictionary.registerOre("oreGold", new ItemStack(ModItems.RAW_ORE.get(), 1, 2));
		}

		if (ConfigBlocksItems.enablePigstep) {
			OreDictionary.registerOre("record", ModItems.PIGSTEP_RECORD.get());
		}

		if (ConfigBlocksItems.enableOtherside) {
			OreDictionary.registerOre("record", ModItems.OTHERSIDE_RECORD.get());
		}

		if (ConfigBlocksItems.enableAmethyst) {
			OreDictionary.registerOre("gemAmethyst", ModItems.AMETHYST_SHARD.get());
			OreDictionary.registerOre("blockGlassTinted", new ItemStack(ModBlocks.TINTED_GLASS.get(), 1, 0));
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

		if (ConfigFunctions.enableStoneBrickRecipes) {
			addShapelessRecipe(new ItemStack(Blocks.mossy_cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.vine));
			addShapelessRecipe(new ItemStack(Blocks.stonebrick, 1, 1), new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.vine));
			addShapedRecipe(new ItemStack(Blocks.stonebrick, 1, 3), "x", "x", 'x', new ItemStack(Blocks.stone_slab, 1, 5));
			GameRegistry.addSmelting(new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stonebrick, 1, 2), 0.0F);
		}

		if (ConfigBlocksItems.enableSlimeBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.SLIME.get()), "xxx", "xxx", "xxx", 'x', new ItemStack(Items.slime_ball));
			addShapelessRecipe(new ItemStack(Items.slime_ball, 9), ModBlocks.SLIME.get());
		}

		if (ConfigBlocksItems.enableCoarseDirt)
			addShapedRecipe(new ItemStack(ModBlocks.COARSE_DIRT.get(), 4), "xy", "yx", 'x', new ItemStack(Blocks.dirt), 'y', new ItemStack(Blocks.gravel));

		if (ConfigBlocksItems.enableMutton)
			GameRegistry.addSmelting(ModItems.MUTTON_RAW.get(), new ItemStack(ModItems.MUTTON_COOKED.get()), 0.35F);

		if (ConfigBlocksItems.enableIronNugget) {
			addShapedRecipe(new ItemStack(Items.iron_ingot), "xxx", "xxx", "xxx", 'x', "nuggetIron");
			addShapelessRecipe(new ItemStack(ModItems.NUGGET_IRON.get(), 9), "ingotIron");
		}

		if (ConfigBlocksItems.enableIronTrapdoor) {
			addShapedRecipe(new ItemStack(ModBlocks.IRON_TRAPDOOR.get()), "xx", "xx", 'x', "ingotIron");
		}

		if (ConfigBlocksItems.enableStones) {
			// Diorite
			addShapedRecipe(new ItemStack(ModBlocks.STONE.get(), 2, 3), "xy", "yx", 'x', new ItemStack(Blocks.cobblestone), 'y', "gemQuartz");
			addShapedRecipe(new ItemStack(ModBlocks.STONE.get(), 4, 4), "xx", "xx", 'x', "stoneDiorite");
			// Andesite
			addShapelessRecipe(new ItemStack(ModBlocks.STONE.get(), 2, 5), new ItemStack(Blocks.cobblestone), "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.STONE.get(), 4, 6), "xx", "xx", 'x', "stoneAndesite");
			// Granite
			addShapelessRecipe(new ItemStack(ModBlocks.STONE.get(), 2, 1), "gemQuartz", "stoneDiorite");
			addShapedRecipe(new ItemStack(ModBlocks.STONE.get(), 4, 2), "xx", "xx", 'x', "stoneGranite");
			Block[] stairs = new Block[]{ModBlocks.GRANITE_STAIRS.get(), ModBlocks.POLISHED_GRANITE_STAIRS.get(), ModBlocks.DIORITE_STAIRS.get(), ModBlocks.POLISHED_DIORITE_STAIRS.get(), ModBlocks.ANDESITE_STAIRS.get(), ModBlocks.POLISHED_ANDESITE_STAIRS.get()};
			for (int i = 0; i < stairs.length; i++) {
				String dictName = "stone" + StringUtils.capitalize(((BlockStoneSlab2) ModBlocks.STONE_SLAB_2.get()).metaBlocks[(i / 2) * 2]) + (i % 2 == 1 ? "Polished" : "");
				addShapedRecipe(new ItemStack(ModBlocks.STONE_SLAB_2.get(), 6, i), "xxx", 'x', dictName);
				addShapedRecipe(new ItemStack(stairs[i], 4), "x  ", "xx ", "xxx", 'x', dictName);
				if (i % 2 == 1) {
					addShapedRecipe(new ItemStack(ModBlocks.STONE_WALL_2.get(), 6, i == 5 ? 2 : i == 3 ? 1 : 0), "xxx", "xxx", 'x', new ItemStack(ModBlocks.STONE.get(), 1, i));
				}
			}
		}

		if (ConfigBlocksItems.enablePrismarine) {

			addShapedRecipe(new ItemStack(ModBlocks.PRISMARINE_BLOCK.get(), 1, 0), "xx", "xx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.PRISMARINE_BLOCK.get(), 1, 1), "xxx", "xxx", "xxx", 'x', "shardPrismarine");
			addShapedRecipe(new ItemStack(ModBlocks.PRISMARINE_BLOCK.get(), 1, 2), "xxx", "xyx", "xxx", 'x', "shardPrismarine", 'y', "dyeBlack");
			addShapedRecipe(new ItemStack(ModBlocks.SEA_LANTERN.get()), "xyx", "yyy", "xyx", 'x', "shardPrismarine", 'y', "crystalPrismarine");

			addShapedRecipe(new ItemStack(ModBlocks.PRISMARINE_STAIRS.get(), 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.PRISMARINE_BLOCK.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.PRISMARINE_STAIRS_BRICK.get(), 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.PRISMARINE_BLOCK.get(), 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.PRISMARINE_STAIRS_DARK.get(), 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.PRISMARINE_BLOCK.get(), 1, 2));
			GameRegistry.addRecipe(new ItemStack(ModBlocks.PRISMARINE_WALL.get(), 6, 0), "xxx", "xxx", 'x', new ItemStack(ModBlocks.PRISMARINE_BLOCK.get(), 1, 0));

			for (int i = 0; i < 3; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.PRISMARINE_SLAB.get(), 6, i), "xxx", 'x', new ItemStack(ModBlocks.PRISMARINE_BLOCK.get(), 1, i));
			}

			if (ConfigFunctions.enableRecipeForPrismarine && !EtFuturum.hasBotania) {
				addShapedRecipe(new ItemStack(ModItems.PRISMARINE_SHARD.get(), 4), "xy", "zx", 'x', "gemQuartz", 'y', "dyeBlue", 'z', "dyeGreen");
				addShapedRecipe(new ItemStack(ModItems.PRISMARINE_CRYSTALS.get(), 4), "xy", "yx", 'x', "gemQuartz", 'y', "dustGlowstone");
			}
		}

		if (ConfigBlocksItems.enableExtraVanillaSlabs) {
			Block[] metaBlocks = new Block[]{Blocks.stone, Blocks.mossy_cobblestone, Blocks.stonebrick, Blocks.sandstone};
			for (int i = 0; i < metaBlocks.length; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.DOUBLE_STONE_SLAB.get(), 6, i), "xxx", 'x', new ItemStack(metaBlocks[i], 1, i != 0 ? i - 1 : i));
			}
			addShapedRecipe(new ItemStack(Blocks.stone_slab, 6, 0), "xxx", 'x', new ItemStack(ModBlocks.SMOOTH_STONE.get(), 1, 0));
		}


		if (ConfigBlocksItems.enableExtraVanillaStairs) {
			addShapedRecipe(new ItemStack(ModBlocks.STONE_STAIRS.get(), 4), "x  ", "xx ", "xxx", 'x', new ItemStack(Blocks.stone, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.MOSSY_COBBLESTONE_STAIRS.get(), 4), "x  ", "xx ", "xxx", 'x', new ItemStack(Blocks.mossy_cobblestone, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.MOSSY_STONE_BRICK_STAIRS.get(), 4), "x  ", "xx ", "xxx", 'x', new ItemStack(Blocks.stonebrick, 1, 1));
		}

		if (ConfigBlocksItems.enableDoors) {
			int output = ConfigBlocksItems.enableDoorRecipeBuffs ? 3 : 1;
			for (int i = 0; i < ModBlocks.doors.length; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.doors[i], output), "xx", "xx", "xx", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			}
			if (ConfigBlocksItems.enableDoorRecipeBuffs) {
				removeFirstRecipeFor(Items.wooden_door);
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.wooden_door, 3), "xx", "xx", "xx", 'x', "plankWood"));
				removeFirstRecipeFor(Items.iron_door);
				GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.iron_door, 3), "xx", "xx", "xx", 'x', "ingotIron"));
			}
		}

		if (ConfigBlocksItems.enableTrapdoors) {
			for (int i = 0; i < ModBlocks.trapdoors.length; i++) {
				if ((i == 5 && !ConfigBlocksItems.enableCrimsonBlocks) || (i == 6 && !ConfigBlocksItems.enableWarpedBlocks))
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
			addShapedRecipe(new ItemStack(ModBlocks.RED_SANDSTONE.get()), "xx", "xx", 'x', new ItemStack(Blocks.sand, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.RED_SANDSTONE.get(), 1, 1), "x", "x", 'x', new ItemStack(ModBlocks.RED_SANDSTONE_SLAB.get()));
			addShapedRecipe(new ItemStack(ModBlocks.RED_SANDSTONE.get(), 4, 2), "xx", "xx", 'x', new ItemStack(ModBlocks.RED_SANDSTONE.get()));
			addShapedRecipe(new ItemStack(ModBlocks.RED_SANDSTONE_SLAB.get(), 6, 1), "xxx", 'x', new ItemStack(ModBlocks.RED_SANDSTONE.get(), 1, 2));
			addShapedRecipe(new ItemStack(ModBlocks.RED_SANDSTONE_SLAB.get(), 6, 0), "xxx", 'x', new ItemStack(ModBlocks.RED_SANDSTONE.get(), 1, OreDictionary.WILDCARD_VALUE));
			addShapedRecipe(new ItemStack(ModBlocks.RED_SANDSTONE_STAIRS.get(), 4), "x  ", "xx ", "xxx", 'x', ModBlocks.RED_SANDSTONE.get());
			addShapedRecipe(new ItemStack(ModBlocks.RED_SANDSTONE_WALL.get(), 6, 0), "xxx", "xxx", 'x', new ItemStack(ModBlocks.RED_SANDSTONE.get(), 1, 0));
		}

		if (ConfigBlocksItems.enableFences) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.fence, 3), "xyx", "xyx", 'x', "plankWood", 'y', "stickWood"));
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.nether_brick_fence, 6), "xyx", "xyx", 'x', Blocks.nether_brick, 'y', "ingotBrickNether"));

			for (int i = 0; i < ModBlocks.fences.length; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.fences[i], 3), "xyx", "xyx", 'x', new ItemStack(Blocks.planks, 1, i + 1), 'y', "stickWood");
			}

			for (int i = 0; i < ModBlocks.fence_gates.length; i++) {
				addShapedRecipe(new ItemStack(ModBlocks.fence_gates[i]), "yxy", "yxy", 'x', new ItemStack(Blocks.planks, 1, i + 1), 'y', "stickWood");
			}
		}

		if (ConfigBlocksItems.enableBanners) {
			for (EnumColour colour : EnumColour.values()) {
				addShapedRecipe(new ItemStack(ModBlocks.BANNER.get(), 1, colour.getDamage()), "xxx", "xxx", " y ", 'x', new ItemStack(Blocks.wool, 1, colour.getDamage()), 'y', "stickWood");
			}
			GameRegistry.addRecipe(new RecipeDuplicatePattern());
			GameRegistry.addRecipe(new RecipeAddPattern());
		}

		if (ConfigBlocksItems.enableArmourStand) {
			addShapedRecipe(new ItemStack(ModItems.WOODEN_ARMORSTAND.get()), "xxx", " x ", "xyx", 'x', "stickWood", 'y', new ItemStack(Blocks.stone_slab));
		}

		if (ConfigEntities.enableRabbit) {
			addShapedRecipe(new ItemStack(ModItems.RABBIT_STEW.get()), " R ", "CPM", " B ", 'R', new ItemStack(ModItems.RABBIT_COOKED.get()), 'C', Items.carrot, 'P', Items.baked_potato, 'M', Blocks.brown_mushroom, 'B', "bowlWood");
			addShapedRecipe(new ItemStack(ModItems.RABBIT_STEW.get()), " R ", "CPD", " B ", 'R', new ItemStack(ModItems.RABBIT_COOKED.get()), 'C', Items.carrot, 'P', Items.baked_potato, 'D', Blocks.red_mushroom, 'B', "bowlWood");
			GameRegistry.addSmelting(ModItems.RABBIT_RAW.get(), new ItemStack(ModItems.RABBIT_COOKED.get()), 0.35F);
			addShapedRecipe(new ItemStack(Items.leather), "xx", "xx", 'x', ModItems.RABBIT_HIDE.get());
		}

		if (ConfigTweaks.enableOldGravel) {
			addShapedRecipe(new ItemStack(ModBlocks.OLD_GRAVEL.get(), 4), "xy", "yx", 'x', ModBlocks.COARSE_DIRT.get(), 'y', Blocks.gravel);
		}

		if (ConfigBlocksItems.enableSponge) {
			addShapelessRecipe(new ItemStack(ModBlocks.SPONGE.get()), Blocks.sponge);
			addShapelessRecipe(new ItemStack(Blocks.sponge), ModBlocks.SPONGE.get());
			GameRegistry.addSmelting(new ItemStack(ModBlocks.SPONGE.get(), 1, 1), new ItemStack(ModBlocks.SPONGE.get()), 0.0F);
		}

		if (ConfigBlocksItems.enableBeetroot) {
			addShapedRecipe(new ItemStack(ModItems.BEETROOT_SOUP.get()), "xxx", "xxx", " y ", 'x', "cropBeetroot", 'y', "bowlWood");
			addShapelessRecipe(new ItemStack(Items.dye, 1, 1), "cropBeetroot");
		}

		if (ConfigBlocksItems.enableChorusFruit) {
			addShapedRecipe(new ItemStack(ModBlocks.END_BRICK_SLAB.get(), 6), "xxx", 'x', ModBlocks.END_BRICKS.get());
			addShapedRecipe(new ItemStack(ModBlocks.END_BRICK_STAIRS.get(), 4), "x  ", "xx ", "xxx", 'x', ModBlocks.END_BRICKS.get());
			addShapedRecipe(new ItemStack(ModBlocks.END_BRICK_WALL.get(), 6, 0), "xxx", "xxx", 'x', new ItemStack(ModBlocks.END_BRICKS.get(), 1, 0));

			addShapedRecipe(new ItemStack(ModBlocks.PURPUR_BLOCK.get(), 4), "xx", "xx", 'x', ModItems.CHORUS_FRUIT_POPPED.get());
			addShapedRecipe(new ItemStack(ModBlocks.PURPUR_STAIRS.get(), 4), "x  ", "xx ", "xxx", 'x', ModBlocks.PURPUR_BLOCK.get());
			addShapedRecipe(new ItemStack(ModBlocks.PURPUR_SLAB.get(), 6), "xxx", 'x', ModBlocks.PURPUR_BLOCK.get());
			addShapedRecipe(new ItemStack(ModBlocks.PURPUR_PILLAR.get()), "x", "x", 'x', ModBlocks.PURPUR_SLAB.get());
			addShapedRecipe(new ItemStack(ModBlocks.END_BRICKS.get(), 4), "xx", "xx", 'x', Blocks.end_stone);

			GameRegistry.addSmelting(new ItemStack(ModItems.CHORUS_FRUIT.get()), new ItemStack(ModItems.CHORUS_FRUIT_POPPED.get()), 0.0F);
			addShapedRecipe(new ItemStack(ModBlocks.END_ROD.get()), "x", "y", 'x', Items.blaze_rod, 'y', ModItems.CHORUS_FRUIT_POPPED.get());
		}

		if (ConfigBlocksItems.enableCryingObsidian) {
			addShapelessRecipe(new ItemStack(ModBlocks.CRYING_OBSIDIAN.get()), Blocks.obsidian, "gemLapis");
		}

		if (ConfigBlocksItems.enableLingeringPotions) {
			addShapelessRecipe(new ItemStack(ModItems.DRAGON_BREATH.get()), new ItemStack(Items.potionitem, 1, 8195), ModItems.CHORUS_FRUIT.get(), ModItems.CHORUS_FRUIT.get());
		}

		if (ConfigEntities.enableDragonRespawn) {
			addShapedRecipe(new ItemStack(ModItems.END_CRYSTAL.get()), "xxx", "xyx", "xzx", 'x', "blockGlassColorless", 'y', Items.ender_eye, 'z', Items.ghast_tear);
		}

		if (ConfigTweaks.enableRoses) {
			addShapelessRecipe(new ItemStack(Items.dye, 1, 1), ModBlocks.ROSE.get());
			addShapedRecipe(new ItemStack(Blocks.double_plant, 1, 4), "xx", "xx", "xx", 'x', new ItemStack(ModBlocks.ROSE.get()));
			addShapedRecipe(new ItemStack(ModBlocks.ROSE.get(), 12), "xx", 'x', new ItemStack(Blocks.double_plant, 1, 4));
		}

		if (ConfigBlocksItems.enableTippedArrows && ConfigBlocksItems.enableLingeringPotions) {
			RecipeSorter.register(Reference.MOD_ID + ".RecipeTippedArrow", RecipeTippedArrow.class, Category.SHAPED, "after:minecraft:shaped");
			GameRegistry.addRecipe(new RecipeTippedArrow(new ItemStack(ModItems.TIPPED_ARROW.get()), "xxx", "xyx", "xxx", 'x', Items.arrow, 'y', new ItemStack(ModItems.LINGERING_POTION.get(), 1, OreDictionary.WILDCARD_VALUE)));
		}

		if (ConfigBlocksItems.enableDyedBeds) {
			for (int i = 0; i < ModBlocks.beds.length; i++) {
				int j = i == 14 ? 15 : i;
				addShapedRecipe(new ItemStack(ModBlocks.beds[i], 1), "###", "XXX", '#', new ItemStack(Blocks.wool, 1, j), 'X', "plankWood");
				if (i > 0) {
					addShapelessRecipe(new ItemStack(ModBlocks.beds[i], 1), ModBlocks.beds[0], ore_dyes[~j & 15]);
				}
			}
			addShapelessRecipe(new ItemStack(Items.bed, 1), new ItemStack(ModBlocks.beds[0]), ore_dyes[1]);
		}


//      if(!ConfigurationHandler.enableTileReplacement) { // Add recipes for updated tiles if tile replacement is disabled.
//          if (ConfigurationHandler.enableColourfulBeacons) {
//              removeFirstRecipeFor(Blocks.beacon); // Remove recipe for Minecrafts Beacon
//              addShapedRecipe(new ItemStack(ModBlocks.beacon), "GGG", "GSG", "OOO", 'G', Blocks.glass, 'S', Items.nether_star, 'O', Blocks.obsidian);
//              addShapelessRecipe(new ItemStack(ModBlocks.beacon), Blocks.beacon); // Minecraft Beacon -> EtFuturum Beacon
//              addShapelessRecipe(new ItemStack(Blocks.beacon), ModBlocks.beacon); // EtFuturm Beacon -> Minecraft Beacon
//          }
//  
//          if (ConfigurationHandler.enableEnchants) {
//              removeFirstRecipeFor(Blocks.enchanting_table); // Remove recipe for Minecrafts Enchanting Table
//              addShapedRecipe(new ItemStack(ModBlocks.enchantment_table), " B ", "D#D", "###", '#', Blocks.obsidian, 'B', Items.book, 'D', "gemDiamond");
//              addShapelessRecipe(new ItemStack(ModBlocks.enchantment_table), Blocks.enchanting_table); // Minecraft Enchanting Table -> EtFuturum Enchanting Table (For any old leftovers)
//              addShapelessRecipe(new ItemStack(Blocks.enchanting_table), ModBlocks.enchantment_table); // EtFuturum Enchanting Table -> Minecraft Enchanting Table (For when you need to to craft something that has it as a component (ChickenChunks))
//          }
//
//          if (ConfigurationHandler.enableInvertedDaylightSensor) {
//              removeFirstRecipeFor(Blocks.daylight_detector); // Remove recipe for Minecrafts Daylight Sensor
//              addShapedRecipe(new ItemStack(ModBlocks.daylight_sensor), "GGG", "QQQ", "WWW", 'G', "blockGlassColorless", 'Q', "gemQuartz", 'W', "slabWood");
//              addShapelessRecipe(new ItemStack(ModBlocks.daylight_sensor), Blocks.daylight_detector);
//              addShapelessRecipe(new ItemStack(Blocks.daylight_detector), ModBlocks.daylight_sensor);
//          }
//
//          if (ConfigurationHandler.enableBrewingStands) {
//              removeFirstRecipeFor(Items.brewing_stand); //gany pls, Blocks.brewing_stand is invalid
//              addShapedRecipe(new ItemStack(ModBlocks.brewing_stand), " i ", "xxx", 'i', Items.blaze_rod, 'x', "cobblestone");
//              addShapelessRecipe(new ItemStack(ModBlocks.brewing_stand), Items.brewing_stand); //Minecraft Brewing Stand -> EtFuturum Brewing Stand
//              addShapelessRecipe(new ItemStack(Items.brewing_stand), ModBlocks.brewing_stand); //EtFuturum Brewing Stand -> Minecraft Brewing Stand
//          }
//      }

		if (ConfigBlocksItems.enableMagmaBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.MAGMA.get()), "xx", "xx", 'x', new ItemStack(Items.magma_cream));
		}

		if (ConfigBlocksItems.enableNewNetherBricks) {
			addShapedRecipe(new ItemStack(ModBlocks.RED_NETHERBRICK.get(), 1, 0), "xi", "ix", 'x', Items.nether_wart, 'i', "ingotBrickNether");
			addShapedRecipe(new ItemStack(ModBlocks.RED_NETHERBRICK.get(), 1, 2), "x", "x", 'x', new ItemStack(Blocks.stone_slab, 1, 6));
			GameRegistry.addSmelting(new ItemStack(Blocks.nether_brick, 1, 1), new ItemStack(ModBlocks.RED_NETHERBRICK.get()), .1F);
			addShapedRecipe(new ItemStack(ModBlocks.RED_NETHERBRICK_STAIRS.get(), 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.RED_NETHERBRICK.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.RED_NETHERBRICK_SLAB.get(), 6, 0), "xxx", 'x', new ItemStack(ModBlocks.RED_NETHERBRICK.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.RED_NETHER_BRICK_WALL.get(), 6, 0), "xxx", "xxx", 'x', new ItemStack(ModBlocks.RED_NETHERBRICK.get(), 1, 0));
		}

		if (ConfigBlocksItems.enableNetherwartBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.NETHER_WART.get()), "xxx", "xxx", "xxx", 'x', Items.nether_wart);
			addShapelessRecipe(new ItemStack(Items.nether_wart, 9), ModBlocks.NETHER_WART.get());
		}

		if (ConfigBlocksItems.enableBoneBlock) {
			addShapedRecipe(new ItemStack(ModBlocks.BONE.get()), "xxx", "xxx", "xxx", 'x', new ItemStack(Items.dye, 1, 15));
//            addShapelessRecipe(new ItemStack(Items.dye, 9, 15), new ItemStack(ModBlocks.bone_block));
		}

		for (int i = 0; i < ore_dyes.length; i++) {
			if (ConfigBlocksItems.enableConcrete) {
				int dye = ~i & 15;
				addShapelessRecipe(new ItemStack(ModBlocks.CONCRETE_POWDER.get(), 8, i),
						ore_dyes[dye], new ItemStack(Blocks.sand, 1, 0), new ItemStack(Blocks.sand, 1, 0), new ItemStack(Blocks.sand, 1, 0),
						new ItemStack(Blocks.sand, 1, 0), Blocks.gravel, Blocks.gravel, Blocks.gravel, Blocks.gravel);
			}
			if (ConfigBlocksItems.enableGlazedTerracotta)
				GameRegistry.addSmelting(new ItemStack(Blocks.stained_hardened_clay, 1, i), new ItemStack(GameRegistry.findBlock(Reference.MOD_ID, dye_names[i] + "_glazed_terracotta")), 0.1F);
		}

		if (ConfigFunctions.enableRecipeForTotem) {
			addShapedRecipe(new ItemStack(ModItems.TOTEM_OF_UNDYING.get()), "EBE", "GBG", " G ", 'E', "gemEmerald", 'G', "ingotGold", 'B', "blockGold");
		}

		if (ConfigBlocksItems.enableWoodRedstone) {
			for (int i = 0; i < ModBlocks.buttons.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.buttons[i], 1), "x", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			addShapedRecipe(new ItemStack(Blocks.wooden_button, 1), "x", 'x', "plankWood");

			for (int i = 0; i < ModBlocks.pressure_plates.length; i++)
				addShapedRecipe(new ItemStack(ModBlocks.pressure_plates[i], 1), "xx", 'x', new ItemStack(Blocks.planks, 1, i + 1));
			addShapedRecipe(new ItemStack(Blocks.wooden_pressure_plate, 1), "xx", 'x', "plankWood");
		}

		for (int i = 0; i < 6; i++) {
			Block slog = i >= 4 ? ModBlocks.LOG2_STRIPPED.get() : ModBlocks.LOG_STRIPPED.get();
			Block log = i >= 4 ? Blocks.log2 : Blocks.log;
			Block sbark = i >= 4 ? ModBlocks.WOOD2_STRIPPED.get() : ModBlocks.WOOD_STRIPPED.get();
			Block bark = i >= 4 ? ModBlocks.BARK2.get() : ModBlocks.BARK.get();

			if (ConfigBlocksItems.enableStrippedLogs) {
				addShapedRecipe(new ItemStack(Blocks.planks, 4, i), "x", 'x', new ItemStack(slog, 1, i % 4));
				if (ConfigBlocksItems.enableBarkLogs) {
					addShapedRecipe(new ItemStack(Blocks.planks, 4, i), "x", 'x', new ItemStack(sbark, 1, i % 4));
					addShapedRecipe(new ItemStack(sbark, 3, i % 4), "xx", "xx", 'x', new ItemStack(slog, 1, i % 4));
//                  if(ConfigurationHandler.enableCrimsonBlocks && i == 0)
//                      addShapedRecipe(new ItemStack(ModBlocks.crimson_stem, 3, 3), "xx", "xx", 'x', new ItemStack(ModBlocks.crimson_stem, 1, 2));
//                  if(ConfigurationHandler.enableWarpedBlocks && i == 0)
//                      addShapedRecipe(new ItemStack(ModBlocks.warped_stem, 3, 3), "xx", "xx", 'x', new ItemStack(ModBlocks.warped_stem, 1, 2));
				}
			}

			if (ConfigBlocksItems.enableBarkLogs) {
				addShapedRecipe(new ItemStack(Blocks.planks, 4, i), "x", 'x', new ItemStack(bark, 1, i % 4));
				addShapedRecipe(new ItemStack(bark, 3, i % 4), "xx", "xx", 'x', new ItemStack(log, 1, i % 4));
//              if(ConfigurationHandler.enableCrimsonBlocks && i == 0)
//                  addShapedRecipe(new ItemStack(ModBlocks.crimson_stem, 3, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.crimson_stem, 1, 0));
//              if(ConfigurationHandler.enableWarpedBlocks && i == 0)
//                  addShapedRecipe(new ItemStack(ModBlocks.warped_stem, 3, 1), "xx", "xx", 'x', new ItemStack(ModBlocks.warped_stem, 1, 0));
			}
		}

		if (ConfigBlocksItems.enableLantern)
			if (!OreDictionary.getOres("nuggetIron").isEmpty())
				addShapedRecipe(new ItemStack(ModBlocks.LANTERN.get()), "xxx", "xix", "xxx", 'x', "nuggetIron", 'i', Blocks.torch);
			else
				addShapedRecipe(new ItemStack(ModBlocks.LANTERN.get()), "i", "x", 'x', "ingotIron", 'i', Blocks.torch);


//      if (ConfigurationHandler.enableCrimsonBlocks)
//          addShapedRecipe(new ItemStack(ModBlocks.nether_planks, 4, 0), "x", 'x', ModBlocks.crimson_stem);
//      
//      if (ConfigurationHandler.enableWarpedBlocks)
//          addShapedRecipe(new ItemStack(ModBlocks.nether_planks, 4, 1), "x", 'x', ModBlocks.warped_stem);

		if (ConfigBlocksItems.enableBarrel)
			addShapedRecipe(new ItemStack(ModBlocks.BARREL.get()), "xsx", "x x", "xsx", 'x', "plankWood", 's', "slabWood");

		if (ConfigBlocksItems.enableBlueIce)
			addShapedRecipe(new ItemStack(ModBlocks.BLUE_ICE.get()), "xxx", "xxx", "xxx", 'x', Blocks.packed_ice);

		if (ConfigBlocksItems.enableSmoker) {
			addShapedRecipe(new ItemStack(ModBlocks.SMOKER.get()), " l ", "lxl", " l ", 'x', Blocks.furnace, 'l', "logWood");
		}

		if (ConfigBlocksItems.enableBlastFurnace) {
			addShapedRecipe(new ItemStack(ModBlocks.BLAST_FURNACE.get()), "iii", "ixi", "sss", 'x', Blocks.furnace, 'i', "ingotIron", 's', ConfigBlocksItems.enableSmoothStone ? ModBlocks.SMOOTH_STONE.get() : Blocks.stone);
		}

//      if(ConfigurationHandler.enableGenericBuildingBlocks) {
//          GameRegistry.addSmelting(Blocks.stone, new ItemStack(ModBlocks.smooth_stone), .1F);
//      }

		if (ConfigBlocksItems.enableNetherite) {
			GameRegistry.addSmelting(ModBlocks.ANCIENT_DEBRIS.get(), new ItemStack(ModItems.NETHERITE_SCRAP.get()), 2F);
			addShapelessRecipe(new ItemStack(ModItems.NETHERITE_INGOT.get()), new ItemStack(ModItems.NETHERITE_SCRAP.get()), new ItemStack(ModItems.NETHERITE_SCRAP.get()),
					new ItemStack(ModItems.NETHERITE_SCRAP.get()), new ItemStack(ModItems.NETHERITE_SCRAP.get()), "ingotGold", "ingotGold", "ingotGold", "ingotGold");
			addShapedRecipe(new ItemStack(ModBlocks.NETHERITE_BLOCK.get()), "xxx", "xxx", "xxx", 'x', ModItems.NETHERITE_INGOT.get());
			addShapedRecipe(new ItemStack(ModItems.NETHERITE_INGOT.get(), 9), "x", 'x', ModBlocks.NETHERITE_BLOCK.get());
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
		if (ConfigBlocksItems.enableNetherGold)
			GameRegistry.addSmelting(new ItemStack(ModBlocks.NETHER_GOLD_ORE.get()), new ItemStack(Items.gold_ingot), .1F);

		if (ConfigBlocksItems.enableSmoothStone) {
			GameRegistry.addSmelting(new ItemStack(Blocks.stone), new ItemStack(ModBlocks.SMOOTH_STONE.get()), .1F);
			if (Loader.isModLoaded("bluepower")) {
				Item stoneTile = GameRegistry.findItem("bluepower", "stone_tile");
				if (stoneTile != null) {
					addShapedRecipe(new ItemStack(stoneTile, 4), "xx", 'x', new ItemStack(ModBlocks.SMOOTH_STONE.get()));
				}
			}
		}

		if (ConfigBlocksItems.enableSmoothSandstone) {
			GameRegistry.addSmelting(new ItemStack(Blocks.sandstone, 1, 0), new ItemStack(ModBlocks.SMOOTH_SANDSTONE.get()), .1F);
			addShapedRecipe(new ItemStack(ModBlocks.SMOOTH_SANDSTONE_STAIRS.get(), 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.SMOOTH_SANDSTONE.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.SMOOTH_SANDSTONE_SLAB.get(), 6, 0), "xxx", 'x', new ItemStack(ModBlocks.SMOOTH_SANDSTONE.get(), 1, 0));
			if (ConfigBlocksItems.enableRedSandstone) {
				GameRegistry.addSmelting(new ItemStack(ModBlocks.RED_SANDSTONE.get(), 1, 0), new ItemStack(ModBlocks.SMOOTH_RED_SANDSTONE.get()), .1F);
				addShapedRecipe(new ItemStack(ModBlocks.SMOOTH_RED_SANDSTONE_STAIRS.get(), 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.SMOOTH_RED_SANDSTONE.get(), 1, 0));
				addShapedRecipe(new ItemStack(ModBlocks.SMOOTH_RED_SANDSTONE_SLAB.get(), 6, 0), "xxx", 'x', new ItemStack(ModBlocks.SMOOTH_RED_SANDSTONE.get(), 1, 0));
			}
		}

		if (ConfigBlocksItems.enableSmoothQuartz) {
			GameRegistry.addSmelting(new ItemStack(Blocks.quartz_block, 1, 0), new ItemStack(ModBlocks.SMOOTH_QUARTZ.get()), .1F);
			addShapedRecipe(new ItemStack(ModBlocks.SMOOTH_QUARTZ_STAIRS.get(), 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.SMOOTH_QUARTZ.get(), 1, 0));
		}
//      
		if (ConfigBlocksItems.enableQuartzBricks)
			addShapedRecipe(new ItemStack(ModBlocks.QUARTZ_BRICKS.get(), 4, 0), "xx", "xx", 'x', new ItemStack(Blocks.quartz_block, 1, 0));

		if (ConfigBlocksItems.enableNewDyes) {
			if (ConfigBlocksItems.enableLilyOfTheValley) {
				addShapelessRecipe(new ItemStack(ModItems.DYE.get(), 1, 0), new ItemStack(ModBlocks.LILY_OF_THE_VALLEY.get(), 1, 0));
			}
			if (ConfigBlocksItems.enableCornflower) {
				addShapelessRecipe(new ItemStack(ModItems.DYE.get(), 1, 1), new ItemStack(ModBlocks.CORNFLOWER.get(), 1, 0));
			}
			if (ConfigBlocksItems.enableWitherRose) {
				addShapelessRecipe(new ItemStack(ModItems.DYE.get(), 1, 3), new ItemStack(ModBlocks.WITHER_ROSE.get(), 1, 0));
			}
			addShapelessRecipe(new ItemStack(ModItems.DYE.get(), 1, 0), new ItemStack(Items.dye, 1, 15));
			addShapelessRecipe(new ItemStack(ModItems.DYE.get(), 1, 1), new ItemStack(Items.dye, 1, 4));
			addShapelessRecipe(new ItemStack(ModItems.DYE.get(), 1, 2), new ItemStack(Items.dye, 1, 3));
			addShapelessRecipe(new ItemStack(ModItems.DYE.get(), 1, 3), new ItemStack(Items.dye, 1, 0));

		}

		if (ConfigBlocksItems.enableCopper) {
			Block[] stairs = new Block[]{ModBlocks.CUT_COPPER_STAIRS.get(), ModBlocks.EXPOSED_CUT_COPPER_STAIRS.get(), ModBlocks.WEATHERED_CUT_COPPER_STAIRS.get(), ModBlocks.OXIDIZED_CUT_COPPER_STAIRS.get(), ModBlocks.WAXED_CUT_COPPER_STAIRS.get(), ModBlocks.WAXED_EXPOSED_CUT_COPPER_STAIRS.get(), ModBlocks.WAXED_WEATHERED_CUT_COPPER_STAIRS.get(), ModBlocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS.get()};
			addShapedRecipe(new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.COPPER_INGOT.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModItems.COPPER_INGOT.get(), 9), "x", 'x', new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModItems.COPPER_INGOT.get(), 9), "x", 'x', new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, 8));
			GameRegistry.addSmelting(new ItemStack(ModBlocks.COPPER_ORE.get()), new ItemStack(ModItems.COPPER_INGOT.get()), .7F);
			for (int i = 0; i <= 7; i++) {
				int j = i;
				if (i > 3)
					j = i + 4;
				if (j != 11) {
					addShapedRecipe(new ItemStack(ModBlocks.COPPER_BLOCK.get(), 4, j + 4), "xx", "xx", 'x', new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, j));
				}
				if (i != 7) {
					addShapedRecipe(new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 6, i), "xxx", 'x', new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, j + 4));
					addShapedRecipe(new ItemStack(stairs[i], 4), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, j + 4));
				}
				if (OreDictionary.doesOreNameExist("materialWax") || OreDictionary.doesOreNameExist("materialWaxcomb") ||
						OreDictionary.doesOreNameExist("materialHoneycomb") || OreDictionary.doesOreNameExist("itemBeeswax")) {
					addShapelessRecipe(new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i + 8), "itemBeeswax", new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i));
					addShapelessRecipe(new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i + 8), "materialWax", new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i));
					addShapelessRecipe(new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i + 8), "materialWaxcomb", new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i));
					addShapelessRecipe(new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i + 8), "materialHoneycomb", new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i));
					if (i > 3) {
						addShapelessRecipe(new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 1, i), "itemBeeswax", new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 1, i - 4));
						addShapelessRecipe(new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 1, i), "materialWax", new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 1, i - 4));
						addShapelessRecipe(new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 1, i), "materialWaxcomb", new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 1, i - 4));
						addShapelessRecipe(new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 1, i), "materialHoneycomb", new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 1, i - 4));
						addShapelessRecipe(new ItemStack(stairs[i], 1), "itemBeeswax", new ItemStack(stairs[i - 4], 1));
						addShapelessRecipe(new ItemStack(stairs[i], 1), "materialWax", new ItemStack(stairs[i - 4], 1));
						addShapelessRecipe(new ItemStack(stairs[i], 1), "materialWaxcomb", new ItemStack(stairs[i - 4], 1));
						addShapelessRecipe(new ItemStack(stairs[i], 1), "materialHoneycomb", new ItemStack(stairs[i - 4], 1));
					}
				} else {
					addShapelessRecipe(new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i + 8), "slimeball", new ItemStack(ModBlocks.COPPER_BLOCK.get(), 1, i));
					if (i > 3) {
						addShapelessRecipe(new ItemStack(ModBlocks.CUT_COPPER_SLAB.get(), 1, i), "slimeball", new ItemStack(stairs[i], 1, i - 4));
						addShapelessRecipe(new ItemStack(stairs[i], 1), "slimeball", new ItemStack(stairs[i - 4], 1));
					}
				}
			}
		}

		if (ConfigBlocksItems.enableDeepslate) {
			GameRegistry.addSmelting(ModBlocks.COBBLED_DEEPSLATE.get(), new ItemStack(ModBlocks.DEEPSLATE.get()), 0.1F);
			GameRegistry.addSmelting(new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 0), new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 1), 0.1F);
			GameRegistry.addSmelting(new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 2), new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 3), 0.1F);

			addShapedRecipe(new ItemStack(ModBlocks.POLISHED_DEEPSLATE.get(), 4, 0), "xx", "xx", 'x', new ItemStack(ModBlocks.COBBLED_DEEPSLATE.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 4, 0), "xx", "xx", 'x', new ItemStack(ModBlocks.POLISHED_DEEPSLATE.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 4, 2), "xx", "xx", 'x', new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 4), "x", "x", 'x', new ItemStack(ModBlocks.DEEPSLATE_SLAB.get(), 1, 0));

			addShapedRecipe(new ItemStack(ModBlocks.COBBLED_DEEPSLATE_STAIRS.get(), 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.COBBLED_DEEPSLATE.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.POLISHED_DEEPSLATE_STAIRS.get(), 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.POLISHED_DEEPSLATE.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_BRICK_STAIRS.get(), 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_TILE_STAIRS.get(), 4, 0), "x  ", "xx ", "xxx", 'x', new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 2));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_SLAB.get(), 6, 0), "xxx", 'x', new ItemStack(ModBlocks.COBBLED_DEEPSLATE.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_SLAB.get(), 6, 1), "xxx", 'x', new ItemStack(ModBlocks.POLISHED_DEEPSLATE.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_BRICK_SLAB.get(), 4, 0), "xxx", 'x', new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_BRICK_SLAB.get(), 4, 1), "xxx", 'x', new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 2));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_WALL.get(), 6, 0), "xxx", "xxx", 'x', new ItemStack(ModBlocks.COBBLED_DEEPSLATE.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_WALL.get(), 6, 1), "xxx", "xxx", 'x', new ItemStack(ModBlocks.POLISHED_DEEPSLATE.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_BRICK_WALL.get(), 6, 0), "xxx", "xxx", 'x', new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.DEEPSLATE_BRICK_WALL.get(), 6, 1), "xxx", "xxx", 'x', new ItemStack(ModBlocks.DEEPSLATE_BRICKS.get(), 1, 2));
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
			Item result = null;
			if (ConfigBlocksItems.enableCopper) {
				result = ModItems.COPPER_INGOT.get();
			} else if (!OreDictionary.getOres("ingotCopper").isEmpty()) {
				result = OreDictionary.getOres("ingotCopper").get(0).getItem();
			}

			if (result != null) {
				addShapedRecipe(new ItemStack(ModBlocks.RAW_ORE_BLOCK.get(), 1, 0), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.RAW_ORE.get(), 1, 0));
				addShapedRecipe(new ItemStack(ModItems.RAW_ORE.get(), 9, 0), "x", 'x', new ItemStack(ModBlocks.RAW_ORE_BLOCK.get(), 1, 0));
				GameRegistry.addSmelting(new ItemStack(ModItems.RAW_ORE.get(), 1, 0), new ItemStack(result, 1, 0), 0.7F);
			}
			addShapedRecipe(new ItemStack(ModBlocks.RAW_ORE_BLOCK.get(), 1, 1), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.RAW_ORE.get(), 1, 1));
			addShapedRecipe(new ItemStack(ModItems.RAW_ORE.get(), 9, 1), "x", 'x', new ItemStack(ModBlocks.RAW_ORE_BLOCK.get(), 1, 1));
			GameRegistry.addSmelting(new ItemStack(ModItems.RAW_ORE.get(), 1, 1), new ItemStack(Items.iron_ingot, 1, 0), 0.7F);
			addShapedRecipe(new ItemStack(ModBlocks.RAW_ORE_BLOCK.get(), 1, 2), "xxx", "xxx", "xxx", 'x', new ItemStack(ModItems.RAW_ORE.get(), 1, 2));
			addShapedRecipe(new ItemStack(ModItems.RAW_ORE.get(), 9, 2), "x", 'x', new ItemStack(ModBlocks.RAW_ORE_BLOCK.get(), 1, 2));
			GameRegistry.addSmelting(new ItemStack(ModItems.RAW_ORE.get(), 1, 2), new ItemStack(Items.gold_ingot, 1, 0), 0.7F);
		}

		if (ConfigBlocksItems.enableSuspiciousStew) {
			for (int i = 0; i < getStewFlowers().size(); i++) {
				ItemStack stew = new ItemStack(ModItems.SUSPICIOUS_STEW.get(), 1, 0);

				PotionEffect effect = EtFuturum.getSuspiciousStewEffect(getStewFlowers().get(i));

				stew.stackTagCompound = new NBTTagCompound();
				NBTTagList effectsList = new NBTTagList();
				stew.stackTagCompound.setTag(ItemSuspiciousStew.effectsList, effectsList);
				NBTTagCompound potionEffect = new NBTTagCompound();
				potionEffect.setByte(ItemSuspiciousStew.stewEffect, (byte) effect.getPotionID());
				potionEffect.setInteger(ItemSuspiciousStew.stewEffectDuration, effect.getDuration());
				effectsList.appendTag(potionEffect);
				addShapelessRecipe(stew, Blocks.red_mushroom, Blocks.brown_mushroom, Items.bowl, getStewFlowers().get(i));
			}
		}

		if (ConfigBlocksItems.enableNewBoats) {
			for (int i = EntityNewBoat.Type.values().length - 1; i >= 0; i--) {
				addShapedRecipe(new ItemStack(i == 0 && ConfigBlocksItems.replaceOldBoats ? Items.boat : ModItems.boats[i], 1),
						(ConfigBlocksItems.replaceOldBoats ? "x x" : "xyx"), "xxx", 'x', i == 0 ? "plankWood" : new ItemStack(Blocks.planks, 1, i), 'y', new ItemStack(Items.wooden_shovel, 1));
			}
			if (!ConfigBlocksItems.replaceOldBoats) {
				addShapelessRecipe(new ItemStack(Items.boat), ModItems.boats[0]);
				addShapelessRecipe(new ItemStack(ModItems.boats[0]), Items.wooden_shovel, Items.boat);
			}
			for (int i = EntityNewBoat.Type.values().length - 1; i >= 0; i--) {
				addShapedRecipe(new ItemStack(ModItems.chest_boats[i]), "c", "b", 'b', i == 0 && ConfigBlocksItems.replaceOldBoats ? Items.boat : ModItems.boats[i], 'c', Blocks.chest);
			}
		}

		if (ConfigBlocksItems.enableShulkerBoxes) {
			addShapedRecipe(new ItemStack(ModBlocks.SHULKER_BOX.get()), "x", "c", "x", 'x', new ItemStack(ModItems.SHULKER_SHELL.get()), 'c', new ItemStack(Blocks.chest));
			for (int i = 0; i < 16; i++) {
				ItemStack output = new ItemStack(ModBlocks.SHULKER_BOX.get());
				output.setTagCompound(new NBTTagCompound());
				output.getTagCompound().setByte("Color", (byte) (i + 1));
				GameRegistry.addRecipe(new RecipeDyedShulkerBox(output, new Object[]{ModBlocks.SHULKER_BOX.get(), ore_dyes[(byte) ((~i & 15))]}));
			}
			if (EtFuturum.hasIronChest && ConfigBlocksItems.enableShulkerBoxesIronChest) {
				addShapedRecipe(new ItemStack(ModItems.SHULKER_BOX_UPGRADE.get(), 1, 0), "XXX", "XYX", "XXX", 'X', "ingotIron", 'Y', ModItems.SHULKER_SHELL.get());
				addShapedRecipe(new ItemStack(ModItems.SHULKER_BOX_UPGRADE.get(), 1, 1), "XXX", "XYX", "XXX", 'X', "ingotCopper", 'Y', ModItems.SHULKER_SHELL.get());
				addShapedRecipe(new ItemStack(ModItems.SHULKER_BOX_UPGRADE.get(), 1, 2), "XYX", "XXX", "XXX", 'X', "ingotGold", 'Y', "ingotIron");
				addShapedRecipe(new ItemStack(ModItems.SHULKER_BOX_UPGRADE.get(), 1, 3), "GXG", "GYG", "GXG", 'X', "gemDiamond", 'Y', "ingotGold", 'G', "blockGlassColorless");
				addShapedRecipe(new ItemStack(ModItems.SHULKER_BOX_UPGRADE.get(), 1, 4), "XYX", "XXX", "XXX", 'X', Blocks.obsidian, 'Y', "blockGlassColorless");
				addShapedRecipe(new ItemStack(ModItems.SHULKER_BOX_UPGRADE.get(), 1, 5), "XYX", "XXX", "XXX", 'X', "blockGlassColorless", 'Y', Blocks.obsidian);
				addShapedRecipe(new ItemStack(ModItems.SHULKER_BOX_UPGRADE.get(), 1, 6), "GGG", "XYX", "XGX", 'X', "ingotIron", 'Y', "ingotCopper", 'G', "blockGlassColorless");
				addShapedRecipe(new ItemStack(ModItems.SHULKER_BOX_UPGRADE.get(), 1, 7), "XYX", "XXX", "XXX", 'X', "ingotSilver", 'Y', "ingotCopper");
				addShapedRecipe(new ItemStack(ModItems.SHULKER_BOX_UPGRADE.get(), 1, 8), "XYX", "GGG", "XGX", 'X', "ingotGold", 'Y', "ingotSilver", 'G', "blockGlassColorless");
			}
		}

		if (ConfigWorld.tileReplacementMode == -1 && ConfigBlocksItems.enableNewTileEntities) {
			if (ConfigBlocksItems.enableAnvil) {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.ANVIL.get()), "BBB", " I ", "III", 'I', new ItemStack(Items.iron_ingot), 'B', new ItemStack(Blocks.iron_block));
				removeFirstRecipeFor(Blocks.anvil);
			}

			if (ConfigBlocksItems.enableBrewingStands) {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.BREWING_STAND.get()), " B ", "CCC", 'C', new ItemStack(Blocks.cobblestone), 'B', new ItemStack(Items.blaze_rod));
				removeFirstRecipeFor(Blocks.brewing_stand);
			}

			if (ConfigBlocksItems.enableColourfulBeacons) {
				GameRegistry.addRecipe(new ItemStack(ModBlocks.BEACON.get()), "GGG", "GNG", "OOO", 'G', new ItemStack(Blocks.glass), 'N', new ItemStack(Items.nether_star), 'O', new ItemStack(Blocks.obsidian));
				removeFirstRecipeFor(Blocks.beacon);
			}

			if (ConfigBlocksItems.enableEnchantingTable) {
				addShapedRecipe(new ItemStack(ModBlocks.ENCHANTMENT_TABLE.get()), " B ", "D#D", "###", '#', Blocks.obsidian, 'B', Items.book, 'D', "gemDiamond");
				removeFirstRecipeFor(Blocks.enchanting_table); // Remove recipe for Minecrafts Enchanting Table
			}

		}

		if (ConfigBlocksItems.enableExtraVanillaWalls) {
			addShapedRecipe(new ItemStack(ModBlocks.STONE_WALL.get(), 6, 0), "BBB", "BBB", 'B', new ItemStack(Blocks.stonebrick, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.STONE_WALL.get(), 6, 1), "BBB", "BBB", 'B', new ItemStack(Blocks.stonebrick, 1, 1));
			addShapedRecipe(new ItemStack(ModBlocks.STONE_WALL.get(), 6, 2), "BBB", "BBB", 'B', new ItemStack(Blocks.sandstone, 1, 0));
			addShapedRecipe(new ItemStack(ModBlocks.STONE_WALL.get(), 6, 3), "BBB", "BBB", 'B', new ItemStack(Blocks.brick_block, 1, 0));

			//TODO Nether brick wall should be individually toggleable because of Netherlicious
			addShapedRecipe(new ItemStack(ModBlocks.NETHER_BRICK_WALL.get(), 6), "BBB", "BBB", 'B', new ItemStack(Blocks.nether_brick));
		}

		if (ConfigBlocksItems.enableSmithingTable) {
			addShapedRecipe(new ItemStack(ModBlocks.SMITHING_TABLE.get(), 1, 0), "II", "PP", "PP", 'P', "plankWood", 'I', "ingotIron");
		}

		if (ConfigBlocksItems.enableFletchingTable) {
			addShapedRecipe(new ItemStack(ModBlocks.FLETCHING_TABLE.get(), 1, 0), "FF", "PP", "PP", 'P', "plankWood", 'F', new ItemStack(Items.flint, 1, 0));
		}

		if (ConfigBlocksItems.enableStonecutter) {
			addShapedRecipe(new ItemStack(ModBlocks.STONECUTTER.get(), 1, 0), " I ", "SSS", 'S', "stone", 'I', "ingotIron");
		}

		if (ConfigBlocksItems.enableComposter) {
			addShapedRecipe(new ItemStack(ModBlocks.COMPOSTER.get(), 1, 0), "S S", "S S", "SSS", 'S', "slabWood");
		}

		if (ConfigBlocksItems.enableCartographyTable) {
			addShapedRecipe(new ItemStack(ModBlocks.CARTOGRAPHY_TABLE.get(), 1, 0), "pp", "PP", "PP", 'P', "plankWood", 'p', new ItemStack(Items.paper, 1, 0));
		}

		if (ConfigBlocksItems.enableLoom) {
			addShapedRecipe(new ItemStack(ModBlocks.LOOM.get(), 1, 0), "SS", "PP", 'P', "plankWood", 'S', new ItemStack(Items.string, 1, 0));
		}

		if (ConfigBlocksItems.enableAmethyst) {
			addShapedRecipe(new ItemStack(ModBlocks.AMETHYST_BLOCK.get(), 1, 0), "AA", "AA", 'A', "gemAmethyst");
			addShapedRecipe(new ItemStack(ModBlocks.TINTED_GLASS.get(), 2, 0), " A ", "AGA", " A ", 'A', "gemAmethyst", 'G', "blockGlassColorless");
		}

		if (ConfigBlocksItems.enableTarget) {
			addShapedRecipe(new ItemStack(ModBlocks.TARGET.get(), 1, 0), " R ", "RHR", " R ", 'R', "dustRedstone", 'H', Blocks.hay_block);
		}

		if (ConfigMixins.enableObservers) {
			addShapedRecipe(new ItemStack(ModBlocks.OBSERVER.get(), 1, 0), "CCC", "RRQ", "CCC", 'R', "dustRedstone", 'C', "cobblestone", 'Q', "gemQuartz");
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

		if (ConfigBlocksItems.enableLilyOfTheValley) {
			list.add(new ItemStack(ModBlocks.LILY_OF_THE_VALLEY.get(), 1, 0));
		}
		if (ConfigBlocksItems.enableCornflower) {
			list.add(new ItemStack(ModBlocks.CORNFLOWER.get(), 1, 0));
		}
		if (ConfigBlocksItems.enableWitherRose) {
			list.add(new ItemStack(ModBlocks.WITHER_ROSE.get(), 1, 0));
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