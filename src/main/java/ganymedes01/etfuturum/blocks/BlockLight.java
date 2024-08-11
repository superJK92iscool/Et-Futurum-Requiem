package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockLight extends BlockBarrier implements ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	private IIcon[] lightIcons;
	private static final String[] types = new String[16];

	public BlockLight() {
		super();
		setNames("light");
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		for (int i = 0; i < 16; i++) {
			types[i] = "light_" + String.format("%02d", i);
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return null;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		if (world instanceof World && ((World) world).isRemote) {
			ItemStack stack = FMLClientHandler.instance().getClientPlayerEntity().getCurrentEquippedItem();
			int hitBoxUpperBounds = stack != null && stack.getItem() == Item.getItemFromBlock(this) ? 1 : 0;
			this.setBlockBounds(0.0F, 0.0F, 0.0F, hitBoxUpperBounds, hitBoxUpperBounds, hitBoxUpperBounds);
		}
	}

	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	public int getMobilityFlag() {
		return 0;
	}

	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(this)) {
			world.setBlockMetadataWithNotify(x, y, z, (world.getBlockMetadata(x, y, z) + 1) % 16, 2);
			return true;
		}
		return false;
	}

	@Override
	public boolean isAir(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List<ItemStack> p_149666_3_) {
		for (int i = 0; i < 16; i++) {
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		lightIcons = new IIcon[16];
		for (int i = 0; i < types.length; i++) {
			lightIcons[i] = reg.registerIcon(types[i]);
		}
		blockIcon = lightIcons[0];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		return lightIcons[meta % types.length];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getParticleName(int meta) {
		return getIcon(2, meta);
	}

	@Override
	public IIcon[] getIcons() {
		return lightIcons;
	}

	@Override
	public String[] getTypes() {
		return types;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return getUnlocalizedName();
	}

	@Override
	public boolean isNormalCube() {
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_)
	{
		return true;
	}
}
