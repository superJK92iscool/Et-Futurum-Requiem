package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.items.block.ItemDecorationWorkbench;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLoom extends Block implements IConfigurable, ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon sideIcon;
	@SideOnly(Side.CLIENT)
	private IIcon bottomIcon;
	
	public BlockLoom() {
		super(Material.wood);
		this.setStepSound(soundTypeWood);
		this.setHardness(2.5F);
		this.setHarvestLevel("axe", 0);
		this.setResistance(2.5F);
		this.setBlockName(Utils.getUnlocalisedName("loom"));
		this.setBlockTextureName("loom");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
	{
		int ordinal = MathHelper.floor_double((double)(p_149689_5_.rotationYaw / 90.0F) + 0.5D) & 3;

		if (ordinal == 1)
		{
			ordinal = 3;
		} else if (ordinal == 2)
		{
			ordinal = 1;
		} else if (ordinal == 3)
		{
			ordinal = 2;
		}
		
		p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, ordinal, 2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return p_149691_1_ == 1 ? this.topIcon : (p_149691_1_ == 0 ? this.bottomIcon : (p_149691_1_ != p_149691_2_ + 2 ? this.sideIcon : this.blockIcon));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_front");
		this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
		this.sideIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		this.bottomIcon = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
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
	
	public int getRenderType()
	{
		return RenderIDs.LOOM;
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableLoom;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemDecorationWorkbench.class;
	}
}