package ganymedes01.etfuturum.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.tileentities.TileEntityGateway;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndGateway extends BlockContainer implements IConfigurable {

	public BlockEndGateway() {
		super(Material.portal);
		this.setLightLevel(1);
		this.setResistance(3600000);
		this.setBlockUnbreakable();
		this.setBlockName(Utils.getUnlocalisedName("end_gateway"));
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);

		Blocks.end_portal.setBlockName(Utils.getUnlocalisedName("end_portal"));
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
	{
		p_149666_3_.add(new ItemStack(Blocks.end_portal, 1, 0));
		p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
	{
		return null;
	}

	public boolean canUpdate()
	{
		return true;
	}
	
	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
		return !(entity instanceof EntityDragon) && !(entity instanceof EntityWither);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityGateway();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return "end_gateway";
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = Blocks.obsidian.getIcon(0, 0);
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}

	public int quantityDropped(Random p_149745_1_)
	{
		return 0;
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public int getRenderType()
	{
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		EnumFacing facing = EnumFacing.getFront(side);
		Block block = world.getBlock(x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ());
		return !block.isOpaqueCube() && block != this;
	}
	
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, int x, int y, int z, Random rand)
	{

		TileEntity tileentity = worldIn.getTileEntity(x, y, z);

		if (tileentity instanceof TileEntityGateway)
		{
			int i = ((TileEntityGateway)tileentity).getParticleAmount();

			for (int j = 0; j < i; ++j)
			{
				double d0 = (double)((float)x + rand.nextFloat());
				double d1 = (double)((float)y + rand.nextFloat());
				double d2 = (double)((float)z + rand.nextFloat());
				double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
				double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
				double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
				int k = rand.nextInt(2) * 2 - 1;

				if (rand.nextBoolean())
				{
					d2 = (double)z + 0.5D + 0.25D * (double)k;
					d5 = (double)(rand.nextFloat() * 2.0F * (float)k);
				}
				else
				{
					d0 = (double)x + 0.5D + 0.25D * (double)k;
					d3 = (double)(rand.nextFloat() * 2.0F * (float)k);
				}

				worldIn.spawnParticle("portal", d0, d1, d2, d3, d4, d5);
			}
		}
	}
	
	@Override
	public boolean isEnabled() {
		return EtFuturum.isTesting;
	}
}
