package ganymedes01.etfuturum;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.blocks.*;
import ganymedes01.etfuturum.blocks.itemblocks.*;
import ganymedes01.etfuturum.blocks.ores.BlockCopperOre;
import ganymedes01.etfuturum.blocks.ores.BlockDeepslateOre;
import ganymedes01.etfuturum.blocks.ores.BlockDeepslateRedstoneOre;
import ganymedes01.etfuturum.blocks.ores.BlockOreNetherGold;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.*;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.tileentities.TileEntityWoodSign;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public enum ModBlocks {
	CRYING_OBSIDIAN(ConfigBlocksItems.enableCryingObsidian, new BlockCryingObsidian()),
	ROSE(ConfigTweaks.enableRoses, new BlockOldRose()),
	OLD_GRAVEL(ConfigTweaks.enableOldGravel, new BlockOldGravel()),


	STONE(ConfigBlocksItems.enableStones, new BlockNewStone(), ItemBlockGeneric.class),
	PRISMARINE_BLOCK(ConfigBlocksItems.enablePrismarine, new BlockPrismarine(), ItemBlockGeneric.class),
	SEA_LANTERN(ConfigBlocksItems.enablePrismarine, new BlockSeaLantern()),
	DAYLIGHT_DETECTOR_INVERTED(ConfigBlocksItems.enableInvertedDaylightSensor, new BlockInvertedDaylightDetector()),
	RED_SANDSTONE(ConfigBlocksItems.enableRedSandstone, new BlockRedSandstone(), ItemBlockRedSandstone.class),
	BROWN_MUSHROOM(ConfigFunctions.enableSilkTouchingMushrooms, new BlockSilkedMushroom(Blocks.brown_mushroom_block, "brown")),
	RED_MUSHROOM(ConfigFunctions.enableSilkTouchingMushrooms, new BlockSilkedMushroom(Blocks.red_mushroom_block, "red")),
	COARSE_DIRT(ConfigBlocksItems.enableCoarseDirt, new BlockCoarseDirt()),
	BANNER(ConfigBlocksItems.enableBanners, new BlockBanner(), ItemBlockBanner.class),
	SLIME(ConfigBlocksItems.enableSlimeBlock, new BlockSlime()),
	SPONGE(ConfigBlocksItems.enableSponge, new BlockSponge(), ItemBlockGeneric.class),
	BEETROOTS(ConfigBlocksItems.enableBeetroot, new BlockBeetroot()),
	PURPUR_BLOCK(ConfigBlocksItems.enableChorusFruit, new BlockPurpur()),
	PURPUR_PILLAR(ConfigBlocksItems.enableChorusFruit, new BlockPurpurPillar()),
	END_BRICKS(ConfigBlocksItems.enableChorusFruit, new BlockEndBricks()),
	GRASS_PATH(ConfigBlocksItems.enableGrassPath, new BlockGrassPath()),
	END_ROD(ConfigBlocksItems.enableChorusFruit, new BlockEndRod()),
	CHORUS_PLANT(ConfigBlocksItems.enableChorusFruit, new BlockChorusPlant()),
	CHORUS_FLOWER(ConfigBlocksItems.enableChorusFruit, new BlockChorusFlower()),
	BONE(ConfigBlocksItems.enableBoneBlock, new BlockBone()),
	RED_NETHERBRICK(ConfigBlocksItems.enableNewNetherBricks, new BlockNewNetherBrick(), ItemBlockGeneric.class), //Also contains chiseled and cracked nether bricks
	NETHER_WART(ConfigBlocksItems.enableNetherwartBlock, new BlockNetherwart()),
	ANCIENT_DEBRIS(ConfigBlocksItems.enableNetherite, new BlockAncientDebris(), ItemBlockUninflammable.class),
	NETHERITE_BLOCK(ConfigBlocksItems.enableNetherite, new BlockNetherite(), ItemBlockUninflammable.class),
	BARRIER(ConfigBlocksItems.enableBarrier, new BlockBarrier()),
	NETHER_GOLD_ORE(ConfigBlocksItems.enableNetherGold, new BlockOreNetherGold()),
	BLUE_ICE(ConfigBlocksItems.enableBlueIce, new BlockBlueIce()),
	SMOOTH_STONE(ConfigBlocksItems.enableSmoothStone, new BlockSmoothStone()),
	SMOOTH_SANDSTONE(ConfigBlocksItems.enableSmoothSandstone, new BlockSmoothSandstone(0)),
	SMOOTH_RED_SANDSTONE(ConfigBlocksItems.enableRedSandstone, new BlockSmoothSandstone(1)),
	SMOOTH_QUARTZ(ConfigBlocksItems.enableSmoothQuartz, new BlockSmoothQuartz()),
	QUARTZ_BRICKS(ConfigBlocksItems.enableQuartzBricks, new BlockQuartzBricks()),
	LOG_STRIPPED(ConfigBlocksItems.enableStrippedLogs, new BlockStrippedOldLog(), ItemBlockGeneric.class),
	LOG2_STRIPPED(ConfigBlocksItems.enableStrippedLogs, new BlockStrippedNewLog(), ItemBlockGeneric.class),
	BARK(ConfigBlocksItems.enableBarkLogs, new BlockWoodBarkOld(), ItemBlockGeneric.class),
	BARK2(ConfigBlocksItems.enableBarkLogs, new BlockWoodBarkNew(), ItemBlockGeneric.class),
	WOOD_STRIPPED(ConfigBlocksItems.enableStrippedLogs && ConfigBlocksItems.enableBarkLogs, new BlockStrippedOldWood(), ItemBlockGeneric.class),
	WOOD2_STRIPPED(ConfigBlocksItems.enableStrippedLogs && ConfigBlocksItems.enableBarkLogs, new BlockStrippedNewWood(), ItemBlockGeneric.class),
	CONCRETE(ConfigBlocksItems.enableConcrete, new BlockConcrete(), ItemBlockGeneric.class),
	CONCRETE_POWDER(ConfigBlocksItems.enableConcrete, new BlockConcretePowder(), ItemBlockGeneric.class),
	COPPER_ORE(ConfigBlocksItems.enableCopper, new BlockCopperOre()),
	DEEPSLATE_COPPER_ORE(ConfigBlocksItems.enableCopper && ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres, new BlockDeepslateOre(COPPER_ORE.get())),
	CORNFLOWER(ConfigBlocksItems.enableCornflower, new BlockCornflower(), ItemBlockFlower.class),
	LILY_OF_THE_VALLEY(ConfigBlocksItems.enableLilyOfTheValley, new BlockLilyOfTheValley(), ItemBlockFlower.class),
	WITHER_ROSE(ConfigBlocksItems.enableWitherRose, new BlockWitherRose(), ItemBlockFlower.class),
	SWEET_BERRY_BUSH(ConfigBlocksItems.enableSweetBerryBushes, new BlockBerryBush(), null),
	WHITE_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(0)),
	ORANGE_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(1)),
	MAGENTA_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(2)),
	LIGHT_BLUE_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(3)),
	YELLOW_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(4)),
	LIME_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(5)),
	PINK_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(6)),
	GRAY_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(7)),
	LIGHT_GRAY_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(8)),
	CYAN_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(9)),
	PURPLE_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(10)),
	BLUE_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(11)),
	BROWN_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(12)),
	GREEN_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(13)),
	RED_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(14)),
	BLACK_GLAZED_TERRACOTTA(ConfigBlocksItems.enableGlazedTerracotta, new BlockGlazedTerracotta(15)),
	COPPER_BLOCK(ConfigBlocksItems.enableCopper, new BlockCopper(), ItemBlockCopper.class),
	LIGHTNING_ROD(EtFuturum.TESTING, new BlockLightningRod()),
	DEEPSLATE(ConfigBlocksItems.enableDeepslate, new BlockDeepslate()),
	COBBLED_DEEPSLATE(ConfigBlocksItems.enableDeepslate, new BlockDeepslateCobbled()),
	POLISHED_DEEPSLATE(ConfigBlocksItems.enableDeepslate, new BlockDeepslatePolished()),
	DEEPSLATE_BRICKS(ConfigBlocksItems.enableDeepslate, new BlockDeepslateBricks(), ItemBlockDeepslate.class),
	TUFF(ConfigBlocksItems.enableTuff, new BlockTuff()),
	RAW_ORE_BLOCK(ConfigBlocksItems.enableRawOres, new BlockRawOre(), ItemBlockRawOre.class),
	SMOOTH_BASALT(ConfigWorld.amethystOuterID == 0 ? ConfigBlocksItems.enableAmethyst : ConfigWorld.amethystOuterID == 1 ? !ConfigBlocksItems.enableTuff : !EtFuturum.hasNetherlicious, new BlockSmoothBasalt()),
	CALCITE(ConfigBlocksItems.enableAmethyst, new BlockCalcite()),
	AMETHYST_BLOCK(ConfigBlocksItems.enableAmethyst, new BlockAmethystBlock()),
	BUDDING_AMETHYST(ConfigBlocksItems.enableAmethyst, new BlockBuddingAmethyst()),
	AMETHYST_CLUSTER_1(ConfigBlocksItems.enableAmethyst, new BlockAmethystCluster(0), ItemBlockAmethystCluster.class),
	AMETHYST_CLUSTER_2(ConfigBlocksItems.enableAmethyst, new BlockAmethystCluster(1), ItemBlockAmethystCluster.class),
	SCULK(ConfigBlocksItems.enableSculk && EtFuturum.TESTING, new BlockSculk()),
	SCULK_CATALYST(ConfigBlocksItems.enableSculk && EtFuturum.TESTING, new BlockSculkCatalyst()),
	TINTED_GLASS(ConfigBlocksItems.enableAmethyst, new BlockTintedGlass()),
	STONE_WALL(ConfigBlocksItems.enableExtraVanillaWalls, new BlockNewWall("stone_wall", new Block[]{Blocks.stonebrick, Blocks.stonebrick, Blocks.sandstone, Blocks.brick_block}, new int[]{0, 1, 0, 0}, new String[]{"stone_brick_wall", "mossy_stone_brick_wall", "sandstone_wall", "brick_wall"}), ItemBlockNewWall.class),
	NETHER_BRICK_WALL(ConfigBlocksItems.enableExtraVanillaWalls, new BlockNewWall("nether_brick_wall", new Block[]{Blocks.nether_brick}, new int[]{0}, null), ItemBlockNewWall.class),
	STONE_WALL_2(ConfigBlocksItems.enableStones, new BlockNewWall("stone_wall_2", new Block[]{STONE.get(), STONE.get(), STONE.get()}, new int[]{1, 3, 5}, new String[]{"granite_wall", "diorite_wall", "andesite_wall"}), ItemBlockNewWall.class),
	RED_SANDSTONE_WALL(ConfigBlocksItems.enableRedSandstone, new BlockNewWall("red_sandstone_wall", new Block[]{RED_SANDSTONE.get()}, new int[]{0}, null), ItemBlockNewWall.class),
	PRISMARINE_WALL(ConfigBlocksItems.enablePrismarine, new BlockNewWall("prismarine_wall", new Block[]{PRISMARINE_BLOCK.get()}, new int[]{0}, null), ItemBlockNewWall.class),
	RED_NETHER_BRICK_WALL(ConfigBlocksItems.enableNewNetherBricks, new BlockNewWall("red_nether_brick_wall", new Block[]{RED_NETHERBRICK.get()}, new int[]{0}, null), ItemBlockNewWall.class),
	END_BRICK_WALL(ConfigBlocksItems.enableChorusFruit, new BlockNewWall("end_brick_wall", new Block[]{END_BRICKS.get()}, new int[]{0}, null), ItemBlockNewWall.class),
	DEEPSLATE_WALL(ConfigBlocksItems.enableDeepslate, new BlockNewWall("deepslate_wall", new Block[]{COBBLED_DEEPSLATE.get(), POLISHED_DEEPSLATE.get()}, new int[]{0, 0}, new String[]{"cobbled_deepslate_wall", "polished_deepslate_wall"}), ItemBlockNewWall.class),
	DEEPSLATE_BRICK_WALL(ConfigBlocksItems.enableDeepslate, new BlockNewWall("deepslate_brick_wall", new Block[]{DEEPSLATE_BRICKS.get(), DEEPSLATE_BRICKS.get()}, new int[]{0, 2}, new String[]{"deepslate_brick_wall", "deepslate_tile_wall"}), ItemBlockNewWall.class),
	DEEPSLATE_COAL_ORE(ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres, new BlockDeepslateOre(Blocks.coal_ore)),
	DEEPSLATE_IRON_ORE(ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres, new BlockDeepslateOre(Blocks.iron_ore)),
	DEEPSLATE_GOLD_ORE(ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres, new BlockDeepslateOre(Blocks.gold_ore)),
	DEEPSLATE_REDSTONE_ORE(ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres, new BlockDeepslateRedstoneOre(false)),
	DEEPSLATE_LIT_REDSTONE_ORE(ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres, new BlockDeepslateRedstoneOre(true)),
	DEEPSLATE_LAPIS_ORE(ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres, new BlockDeepslateOre(Blocks.lapis_ore)),
	DEEPSLATE_DIAMOND_ORE(ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres, new BlockDeepslateOre(Blocks.diamond_ore)),
	DEEPSLATE_EMERALD_ORE(ConfigBlocksItems.enableDeepslate && ConfigBlocksItems.enableDeepslateOres, new BlockDeepslateOre(Blocks.emerald_ore)),
	BREWING_STAND(ConfigBlocksItems.enableBrewingStands, new BlockNewBrewingStand()),
	BEACON(ConfigBlocksItems.enableColourfulBeacons, new BlockNewBeacon()),
	ENCHANTMENT_TABLE(ConfigBlocksItems.enableEnchantingTable, new BlockNewEnchantmentTable()),
	ANVIL(ConfigBlocksItems.enableAnvil, new BlockNewAnvil()),
	DAYLIGHT_DETECTOR(ConfigBlocksItems.enableInvertedDaylightSensor && ConfigBlocksItems.enableOldBaseDaylightSensor, new BlockNewDaylightSensor()),
	FROSTED_ICE(ConfigEnchantsPotions.enableFrostWalker, new BlockFrostedIce()),
	LAVA_CAULDRON(ConfigBlocksItems.enableLavaCauldrons, new BlockLavaCauldron(), null),
	POTION_CAULDRON(ConfigBlocksItems.enablePotionCauldron, new BlockPotionCauldron(), null),
	END_GATEWAY(EtFuturum.TESTING, new BlockEndGateway()),
	OBSERVER(ConfigMixins.enableObservers, new BlockObserver()),
	TARGET(ConfigBlocksItems.enableTarget, new BlockTarget()),
	RED_SANDSTONE_STAIRS(ConfigBlocksItems.enableRedSandstone, new BlockGenericStairs(RED_SANDSTONE.get(), 0).setBlockName(Utils.getUnlocalisedName("red_sandstone_stairs"))),
	PURPUR_STAIRS(ConfigBlocksItems.enableChorusFruit, new BlockGenericStairs(PURPUR_BLOCK.get(), 0).setBlockName(Utils.getUnlocalisedName("purpur_stairs"))),
	RED_SANDSTONE_SLAB(ConfigBlocksItems.enableRedSandstone, new BlockRedSandstoneSlab(false), ItemBlockGenericSlab.class),
	DOUBLE_RED_SANDSTONE_SLAB(ConfigBlocksItems.enableRedSandstone, new BlockRedSandstoneSlab(true), ItemBlockGenericSlab.class),
	PURPUR_SLAB(ConfigBlocksItems.enableChorusFruit, new BlockPurpurSlab(false), ItemBlockGenericSlab.class),
	DOUBLE_PURPUR_SLAB(ConfigBlocksItems.enableChorusFruit, new BlockPurpurSlab(true), ItemBlockGenericSlab.class),
	STONE_SLAB(ConfigBlocksItems.enableExtraVanillaSlabs, new BlockStoneSlab1(false), ItemBlockGenericSlab.class),
	DOUBLE_STONE_SLAB(ConfigBlocksItems.enableExtraVanillaSlabs, new BlockStoneSlab1(true), ItemBlockGenericSlab.class),
	STONE_SLAB_2(ConfigBlocksItems.enableStones, new BlockStoneSlab2(false), ItemBlockGenericSlab.class),
	DOUBLE_STONE_SLAB_2(ConfigBlocksItems.enableStones, new BlockStoneSlab2(true), ItemBlockGenericSlab.class),
	PRISMARINE_STAIRS(ConfigBlocksItems.enablePrismarine, new BlockGenericStairs(PRISMARINE_BLOCK.get(), 0).setBlockName(Utils.getUnlocalisedName("prismarine_stairs"))),
	PRISMARINE_STAIRS_BRICK(ConfigBlocksItems.enablePrismarine, new BlockGenericStairs(PRISMARINE_BLOCK.get(), 1).setBlockName(Utils.getUnlocalisedName("prismarine_stairs_brick"))),
	PRISMARINE_STAIRS_DARK(ConfigBlocksItems.enablePrismarine, new BlockGenericStairs(PRISMARINE_BLOCK.get(), 2).setBlockName(Utils.getUnlocalisedName("prismarine_stairs_dark"))),
	SMOOTH_SANDSTONE_STAIRS(ConfigBlocksItems.enableSmoothSandstone, new BlockGenericStairs(SMOOTH_SANDSTONE.get(), 0).setBlockName(Utils.getUnlocalisedName("smooth_sandstone_stairs"))),
	SMOOTH_RED_SANDSTONE_STAIRS(ConfigBlocksItems.enableRedSandstone, new BlockGenericStairs(SMOOTH_RED_SANDSTONE.get(), 0).setBlockName(Utils.getUnlocalisedName("smooth_red_sandstone_stairs"))),
	SMOOTH_QUARTZ_STAIRS(ConfigBlocksItems.enableSmoothQuartz, new BlockGenericStairs(SMOOTH_QUARTZ.get(), 0).setBlockName(Utils.getUnlocalisedName("smooth_quartz_stairs"))),
	RED_NETHERBRICK_STAIRS(ConfigBlocksItems.enableNewNetherBricks, new BlockGenericStairs(RED_NETHERBRICK.get(), 0).setBlockName(Utils.getUnlocalisedName("red_netherbrick_stairs"))),
	GRANITE_STAIRS(ConfigBlocksItems.enableStones, new BlockGenericStairs(STONE.get(), 1).setBlockName(Utils.getUnlocalisedName("granite_stairs"))),
	POLISHED_GRANITE_STAIRS(ConfigBlocksItems.enableStones, new BlockGenericStairs(STONE.get(), 2).setBlockName(Utils.getUnlocalisedName("polished_granite_stairs"))),
	DIORITE_STAIRS(ConfigBlocksItems.enableStones, new BlockGenericStairs(STONE.get(), 3).setBlockName(Utils.getUnlocalisedName("diorite_stairs"))),
	POLISHED_DIORITE_STAIRS(ConfigBlocksItems.enableStones, new BlockGenericStairs(STONE.get(), 4).setBlockName(Utils.getUnlocalisedName("polished_diorite_stairs"))),
	ANDESITE_STAIRS(ConfigBlocksItems.enableStones, new BlockGenericStairs(STONE.get(), 5).setBlockName(Utils.getUnlocalisedName("andesite_stairs"))),
	POLISHED_ANDESITE_STAIRS(ConfigBlocksItems.enableStones, new BlockGenericStairs(STONE.get(), 6).setBlockName(Utils.getUnlocalisedName("polished_andesite_stairs"))),
	MOSSY_STONE_BRICK_STAIRS(ConfigBlocksItems.enableExtraVanillaStairs, new BlockGenericStairs(Blocks.stonebrick, 1).setBlockName(Utils.getUnlocalisedName("mossy_stone_brick_stairs"))),
	MOSSY_COBBLESTONE_STAIRS(ConfigBlocksItems.enableExtraVanillaStairs, new BlockGenericStairs(Blocks.mossy_cobblestone, 0).setBlockName(Utils.getUnlocalisedName("mossy_cobblestone_stairs"))),
	STONE_STAIRS(ConfigBlocksItems.enableExtraVanillaStairs, new BlockGenericStairs(Blocks.stone, 0).setBlockName(Utils.getUnlocalisedName("stone_stairs"))),
	END_BRICK_STAIRS(ConfigBlocksItems.enableChorusFruit, new BlockGenericStairs(END_BRICKS.get(), 0).setBlockName(Utils.getUnlocalisedName("end_brick_stairs"))),
	SMOOTH_SANDSTONE_SLAB(ConfigBlocksItems.enableSmoothSandstone, new BlockSmoothSandstoneSlab(0, false), ItemBlockGenericSlab.class),
	DOUBLE_SMOOTH_SANDSTONE_SLAB(ConfigBlocksItems.enableSmoothSandstone, new BlockSmoothSandstoneSlab(0, true), ItemBlockGenericSlab.class),
	SMOOTH_RED_SANDSTONE_SLAB(ConfigBlocksItems.enableRedSandstone, new BlockSmoothSandstoneSlab(1, false), ItemBlockGenericSlab.class),
	DOUBLE_SMOOTH_RED_SANDSTONE_SLAB(ConfigBlocksItems.enableSmoothSandstone, new BlockSmoothSandstoneSlab(1, true), ItemBlockGenericSlab.class),
	PRISMARINE_SLAB(ConfigBlocksItems.enablePrismarine, new BlockPrismarineSlab(false), ItemBlockGenericSlab.class),
	DOUBLE_PRISMARINE_SLAB(ConfigBlocksItems.enableSmoothQuartz, new BlockPrismarineSlab(true), ItemBlockGenericSlab.class),
	SMOOTH_QUARTZ_SLAB(ConfigBlocksItems.enableSmoothQuartz, new BlockSmoothQuartzSlab(false), ItemBlockGenericSlab.class),
	DOUBLE_SMOOTH_QUARTZ_SLAB(ConfigBlocksItems.enableSmoothQuartz, new BlockSmoothQuartzSlab(true), ItemBlockGenericSlab.class),
	RED_NETHERBRICK_SLAB(ConfigBlocksItems.enableNewNetherBricks, new BlockRedNetherBrickSlab(false), ItemBlockGenericSlab.class),
	DOUBLE_RED_NETHERBRICK_SLAB(ConfigBlocksItems.enableNewNetherBricks, new BlockRedNetherBrickSlab(true), ItemBlockGenericSlab.class),
	END_BRICK_SLAB(ConfigBlocksItems.enableChorusFruit, new BlockEndBrickSlab(false), ItemBlockGenericSlab.class),
	DOUBLE_END_BRICK_SLAB(ConfigBlocksItems.enableChorusFruit, new BlockEndBrickSlab(true), ItemBlockGenericSlab.class),
	CUT_COPPER_SLAB(ConfigBlocksItems.enableCopper, new BlockCutCopperSlab(false), ItemBlockGenericSlab.class),
	DOUBLE_CUT_COPPER_SLAB(ConfigBlocksItems.enableCopper, new BlockCutCopperSlab(true), ItemBlockGenericSlab.class),
	DEEPSLATE_SLAB(ConfigBlocksItems.enableDeepslate, new BlockDeepslateSlab(false, false), ItemBlockGenericSlab.class),
	DOUBLE_DEEPSLATE_SLAB(ConfigBlocksItems.enableDeepslate, new BlockDeepslateSlab(true, false), ItemBlockGenericSlab.class),
	DEEPSLATE_BRICK_SLAB(ConfigBlocksItems.enableDeepslate, new BlockDeepslateSlab(false, true), ItemBlockGenericSlab.class),
	DOUBLE_DEEPSLATE_BRICK_SLAB(ConfigBlocksItems.enableDeepslate, new BlockDeepslateSlab(true, true), ItemBlockGenericSlab.class),
	NETHERITE_STAIRS(ConfigBlocksItems.enableNetherite, new BlockNetheriteStairs(), ItemBlockUninflammable.class),
	CUT_COPPER_STAIRS(ConfigBlocksItems.enableCopper, new BlockCutCopperStairs(4)),
	EXPOSED_CUT_COPPER_STAIRS(ConfigBlocksItems.enableCopper, new BlockCutCopperStairs(5)),
	WEATHERED_CUT_COPPER_STAIRS(ConfigBlocksItems.enableCopper, new BlockCutCopperStairs(6)),
	OXIDIZED_CUT_COPPER_STAIRS(ConfigBlocksItems.enableCopper, new BlockCutCopperStairs(7)),
	WAXED_CUT_COPPER_STAIRS(ConfigBlocksItems.enableCopper, new BlockCutCopperStairs(12)),
	WAXED_EXPOSED_CUT_COPPER_STAIRS(ConfigBlocksItems.enableCopper, new BlockCutCopperStairs(13)),
	WAXED_WEATHERED_CUT_COPPER_STAIRS(ConfigBlocksItems.enableCopper, new BlockCutCopperStairs(14)),
	WAXED_OXIDIZED_CUT_COPPER_STAIRS(ConfigBlocksItems.enableCopper, new BlockCutCopperStairs(15)),
	COBBLED_DEEPSLATE_STAIRS(ConfigBlocksItems.enableDeepslate, new BlockGenericStairs(COBBLED_DEEPSLATE.get(), 0).setBlockName(Utils.getUnlocalisedName("cobbled_deepslate_stairs"))),
	POLISHED_DEEPSLATE_STAIRS(ConfigBlocksItems.enableDeepslate, new BlockGenericStairs(POLISHED_DEEPSLATE.get(), 0).setBlockName(Utils.getUnlocalisedName("polished_deepslate_stairs"))),
	DEEPSLATE_BRICK_STAIRS(ConfigBlocksItems.enableDeepslate, new BlockGenericStairs(DEEPSLATE_BRICKS.get(), 0).setBlockName(Utils.getUnlocalisedName("deepslate_brick_stairs"))),
	DEEPSLATE_TILE_STAIRS(ConfigBlocksItems.enableDeepslate, new BlockGenericStairs(DEEPSLATE_BRICKS.get(), 2).setBlockName(Utils.getUnlocalisedName("deepslate_tile_stairs")).setStepSound(ModSounds.soundDeepslateTiles)),
	IRON_TRAPDOOR(ConfigBlocksItems.enableIronTrapdoor, new BlockIronTrapdoor()),
	MAGMA(ConfigBlocksItems.enableMagmaBlock, new BlockMagma()),
	BARREL(ConfigBlocksItems.enableBarrel, new BlockBarrel()),
	LANTERN(ConfigBlocksItems.enableLantern, new BlockLantern()),
	SMOKER(ConfigBlocksItems.enableSmoker, new BlockSmoker(false)),
	LIT_SMOKER(ConfigBlocksItems.enableSmoker, new BlockSmoker(true)),
	BLAST_FURNACE(ConfigBlocksItems.enableBlastFurnace, new BlockBlastFurnace(false)),
	LIT_BLAST_FURNACE(ConfigBlocksItems.enableBlastFurnace, new BlockBlastFurnace(true)),
	SHULKER_BOX(ConfigBlocksItems.enableShulkerBoxes, new BlockShulkerBox(), ItemBlockShulkerBox.class),
	SMITHING_TABLE(ConfigBlocksItems.enableSmithingTable, new BlockSmithingTable()),
	COMPOSTER(ConfigBlocksItems.enableComposter, new BlockComposter()),
	STONECUTTER(ConfigBlocksItems.enableStonecutter, new BlockStonecutter(), ItemBlockDecorationWorkbench.class),
	FLETCHING_TABLE(ConfigBlocksItems.enableFletchingTable, new BlockFletchingTable(), ItemBlockDecorationWorkbench.class),
	CARTOGRAPHY_TABLE(ConfigBlocksItems.enableCartographyTable, new BlockCartographyTable(), ItemBlockDecorationWorkbench.class),
	LOOM(ConfigBlocksItems.enableLoom, new BlockLoom(), ItemBlockDecorationWorkbench.class),
	DRIPSTONE_BLOCK(EtFuturum.TESTING, new BlockDripstone()),
	POINTED_DRIPSTONE(EtFuturum.TESTING, new BlockPointedDripstone()),

	//signs
	SIGN_SPRUCE(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, true, 1)),
	WALL_SIGN_SPRUCE(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, false, 1)),
	SIGN_BIRCH(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, true, 2)),
	WALL_SIGN_BIRCH(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, false, 2)),
	SIGN_JUNGLE(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, true, 3)),
	WALL_SIGN_JUNGLE(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, false, 3)),
	SIGN_ACACIA(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, true, 4)),
	WALL_SIGN_ACACIA(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, false, 4)),
	SIGN_DARK_OAK(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, true, 5)),
	WALL_SIGN_DARK_OAK(ConfigBlocksItems.enableSigns, new BlockWoodSign(TileEntityWoodSign.class, false, 5)),

	TRAPDOOR_SPRUCE(ConfigBlocksItems.enableTrapdoors, new BlockWoodTrapdoor(1)),
	TRAPDOOR_BIRCH(ConfigBlocksItems.enableTrapdoors, new BlockWoodTrapdoor(2)),
	TRAPDOOR_JUNGLE(ConfigBlocksItems.enableTrapdoors, new BlockWoodTrapdoor(3)),
	TRAPDOOR_ACACIA(ConfigBlocksItems.enableTrapdoors, new BlockWoodTrapdoor(4)),
	TRAPDOOR_DARK_OAK(ConfigBlocksItems.enableTrapdoors, new BlockWoodTrapdoor(5)),

	BUTTON_SPRUCE(ConfigBlocksItems.enableWoodRedstone, new BlockWoodButton(1)),
	BUTTON_BIRCH(ConfigBlocksItems.enableWoodRedstone, new BlockWoodButton(2)),
	BUTTON_JUNGLE(ConfigBlocksItems.enableWoodRedstone, new BlockWoodButton(3)),
	BUTTON_ACACIA(ConfigBlocksItems.enableWoodRedstone, new BlockWoodButton(4)),
	BUTTON_DARK_OAK(ConfigBlocksItems.enableWoodRedstone, new BlockWoodButton(5)),

	PRESSURE_PLATE_SPRUCE(ConfigBlocksItems.enableWoodRedstone, new BlockWoodPressurePlate(1)),
	PRESSURE_PLATE_BIRCH(ConfigBlocksItems.enableWoodRedstone, new BlockWoodPressurePlate(2)),
	PRESSURE_PLATE_JUNGLE(ConfigBlocksItems.enableWoodRedstone, new BlockWoodPressurePlate(3)),
	PRESSURE_PLATE_ACACIA(ConfigBlocksItems.enableWoodRedstone, new BlockWoodPressurePlate(4)),
	PRESSURE_PLATE_DARK_OAK(ConfigBlocksItems.enableWoodRedstone, new BlockWoodPressurePlate(5)),

	FENCE_SPRUCE(ConfigBlocksItems.enableFences, new BlockWoodFence(1)),
	FENCE_BIRCH(ConfigBlocksItems.enableFences, new BlockWoodFence(2)),
	FENCE_JUNGLE(ConfigBlocksItems.enableFences, new BlockWoodFence(3)),
	FENCE_ACACIA(ConfigBlocksItems.enableFences, new BlockWoodFence(4)),
	FENCE_DARK_OAK(ConfigBlocksItems.enableFences, new BlockWoodFence(5)),

	FENCE_GATE_SPRUCE(ConfigBlocksItems.enableFences, new BlockWoodFenceGate(1)),
	FENCE_GATE_BIRCH(ConfigBlocksItems.enableFences, new BlockWoodFenceGate(2)),
	FENCE_GATE_JUNGLE(ConfigBlocksItems.enableFences, new BlockWoodFenceGate(3)),
	FENCE_GATE_ACACIA(ConfigBlocksItems.enableFences, new BlockWoodFenceGate(4)),
	FENCE_GATE_DARK_OAK(ConfigBlocksItems.enableFences, new BlockWoodFenceGate(5)),

	DOOR_SPRUCE(ConfigBlocksItems.enableDoors, new BlockWoodDoor(1), ItemBlockWoodDoor.class),
	DOOR_BIRCH(ConfigBlocksItems.enableDoors, new BlockWoodDoor(2), ItemBlockWoodDoor.class),
	DOOR_JUNGLE(ConfigBlocksItems.enableDoors, new BlockWoodDoor(3), ItemBlockWoodDoor.class),
	DOOR_ACACIA(ConfigBlocksItems.enableDoors, new BlockWoodDoor(4), ItemBlockWoodDoor.class),
	DOOR_DARK_OAK(ConfigBlocksItems.enableDoors, new BlockWoodDoor(5), ItemBlockWoodDoor.class),

//			"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"};
	WHITE_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(0), ItemBlockDyedBed.class),
	ORANGE_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(1), ItemBlockDyedBed.class),
	MAGENTA_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(2), ItemBlockDyedBed.class),
	LIGHT_BLUE_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(3), ItemBlockDyedBed.class),
	YELLOW_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(4), ItemBlockDyedBed.class),
	LIME_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(5), ItemBlockDyedBed.class),
	PINK_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(6), ItemBlockDyedBed.class),
	GRAY_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(7), ItemBlockDyedBed.class),
	LIGHT_GRAY_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(8), ItemBlockDyedBed.class),
	CYAN_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(9), ItemBlockDyedBed.class),
	PURPLE_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(10), ItemBlockDyedBed.class),
	BLUE_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(11), ItemBlockDyedBed.class),
	BROWN_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(12), ItemBlockDyedBed.class),
	GREEN_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(13), ItemBlockDyedBed.class),
	BLACK_BED(ConfigBlocksItems.enableDyedBeds, new BlockDyedBed(15), ItemBlockDyedBed.class);

	public static final ModBlocks[] BEDS = new ModBlocks[] {WHITE_BED, ORANGE_BED, MAGENTA_BED, LIGHT_BLUE_BED, YELLOW_BED, LIME_BED, PINK_BED, GRAY_BED, LIGHT_GRAY_BED, CYAN_BED,
			PURPLE_BED, BLUE_BED, BROWN_BED, GREEN_BED, BLACK_BED};
	public static final ModBlocks[] DOORS = new ModBlocks[] {DOOR_SPRUCE, DOOR_BIRCH, DOOR_JUNGLE, DOOR_ACACIA, DOOR_DARK_OAK};
	public static final ModBlocks[] FENCE_GATES = new ModBlocks[] {FENCE_GATE_SPRUCE, FENCE_GATE_BIRCH, FENCE_GATE_JUNGLE, FENCE_GATE_ACACIA, FENCE_GATE_DARK_OAK};
	public static final ModBlocks[] FENCES = new ModBlocks[] {FENCE_SPRUCE, FENCE_BIRCH, FENCE_JUNGLE, FENCE_ACACIA, FENCE_DARK_OAK};
	public static final ModBlocks[] PRESSURE_PLATES = new ModBlocks[] {PRESSURE_PLATE_SPRUCE, PRESSURE_PLATE_BIRCH, PRESSURE_PLATE_JUNGLE, PRESSURE_PLATE_ACACIA, PRESSURE_PLATE_DARK_OAK};
	public static final ModBlocks[] BUTTONS = new ModBlocks[] {BUTTON_SPRUCE, BUTTON_BIRCH, BUTTON_JUNGLE, BUTTON_ACACIA, BUTTON_DARK_OAK};
	public static final ModBlocks[] TRAPDOORS = new ModBlocks[] {TRAPDOOR_SPRUCE, TRAPDOOR_BIRCH, TRAPDOOR_JUNGLE, TRAPDOOR_ACACIA, TRAPDOOR_DARK_OAK};
	public static final ModBlocks[] WALL_SIGNS = new ModBlocks[] {WALL_SIGN_SPRUCE, WALL_SIGN_BIRCH, WALL_SIGN_JUNGLE, WALL_SIGN_ACACIA, WALL_SIGN_DARK_OAK};
	public static final ModBlocks[] STANDING_SIGNS = new ModBlocks[] {SIGN_SPRUCE, SIGN_BIRCH, SIGN_JUNGLE, SIGN_ACACIA, SIGN_DARK_OAK};
	public static final ModBlocks[] TERRACOTTA = new ModBlocks[] {WHITE_GLAZED_TERRACOTTA, ORANGE_GLAZED_TERRACOTTA, MAGENTA_GLAZED_TERRACOTTA, LIGHT_BLUE_GLAZED_TERRACOTTA,
			YELLOW_GLAZED_TERRACOTTA, LIME_GLAZED_TERRACOTTA, PINK_GLAZED_TERRACOTTA, GRAY_GLAZED_TERRACOTTA, LIGHT_GRAY_GLAZED_TERRACOTTA, CYAN_GLAZED_TERRACOTTA,
			PURPLE_GLAZED_TERRACOTTA, BLUE_GLAZED_TERRACOTTA, BROWN_GLAZED_TERRACOTTA, GREEN_GLAZED_TERRACOTTA, RED_GLAZED_TERRACOTTA, BLACK_GLAZED_TERRACOTTA};

	/*
	 * Stand-in static final fields because some mods incorrectly referenced my code directly.
	 * They should be using GameRegistry.findBlock but it is what it is I guess.
	 */

	//Immersive Cavegen
	@Deprecated public static final Block deepslate = DEEPSLATE.get();

	//D-Mod
	@Deprecated public static final Block sweet_berry_bush = SWEET_BERRY_BUSH.get();

	//WTF Cave Biomes
	@Deprecated public static final Block prismarine = PRISMARINE_BLOCK.get();
	@Deprecated public static final Block sea_lantern = SEA_LANTERN.get();
	@Deprecated public static final Block red_sandstone = RED_SANDSTONE.get();

	public static void init() {
		for(ModBlocks block : values()) {
			if(block.isEnabled()) {
				if (block.getItemBlock() != null || !block.getHasItemBlock()) {
					GameRegistry.registerBlock(block.get(), block.getItemBlock(), block.name().toLowerCase());
					//This part is used if the getItemBlock() is not null, so we register a custom ItemBlock class as the ItemBlock
					//It is also used if the getItemBlock() == null and getHasItemBlock() is false, meaning we WANT to register it as null, making the block have no inventory item.
				} else {
					GameRegistry.registerBlock(block.get(), block.name().toLowerCase());
					//Used if getItemBlock() == null but getHasItemBlock() is true, registering it with a default inventory item.
				}
			}
		}
	}

	private final boolean isEnabled;
	private final Block theBlock;
	/**
	 * null == default ItemBlock
	 */
	private final Class<? extends ItemBlock> itemBlock;
	/**
	 * Determines if we should register the block with an ItemBlock.
	 * Set to false when the constructor that specifies the ItemBlock is specifically set to false.
	 */
	private boolean hasItemBlock;

	ModBlocks(Boolean enabled, Block block) {
		this(enabled, block, null);
		hasItemBlock = true;
	}

	ModBlocks(Boolean enabled, Block block, Class<? extends ItemBlock> iblock) {
		isEnabled = enabled;
		theBlock = block;
		itemBlock = iblock;
		hasItemBlock = iblock != null;
	}

	/**
	 * If this is false, the block is initialized without an inventory item, or ItemBlock.
	 */
	public boolean getHasItemBlock() {
		return hasItemBlock;
	}
	public Block get() {
		return theBlock;
	}
	public Class<? extends ItemBlock> getItemBlock() {
		return itemBlock;
	}
	public boolean isEnabled() {
		return isEnabled;
	}

	public ItemStack newItemStack() {
		return newItemStack(1);
	}

	public ItemStack newItemStack(int count) {
		return newItemStack(count, 0);
	}

	public ItemStack newItemStack(int count, int meta) {
		return new ItemStack(this.get(), count, meta);
	}
}