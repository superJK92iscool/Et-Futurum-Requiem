package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLantern extends Block implements IConfigurable {

	public BlockLantern() {
		super(Material.iron);
		float r = 0.0625F;
		float f = 0.375F;
		float f1 = f / 2.0F;
		this.setHarvestLevel("pickaxe", 0);
		this.setStepSound(ConfigurationHandler.enableNewBlocksSounds ? ModSounds.soundLantern : soundTypeMetal);
		this.setHardness(3.5F);
		this.setResistance(3.5F);
		this.setLightLevel(1);
		this.setLightOpacity(500);
		this.setBlockBounds(0.5F - f1, r * 1, 0.5F - f1, 0.5F + f1, r * 7, 0.5F + f1);
		this.setBlockName(Utils.getUnlocalisedName("lantern"));
		this.setBlockTextureName("lantern");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableLantern;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
	{
		float r = 0.0625F;
		float f = 0.375F;
		float f1 = f / 2.0F;
		if (p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) == 0)
			this.setBlockBounds(0.5F - f1, 0.0F, 0.5F - f1, 0.5F + f1, r * 7, 0.5F + f1);
		else
			this.setBlockBounds(0.5F - f1, r * 1, 0.5F - f1, 0.5F + f1, r * 8, 0.5F + f1);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		int i1 = world.getBlockMetadata(x, y + 1, z);
		int stairMeta = world.getBlockMetadata(x, y + 1, z) & 7;
		return  world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y, z) ||
				World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) ||
				world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN) ||
				world.getBlock(x, y + 1, z) instanceof BlockWall ||
				world.getBlock(x, y + 1, z) instanceof BlockFence ||
				((world.getBlock(x, y + 1, z) instanceof BlockStairs) && stairMeta <= 3) ||
				((world.getBlock(x, y + 1, z) instanceof BlockSlab) && i1 <= 7);
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
	{
		int i = 0;
		if (side == 0) {
			i = 1;
		} else if (side > 0){
			if (world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y, z) || World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)) {
				i = 0;
			} else {
				i = 1;
			}
		}
		return i;
	}
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		this.onNeighborBlockChange(world, x, y, z, block);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int i = world.getBlockMetadata(x, y, z);
		int i2 = world.getBlockMetadata(x, y + 1, z);
		int stairMeta1 = i2 & 7;
		
		if (i == 0 && !world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y, z) &&
				!World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)) {
			this.setLanternToAir(world, x, y, z);
		} else if (i == 1 && !world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN) &&
				!(world.getBlock(x, y + 1, z) instanceof BlockWall) &&
				!(world.getBlock(x, y + 1, z) instanceof BlockFence) &&
				!((world.getBlock(x, y + 1, z) instanceof BlockStairs) && stairMeta1 <= 3) && 
				!((world.getBlock(x, y + 1, z) instanceof BlockSlab) && i2 <= 7)) {
			this.setLanternToAir(world, x, y, z);
		}
	}
	
	public void setLanternToAir(World world, int x, int y, int z) {
		if (!world.isRemote) {
			this.dropBlockAsItem(world, x, y, z, 0, 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return "lantern";
	}
	
	@Override
	public int getRenderType()
	{
		return RenderIDs.LANTERN;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

}
