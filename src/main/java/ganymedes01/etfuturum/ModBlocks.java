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
import ganymedes01.etfuturum.blocks.BlockLantern;
import ganymedes01.etfuturum.blocks.BlockNetherite;
import ganymedes01.etfuturum.blocks.BlockNetheriteStairs;
import ganymedes01.etfuturum.blocks.BlockOreNetherGold;
import ganymedes01.etfuturum.blocks.BlockPrismarineStairs;
import ganymedes01.etfuturum.blocks.BlockSilkedMushroom;
import ganymedes01.etfuturum.blocks.BlockSmoker;
import ganymedes01.etfuturum.blocks.BlockStrippedNewLog;
import ganymedes01.etfuturum.blocks.BlockStrippedNewWood;
import ganymedes01.etfuturum.blocks.BlockStrippedOldLog;
import ganymedes01.etfuturum.blocks.BlockStrippedOldWood;
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
import ganymedes01.etfuturum.blocks.PrismarineSlabBrick;
import ganymedes01.etfuturum.blocks.PrismarineSlabDark;
import ganymedes01.etfuturum.blocks.PurpurBlock;
import ganymedes01.etfuturum.blocks.PurpurPillar;
import ganymedes01.etfuturum.blocks.PurpurSlab;
import ganymedes01.etfuturum.blocks.PurpurStairs;
import ganymedes01.etfuturum.blocks.RedSandstone;
import ganymedes01.etfuturum.blocks.RedSandstoneSlab;
import ganymedes01.etfuturum.blocks.RedSandstoneStairs;
import ganymedes01.etfuturum.blocks.SeaLantern;
import ganymedes01.etfuturum.blocks.SlimeBlock;
import ganymedes01.etfuturum.blocks.Sponge;
import ganymedes01.etfuturum.blocks.Stone;
import ganymedes01.etfuturum.tileentities.TileEntityWoodSign;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;

public class ModBlocks {

    public static final Block stone = new Stone();
    public static final Block iron_trapdoor = new IronTrapdoor();
    public static final Block prismarine = new PrismarineBlocks();
    public static final Block sea_lantern = new SeaLantern();
    public static final Block inverted_daylight_detector = new InvertedDaylightDetector();
    public static final Block red_sandstone = new RedSandstone();
    public static final Block red_sandstone_slab = new RedSandstoneSlab();
    public static final Block red_sandstone_stairs = new RedSandstoneStairs();
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
    public static final Block purpur_stairs = new PurpurStairs();
    public static final Block purpur_slab = new PurpurSlab();
    public static final Block end_bricks = new EndBricks();
    public static final Block grass_path = new GrassPath();
    public static final Block end_rod = new EndRod();
    public static final Block chorus_plant = new ChorusPlant();
    public static final Block chorus_flower = new ChorusFlower();
    public static final Block crying_obsidian = new CryingObsidian();
    public static final Block frosted_ice = new FrostedIce();
    public static final Block brewing_stand = new NewBrewingStand();
    public static final Block rose = new OldRose();
    public static final Block beacon = new NewBeacon();
    public static final Block enchantment_table = new NewEnchantmentTable();
    public static final Block anvil = new NewAnvil();
    public static final Block daylight_sensor = new NewDaylightSensor();
    
    public static final Block bone_block = new BoneBlock();
    public static final Block magma_block = new MagmaBlock();
    public static final Block new_nether_brick = new NewNetherBrick();
    public static final Block nether_wart_block = new NetherwartBlock();
    public static final Block ancient_debris = new BlockAncientDebris();
    public static final Block netherite_block = new BlockNetherite();
    public static final Block netherite_stairs = new BlockNetheriteStairs();
	public static final Block barrel = new BlockBarrel();
	public static final Block lantern = new BlockLantern();
	public static final Block smoker = new BlockSmoker(false);
	public static final Block lit_smoker = new BlockSmoker(true);
	public static final Block blast_furnace = new BlockBlastFurnace(false);
	public static final Block lit_blast_furnace = new BlockBlastFurnace(true);
	public static final Block barrier = new BlockBarrier();
	public static final Block nether_gold_ore = new BlockOreNetherGold();
	public static final Block blue_ice = new BlockBlueIce();
    
    public static final Block concrete = new BlockConcrete();
    public static final Block concrete_powder = new BlockConcretePowder();
    
    //do prismarine slab/stairs
    public static final Block prismarineStairsRough = new BlockPrismarineStairs(prismarine, 0).setBlockName("prismarine_stairs");
    public static final Block prismarineStairsBrick = new BlockPrismarineStairs(prismarine, 1).setBlockName("prismarine_stairs_brick");
    public static final Block prismarineStairsDark = new BlockPrismarineStairs(prismarine, 2).setBlockName("prismarine_stairs_dark");
    public static final Block prismarineSlabRough = new PrismarineSlab();
    public static final Block prismarineSlabBrick = new PrismarineSlabBrick();
    public static final Block prismarineSlabDark = new PrismarineSlabDark();
    
    //wood trapdoors, buttons, pressure plates
    
    public static final Block log_stripped = new BlockStrippedOldLog();
    public static final Block log2_stripped = new BlockStrippedNewLog();
    public static final Block log_bark = new BlockWoodBarkOld();
    public static final Block log2_bark = new BlockWoodBarkNew();
    public static final Block wood_stripped = new BlockStrippedOldWood();
    public static final Block wood2_stripped = new BlockStrippedNewWood();
    
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