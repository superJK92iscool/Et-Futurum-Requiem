package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BaseSlab extends BlockSlab implements ISubBlocksBlock {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	public final String[] types;
	private final BaseSlab singleSlab;
	private BaseSlab doubleSlab;
	/**
	 * Used to know the previous slab registered, so the slab knows what the double slab version is without passing it as a constructor argument every time
	 * We store the previous slab we registered, so it can be passed to the double slab that registers after it.
	 */
	private static BaseSlab previousSlab;
	private Block mapColorBase;

	public BaseSlab(boolean isDouble, Material material, String... names) {
		super(isDouble, material);
		if (names.length > 8) {
			throw new IllegalArgumentException("Slabs can't have more than 8 subtypes! Tried to register a slab with " + names.length);
		}
		types = names;
		useNeighborBrightness = !isDouble;
		opaque = isDouble;
		if (isDouble) {
			doubleSlab = this;
			previousSlab.doubleSlab = this;
			singleSlab = previousSlab;
			previousSlab = null;
		} else {
			singleSlab = previousSlab = this;
			setCreativeTab(EtFuturum.creativeTabBlocks);
		}
	}

	public BaseSlab setUnlocalizedNameWithPrefix(String name) {
		setBlockName(Utils.getUnlocalisedName(name));
		return this;
	}

	public BaseSlab setNames(String name) {
		setUnlocalizedNameWithPrefix(name);
		setBlockTextureName(name);
		return this;
	}

	public BaseSlab setToolClass(String toolClass, int level) {
		for (int m = 0; m < 8; m++) {
			setHarvestLevel(toolClass, level, m);
		}
		return this;
	}

	public BaseSlab setToolClass(String toolClass, int level, int meta) {
		setHarvestLevel(toolClass, level, meta);
		if (meta < 8) {
			setHarvestLevel(toolClass, level, meta + 8);
		}
		return this;
	}

	public BaseSlab setMapColorBaseBlock(Block block) {
		mapColorBase = block;
		return this;
	}

	public BaseSlab setBlockSound(SoundType type) {
		Utils.setBlockSound(this, type);
		return this;
	}

	@Override
	public MapColor getMapColor(int p_149728_1_) {
		return mapColorBase == null ? super.getMapColor(p_149728_1_ % 8) : mapColorBase.getMapColor(p_149728_1_ % 8);
	}

	public BaseSlab getDoubleSlab() {
		return doubleSlab;
	}

	public BaseSlab getSingleSlab() {
		return singleSlab;
	}

	@Override
	public IIcon[] getIcons() {
		return icons;
	}

	protected void setIcons(IIcon[] icons) {
		this.icons = icons;
	}

	@Override
	public String[] getTypes() {
		return types;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		setIcons(new IIcon[getTypes().length]);
		for (int i = 0; i < getIcons().length; i++) {
			getIcons()[i] = "".equals(getTypes()[i]) ? reg.registerIcon(getTextureName()) : reg.registerIcon((getTextureDomain().isEmpty() ? "" : getTextureDomain() + ":") + getTypes()[i]);
		}
		blockIcon = getIcons()[0];
	}

	@Override
	public String getNameFor(ItemStack stack) {
		return func_150002_b(stack.getItemDamage());
	}

	@Override
	public String func_150002_b(int meta) {
		String type = getTypes()[Math.max(0, (meta % 8) % getTypes().length)];
		type = ("".equals(type) ? getUnlocalizedName().replace("tile.", "").replace("etfuturum.", "") : type);
		if (type.toLowerCase().endsWith("bricks") || type.toLowerCase().endsWith("tiles")) {
			type = type.substring(0, type.length() - 1);
		}
		return type + "_slab";
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		if (!field_150004_a) {
			for (int i = 0; i < types.length; i++) {
				p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[(meta % 8) % icons.length];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return Item.getItemFromBlock(singleSlab);
	}

	/*
	 * Vanilla slabs don't override this so how the hell do they make double slabs drop the single variant?
	 * If I could figure it out I likely won't need to store the single slab...
	 */
	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(singleSlab);
	}
}
