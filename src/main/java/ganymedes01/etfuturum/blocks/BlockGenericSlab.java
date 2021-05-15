package ganymedes01.etfuturum.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModBlocks.ISubBlocksBlock;
import ganymedes01.etfuturum.items.block.ItemBlockGenericSlab;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public abstract class BlockGenericSlab extends BlockSlab implements ISubBlocksBlock {

	public final String[] metaBlocks;
	
	public BlockGenericSlab(boolean p_i45410_1_, Material p_i45410_2_, String... names) {
		super(p_i45410_1_, p_i45410_2_);
		metaBlocks = names;
		useNeighborBrightness = !field_150004_a;
		opaque = field_150004_a;
	}

	@Override
	public Class<? extends ItemBlock> getItemBlockClass() {
		return ItemBlockGenericSlab.class;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.etfuturum." + (field_150004_a ? "double_" : "") + super.getUnlocalizedName().split("\\.")[2];
	}

	public String func_150002_b(int meta)
	{
		meta %= 8;
				
		if(meta >= metaBlocks.length) {
			meta = 0;
		}

		if(metaBlocks[meta].equals("")) {
			return super.getUnlocalizedName();
		} else {
			return "tile.etfuturum." + metaBlocks[meta] + "_" + getUnlocalizedName().split("_")[field_150004_a ? 2 : 1];
		}
	}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
	{
		for (int i = 0; i < metaBlocks.length && !field_150004_a; i++)
		{
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
		}
	}
	
	public abstract BlockGenericSlab[] getSlabTypes();
	
	public abstract IIcon[] getSlabIcons(int side);
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		IIcon[] icons = getSlabIcons(side);
		
		meta %= 8;
				
		if(meta >= icons.length) {
			meta = 0;
		}

		return icons[meta];
	}

	public boolean isDouble() {
		return field_150004_a;
	}
	
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	{
		return Item.getItemFromBlock((BlockSlab)getSlabTypes()[0]);
	}
	
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return Item.getItemFromBlock((BlockSlab)getSlabTypes()[0]);
	}
}
