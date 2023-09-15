package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockLodestone extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;

	public BlockLodestone() {
		super(Material.rock);
		setHardness(3.5F);
		setResistance(3.5F);
		setHarvestLevel("pickaxe", 1);
		setBlockName(Utils.getUnlocalisedName("lodestone"));
		setBlockTextureName("lodestone");
		setCreativeTab(EtFuturum.creativeTabBlocks);
		Utils.setBlockSound(this, ModSounds.soundLodestone);
		setTickRandomly(true);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return p_149691_1_ > 1 ? blockIcon : topIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
	}

}
