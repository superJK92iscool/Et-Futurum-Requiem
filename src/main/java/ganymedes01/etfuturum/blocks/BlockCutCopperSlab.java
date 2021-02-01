package ganymedes01.etfuturum.blocks;

import java.util.Random;

import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCutCopperSlab extends BlockGenericSlab implements IConfigurable {

	public BlockCutCopperSlab(boolean p_i45410_1_) {
		super(p_i45410_1_, Material.iron, "", "lightly_weathered", "semi_weathered", "weathered", "waxed", "waxed_lightly_weathered", "waxed_semi_weathered");
		setHardness(3);
		setResistance(6);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("cut_copper_slab"));
		setBlockTextureName("cut_copper");
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundCopper : Block.soundTypeMetal);
	}

    public void onBlockAdded(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z) % 8;
        if (meta > 2)
        	return;
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    	
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote)
            return;
        int meta = world.getBlockMetadata(x, y, z);
        if (meta % 8 > 2)
        	return;
        if(world.getBlock(x, y, z) == this) {
        	world.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
        }
        if(!(meta % 8 + 1 > 2))
            world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    }
    
    public int tickRate(World world) {
    	return ConfigurationHandler.minCopperOxidationTime + world.rand.nextInt(ConfigurationHandler.maxCopperOxidationTime + 1);
    }

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableCopper;
	}

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab)ModBlocks.cut_copper_slab, (BlockGenericSlab)ModBlocks.double_cut_copper_slab};
	}

    public String func_150002_b(int meta)
    {
    	meta %= 8;
        		
        if(meta >= metaBlocks.length) {
        	meta = 0;
        }

        if(metaBlocks[meta].equals("")) {
        	return super.getUnlocalizedName();
        } else {
            return "tile.etfuturum." + metaBlocks[meta] + "_" + super.getUnlocalizedName().split("tile.etfuturum.")[1];
        }
    }

	@Override
	public IIcon[] getSlabIcons(int side) {
		IIcon[] blocks = new IIcon[7];
        for(int i = 0; i < 7; i++) {
        	blocks[i] = ModBlocks.copper_block.getIcon(side, (i % 4) + 4);
        };
        return blocks;
	}

}
