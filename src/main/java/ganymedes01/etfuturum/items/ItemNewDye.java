package ganymedes01.etfuturum.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ItemNewDye extends ItemGeneric {

	public ItemNewDye() {
		super("white", "blue", "brown", "black");
		setTextureName("dye");
		setUnlocalizedName(Utils.getUnlocalisedName("dye"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++)
			icons[i] = reg.registerIcon(types[i] + "_" + getIconString());
	}
}
