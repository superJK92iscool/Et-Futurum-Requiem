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

public class BlockCutCopperStairs extends BlockGenericStairs implements IConfigurable{

	public BlockCutCopperStairs(int p_i45428_2_) {
		super(ModBlocks.copper_block, p_i45428_2_, ConfigurationHandler.enableCopper);
		String name = "cut_copper_stairs";
		String subtype = "";
		switch(meta) {
			case 5: subtype = "lightly_weathered"; break;
			case 6: subtype = "semi_weathered"; break;
			case 7: subtype = "weathered"; break;
			case 12: subtype = "waxed"; break;
			case 13: subtype = "waxed_lightly_weathered"; break;
			case 14: subtype = "waxed_semi_weathered"; break;
		}
		setBlockName(Utils.getUnlocalisedName(subtype + (subtype.equals("") ? "" : "_") + name));
	}
	
	public Block getNextWeatherStage() {
		switch(meta) {
		case 4: return ModBlocks.lightly_weathered_cut_copper_stairs;
		case 5: return ModBlocks.semi_weathered_cut_copper_stairs;
		case 6: return ModBlocks.weathered_cut_copper_stairs;
		default: return null;
		}
	}

    public void onBlockAdded(World world, int x, int y, int z)
    {
        if (meta > 7 || meta % 4 == 3)
        	return;
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    	
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote)
            return;
        if (meta > 7 || meta % 4 == 3)
        	return;
        if(world.getBlock(x, y, z) == this) {
        	world.setBlock(x, y, z, getNextWeatherStage(), world.getBlockMetadata(x, y, z), 2);
        }
        if(!(meta + 1 > 7 || (meta + 1) % 4 == 3))
            world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    }

    public int tickRate(World world) {
    	return ConfigurationHandler.minCopperOxidationTime + world.rand.nextInt(ConfigurationHandler.maxCopperOxidationTime + 1);
    }
}
