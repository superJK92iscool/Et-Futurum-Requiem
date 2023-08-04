package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCutCopperStairs extends BasicStairs implements IDegradable {

	public BlockCutCopperStairs(int p_i45428_2_) {
		super(ModBlocks.COPPER_BLOCK.get(), p_i45428_2_);
		String name = "cut_copper";
		String subtype;
		switch (meta) {
			default:
				subtype = "";
				break;
			case 5:
				subtype = "exposed";
				break;
			case 6:
				subtype = "weathered";
				break;
			case 7:
				subtype = "oxidized";
				break;
			case 12:
				subtype = "waxed";
				break;
			case 13:
				subtype = "waxed_exposed";
				break;
			case 14:
				subtype = "waxed_weathered";
				break;
			case 15:
				subtype = "waxed_oxidized";
				break;
		}
		setBlockName(subtype + (subtype.equals("") ? "" : "_") + name);
		setTickRandomly(meta < 7);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		tickDegradation(world, x, y, z, rand);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		return tryWaxOnWaxOff(world, x, y, z, entityPlayer);
	}

	@Override
	public int getCopperMeta(int meta) {
		if(this == ModBlocks.CUT_COPPER_STAIRS.get()) return 4;
		if(this == ModBlocks.EXPOSED_CUT_COPPER_STAIRS.get()) return 5;
		if(this == ModBlocks.WEATHERED_CUT_COPPER_STAIRS.get()) return 6;
		if(this == ModBlocks.OXIDIZED_CUT_COPPER_STAIRS.get()) return 7;
		if(this == ModBlocks.WAXED_CUT_COPPER_STAIRS.get()) return 12;
		if(this == ModBlocks.WAXED_EXPOSED_CUT_COPPER_STAIRS.get()) return 13;
		if(this == ModBlocks.WAXED_WEATHERED_CUT_COPPER_STAIRS.get()) return 14;
		if(this == ModBlocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS.get()) return 15;
		return 0;
	}
	
	public Block getCopperBlockFromMeta(int i) {
		switch(i) {
		case 4: return ModBlocks.CUT_COPPER_STAIRS.get();
		case 5: return ModBlocks.EXPOSED_CUT_COPPER_STAIRS.get();
		case 6: return ModBlocks.WEATHERED_CUT_COPPER_STAIRS.get();
		case 7: return ModBlocks.OXIDIZED_CUT_COPPER_STAIRS.get();
		case 12: return ModBlocks.WAXED_CUT_COPPER_STAIRS.get();
		case 13: return ModBlocks.WAXED_EXPOSED_CUT_COPPER_STAIRS.get();
		case 14: return ModBlocks.WAXED_WEATHERED_CUT_COPPER_STAIRS.get();
		case 15: return ModBlocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS.get();
		default: return ModBlocks.CUT_COPPER_STAIRS.get();
		}
	}
}
