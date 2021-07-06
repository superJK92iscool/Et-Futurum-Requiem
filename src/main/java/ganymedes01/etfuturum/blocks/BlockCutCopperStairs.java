package ganymedes01.etfuturum.blocks;

import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;

import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockCutCopperStairs extends BlockGenericStairs implements IDegradable {

	public BlockCutCopperStairs(int p_i45428_2_) {
		super(ModBlocks.copper_block, p_i45428_2_);
		String name = "cut_copper_stairs";
		String subtype;
		switch(meta) {
			default: subtype = ""; break;
			case 5: subtype = "exposed"; break;
			case 6: subtype = "weathered"; break;
			case 7: subtype = "oxidized"; break;
			case 12: subtype = "waxed"; break;
			case 13: subtype = "waxed_exposed"; break;
			case 14: subtype = "waxed_weathered"; break;
			case 15: subtype = "waxed_oxidized"; break;
		}
		setBlockName(Utils.getUnlocalisedName(subtype + (subtype.equals("") ? "" : "_") + name));
		setTickRandomly(meta < 7 ? true : false);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (world.isRemote)
			return;
		tickDegradation(world, x, y, z, rand);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
	{
		return tryWaxOnWaxOff(world, x, y, z, entityPlayer);
	}

	@Override
	public int getCopperMeta(int meta) {
		if(this == ModBlocks.cut_copper_stairs) return 4;
		if(this == ModBlocks.exposed_cut_copper_stairs) return 5; 
		if(this == ModBlocks.weathered_cut_copper_stairs) return 6;
		if(this == ModBlocks.oxidized_cut_copper_stairs) return 7;
		if(this == ModBlocks.waxed_cut_copper_stairs) return 12;
		if(this == ModBlocks.waxed_exposed_cut_copper_stairs) return 13;
		if(this == ModBlocks.waxed_weathered_cut_copper_stairs) return 14;
		if(this == ModBlocks.waxed_oxidized_cut_copper_stairs) return 15;
		return 0;
	}
	
	public Block getCopperBlockFromMeta(int i) {
		switch(i) {
		case 4: return ModBlocks.cut_copper_stairs;
		case 5: return ModBlocks.exposed_cut_copper_stairs;
		case 6: return ModBlocks.weathered_cut_copper_stairs;
		case 7: return ModBlocks.oxidized_cut_copper_stairs;
		case 12: return ModBlocks.waxed_cut_copper_stairs;
		case 13: return ModBlocks.waxed_exposed_cut_copper_stairs;
		case 14: return ModBlocks.waxed_weathered_cut_copper_stairs;
		case 15: return ModBlocks.waxed_oxidized_cut_copper_stairs;
		default: return ModBlocks.cut_copper_stairs;
		}
	}
}
