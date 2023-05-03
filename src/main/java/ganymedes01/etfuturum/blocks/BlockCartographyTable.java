package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.blocks.itemblocks.ItemBlockDecorationWorkbench;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCartographyTable extends Block implements IConfigurable, ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;
	@SideOnly(Side.CLIENT)
	private IIcon bottomIcon;
	
	public BlockCartographyTable() {
		super(Material.wood);
		this.setStepSound(soundTypeWood);
		this.setHardness(2.5F);
		this.setHarvestLevel("axe", 0);
		this.setResistance(2.5F);
		this.setBlockName(Utils.getUnlocalisedName("cartography_table"));
		this.setBlockTextureName("cartography_table");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return p_149691_1_ == 1 ? this.topIcon : (p_149691_1_ == 0 ? Blocks.planks.getIcon(0, 5) : (p_149691_1_ == 2 || p_149691_1_ == 5 ? this.bottomIcon : p_149691_1_ == 3 ? this.blockIcon : this.sideIcon));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side1");
		this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
		this.sideIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side2");
		this.bottomIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side3");
	}
	
	@Override
	public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return true;
	}
	
	@Override
	public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return 5;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
		return 20;
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableCartographyTable;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockDecorationWorkbench.class;
	}
}
