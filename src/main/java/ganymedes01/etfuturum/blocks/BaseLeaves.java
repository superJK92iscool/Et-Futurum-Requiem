package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public abstract class BaseLeaves extends BlockLeaves implements ISubBlocksBlock {

	private final String[] types;

	public BaseLeaves(String... types) {
		this.types = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			this.types[i] = types[i] + "_leaves";
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < getTypes().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return field_150129_M[isOpaqueCube() /*OptiFine compat*/ ? 1 : 0][(p_149691_2_ % 4) % types.length];
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		this.field_150129_M[0] = new IIcon[types.length];
		this.field_150129_M[1] = new IIcon[types.length];
		for (int i = 0; i < types.length; ++i) {
			this.field_150129_M[0][i] = p_149651_1_.registerIcon(types[i]);
			this.field_150129_M[1][i] = p_149651_1_.registerIcon(types[i] + "_opaque");
		}
	}

	@Override
	public String[] func_150125_e() {
		return getTypes();
	}

	public abstract Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_);

	@Override
	public boolean isOpaqueCube() { //OptiFine compat
		return Blocks.leaves.isOpaqueCube();
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) { //OptiFine compat
		return Blocks.leaves.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 30;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return 60;
	}

	@Override
	public IIcon[] getIcons() {
		return field_150129_M[0];
	}

	@Override
	public String[] getTypes() {
		return types;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return types[stack.getItemDamage() % types.length];
	}
}
