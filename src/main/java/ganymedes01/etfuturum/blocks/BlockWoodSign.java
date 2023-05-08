package ganymedes01.etfuturum.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.recipes.ModRecipes;
import net.minecraft.block.BlockSign;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockWoodSign extends BlockSign {
	
	public final int meta;
	public final boolean standing;

	public BlockWoodSign(Class<?> p_i45426_1_, boolean p_i45426_2_, int i) {
		super(p_i45426_1_, p_i45426_2_);
//      if(i >= 6) {
//          EtFuturum.forceSetMaterial(this, EtFuturum.netherwood);
//      }
		setHarvestLevel("axe", 0);
		setHardness(1.0F);
		disableStats();
		setStepSound(soundTypeWood);
		setBlockName(Utils.getUnlocalisedName((!p_i45426_2_ ? "wall_" : "") + "sign_" + ModRecipes.woodTypes[i]));
		setCreativeTab(null);
		standing = p_i45426_2_;
		meta = i;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int _meta) {
//      if(this.meta < 6)
			return Blocks.planks.getIcon(side, this.meta);
//      else
//          return ModBlocks.nether_planks.getIcon(side, this.meta - 6);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return ModItems.signs[meta - 1];
	}
	

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
	{
		return ModItems.signs[meta - 1];
	}

}
