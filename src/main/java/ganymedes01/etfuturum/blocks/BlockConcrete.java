package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.recipes.ModRecipes;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockConcrete extends BlockGeneric implements IConfigurable {

	public BlockConcrete() {
		super(Material.rock, ModRecipes.dye_names);
		this.setHarvestLevel("pickaxe", 0);
		this.setStepSound(soundTypeStone);
		this.setHardness(1.8F);
		this.setResistance(1.8F);
		this.setBlockName(Utils.getUnlocalisedName("concrete"));
		this.setBlockTextureName("concrete");
		this.setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++)
			if ("".equals(types[i]))
				icons[i] = reg.registerIcon(getTextureName());
			else
				icons[i] = reg.registerIcon( types[i] + "_" + getTextureName());
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableConcrete;
	}

}
