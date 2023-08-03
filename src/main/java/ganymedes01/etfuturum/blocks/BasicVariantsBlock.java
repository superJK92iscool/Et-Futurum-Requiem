package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BasicVariantsBlock extends Block implements ISubBlocksBlock {
	@SideOnly(Side.CLIENT)
	private IIcon[] icons;
	private final String[] types;

	private final int startMeta;

	public BasicVariantsBlock(Material material, String... types) {
		this(material, 0, types);
	}

	public BasicVariantsBlock(Material material, int startMeta, String... types) {
		super(material);
		this.types = types;
		this.startMeta = startMeta;
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
	public String getNameFor(ItemStack stack) {
		String type = getTypes()[Math.max(startMeta, stack.getItemDamage() % getTypes().length)];
		return "".equals(type) ? getUnlocalizedName().replace("tile.", "").replace("etfuturum.", "") : type;
	}

	@Override
	public int damageDropped(int meta) {
		return meta % getTypes().length;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = startMeta; i < getTypes().length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return getIcons()[Math.max(startMeta, meta % icons.length)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		setIcons(new IIcon[getTypes().length]);
		for (int i = 0; i < getIcons().length; i++) {
			getIcons()[i] = "".equals(getTypes()[i]) ? reg.registerIcon(getTextureName()) : reg.registerIcon(getTypes()[i]);
		}
	}
}