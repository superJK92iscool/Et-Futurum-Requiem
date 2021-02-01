package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class PrismarineSlab extends BlockGenericSlab implements IConfigurable {

    public PrismarineSlab(boolean p_i45410_1_) {
        super(p_i45410_1_, Material.rock, "rough", "brick", "dark");
        setHardness(1.5F);
        setResistance(6.0F);
        setBlockName(Utils.getUnlocalisedName("prismarine_slab"));
        setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
    }

    @Override
    public boolean isEnabled() {
        return ConfigurationHandler.enablePrismarine;
    }

	@Override
	public BlockGenericSlab[] getSlabTypes() {
		return new BlockGenericSlab[] {(BlockGenericSlab)ModBlocks.prismarine_slab, (BlockGenericSlab)ModBlocks.double_prismarine_slab};
	}
	
    public String func_150002_b(int meta)
    {
    	meta %= 8;
        		
        if(meta >= metaBlocks.length) {
        	meta = 0;
        }

        return "tile.etfuturum." + metaBlocks[meta] + "_" + super.getUnlocalizedName().split("\\.")[2];
    }

	@Override
	public IIcon[] getSlabIcons(int side) {
		IIcon[] blocks = new IIcon[3];
        for(int i = 0; i < 3; i++) {
        	blocks[i] = ModBlocks.prismarine.getIcon(side, i);
        };
        return blocks;
	}
}