package ganymedes01.etfuturum.api;

import com.google.common.collect.Lists;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.Tags;
import ganymedes01.etfuturum.blocks.BlockBerryBush;
import ganymedes01.etfuturum.blocks.BlockChorusFlower;
import ganymedes01.etfuturum.compat.ExternalContent;
import ganymedes01.etfuturum.core.utils.Logger;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import roadhog360.hogutils.api.hogtags.HogTagsHelper;

import java.util.List;

@Deprecated
public class BeePlantRegistry {
	/// Deprecated; Use HogUtils tagging
	///
	/// Tags: `minecraft:bee_foods` (item) and `minecraft:bee_attractive` (block)
	@Deprecated
	public static void addFlower(Block block, int meta) {
		HogTagsHelper.BlockTags.addTags(block, meta, "minecraft:bee_attractive");
		Item item = Item.getItemFromBlock(block);
		if(item != null) {
			HogTagsHelper.ItemTags.addTags(item, meta, "minecraft:bee_foods");
		}
	}

	/// Deprecated; Use HogUtils tagging
	@Deprecated
	public static void addCrop(Block block) {
		if (!(block instanceof IGrowable)) {
			Logger.warn("Bee crops can only be instance of IGrowable; this entry will do nothing!");
		}
		HogTagsHelper.BlockTags.addTags(block, OreDictionary.WILDCARD_VALUE, "minecraft:bee_growables");
	}

	/// Deprecated; Use HogUtils tagging
	///
	/// Tags: `minecraft:bee_foods` (item) and `minecraft:bee_attractive` (block)
	@Deprecated
	public static void removeFlower(Block block, int meta) {
		HogTagsHelper.BlockTags.removeTags(block, meta, "minecraft:bee_attractive");
		Item item = Item.getItemFromBlock(block);
		if(item != null) {
			HogTagsHelper.ItemTags.removeTags(item, meta, "minecraft:bee_foods");
		}
	}

	/// Deprecated; Use HogUtils tagging
	@Deprecated
	public static void removeCrop(Block block) {
		HogTagsHelper.BlockTags.removeTags(block, OreDictionary.WILDCARD_VALUE, "minecraft:bee_growables");
	}

	/// Deprecated; Use HogUtils tagging
	/// Returns `TRUE` if this is either a bee food OR bee flower.
	/// This is because the previous registry did not have separate lists for bee breedables, and pollinateable flowers.
	///
	/// Tags: `minecraft:bee_foods` (item) and `minecraft:bee_attractive` (block)
	@Deprecated
	public static boolean isFlower(Block block, int meta) {
		Item item = Item.getItemFromBlock(block);
		if(item != null && HogTagsHelper.ItemTags.getTags(item, meta).contains("minecraft:bee_foods")) {
			return true;
		}
		return HogTagsHelper.BlockTags.getTags(block, meta).contains("minecraft:bee_attractive");
	}

	/// Deprecated; Use HogUtils tagging
	@Deprecated
	public static boolean isCrop(Block block) {
		return HogTagsHelper.BlockTags.getTags(block, OreDictionary.WILDCARD_VALUE).contains("minecraft:bee_growables");
	}

	@SuppressWarnings("unchecked")
	public static void init() {
		for (Block block : (Iterable<Block>) Block.blockRegistry) {
			if (block instanceof BlockFlower || block instanceof BlockChorusFlower) {
				addFlower(block, OreDictionary.WILDCARD_VALUE);
				HogTagsHelper.BlockTags.addTags(block, "minecraft:bee_attractive");
			}
			if (block instanceof BlockCrops || block instanceof BlockStem || block instanceof BlockBerryBush) {
				addCrop(block);
				//TODO: Add cave vines as a pollinatable crop, when they get added
			}
		}
		for (Item item : (Iterable<Item>) Item.itemRegistry) {
			HogTagsHelper.ItemTags.addTags(item, "minecraft:bee_foods");
		}

		HogTagsHelper.ItemTags.addTags(Item.getItemFromBlock(Blocks.double_plant), 0, "minecraft:bee_foods");
		HogTagsHelper.ItemTags.addTags(Item.getItemFromBlock(Blocks.double_plant), 1, "minecraft:bee_foods");
		HogTagsHelper.ItemTags.addTags(Item.getItemFromBlock(Blocks.double_plant), 4, "minecraft:bee_foods");
		HogTagsHelper.ItemTags.addTags(Item.getItemFromBlock(Blocks.double_plant), 5, "minecraft:bee_foods");
		HogTagsHelper.BlockTags.addTags(Blocks.double_plant, 0, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(Blocks.double_plant, 1, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(Blocks.double_plant, 4, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(Blocks.double_plant, 5, "minecraft:bee_attractive");

		HogTagsHelper.ItemTags.addTags(ModBlocks.AZALEA.getItem(), 0, "minecraft:bee_foods");
		HogTagsHelper.BlockTags.addTags(ModBlocks.AZALEA.get(), 1, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(ModBlocks.AZALEA.get(), 9, "minecraft:bee_attractive");

		HogTagsHelper.ItemTags.addTags(ModBlocks.AZALEA_LEAVES.getItem(), 1, "minecraft:bee_foods");
		HogTagsHelper.BlockTags.addTags(ModBlocks.AZALEA_LEAVES.get(), 1, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(ModBlocks.AZALEA_LEAVES.get(), 5, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(ModBlocks.AZALEA_LEAVES.get(), 9, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(ModBlocks.AZALEA_LEAVES.get(), 13, "minecraft:bee_attractive");

		//Mangrove propagule
		HogTagsHelper.ItemTags.addTags(ModBlocks.SAPLING.getItem(), 0, "minecraft:bee_foods");
		HogTagsHelper.BlockTags.addTags(ModBlocks.SAPLING.get(), 0, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(ModBlocks.SAPLING.get(), 8, "minecraft:bee_attractive");

		//Cherry leaves
		HogTagsHelper.ItemTags.addTags(ModBlocks.LEAVES.getItem(), 1, "minecraft:bee_foods");
		HogTagsHelper.BlockTags.addTags(ModBlocks.LEAVES.get(), 1, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(ModBlocks.LEAVES.get(), 5, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(ModBlocks.LEAVES.get(), 9, "minecraft:bee_attractive");
		HogTagsHelper.BlockTags.addTags(ModBlocks.LEAVES.get(), 13, "minecraft:bee_attractive");

		HogTagsHelper.ItemTags.addTags(ModBlocks.PINK_PETALS.getItem(), "minecraft:bee_foods");
		HogTagsHelper.BlockTags.addTags(ModBlocks.PINK_PETALS.get(), "minecraft:bee_attractive");

		HogTagsHelper.BlockTags.addTags(ExternalContent.Blocks.CFB_CAMPFIRE.get(), Tags.MOD_ID + ":bee_hive_fumigators");
		HogTagsHelper.BlockTags.addTags(ExternalContent.Blocks.CFB_SOUL_CAMPFIRE.get(), Tags.MOD_ID + ":bee_hive_fumigators");
		HogTagsHelper.BlockTags.addTags(ExternalContent.Blocks.CFB_FOXFIRE_CAMPFIRE.get(), Tags.MOD_ID + ":bee_hive_fumigators");
		HogTagsHelper.BlockTags.addTags(ExternalContent.Blocks.CFB_SHADOW_CAMPFIRE.get(), Tags.MOD_ID + ":bee_hive_fumigators");

		HogTagsHelper.BlockTags.addTags(ExternalContent.Blocks.BAMBOO_CAMPFIRE.get(), Tags.MOD_ID + ":bee_hive_fumigators");
		HogTagsHelper.BlockTags.addTags(ExternalContent.Blocks.ECRU_LEAVES_FIRE.get(), Tags.MOD_ID + ":bee_hive_fumigators");
		HogTagsHelper.BlockTags.addTags(ExternalContent.Blocks.THAUMCRAFT_AIRY.get(), Tags.MOD_ID + ":bee_hive_fumigators");
		//TODO: And spore blossoms, when added
	}
}
