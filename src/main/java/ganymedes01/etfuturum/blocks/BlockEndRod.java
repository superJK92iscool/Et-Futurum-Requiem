package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.particle.ParticleHandler;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockEndRod extends Block implements IConfigurable {

	public BlockEndRod() {
		super(Material.circuits);
		setStepSound(Block.soundTypeWood);
		setHardness(0);
		setLightLevel(0.9375F);
		setBlockTextureName("end_rod");
		setBlockName(Utils.getUnlocalisedName("end_rod"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {
		return !(entity instanceof EntityDragon);
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z) % 6);
		Random rand = new Random();
		double d0 = (double)x + 0.55D - (double)(rand.nextFloat() * 0.1F);
		double d1 = (double)y + 0.55D - (double)(rand.nextFloat() * 0.1F);
		double d2 = (double)z + 0.55D - (double)(rand.nextFloat() * 0.1F);
		double d3 = (double)(0.4F - (rand.nextFloat() + rand.nextFloat()) * 0.4F);

		if (rand.nextInt(5) == 0)
		{
			ParticleHandler.END_ROD.spawn(world, d0 + dir.offsetX * d3, d1 + dir.offsetY * d3, d2 + dir.offsetZ * d3);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z) % 6);

		if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP)
			setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 1.0F, 0.625F);
		else if (dir == ForgeDirection.WEST || dir == ForgeDirection.EAST)
			setBlockBounds(0.0F, 0.375F, 0.375F, 1.0F, 0.625F, 0.625F);
		else if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH)
			setBlockBounds(0.375F, 0.375F, 0.0F, 0.625F, 0.625F, 1.0F);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		ForgeDirection dir = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z) % 6);

		if (dir == ForgeDirection.DOWN || dir == ForgeDirection.UP)
			return AxisAlignedBB.getBoundingBox(x+0.375F, y+0.0F, z+0.375F, x+0.625F, y+1.0F, z+0.625F);
		else if (dir == ForgeDirection.WEST || dir == ForgeDirection.EAST)
			return AxisAlignedBB.getBoundingBox(x+0.0F, y+0.375F, z+0.375F, x+1.0F, y+0.625F, z+0.625F);
		else if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH)
			return AxisAlignedBB.getBoundingBox(x+0.375F, y+0.375F, z+0.0F, x+0.625F, y+0.625F, z+1.0F);
		return null;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return this.getCollisionBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		ForgeDirection dir = ForgeDirection.getOrientation(side).getOpposite();
		if (world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) != this)
			dir = dir.getOpposite();
		return dir.ordinal();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableChorusFruit;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.END_ROD;
	}
}