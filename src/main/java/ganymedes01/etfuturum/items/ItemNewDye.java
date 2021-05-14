package ganymedes01.etfuturum.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemNewDye extends ItemGeneric implements IConfigurable {

	public ItemNewDye() {
		super("white", "blue", "brown", "black");
		setTextureName("dye");
		setUnlocalizedName(Utils.getUnlocalisedName("dye"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}
	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableNewDyes;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++)
			icons[i] = reg.registerIcon(types[i] + "_" + getIconString());
	}
}
