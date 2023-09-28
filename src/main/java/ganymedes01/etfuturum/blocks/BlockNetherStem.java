package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.client.InterpolatedIcon;
import ganymedes01.etfuturum.client.sound.ModSounds;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import java.util.List;

public class BlockNetherStem extends BlockLog implements ISubBlocksBlock {

	private final String type;
	public static final String[] icon_names = new String[]{"stem", "hyphae", "stripped_stem", "stripped_hyphae"};

	public BlockNetherStem(String type) {
		super();
		this.type = type;
		setBlockName(Utils.getUnlocalisedName(type + "_stem"));
		Utils.setBlockSound(this, ModSounds.soundStem);
		setCreativeTab(EtFuturum.creativeTabBlocks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		InterpolatedIcon animatedStem = new InterpolatedIcon(type + "_stem");
		if (iconRegister instanceof TextureMap) {
			((TextureMap) iconRegister).setTextureEntry(type + "_stem", animatedStem);
		}

		field_150167_a = new IIcon[4];
		field_150166_b = new IIcon[4];

		blockIcon = field_150166_b[1] = field_150167_a[0] = field_150167_a[1] = animatedStem;
		field_150166_b[3] = field_150167_a[2] = field_150167_a[3] = iconRegister.registerIcon("stripped_" + type + "_stem");
		field_150166_b[0] = iconRegister.registerIcon(type + "_stem_top");
		field_150166_b[2] = iconRegister.registerIcon("stripped_" + type + "_stem_top");
	}

	@Override
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
		if (ConfigBlocksItems.enableBarkLogs) {
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
		}
		if (ConfigBlocksItems.enableStrippedLogs) {
			if (ConfigBlocksItems.enableBarkLogs) {
				p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
			}
			p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
		}
	}

	@Override
	public IIcon[] getIcons() {
		return field_150167_a;
	}

	@Override
	public String[] getTypes() {
		return icon_names;
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return getName(stack.getItemDamage() % 4);
	}

	private String getName(int meta) {
		switch (meta) {
			case 1:
				return type + "_hyphae";
			case 2:
				return "stripped_" + type + "_stem";
			case 3:
				return "stripped_" + type + "_hyphae";
			default:
			case 0:
				return type + "_stem";
		}
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
		return false;
	}
}
