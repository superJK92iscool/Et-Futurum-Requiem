package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;

public class BlockPrismarine extends BlockGeneric implements IConfigurable {

	public BlockPrismarine() {
		super(Material.rock, "rough", "bricks", "dark");
		setHardness(1.5F);
		setResistance(10.0F);
		setBlockTextureName("prismarine");
		setBlockName(Utils.getUnlocalisedName("prismarine_block"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		if (icons == null)
			icons = new IIcon[types.length];

		for (int i = 0; i < types.length; i++) {
			String texture = getTextureName() + "_" + types[i];
			if ("rough".equals(types[i])) {
				icons[i] = new InterpolatedIcon(texture);
				if(reg instanceof TextureMap) {
					((TextureMap)reg).setTextureEntry(texture, (InterpolatedIcon)icons[i]);
				}
			} else {
				icons[i] = reg.registerIcon(texture);
			}
		}
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enablePrismarine;
	}
}