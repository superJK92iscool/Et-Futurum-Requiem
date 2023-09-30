package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;

/**
 * Because the standard block constructor is protected...
 * I also provide some extra helper functions and stuff :3
 */
public class BaseBush extends BlockBush {

	private Block mapColorBase;
	private boolean interpolatedIcon;

	public BaseBush(Material p_i45394_1_) {
		super(p_i45394_1_);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	public BaseBush setUnlocalizedNameWithPrefix(String name) {
		setBlockName(Utils.getUnlocalisedName(name));
		return this;
	}

	@Override
	public Block setBlockTextureName(String name) {
		if (name.endsWith("$i")) {
			interpolatedIcon = true;
			name = name.substring(0, name.length() - 2);
		}
		return super.setBlockTextureName(name);
	}

	public BaseBush setNames(String name) {
		if (name.endsWith("$i")) {
			interpolatedIcon = true;
			name = name.substring(0, name.length() - 2);
		}
		setUnlocalizedNameWithPrefix(name);
		setBlockTextureName(name);
		return this;
	}

	public BaseBush setToolClass(String toolClass, int level) {
		for (int m = 0; m < 16; m++) {
			setHarvestLevel(toolClass, level, m);
		}
		return this;
	}

	public BaseBush setToolClass(String toolClass, int level, int meta) {
		setHarvestLevel(toolClass, level, meta);
		return this;
	}

	public BaseBush setBlockSound(SoundType type) {
		Utils.setBlockSound(this, type);
		return this;
	}

	public BaseBush setMapColorBaseBlock(Block block) {
		mapColorBase = block;
		return this;
	}

	@Override
	public MapColor getMapColor(int p_149728_1_) {
		return mapColorBase == null ? super.getMapColor(p_149728_1_) : mapColorBase.getMapColor(p_149728_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_) {
		if (interpolatedIcon) {
			blockIcon = new InterpolatedIcon(textureName);
			if (p_149651_1_ instanceof TextureMap) {
				((TextureMap) p_149651_1_).setTextureEntry(textureName, (InterpolatedIcon) blockIcon);
			}
		} else {
			super.registerBlockIcons(p_149651_1_);
		}
	}
}
