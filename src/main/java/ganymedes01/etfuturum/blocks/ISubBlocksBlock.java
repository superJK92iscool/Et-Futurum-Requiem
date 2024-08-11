package ganymedes01.etfuturum.blocks;

import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface ISubBlocksBlock {

	IIcon[] getIcons();

	String[] getTypes();

	String getNameFor(ItemStack stack);

	default String getTextureDomain() {
		return "";
	}

	default String getTextureSubfolder() {
		return "";
	}

	default String getNameDomain() {
		return Reference.MOD_ID;
	}
}
