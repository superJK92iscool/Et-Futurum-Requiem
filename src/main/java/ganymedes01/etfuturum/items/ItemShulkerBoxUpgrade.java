package ganymedes01.etfuturum.items;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.EtFuturum;
import ganymedes01.etfuturum.blocks.IConfigurable;
import ganymedes01.etfuturum.configuration.configs.ConfigBlocksItems;
import ganymedes01.etfuturum.core.utils.ExternalContent;
import ganymedes01.etfuturum.core.utils.Utils;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox;
import ganymedes01.etfuturum.tileentities.TileEntityShulkerBox.ShulkerBoxType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemShulkerBoxUpgrade extends ItemGeneric implements IConfigurable {
	
	private static final int[] UPGRADE_IDS = new int[] {1, 4, 2, 3, 7, 6, 1, 5, 2};

	public ItemShulkerBoxUpgrade() {
		super("vanilla_iron_upgrade", "vanilla_copper_upgrade", "iron_gold_upgrade", "gold_diamond_upgrade", "diamond_obsidian_upgrade", "diamond_crystal_upgrade", "copper_iron_upgrade", "copper_silver_upgrade", "silver_gold_upgrade");

		setTextureName("hulker_box_upgrade");
		setUnlocalizedName(Utils.getUnlocalisedName("shulker_box_upgrade"));
		setCreativeTab(isEnabled() ? EtFuturum.creativeTabItems : null);
	}
  public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_)
  {
	  System.out.println(getUnlocalizedName(p_77648_1_));
	  return false;
  }

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + "shulker_" + types[Math.max(Math.min(stack.getItemDamage(), types.length - 1), 0)];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		icons = new IIcon[types.length];
		for (int i = 0; i < types.length; i++)
			icons[i] = reg.registerIcon("ironshulkerbox:" + types[i]);
	}
	
	@Override
	public boolean isEnabled() {
		return ExternalContent.hasIronChest && ConfigBlocksItems.enableShulkerBoxesIronChest;
	}

}
