package ganymedes01.etfuturum.core.utils.structurenbt;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BlockStateConverter {

	public static final BlockStateConverter DEFAULT_INSTANCE = new BlockStateConverter();

	public Map<Integer, BlockStateContainer> processPalette(ForgeDirection dir, Set<Pair<Integer, NBTTagCompound>> paletteNBT) {
		Map<Integer, BlockStateContainer> map = new HashMap<>();
		for (Pair<Integer, NBTTagCompound> pair : paletteNBT) {
			String namespace = NBTStructure.getBlockNamespaceFromNBT(pair.getRight());

			//TODO: TileEntity data from some default states (eg potted_poppy)
			//Flower pots and skulls will need some default TE data since some things that are TE data in 1.7.10 are now BlockState data.

			Map<String, String> properties = NBTStructure.getProperties(pair.getRight());
			Block block = getBlockFromNamespace(namespace, properties);
			if (block == null) {
				block = Blocks.stone;
			}
			map.put(pair.getLeft(), new BlockStateContainer(block, getMetaFromStateWithSubtypeAdditions(namespace, properties, dir)));
		}
		return map;
	}

	/**
	 * This function is for getting the equivalent meta value for a BlockState value when unflattening an ID in structure NBT.
	 * For example an up-down log is meta 0 in unflattened versions, x-facing log is 1 and z is 2.
	 * <p>
	 * Wall and fence states are completely discarded as all of their connections and states are done directly in the renderer.
	 * <p>
	 * The facing direction can only be NORTH, SOUTH, EAST, or WEST.
	 */
	public int getMetaFromState(String blockName, Map<String, String> blockStates, ForgeDirection dir) {
		String truncatedName = blockName.substring(blockName.indexOf(":") + 1);
		//For rotatable pillars like logs
		if (blockStates.containsKey("axis")) {
			String axis = blockStates.get("axis");
			if (truncatedName.equals("quartz_pillar")) {
				switch (axis) { //Because these cheeky bastards just have to be different from other pillars for some reason, so we'll do this here instead of in the meta additiosn part
					case "y":
						return 2;
					case "x":
						return dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH ? 3 : 4;
					case "z":
						return dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH ? 4 : 3;
				}
			} else {
				switch (axis) {
					case "y":
						return 0;
					case "x":
						return dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH ? 4 : 8;
					case "z":
						return dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH ? 8 : 4;
				}
			}
		}
		//For blocks that can face in different directions
		//Note not every block that uses this state has the same meta values in 1.7.10, there will be a lot
		else if (blockStates.containsKey("facing")) {
			String facing = blockStates.get("facing");
			if (truncatedName.endsWith("wall_torch") || truncatedName.endsWith("stairs")) {
				int meta = 0; //temporary
				if (truncatedName.endsWith("wall_torch")) {
					meta = 1;
				} else if ("top".equals(blockStates.get("half"))) { //For stairs. Shape is ignored because that's handled by the renderer in this version.
					meta = 4;
				}
				switch (facing) {
					case "east":
						return meta + ((dir.ordinal() - 2) % 4);
					case "west":
						return meta + (((dir.ordinal() - 2) + 1) % 4);
					case "south":
						return meta + (((dir.ordinal() - 2) + 2) % 4);
					case "north":
						return meta + (((dir.ordinal() - 2) + 3) % 4);
				}
			} else if (truncatedName.equals("end_portal_frame")) {
				switch (facing) {
					case "south":
						return ((dir.ordinal() - 2) % 4); //These probably don't rotate properly
					case "west":
						return (((dir.ordinal() - 2) + 1) % 4);
					case "north":
						return (((dir.ordinal() - 2) + 2) % 4);
					case "east":
						return (((dir.ordinal() - 2) + 3) % 4);
				}
			} else if (truncatedName.endsWith("anvil")) {
				switch (facing) {
					case "west":
						return ((dir.ordinal() - 2) % 4); //These probably don't rotate properly
					case "north":
						return (((dir.ordinal() - 2) + 1) % 4);
					case "east":
						return (((dir.ordinal() - 2) + 2) % 4);
					case "south":
						return (((dir.ordinal() - 2) + 3) % 4);
				}
			} else {
				switch (facing) {
					case "down":
						return 0;
					case "up":
						return 1;
					case "north":
						return ((dir.ordinal() - 2) % 4) + 2;
					case "south":
						return (((dir.ordinal() - 2) + 1) % 4) + 2;
					case "west":
						return (((dir.ordinal() - 2) + 2) % 4) + 2;
					case "east":
						return (((dir.ordinal() - 2) + 3) % 4) + 2;
				}
			}
		} else if (blockStates.containsKey("stage") && (truncatedName.endsWith("sapling") || truncatedName.equals("mangrove_propagule"))) {
			if (blockStates.get("stage").equals("1")) {
				return 8;
			}
		} else if (blockStates.containsKey("persistent")) {
			if (blockStates.get("persistent").equals("true")) {
				return 4;
			}
		} else if (blockStates.containsKey("type") && truncatedName.contains("slab")) {
			if (blockStates.get("type").equals("top")) {
				return 8;
			}
		} else if (blockStates.containsKey("half") && blockStates.get("half").equals("upper")) {
			if (truncatedName.equals("sunflower") || truncatedName.equals("lilac") || truncatedName.equals("tall_grass") || truncatedName.equals("large_fern")
					|| truncatedName.equals("rose_bush") || truncatedName.equals("peony")) {
				return 8;
			}
		} else if (blockStates.containsKey("shape") && truncatedName.endsWith("rail")) {
			int meta = 0;
			if (blockStates.containsKey("powered") && Boolean.parseBoolean(blockStates.get("powered"))) {
				meta = 8;
			}
			switch (blockStates.get("shape")) {
				case "north_south":
					meta = (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH ? 0 : 1);
					break;
				case "east_west":
					meta = (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST ? 1 : 0);
					break;
				case "ascending_east":
					return meta + ((dir.ordinal() - 2) % 4) + 2; //This math probably doesn't work, these four states should go from 2-5
				case "ascending_west":
					return meta + (((dir.ordinal() - 2) + 1) % 4) + 2;
				case "ascending_north":
					return meta + (((dir.ordinal() - 2) + 2) % 4) + 2;
				case "ascending_south":
					return meta + (((dir.ordinal() - 2) + 3) % 4) + 2;
				case "south_east":
					return meta + ((dir.ordinal() - 2) % 4) + 6; //Same as above but the states should go from 6-9
				case "south_west":
					return meta + (((dir.ordinal() - 2) + 1) % 4) + 6; //Shouldn't need to conditionally remove these because other rail types never reach these anyways
				case "north_west":
					return meta + (((dir.ordinal() - 2) + 2) % 4) + 6;
				case "north_east":
					return meta + (((dir.ordinal() - 2) + 3) % 4) + 6;
			}
		} else if (blockStates.containsKey("layers")) {
			return Integer.parseInt(blockStates.get("layers"));
		} else if (blockStates.containsKey("power")) {
			return Integer.parseInt(blockStates.get("power"));
		}
		//TODO: Map the large mushrooms to the states that exist in 1.7.10, for now I skipped them because they're fucking weird
		//TODO: Trapdoors, fence gates, buttons, pressure plates, melon and pumpkin stems, water, lava, daylight sensors, doors, repeaters, comparaters, skulls, signs, beds, tripwires, tripwire hooks
		//Water and lava might need some extra work as well.
		return 0;
	}

	/**
	 * Runs startsWith on every BlockState key.
	 *
	 * @param blockStates
	 * @param string
	 * @return
	 */
	protected final boolean blockStatesStartWith(Map<String, String> blockStates, String string) {
		return blockStates.keySet().stream().anyMatch(stateName -> stateName.startsWith(string));
	}

	/**
	 * Runs endsWith on every BlockState key.
	 *
	 * @param blockStates
	 * @param string
	 * @return
	 */
	protected final boolean blockStatesEndWith(Map<String, String> blockStates, String string) {
		return blockStates.keySet().stream().anyMatch(stateName -> stateName.endsWith(string));
	}

	/**
	 * Runs contains on every BlockState key.
	 *
	 * @param blockStates
	 * @param string
	 * @return
	 */
	protected final boolean blockStatesContain(Map<String, String> blockStates, String string) {
		return blockStates.keySet().stream().anyMatch(stateName -> stateName.contains(string));
	}

	/**
	 * Calls getMetaFromState and adds the metadata for sub-blocks as well (eg minecraft:black_wool adds to 15)
	 * This intentionally contains duplicate switch case statements and more individualized math functions just to make it easier to read what it's doing at a glance.
	 *
	 * @param blockName
	 * @param blockStates
	 * @param dir
	 * @return
	 */
	public int getMetaFromStateWithSubtypeAdditions(String blockName, Map<String, String> blockStates, ForgeDirection dir) {
		int meta = getMetaFromState(blockName, blockStates, dir);
		if (blockName.startsWith("minecraft:")) {
			String truncatedName = blockName.replace("minecraft:", "");
			switch (truncatedName) {
				case "coarse_dirt":
					meta = 1;
					break;

				case "spruce_planks":
					meta = 1;
					break;
				case "birch_planks":
					meta = 2;
					break;
				case "jungle_planks":
					meta = 3;
					break;
				case "acacia_planks":
					meta = 4;
					break;
				case "dark_oak_planks":
					meta = 5;
					break;

				case "oak_sapling":
					meta = 0;
					if (getMetaFromState(truncatedName, blockStates, dir) > 7) {
						meta += 8;
					}
					break;
				case "spruce_sapling":
					meta = 1;
					if (getMetaFromState(truncatedName, blockStates, dir) > 7) {
						meta += 8;
					}
					break;
				case "birch_sapling":
					meta = 2;
					if (getMetaFromState(truncatedName, blockStates, dir) > 7) {
						meta += 8;
					}
					break;
				case "jungle_sapling":
					meta = 3;
					if (getMetaFromState(truncatedName, blockStates, dir) > 7) {
						meta += 8;
					}
					break;
				case "acacia_sapling":
					meta = 4;
					if (getMetaFromState(truncatedName, blockStates, dir) > 7) {
						meta += 8;
					}
					break;
				case "dark_oak_sapling":
					meta = 5;
					if (getMetaFromState(truncatedName, blockStates, dir) > 7) {
						meta += 8;
					}
					break;
				case "red_sand":
					meta = 1;
					break;

				case "spruce_log":
					meta = 1;
					break;
				case "birch_log":
					meta = 2;
					break;
				case "jungle_log":
					meta = 3;
					break;

				case "dark_oak_log":
					meta = 1;
					break;

				case "spruce_leaves":
					meta += 1;
					break;
				case "birch_leaves":
					meta += 2;
					break;
				case "jungle_leaves":
					meta += 3;
					break;

				case "dark_oak_leaves":
					meta += 1;
					break;

				case "chiseled_sandstone":
					meta = 1;
					break;
				case "cut_sandstone":
					meta = 2;
					break;

				case "grass":
					meta = 1;
					break;
				case "fern":
					meta = 2;
					break;

				case "blue_orchid":
					meta = 1;
					break;
				case "allium":
					meta = 2;
					break;
				case "azure_bluet":
					meta = 3;
					break;
				case "red_tulip":
					meta = 4;
					break;
				case "orange_tulip":
					meta = 5;
					break;
				case "white_tulip":
					meta = 6;
					break;
				case "pink_tulip":
					meta = 7;
					break;
				case "oxeye_daisy":
					meta = 8;
					break;

				case "spruce_slab":
					meta += 1;
					break;
				case "birch_slab":
					meta += 2;
					break;
				case "jungle_slab":
					meta += 3;
					break;
				case "acacia_slab":
					meta += 4;
					break;
				case "dark_oak_slab":
					meta += 5;
					break;

				case "sandstone_slab":
					meta += 1;
					break;
				case "petrified_oak_slab":
					meta += 2;
					break;
				case "cobblestone_slab":
					meta += 3;
					break;
				case "brick_slab":
					meta += 4;
					break;
				case "stone_brick_slab":
					meta += 5;
					break;
				case "nether_brick_slab":
					meta += 6;
					break;
				case "quartz_slab":
					meta += 7;
					break;

				case "smooth_stone":
					meta = 8;
					break;
				case "smooth_sandstone":
					meta = 9;
					break;
				case "smooth_quartz":
					meta = 10;
					break;

				case "carved_pumpkin":
				case "jack_o_lantern":
					meta -= 2;
					break;

				case "podzol":
					meta = 2;
					break;
				case "anvil":
					if (meta > 1) {
						meta -= 2;
					}
					break;
				case "chipped_anvil":
					if (meta > 1) {
						meta -= 2;
						meta += 4;
					}
					break;
				case "damaged_anvil":
					if (meta > 1) {
						meta -= 2;
						meta += 8;
					}
					break;

				case "infested_cobblestone":
					meta = 1;
					break;
				case "infested_stone_bricks":
					meta = 2;
					break;
				case "infested_mossy_stone_bricks":
					meta = 3;
					break;
				case "infested_bracked_stone_bricks":
					meta = 4;
					break;
				case "infested_chiseled_stone_bricks":
					meta = 5;
					break;

				case "mossy_stone_bricks":
					meta = 1;
					break;
				case "bracked_stone_bricks":
					meta = 2;
					break;
				case "chiseled_stone_bricks":
					meta = 3;
					break;

				case "mossy_cobblestone_wall":
					meta = 1;
					break;

				case "chiseled_quartz_block":
					meta = 1;
					break;
				//Quartz pillars are handled in getMetaFromState since their rotation metas are different from every other pillar in the game for some reason
				//May as well do it all there at that point.

				case "lilac":
					if (blockStates.containsKey("half") && blockStates.get("half").equals("upper")) {
						meta = 1;
					}
					break;
				case "tall_grass":
					if (blockStates.containsKey("half") && blockStates.get("half").equals("upper")) {
						meta = 2;
					}
					break;
				case "large_fern":
					if (blockStates.containsKey("half") && blockStates.get("half").equals("upper")) {
						meta = 3;
					}
					break;
				case "rose_bush":
					if (blockStates.containsKey("half") && blockStates.get("half").equals("upper")) {
						meta = 4;
					}
					break;
				case "peony":
					if (blockStates.containsKey("half") && blockStates.get("half").equals("upper")) {
						meta = 5;
					}
					break;

				case "orange_wool":
				case "orange_carpet":
				case "orange_stained_glass":
				case "orange_stained_glass_pane":
				case "orange_terracotta":
					meta = 1;
					break;
				case "magenta_wool":
				case "magenta_carpet":
				case "magenta_stained_glass":
				case "magenta_stained_glass_pane":
				case "magenta_terracotta":
					meta = 2;
					break;
				case "light_blue_wool":
				case "light_blue_carpet":
				case "light_blue_stained_glass":
				case "light_blue_stained_glass_pane":
				case "light_blue_terracotta":
					meta = 3;
					break;
				case "yellow_wool":
				case "yellow_carpet":
				case "yellow_stained_glass":
				case "yellow_stained_glass_pane":
				case "yellow_terracotta":
					meta = 4;
					break;
				case "lime_wool":
				case "lime_carpet":
				case "lime_stained_glass":
				case "lime_stained_glass_pane":
				case "lime_terracotta":
					meta = 5;
					break;
				case "pink_wool":
				case "pink_carpet":
				case "pink_stained_glass":
				case "pink_stained_glass_pane":
				case "pink_terracotta":
					meta = 6;
					break;
				case "gray_wool":
				case "gray_carpet":
				case "gray_stained_glass":
				case "gray_stained_glass_pane":
				case "gray_terracotta":
					meta = 7;
					break;
				case "light_gray_wool":
				case "light_gray_carpet":
				case "light_gray_stained_glass":
				case "light_gray_stained_glass_pane":
				case "light_gray_terracotta":
					meta = 8;
					break;
				case "cyan_wool":
				case "cyan_carpet":
				case "cyan_stained_glass":
				case "cyan_stained_glass_pane":
				case "cyan_terracotta":
					meta = 9;
					break;
				case "purple_wool":
				case "purple_carpet":
				case "purple_stained_glass":
				case "purple_stained_glass_pane":
				case "purple_terracotta":
					meta = 10;
					break;
				case "blue_wool":
				case "blue_carpet":
				case "blue_stained_glass":
				case "blue_stained_glass_pane":
				case "blue_terracotta":
					meta = 11;
					break;
				case "brown_wool":
				case "brown_carpet":
				case "brown_stained_glass":
				case "brown_stained_glass_pane":
				case "brown_terracotta":
					meta = 12;
					break;
				case "green_wool":
				case "green_carpet":
				case "green_stained_glass":
				case "green_stained_glass_pane":
				case "green_terracotta":
					meta = 13;
					break;
				case "red_wool":
				case "red_carpet":
				case "red_stained_glass":
				case "red_stained_glass_pane":
				case "red_terracotta":
					meta = 14;
					break;
				case "black_wool":
				case "black_carpet":
				case "black_stained_glass":
				case "black_stained_glass_pane":
				case "black_terracotta":
					meta = 15;
					break;
				default:
					break;
			}
		}
		return meta;
	}

	public Block getBlockFromNamespace(String blockName, Map<String, String> blockStates) {
		String truncatedName = blockName.substring(blockName.indexOf(":") + 1);
		if (blockName.startsWith("minecraft:")) {
			switch (truncatedName) {
				case "grass_block":
					return Blocks.grass;

				case "coarse_dirt":
				case "podzol":
					return Blocks.dirt;

				case "oak_planks":
				case "spruce_planks":
				case "birch_planks":
				case "jungle_planks":
				case "acacia_planks":
				case "dark_oak_planks":
					return Blocks.planks;

				case "red_sand":
					return Blocks.sand;

				case "oak_log":
				case "spruce_log":
				case "birch_log":
				case "jungle_log":
					return Blocks.log;

				case "acacia_log":
				case "dark_oak_log":
					return Blocks.log2;

				case "oak_leaves":
				case "spruce_leaves":
				case "birch_leaves":
				case "jungle_leaves":
					return Blocks.leaves;

				case "acacia_leaves":
				case "dark_oak_leaves":
					return Blocks.leaves2;

				case "chiseled_sandstone":
				case "cut_sandstone":
					return Blocks.sandstone;

				case "note_block":
					return Blocks.noteblock;

				case "powered_rail":
					return Blocks.golden_rail;

				case "cobweb":
					return Blocks.web;

				case "grass":
				case "fern":
					return Blocks.tallgrass;

				case "dead_bush":
					return Blocks.deadbush;

				//piston_extension intentionally left out, the data is probably different and structures don't need this as it is for pistons only
				//If it is needed for some reason, you should probably parse the data yourself because the TE data is different from 1.7.10 and a "bare" piston extension is useless.

				case "dandelion":
					return Blocks.yellow_flower;

				case "poppy":
				case "blue_orchid":
				case "allium":
				case "azure_bluet":
				case "red_tulip":
				case "orange_tulip":
				case "white_tulip":
				case "pink_tulip":
				case "oxeye_daisy":
					return Blocks.red_flower;

				case "oak_slab":
				case "birch_slab":
				case "spruce_slab":
				case "jungle_slab":
				case "acacia_slab":
				case "dark_oak_slab":
					if (blockStates.containsKey("type") && blockStates.get("type").equals("double")) {
						return Blocks.double_wooden_slab;
					} else {
						return Blocks.wooden_slab;
					}

				case "smooth_stone_slab":
				case "sandstone_slab":
				case "petrified_oak_slab":
				case "cobblestone_slab":
				case "brick_slab":
				case "stone_brick_slab":
				case "nether_brick_slab":
				case "quartz_slab":

				case "smooth_stone":
				case "smooth_sandstone":
				case "smooth_quartz":
					if (blockStates.containsKey("type") && blockStates.get("type").equals("double")) {
						return Blocks.double_stone_slab;
					} else {
						return Blocks.stone_slab;
					}

				case "bricks":
					return Blocks.brick_block;

				case "spawner":
					return Blocks.mob_spawner;

				case "furnace":
					if (blockStates.containsKey("lit") && Boolean.parseBoolean(blockStates.get("lit"))) {
						return Blocks.furnace;
					}
					break;

				case "cobblestone_stairs":
					return Blocks.stone_stairs;

				case "redstone_ore":
					if (blockStates.containsKey("lit") && Boolean.parseBoolean(blockStates.get("lit"))) {
						return Blocks.lit_redstone_ore;
					}


				case "wall_torch":
					return Blocks.torch;
				case "redstone_wall_torch":
				case "redstone_torch":
					if (blockStates.containsKey("lit") && !Boolean.parseBoolean(blockStates.get("lit"))) {
						return Blocks.unlit_redstone_torch;
					}
					return Blocks.redstone_torch;

				case "snow":
					return Blocks.snow_layer;

				case "snow_block":
					return Blocks.snow;

				case "carved_pumpkin":
					return Blocks.pumpkin;
				case "jack_o_lantern":
					return Blocks.lit_pumpkin;

				case "infested_stone":
				case "infested_cobblestone":
				case "infested_stone_bricks":
				case "infested_mossy_stone_bricks":
				case "infested_cracked_stone_bricks":
				case "infested_chiseled_stone_bricks":
					return Blocks.monster_egg;

				case "stone_bricks":
				case "mossy_stone_bricks":
				case "cracked_stone_bricks":
				case "chiseled_stone_bricks":
					return Blocks.stonebrick;

				case "mushroom_stem": //TODO: Verify how to check if a stem is a red or brown mushroom stem
					return Blocks.red_mushroom_block;

				case "melon":
					return Blocks.melon_block;

				case "lily_pad":
					return Blocks.waterlily;

				case "nether_bricks":
					return Blocks.nether_brick;

				case "redstone_lamp":
					if (blockStates.containsKey("lit") && Boolean.parseBoolean(blockStates.get("lit"))) {
						return Blocks.lit_redstone_lamp;
					}

				case "mossy_cobblestone_wall":
					return Blocks.cobblestone_wall;

				case "daylight_detector":
					if (blockStates.containsKey("inverted") && Boolean.parseBoolean(blockStates.get("inverted"))) {
						return Blocks.stone; //There is no inverted daylight detector in 1.7.10
					}

				case "nether_quartz_ore":
					return Blocks.quartz_ore;

				case "chiseled_quartz_block":
				case "quartz_pillar":
					return Blocks.quartz_block;

				case "terracotta":
					return Blocks.hardened_clay;

				case "sunflower":
				case "lilac":
				case "tall_grass":
				case "large_fern":
				case "rose_bush":
				case "peony":
					return Blocks.double_plant;

				case "repeater":
					if (blockStates.containsKey("powered") && Boolean.parseBoolean(blockStates.get("powered"))) {
						return Blocks.powered_repeater;
					} else {
						return Blocks.unpowered_repeater;
					}

				case "comparator":
					if (blockStates.containsKey("powered") && Boolean.parseBoolean(blockStates.get("powered"))) {
						return Blocks.powered_comparator;
					} else {
						return Blocks.unpowered_comparator;
					}

				case "skeleton_skull":
				case "skeleton_wall_skull":
				case "wither_skeleton_skull":
				case "wither_skeleton_wall_skull":
				case "zombie_skull":
				case "zombie_wall_skull":
				case "player_skull":
				case "player_wall_skull":
				case "creeper_skull":
				case "creeper_wall_skull":
					return Blocks.skull;

				case "attached_pumpkin_stem":
					return Blocks.pumpkin_stem;
				case "attached_melon_stem":
					return Blocks.melon_stem;

				case "potted_poppy":
				case "potted_dandelion":
				case "potted_blue_orchid":
				case "potted_allium":
				case "potted_azure_bluet":
				case "potted_oxeye_daisy":
				case "potted_red_tulip":
				case "potted_orange_tulip":
				case "potted_white_tulip":
				case "potted_pink_tulip":
				case "potted_fern":
				case "potted_cactus":
				case "potted_dead_bush":
				case "potted_red_mushroom":
				case "potted_brown_mushroom":
				case "potted_oak_sapling":
				case "potted_spruce_sapling":
				case "potted_birch_sapling":
				case "potted_jungle_sapling":
				case "potted_acacia_sapling":
				case "potted_dark_oak_sapling":
					return Blocks.flower_pot;

				//TODO: tripwires, redstone wires

				case "oak_pressure_plate":
					return Blocks.wooden_pressure_plate;
				case "oak_fence":
					return Blocks.fence;
				case "oak_trapdoor":
					return Blocks.trapdoor;
				case "oak_fence_gate":
					return Blocks.fence_gate;
				case "oak_button":
					return Blocks.wooden_button;
				case "oak_door":
					return Blocks.wooden_door;
				case "oak_sign":
					if (blockStates.containsKey("rotation")) {
						//TODO: Verify this. Do standing signs have a block tag not on the wiki?
						//The wiki only shows the data "facing" for wall signs, and "rotation" for standing signs. Is this the proper way to check?
						return Blocks.standing_sign;
					} else {
						return Blocks.wall_sign;
					}

				case "red_bed":
					return Blocks.bed;
				case "white_wool":
				case "orange_wool":
				case "magenta_wool":
				case "light_blue_wool":
				case "yellow_wool":
				case "lime_wool":
				case "pink_wool":
				case "gray_wool":
				case "light_gray_wool":
				case "cyan_wool":
				case "purple_wool":
				case "blue_wool":
				case "brown_wool":
				case "green_wool":
				case "red_wool":
				case "black_wool":
					return Blocks.wool;
				case "white_carpet":
				case "orange_carpet":
				case "magenta_carpet":
				case "light_blue_carpet":
				case "yellow_carpet":
				case "lime_carpet":
				case "pink_carpet":
				case "gray_carpet":
				case "light_gray_carpet":
				case "cyan_carpet":
				case "purple_carpet":
				case "blue_carpet":
				case "brown_carpet":
				case "green_carpet":
				case "red_carpet":
				case "black_carpet":
					return Blocks.carpet;
				case "white_stained_glass":
				case "orange_stained_glass":
				case "magenta_stained_glass":
				case "light_blue_stained_glass":
				case "yellow_stained_glass":
				case "lime_stained_glass":
				case "pink_stained_glass":
				case "gray_stained_glass":
				case "light_gray_stained_glass":
				case "cyan_stained_glass":
				case "purple_stained_glass":
				case "blue_stained_glass":
				case "brown_stained_glass":
				case "green_stained_glass":
				case "red_stained_glass":
				case "black_stained_glass":
					return Blocks.stained_glass;
				case "white_stained_glass_pane":
				case "orange_stained_glass_pane":
				case "magenta_stained_glass_pane":
				case "light_blue_stained_glass_pane":
				case "yellow_stained_glass_pane":
				case "lime_stained_glass_pane":
				case "pink_stained_glass_pane":
				case "gray_stained_glass_pane":
				case "light_gray_stained_glass_pane":
				case "cyan_stained_glass_pane":
				case "purple_stained_glass_pane":
				case "blue_stained_glass_pane":
				case "brown_stained_glass_pane":
				case "green_stained_glass_pane":
				case "red_stained_glass_pane":
				case "black_stained_glass_pane":
					return Blocks.stained_glass_pane;
				case "white_terracotta":
				case "orange_terracotta":
				case "magenta_terracotta":
				case "light_blue_terracotta":
				case "yellow_terracotta":
				case "lime_terracotta":
				case "pink_terracotta":
				case "gray_terracotta":
				case "light_gray_terracotta":
				case "cyan_terracotta":
				case "purple_terracotta":
				case "blue_terracotta":
				case "brown_terracotta":
				case "green_terracotta":
				case "red_terracotta":
				case "black_terracotta":
					return Blocks.stained_hardened_clay;
				default:
					break;
			}
		}

		//If the block isn't above, we check the block registry to see if it's there.
		//Not all blocks are there because their ID is the same in modern versions as here so we don't need to put them there.
		//I should check if the above (getting block variable directly) works with mods that do registry replacement. Should we support that?

		//"stone_slab" is a different block in modern, "smooth_stone_slab" is the stone slab for this version but "stone_slab" is an ID in 1.7 so it will get recognized
		//Should we manually override "stone_slab" to return the default of Blocks.stone or should we leave it alone?

		Block block = GameRegistry.findBlock(blockName.substring(0, blockName.indexOf(":")), truncatedName);
		return block == null ? Blocks.stone : block;
	}

	public Pair<Item, Integer> getItemAndMetadataFromNamespace(String itemName) {
		return new ImmutablePair<>(Items.rotten_flesh, 0); //TODO: Actual mappings (rotten flesh will probably be the default return value though lol)
	}
}
