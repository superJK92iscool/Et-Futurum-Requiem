package ganymedes01.etfuturum.items;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.tuple.MutablePair;

import com.google.common.collect.Maps;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemLodestoneCompass extends Item {
	
	public static final Map<UUID, MutablePair<Double, Double>> lodestoneCompasses = Maps.newHashMap();
	
	@SideOnly(Side.CLIENT)
	private IIcon[] frames;

	public ItemLodestoneCompass() {
		setTextureName("lodestone_compass");
		setUnlocalizedName(Utils.getUnlocalisedName("lodestone_compass"));
		setCreativeTab(null);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_149651_1_)
	{
		frames = new IIcon[32];
		for(int i = 0; i < frames.length; i++) {
			frames[i] = p_149651_1_.registerIcon(getIconString() + "_" + String.format("%02d", i));
		}
		itemIcon = frames[0];
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack p_77636_1_)
	{
		return true;
	}

	/**
	 * Return an item rarity from EnumRarity
	 */
	public EnumRarity getRarity(ItemStack p_77613_1_)
	{
		return EnumRarity.common;
	}
}
