package ganymedes01.etfuturum.api;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.api.mappings.RegistryMapping;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The registry for which blocks should convert into a deepslate ore. When the game reaches
 * LoaderState.AVAILABLE, the registry cannot be added to. It is highly advised you use
 * the helper methods when getting an ore, or checking of one exists, instead of using {@code getOreMap()},
 * in the case the key changes to something more efficient.
 * <p>
 * The getOre methods return a BlockAndMetadataMapping, which is used to store the block instance
 * and its desired meta value in the same object.
 *
 * @author roadhog360
 * @apiNote Example: {@code DeepslateOreRegistry.addOre(Blocks.iron_ore, ModBlocks.deepslate_iron_ore);}
 * would cause vanilla iron ore to convert into the "deepslate_iron_ore" block when
 * deepslate generates over it.
 */
public class DeepslateOreRegistry {

	private static final Map<Integer, RegistryMapping<Block>> deepslateOres = new HashMap<>();
	/**
	 * Temporarily stores the mapping an input hash is associated with so we can iterate
	 * through it on the game load to copy furnace smelting and OreDict entries.
	 */
	private static final Map<Integer, RegistryMapping<Block>> inputOres = new HashMap<>();

	/**
	 * Adds a block to block pair to the deepslate mapping registry.
	 * Assumes a metadata value of 0.
	 * This is used when Et Futurum deepslate overwrites the specified block.
	 *
	 * @param from The block:0 to overwrite
	 * @param to   The block:0 deepslate changes it to
	 */
	public static void addOre(Block from, Block to) {
		addOre(from, 0, to, 0);
	}

	/**
	 * Adds a block/metadata to block/metadata pair to the deepslate mapping registry.
	 * This is used when Et Futurum deepslate overwrites the specified block.
	 *
	 * @param from     The block to overwrite
	 * @param fromMeta The block's meta to overwrite
	 * @param to       The block deepslate changes it to
	 * @param toMeta   The meta deepslate changes it to
	 */
	public static void addOre(Block from, int fromMeta, Block to, int toMeta) {
		if (((fromMeta < 0 || fromMeta > 15) && fromMeta != OreDictionary.WILDCARD_VALUE) || ((toMeta < 0 || toMeta > 15) && toMeta != OreDictionary.WILDCARD_VALUE)) {
			throw new IllegalArgumentException("Meta must be between 0 and 15 (inclusive)");
		}
		RegistryMapping<Block> inputMapping = new RegistryMapping<>(from, fromMeta);
		deepslateOres.put(inputMapping.hashCode(), new RegistryMapping<>(to, toMeta));
		inputOres.put(inputMapping.hashCode(), inputMapping);
	}

	/**
	 * Adds a block to block pair to the deepslate mapping registry.
	 * This is used when Et Futurum deepslate overwrites the specified block.
	 * <p>
	 * Note this does not actually look at the tags for an item, it just replaces everything in that tag.
	 * CraftTweaker OreDictionary additions/removals may not work. For best results, please restart the game before reporting issues.
	 *
	 * @param oreDict The oreDict tag to overwrite
	 * @param to   The block deepslate changes it to
	 * @param toMeta   The metadata deepslate changes it to
	 */
	public static void addOre(String oreDict, Block to, int toMeta) {
		for (ItemStack ore : OreDictionary.getOres(oreDict)) {
			Block blockToAdd = Block.getBlockFromItem(ore.getItem());
			if (blockToAdd != null) {
				addOre(blockToAdd, ore.getItemDamage(), to, toMeta);
			}
		}
	}

	/**
	 * Adds a block to block pair to the deepslate mapping registry.
	 * This is used when Et Futurum deepslate overwrites the specified block.
	 * <p>
	 * Note this does not actually look at the tags for an item, it just replaces everything in that tag.
	 * CraftTweaker OreDictionary additions/removals may not work. For best results, please restart the game before reporting issues.
	 *
	 * @param oreDict The oreDict tag to overwrite
	 * @param to      The block:0 deepslate changes it to
	 */
	public static void addOre(String oreDict, Block to) {
		addOre(oreDict, to, 0);
	}

	/**
	 * Does deepslate replace this block?
	 * Assumes the input has a meta value of zero.
	 *
	 * @param block
	 * @return
	 */
	public static boolean hasOre(Block block) {
		return hasOre(block, 0);
	}

	/**
	 * Does deepslate replace this block?
	 *
	 * @param block
	 * @return
	 */
	public static boolean hasOre(Block block, int meta) {
		return deepslateOres.containsKey(block.hashCode() + meta);
	}

	/**
	 * Pass in a block, whatever deepslate will replace it with is the return.
	 * Assumes a meta value of 0.
	 *
	 * @param block
	 * @return A mapping containing the deepslate alternative of a block. This is
	 * an instance of the BlockAndMetadataMapping class, containing a variable with
	 * the block instance and the meta data it should be replaced with.
	 */
	public static RegistryMapping<Block> getOre(Block block) {
		return getOre(block, 0);
	}


	/**
	 * Pass in a block, whatever deepslate will replace it with is the return.
	 *
	 * @param block
	 * @param meta
	 * @return A mapping containing the deepslate alternative of the input block. This is
	 * an instance of the BlockAndMetadataMapping class, containing a variable with
	 * the block instance and the meta data it should be replaced with.
	 */
	public static RegistryMapping<Block> getOre(Block block, int meta) {
		return deepslateOres.get(block.hashCode() + meta);
	}

	/**
	 * @return The entire deepslate ore mapping, where the hash for a metadata/block pair is the key.
	 * The key's return value is of the class BlockAndMetadataMapping, which just store
	 * a Block instance, and a metadata value.
	 * <p>
	 * Hashes are used so we don't have to create hundreds of BlockAndMetadataMapping
	 * instances just to compare each block we replace.
	 * <p>
	 * The hash is {@code block.hashCode() + meta}.
	 * <p>
	 * Do not use this to add or get items from the map this way,
	 * in case the key changes.
	 */
	public static Map<Integer, RegistryMapping<Block>> getOreMap() {
		return deepslateOres;
	}

	public static void init() {
		if (ConfigBlocksItems.enableDeepslateOres) { //Copy block settings from deepslate base blocks
			for (Entry<Integer, RegistryMapping<Block>> entry : getOreMap().entrySet()) {

				Block oreNorm = inputOres.get(entry.getKey()).getObject();
				if (Block.blockRegistry.getNameForObject(oreNorm) == null || oreNorm == Blocks.air) continue;
				Block oreDeep = entry.getValue().getObject();
				if (Block.blockRegistry.getNameForObject(oreDeep) == null || oreDeep == Blocks.air) continue;

				ItemStack
						stackNorm = new ItemStack(oreNorm, 1, inputOres.get(entry.getKey()).getMeta()),
						stackDeep = new ItemStack(oreDeep, 1, entry.getValue().getMeta());

				for (String oreName : EtFuturum.getOreStrings(stackNorm)) {
					OreDictionary.registerOre(oreName.replace("Vanillastone", "Deepslate"), stackDeep.copy()); // Yes the .copy() is required!
				}

				if (FurnaceRecipes.smelting().getSmeltingResult(stackNorm) != null) {
					GameRegistry.addSmelting(stackDeep, FurnaceRecipes.smelting().getSmeltingResult(stackNorm), FurnaceRecipes.smelting().func_151398_b(stackNorm));
				}
			}
			inputOres.clear();
		}
	}

}
