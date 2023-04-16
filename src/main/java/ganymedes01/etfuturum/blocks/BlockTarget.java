package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTarget extends Block implements IConfigurable {
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	public BlockTarget() {
		super(Material.rock);
		setHardness(0.5F);
		setResistance(0.5F);
		setStepSound(soundTypePiston);
		setBlockTextureName("target");
		setBlockName(Utils.getUnlocalisedName("target"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
		setStepSound(soundTypeGrass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon("target_side");
		topIcon = p_149651_1_.registerIcon("target_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? topIcon : blockIcon;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity e) {
		if(world.isRemote)
			return;
		int power = world.getBlockMetadata(x, y, z);
		if(power == 0 && e instanceof IProjectile) {
			int newMeta = determinePower(world, x, y, z, e);
			world.setBlockMetadataWithNotify(x, y, z, newMeta, 3);
			int delay = e instanceof EntityArrow ? 20 : 8;
			world.scheduleBlockUpdate(x, y, z, this, delay);
		}
	}

	private static int findSideHit(int p_150071_1_, int p_150071_2_, int p_150071_3_, Entity p_150071_4_) {
		if (MathHelper.abs((float)p_150071_4_.posX - (float)p_150071_1_) < 2.0F && MathHelper.abs((float)p_150071_4_.posZ - (float)p_150071_3_) < 2.0F)
		{
			double d0 = p_150071_4_.posY + 1.82D - (double)p_150071_4_.yOffset;

			if (d0 - (double)p_150071_2_ > 2.0D)
			{
				return 1;
			}

			if ((double)p_150071_2_ - d0 > 0.0D)
			{
				return 0;
			}
		}

		int l = MathHelper.floor_double((double)(p_150071_4_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
	}

	private static double fractionalPos(double n) {
		return n - MathHelper.floor_double(n);
	}

	private static int determinePower(World world, int x, int y, int z, Entity e) {
		MovingObjectPosition hit = world.func_147447_a(Vec3.createVectorHelper(e.posX, e.posY, e.posZ), Vec3.createVectorHelper(x + 0.5, y + 0.5, z + 0.5), true, false, false);
		if (hit != null && hit.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			double xDiff = Math.abs(fractionalPos(hit.hitVec.xCoord) - 0.5);
			double yDiff = Math.abs(fractionalPos(hit.hitVec.yCoord) - 0.5);
			double zDiff = Math.abs(fractionalPos(hit.hitVec.zCoord) - 0.5);
			double finalDiff;
			switch(hit.sideHit) {
				default:
				case 0:
				case 1:
					finalDiff = Math.max(xDiff, zDiff);
					break;
				case 2:
				case 3:
					finalDiff = Math.max(xDiff, yDiff);
					break;
				case 4:
				case 5:
					finalDiff = Math.max(yDiff, zDiff);
					break;
			}
			return Math.max(1, MathHelper.ceiling_double_int(15.0 * MathHelper.clamp_double((0.5 - finalDiff) / 0.5, 0.0, 1.0)));
		}
		return 0;
	}

	@Override
	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
		p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, 0, 3);
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_) {
		return isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_) {
		return p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
	}

	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableTarget;
	}
}
