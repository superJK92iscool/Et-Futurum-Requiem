package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class BlockSmithingTable extends Block implements IConfigurable {

    @SideOnly(Side.CLIENT)
    private IIcon topIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideIcon;
    @SideOnly(Side.CLIENT)
    private IIcon bottomIcon;
    
	public BlockSmithingTable() {
		super(Material.wood);
		this.setStepSound(soundTypeWood);
		this.setHardness(2.5F);
		this.setHarvestLevel("axe", 0);
		this.setResistance(2.5F);
		this.setBlockName(Utils.getUnlocalisedName("smithing_table"));
		this.setBlockTextureName("smithing_table");
		this.useNeighborBrightness = true;
		this.setLightOpacity(500);
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return EtFuturum.isTesting;
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_1_ == 1 ? this.topIcon : (p_149691_1_ == 0 ? bottomIcon : (p_149691_1_ != 2 && p_149691_1_ != 4 ? this.blockIcon : this.sideIcon));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.sideIcon = p_149651_1_.registerIcon(this.getTextureName() + "_front");
        this.bottomIcon = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
    }

}
