package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class BlockInfestedDeepslate extends BlockSilverfish {

	public static final String[] icon_names = new String[]{"deepslate"};

	public BlockInfestedDeepslate() {
		super();
		setBlockName(Utils.getUnlocalisedName("infested_deepslate"));
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return ModBlocks.DEEPSLATE.get().getBlockTextureFromSide(p_149691_1_);
	}

	public static boolean func_150196_a(Block p_150196_0_) {
		return p_150196_0_ == ModBlocks.DEEPSLATE.get();
	}

	public static int func_150195_a(Block p_150195_0_, int p_150195_1_) {
		return 0;
	}

	@Override
	protected ItemStack createStackedBlock(int p_149644_1_) {
		return ModBlocks.DEEPSLATE.newItemStack();
	}

	@Override
	public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List<ItemStack> p_149666_3_) {
		for (int i = 0; i < icon_names.length; ++i) {
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
		}
	}
}
