package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BaseWall extends BlockWall implements ISubBlocksBlock {

	private final Block[] blocks;
	private final int[] metas;
	private final String[] names;

	public int variations;

	public BaseWall(String string, Block[] blocks, int[] metas, String[] names) {
		super(blocks[0]);
		this.blocks = blocks;
		this.metas = metas;
		this.names = names;
		variations = blocks.length;
		setStepSound(blocks[0].stepSound);
		setCreativeTab(EtFuturum.creativeTabBlocks);
		setBlockName(Utils.getUnlocalisedName(string));
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return blocks[p_149691_2_ % variations].getIcon(p_149691_1_, metas[p_149691_2_ % variations]);
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		for (int i = 0; i < blocks.length; i++) {
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
		}
	}

	public boolean canConnectWallTo(IBlockAccess p_150091_1_, int p_150091_2_, int p_150091_3_, int p_150091_4_) {
		Block block = p_150091_1_.getBlock(p_150091_2_, p_150091_3_, p_150091_4_);
		return block instanceof BlockFenceGate || block instanceof BaseWall || super.canConnectWallTo(p_150091_1_, p_150091_2_, p_150091_3_, p_150091_4_);
	}

	public float getBlockHardness(World p_149712_1_, int p_149712_2_, int p_149712_3_, int p_149712_4_) {
		return blocks[p_149712_1_.getBlockMetadata(p_149712_2_, p_149712_3_, p_149712_4_) % variations].getBlockHardness(p_149712_1_, p_149712_2_, p_149712_3_, p_149712_4_);
	}

	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return blocks[world.getBlockMetadata(x, y, z) % variations].getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ) / 5;
	}

	@Override
	public int damageDropped(int meta) {
		return meta % variations;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public IIcon[] getIcons() {
		return new IIcon[0];
	}

	@Override
	public String[] getTypes() {
		return names;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return "tile." + getNameDomain() + "." + (names != null && names.length > 0 ? names[stack.getItemDamage() % variations] : getUnlocalizedName().substring(6 + Reference.MOD_ID.length()));
	}
}
