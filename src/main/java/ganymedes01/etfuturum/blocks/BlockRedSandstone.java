package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockSandStone;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockRedSandstone extends BlockSandStone {

	public BlockRedSandstone() {
		setHardness(0.8F);
		setBlockTextureName("red_sandstone");
		setBlockName(Utils.getUnlocalisedName("red_sandstone"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.field_150158_M = new IIcon[3];

		this.field_150158_M[0] = p_149651_1_.registerIcon(getTextureName());
		this.field_150158_M[1] = p_149651_1_.registerIcon("chiseled_" + getTextureName());
		this.field_150158_M[2] = p_149651_1_.registerIcon("cut_" + getTextureName());

		this.field_150159_N = p_149651_1_.registerIcon(getTextureName() + "_top");
		this.field_150160_O = p_149651_1_.registerIcon(getTextureName() + "_bottom");
	}
}