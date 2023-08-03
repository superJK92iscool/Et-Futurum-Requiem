package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface ISubBlocksBlock {
	@SideOnly(Side.CLIENT)
	IIcon[] getIcons();

	String[] getTypes();

	String getNameFor(ItemStack stack);
}
