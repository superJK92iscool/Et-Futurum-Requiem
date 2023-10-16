package ganymedes01.etfuturum.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.ModItems;
import ganymedes01.etfuturum.items.ItemModdedRawOre;
import ganymedes01.etfuturum.lib.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockModdedRawOre extends BaseSubtypesBlock {

	private static final String[] names = new String[ItemModdedRawOre.names.length];

	static {
		for (int i = 0; i < names.length; i++) {
			names[i] = ItemModdedRawOre.names[i] + "_block";
		}
	}

	public BlockModdedRawOre() {
		super(Material.rock, names);
		setNames("modded_raw_ore");
		setHardness(5);
		setResistance(6);
		setHarvestLevel("pickaxe", 2); //TODO: Config for this since some mods use different harvest levels for these materials
		setHarvestLevel("pickaxe", 1, 0);
		setHarvestLevel("pickaxe", 1, 1);
		setHarvestLevel("pickaxe", 3, 6);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		ModItems.MODDED_RAW_ORE.get().getSubItems(item, tab, list);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		setIcons(new IIcon[getTypes().length]);
		for (int i = 0; i < getTypes().length; i++) {
			getIcons()[i] = reg.registerIcon(Reference.MOD_ID + ":" + getTypes()[i]);
		}
	}
}
