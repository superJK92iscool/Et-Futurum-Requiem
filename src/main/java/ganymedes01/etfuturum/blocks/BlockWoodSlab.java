package ganymedes01.etfuturum.blocks;

import com.google.common.collect.Lists;
import ganymedes01.etfuturum.ModBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockWoodSlab extends BaseSlab {
	final BlockWoodPlanks basePlanks;

	public BlockWoodSlab(boolean isDouble) {
		super(isDouble, Material.wood, "crimson", "warped");
		basePlanks = (BlockWoodPlanks) ModBlocks.WOOD_PLANKS.get();
		setHardness(2.0F);
		setResistance(5.0F);
		setStepSound(soundTypeWood);
	}

	@Override
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		List<ItemStack> list = Lists.newArrayList();
		ModBlocks.WOOD_PLANKS.get().getSubBlocks(p_149666_1_, p_149666_2_, list);
		for (int i = 0; i < Math.min(list.size(), 8); i++) {
			p_149666_3_.add(list.get(i));
		}
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return basePlanks.getIcon(p_149691_1_, p_149691_2_ % 8);
	}
}
