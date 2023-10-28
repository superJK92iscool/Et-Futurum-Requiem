package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface ISubBlocksBlock {
	@SideOnly(Side.CLIENT)
	IIcon[] getIcons();

	String[] getTypes();

	String getNameFor(ItemStack stack);

	default String getTextureDomain() {
		return "";
	}

	default String getNameDomain() {
		return Reference.MOD_ID;
	}
}
