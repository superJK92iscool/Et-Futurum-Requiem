package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockAzalea extends BaseBush implements ISubBlocksBlock {


	@SideOnly(Side.CLIENT)
	public IIcon sideIcon;
	@SideOnly(Side.CLIENT)
	public IIcon topIcon;
	public int meta;


	private IIcon[] icons;
	private final String[] types = new String[]{"azalea", "flowering_azalea"};

	public BlockAzalea(int meta) {
		super(Material.wood);

		setHardness(0.0F);
		setResistance(0.0F);
		setMapColorBaseBlock(Blocks.grass);
		setBlockSound(ModSounds.soundMoss);
		setBlockName(Utils.getUnlocalisedName("azalea"));
		setBlockTextureName("azalea");
		setCreativeTab(EtFuturum.creativeTabBlocks);
		this.meta = meta;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlock(x, y - 1, z).getMaterial() == Material.grass || world.getBlock(x, y - 1, z).getMaterial() == Material.grass; // Azalea can only be placed on grass
	}

	@Override
	public boolean canPlaceBlockOn(Block block) {
		return block.getMaterial() == Material.grass;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		// Define the dimensions of the top and bottom parts of the block
		double minX = x + 0.0;
		double minZ = z + 0.0;
		double maxX = x + 1.0;
		double maxYTop = y + 0.5;
		double maxZ = z + 1.0;

		return AxisAlignedBB.getBoundingBox(minX, maxYTop, minZ, maxX, maxYTop + 0.5, maxZ);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}

//	@Override
//	public int getRenderBlockPass() {
//		return RenderIDs.AZALEA;
//	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_plant");
		this.sideIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		this.topIcon = p_149651_1_.registerIcon(this.getTextureName() + "_top");
	}

	@Override
	public IIcon[] getIcons() {
		return icons;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 1){
			return this.sideIcon;
		}
		if(side == 2){
			return this.topIcon;
		}
		return this.blockIcon;
	}

	@Override
	public String[] getTypes() {
		return types;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return getTypes()[stack.getItemDamage() % types.length];
	}
}