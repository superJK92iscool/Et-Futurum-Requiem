package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.RenderIDs;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.List;

public class BlockAzalea extends BlockBush implements ISubBlocksBlock {


	@SideOnly(Side.CLIENT)
	public IIcon[] sideIcons;
	@SideOnly(Side.CLIENT)
	public IIcon[] topIcons;
	public int meta;

	private final String[] types = new String[]{"azalea", "flowering_azalea"};

	public BlockAzalea() {
		super(Material.wood);
		setHardness(0.0F);
		setResistance(0.0F);
		Utils.setBlockSound(this, ModSounds.soundAzalea);
		setBlockName(Utils.getUnlocalisedName("azalea"));
		setBlockTextureName("azalea");
		setCreativeTab(EtFuturum.creativeTabBlocks);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Plains;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return world.getBlock(x, y - 1, z).getMaterial() == Material.clay || super.canBlockStay(world, x, y, z);
	}

	public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
		setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
		setBlockBounds(0.4375F, 0.5F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
		super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x + 0.0F, y + 0.5F, z + 0.0F, x + 1.0F, y + 1.0F, z + 1.0F);
	}

	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIDs.AZALEA;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		return p_149646_5_ != 0 && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_plant");

		sideIcons = new IIcon[2];
		topIcons = new IIcon[2];
		sideIcons[0] = p_149651_1_.registerIcon(this.getTextureName() + "_side");
		sideIcons[1] = p_149651_1_.registerIcon("flowering_" + this.getTextureName() + "_side");
		topIcons[0] = p_149651_1_.registerIcon(this.getTextureName() + "_top");
		topIcons[1] = p_149651_1_.registerIcon("flowering_" + this.getTextureName() + "_top");
	}

	@Override
	public IIcon[] getIcons() {
		return sideIcons;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0) {
			return this.blockIcon;
		}
		if (side == 1) {
			return topIcons[meta % topIcons.length];
		}
		return sideIcons[meta % topIcons.length];
	}

	@Override
	public String[] getTypes() {
		return types;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return getTypes()[stack.getItemDamage() % types.length];
	}

	@Override
	public MapColor getMapColor(int p_149728_1_) {
		return MapColor.grassColor;
	}
}