package ganymedes01.etfuturum.blocks;

import java.util.Random;

import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockCutCopperStairs extends BlockGenericStairs implements IDegradable {

	public BlockCutCopperStairs(int p_i45428_2_) {
		super(ModBlocks.copper_block, p_i45428_2_);
		String name = "cut_copper_stairs";
		String subtype = "";
		switch(meta) {
			case 5: subtype = "exposed"; break;
			case 6: subtype = "weathered"; break;
			case 7: subtype = "oxidized"; break;
			case 12: subtype = "waxed"; break;
			case 13: subtype = "waxed_exposed"; break;
			case 14: subtype = "waxed_weathered"; break;
		}
		setBlockName(Utils.getUnlocalisedName(subtype + (subtype.equals("") ? "" : "_") + name));
		setTickRandomly(meta <= 7 ? true : false);
	}
	
	public Block getNextWeatherStage() {
		switch(meta) {
		case 4: return ModBlocks.exposed_cut_copper_stairs;
		case 5: return ModBlocks.weathered_cut_copper_stairs;
		case 6: return ModBlocks.oxidized_cut_copper_stairs;
		default: return null;
		}
	}
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote)
            return;
        if (getDegredationState(0) == -1)
        	return;
        tickDegradation(world, x, y, z, rand);
    }

    private void tickDegradation(World world, int x, int y, int z, Random random) {
    	float f = 0.05688889F;
    	if (random.nextFloat() < f) {
    		this.tryDegrade(world, x, y, z, random);
    	}
    }
    
	private void tryDegrade(World world, int x, int y, int z, Random random) {
	   int i = getDegredationState(0); //Different since stairs are blocks and not meta variations. Using 0 since the state checker uses the block instance.
	   int j = 0;
	   int k = 0;
	   
	   for(int x1 = -4; x1 <= 4; x1++) {
	       for(int y1 = -4; y1 <= 4; y1++) {
	           for(int z1 = -4; z1 <= 4; z1++) {
	        	   Block block = world.getBlock(x1 + x, y1 + y, z1 + z);
	               if(block instanceof IDegradable && (x1 != 0 || y1 != 0 || z1 != 0) && Math.abs(x1) + Math.abs(y1) + Math.abs(z1) <= 4) {
	            	   int m = ((IDegradable)block).getDegredationState(world.getBlockMetadata(x1, y1, z1));
	                   
	                   if(m == -1)
	                	   continue;
	                   
	                   if (m < i) {
	                      return;
	                   }
	          
	                   if (m > i) {
	                      ++k;
	                   } else {
	                      ++j;
	                   }
	               }
	           }
	       }
	   }

       float f = (float)(k + 1) / (float)(k + j + 1);
       float g = f * f * (i == 0 ? 0.75F : 1F);
       if (random.nextFloat() < g) {
          world.setBlock(x, y, z, getNextWeatherStage(), world.getBlockMetadata(x, y, z), 2);
       }
    }

	@Override
	public int getDegredationState(int meta) {
		return this == ModBlocks.cut_copper_stairs ? 0 : this == ModBlocks.exposed_cut_copper_stairs ? 1 : this == ModBlocks.weathered_cut_copper_stairs ? 2 : this == ModBlocks.oxidized_cut_copper_stairs ? 3 : -1;
	}
}
