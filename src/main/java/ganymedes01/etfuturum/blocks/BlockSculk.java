package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.world.IBlockAccess;

import java.util.Random;

public class BlockSculk extends BaseBlock {
	public BlockSculk() {
		super(Material.ground);
		setNames("sculk");
		setHardness(.6F);
		setResistance(.2F);
		Utils.setBlockSound(this, ModSounds.soundSculk);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		blockIcon = new InterpolatedIcon(getTextureName());
		if(reg instanceof TextureMap) {
			((TextureMap) reg).setTextureEntry(getTextureName(), (InterpolatedIcon) blockIcon);
		}
	}

	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune) {
		return 1;
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return null;
	}
}
