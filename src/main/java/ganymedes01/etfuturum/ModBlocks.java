package ganymedes01.etfuturum;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.blocks.*;
import ganymedes01.etfuturum.blocks.ores.BlockCopperOre;
import ganymedes01.etfuturum.blocks.ores.BlockDeepslateOre;
import ganymedes01.etfuturum.blocks.ores.BlockDeepslateRedstoneOre;
import ganymedes01.etfuturum.blocks.ores.BlockOreNetherGold;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.IRegistryName;
import ganymedes01.etfuturum.tileentities.TileEntityWoodSign;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;

public class ModBlocks {
	
	private static final List<Block> initList = new ArrayList<Block>();
	
	//old blocks
	public static final Block crying_obsidian = initBlock(new BlockCryingObsidian());
	public static final Block rose = initBlock(new BlockOldRose());

	//Standard Et Futurum 1.5.5 blocks
	public static final Block stone = initBlock(new BlockNewStone());
	public static final Block prismarine = initBlock(new BlockPrismarine());
	public static final Block sea_lantern = initBlock(new BlockSeaLantern());
	public static final Block inverted_daylight_detector = initBlock(new BlockInvertedDaylightDetector());
	public static final Block red_sandstone = initBlock(new BlockRedSandstone());
	public static final Block brown_mushroom_block = initBlock(new BlockSilkedMushroom(Blocks.brown_mushroom_block, "brown"));
	public static final Block red_mushroom_block = initBlock(new BlockSilkedMushroom(Blocks.red_mushroom_block, "red"));
	public static final Block coarse_dirt = initBlock(new BlockCoarseDirt());
	public static final Block banner = initBlock(new BlockBanner());
	public static final Block slime = initBlock(new BlockSlime());
	public static final Block sponge = initBlock(new BlockSponge());
	public static final Block old_gravel = initBlock(new BlockOldGravel());
	public static final Block beetroot = initBlock(new BlockBeetroot());
	public static final Block purpur_block = initBlock(new BlockPurpur());
	public static final Block purpur_pillar = initBlock(new BlockPurpurPillar());
	public static final Block end_bricks = initBlock(new BlockEndBricks());
	public static final Block grass_path = initBlock(new BlockGrassPath());
	public static final Block end_rod = initBlock(new BlockEndRod());
	public static final Block chorus_plant = initBlock(new BlockChorusPlant());
	public static final Block chorus_flower = initBlock(new BlockChorusFlower());
	
	public static final Block bone_block = initBlock(new BlockBone());
	public static final Block new_nether_brick = initBlock(new BlockNewNetherBrick());
	public static final Block nether_wart_block = initBlock(new BlockNetherwart());
	public static final Block ancient_debris = initBlock(new BlockAncientDebris());
	public static final Block netherite_block = initBlock(new BlockNetherite());
	public static final Block barrier = initBlock(new BlockBarrier());
	public static final Block nether_gold_ore = initBlock(new BlockOreNetherGold());
	public static final Block blue_ice = initBlock(new BlockBlueIce());
	public static final Block smooth_stone = initBlock(new BlockSmoothStone());
	public static final Block smooth_sandstone = initBlock(new BlockSmoothSandstone(0));
	public static final Block smooth_red_sandstone = initBlock(new BlockSmoothSandstone(1));
	public static final Block smooth_quartz = initBlock(new BlockSmoothQuartz());
	public static final Block quartz_bricks = initBlock(new BlockQuartzBricks());
	public static final Block log_stripped = initBlock(new BlockStrippedOldLog());
	public static final Block log2_stripped = initBlock(new BlockStrippedNewLog());
	public static final Block log_bark = initBlock(new BlockWoodBarkOld());
	public static final Block log2_bark = initBlock(new BlockWoodBarkNew());
	public static final Block wood_stripped = initBlock(new BlockStrippedOldWood());
	public static final Block wood2_stripped = initBlock(new BlockStrippedNewWood());
	public static final Block concrete = initBlock(new BlockConcrete());
	public static final Block concrete_powder = initBlock(new BlockConcretePowder());
	public static final Block copper_ore = initBlock(new BlockCopperOre());
	public static final Block deepslate_copper_ore = initBlock(new BlockDeepslateOre(ModBlocks.copper_ore));
	public static final Block cornflower = initBlock(new BlockCornflower());
	public static final Block lily_of_the_valley = initBlock(new BlockLilyOfTheValley());
	public static final Block wither_rose = initBlock(new BlockWitherRose());
	public static final Block sweet_berry_bush = initBlock(new BlockBerryBush());
	public static final Block white_glazed_terracotta = initBlock(new BlockGlazedTerracotta(0));
	public static final Block orange_glazed_terracotta = initBlock(new BlockGlazedTerracotta(1));
	public static final Block magenta_glazed_terracotta = initBlock(new BlockGlazedTerracotta(2));
	public static final Block light_blue_glazed_terracotta = initBlock(new BlockGlazedTerracotta(3));
	public static final Block yellow_glazed_terracotta = initBlock(new BlockGlazedTerracotta(4));
	public static final Block lime_glazed_terracotta = initBlock(new BlockGlazedTerracotta(5));
	public static final Block pink_glazed_terracotta = initBlock(new BlockGlazedTerracotta(6));
	public static final Block gray_glazed_terracotta = initBlock(new BlockGlazedTerracotta(7));
	public static final Block light_gray_glazed_terracotta = initBlock(new BlockGlazedTerracotta(8));
	public static final Block cyan_glazed_terracotta = initBlock(new BlockGlazedTerracotta(9));
	public static final Block purple_glazed_terracotta = initBlock(new BlockGlazedTerracotta(10));
	public static final Block blue_glazed_terracotta = initBlock(new BlockGlazedTerracotta(11));
	public static final Block brown_glazed_terracotta = initBlock(new BlockGlazedTerracotta(12));
	public static final Block green_glazed_terracotta = initBlock(new BlockGlazedTerracotta(13));
	public static final Block red_glazed_terracotta = initBlock(new BlockGlazedTerracotta(14));
	public static final Block black_glazed_terracotta = initBlock(new BlockGlazedTerracotta(15));
	public static final Block copper_block = initBlock(new BlockCopper());
	public static final Block lightning_rod = initBlock(new BlockLightningRod());
	public static final Block deepslate = initBlock(new BlockDeepslate());
	public static final Block cobbled_deepslate = initBlock(new BlockDeepslateCobbled());
	public static final Block polished_deepslate = initBlock(new BlockDeepslatePolished());
	public static final Block deepslate_bricks = initBlock(new BlockDeepslateBricks());
	public static final Block tuff = initBlock(new BlockTuff());
	public static final Block raw_ore_block = initBlock(new BlockRawOre());
	public static final Block smooth_basalt = initBlock(new BlockSmoothBasalt());
	public static final Block calcite = initBlock(new BlockCalcite());
	public static final Block amethyst_block = initBlock(new BlockAmethystBlock());
	public static final Block budding_amethyst = initBlock(new BlockBuddingAmethyst());
	public static final Block amethyst_cluster_1 = initBlock(new BlockAmethystCluster(0));
	public static final Block amethyst_cluster_2 = initBlock(new BlockAmethystCluster(1));

	public static final Block sculk = initBlock(new BlockSculk());
	public static final Block sculk_catalyst = initBlock(new BlockSculkCatalyst());
	public static final Block tinted_glass = initBlock(new BlockTintedGlass());
	
	//walls
	public static final Block generic_wall = initBlock(new BlockNewWall("stone_wall", new Block[] {Blocks.stonebrick, Blocks.stonebrick, Blocks.sandstone, Blocks.brick_block}, new int[] {0, 1, 0, 0}, new String[] {"stone_brick_wall", "mossy_stone_brick_wall", "sandstone_wall", "brick_wall"}));
	public static final Block nether_brick_wall = initBlock(new BlockNewWall("nether_brick_wall", new Block[] {Blocks.nether_brick}, new int[] {0}, null));

	public static final Block stone_wall = initBlock(new BlockNewWall("stone_wall_2", new Block[] {stone, stone, stone}, new int[] {1, 3, 5}, new String[] {"granite_wall", "diorite_wall", "andesite_wall"}));
	public static final Block red_sandstone_wall = initBlock(new BlockNewWall("red_sandstone_wall", new Block[] {red_sandstone}, new int[] {0}, null));
	public static final Block prismarine_wall = initBlock(new BlockNewWall("prismarine_wall", new Block[] {prismarine}, new int[] {0}, null));
	public static final Block red_nether_brick_wall = initBlock(new BlockNewWall("red_nether_brick_wall", new Block[] {new_nether_brick}, new int[] {0}, null));
	public static final Block end_brick_wall = initBlock(new BlockNewWall("end_brick_wall", new Block[] {end_bricks}, new int[] {0}, null));
	
	public static final Block deepslate_wall = initBlock(new BlockNewWall("deepslate_wall", new Block[] {cobbled_deepslate, polished_deepslate}, new int[] {0, 0}, new String[] {"cobbled_deepslate_wall", "polished_deepslate_wall"}));
	public static final Block deepslate_brick_wall = initBlock(new BlockNewWall("deepslate_brick_wall", new Block[] {deepslate_bricks, deepslate_bricks}, new int[] {0, 2}, new String[] {"deepslate_brick_wall", "deepslate_tile_wall"}));
	
	//deepslate vanilla ores
	public static final Block deepslate_coal_ore = initBlock(new BlockDeepslateOre(Blocks.coal_ore));
	public static final Block deepslate_iron_ore = initBlock(new BlockDeepslateOre(Blocks.iron_ore));
	public static final Block deepslate_gold_ore = initBlock(new BlockDeepslateOre(Blocks.gold_ore));
	public static final Block deepslate_redstone_ore = initBlock(new BlockDeepslateRedstoneOre(false));
	public static final Block deepslate_lit_redstone_ore = initBlock(new BlockDeepslateRedstoneOre(true));
	public static final Block deepslate_lapis_ore = initBlock(new BlockDeepslateOre(Blocks.lapis_ore));
	public static final Block deepslate_diamond_ore = initBlock(new BlockDeepslateOre(Blocks.diamond_ore));
	public static final Block deepslate_emerald_ore = initBlock(new BlockDeepslateOre(Blocks.emerald_ore));
	
	//technical blocks
	public static final Block brewing_stand = initBlock(new BlockNewBrewingStand());
	public static final Block beacon = initBlock(new BlockNewBeacon());
	public static final Block enchanting_table = initBlock(new BlockNewEnchantmentTable());
	public static final Block anvil = initBlock(new BlockNewAnvil());
	public static final Block daylight_detector = initBlock(new BlockNewDaylightSensor());
	public static final Block frosted_ice = initBlock(new BlockFrostedIce());
	public static final Block lava_cauldron = initBlock(new BlockLavaCauldron());
	public static final Block end_gateway = initBlock(new BlockEndGateway());
	
	//slab/stairs
	public static final Block red_sandstone_stairs = initBlock(new BlockGenericStairs(red_sandstone, 0).setBlockName(Utils.getUnlocalisedName("red_sandstone_stairs")));
	public static final Block purpur_stairs = initBlock(new BlockGenericStairs(purpur_block, 0).setBlockName(Utils.getUnlocalisedName("purpur_stairs")));
	public static final Block red_sandstone_slab = initBlock(new BlockRedSandstoneSlab(false));
	public static final Block double_red_sandstone_slab = initBlock(new BlockRedSandstoneSlab(true));
	public static final Block purpur_slab = initBlock(new BlockPurpurSlab(false));
	public static final Block double_purpur_slab = initBlock(new BlockPurpurSlab(true));
	
	public static final Block generic_slab = initBlock(new BlockStoneSlab1(false));
	public static final Block double_generic_slab = initBlock(new BlockStoneSlab1(true));
	public static final Block stone_slab = initBlock(new BlockStoneSlab2(false));
	public static final Block double_stone_slab = initBlock(new BlockStoneSlab2(true));
	
	public static final Block rough_prismarine_stairs = initBlock(new BlockGenericStairs(prismarine, 0).setBlockName(Utils.getUnlocalisedName("prismarine_stairs")));
	public static final Block prismarine_brick_stairs = initBlock(new BlockGenericStairs(prismarine, 1).setBlockName(Utils.getUnlocalisedName("prismarine_stairs_brick")));
	public static final Block dark_prismarine_stairs = initBlock(new BlockGenericStairs(prismarine, 2).setBlockName(Utils.getUnlocalisedName("prismarine_stairs_dark")));
	public static final Block smooth_sandstone_stairs = initBlock(new BlockGenericStairs(smooth_sandstone, 0).setBlockName(Utils.getUnlocalisedName("smooth_sandstone_stairs")));
	public static final Block smooth_red_sandstone_stairs = initBlock(new BlockGenericStairs(smooth_red_sandstone, 0).setBlockName(Utils.getUnlocalisedName("smooth_red_sandstone_stairs")));
	public static final Block smooth_quartz_stairs = initBlock(new BlockGenericStairs(smooth_quartz, 0).setBlockName(Utils.getUnlocalisedName("smooth_quartz_stairs")));
	public static final Block red_nether_brick_stairs = initBlock(new BlockGenericStairs(new_nether_brick, 0).setBlockName(Utils.getUnlocalisedName("red_netherbrick_stairs")));
	public static final Block granite_stairs = initBlock(new BlockGenericStairs(stone, 1).setBlockName(Utils.getUnlocalisedName("granite_stairs")));
	public static final Block polished_granite_stairs = initBlock(new BlockGenericStairs(stone, 2).setBlockName(Utils.getUnlocalisedName("polished_granite_stairs")));
	public static final Block diorite_stairs = initBlock(new BlockGenericStairs(stone, 3).setBlockName(Utils.getUnlocalisedName("diorite_stairs")));
	public static final Block polished_diorite_stairs = initBlock(new BlockGenericStairs(stone, 4).setBlockName(Utils.getUnlocalisedName("polished_diorite_stairs")));
	public static final Block andesite_stairs = initBlock(new BlockGenericStairs(stone, 5).setBlockName(Utils.getUnlocalisedName("andesite_stairs")));
	public static final Block polished_andesite_stairs = initBlock(new BlockGenericStairs(stone, 6).setBlockName(Utils.getUnlocalisedName("polished_andesite_stairs")));
	public static final Block mossy_stone_brick_stairs = initBlock(new BlockGenericStairs(Blocks.stonebrick, 1).setBlockName(Utils.getUnlocalisedName("mossy_stone_brick_stairs")));
	public static final Block mossy_cobblestone_stairs = initBlock(new BlockGenericStairs(Blocks.mossy_cobblestone, 0).setBlockName(Utils.getUnlocalisedName("mossy_cobblestone_stairs")));
	public static final Block stone_stairs = initBlock(new BlockGenericStairs(Blocks.stone, 0).setBlockName(Utils.getUnlocalisedName("stone_stairs")));
	public static final Block end_brick_stairs = initBlock(new BlockGenericStairs(end_bricks, 0).setBlockName(Utils.getUnlocalisedName("end_brick_stairs")));
	
	public static final Block smooth_sandstone_slab = initBlock(new BlockSmoothSandstoneSlab(0, false));
	public static final Block double_smooth_sandstone_slab = initBlock(new BlockSmoothSandstoneSlab(0, true));
	public static final Block smooth_red_sandstone_slab = initBlock(new BlockSmoothSandstoneSlab(1, false));
	public static final Block double_smooth_red_sandstone_slab = initBlock(new BlockSmoothSandstoneSlab(1, true));
	public static final Block prismarine_slab = initBlock(new BlockPrismarineSlab(false));
	public static final Block double_prismarine_slab = initBlock(new BlockPrismarineSlab(true));
	public static final Block smooth_quartz_slab = initBlock(new BlockSmoothQuartzSlab(false));
	public static final Block double_smooth_quartz_slab = initBlock(new BlockSmoothQuartzSlab(true));
	public static final Block red_nether_brick_slab = initBlock(new BlockRedNetherBrickSlab(false));
	public static final Block double_red_nether_brick_slab = initBlock(new BlockRedNetherBrickSlab(true));
	public static final Block end_brick_slab = initBlock(new BlockEndBrickSlab(false));
	public static final Block double_end_brick_slab = initBlock(new BlockEndBrickSlab(true));
	public static final Block cut_copper_slab = initBlock(new BlockCutCopperSlab(false));
	public static final Block double_cut_copper_slab = initBlock(new BlockCutCopperSlab(true));
	public static final Block deepslate_slab = initBlock(new BlockDeepslateSlab(false, false));
	public static final Block double_deepslate_slab = initBlock(new BlockDeepslateSlab(true, false));
	public static final Block deepslate_brick_slab = initBlock(new BlockDeepslateSlab(false, true));
	public static final Block double_deepslate_brick_slab = initBlock(new BlockDeepslateSlab(true, true));
	public static final Block netherite_stairs = initBlock(new BlockNetheriteStairs());
	public static final Block cut_copper_stairs = initBlock(new BlockCutCopperStairs(4));
	public static final Block exposed_cut_copper_stairs = initBlock(new BlockCutCopperStairs(5));
	public static final Block weathered_cut_copper_stairs = initBlock(new BlockCutCopperStairs(6));
	public static final Block oxidized_cut_copper_stairs = initBlock(new BlockCutCopperStairs(7));
	public static final Block waxed_cut_copper_stairs = initBlock(new BlockCutCopperStairs(12));
	public static final Block waxed_exposed_cut_copper_stairs = initBlock(new BlockCutCopperStairs(13));
	public static final Block waxed_weathered_cut_copper_stairs = initBlock(new BlockCutCopperStairs(14));
	public static final Block waxed_oxidized_cut_copper_stairs = initBlock(new BlockCutCopperStairs(15));
	public static final Block cobbled_deepslate_stairs = initBlock(new BlockGenericStairs(ModBlocks.cobbled_deepslate, 0).setBlockName(Utils.getUnlocalisedName("cobbled_deepslate_stairs")));
	public static final Block polished_deepslate_stairs = initBlock(new BlockGenericStairs(ModBlocks.polished_deepslate, 0).setBlockName(Utils.getUnlocalisedName("polished_deepslate_stairs")));
	public static final Block deepslate_brick_stairs = initBlock(new BlockGenericStairs(ModBlocks.deepslate_bricks, 0).setBlockName(Utils.getUnlocalisedName("deepslate_brick_stairs")));
	public static final Block deepslate_tile_stairs = initBlock(new BlockGenericStairs(ModBlocks.deepslate_bricks, 2).setBlockName(Utils.getUnlocalisedName("deepslate_tile_stairs")));
	
	//Mechanic/Functional blocks
	public static final Block iron_trapdoor = initBlock(new BlockIronTrapdoor());
	
	public static final Block magma_block = initBlock(new BlockMagma());
	public static final Block barrel = initBlock(new BlockBarrel());
	public static final Block lantern = initBlock(new BlockLantern());
	public static final Block smoker = initBlock(new BlockSmoker(false));
	public static final Block lit_smoker = initBlock(new BlockSmoker(true));
	public static final Block blast_furnace = initBlock(new BlockBlastFurnace(false));
	public static final Block lit_blast_furnace = initBlock(new BlockBlastFurnace(true));
	public static final Block shulker_box = initBlock(new BlockShulkerBox());
	public static final Block smithing_table = initBlock(new BlockSmithingTable());
	public static final Block composter = initBlock(new BlockComposter());
	public static final Block stonecutter = initBlock(new BlockStonecutter());
	public static final Block fletching_table = initBlock(new BlockFletchingTable());
	public static final Block cartography_table = initBlock(new BlockCartographyTable());
	public static final Block loom = initBlock(new BlockLoom());
	public static final Block dripstone_block = initBlock(new BlockDripstone());
	public static final Block pointed_dripstone = initBlock(new BlockPointedDripstone());
	
	public static final String[] woodTypes = new String[] {"oak", "spruce", "birch", "jungle", "acacia", "dark_oak"/*, "crimson", "warped"*/};
	public static final Block[] doors = new Block[woodTypes.length - 1];
	public static final Block[] fences = new Block[woodTypes.length - 1];
	public static final Block[] gates = new Block[woodTypes.length - 1];
	
	public static final Block[] pressure_plates = new Block[woodTypes.length - 1];
	public static final Block[] buttons = new Block[woodTypes.length - 1];
	public static final Block[] trapdoors = new Block[woodTypes.length - 1];
	public static final Block[] signs = new Block[woodTypes.length - 1];
	public static final Block[] wall_signs = new Block[woodTypes.length - 1];
	public static final Block[] beds = new Block[15];

	static {
		for (int i = 0; i < signs.length; i++) {
			signs[i] = initBlock(new BlockWoodSign(TileEntityWoodSign.class, true, i + 1));
			wall_signs[i] = initBlock(new BlockWoodSign(TileEntityWoodSign.class, false, i + 1));
		}
		
		for (int i = 0; i < doors.length; i++) {
			doors[i] = initBlock(new BlockWoodDoor(i + 1));
		}

		for (int i = 0; i < fences.length; i++) {
			fences[i] = initBlock(new BlockWoodFence(i + 1));
		}

		for (int i = 0; i < gates.length; i++) {
			gates[i] = initBlock(new BlockWoodFenceGate(i + 1));
		}
		
		for (int i = 0; i < pressure_plates.length; i++) {
			pressure_plates[i] = initBlock(new BlockWoodPressurePlate(i + 1));
		}
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = initBlock(new BlockWoodButton(i + 1));
		}
		
		for (int i = 0; i < trapdoors.length; i++) {
			trapdoors[i] = initBlock(new BlockWoodTrapdoor(i + 1));
		}
		
		for(int i = 0; i < beds.length; i++) {
			beds[i] = initBlock(new BlockDyedBed(i));
		}
	}
	
	private static Block initBlock(Block block) {
		if(!(block instanceof IConfigurable) || ((IConfigurable)block).isEnabled())
			initList.add(block);
		return block;
	}
	
	public static void init() {
		for(Block block : initList) {
			String name = block.getUnlocalizedName();
			String[] strings = name.split("\\.");
			String registryName = block instanceof IRegistryName ? ((IRegistryName)block).getRegistryName() : strings[strings.length - 1];

			if (block instanceof ISubBlocksBlock) {
				GameRegistry.registerBlock(block, ((ISubBlocksBlock) block).getItemBlockClass(), registryName);
			} else {
				GameRegistry.registerBlock(block, registryName);
			}
		}
	}
	
	public static interface ISubBlocksBlock {
		Class<? extends ItemBlock> getItemBlockClass();
	}
}
