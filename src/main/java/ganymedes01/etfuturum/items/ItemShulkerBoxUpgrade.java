package ganymedes01.etfuturum.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemShulkerBoxUpgrade extends ItemGeneric {
	
	private static final int[] UPGRADE_IDS = new int[] {1, 4, 2, 3, 7, 6, 1, 5, 2};

	public ItemShulkerBoxUpgrade() {
		super("vanilla_iron_upgrade", "vanilla_copper_upgrade", "iron_gold_upgrade", "gold_diamond_upgrade", "diamond_obsidian_upgrade", "diamond_crystal_upgrade", "copper_iron_upgrade", "copper_silver_upgrade", "silver_gold_upgrade");

		setTextureName("shulker_box_upgrade");
		setUnlocalizedName(Utils.getUnlocalisedName("shulker_box_upgrade"));
		setCreativeTab(EtFuturum.creativeTabItems);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item.shulker_" + types[Math.max(Math.min(stack.getItemDamage(), types.length - 1), 0)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++)
			icons[i] = reg.registerIcon("ironshulkerbox:" + types[i]);
	}

}
