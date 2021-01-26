package ganymedes01.etfuturum;

import java.lang.reflect.Field;

import cpw.mods.fml.common.registry.GameRegistry;
import ganymedes01.etfuturum.blocks.BlockAncientDebris;
import ganymedes01.etfuturum.blocks.BlockBanner;
import ganymedes01.etfuturum.blocks.BlockBarrel;
import ganymedes01.etfuturum.blocks.BlockBarrier;
import ganymedes01.etfuturum.blocks.BlockBeetroot;
import ganymedes01.etfuturum.blocks.BlockBlastFurnace;
import ganymedes01.etfuturum.blocks.BlockBlueIce;
import ganymedes01.etfuturum.blocks.BlockConcrete;
import ganymedes01.etfuturum.blocks.BlockConcretePowder;
import ganymedes01.etfuturum.blocks.BlockCornflower;
import ganymedes01.etfuturum.blocks.BlockEndBrickSlab;
import ganymedes01.etfuturum.blocks.BlockGenericStairs;
import ganymedes01.etfuturum.blocks.BlockLantern;
import ganymedes01.etfuturum.blocks.BlockLilyOfTheValley;
import ganymedes01.etfuturum.blocks.BlockNetherite;
import ganymedes01.etfuturum.blocks.BlockNetheriteStairs;
import ganymedes01.etfuturum.blocks.BlockOreNetherGold;
import ganymedes01.etfuturum.blocks.BlockQuartzBricks;
import ganymedes01.etfuturum.blocks.BlockRedNetherBrickSlab;
import ganymedes01.etfuturum.blocks.BlockSilkedMushroom;
import ganymedes01.etfuturum.blocks.BlockSmoker;
import ganymedes01.etfuturum.blocks.BlockSmoothQuartz;
import ganymedes01.etfuturum.blocks.BlockSmoothQuartzSlab;
import ganymedes01.etfuturum.blocks.BlockSmoothSandstone;
import ganymedes01.etfuturum.blocks.BlockSmoothSandstoneSlab;
import ganymedes01.etfuturum.blocks.BlockSmoothStone;
import ganymedes01.etfuturum.blocks.BlockStoneSlab1;
import ganymedes01.etfuturum.blocks.BlockStoneSlab2;
import ganymedes01.etfuturum.blocks.BlockStrippedNewLog;
import ganymedes01.etfuturum.blocks.BlockStrippedNewWood;
import ganymedes01.etfuturum.blocks.BlockStrippedOldLog;
import ganymedes01.etfuturum.blocks.BlockStrippedOldWood;
import ganymedes01.etfuturum.blocks.BlockWitherRose;
import ganymedes01.etfuturum.blocks.BlockWoodBarkNew;
import ganymedes01.etfuturum.blocks.BlockWoodBarkOld;
import ganymedes01.etfuturum.blocks.BlockWoodButton;
import ganymedes01.etfuturum.blocks.BlockWoodDoor;
import ganymedes01.etfuturum.blocks.BlockWoodFence;
import ganymedes01.etfuturum.blocks.BlockWoodFenceGate;
import ganymedes01.etfuturum.blocks.BlockWoodPressurePlate;
import ganymedes01.etfuturum.blocks.BlockWoodSign;
import ganymedes01.etfuturum.blocks.BlockWoodTrapdoor;
import ganymedes01.etfuturum.blocks.BoneBlock;
import ganymedes01.etfuturum.blocks.ChorusFlower;
import ganymedes01.etfuturum.blocks.ChorusPlant;
import ganymedes01.etfuturum.blocks.CoarseDirt;
import ganymedes01.etfuturum.blocks.CryingObsidian;
import ganymedes01.etfuturum.blocks.EndBricks;
import ganymedes01.etfuturum.blocks.EndRod;
import ganymedes01.etfuturum.blocks.FrostedIce;
import ganymedes01.etfuturum.blocks.GrassPath;
import ganymedes01.etfuturum.blocks.InvertedDaylightDetector;
import ganymedes01.etfuturum.blocks.IronTrapdoor;
import ganymedes01.etfuturum.blocks.MagmaBlock;
import ganymedes01.etfuturum.blocks.NetherwartBlock;
import ganymedes01.etfuturum.blocks.NewAnvil;
import ganymedes01.etfuturum.blocks.NewBeacon;
import ganymedes01.etfuturum.blocks.NewBrewingStand;
import ganymedes01.etfuturum.blocks.NewDaylightSensor;
import ganymedes01.etfuturum.blocks.NewEnchantmentTable;
import ganymedes01.etfuturum.blocks.NewNetherBrick;
import ganymedes01.etfuturum.blocks.OldGravel;
import ganymedes01.etfuturum.blocks.OldRose;
import ganymedes01.etfuturum.blocks.PrismarineBlocks;
import ganymedes01.etfuturum.blocks.PrismarineSlab;
import ganymedes01.etfuturum.blocks.PurpurBlock;
import ganymedes01.etfuturum.blocks.PurpurPillar;
import ganymedes01.etfuturum.blocks.PurpurSlab;
import ganymedes01.etfuturum.blocks.RedSandstone;
import ganymedes01.etfuturum.blocks.RedSandstoneSlab;
import ganymedes01.etfuturum.blocks.SeaLantern;
import ganymedes01.etfuturum.blocks.SlimeBlock;
import ganymedes01.etfuturum.blocks.Sponge;
import ganymedes01.etfuturum.blocks.Stone;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.tileentities.TileEntityWoodSign;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;

public class ModBlocks {

    public static final Block stone = new Stone();
    public static final Block prismarine = new PrismarineBlocks();
    public static final Block sea_lantern = new SeaLantern();
    public static final Block inverted_daylight_detector = new InvertedDaylightDetector();
    public static final Block red_sandstone = new RedSandstone();
    public static final Block brown_mushroom_block = new BlockSilkedMushroom(Blocks.brown_mushroom_block, "brown");
    public static final Block red_mushroom_block = new BlockSilkedMushroom(Blocks.red_mushroom_block, "red");
    public static final Block coarse_dirt = new CoarseDirt();
    public static final Block banner = new BlockBanner();
    public static final Block slime = new SlimeBlock();
    public static final Block sponge = new Sponge();
    public static final Block old_gravel = new OldGravel();
    public static final Block beetroot = new BlockBeetroot();
    public static final Block purpur_block = new PurpurBlock();
    public static final Block purpur_pillar = new PurpurPillar();
    public static final Block end_bricks = new EndBricks();
    public static final Block grass_path = new GrassPath();
    public static final Block end_rod = new EndRod();
    public static final Block chorus_plant = new ChorusPlant();
    public static final Block chorus_flower = new ChorusFlower();
    public static final Block crying_obsidian = new CryingObsidian();
    public static final Block rose = new OldRose();
    
    public static final Block bone_block = new BoneBlock();
    public static final Block new_nether_brick = new NewNetherBrick();
    public static final Block nether_wart_block = new NetherwartBlock();
    public static final Block ancient_debris = new BlockAncientDebris();
    public static final Block netherite_block = new BlockNetherite();
	public static final Block barrier = new BlockBarrier();
	public static final Block nether_gold_ore = new BlockOreNetherGold();
	public static final Block blue_ice = new BlockBlueIce();
	public static final Block smooth_stone = new BlockSmoothStone();
	public static final Block smooth_sandstone = new BlockSmoothSandstone(0);
	public static final Block smooth_red_sandstone = new BlockSmoothSandstone(1);
	public static final Block smooth_quartz = new BlockSmoothQuartz();
	public static final Block quartz_bricks = new BlockQuartzBricks();
    public static final Block log_stripped = new BlockStrippedOldLog();
    public static final Block log2_stripped = new BlockStrippedNewLog();
    public static final Block log_bark = new BlockWoodBarkOld();
    public static final Block log2_bark = new BlockWoodBarkNew();
    public static final Block wood_stripped = new BlockStrippedOldWood();
    public static final Block wood2_stripped = new BlockStrippedNewWood();
    public static final Block concrete = new BlockConcrete();
    public static final Block concrete_powder = new BlockConcretePowder();
    
    //technical blocks
    public static final Block brewing_stand = new NewBrewingStand();
    public static final Block beacon = new NewBeacon();
    public static final Block enchantment_table = new NewEnchantmentTable();
    public static final Block anvil = new NewAnvil();
    public static final Block daylight_sensor = new NewDaylightSensor();
    public static final Block frosted_ice = new FrostedIce();
    
    //do slab/stairs
    public static final Block red_sandstone_stairs = new BlockGenericStairs(red_sandstone, 0, ConfigurationHandler.enableRedSandstone).setBlockName(Utils.getUnlocalisedName("red_sandstone_stairs"));
    public static final Block purpur_stairs = new BlockGenericStairs(purpur_block, 0, ConfigurationHandler.enableChorusFruit).setBlockName(Utils.getUnlocalisedName("purpur_stairs"));
    public static final Block red_sandstone_slab = new RedSandstoneSlab(false);
    public static final Block double_red_sandstone_slab = new RedSandstoneSlab(true);
    public static final Block purpur_slab = new PurpurSlab(false);
    public static final Block double_purpur_slab = new PurpurSlab(true);
    
    public static final Block generic_slab = new BlockStoneSlab1(false);
    public static final Block double_generic_slab = new BlockStoneSlab1(true);
    public static final Block stone_slab = new BlockStoneSlab2(false);
    public static final Block double_stone_slab = new BlockStoneSlab2(true);
    
    public static final Block rough_prismarine_stairs = new BlockGenericStairs(prismarine, 0, ConfigurationHandler.enablePrismarine).setBlockName(Utils.getUnlocalisedName("prismarine_stairs"));
    public static final Block brick_prismarine_stairs = new BlockGenericStairs(prismarine, 1, ConfigurationHandler.enablePrismarine).setBlockName(Utils.getUnlocalisedName("prismarine_stairs_brick"));
    public static final Block dark_prismarine_stairs = new BlockGenericStairs(prismarine, 2, ConfigurationHandler.enablePrismarine).setBlockName(Utils.getUnlocalisedName("prismarine_stairs_dark"));
    public static final Block smooth_sandstone_stairs = new BlockGenericStairs(smooth_sandstone, 0, ConfigurationHandler.enableSmoothSandstone).setBlockName(Utils.getUnlocalisedName("smooth_sandstone_stairs"));
    public static final Block smooth_red_sandstone_stairs = new BlockGenericStairs(smooth_red_sandstone, 0, ConfigurationHandler.enableRedSandstone && ConfigurationHandler.enableSmoothSandstone).setBlockName(Utils.getUnlocalisedName("smooth_red_sandstone_stairs"));
    public static final Block smooth_quartz_stairs = new BlockGenericStairs(smooth_quartz, 0, ConfigurationHandler.enableSmoothQuartz).setBlockName(Utils.getUnlocalisedName("smooth_quartz_stairs"));
    public static final Block red_nether_brick_stairs = new BlockGenericStairs(new_nether_brick, 0, ConfigurationHandler.enableNewNetherBricks).setBlockName(Utils.getUnlocalisedName("red_netherbrick_stairs"));
    public static final Block granite_stairs = new BlockGenericStairs(stone, 1, ConfigurationHandler.enableStones).setBlockName(Utils.getUnlocalisedName("granite_stairs"));
    public static final Block polished_granite_stairs = new BlockGenericStairs(stone, 2, ConfigurationHandler.enableStones).setBlockName(Utils.getUnlocalisedName("polished_granite_stairs"));
    public static final Block diorite_stairs = new BlockGenericStairs(stone, 3, ConfigurationHandler.enableStones).setBlockName(Utils.getUnlocalisedName("diorite_stairs"));
    public static final Block polished_diorite_stairs = new BlockGenericStairs(stone, 4, ConfigurationHandler.enableStones).setBlockName(Utils.getUnlocalisedName("polished_diorite_stairs"));
    public static final Block andesite_stairs = new BlockGenericStairs(stone, 5, ConfigurationHandler.enableStones).setBlockName(Utils.getUnlocalisedName("andesite_stairs"));
    public static final Block polished_andesite_stairs = new BlockGenericStairs(stone, 6, ConfigurationHandler.enableStones).setBlockName(Utils.getUnlocalisedName("polished_andesite_stairs"));
    public static final Block mossy_stone_brick_stairs = new BlockGenericStairs(Blocks.stonebrick, 1, ConfigurationHandler.enableGenericStairs).setBlockName(Utils.getUnlocalisedName("mossy_stone_brick_stairs"));
    public static final Block mossy_cobblestone_stairs = new BlockGenericStairs(Blocks.mossy_cobblestone, 0, ConfigurationHandler.enableGenericStairs).setBlockName(Utils.getUnlocalisedName("mossy_cobblestone_stairs"));
    public static final Block stone_stairs = new BlockGenericStairs(Blocks.stone, 0, ConfigurationHandler.enableGenericStairs).setBlockName(Utils.getUnlocalisedName("stone_stairs"));
    public static final Block end_brick_stairs = new BlockGenericStairs(ModBlocks.end_bricks, 0, ConfigurationHandler.enableChorusFruit).setBlockName(Utils.getUnlocalisedName("end_brick_stairs"));
    
    public static final Block cornflower = new BlockCornflower();
    public static final Block lily_of_the_valley = new BlockLilyOfTheValley();
    public static final Block wither_rose = new BlockWitherRose();
    
    public static final Block smooth_sandstone_slab = new BlockSmoothSandstoneSlab(0, false);
    public static final Block double_smooth_sandstone_slab = new BlockSmoothSandstoneSlab(0, true);
    public static final Block smooth_red_sandstone_slab = new BlockSmoothSandstoneSlab(1, false);
    public static final Block double_smooth_red_sandstone_slab = new BlockSmoothSandstoneSlab(1, true);
    public static final Block prismarine_slab = new PrismarineSlab(false);
    public static final Block double_prismarine_slab = new PrismarineSlab(true);
    public static final Block smooth_quartz_slab = new BlockSmoothQuartzSlab(false);
    public static final Block double_smooth_quartz_slab = new BlockSmoothQuartzSlab(true);
    public static final Block red_nether_brick_slab = new BlockRedNetherBrickSlab(false);
    public static final Block double_red_nether_brick_slab = new BlockRedNetherBrickSlab(true);
    public static final Block end_brick_slab = new BlockEndBrickSlab(false);
    public static final Block double_end_brick_slab = new BlockEndBrickSlab(true);
    public static final Block netherite_stairs = new BlockNetheriteStairs();
    
    //Mechanic/Functional blocks
    public static final Block iron_trapdoor = new IronTrapdoor();
    
    public static final Block magma_block = new MagmaBlock();
	public static final Block barrel = new BlockBarrel();
	public static final Block lantern = new BlockLantern();
	public static final Block smoker = new BlockSmoker(false);
	public static final Block lit_smoker = new BlockSmoker(true);
	public static final Block blast_furnace = new BlockBlastFurnace(false);
	public static final Block lit_blast_furnace = new BlockBlastFurnace(true);
	
    public static final String[] woodTypes = new String[] {"oak", "spruce", "birch", "jungle", "acacia", "dark_oak"/*, "crimson", "warped"*/};
    public static final Block[] doors = new Block[woodTypes.length - 1];
    public static final Block[] fences = new Block[woodTypes.length - 1];
    public static final Block[] gates = new Block[woodTypes.length - 1];
    
    public static final Block[] pressure_plates = new Block[woodTypes.length - 1];
    public static final Block[] buttons = new Block[woodTypes.length - 1];
    public static final Block[] trapdoors = new Block[woodTypes.length - 1];
	public static final Block[] signs = new Block[woodTypes.length - 1];
	public static final Block[] wall_signs = new Block[woodTypes.length - 1];

    static {
		for (int i = 0; i < signs.length; i++) {
			signs[i] = new BlockWoodSign(TileEntityWoodSign.class, true, i + 1);
			wall_signs[i] = new BlockWoodSign(TileEntityWoodSign.class, false, i + 1);
		}
		
        for (int i = 0; i < doors.length; i++)
            doors[i] = new BlockWoodDoor(i + 1);

        for (int i = 0; i < fences.length; i++)
            fences[i] = new BlockWoodFence(i + 1);

        for (int i = 0; i < gates.length; i++)
            gates[i] = new BlockWoodFenceGate(i + 1);
        
        for (int i = 0; i < pressure_plates.length; i++)
            pressure_plates[i] = new BlockWoodPressurePlate(i + 1);
        
        for (int i = 0; i < buttons.length; i++)
            buttons[i] = new BlockWoodButton(i + 1);
        
        for (int i = 0; i < trapdoors.length; i++)
            trapdoors[i] = new BlockWoodTrapdoor(i + 1);
    }

    public static void init() {        
        try {
            for (Field f : ModBlocks.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Block)
                    registerBlock((Block) obj);
                else if (obj instanceof Block[])
                    for (Block block : (Block[]) obj)
                        if (block != null)
                            registerBlock(block);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerBlock(Block block) {
        if (!(block instanceof IConfigurable) || ((IConfigurable) block).isEnabled()) {
            String name = block.getUnlocalizedName();
            String[] strings = name.split("\\.");

            if (block instanceof ISubBlocksBlock)
                GameRegistry.registerBlock(block, ((ISubBlocksBlock) block).getItemBlockClass(), strings[strings.length - 1]);
            else
                GameRegistry.registerBlock(block, strings[strings.length - 1]);

			if (block instanceof IBurnableBlock) {
				int[] i = ((IBurnableBlock) block).getFireInfo();
				if(i != null)
					Blocks.fire.setFireInfo(block, i[0], i[1]);
			}
        }
    }

    public static interface ISubBlocksBlock {

        Class<? extends ItemBlock> getItemBlockClass();
    }

	
	public static interface IBurnableBlock {
		
		int[] getFireInfo();
	}
}