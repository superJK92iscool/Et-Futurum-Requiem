package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigExperiments;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockNetherRoots extends BlockBush implements ISubBlocksBlock {

	private IIcon[] icons;
	private IIcon[] pottedIcons;
	private final String[] types = new String[]{"crimson_roots", "warped_roots"};
	private static final String name = "roots"; //Bypass stupid MC dev warning for no translation key

	public BlockNetherRoots() {
		Utils.setBlockSound(this, ModSounds.soundNetherRoots);
		setBlockName(name);
		setBlockTextureName(name);
		setCreativeTab(EtFuturum.creativeTabBlocks);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
		return EnumPlantType.Nether;
	}

	public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_) {
		Block block = p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_);
		return block == Blocks.mycelium || block.canSustainPlant(p_149718_1_, p_149718_2_, p_149718_3_ - 1, p_149718_4_, ForgeDirection.UP, this)
				|| block.canSustainPlant(p_149718_1_, p_149718_2_, p_149718_3_ - 1, p_149718_4_, ForgeDirection.UP, Blocks.tallgrass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[meta % icons.length];
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TileEntityFlowerPot && world.getBlock(x, y, z).getRenderType() == 33) {
			return pottedIcons[((TileEntityFlowerPot) te).getFlowerPotData() % icons.length];
		}
		return super.getIcon(world, x, y, z, side);
	}

	@Override
	public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
		return damageDropped(p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_));
	}

	@Override
	public int damageDropped(int p_149692_1_) {
		return p_149692_1_;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if (ConfigExperiments.enableCrimsonBlocks) {
			list.add(new ItemStack(item, 1, 0));
		}
		if (ConfigExperiments.enableWarpedBlocks) {
			list.add(new ItemStack(item, 1, 1));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[2];
		pottedIcons = new IIcon[2];

		blockIcon = icons[0] = reg.registerIcon("crimson_roots");
		icons[1] = reg.registerIcon("warped_roots");
		pottedIcons[0] = reg.registerIcon("crimson_roots_pot");
		pottedIcons[1] = reg.registerIcon("warped_roots_pot");
	}

	@Override
	public IIcon[] getIcons() {
		return icons;
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
