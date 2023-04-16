package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.api.IBlockObserver;
import ganymedes01.etfuturum.configuration.configs.ConfigMixins;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.ducks.IObserverWorldExtension;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockObserver extends Block implements IConfigurable, IBlockObserver {
	@SideOnly(Side.CLIENT)
	private IIcon observerFront, observerBack, observerTop, observerBackLit;

	public BlockObserver() {
		super(Material.rock);
		this.setHardness(3F);
		this.setHarvestLevel("pickaxe", 0);
		this.setResistance(3F);
		this.setBlockName(Utils.getUnlocalisedName("observer"));
		this.setBlockTextureName("observer");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		int k = BlockPistonBase.getPistonOrientation(meta);
		boolean powered = (meta & 0x8) != 0;
		if(side == k)
			return this.observerFront;
		else if(side == Facing.oppositeSide[k])
			return powered ? this.observerBackLit : this.observerBack;
		else {
			int topSide;
			switch(k) {
				case 2:
				case 3:
				case 4:
				case 5:
					topSide = 1;
					break;
				default:
				case 0:
				case 1:
					topSide = 2;
					break;
			}
			return (side == topSide || side == Facing.oppositeSide[topSide]) ? this.observerTop : this.blockIcon;
		}
	}

	@Override
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
	{
		int l = BlockPistonBase.determineOrientation(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_);
		p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, Facing.oppositeSide[l], 2);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = p_149651_1_.registerIcon("observer_side");
		this.observerFront = p_149651_1_.registerIcon("observer_front");
		this.observerTop = p_149651_1_.registerIcon( "observer_top");
		this.observerBack = p_149651_1_.registerIcon( "observer_back");
		this.observerBackLit = p_149651_1_.registerIcon( "observer_back_lit");
	}

	@Override
	public int getRenderType() {
		return RenderIDs.OBSERVER;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return ConfigMixins.enableObservers;
	}

	@Override
	public void observedNeighborChange(World world, int observerX, int observerY, int observerZ, Block changedBlock, int changedX, int changedY, int changedZ) {
		if(world.isRemote)
			return;
		int myMeta = world.getBlockMetadata(observerX, observerY, observerZ);
		int facing = BlockPistonBase.getPistonOrientation(myMeta);
		int observedX = observerX + Facing.offsetsXForSide[facing];
		int observedY = observerY + Facing.offsetsYForSide[facing];
		int observedZ = observerZ + Facing.offsetsZForSide[facing];
		if(observedX == changedX && observedY == changedY && observedZ == changedZ) {
			if((myMeta & 8) == 0 && !((IObserverWorldExtension)world).etfu$hasScheduledUpdate(observerX, observerY, observerZ, this)) {
				world.scheduleBlockUpdate(observerX, observerY, observerZ, this, 2);
			}
		}
	}

	protected void updateNeighborsInFront(World worldIn, int x, int y, int z)
	{
		int facing = BlockPistonBase.getPistonOrientation(worldIn.getBlockMetadata(x, y, z));
		int opposite = Facing.oppositeSide[facing];
		int newX = x + Facing.offsetsXForSide[opposite];
		int newY = y + Facing.offsetsYForSide[opposite];
		int newZ = z + Facing.offsetsZForSide[opposite];
		worldIn.notifyBlockOfNeighborChange(newX, newY, newZ, this);
		worldIn.notifyBlocksOfNeighborChange(newX, newY, newZ, this, facing);
	}


	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		if((meta & 8) != 0) {
			world.setBlockMetadataWithNotify(x, y, z, meta & 7, 2);
		} else {
			world.setBlockMetadataWithNotify(x, y, z, meta | 8, 2);
			world.scheduleBlockUpdate(x, y, z, this, 2);
		}
		updateNeighborsInFront(world, x, y, z);
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
		int metadata = p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
		if((metadata & 8) == 0)
			return 0;
		return (BlockPistonBase.getPistonOrientation(metadata) == p_149709_5_) ? 15 : 0;
	}
}
