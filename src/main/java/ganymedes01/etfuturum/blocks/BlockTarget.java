package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.api.HoeRegistry;
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

public class BlockTarget extends BaseBlock {
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;

	public BlockTarget() {
		super(Material.grass);
		setNames("target");
		setStepSound(soundTypeGrass);
		setHardness(0.5F);
		setResistance(0.5F);
		HoeRegistry.addToHoeArray(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(getTextureName() + "_side");
		topIcon = p_149651_1_.registerIcon(getTextureName() + "_top");
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

	/**
	 * For some reason, returning FALSE will do the power updates??
	 * This function should be named shouldNOTCheckPower?!
	 */
	public boolean shouldCheckWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return false;
	}

	@Override
	public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int meta) {
		return isProvidingWeakPower(world, x, y, z, meta);
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int meta) {
		return world.getBlockMetadata(x, y, z);
	}

	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	public boolean isNormalCube() {
		return true;
	}

	public boolean isOpaqueCube() {
		return true;
	}
}
