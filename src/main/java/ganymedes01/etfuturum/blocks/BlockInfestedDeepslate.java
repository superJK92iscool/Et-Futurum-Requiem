package ganymedes01.etfuturum.blocks;

import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.ModBlocks;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockInfestedDeepslate extends BlockSilverfish implements IConfigurable {

	public static final String[] field_150198_a = new String[] {"deepslate"};
	
	public BlockInfestedDeepslate() {
		super();
		setBlockName(Utils.getUnlocalisedName("infested_deepslate"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_)
	{
		return ModBlocks.deepslate.getBlockTextureFromSide(p_149691_1_);
	}
	
	public static boolean func_150196_a(Block p_150196_0_)
	{
		return p_150196_0_ == ModBlocks.deepslate;
	}
	
	public static int func_150195_a(Block p_150195_0_, int p_150195_1_)
	{
		return 0;
	}
	
	public static ImmutablePair func_150197_b(int p_150197_0_)
	{
		return new ImmutablePair(ModBlocks.deepslate, Integer.valueOf(0));
	}
	
	protected ItemStack createStackedBlock(int p_149644_1_)
	{
		return new ItemStack(ModBlocks.deepslate);
	}
	
	public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
	{
		return 0;
	}

	@Override
	public boolean isEnabled() {
		return false;
//      return ConfigurationHandler.enableDeepslate; //Add infested stone config
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
	{
		for (int i = 0; i < field_150198_a.length; ++i)
		{
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
		}
	}
}
