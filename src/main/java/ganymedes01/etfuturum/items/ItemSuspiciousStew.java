package ganymedes01.etfuturum.items;

import org.apache.commons.lang3.ArrayUtils;

import ganymedes01.etfuturum.IConfigurable;
import ganymedes01.etfuturum.configuration.ConfigurationHandler;
import ganymedes01.etfuturum.core.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSuspiciousStew extends ItemSoup implements IConfigurable {

	public static final String stewEffect = "Effect";
	public static final String stewEffectDuration = "EffectDuration";
	public static final String stewEffectLevel = "EffectLevel";
	
	public ItemSuspiciousStew() {
		super(6);
		setTextureName("suspicious_stew");
		setUnlocalizedName(Utils.getUnlocalisedName("suspicious_stew"));
		setCreativeTab(null);
	}

    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
    	applyPotionEffect(p_77654_1_, p_77654_3_);
    	return super.onEaten(p_77654_1_, p_77654_2_, p_77654_3_);
    }
    
	@Override
	public boolean isEnabled() {
		return ConfigurationHandler.enableSuspiciousStew;
	}

	public void applyPotionEffect(ItemStack stack, EntityPlayer player) {
		if(hasPotion(stack) && player != null) {
			if(stack.getTagCompound().getInteger(stewEffect) > 0 && stack.getTagCompound().getInteger(stewEffect) < Potion.potionTypes.length && Potion.potionTypes[stack.getTagCompound().getInteger(stewEffect)] != null)
				player.addPotionEffect(new PotionEffect(stack.getTagCompound().getInteger(stewEffect), stack.getTagCompound().getInteger(stewEffectDuration), stack.getTagCompound().getInteger(stewEffectLevel)));
		}
	}
	
	public boolean hasPotion(ItemStack stack) {
		if(!stack.hasTagCompound())
			return false;
		return stack.getTagCompound().hasKey(stewEffect) && stack.getTagCompound().hasKey(stewEffectDuration) && stack.getTagCompound().hasKey(stewEffectLevel);
	}
	
    public boolean getShareTag()
    {
        return true;
    }
}
