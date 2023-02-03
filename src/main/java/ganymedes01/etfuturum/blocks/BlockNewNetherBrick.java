package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.configuration.configs.ConfigSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockNewNetherBrick extends BlockGeneric implements IConfigurable {

	public BlockNewNetherBrick()
	{
		super(Material.rock, "", "cracked", "chiseled");
		this.setResistance(6);
		this.setHardness(2);
		setStepSound(ConfigSounds.enableNewBlockSounds ? ModSounds.soundNetherBricks : soundTypePiston);
		this.setHarvestLevel("pickaxe", 0);
		this.setBlockTextureName("nether_bricks");
		setBlockName(Utils.getUnlocalisedName("red_netherbrick"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabBlocks : null);
	}
	
	@Override
	public boolean isEnabled() {
		return ConfigBlocksItems.enableNewNetherBricks;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++)
			if ("".equals(types[i]))
				icons[i] = reg.registerIcon("red_" + getTextureName());
			else
				icons[i] = reg.registerIcon( types[i] + "_" + getTextureName());
	}
}
